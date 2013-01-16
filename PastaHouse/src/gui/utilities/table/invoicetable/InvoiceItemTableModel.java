/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table.invoicetable;

import database.extra.InvoiceItem;
import java.util.Map;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Warkst
 */
public class InvoiceItemTableModel extends AbstractTableModel {

    private final Map<Integer, InvoiceItem> data;

   
    public InvoiceItemTableModel(Map<Integer, InvoiceItem> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Artikel";
            case 1:
                return "BTW";
            case 2:
                return "Hoeveelheid";
            case 3:
                return "Prijs";
            case 4:
                return "Totaal";
            default:
                return "ERROR";
        }
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceItem item = (InvoiceItem) data.values().toArray()[rowIndex];
        double price;
        String code = item.getArticle().getCode();
        if (code.equals("A")) {
            price = item.getArticle().getPriceA();
        } else {
            price = item.getArticle().getPriceB();
        }
        switch (columnIndex) {
            case 0:
                return item.getArticle().getName();
            case 1:
                return item.getAmount();
            case 2:
                return item.getTaxes();
            case 3:
                return price;
            case 4:
                return price * item.getAmount() * 1+(item.getTaxes()/100);

            default:
                return "Error";
        }
    }
}
