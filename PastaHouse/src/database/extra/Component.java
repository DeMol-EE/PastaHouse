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
    
    public Component(){
	
    }
    
    public Component(Ingredient ingr, int rank, double quantity) {
	this.ingredient = ingr;
	this.rank = rank;
	this.quantity = quantity;
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
    
    public String isIngredientType(){
	return ingredient.isBasicIngredient()? "Basisingrediënt" : "Recept";
    }
    
    public double getPieces(){
	return ingredient == null? 0.0 : quantity/ingredient.getWeightPerUnit();
    }
}