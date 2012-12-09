
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.extra.Component;
import database.extra.Ingredient;
import database.models.BasicIngredientModel;
import database.models.RecipeModel;
import database.models.SupplierModel;
import database.tables.BasicIngredient;
import database.tables.Client;
import database.tables.Recipe;
import database.tables.Supplier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import logging.MyLogger;
import tools.Configuration;
import tools.StringTools;

/**
 *
 * @author Warkst
 */
public class Database {

    private static Database driver;
    private Connection connection;
    private Statement statement;
    private Map<Integer, Supplier> suppliersById;
    private Map<Integer, BasicIngredient> basicIngredientsById;
    private Map<Integer, Recipe> recipesById;
    private Map<Integer, Client> clientsById;
    private Map<String, Supplier> suppliersByFirm;
    private Map<String, BasicIngredient> basicIngredientsByName;
    private Map<String, Recipe> recipesByName;
    private Map<String, Integer> municipales;

    private Database() {
        try {
            suppliersById = new TreeMap<Integer, Supplier>();
            basicIngredientsById = new TreeMap<Integer, BasicIngredient>();
            recipesById = new TreeMap<Integer, Recipe>();
            clientsById = new TreeMap<Integer, Client>();
	    suppliersByFirm = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);
	    basicIngredientsByName = new TreeMap<String, BasicIngredient>(String.CASE_INSENSITIVE_ORDER);
	    recipesByName = new TreeMap<String, Recipe>(String.CASE_INSENSITIVE_ORDER);
	    
            municipales = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
            // connect to db
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(Configuration.center().getDB_URL());
            statement = connection.createStatement();

            // copy data
            loadSuppliers();
            loadBasicIngredients();
            loadRecipes();
            loadMunicipales();

        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
	    MyLogger.log(ex.getMessage());
        }
    }

    public static Database driver() {
        if (driver == null) {
            driver = new Database();
        }
        return driver;
    }

    public Connection getConnection() {
        return connection;
    }

    private void loadSuppliers() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_SUP());
        while (rs.next()) {
	    Supplier s = Supplier.loadWithValues(
		    rs.getInt("id"),
		    rs.getString("firma"),
                    rs.getString("adres"),
                    rs.getString("gemeente"),
                    rs.getInt("postcode"),
                    rs.getString("tel"),
                    rs.getString("tel2"),
                    rs.getString("gsm"),
                    rs.getString("fax"),
                    rs.getString("email"),
                    rs.getString("opmerking"),
                    rs.getString("contactpersoon"),
                    rs.getBoolean("verwijderd"));
            suppliersById.put(s.getPrimaryKeyValue(), s);
            suppliersByFirm.put(s.getFirm(), s);
        }

//        System.out.println("Database driver:: loaded " + suppliersById.size() + " suppliers!");
	MyLogger.log("Database driver:: loaded " + suppliersById.size() + " suppliers!", MyLogger.LOW);
    }

    public FunctionResult<Supplier> addSupplier(SupplierModel sup) throws SQLException {
        int code = 0;
	Supplier newSup = null;
        String insertTableSQL = "INSERT INTO suppliers"
                + "(firma, adres, gemeente, tel, tel2, gsm, email, opmerking, contactpersoon, fax, postcode, verwijderd) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
        try {
            preparedStatement.setString(1, sup.getFirm());
            preparedStatement.setString(2, sup.getAddress());
            preparedStatement.setString(3, sup.getMunicipality());
            preparedStatement.setString(4, sup.getTelephone());
            preparedStatement.setString(5, sup.getTelephone2());
            preparedStatement.setString(6, sup.getCellphone());
            preparedStatement.setString(7, sup.getEmail());
            preparedStatement.setString(8, sup.getNotes());
            preparedStatement.setString(9, sup.getContact());
            preparedStatement.setString(10, sup.getFax());
            preparedStatement.setInt(11, sup.getZipcode());
            preparedStatement.setInt(12, 0);
            preparedStatement.executeUpdate();
	    
	    ResultSet rs = statement.executeQuery("SELECT id FROM "+Configuration.center().getDB_TABLE_SUP()+" WHERE firma=\""+sup.getFirm()+"\"");
	    if (rs.next()) {
		newSup = Supplier.createFromModel(rs.getInt("id"), sup);
		suppliersById.put(newSup.getPrimaryKeyValue(), newSup);
		suppliersByFirm.put(newSup.getFirm(), newSup);
	    } else {
		code = 2;
	    }
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
	    code = 1;
        } finally {
            preparedStatement.close();
        }
        return new FunctionResult<Supplier>(code, newSup);
    }

    private void loadBasicIngredients() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_INGR());
        while (rs.next()) {
	    BasicIngredient b = BasicIngredient.loadWithValues(
		    rs.getInt("id"),
                    suppliersById.get(rs.getInt("firmaid")),
                    rs.getString("merk"),
                    rs.getString("verpakking"),
                    rs.getDouble("prijsPerVerpakking"),
                    rs.getDouble("gewichtPerVerpakking"),
                    rs.getDouble("verliespercentage"),
                    rs.getDouble("BTW"),
                    rs.getString("naam"),
                    rs.getString("datum"),
                    rs.getString("opmerking"));
            basicIngredientsById.put(b.getPrimaryKeyValue(), b);
            basicIngredientsByName.put(b.getName(), b);
        }

//        System.out.println("Database driver:: loaded " + basicIngredientsById.size() + " basic ingredients!");
	MyLogger.log("Database driver:: loaded " + basicIngredientsById.size() + " basic ingredients!", MyLogger.LOW);
    }

    public FunctionResult<BasicIngredient> addBasicIngredient(BasicIngredientModel ingredient) throws SQLException {
	int code = 0;
	BasicIngredient newBI = null;
        String insertTableSQL = "INSERT INTO ingredients"
                + "(firmaid, naam, merk, verpakking, prijsPerVerpakking, gewichtPerVerpakking, verliespercentage, BTW, datum, opmerking) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
        try {
            preparedStatement.setInt(1, ingredient.getSupplier().getPrimaryKeyValue());
            preparedStatement.setString(2, ingredient.getName());
            preparedStatement.setString(3, ingredient.getBrand());
            preparedStatement.setString(4, ingredient.getPackaging());
            preparedStatement.setDouble(5, ingredient.getPricePerUnit());
            preparedStatement.setDouble(6, ingredient.getWeightPerUnit());
            preparedStatement.setDouble(7, ingredient.getLossPercent());
            preparedStatement.setDouble(8, ingredient.getTaxes());
            preparedStatement.setString(9, ingredient.getDate());
            preparedStatement.setString(10, ingredient.getNotes());
            preparedStatement.executeUpdate();
	    
	    ResultSet rs = statement.executeQuery("SELECT id FROM "+Configuration.center().getDB_TABLE_INGR()+" WHERE naam=\""+ingredient.getName()+"\"");
	    if (rs.next()) {
		newBI = BasicIngredient.createFromModel(rs.getInt("id"), ingredient);
		basicIngredientsById.put(newBI.getPrimaryKeyValue(), newBI);
		basicIngredientsByName.put(newBI.getName(), newBI);
	    } else {
		code = 2;
	    }
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
	    code = 1;
        } finally {
            preparedStatement.close();
        }
        return new FunctionResult<BasicIngredient>(code, newBI);
    }

    private void loadRecipes() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_REC());
        while (rs.next()) {
	    Recipe r = Recipe.createStub(
		    rs.getInt("id"),
                    rs.getString("naam"),
                    rs.getString("datum"),
                    rs.getString("bereiding"),
                    rs.getDouble("nettogewicht"));
            recipesById.put(r.getPrimaryKeyValue(), r);
            recipesByName.put(r.getName(), r);
        }
        // also copy all linked ingredients and recipes
        rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_REC_INGR());
        int ingrLinks = 0;
        while (rs.next()) {
            int recipeName = rs.getInt("receptid");
            int ingredientName = rs.getInt("ingredientid");
            int rank = rs.getInt("rang");
            double quantity = rs.getDouble("quantiteit");
            recipesById.get(recipeName).addIngredient(basicIngredientsById.get(ingredientName), rank, quantity);
            ingrLinks++;
        }
        rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_REC_REC());
        int recLinks = 0;
        while (rs.next()) {
            int recipeName = rs.getInt("receptid");
            int subrecipeName = rs.getInt("deelreceptid");
            int rank = rs.getInt("rang");
            double quantity = rs.getDouble("quantiteit");
            recipesById.get(recipeName).addIngredient(recipesById.get(subrecipeName), rank, quantity);
            recLinks++;
        }

//        System.out.println("Database driver:: loaded " + recipesById.size() + " recipes (linked " + ingrLinks + " ingredients and " + recLinks + " recipes)!");
	MyLogger.log("Database driver:: loaded " + recipesById.size() + " recipes (linked " + ingrLinks + " ingredients and " + recLinks + " recipes)!", MyLogger.LOW);
    }

    public FunctionResult<Recipe> addRecipe(RecipeModel recipe) throws SQLException {
	boolean autoComm = connection.getAutoCommit();
	int code = 0;
	Recipe newRec = null;
        PreparedStatement stmt = null;
        try {
            connection.setAutoCommit(false);
            String insertrecipe = "INSERT INTO recipes"
                    + "(naam, bereiding, datum, nettogewicht) VALUES"
                    + "(?,?,?,?)";
            String insertrecrec = "INSERT INTO recipesrecipes"
                    + "(receptid, deelreceptid, rang, quantiteit) VALUES"
                    + "(?,?,?,?)";
            String insertrecing = "INSERT INTO recipesingredients"
                    + "(receptid, ingredientid, rang, quantiteit) VALUES"
                    + "(?,?,?,?)";
            stmt = connection.prepareStatement(insertrecipe);
            stmt.setString(1, recipe.getName());
            stmt.setString(2, recipe.getPreparation());
            stmt.setString(3, recipe.getDate());
            stmt.setDouble(4, recipe.getNetWeight());
            stmt.executeUpdate();
//	    connection.commit();
	    
	    ResultSet rs = statement.executeQuery("SELECT id FROM "+Configuration.center().getDB_TABLE_REC()+" WHERE naam=\""+recipe.getName()+"\"");
	    if (rs.next()) {
		newRec = Recipe.createFromModel(rs.getInt("id"), recipe);
		recipesById.put(newRec.getPrimaryKeyValue(), newRec);
		recipesByName.put(newRec.getName(), newRec);
	    } else {
		code = 2;
	    }
	    
            Map<Integer, Component> ings = recipe.getComponents();
            for (int i : ings.keySet()) {
                Component comp = ings.get(i);
                if (comp.getIngredient().isBasicIngredient()) {
                    stmt = connection.prepareStatement(insertrecing);
                } else {
                    stmt = connection.prepareStatement(insertrecrec);
                }
                stmt.setInt(1, newRec.getPrimaryKeyValue());
                stmt.setInt(2, comp.getIngredient().getPrimaryKeyValue());
                stmt.setInt(3, comp.getRank());
                stmt.setDouble(4, comp.getQuantity());
                stmt.executeUpdate();
            }
            connection.commit();
	    
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            connection.rollback();
	    code = 1;
        } finally {
	    connection.setAutoCommit(autoComm);
            if (stmt != null) {
                stmt.close();
            }
        }
        return new FunctionResult<Recipe>(code, newRec);
    }

    public boolean updateRecipe(Recipe recipe) throws SQLException {
        boolean autoComm = connection.getAutoCommit();
	boolean flag = true;
        String insertrecrec = "INSERT INTO recipesrecipes"
                + "(receptid, deelreceptid, rang, quantiteit) VALUES"
                + "(?,?,?,?)";
        String insertrecing = "INSERT INTO recipesingredients"
                + "(receptid, ingredientid, rang, quantiteit) VALUES"
                + "(?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update recipes set naam=?, bereiding=?, datum=?, nettogewicht=? where id=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, recipe.getName());
            stmt.setString(2, recipe.getPreparation());
            stmt.setString(3, recipe.getDate());
            stmt.setDouble(4, recipe.getNetWeight());
            stmt.setInt(5, recipe.getPrimaryKeyValue());
            stmt.executeUpdate();
            Statement st = connection.createStatement();
            sql = "DELETE FROM recipesrecipes WHERE receptid = " + recipe.getPrimaryKeyValue();
            st.executeUpdate(sql);
            sql = "DELETE FROM recipesingredients WHERE receptid = " + recipe.getPrimaryKeyValue();
            st.executeUpdate(sql);
            Map<Integer, Component> ings = recipe.getComponents();

            for (int i : ings.keySet()) {
                Component comp = ings.get(i);
                if (comp.getIngredient().isBasicIngredient()) {
                    stmt = connection.prepareStatement(insertrecing);
                } else {
                    stmt = connection.prepareStatement(insertrecrec);
                }
                stmt.setInt(1, recipe.getPrimaryKeyValue());
                stmt.setInt(2, comp.getIngredient().getPrimaryKeyValue());
                stmt.setInt(3, comp.getRank());
                stmt.setDouble(4, comp.getQuantity());
                stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        } finally {
	    connection.setAutoCommit(autoComm);
            if (stmt != null) {
                stmt.close();
            }
        }
        return flag;
    }

    private void loadMunicipales() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_MUNI());
        while (rs.next()) {
            int code = rs.getInt("code");
            String name = StringTools.capitalizeEach(rs.getString("name"));
            municipales.put(name, code);
        }
    }
    
    public Map<Integer, Client> getClients() {
	return clientsById;
    }

    public Map<Integer, Supplier> getSuppliers() {
        return suppliersById;
    }
    
    public Map<String, Supplier> getSuppliersAlphabetically() {
	return suppliersByFirm;
    }

    public Map<Integer, BasicIngredient> getBasicIngredients() {
        return basicIngredientsById;
    }
    
    public Map<String, BasicIngredient> getBasicIngredientsAlphabetically() {
        return basicIngredientsByName;
    }

    public Map<Integer, Recipe> getRecipes() {
        return recipesById;
    }
    
    public Map<String, Recipe> getRecipesAlphabetically() {
        return recipesByName;
    }

    public Map<String, Integer> getMunicipales() {
        return municipales;
    }

    public List<Ingredient> getIngredients() {
	Map<String, Ingredient> sorted = new TreeMap<String, Ingredient>(String.CASE_INSENSITIVE_ORDER);
	sorted.putAll(basicIngredientsByName);
	sorted.putAll(recipesByName);
        ArrayList<Ingredient> me = new ArrayList<Ingredient>();
	me.addAll(sorted.values());
        return me;
    }

    /**
     * Tries to send an update command to the database to update the record with
     * the specified id of the specified table to set the new values. Values is
     * expected to be of the format: "column_name = value, ...".
     *
     * @param table
     * @param primaryKey
     * @param primaryKeyValue
     * @param values
     * @return
     */
    public boolean executeUpdate(String table, String primaryKey, int primaryKeyValue, String values) {
        try {
            statement.executeUpdate("UPDATE " + table + " SET " + values + " WHERE " + primaryKey + " = \"" + primaryKeyValue + "\"");
            System.out.println("DatabaseDriver::Executed update:\n"
                    + "UPDATE " + table + " SET " + values + " WHERE " + primaryKey + " = \"" + primaryKeyValue + "\" \nSUCCES!");
            return true;
        } catch (Exception e) {
            // do logging
            System.err.println("DatabaseDriver::Update command: \n"
                    + "UPDATE " + table + " SET " + values + " WHERE " + primaryKey + " = \"" + primaryKeyValue + "\" \nFAILED:\n" + e.getMessage());

            return false;
        }
    }
    
    /**
     * TODO: MAKE FAILSAFE so two calls can't generate the same id!
     * 
     * 
     * @param table
     * @return
     * @throws Exception 
     */
    public int generateIdForTable(String table) throws SQLException{
	if (table.equalsIgnoreCase(Configuration.center().getDB_TABLE_SUP())) {
	    return Collections.max(suppliersById.keySet())+1;
	} else if (table.equalsIgnoreCase(Configuration.center().getDB_TABLE_REC())) {
	    return Collections.max(recipesById.keySet())+1;
	} else if (table.equalsIgnoreCase(Configuration.center().getDB_TABLE_INGR())) {
	    return Collections.max(basicIngredientsById.keySet())+1;
	} else {
	    throw new SQLException("DatabaseDriver::Table '"+table+"' is not a database table and a valid PK could not be generated!");
	}
    }

    /**
     * Tries to close the active database connection.
     *
     * @throws SQLException when the connection can't be closed.
     */
    public void shutdown() throws SQLException {
        connection.close();
    }
}
