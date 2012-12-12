
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.extra.Component;
import database.extra.Contact;
import database.extra.Ingredient;
import database.models.ArticleModel;
import database.models.BasicIngredientModel;
import database.models.ClientModel;
import database.models.RecipeModel;
import database.models.SupplierModel;
import database.tables.Article;
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
    private Map<Integer, Article> articlesById;
    private Map<String, Supplier> suppliersByFirm;
    private Map<String, BasicIngredient> basicIngredientsByName;
    private Map<String, Recipe> recipesByName;
    private Map<String, Client> clientsByName;
    private Map<String, Integer> municipales;
    private Map<String, Article> articlesByName;

    private Database() {
        try {
            suppliersById = new TreeMap<Integer, Supplier>();
            basicIngredientsById = new TreeMap<Integer, BasicIngredient>();
            recipesById = new TreeMap<Integer, Recipe>();
            clientsById = new TreeMap<Integer, Client>();
            articlesById = new TreeMap<Integer, Article>();
	    
	    suppliersByFirm = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);
	    basicIngredientsByName = new TreeMap<String, BasicIngredient>(String.CASE_INSENSITIVE_ORDER);
	    recipesByName = new TreeMap<String, Recipe>(String.CASE_INSENSITIVE_ORDER);
	    clientsByName = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);
	    articlesByName = new TreeMap<String, Article>(String.CASE_INSENSITIVE_ORDER);
	    
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
	    loadArticles();

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
    
    private void loadClients() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_CL());
        while (rs.next()) {
	    Client s = Client.loadWithValues(
		    rs.getInt("id"),
		    rs.getString("naam"),
                    rs.getString("contactpersoon"),
                    rs.getString("adres"),
                    rs.getInt("postcode"),
		    rs.getString("gemeente"),
                    rs.getString("tel"),
                    rs.getString("tel2"),
                    rs.getString("gsm"),
                    rs.getString("fax"),
                    rs.getString("email"),
                    rs.getString("btwnummer"),
                    rs.getString("prijscode"),
                    rs.getString("opmerking"));
            clientsById.put(s.getPrimaryKeyValue(), s);
            clientsByName.put(s.getName(), s);
        }

//        System.out.println("Database driver:: loaded " + suppliersById.size() + " suppliers!");
	MyLogger.log("Database driver:: loaded " + suppliersById.size() + " suppliers!", MyLogger.LOW);
    }

    private void loadSuppliers() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_SUP());
        while (rs.next()) {
	    Supplier s = Supplier.loadWithValues(
		    rs.getInt("id"),
		    rs.getString("firma"),
                    rs.getString("contactpersoon"),
                    rs.getString("adres"),
                    rs.getInt("postcode"),
		    rs.getString("gemeente"),
                    rs.getString("tel"),
                    rs.getString("tel2"),
                    rs.getString("gsm"),
                    rs.getString("fax"),
                    rs.getString("email"),
                    rs.getString("btwnr"),
                    rs.getString("prijscode"),
                    rs.getString("opmerking"),
                    rs.getBoolean("verwijderd"));
            suppliersById.put(s.getPrimaryKeyValue(), s);
            suppliersByFirm.put(s.getName(), s);
        }

//        System.out.println("Database driver:: loaded " + suppliersById.size() + " suppliers!");
	MyLogger.log("Database driver:: loaded " + suppliersById.size() + " suppliers!", MyLogger.LOW);
    }

    public FunctionResult<Supplier> addSupplier(SupplierModel sup) throws SQLException {
        int code = 0;
	Supplier newSup = null;
        String insertTableSQL = "INSERT INTO suppliers"
                + "(firma, adres, gemeente, tel, tel2, gsm, email, opmerking, contactpersoon, fax, postcode, verwijderd, btwnr, prijscode) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
	    preparedStatement.setString(13, sup.getTaxnumber());
            preparedStatement.setString(14, sup.getPricecode());
            preparedStatement.executeUpdate();
	    
	    ResultSet rs = statement.executeQuery("SELECT id FROM "+Configuration.center().getDB_TABLE_SUP()+" WHERE firma=\""+sup.getFirm()+"\"");
	    if (rs.next()) {
		newSup = Supplier.createFromModel(rs.getInt("id"), sup);
		suppliersById.put(newSup.getPrimaryKeyValue(), newSup);
		suppliersByFirm.put(newSup.getName(), newSup);
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
    
    public FunctionResult<Client> addClient(ClientModel cl) throws SQLException {
        int code = 0;
	Client newCl = null;
        String insertTableSQL = "INSERT INTO klanten"
                + "(naam, adres, gemeente, tel, tel2, gsm, email, opmerking, contactpersoon, fax, postcode, verwijderd, btwnummer, prijscode) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
        try {
            preparedStatement.setString(1, cl.getName());
            preparedStatement.setString(2, cl.getAddress());
            preparedStatement.setString(3, cl.getMunicipality());
            preparedStatement.setString(4, cl.getTelephone());
            preparedStatement.setString(5, cl.getTelephone2());
            preparedStatement.setString(6, cl.getCellphone());
            preparedStatement.setString(7, cl.getEmail());
            preparedStatement.setString(8, cl.getNotes());
            preparedStatement.setString(9, cl.getContact());
            preparedStatement.setString(10, cl.getFax());
            preparedStatement.setInt(11, cl.getZipcode());
            preparedStatement.setInt(12, 0);
	    preparedStatement.setString(13, cl.getTaxnumber());
            preparedStatement.setString(14, cl.getPricecode());
            preparedStatement.executeUpdate();
	    
	    ResultSet rs = statement.executeQuery("SELECT id FROM "+Configuration.center().getDB_TABLE_CL()+" WHERE naam=\""+cl.getName()+"\"");
	    if (rs.next()) {
		newCl = Client.createFromModel(rs.getInt("id"), cl);
		clientsById.put(newCl.getPrimaryKeyValue(), newCl);
		clientsByName.put(newCl.getName(), newCl);
	    } else {
		code = 2;
	    }
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
	    code = 1;
        } finally {
            preparedStatement.close();
        }
        return new FunctionResult<Client>(code, newCl);
    }
    
    public FunctionResult<Article> addArticle(ArticleModel art) throws SQLException {
        int code = 0;
	Article newArt = null;
        String insertTableSQL = "INSERT INTO articles"
                + "(name, code, pricea, priceb, unit, taxes) VALUES"
                + "(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
        try {
            preparedStatement.setString(1, art.getName());
            preparedStatement.setString(2, art.getCode());
            preparedStatement.setDouble(3, art.getPriceA());
            preparedStatement.setDouble(4, art.getPriceB());
	    preparedStatement.setString(5, art.getUnit());
            preparedStatement.setDouble(6, art.getTaxes());
            preparedStatement.executeUpdate();
	    
	    ResultSet rs = statement.executeQuery("SELECT id FROM "+Configuration.center().getDB_TABLE_ART()+" WHERE name=\""+art.getName()+"\"");
	    if (rs.next()) {
		newArt = Article.createFromModel(rs.getInt("id"), art);
		articlesById.put(newArt.getPrimaryKeyValue(), newArt);
		articlesByName.put(newArt.getName(), newArt);
	    } else {
		code = 2;
	    }
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
	    code = 1;
        } finally {
            preparedStatement.close();
        }
        return new FunctionResult<Article>(code, newArt);
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
    
    private void loadArticles() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_ART());
        while (rs.next()) {
	    Article a = Article.loadWithValues(
		    rs.getInt("id"),
                    rs.getString("code"),
		    rs.getString("name"),
                    rs.getDouble("pricea"),
                    rs.getDouble("priceb"),
                    rs.getString("unit"),
		    rs.getDouble("taxes"));
            articlesById.put(a.getPrimaryKeyValue(), a);
            articlesByName.put(a.getName(), a);
        }

//        System.out.println("Database driver:: loaded " + suppliersById.size() + " suppliers!");
	MyLogger.log("Database driver:: loaded " + articlesById.size() + " articles!", MyLogger.LOW);
    }
    
    public Map<Integer, Client> getClients() {
	return clientsById;
    }
    
    public Map<String, Client> getClientsAlphabetically() {
	return clientsByName;
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
    
    public Map<Integer, Article> getArticles() {
	return articlesById;
    }
    
    public Map<String, Article> getArticlesAlphabetically() {
	return articlesByName;
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
    
    public Map<String, Contact> getContactsAlphabetically() {
	Map<String, Contact> contacts = new TreeMap<String, Contact>(String.CASE_INSENSITIVE_ORDER);
	contacts.putAll(suppliersByFirm);
	return contacts;
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
