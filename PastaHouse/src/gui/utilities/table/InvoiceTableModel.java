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

    private String[][] data = new String[8][2];
    
    public InvoiceTableModel(){
	data[0][0] = "lol";
	data[0][1] = "copter";
	data[1][0] = "derp";
	data[1][1] = "herp";
	data[2][0] = "superstar";
	data[2][1] = "dj";
	data[3][0] = "dong";
	data[3][1] = "russian";
	data[4][0] = "pirate";
	data[4][1] = "new";
	data[5][0] = "gladiatar";
	data[5][1] = "mortal";
	data[6][0] = "combat";
	data[6][1] = "pistoletov";
	data[7][0] = "shuffle";
	data[7][1] = "melbourne";
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
	return data.length;
    }

    @Override
    public int getColumnCount() {
	return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	return data[rowIndex][columnIndex];
    }
    
}
