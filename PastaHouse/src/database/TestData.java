/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Warkst
 */
public class TestData {
    public static void main(String[] args) throws Exception {
	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:pastahouse.db");
        Statement stat = conn.createStatement();
        
	// reset db
	stat.executeUpdate("drop table if exists ingredients;");
        stat.executeUpdate("drop table if exists recipes;");
        stat.executeUpdate("drop table if exists recipesrecipes;");
        stat.executeUpdate("drop table if exists recipesingredients;");
	
	// create new and fresh tables
        stat.executeUpdate("create table ingredients (id INTEGER PRIMARY KEY, name TEXT NOT NULL ON CONFLICT FAIL, price REAL NOT NULL ON CONFLICT FAIL);");
        stat.executeUpdate("create table recipes (id INTEGER PRIMARY KEY, name TEXT NOT NULL ON CONFLICT FAIL, preparation TEXT, loss INTEGER CHECK (loss BETWEEN 0 AND 100));");
        stat.executeUpdate("create table recipesrecipes (id INTEGER PRIMARY KEY, parentid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, childid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, quantity REAL NOT NULL ON CONFLICT FAIL);");
        stat.executeUpdate("create table recipesingredients (id INTEGER PRIMARY KEY, parentid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, childid INTEGER REFERENCES ingredients (id) ON DELETE RESTRICT, quantity REAL NOT NULL ON CONFLICT FAIL);");

	
	//add test data
	PreparedStatement prepIngr = conn.prepareStatement("insert into ingredients values (null, ?, ?);");
	prepIngr.setString(1, "pasta");
	prepIngr.setDouble(2, 2.15);
	prepIngr.addBatch();
	prepIngr.setString(1, "tomaten");
	prepIngr.setDouble(2, 3.50);
	prepIngr.addBatch();
	prepIngr.setString(1, "zout");
	prepIngr.setDouble(2, 0.45);
	prepIngr.addBatch();
	prepIngr.setString(1, "water");
	prepIngr.setDouble(2, 0.00);
	prepIngr.addBatch();
        conn.setAutoCommit(false);
        prepIngr.executeBatch();
        conn.setAutoCommit(true);
	
	PreparedStatement prepRec = conn.prepareStatement("insert into recipes values (null, ?, ?, ?);");
	prepRec.setString(1, "saus");
	prepRec.setString(2, "Kap alles bij elkaar en roer gelijk ne zot");
	prepRec.setInt(3, 6);
	prepRec.addBatch();
        conn.setAutoCommit(false);
        prepRec.executeBatch();
        conn.setAutoCommit(true);
	
	PreparedStatement prepRecIngr = conn.prepareStatement("insert into recipesingredients values (null, ?, ?, ?);");
	prepRecIngr.setInt(1, 1);
	prepRecIngr.setInt(2, 2);
	prepRecIngr.setDouble(3, 2.1);
	prepRecIngr.addBatch();
	prepRecIngr.setInt(1, 1);
	prepRecIngr.setInt(2, 3);
	prepRecIngr.setDouble(3, 0.04);
	prepRecIngr.addBatch();
        conn.setAutoCommit(false);
        prepRecIngr.executeBatch();
        conn.setAutoCommit(true);
	
	// fetch all ingredients
        ResultSet rs = stat.executeQuery("select * from ingredients;");
	System.out.println("Ingredients:");
	System.out.println("------------");
        while (rs.next()) {
            System.out.print("\t(" + rs.getInt(1)+")");
            System.out.print("\tname: " + rs.getString("name"));
            System.out.println("\tprice: " + rs.getDouble("price"));
        }
	System.out.println("");
	
	// fetch all recipes
	rs = stat.executeQuery("select * from recipes;");
	System.out.println("recipes:");
	System.out.println("--------");
        while (rs.next()) {
            System.out.print("\t(" + rs.getInt(1)+")");
            System.out.print("\tname: " + rs.getString("name"));
            System.out.println("\tloss: " + rs.getInt("loss")+ "\tpreparation:");
	    System.out.println("\t\t"+ rs.getString("preparation"));
        }
	System.out.println("");
	
	// fetch all recipes-ingredients relations
	rs = stat.executeQuery("select DISTINCT * from recipesingredients JOIN recipes ON parentid JOIN ingredients ON childid GROUP BY parentid, childid;");
	System.out.println("recipes-ingredients:");
	System.out.println("--------");
        while (rs.next()) {
            System.out.print("\t(" + rs.getInt(1)+")");
            System.out.print("\trecipeID: " + rs.getInt("parentid"));
            System.out.print("\tchildID: " + rs.getInt("childid"));
            System.out.println("\tquantity: " + rs.getDouble("quantity"));
        }
	
        rs.close();
        conn.close();
    }
}
