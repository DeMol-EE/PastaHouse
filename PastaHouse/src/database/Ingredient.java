/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Warkst
 */
public abstract class Ingredient implements Record {
    private String name;
    private String date;
    
    public Ingredient(String name, String date){
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
    public String getPrimaryKey(){
	return "naam";
    }
    
    public abstract double getPricePerWeight();
}
