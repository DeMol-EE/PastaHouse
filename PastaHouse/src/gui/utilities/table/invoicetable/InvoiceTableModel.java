/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table.invoicetable;

import database.tables.Invoice;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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

    public boolean removeInvoiceAtRows(int[] rows) {
        List<Invoice> invoicesToRemove = new ArrayList<Invoice>();
        for (int row : rows) {
            invoicesToRemove.add(getInvoiceAtRow(row));
        }

        int removed = 0;

        for (Invoice invoice : invoicesToRemove) {
            if (invoice.delete()) {
                removed++;
            } else {
                break;
            }
        }

        fireTableDataChanged();

        return removed == rows.length;
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

    public Invoice getInvoice(int id) {
        return data.get(id);
    }

    @Override
    public Class getColumnClass(int col) {
        switch (col) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Date.class;
            case 3:
                return Boolean.class;
            default:
                return Object.class;
        }
    }
}
