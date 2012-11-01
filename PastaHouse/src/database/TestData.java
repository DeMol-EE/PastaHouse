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
	stat.executeUpdate("drop table if exists suppliers;");
	stat.executeUpdate("drop table if exists ingredients;");
        stat.executeUpdate("drop table if exists recipes;");
        stat.executeUpdate("drop table if exists recipesrecipes;");
        stat.executeUpdate("drop table if exists recipesingredients;");
	
	// create new and fresh tables
        stat.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_SUP()+" ("
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
	stat.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_INGR()+"("
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
	stat.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "naam TEXT NOT NULL ON CONFLICT FAIL, "
		    + "bereiding TEXT, "
		    + "datum TEXT, "
		    + "nettogewicht REAL NOT NULL ON CONFLICT FAIL);");
	stat.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC_INGR()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "receptid INTEGER REFERENCES recipes (id) ON DELETE CASCADE, "
		    + "ingredientid INTEGER REFERENCES ingredients (id) ON DELETE RESTRICT, "
		    + "rang INTEGER NOT NULL ON CONFLICT FAIL,"
		    + "quantiteit REAL NOT NULL ON CONFLICT FAIL);");
	stat.executeUpdate("create table IF NOT EXISTS "+Configuration.center().getDB_TABLE_REC_REC()+" ("
		    + "id INTEGER PRIMARY KEY, "
		    + "receptid INTEGER REFERENCES recipes (id) ON DELETE CASCADE, "
		    + "deelreceptid INTEGER REFERENCES recipes (id) ON DELETE RESTRICT, "
		    + "rang INTEGER NOT NULL ON CONFLICT FAIL,"
		    + "quantiteit REAL NOT NULL ON CONFLICT FAIL);");
	// ADD RANG INDEX TO RELATION ATTRIBUTES TO INDICATE POSITION IN INGREDIENT LIST
	
	
	//add test data
	PreparedStatement prepSup = conn.prepareStatement("insert into suppliers values (null, ?, ?, ?, ?, ?, ?, ?, ?, 0);");
	prepSup.setString(1, "colruyt");
	prepSup.setString(2, "derpstraat 5");
	prepSup.setString(3, "gent");
	prepSup.setString(4, "09 221 77 40");
	prepSup.setString(5, "");
	prepSup.setString(6, "colruyt@gent.be");
	prepSup.setString(7, "open in de week van 8 tot 19");
	prepSup.setString(8, "Jos de Josser");
	prepSup.addBatch();
	prepSup.setString(1, "delhaize");
	prepSup.setString(2, "voskenslaan 1");
	prepSup.setString(3, "gent");
	prepSup.setString(4, "");
	prepSup.setString(5, "");
	prepSup.setString(6, "delhaize@gent.be");
	prepSup.setString(7, "");
	prepSup.setString(8, "Jeanine de Bakker");
	prepSup.addBatch();
        conn.setAutoCommit(false);
        prepSup.executeBatch();
        conn.setAutoCommit(true);
	
	PreparedStatement prepIngr = conn.prepareStatement("insert into ingredients values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
	prepIngr.setInt(1, 1);
	prepIngr.setString(2, "pasta");
	prepIngr.setString(3, "fender");
	prepIngr.setString(4, "doos");
	prepIngr.setDouble(5, 2.19);
	prepIngr.setDouble(6, 0.5);
	prepIngr.setDouble(7, 0.0);
	prepIngr.setDouble(8, 6.0);
	prepIngr.setString(9, "8/11/2008");
	prepIngr.addBatch();
	prepIngr.setInt(1, 2);
	prepIngr.setString(2, "water");
	prepIngr.setString(3, "squire");
	prepIngr.setString(4, "fles");
	prepIngr.setDouble(5, 0.49);
	prepIngr.setDouble(6, 1.5);
	prepIngr.setDouble(7, 0.01);
	prepIngr.setDouble(8, 6.0);
	prepIngr.setString(9, "14/3/2010");
	prepIngr.addBatch();
	prepIngr.setInt(1, 2);
	prepIngr.setString(2, "zout");
	prepIngr.setString(3, "roland");
	prepIngr.setString(4, "pak");
	prepIngr.setDouble(5, 1.3);
	prepIngr.setDouble(6, 1.0);
	prepIngr.setDouble(7, 0.0);
	prepIngr.setDouble(8, 6.0);
	prepIngr.setString(9, "23/5/2009");
	prepIngr.addBatch();
	prepIngr.setInt(1, 1);
	prepIngr.setString(2, "tomaten");
	prepIngr.setString(3, "peavey");
	prepIngr.setString(4, "doos");
	prepIngr.setDouble(5, 4.5);
	prepIngr.setDouble(6, 0.75);
	prepIngr.setDouble(7, 3.0);
	prepIngr.setDouble(8, 6.0);
	prepIngr.setString(9, "30/10/2012");
	prepIngr.addBatch();
        conn.setAutoCommit(false);
        prepIngr.executeBatch();
        conn.setAutoCommit(true);
	
	PreparedStatement prepRec = conn.prepareStatement("insert into recipes values (null, ?, ?, ?, ?);");
	prepRec.setString(1, "saus");
	prepRec.setString(2, "Kap alles bij elkaar en roer gelijk ne zot");
	prepRec.setString(3, "15/6/2011");
	prepRec.setDouble(4, 3.2);
	prepRec.setInt(3, 6);
	prepRec.addBatch();
        conn.setAutoCommit(false);
        prepRec.executeBatch();
        conn.setAutoCommit(true);
	
	PreparedStatement prepRecIngr = conn.prepareStatement("insert into recipesingredients values (null, ?, ?, ?, ?);");
	prepRecIngr.setInt(1, 1);
	prepRecIngr.setInt(2, 2);
	prepRecIngr.setDouble(3, 2);
	prepRecIngr.setDouble(4, 2.1);
	prepRecIngr.addBatch();
	prepRecIngr.setInt(1, 1);
	prepRecIngr.setInt(2, 3);
	prepRecIngr.setDouble(3, 1);
	prepRecIngr.setDouble(4, 0.04);
	prepRecIngr.addBatch();
        conn.setAutoCommit(false);
        prepRecIngr.executeBatch();
        conn.setAutoCommit(true);
	
	// fetch all suppliers
        ResultSet rs = stat.executeQuery("select * from suppliers;");
	System.out.println("Suppliers:");
	System.out.println("----------");
        while (rs.next()) {
            System.out.print("\t(" + rs.getInt(1)+")");
            System.out.print("\tfirma: " + rs.getString("firma"));
            System.out.println("\tadres: " + rs.getString("adres"));
        }
	System.out.println("");
	
	// fetch all ingredients
        rs = stat.executeQuery("select * from ingredients;");
	System.out.println("Ingredients:");
	System.out.println("------------");
        while (rs.next()) {
            System.out.print("\t(" + rs.getInt(1)+")");
            System.out.print("\tname: " + rs.getString("naam"));
            System.out.println("\tprice: " + rs.getDouble("prijsPerVerpakking"));
        }
	System.out.println("");
	
	// fetch all recipes
	rs = stat.executeQuery("select * from recipes;");
	System.out.println("recipes:");
	System.out.println("--------");
        while (rs.next()) {
            System.out.print("\t(" + rs.getInt(1)+")");
            System.out.print("\tname: " + rs.getString("naam"));
            System.out.println("\tpreparation:");
	    System.out.println("\t\t"+ rs.getString("bereiding"));
        }
	System.out.println("");
	
	// fetch all recipes-ingredients relations
//	rs = stat.executeQuery("select DISTINCT * from recipesingredients JOIN recipes ON parentid JOIN ingredients ON childid GROUP BY parentid, childid;");
//	System.out.println("recipes-ingredients:");
//	System.out.println("--------");
//        while (rs.next()) {
//            System.out.print("\t(" + rs.getInt(1)+")");
//            System.out.print("\trecipeID: " + rs.getInt("parentid"));
//            System.out.print("\tchildID: " + rs.getInt("childid"));
//            System.out.println("\tquantity: " + rs.getDouble("quantity"));
//        }
	
        rs.close();
        conn.close();
    }
}
