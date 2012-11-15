/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import utilities.Configuration;
import utilities.Utilities;

/**
 *
 * @author Warkst
 */
public class BasicIngredient extends Ingredient {
    private final String table_id = Configuration.center().getDB_TABLE_INGR();
    
    // database columns
    private Supplier supplier;
    
    private String brand;
    private String packaging;
    private double pricePerUnit;
    private double weightPerUnit;
    private double lossPercent;
    private double taxes;
    private String notes;
    
    // derived variables
    private double pricePerWeight;
    private double grossPrice;
    private double netPrice;
    
    public BasicIngredient(String name, String date){
	super(name, date);
    }

    private BasicIngredient(Supplier supplier, String brand, String packaging, double pricePerUnit, double weightPerUnit, double lossPercent, double taxes, String name, String date, String notes) {
	super(name, date);
	this.supplier = supplier;
	this.brand = brand;
	this.packaging = packaging;
	this.pricePerUnit = pricePerUnit;
	this.weightPerUnit = weightPerUnit;
	this.lossPercent = lossPercent;
	this.taxes = taxes;
	this.notes = notes;
	
	this.pricePerWeight = pricePerUnit/weightPerUnit;
	this.grossPrice = pricePerWeight/(1.0-(0.01*lossPercent));
	this.netPrice = grossPrice * (1.0 + 0.01*taxes);
    }
    
    public BasicIngredient(BasicIngredient b){
	super(b.getName(), b.getDate());
	this.supplier = b.getSupplier();
	this.brand = b.getBrand();
	this.packaging = b.getPackaging();
	this.pricePerUnit = b.getPricePerUnit();
	this.weightPerUnit = b.getWeightPerUnit();
	this.pricePerWeight = b.getPricePerWeight();
	this.lossPercent = b.getLossPercent();
	this.taxes = b.getTaxes();
	this.pricePerWeight = b.getPricePerWeight();
	this.grossPrice = b.getGrossPrice();
	this.netPrice = b.getNetPrice();
	this.notes = b.getNotes();
    }
    
    public static BasicIngredient loadWithValues(Supplier supplier, String brand, String packaging, double pricePerUnit, double weightPerUnit, double lossPercent, double taxes, String name, String date, String notes) {
	return new BasicIngredient(supplier, brand, packaging, pricePerUnit, weightPerUnit, lossPercent, taxes, name, date, notes);
    }
    
    public void load(BasicIngredient b){
	this.setName(b.getName());
	this.setDate(b.getDate());
	this.supplier = b.getSupplier();
	this.brand = b.getBrand();
	this.packaging = b.getPackaging();
	this.pricePerUnit = b.getPricePerUnit();
	this.weightPerUnit = b.getWeightPerUnit();
	this.pricePerWeight = b.getPricePerWeight();
	this.lossPercent = b.getLossPercent();
	this.taxes = b.getTaxes();
	this.pricePerWeight = b.getPricePerWeight();
	this.grossPrice = b.getGrossPrice();
	this.netPrice = b.getNetPrice();
	this.notes = b.getNotes();
    }

    public String getTable_id() {
	return table_id;
    }

    public void setSupplier(Supplier supplier) {
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

    @Override
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
    public double getPricePerWeight() {
	return pricePerWeight;
    }

    public double getGrossPrice() {
	return grossPrice;
    }

    public double getNetPrice() {
	return netPrice;
    }
    
    public String getNotes(){
	return notes;
    }
    
    public void setNotes(String notes){
	this.notes = notes;
    }
    
    @Override
    public boolean create(){
	return false;
    }
    
    @Override
    public boolean update(){
	return Database.driver().executeUpdate(table_id, getPrimaryKey(), getName(),  
		"firma = \""+ supplier.getFirm() +"\", "
		+ "naam = "+(getName().length()>0 ? "\""+ getName() +"\"":"NULL")+", "
		+ "merk = "+(brand.length()>0 ?"\""+brand +"\"":"NULL")+", "
		+ "verpakking = "+(packaging.length()>0? "\""+packaging +"\"":"NULL")+", "
		+ "prijsPerVerpakking = "+(pricePerUnit>0? "\""+pricePerUnit +"\"":"NULL")+", "
		+ "gewichtPerVerpakking = "+(weightPerUnit>0? "\""+weightPerUnit +"\"":"NULL")+", "
		+ "verliespercentage = "+(lossPercent>0? "\""+lossPercent +"\"":"NULL")+", "
		+ "BTW = "+(taxes>0? "\""+taxes +"\"":"NULL")+", "
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
	return Utilities.capitalize(super.getName());
    }
}
