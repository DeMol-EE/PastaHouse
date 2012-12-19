/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table;

import database.tables.Invoice;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Warkst
 */
public class InvoiceTableModel extends AbstractTableModel {

    private final Map<Integer, Invoice> data;

    public InvoiceTableModel(Map<Integer, Invoice> data) {
        this.data = data;
    }

    public Invoice getInvoiceAtRow(int row) {
        return (row > 0 && row < data.size()) ? (Invoice) data.values().toArray()[row] : (Invoice) data.values().toArray()[0];
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nummer";
            case 1:
                return "Klant";
            case 2:
                return "Datum";
            default:
                return "ID";
        }
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        return false;
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return ((Invoice) data.values().toArray()[rowIndex]).getNumber();
            case 1:
                return ((Invoice) data.values().toArray()[rowIndex]).getClient().getSortKey();
            case 2:
                return ((Invoice) data.values().toArray()[rowIndex]).getDate();
            case 3:
                return ((Invoice) data.values().toArray()[rowIndex]).getPrimaryKeyValue();
            default:
                return "Error";
        }
    }

    public Invoice getInvoice(int number) {
        return data.get(number);
    }
}
