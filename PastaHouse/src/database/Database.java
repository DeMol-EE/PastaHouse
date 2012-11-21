
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Configuration;
import utilities.StringTools;

/**
 *
 * @author Warkst
 */
public class Database {

    private static Database driver;
    private Connection connection;
    private Statement statement;
    private Map<Integer, Supplier> suppliers;
    private Map<Integer, BasicIngredient> basicIngredients;
    private Map<Integer, Recipe> recipes;
    private Map<String, Integer> municipales;

    private Database() {
        try {
            suppliers = new TreeMap<Integer, Supplier>();
            basicIngredients = new TreeMap<Integer, BasicIngredient>();
            recipes = new TreeMap<Integer, Recipe>();
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
            suppliers.put(rs.getInt("id"),
                    Supplier.loadWithValues(rs.getString("firma"),
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
                    rs.getBoolean("verwijderd")));
        }

        System.out.println("Database driver:: loaded " + suppliers.size() + " suppliers!");
    }

    public boolean addSupplier(Supplier sup) throws SQLException {
        boolean flag = true;
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
        } catch (SQLException ex) {
            Logger.getLogger(Supplier.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        } finally {
            preparedStatement.close();
        }
        return flag;
    }

    private void loadBasicIngredients() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_INGR());
        while (rs.next()) {
            basicIngredients.put(rs.getInt("id"),
                    BasicIngredient.loadWithValues(
                    suppliers.get(
                    rs.getString("firma")),
                    rs.getString("merk"),
                    rs.getString("verpakking"),
                    rs.getDouble("prijsPerVerpakking"),
                    rs.getDouble("gewichtPerVerpakking"),
                    rs.getDouble("verliespercentage"),
                    rs.getDouble("BTW"),
                    rs.getString("naam"),
                    rs.getString("datum"),
                    rs.getString("opmerking"),
                    rs.getInt("id")));
        }

        System.out.println("Database driver:: loaded " + basicIngredients.size() + " basic ingredients!");
    }

    public boolean addIngredient(BasicIngredient ingredient) {
        if (executeInsert(Configuration.center().getDB_TABLE_INGR(),
                "NULL, "
                + "\"" + ingredient.getSupplier().getFirm() + "\", "
                + (ingredient.getName().length() > 0 ? "\"" + ingredient.getName() + "\"" : "NULL") + ", "
                + (ingredient.getBrand().length() > 0 ? "\"" + ingredient.getBrand() + "\"" : "NULL") + ", "
                + (ingredient.getPackaging().length() > 0 ? "\"" + ingredient.getPackaging() + "\"" : "NULL") + ", "
                + (ingredient.getPricePerUnit() > 0 ? "\"" + ingredient.getPricePerUnit() + "\"" : "NULL") + ", "
                + (ingredient.getWeightPerUnit() > 0 ? "\"" + ingredient.getWeightPerUnit() + "\"" : "NULL") + ", "
                + (ingredient.getLossPercent() > 0 ? "\"" + ingredient.getLossPercent() + "\"" : "NULL") + ", "
                + (ingredient.getTaxes() > 0 ? "\"" + ingredient.getTaxes() + "\"" : "NULL") + ", "
                + (ingredient.getDate().length() > 0 ? "\"" + ingredient.getDate() + "\"" : "NULL") + ", "
                + (ingredient.getNotes().length() > 0 ? "\"" + ingredient.getNotes() + "\"" : "NULL"))) {
            basicIngredients.put(ingredient.getPrimaryKeyValue(), ingredient);
            return true;
        } else {
            return false;
        }
    }

    private void loadRecipes() throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_REC());
        while (rs.next()) {
            recipes.put(rs.getInt("id"),
                    Recipe.createStub(
                    rs.getString("naam"),
                    rs.getString("datum"),
                    rs.getString("bereiding"),
                    rs.getDouble("nettogewicht")));
        }
        // also copy all linked ingredients and recipes
        rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_REC_INGR());
        int ingrLinks = 0;
        while (rs.next()) {
            String recipeName = rs.getString("receptnaam").toLowerCase();
            String ingredientName = rs.getString("ingredientnaam").toLowerCase();
            int rank = rs.getInt("rang");
            double quantity = rs.getDouble("quantiteit");
            recipes.get(recipeName).addIngredient(basicIngredients.get(ingredientName), rank, quantity, true);
            ingrLinks++;
        }
        rs = statement.executeQuery("SELECT * FROM " + Configuration.center().getDB_TABLE_REC_REC());
        int recLinks = 0;
        while (rs.next()) {
            String recipeName = rs.getString("receptnaam").toLowerCase();
            String subrecipeName = rs.getString("deelreceptnaam").toLowerCase();
            int rank = rs.getInt("rang");
            double quantity = rs.getDouble("quantiteit");
            recipes.get(recipeName).addIngredient(recipes.get(subrecipeName), rank, quantity, false);
            recLinks++;
        }

        System.out.println("Database driver:: loaded " + recipes.size() + " recipes (linked " + ingrLinks + " ingredients and " + recLinks + " recipes)!");
    }

    public boolean addRecipe(Recipe recipe) throws SQLException {
        boolean flag = true;
        PreparedStatement stmt = null;
        try {
            connection.setAutoCommit(false);
            String insertrecipe = "INSERT INTO recipes"
                    + "(naam, bereiding, datum, nettogewicht) VALUES"
                    + "(?,?,?,?)";
            String insertrecrec = "INSERT INTO recipesrecipes"
                    + "(receptnaam, deelreceptnaam, rang, quantiteit) VALUES"
                    + "(?,?,?,?)";
            String insertrecing = "INSERT INTO recipesingredients"
                    + "(receptnaam, ingredientnaam, rang, quantiteit) VALUES"
                    + "(?,?,?,?)";
            stmt = connection.prepareStatement(insertrecipe);
            stmt.setString(1, recipe.getName());
            stmt.setString(2, recipe.getPreparation());
            stmt.setString(3, recipe.getDate());
            stmt.setDouble(4, recipe.getNetWeight());
            stmt.executeUpdate();
            Map<Integer, Component> ings = recipe.getIngredients();
            for (int i : ings.keySet()) {
                Component comp = ings.get(i);
                if (comp.isBasicIngredient()) {
                    stmt = connection.prepareStatement(insertrecing);
                } else {
                    stmt = connection.prepareStatement(insertrecrec);
                }
                stmt.setString(0, recipe.getName());
                stmt.setString(1, comp.getIngredient().getName());
                stmt.setInt(2, comp.getRank());
                stmt.setDouble(3, comp.getQuantity());
                stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            connection.rollback();
            flag = false;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        return flag;
    }

    public boolean updateRecipe(Recipe recipe) throws SQLException {
        boolean flag = true;
        String insertrecrec = "INSERT INTO recipesrecipes"
                + "(receptnaam, deelreceptnaam, rang, quantiteit) VALUES"
                + "(?,?,?,?)";
        String insertrecing = "INSERT INTO recipesingredients"
                + "(receptnaam, ingredientnaam, rang, quantiteit) VALUES"
                + "(?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update recipes set naam=?, bereiding=?, datum=?, nettogewicht=? where naam=?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, recipe.getName());
            stmt.setString(2, recipe.getPreparation());
            stmt.setString(3, recipe.getDate());
            stmt.setDouble(4, recipe.getNetWeight());
            stmt.setString(5, recipe.getName());
            stmt.executeUpdate();
            Statement st = connection.createStatement();
            sql = "DELETE FROM recipesrecipes WHERE receptnaam = " + recipe.getName();
            st.executeUpdate(sql);
            sql = "DELETE FROM recipesingredients WHERE receptnaam = " + recipe.getName();
            st.executeUpdate(sql);
            Map<Integer, Component> ings = recipe.getIngredients();

            for (int i : ings.keySet()) {
                Component comp = ings.get(i);
                if (comp.isBasicIngredient()) {
                    stmt = connection.prepareStatement(insertrecing);
                } else {
                    stmt = connection.prepareStatement(insertrecrec);
                }
                stmt.setString(0, recipe.getName());
                stmt.setString(1, comp.getIngredient().getName());
                stmt.setInt(2, comp.getRank());
                stmt.setDouble(3, comp.getQuantity());
                stmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            flag = false;
        } finally {
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

    public Map<Integer, Supplier> getSuppliers() {
        return suppliers;
    }

    public Map<Integer, BasicIngredient> getBasicIngredients() {
        return basicIngredients;
    }

    public Map<Integer, Recipe> getRecipes() {
        return recipes;
    }

    public Map<String, Integer> getMunicipales() {
        return municipales;
    }

    public List<Ingredient> getIngredients() {
        ArrayList<Ingredient> me = new ArrayList<Ingredient>();
        me.addAll(basicIngredients.values());
        me.addAll(recipes.values());
        return me;
    }

    public void executeStatement(String s) {
    }

    /**
     *
     * @param table
     * @param values
     * @throws SQLException
     */
    public boolean executeInsert(String table, String values) {
        try {
            statement.executeUpdate("INSERT INTO " + table + " VALUES (" + values + ")");
            System.out.println("DatabaseDriver::Executed command:\n"
                    + "INSERT INTO " + table + " VALUES (" + values + ")\n"
                    + "SUCCES!\n");
            return true;
        } catch (Exception e) {
            // do logging
            // show error elsewhere (hence the return false!)
            System.err.println("DatabaseDriver::Executed command:\n"
                    + "INSERT INTO " + table + " VALUES (" + values + ")\n"
                    + "FAILED:\n" + e.getMessage());

            return false;
        }
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
    public boolean executeUpdate(String table, String primaryKey, String primaryKeyValue, String values) {
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

    public boolean executeDelete(String table, int id) {
        return false;
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
