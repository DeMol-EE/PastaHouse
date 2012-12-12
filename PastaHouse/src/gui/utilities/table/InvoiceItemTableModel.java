/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table;

import database.extra.InvoiceItem;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Warkst
 */
public class InvoiceItemTableModel extends AbstractTableModel{

    private final Map<Integer, InvoiceItem> data;
    
    public InvoiceItemTableModel(Map<Integer, InvoiceItem> data){
	this.data = data;
    }
    
    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public int getColumnCount() {
	return 3;
    }

    @Override
    public String getColumnName(int column) {
	switch(column){
	    case 0: return "Artikel";
	    case 1: return "Hoeveelheid";
	    case 2: return "BTW";
	    default: return "ERROR";
	}
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
	return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	switch(columnIndex){
	    case 0: return ((InvoiceItem)data.values().toArray()[rowIndex]).getArticle().getName();
	    case 1: return ((InvoiceItem)data.values().toArray()[rowIndex]).getAmount();
	    case 2: return ((InvoiceItem)data.values().toArray()[rowIndex]).getTaxes();
	    default: return "Error";
	}
    }
    
}
