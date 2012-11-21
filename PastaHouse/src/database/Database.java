
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Configuration;

/**
 *
 * @author Warkst
 */
public class Database {
    private static Database driver;
    private Connection connection;
    private Statement statement;
    
    private Map<String, Supplier> suppliers;
    private Map<String, BasicIngredient> basicIngredients;
    private Map<String, Recipe> recipes;
    private Map<String, Integer> municipales;
    
    private Database() {
	try {
	    suppliers = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);
	    basicIngredients = new TreeMap<String, BasicIngredient>(String.CASE_INSENSITIVE_ORDER);
	    recipes = new TreeMap<String, Recipe>(String.CASE_INSENSITIVE_ORDER);
	    municipales = new TreeMap<String, Integer>(String.CASE_INSENSITIVE_ORDER);
	    // connect to db
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection(Configuration.center().getDB_URL());
	    statement = connection.createStatement();
	     
	    // load data
	    loadSuppliers();
	    loadBasicIngredients();
	    loadRecipes();
            loadMunicipales();
	    
	} catch (Exception ex) {
	    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public static Database driver(){
	if(driver==null) {
	    driver = new Database();
	}
	return driver;
    }
    
    private void loadSuppliers() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_SUP());
	while (rs.next()) {	    
	    suppliers.put(rs.getString("firma").toLowerCase(),
		    Supplier.loadWithValues(rs.getString("firma"), 
		    rs.getString("adres"), 
		    rs.getString("gemeente"), 
		    rs.getString("tel"), 
		    rs.getString("gsm"), 
		    rs.getString("fax"), 
		    rs.getString("email"), 
		    rs.getString("opmerking"), 
		    rs.getString("contactpersoon"), 
		    rs.getBoolean("verwijderd")));
	}
	
	System.out.println("Database driver:: loaded "+suppliers.size()+" suppliers!");
    }
    
    public boolean addSupplier(Supplier sup){
        if (executeInsert(Configuration.center().getDB_TABLE_SUP(),
                "\""+ sup.getFirm())){
            return true;
        } else {
            return false;
        }
    }
    
    private void loadBasicIngredients() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_INGR());
	while (rs.next()) {	    
	    basicIngredients.put(rs.getString("naam").toLowerCase(), 
		    BasicIngredient.loadWithValues(
		    suppliers.get(rs.getString("firma")), 
		    rs.getString("merk"), 
		    rs.getString("verpakking"), 
		    rs.getDouble("prijsPerVerpakking"), 
		    rs.getDouble("gewichtPerVerpakking"), 
		    rs.getDouble("verliespercentage"), 
		    rs.getDouble("BTW"), 
		    rs.getString("naam"), 
		    rs.getString("datum"),
		    rs.getString("opmerking")));
	}
	
	System.out.println("Database driver:: loaded "+basicIngredients.size()+" basic ingredients!");
    }
    
    public boolean addIngredient(BasicIngredient ingredient){
	if (executeInsert(Configuration.center().getDB_TABLE_INGR(), 
		"\""+ ingredient.getSupplier().getFirm() +"\", "
		+(ingredient.getName().length()>0 ? "\""+ ingredient.getName() +"\"":"NULL")+", "
		+(ingredient.getBrand().length()>0 ?"\""+ingredient.getBrand() +"\"":"NULL")+", "
		+(ingredient.getPackaging().length()>0? "\""+ingredient.getPackaging() +"\"":"NULL")+", "
		+(ingredient.getPricePerUnit()>0? "\""+ingredient.getPricePerUnit() +"\"":"NULL")+", "
		+(ingredient.getWeightPerUnit()>0? "\""+ingredient.getWeightPerUnit() +"\"":"NULL")+", "
		+(ingredient.getLossPercent()>0? "\""+ingredient.getLossPercent() +"\"":"NULL")+", "
		+(ingredient.getTaxes()>0? "\""+ingredient.getTaxes() +"\"":"NULL")+", "
		+(ingredient.getDate().length()>0? "\""+ingredient.getDate() +"\"":"NULL")+", "
		+(ingredient.getNotes().length()>0 ? "\""+ingredient.getNotes() +"\"":"NULL"))) {
	    basicIngredients.put(ingredient.getPrimaryKeyValue(), ingredient);
	    return true;
	} else {
	    return false;
	}
    }
    
    private void loadRecipes() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_REC());
	while (rs.next()) {	    
	    recipes.put(rs.getString("naam").toLowerCase(), 
		    Recipe.createStub(
		    rs.getString("naam"), 
		    rs.getString("datum"), 
		    rs.getString("bereiding"), 
		    rs.getDouble("nettogewicht")));
	}
	// also load all linked ingredients and recipes
	rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_REC_INGR());
	int ingrLinks = 0;
	while(rs.next()){
	    String recipeName = rs.getString("receptnaam").toLowerCase();
	    String ingredientName = rs.getString("ingredientnaam").toLowerCase();
	    int rank = rs.getInt("rang");
	    double quantity = rs.getDouble("quantiteit");
	    recipes.get(recipeName).addIngredient(basicIngredients.get(ingredientName), rank, quantity, true);
	    ingrLinks++;
	}
	rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_REC_REC());
	int recLinks = 0;
	while(rs.next()){
	    String recipeName = rs.getString("receptnaam").toLowerCase();
	    String subrecipeName = rs.getString("deelreceptnaam").toLowerCase();
	    int rank = rs.getInt("rang");
	    double quantity = rs.getDouble("quantiteit");
	    recipes.get(recipeName).addIngredient(recipes.get(subrecipeName), rank, quantity, false);
	    recLinks++;
	}
	
	System.out.println("Database driver:: loaded "+recipes.size()+" recipes (linked "+ingrLinks+" ingredients and "+recLinks+" recipes)!");
    }
    
    public boolean addRecipe(Recipe recipe){
        return true;
    }
    
    private void loadMunicipales() throws SQLException{
        ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_MUNI());
        while(rs.next()){
            int code = rs.getInt("code");
            String name = rs.getString("name").toLowerCase();
            municipales.put(name, code);
        }
    }

    public Map<String, Supplier> getSuppliers() {
	return suppliers;
    }

    public Map<String, BasicIngredient> getBasicIngredients() {
	return basicIngredients;
    }

    public Map<String, Recipe> getRecipes() {
	return recipes;
    }

    public Map<String, Integer> getMunicipales() {
        return municipales;
    }
    
    public Map<String, Ingredient> getIngredients(){
	Map<String, Ingredient> me = new TreeMap<String, Ingredient>();
	me.putAll(basicIngredients);
	me.putAll(recipes);
	return me;
    }
    
    public void executeStatement(String s){
	
    }
    
    /**
     * 
     * @param table
     * @param values
     * @throws SQLException 
     */
    public boolean executeInsert(String table, String values){
	try{
	    statement.executeUpdate("INSERT INTO "+table+" VALUES ("+values+")");
	    System.out.println("DatabaseDriver::Executed command:\n"
		    + "INSERT INTO "+table+" VALUES ("+values+")\n"
		    + "SUCCES!\n");
	    return true;
	} catch (Exception e){
	    // do logging
	    // show error elsewhere (hence the return false!)
	    System.err.println("DatabaseDriver::Executed command:\n"
		    + "INSERT INTO "+table+" VALUES ("+values+")\n"
		    + "FAILED:\n"+e.getMessage());
	    
	    return false;
	}
    }
    
    /**
     * Tries to send an update command to the database to update the record
     * with the specified id of the specified table to set the new values.
     * Values is expected to be of the format: "column_name = value, ...".
     * 
     * @param table
     * @param primaryKey
     * @param primaryKeyValue
     * @param values
     * @return 
     */
    public boolean executeUpdate(String table, String primaryKey, String primaryKeyValue, String values){
	try{
	    statement.executeUpdate("UPDATE "+table+" SET "+values+" WHERE "+primaryKey+" = \""+primaryKeyValue+"\"");
	    System.out.println("DatabaseDriver::Executed update:\n"
		    + "UPDATE "+table+" SET "+values+" WHERE "+primaryKey+" = \""+primaryKeyValue+"\" \nSUCCES!");
	    return true;
	} catch(Exception e){
	    // do logging
	    System.err.println("DatabaseDriver::Update command: \n"
		    + "UPDATE "+table+" SET "+values+" WHERE "+primaryKey+" = \""+primaryKeyValue+"\" \nFAILED:\n"+e.getMessage());
	    
	    return false;
	}
    }
    
    public boolean executeDelete(String table, int id){
	return false;
    }
    
    /**
     * Tries to close the active database connection.
     * 
     * @throws SQLException when the connection can't be closed.
     */
    public void shutdown() throws SQLException{
	connection.close();
    }
}
