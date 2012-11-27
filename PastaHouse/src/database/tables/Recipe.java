/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.Database;
import database.extra.Component;
import database.extra.Ingredient;
import database.models.RecipeModel;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.Configuration;
import utilities.StringTools;

/**
 *
 * @author Warkst
 */
public class Recipe extends Ingredient {
//    private final String table_id = Configuration.center().getDB_TABLE_REC();
    
    // database variables
    private double netWeight;
    private String preparation;
    private Map<Integer, Component> ingredients;
    
    // derived variables
    
    private Recipe(int id, RecipeModel r){
	super(r.getName(), r.getDate(), id, Configuration.center().getDB_TABLE_REC());
	netWeight = r.getNetWeight();
	preparation = r.getPreparation();
	ingredients = new TreeMap<Integer, Component>();
	ingredients.putAll(r.getIngredients());
    }
    
    /**
     * Copy constructor.
     * 
     * @param r 
     */
    public Recipe(Recipe r){
	super(r.getName(), r.getDate(), r.getPrimaryKeyValue(), r.getTableName());
	netWeight = r.netWeight;
	preparation = r.preparation;
	ingredients = new TreeMap<Integer, Component>();
	ingredients.putAll(r.ingredients);
    }
    
    public Recipe(int id, String name, String date){
	this(id, name, date, "", 0.0);
    }
    
    private Recipe(int id, String name, String date, String preparation, double netWeight){
	super(name, date, id, Configuration.center().getDB_TABLE_REC());
	this.preparation = preparation;
	this.netWeight = netWeight;
	
	ingredients = new TreeMap<Integer, Component>();
    }
    
    public static Recipe createStub(int id, String name, String date, String preparation, double netWeight){
	return new Recipe(id, name, date, preparation, netWeight);
    }
    
    public static Recipe createFromModel(int id, RecipeModel model){
	return new Recipe(id, model);
    }
    
    public void copy(Recipe r){
	setName(r.getName());
	setDate(r.getDate());
	netWeight = r.netWeight;
	preparation = r.preparation;
	ingredients = new TreeMap<Integer, Component>();
	ingredients.putAll(r.ingredients);
    }
    
    public void addIngredient(Ingredient i, int rank, double quantity){
	ingredients.put(rank, new Component(i, rank, quantity));
    }

    public void setNetWeight(double netWeight) {
	this.netWeight = netWeight;
    }

    public void setPreparation(String preparation) {
	this.preparation = preparation;
    }
    
    public double getNetWeight() {
	return netWeight;
    }

    public String getPreparation() {
	return preparation;
    }

    public void setIngredients(Map<Integer, Component> ingredients) {
	this.ingredients = ingredients;
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
	
	return Math.abs(netWeight-0.0)>0.0001 ? returnMe/netWeight : 0.0;
    }
    
    @Override
    public boolean update(){
        try {
            return Database.driver().updateRecipe(this);
        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
    
    @Override
    public boolean isBasicIngredient(){
	return false;
    }
}
