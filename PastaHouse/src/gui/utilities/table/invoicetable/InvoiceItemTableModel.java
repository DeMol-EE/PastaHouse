/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table.invoicetable;

import database.extra.InvoiceItem;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Warkst
 */
public class InvoiceItemTableModel extends AbstractTableModel {

	private ArrayList<InvoiceItem> data;
	private String pricecode;
	private final boolean editable;
	private final InvoiceItemTableModelDelegate delegate;

	public InvoiceItemTableModel(ArrayList<InvoiceItem> data, String pricecode) {
		this(data, pricecode, null, false);
	}

	public InvoiceItemTableModel(ArrayList<InvoiceItem> data, String pricecode, InvoiceItemTableModelDelegate delegate, boolean editable) {
		this.data = data;
		this.pricecode = pricecode;
		this.editable = editable;
		this.delegate = delegate;
	}

	public void setData(ArrayList<InvoiceItem> data, String pricecode) {
		this.data = data;
		this.pricecode = pricecode;
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "Artikel";
			case 1:
				return "LOT";
			case 2:
				return "BTW";
			case 3:
				return "Hoeveelheid";
			case 4:
				return "Prijs";
			case 5:
				return "Totaal";
			default:
				return "ERROR";
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (!editable) {
			return false;
		}
		return col == 3 || col == 1;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
//	System.out.println("Called 'set value' at "+row+", "+col+": "+value);
		try {
			if (col == 3) {
				double v = Double.parseDouble((String) value);
				if (v >= 0) {
					InvoiceItem ii = data.get(row);
					ii.setAmount(v);
					fireTableCellUpdated(row, col);
					fireTableCellUpdated(row, 5);
					delegate.dataChanged();
				}
			} else if (col == 1) {
				data.get(row).setLot((String)value);
				fireTableCellUpdated(row, col);
				delegate.dataChanged();
			}
		} catch (Exception e) {
			System.err.println("Something went wrong setting the value at " + row + ", " + col + ":\n" + e.getMessage());
		}
	}

	public void addComponent(InvoiceItem item) {
		fireTableRowsInserted(0, data.size() - 1);
	}

	public void updatePricecode(String pricecode) {
		this.pricecode = pricecode;
		for (InvoiceItem item : data) {
			item.setPrice(item.getArticle().getPriceForCode(pricecode));
		}
		fireTableDataChanged();
	}

	public String priceCode() {
		return pricecode;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		InvoiceItem item = (InvoiceItem) data.toArray()[rowIndex];
		double price = item.getPrice();
		switch (columnIndex) {
			case 0:
				return item.getArticlename();
			case 1:
				return item.getLot();
			case 2:
				return item.getTaxes();
			case 3:
				return item.getAmount();
			case 4:
				return price;
			case 5:
//                return price * item.getAmount() * (1 + (item.getArticle().getTaxes() / 100));
				return price * item.getAmount();

			default:
				return "Error";
		}
	}
}
