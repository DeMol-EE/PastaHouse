/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.Database;
import database.FunctionResult;
import database.extra.Ingredient;
import database.models.BasicIngredientModel;
import java.sql.SQLException;
import tools.Configuration;
import tools.StringTools;

/**
 *
 * @author Warkst
 */
public class BasicIngredient extends Ingredient {
    // database columns
    private Contact supplier; // Foreign key, references Suppliers
    private String brand;
    private String packaging;
    private double pricePerUnit;
    private double weightPerUnit;
    private double lossPercent;
    private double taxes;
    private String notes;
    
    // derived variables
    
    private BasicIngredient(int id, Contact supplier, String brand, String packaging, double pricePerUnit, double weightPerUnit, double lossPercent, double taxes, String name, String date, String notes) {
	super(name, date, id, Configuration.center().getDB_TABLE_INGR());
	this.supplier = supplier;
	this.brand = brand;
	this.packaging = packaging;
	this.pricePerUnit = pricePerUnit;
	this.weightPerUnit = weightPerUnit;
	this.lossPercent = lossPercent;
	this.taxes = taxes;
	this.notes = notes;
    }
    
    private BasicIngredient(int id, BasicIngredientModel b){
        super(b.getName(), b.getDate(), id, Configuration.center().getDB_TABLE_INGR());
	this.supplier = b.getSupplier();
	this.brand = b.getBrand();
	this.packaging = b.getPackaging();
	this.pricePerUnit = b.getPricePerUnit();
	this.weightPerUnit = b.getWeightPerUnit();
	this.lossPercent = b.getLossPercent();
	this.taxes = b.getTaxes();
	this.notes = b.getNotes();
    }
    
    public BasicIngredient(BasicIngredient b){
        super(b.getName(), b.getDate(), b.getPrimaryKeyValue(), b.getTableName());
	this.supplier = b.getSupplier();
	this.brand = b.getBrand();
	this.packaging = b.getPackaging();
	this.pricePerUnit = b.getPricePerUnit();
	this.weightPerUnit = b.getWeightPerUnit();
	this.lossPercent = b.getLossPercent();
	this.taxes = b.getTaxes();
	this.notes = b.getNotes();
    }
    
    public static BasicIngredient loadWithValues(int id, Contact supplier, String brand, String packaging, double pricePerUnit, double weightPerUnit, double lossPercent, double taxes, String name, String date, String notes) {
	return new BasicIngredient(id, supplier, brand, packaging, pricePerUnit, weightPerUnit, lossPercent, taxes, name, date, notes);
    }
    
    public static BasicIngredient createFromModel(int id, BasicIngredientModel model) throws SQLException{
	return new BasicIngredient(id, model);
    }
    
    public void copy(BasicIngredient b){
	this.setName(b.getName());
	this.setDate(b.getDate());
	this.supplier = b.getSupplier();
	this.brand = b.getBrand();
	this.packaging = b.getPackaging();
	this.pricePerUnit = b.getPricePerUnit();
	this.weightPerUnit = b.getWeightPerUnit();
	this.lossPercent = b.getLossPercent();
	this.taxes = b.getTaxes();
	this.notes = b.getNotes();
    }

    public void setSupplier(Contact supplier) {
	this.supplier = supplier;
    }

    public void setBrand(String brand) {
	this.brand = brand;
    }

    public void setPackaging(String packaging) {
	this.packaging = packaging;
    }

    public void setPricePerUnit(double pricePerUnit) {
	this.pricePerUnit = pricePerUnit;
    }

    public void setWeightPerUnit(double weightPerUnit) {
	this.weightPerUnit = weightPerUnit;
    }

    public void setLossPercent(double lossPercent) {
	this.lossPercent = lossPercent;
    }

    public void setTaxes(double taxes) {
	this.taxes = taxes;
    }

    public Contact getSupplier() {
	return supplier;
    }

    public String getBrand() {
	return brand;
    }

    @Override
    public String getPackaging() {
	return packaging;
    }

    public double getPricePerUnit() {
	return pricePerUnit;
    }

    @Override
    public double getWeightPerUnit() {
	return weightPerUnit;
    }

    @Override
    public double getLossPercent() {
	return lossPercent;
    }

    public double getTaxes() {
	return taxes;
    }

    @Override
    public double getPricePerWeight() {
	return pricePerUnit/weightPerUnit;
    }

    public double getGrossPrice() {
	return getPricePerWeight()/(1.0-(0.01*lossPercent));
    }

    public double getNetPrice() {
	return getGrossPrice() * (1.0 + 0.01*taxes);
    }
    
    public String getNotes(){
	return notes;
    }
    
    public void setNotes(String notes){
	this.notes = notes;
    }
    
    @Override
    public FunctionResult<BasicIngredient> update(){
	return Database.driver().executeUpdate(getTableName(), getPrimaryKey(), getPrimaryKeyValue(),  
		"firmaid = "+ (supplier == null ? "NULL" : supplier.getPrimaryKeyValue()) +", "
		+ "naam = "+(getName().length()>0 ? "\""+ getName() +"\"":"NULL")+", "
		+ "merk = "+(brand.length()>0 ?"\""+brand +"\"":"NULL")+", "
		+ "verpakking = "+(packaging.length()>0? "\""+packaging +"\"":"NULL")+", "
		+ "prijsPerVerpakking = "+(pricePerUnit>0? "\""+pricePerUnit +"\"":"0")+", "
		+ "gewichtPerVerpakking = "+(weightPerUnit>0? "\""+weightPerUnit +"\"":"0")+", "
		+ "verliespercentage = "+(lossPercent>=0? "\""+lossPercent +"\"":"0")+", "
		+ "BTW = "+(taxes>0? "\""+taxes +"\"":"0")+", "
		+ "datum = "+(getDate().length()>0? "\""+getDate() +"\"":"NULL")+", "
		+ "opmerking = "+(notes.length()>0 ? "\""+notes +"\"":"NULL"));
    }
    
    @Override
    public boolean delete(){
	return false;
    }
    
    @Override
    public String toString(){
	/*
	 * The default cell renderer knows how to display strings and icons and it displays Objects by invoking toString.
	 */
	return StringTools.capitalize(super.getName());
    }
    
    @Override
    public boolean isBasicIngredient(){
	return true;
    }
}
