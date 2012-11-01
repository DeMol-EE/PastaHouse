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
import java.util.AbstractMap;
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
	    
	    //first time run: set up the database (create if not exists...)
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_SUP()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "firma TEXT NOT NULL ON CONFLICT FAIL, "
		    + "adres TEXT, "
		    + "gemeente TEXT, "
		    + "tel TEXT, "
		    + "gsm TEXT, "
		    + "email TEXT, "
		    + "opmerking TEXT, "
		    + "contactpersoon TEXT, "
		    + "verwijderd INTEGER DEFAULT 0);");
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_INGR()+"("
		    + "id INTEGER PRIMARY KEY, "
		    + "leverancierid INTEGER REFERENCES "+Configuration.center().getDB_TABLE_SUP()+ " (id) ON DELETE NO ACTION, "
		    + "naam TEXT NOT NULL ON CONFLICT FAIL, "
		    + "merk TEXT, "
		    + "verpakking TEXT, "
		    + "prijsPerVerpakking REAL NOT NULL ON CONFLICT FAIL, "
		    + "gewichtPerVerpakking REAL NOT NULL ON CONFLICT FAIL, "
		    + "verliespercentage REAL DEFAULT 0, "
		    + "BTW REAL DEFAULT 6, "
		    + "datum TEXT);");
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "naam TEXT NOT NULL ON CONFLICT FAIL, "
		    + "bereiding TEXT, "
		    + "datum TEXT, "
		    + "nettogewicht REAL NOT NULL ON CONFLICT FAIL);");
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC_INGR()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "receptid INTEGER REFERENCES recipes (id) ON DELETE CASCADE, "
		    + "ingredientid INTEGER REFERENCES ingredients (id) ON DELETE RESTRICT, "
		    + "rang INTEGER NOT NULL ON CONFLICT FAIL,"
		    + "quantiteit REAL NOT NULL ON CONFLICT FAIL);");
	    statement.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC_REC()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "receptid INTEGER REFERENCES recipes (id) ON DELETE CASCADE, "
		    + "deelreceptid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, "
		    + "rang INTEGER NOT NULL ON CONFLICT FAIL,"
		    + "quantiteit REAL NOT NULL ON CONFLICT FAIL);");
	    
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
    
    public Map<Integer, Supplier> loadSuppliers() throws SQLException{
	ResultSet rs = statement.executeQuery("SELECT * FROM "+Configuration.center().getDB_TABLE_SUP());
	while (rs.next()) {	    
	    suppliers.put(rs.getInt("id"), 
		    Supplier.loadWithValues(rs.getInt("id"), rs.getString("firma"), rs.getString("adres")));
	}
	
	System.out.println(suppliers);
	
	return suppliers;
    }
    
    public Map<Integer, BasicIngredient> loadBasicIngredients() throws SQLException{
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
