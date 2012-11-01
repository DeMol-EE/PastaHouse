/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Warkst
 */
public class Recipe extends Ingredient {
    private final String table_id = Configuration.center().getDB_TABLE_REC();
    
    // database variables
    private double netWeight;
    private String preparation;
//    private ArrayList<BasicIngredient> basicIngredients;
//    private ArrayList<Recipe> recipes;
    private HashMap<Integer, Component> ingredients;
    
    // derived variables
    
    public Recipe(String name, String date){
	super(name, date);
    }
    
    private Recipe(String name, String date, String preparation, double netWeight){
	super(name, date);
	this.preparation = preparation;
	this.netWeight = netWeight;
	
//	basicIngredients = new ArrayList<BasicIngredient>();
//	recipes = new ArrayList<Recipe>();
	ingredients = new HashMap<Integer, Component>();
    }
    
    public static Recipe createStub(String name, String date, String preparation, double netWeight){
	return new Recipe(name, date, preparation, netWeight);
    }
    
//    public void addBasicIngredient(BasicIngredient bi){
//	
//    }
//    
//    public void addRecipe(Recipe r){
//	
//    }
    
    public void addIngredient(Ingredient i, int rank, double quantity){
	ingredients.put(rank, new Component(i, rank, quantity));
    }
    
    public double getNetWeight() {
	return netWeight;
    }

    public String getPreparation() {
	return preparation;
    }
    
    @Override
    public void create() throws SQLException{
	Database.driver().executeInsert(table_id, "bereiding = "+preparation);
    }
    
    @Override
    public void update() throws SQLException{
	Database.driver().executeUpdate(table_id, getName(), "bereiding = "+preparation);
	// update recipes-recipes
	// update recipes-ingredients
    }
    
    @Override
    public void delete(){
	
    }
}
