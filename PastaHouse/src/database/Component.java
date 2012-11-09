/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Robin jr
 */
public class Component {
    private Ingredient ingredient;
    private int rank;
    private double quantity;
    private boolean isIngredient;
    
    public Component(){
	
    }
    
    public Component(Ingredient ingr, int rank, double quantity, boolean isIngredient) {
	this.ingredient = ingr;
	this.rank = rank;
	this.quantity = quantity;
	this.isIngredient = isIngredient;
    }

    public Ingredient getIngredient() {
	return ingredient;
    }

    public int getRank() {
	return rank;
    }

    public double getQuantity() {
	return quantity;
    }
    
    public double getPieces() {
	return quantity * ingredient.getWeightPerUnit();
    }

    public void setIngredient(Ingredient ingredient) {
	this.ingredient = ingredient;
    }

    public void setRank(int rank) {
	this.rank = rank;
    }

    public void setQuantity(double quantity) {
	this.quantity = quantity;
    }

    public void setIsIngredient(boolean isIngredient) {
	this.isIngredient = isIngredient;
    }
    
    public boolean isIngredient(){
	return isIngredient;
    }
    
    public String isIngredientType(){
	return isIngredient? "Basisingrediënt" : "Recept";
    }
}