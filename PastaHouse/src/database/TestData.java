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

//	PreparedStatement prep = conn.prepareStatement(
//            "insert into people values (?, ?);");
//
//        prep.setString(1, "Gandhi");
//        prep.setString(2, "politics");
//        prep.addBatch();
//        prep.setString(1, "Turing");
//        prep.setString(2, "computers");
//        prep.addBatch();
//        prep.setString(1, "Wittgenstein");
//        prep.setString(2, "smartypants");
//        prep.addBatch();
//
//        conn.setAutoCommit(false);
//        prep.executeBatch();
//        conn.setAutoCommit(true);
//
//        ResultSet rs = stat.executeQuery("select * from people;");
//        while (rs.next()) {
//            System.out.println("name = " + rs.getString("name"));
//            System.out.println("job = " + rs.getString("occupation"));
//        }
//        rs.close();
//        conn.close();
    }
}
