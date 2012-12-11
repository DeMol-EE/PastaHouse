/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import database.Database;
import database.FunctionResult;
import database.extra.Component;
import database.tables.Recipe;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Warkst
 */
public class RecipeModel implements Model{
    private String name;
    private String date;
    
    private double netWeight;
    private String preparation;
    private Map<Integer, Component> components;

    public RecipeModel(){
	components = new TreeMap<Integer, Component>();
    }
    
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public double getNetWeight() {
	return netWeight;
    }

    public void setNetWeight(double netWeight) {
	this.netWeight = netWeight;
    }

    public String getPreparation() {
	return preparation;
    }

    public void setPreparation(String preparation) {
	this.preparation = preparation;
    }

    public Map<Integer, Component> getComponents() {
	return components;
    }

    public void setIngredients(Map<Integer, Component> ingredients) {
	this.components = ingredients;
    }
    
    /**
     * Lazily calculate and return the gross weight of the recipe as the sum of the quantities (= net weights) of all the ingredients.
     * 
     * @return 
     */
    public double getGrossWeight(){
	double returnMe = 0.0;
	
	for (Map.Entry<Integer, Component> entry : components.entrySet()) {
	    returnMe += entry.getValue().getQuantity();
	}
	
	return returnMe;
    }
    
    public double getPricePerWeight(){
	double returnMe = 0.0;
	
	for (Map.Entry<Integer, Component> entry : components.entrySet()) {
	    if (entry.getValue().getIngredient() != null) {
		returnMe += entry.getValue().getIngredient().getPricePerWeight()*entry.getValue().getQuantity();
	    }
	}
	
//	return Math.abs(netWeight-0.0)>0.0001 ? returnMe/netWeight : 0.0;
	return getGrossWeight()==0 ? 0 : returnMe/getGrossWeight();
    }
    
    @Override
    public FunctionResult<Recipe> create(){
	try {
	    return Database.driver().addRecipe(this);
	} catch (SQLException ex) {
	    return new FunctionResult<Recipe>(3, null);
	}
    }
}
