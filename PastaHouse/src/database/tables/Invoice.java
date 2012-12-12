/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.extra.InvoiceItem;
import database.extra.Record;
import java.util.List;

/**
 *
 * @author Robin jr
 */
public class Invoice extends Record{
    private int number;
    private String date;
    private Client client;
    private String priceCode;
    private double save;
    private List<InvoiceItem> items;

    public Invoice(int id, String... rest){
	super(id, "atable");
    }
    
    @Override
    public boolean update() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete() {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}
