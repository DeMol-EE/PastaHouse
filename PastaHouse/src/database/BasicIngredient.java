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
    private final String table_id = Configuration.center().getDB_TABLE_INGR();
    
    private Supplier supplier;
    
    private String brand;
    private String packaging;
    private double pricePerUnit;
    private double weightPerUnit;
    private double lossPercent;
    private double taxes;
    
    
    public BasicIngredient(int id, String name, String date){
	super(id, name, date);
    }

    private  BasicIngredient(Supplier supplier, String brand, String packaging, double pricePerUnit, double weightPerUnit, double lossPercent, double taxes, int id, String name, String date) {
	super(id, name, date);
	this.supplier = supplier;
	this.brand = brand;
	this.packaging = packaging;
	this.pricePerUnit = pricePerUnit;
	this.weightPerUnit = weightPerUnit;
	this.lossPercent = lossPercent;
	this.taxes = taxes;
    }
    
    public static BasicIngredient loadWithValues(Supplier supplier, String brand, String packaging, double pricePerUnit, double weightPerUnit, double lossPercent, double taxes, int id, String name, String date) {
	return new BasicIngredient(supplier, brand, packaging, pricePerUnit, weightPerUnit, lossPercent, taxes, id, name, date);
    }

    public String getTable_id() {
	return table_id;
    }

    public Supplier getSupplier() {
	return supplier;
    }

    public String getBrand() {
	return brand;
    }

    public String getPackaging() {
	return packaging;
    }

    public double getPricePerUnit() {
	return pricePerUnit;
    }

    public double getWeightPerUnit() {
	return weightPerUnit;
    }

    public double getLossPercent() {
	return lossPercent;
    }

    public double getTaxes() {
	return taxes;
    }
    
    @Override
    public void create() throws SQLException{
//	Database.driver().executeInsert(table_id, "price = "+price);
    }
    
    @Override
    public void update() throws SQLException{
//	Database.driver().executeUpdate(table_id, super.getId(), "price = "+price);
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
