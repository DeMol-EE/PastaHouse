/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Warkst
 */
public abstract class Ingredient extends Record implements Comparable<Ingredient> {
    private String name;
    private String date;
    
    public Ingredient(int id, String name, String date){
	super(id);
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

    @Override
    public int compareTo(Ingredient o) {
	return getName().compareTo(o.getName());
    }
}
