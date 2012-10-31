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
    
    private Map<String, BasicIngredient> basicIngredients;
    private Map<String, Recipe> recipes;
    
    private Database() {
	try {
	    basicIngredients = new TreeMap<String, BasicIngredient>();
	    recipes = new TreeMap<String, Recipe>();
	    
	    // connect to db
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection(Configuration.center().getDB_URL());
	    statement = connection.createStatement();
	    
	    //first time run: set up the database (create if not exists...)
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_INGR()+"("
		    + "id INTEGER PRIMARY KEY, "
		    + "name TEXT NOT NULL ON CONFLICT FAIL, "
		    + "price REAL NOT NULL ON CONFLICT FAIL);");
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "name TEXT NOT NULL ON CONFLICT FAIL, "
		    + "preparation TEXT, "
		    + "loss INTEGER CHECK (loss BETWEEN 0 AND 100));");
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC_INGR()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "parentid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, "
		    + "childid INTEGER REFERENCES ingredients (id) ON DELETE RESTRICT, "
		    + "rank INTEGER NOT NULL ON CONFLICT FAIL,"
		    + "quantity REAL NOT NULL ON CONFLICT FAIL);");
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC_REC()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "parentid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, "
		    + "childid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, "
		    + "rank INTEGER NOT NULL ON CONFLICT FAIL,"
		    + "quantity REAL NOT NULL ON CONFLICT FAIL);");
	    
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
    
    public Map<String, BasicIngredient> loadBasicIngredients() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_INGR());
	while (rs.next()) {	    
	    basicIngredients.put(rs.getString("name"), BasicIngredient.loadWithValues(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
	}
	
	return basicIngredients;
    }
    
    public ArrayList<BasicIngredient> loadRecipes() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_REC());
	// also load all recipes and ingredients linked to the recipe
	// ...
	while (rs.next()) {	    
	    
	}
	
	return null;
    }
    
    public Map<String, Component> loadIngredients(){
	TreeMap<String, Component> ingredients = new TreeMap<String, Component>();
	
	for (Map.Entry<String, BasicIngredient> entry : basicIngredients.entrySet()) {
	    if(!ingredients.containsKey(entry.getKey())){
		ingredients.put(entry.getKey(), entry.getValue());
	    } else {
		System.err.println("Duplicate entry found in components table for ingredient with name \""+entry.getKey()+"\" (did not overwrite already present value)");
	    }
	}
	
	for (Map.Entry<String, Recipe> entry : recipes.entrySet()) {
	    if(!ingredients.containsKey(entry.getKey())){
		ingredients.put(entry.getKey(), entry.getValue());
	    } else {
		System.err.println("Duplicate entry found in components table for recipe with name \""+entry.getKey()+"\" (did not overwrite already present value)");
	    }
	}
	
	return ingredients;
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
