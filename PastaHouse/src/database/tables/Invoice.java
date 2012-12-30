/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.Database;
import database.FunctionResult;
import database.extra.InvoiceItem;
import database.extra.Record;
import database.models.InvoiceModel;
import database.models.RecipeModel;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.Configuration;

/**
 *
 * @author Robin jr
 */
public class Invoice extends Record<Invoice>{
    private int number;
    private String date;
    private Contact client;
    private String priceCode;
    private double save;
    private Map<Integer, InvoiceItem> items;

    private Invoice(int id, int number, String date, Contact client, String priceCode, double save) {
	super(id, Configuration.center().getDB_TABLE_INV());
	this.number = number;
	this.date = date;
	this.client = client;
	this.priceCode = priceCode;
	this.save = save;
	this.items = new TreeMap<Integer, InvoiceItem>();
    }
    
    public static Invoice createStub(int id, int number, String date, Contact client, String priceCode, double save){
	return new Invoice(id, number, date, client, priceCode, save);
    }
    
    public static Invoice createFromModel(int id, InvoiceModel model){
	return new Invoice(id, model.getNumber(), model.getDate(), model.getClient(), model.getPriceCode(), model.getSave());
    }

    public int getNumber() {
	return number;
    }

    public void setNumber(int number) {
	this.number = number;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public Contact getClient() {
	return client;
    }

    public void setClient(Contact client) {
	this.client = client;
    }

    public String getPriceCode() {
	return priceCode;
    }

    public void setPriceCode(String priceCode) {
	this.priceCode = priceCode;
    }

    public double getSave() {
	return save;
    }

    public void setSave(double save) {
	this.save = save;
    }

    public void addItem(int rank, double amount, double save, Article article) {
	items.put(rank, new InvoiceItem(article, rank, amount, save));
    }
    
    public Map<Integer, InvoiceItem> items(){
	return items;
    }
    
    @Override
    public FunctionResult<Invoice> update() {
	try {
            return Database.driver().updateInvoice(this);
        } catch (SQLException ex) {
            Logger.getLogger(Recipe.class.getName()).log(Level.SEVERE, null, ex);
            return new FunctionResult<Invoice>(2, null, ex.getMessage());
        }
    }

    @Override
    public boolean delete() {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}
