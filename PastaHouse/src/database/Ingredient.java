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
    private final int id;

    public Ingredient(String name, String date, int id) {
        this.name = name;
        this.date = date;
        this.id = id;
    }
    
    public Ingredient(String name, String date){
	this(name, date, -1);
    }

    public int getId() {
        return id;
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
    public String getPrimaryKey(){
	return "naam";
    }
    
    @Override
    public int getPrimaryKeyValue(){
	return id;
    }
    
    public abstract double getPricePerWeight();
    
    public abstract double getWeightPerUnit();
    
    public abstract String getPackaging();
    
    public abstract double getLossPercent();
}
