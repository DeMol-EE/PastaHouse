/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.extra;

/**
 * An extension to the base class Record by adding a name and date.
 * Represents both Recipes and Basic Ingredients. Offers the functionality
 * to get a couple of attributes needed for both, such as a loss %, price
 * per weight, weight per unit and packaging.
 *
 * @author Warkst
 */
public abstract class Ingredient extends Record implements Comparable<Ingredient> {
    private String name;
    private String date;

    public Ingredient(String name, String date, int id, String table) {
	super(id, table);
        this.name = name;
        this.date = date;
    }

    public String getName() {
	return name;
    }
    
    public String getDate() {
	return date;
    }
    
    public void setName(String name) {
	this.name = name;
    }
    
    public void setDate(String date){
	this.date = date;
    }

    @Override
    public int compareTo(Ingredient o) {
	return name.toUpperCase().compareTo(o.getName().toUpperCase());
    }
    
    public abstract double getPricePerWeight();
    
    public abstract double getWeightPerUnit();
    
    public abstract String getPackaging();
    
    public abstract double getLossPercent();
    
    public abstract boolean isBasicIngredient();
    
    public abstract boolean isInBulk();
}
