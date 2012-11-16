/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import utilities.Configuration;
import utilities.Utilities;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Warkst
 */
public class Recipe extends Ingredient {
    private final String table_id = Configuration.center().getDB_TABLE_REC();
    
    // database variables
    private double netWeight;
    private String preparation;
    private Map<Integer, Component> ingredients;
    
    // derived variables
    
    public Recipe(String name, String date){
	super(name, date);
    }
    
    private Recipe(String name, String date, String preparation, double netWeight){
	super(name, date);
	this.preparation = preparation;
	this.netWeight = netWeight;
	
	ingredients = new TreeMap<Integer, Component>();
    }
    
    public static Recipe createStub(String name, String date, String preparation, double netWeight){
	return new Recipe(name, date, preparation, netWeight);
    }
    
    public void addIngredient(Ingredient i, int rank, double quantity, boolean isIngredient){
	ingredients.put(rank, new Component(i, rank, quantity, isIngredient));
    }
    
    public double getNetWeight() {
	return netWeight;
    }

    public String getPreparation() {
	return preparation;
    }
    
    /**
     * Lazily calculate and return the gross weight of the recipe's ingredients
     * 
     * @return 
     */
    public double getGrossWeight(){
	double returnMe = 0.0;
	
	for (Map.Entry<Integer, Component> entry : ingredients.entrySet()) {
	    returnMe += entry.getValue().getQuantity();
	}
	
	return returnMe;
    }
    
    public Map<Integer, Component> getIngredients(){
	return ingredients;
    }
    
    @Override
    public double getPricePerWeight(){
	double returnMe = 0.0;
	
	for (Map.Entry<Integer, Component> entry : ingredients.entrySet()) {
	    returnMe += entry.getValue().getIngredient().getPricePerWeight();
	}
	
	return returnMe;
    }
    
    @Override
    public boolean create(){
	return Database.driver().executeInsert(table_id, "bereiding = "+preparation);
    }
    
    @Override
    public boolean update(){
	return false;
//	return Database.driver().executeUpdate(table_id, getName(), "bereiding = "+preparation);
	// update recipes-recipes
	// update recipes-ingredients
    }
    
    @Override
    public boolean delete(){
	return false;
    }
    
    @Override
    public String toString(){
	return Utilities.capitalize(getName());
    }
    
    @Override
    public double getWeightPerUnit(){
	return netWeight;
    }
}
