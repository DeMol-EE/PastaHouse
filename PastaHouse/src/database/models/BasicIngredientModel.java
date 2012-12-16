/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import database.Database;
import database.FunctionResult;
import database.tables.BasicIngredient;
import database.tables.Contact;
import java.sql.SQLException;

/**
 *
 * @author Warkst
 */
public class BasicIngredientModel implements Model{
    private String name;
    private String date;
    
    private Contact supplier; // Foreign key, references Suppliers
    private String brand;
    private String packaging;
    private double pricePerUnit;
    private double weightPerUnit;
    private double lossPercent;
    private double taxes;
    private String notes;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public Contact getSupplier() {
	return supplier;
    }

    public void setSupplier(Contact supplier) {
	this.supplier = supplier;
    }

    public String getBrand() {
	return brand;
    }

    public void setBrand(String brand) {
	this.brand = brand;
    }

    public String getPackaging() {
	return packaging;
    }

    public void setPackaging(String packaging) {
	this.packaging = packaging;
    }

    public double getPricePerUnit() {
	return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
	this.pricePerUnit = pricePerUnit;
    }

    public double getWeightPerUnit() {
	return weightPerUnit;
    }

    public void setWeightPerUnit(double weightPerUnit) {
	this.weightPerUnit = weightPerUnit;
    }

    public double getLossPercent() {
	return lossPercent;
    }

    public void setLossPercent(double lossPercent) {
	this.lossPercent = lossPercent;
    }

    public double getTaxes() {
	return taxes;
    }

    public void setTaxes(double taxes) {
	this.taxes = taxes;
    }

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
    }
    
    public double getPricePerWeight() {
	return pricePerUnit/weightPerUnit;
    }

    public double getGrossPrice() {
	return getPricePerWeight()/(1.0-(0.01*lossPercent));
    }

    public double getNetPrice() {
	return getGrossPrice() * (1.0 + 0.01*taxes);
    }
    
    @Override
    public FunctionResult<BasicIngredient> create(){
	try {
	    return Database.driver().addBasicIngredient(this);
	} catch (SQLException ex) {
	    System.err.println("Exception:\n"+ex.getMessage());
	    return new FunctionResult<BasicIngredient>(3, null, ex.getMessage());
	}
    }
}
