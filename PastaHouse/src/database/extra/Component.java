/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.extra;

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
    
    /**
     * Returns the used amount of units of this ingredient, calculated using the
     * gross needed weight of the ingredient to get the desired entered net
     * amount, multiplied by the (gross) weight per unit.
     * 
     * @return The needed amount of units of this ingredient.
     */
    public double getUnits() {
	double d = (quantity / (1.0 - 0.01 * ingredient.getLossPercent()) )* ingredient.getWeightPerUnit();
	return d * Math.signum(d);
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
    
    public double getGrossQuantity(){
	double q = quantity / (1.0 - 0.01 * ingredient.getLossPercent());
	return q * Math.signum(q);
    }
    
    public boolean isBasicIngredient(){
	return isIngredient;
    }
    
    public String isIngredientType(){
	return isIngredient? "Basisingrediënt" : "Recept";
    }
}