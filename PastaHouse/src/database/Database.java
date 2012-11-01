
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
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private Database() {
	try {
	    suppliers = new TreeMap<Integer, Supplier>();
	    basicIngredients = new TreeMap<Integer, BasicIngredient>();
	    recipes = new TreeMap<Integer, Recipe>();
	    
	    // connect to db
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection(Configuration.center().getDB_URL());
	    statement = connection.createStatement();
	     
	    // load data
	    loadSuppliers();
	    loadBasicIngredients();
	    loadRecipes();
	    
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
    
    public void loadSuppliers() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_SUP());
	while (rs.next()) {	    
	    suppliers.put(rs.getInt("id"), 
		    Supplier.loadWithValues(rs.getInt("id"), 
		    rs.getString("firma"), 
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
    
    public void loadBasicIngredients() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_INGR());
	while (rs.next()) {	    
	    basicIngredients.put(rs.getInt("id"), 
		    BasicIngredient.loadWithValues(
		    suppliers.get(rs.getInt("leverancierid")), 
		    rs.getString("merk"), 
		    rs.getString("verpakking"), 
		    rs.getDouble("prijsPerVerpakking"), 
		    rs.getDouble("gewichtPerVerpakking"), 
		    rs.getDouble("verliespercentage"), 
		    rs.getDouble("BTW"), 
		    rs.getInt("id"), 
		    rs.getString("naam"), 
		    rs.getString("datum")));
	}
	
	System.out.println("Database driver:: loaded "+basicIngredients.size()+" basic ingredients!");
    }
    
    public void loadRecipes() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_REC());
	while (rs.next()) {	    
	    recipes.put(rs.getInt("id"), 
		    Recipe.createStub(
		    rs.getInt("id"), 
		    rs.getString("naam"), 
		    rs.getString("datum"), 
		    rs.getString("bereiding"), 
		    rs.getDouble("nettogewicht")));
	}
	// also load all linked ingredients and recipes
	rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_REC_INGR());
	while(rs.next()){
	    int recipeId = rs.getInt("receptid");
	    int ingredientId = rs.getInt("ingredientid");
	    int rank = rs.getInt("rang");
	    double quantity = rs.getDouble("quantiteit");
	}
	
	System.out.println("Database driver:: loaded "+recipes.size()+" recipes!");
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
    
    public void executeStatement(String s){
	
    }
    
    /**
     * 
     * @param table
     * @param values
     * @throws SQLException 
     */
    public void executeInsert(String table, String values) throws SQLException{
	statement.executeUpdate("INSERT INTO "+table+" VALUES (null, "+values+")");
	System.out.println("DatabaseDriver::Executed insert of ("+values+") into "+table);
    }
    
    /**
     * Tries to send an update command to the database to update the record
     * with the specified id of the specified table to set the new values.
     * Values is expected to be of the format: "column_name = value, ...".
     * 
     * @param table The table of the record to be updated.
     * @param id The primary key of the record to be updated.
     * @param values The new set of values for the record.
     * @throws SQLException when the update statement fails to complete.
     */
    public void executeUpdate(String table, int id, String values) throws SQLException{
	statement.executeUpdate("UPDATE "+table+" SET "+values+" WHERE id = "+id);
	System.out.println("DatabaseDriver::Executed update of ("+values+") into "+table+" for id "+id);
    }
    
    public void executeDelete(String table, int id){
	
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
