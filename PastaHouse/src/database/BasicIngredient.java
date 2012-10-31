/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;

/**
 *
 * @author Warkst
 */
public class BasicIngredient extends Component {
    
    private double price;
    private final String table_id = Configuration.center().getDB_TABLE_INGR();
    
    public BasicIngredient(int id, String name){
	super(id, name);
    }
    
    private BasicIngredient(int id, String name, double price){
	super(id, name);
	this.price = price;
    }
    
    public static BasicIngredient loadWithValues(int id, String name, double price){
	return new BasicIngredient(id, name, price);
    }
    
    public double getPrice(){
	return price;
    }
    
    public void setPrice(double price){
	this.price = price;
    }
    
    @Override
    public void create() throws SQLException{
	Database.driver().executeInsert(table_id, "price = "+price);
    }
    
    @Override
    public void update() throws SQLException{
	Database.driver().executeUpdate(table_id, super.getId(), "price = "+price);
    }
    
    @Override
    public void delete(){
	
    }
    
    @Override
    public String toString(){
	/*
	 * The default cell renderer knows how to display strings and icons and it displays Objects by invoking toString.
	 */
	return super.getName();
    }
}
