/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import database.Database;
import database.FunctionResult;
import database.extra.Component;
import database.extra.InvoiceItem;
import database.tables.Article;
import database.tables.Contact;
import database.tables.Invoice;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Hannes
 */
public class InvoiceModel implements Model {

    private Integer number;
    private Contact client;
    private String date;
    private String priceCode;
    private double save;
    private Map<Integer, InvoiceItem> items = new TreeMap<Integer, InvoiceItem>();

    public Map<Integer, InvoiceItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, InvoiceItem> items) {
        this.items = items;
    }



    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Contact getClient() {
        return client;
    }

    public void setClient(Contact client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public FunctionResult<Invoice> create() {
        try {
            return Database.driver().addInvoice(this);
        } catch (SQLException ex) {
            System.err.println("Exception:\n" + ex.getMessage());
            return new FunctionResult<Invoice>(3, null, ex.getMessage());
        }
    }
}
