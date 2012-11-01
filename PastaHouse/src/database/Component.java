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
}