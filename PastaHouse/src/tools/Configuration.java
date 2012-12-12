/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 *
 * @author Warkst
 */
public class Configuration {
    // key paths of the key-value pairs in the conf file
    private final String DB_URL_kp = "db_url";
    private final String DB_TABLE_INGR_kp = "table_ingredients";
    private final String DB_TABLE_REC_kp = "table_recipes";
    private final String DB_TABLE_REC_INGR_kp = "table_recipes_ingredients";
    private final String DB_TABLE_REC_REC_kp = "table_recipes_recipes";
    private final String DB_TABLE_CON_kp = "table_contacts";
    private final String DB_TABLE_MUNI_kp = "table_municipales";
    private final String DB_TABLE_ART_kp = "table_articles";
    private final String DB_TABLE_INV_kp = "table_invoices";
    private final String DB_TABLE_INV_ART_kp = "table_invoices_articles";
    
    // defaults
    private String DB_URL_d = "jdbc:sqlite:pastahouse.db";
    private String DB_TABLE_INGR_d = "ingredients";
    private String DB_TABLE_REC_d = "recipes";
    private String DB_TABLE_REC_INGR_d = "recipesingredients";
    private String DB_TABLE_REC_REC_d = "recipesrecipes";
    private String DB_TABLE_CON_d = "contacts";
    private String DB_TABLE_MUNI_d = "Gemeentes";
    private String DB_TABLE_ART_d = "articles";
    private String DB_TABLE_INV_d = "invoices";
    private String DB_TABLE_INV_ART_d = "invoicesarticles";
    
    // variables storing the actual values from the conf file
    private String DB_URL;
    private String DB_TABLE_INGR;
    private String DB_TABLE_REC;
    private String DB_TABLE_REC_INGR;
    private String DB_TABLE_REC_REC;
    private String DB_TABLE_CON;
    private String DB_TABLE_MUNI;
    private String DB_TABLE_ART;
    private String DB_TABLE_INV;
    private String DB_TABLE_INV_ART;
 
    private static Configuration center;
    
    // read config from file and store in variables
    private Configuration(){
	try{
	    // open file and read contents
	    File conf = new File("conf.txt");
	    if(!conf.exists()){
		conf.createNewFile();
		// set variables to defaults
		DB_URL = DB_URL_d;
		DB_TABLE_INGR = DB_TABLE_INGR_d;
		DB_TABLE_REC = DB_TABLE_REC_d;
		DB_TABLE_REC_INGR = DB_TABLE_REC_INGR_d;
		DB_TABLE_REC_REC = DB_TABLE_REC_REC_d;
		DB_TABLE_CON = DB_TABLE_CON_d;
                DB_TABLE_MUNI = DB_TABLE_MUNI_d;
                DB_TABLE_ART = DB_TABLE_ART_d;
                DB_TABLE_INV = DB_TABLE_INV_d;
                DB_TABLE_INV_ART = DB_TABLE_INV_ART_d;
    
		// write the settings to the file
		FileWriter fstream = new FileWriter(conf);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(DB_URL_kp+","+DB_URL_d+"\n");
		out.write(DB_TABLE_INGR_kp+","+DB_TABLE_INGR_d+"\n");
		out.write(DB_TABLE_REC_kp+","+DB_TABLE_REC_d+"\n");
		out.write(DB_TABLE_REC_INGR_kp+","+DB_TABLE_REC_INGR_d+"\n");
		out.write(DB_TABLE_REC_REC_kp+","+DB_TABLE_REC_REC_d+"\n");
		out.write(DB_TABLE_CON_kp+","+DB_TABLE_CON_d+"\n");
                out.write(DB_TABLE_MUNI_kp+","+DB_TABLE_MUNI_d+"\n");
                out.write(DB_TABLE_ART_kp+","+DB_TABLE_ART_d+"\n");
                out.write(DB_TABLE_INV_kp+","+DB_TABLE_INV_d+"\n");
                out.write(DB_TABLE_INV_ART_kp+","+DB_TABLE_INV_ART_d+"\n");
		//Close the output stream
		out.close();
	    } else {
		// read values into temp HashMap
		
		HashMap<String, String> values = new HashMap<String, String>();
		
		FileInputStream fstream = new FileInputStream(conf);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
		    // Print the content on the console
		    String[] split = strLine.split(",");
		    values.put(split[0], split[1]);
		}
		
		// set variables
		DB_URL = values.containsKey(DB_URL_kp) ? values.get(DB_URL_kp) : DB_URL_d;
		DB_TABLE_INGR = values.containsKey(DB_TABLE_INGR_kp) ? values.get(DB_TABLE_INGR_kp) : DB_TABLE_INGR_d;
		DB_TABLE_REC = values.containsKey(DB_TABLE_REC_kp) ? values.get(DB_TABLE_REC_kp) : DB_TABLE_REC_d;
		DB_TABLE_REC_INGR = values.containsKey(DB_TABLE_REC_INGR_kp) ? values.get(DB_TABLE_REC_INGR_kp) : DB_TABLE_REC_INGR_d;
		DB_TABLE_REC_REC = values.containsKey(DB_TABLE_REC_REC_kp) ? values.get(DB_TABLE_REC_REC_kp) : DB_TABLE_REC_REC_d;
		DB_TABLE_CON = values.containsKey(DB_TABLE_CON_kp) ? values.get(DB_TABLE_CON_kp) : DB_TABLE_CON_d;
                DB_TABLE_MUNI = values.containsKey(DB_TABLE_MUNI_kp) ? values.get(DB_TABLE_MUNI_kp) : DB_TABLE_MUNI_d;
                DB_TABLE_ART = values.containsKey(DB_TABLE_ART_kp) ? values.get(DB_TABLE_ART_kp) : DB_TABLE_ART_d;
                DB_TABLE_INV = values.containsKey(DB_TABLE_INV_kp) ? values.get(DB_TABLE_INV_kp) : DB_TABLE_INV_d;
                DB_TABLE_INV_ART = values.containsKey(DB_TABLE_INV_ART_kp) ? values.get(DB_TABLE_INV_ART_kp) : DB_TABLE_INV_ART_d;
	    }
	    
	} catch(Exception e){
	    // show error and shut down
	    
	}
    }
    
    public static Configuration center(){
	if(center == null){
	    center = new Configuration();
	}
	return center;
    }

    public String getDB_URL() {
	return DB_URL;
    }

    public String getDB_TABLE_INGR() {
	return DB_TABLE_INGR;
    }

    public String getDB_TABLE_REC() {
	return DB_TABLE_REC;
    }

    public String getDB_TABLE_REC_INGR() {
	return DB_TABLE_REC_INGR;
    }

    public String getDB_TABLE_REC_REC() {
	return DB_TABLE_REC_REC;
    }
    
    public String getDB_TABLE_CON() {
	return DB_TABLE_CON;
    }
    
    public String getDB_TABLE_MUNI() {
	return DB_TABLE_MUNI;
    }
    
    public String getDB_TABLE_ART() {
	return DB_TABLE_ART;
    }
    
    public String getDB_TABLE_INV() {
	return DB_TABLE_INV;
    }
    
    public String getDB_TABLE_INV_ART() {
	return DB_TABLE_INV_ART;
    }
}
