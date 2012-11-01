/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Warkst
 */
public class Recipe extends Ingredient {
    private final String table_id = Configuration.center().getDB_TABLE_REC();
    
    // database variables
    private double netWeight;
    private String preparation;
    private ArrayList<BasicIngredient> basicIngredients;
    private ArrayList<Recipe> recipes;
    
    // derived variables
    
    public Recipe(int id, String name, String date){
	super(id, name, date);
    }
    
    private Recipe(int id, String name, String date, String preparation, double netWeight){
	super(id, name, date);
	this.preparation = preparation;
	this.netWeight = netWeight;
	
	basicIngredients = new ArrayList<BasicIngredient>();
	recipes = new ArrayList<Recipe>();
    }
    
    public static Recipe createStub(int id, String name, String date, String preparation, double netWeight){
	return new Recipe(id, name, date, preparation, netWeight);
    }
    
    public void addBasicIngredient(BasicIngredient bi){
	
    }
    
    public void addRecipe(Recipe r){
	
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
	Database.driver().executeUpdate(table_id, super.getId(), "bereiding = "+preparation);
	// update recipes-recipes
	// update recipes-ingredients
    }
    
    @Override
    public void delete(){
	
    }
}
