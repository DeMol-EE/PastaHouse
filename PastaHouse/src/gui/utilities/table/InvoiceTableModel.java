/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Warkst
 */
public class InvoiceTableModel extends AbstractTableModel{

    private String[][] data = new String[3][2];
    
    public InvoiceTableModel(){
	data[0][0] = "lol";
	data[0][1] = "copter";
	data[1][0] = "derp";
	data[1][1] = "herp";
	data[2][0] = "superstar";
	data[2][1] = "dj";
    }

    @Override
    public String getColumnName(int column) {
	return column==0? "Datum" : "Klant";
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
	return false;
    }
    
    @Override
    public int getRowCount() {
	return 3;
    }

    @Override
    public int getColumnCount() {
	return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	return data[rowIndex][columnIndex];
    }
    
}
