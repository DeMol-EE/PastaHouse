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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ArrayList<InvoiceItem> items;
    
    private Map<Double, List<InvoiceItem>> itemsPerCategory;

    private Invoice(int id, int number, String date, Contact client, String priceCode, double save) {
	super(id, Configuration.center().getDB_TABLE_INV());
	this.number = number;
	this.date = date;
	this.client = client;
	this.priceCode = priceCode;
	this.save = save;
	this.items = new ArrayList<InvoiceItem>();
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

    public void addItem( double amount, Article article) {
	items.add(new InvoiceItem(article, amount));
    }
    
    public ArrayList<InvoiceItem> items(){
	return items;
    }
    
    public Map<Double, List<InvoiceItem>> itemsPerTaxesCategory(boolean reset){
	if (itemsPerCategory == null || reset == true) {
	    itemsPerCategory = new HashMap<Double, List<InvoiceItem>>();
	
	    for (InvoiceItem invoiceItem : items()) {
		if (itemsPerCategory.containsKey(invoiceItem.getArticle().getTaxes())) {
		    itemsPerCategory.get(invoiceItem.getArticle().getTaxes()).add(invoiceItem);
		} else {
		    List<InvoiceItem> _items = new ArrayList<InvoiceItem>();
		    _items.add(invoiceItem);
		    itemsPerCategory.put(invoiceItem.getArticle().getTaxes(), _items);
		}
	    }
	}
	return itemsPerCategory;
    }
    
    public Map<Double, List<InvoiceItem>> itemsPerTaxesCategory(){
	return itemsPerTaxesCategory(true);
    }
    
    public List<Double> netBeforeSave(){
	List<Double> nets = new ArrayList<Double>();
	
	itemsPerTaxesCategory(false);
	
	for (List<InvoiceItem> list : itemsPerCategory.values()) {
	    double net = 0.0;
	    for (InvoiceItem invoiceItem : list) {
		net+=invoiceItem.getArticle().getPriceForCode(getPriceCode())*invoiceItem.getAmount();
	    }
	    nets.add(net);
	}
	
	return nets;
    }
    
    public List<Double> savings(){
	List<Double> savings = new ArrayList<Double>();
	
	List<Double> nets = netBeforeSave();
	
	int index = 0;
	for (List<InvoiceItem> list : itemsPerCategory.values()) {
	    double sav = nets.get(index) * getSave()/100;
	    savings.add(sav);
	    index++;
	}
	
	return savings;
    }
    
    public List<Double> netAfterSave(){
	List<Double> nets = new ArrayList<Double>();
	
	List<Double> net = netBeforeSave();
	List<Double> saves = savings();
	
	for (int i = 0; i < net.size(); i++) {
	    nets.add(net.get(i) - saves.get(i));
	}
	
	return nets;
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
	return Database.driver().deleteInvoice(this).getCode()==0;
    }
}
