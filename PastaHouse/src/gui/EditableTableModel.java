/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import database.Component;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Robin jr
 */
public class EditableTableModel extends AbstractTableModel {

    private Map<Integer, Component> data;
    
    public EditableTableModel(Map<Integer, Component> data){
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
    public Object getValueAt(int rowIndex, int columnIndex) {
	return data.get(rowIndex);
    }
    
    @Override
    public String getColumnName(int column) {
	return "Derp";
    }

    // add a custom cell renderer for Double.class type to use proper formatting
    // add a custom cell renderer for Ingredient type (with hyperlink)
    @Override
    public Class<?> getColumnClass(int columnIndex) {
	return JButton.class;
    }
}
