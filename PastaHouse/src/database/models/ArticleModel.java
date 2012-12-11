/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import database.FunctionResult;

/**
 *
 * @author Warkst
 */
public class ArticleModel implements Model{
    private String code;
    private String name;
    private double priceA;
    private double priceB;
    private String unit;
    private double taxes;

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public double getPriceA() {
	return priceA;
    }

    public void setPriceA(double priceA) {
	this.priceA = priceA;
    }

    public double getPriceB() {
	return priceB;
    }

    public void setPriceB(double priceB) {
	this.priceB = priceB;
    }

    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

    public double getTaxes() {
	return taxes;
    }

    public void setTaxes(double taxes) {
	this.taxes = taxes;
    }
    
    @Override
    public FunctionResult create() {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
