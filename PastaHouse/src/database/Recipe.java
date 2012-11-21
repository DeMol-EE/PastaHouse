/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.Map;
import java.util.TreeMap;
import utilities.Configuration;
import utilities.StringTools;

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
    
    public Recipe(Recipe r){
	super(r.getName(), r.getDate());
	netWeight = r.netWeight;
	preparation = r.preparation;
	ingredients = new TreeMap<Integer, Component>();
	ingredients.putAll(r.ingredients);
    }
    
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
    
    public void copy(Recipe r){
	setName(r.getName());
	setDate(r.getDate());
	netWeight = r.netWeight;
	preparation = r.preparation;
	ingredients = new TreeMap<Integer, Component>();
	ingredients.putAll(r.ingredients);
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
     * Lazily calculate and return the gross weight of the recipe as the sum of the quantities (= net weights) of all the ingredients.
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
	    if (entry.getValue().getIngredient() != null) {
		returnMe += entry.getValue().getIngredient().getPricePerWeight()*entry.getValue().getGrossQuantity();
	    }
	}
	
	return returnMe/netWeight;
    }
    
    @Override
    public boolean create(){
	return Database.driver().executeInsert(table_id, "bereiding = "+preparation);
    }
    
    @Override
    public boolean update(){
	System.err.println("TODO: set update in db!");
	
//	return Database.driver().executeUpdate(table_id, getName(), "bereiding = "+preparation);
	// update recipes-recipes
	// update recipes-ingredients
	return false;
    }
    
    @Override
    public boolean delete(){
	return false;
    }
    
    @Override
    public String toString(){
	return StringTools.capitalize(getName());
    }
    
    @Override
    public double getWeightPerUnit(){
	return netWeight;
    }

    @Override
    public String getPackaging() {
	return ""; // geen packaging
    }

    @Override
    public double getLossPercent() {
	return netWeight/getGrossWeight();
    }
}
