/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table;

import database.extra.Component;
import database.extra.Ingredient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Robin jr
 */
public class EditableRecipeTableModel extends AbstractTableModel implements Reorderable {

    private Map<Integer, Component> data;
    
    public EditableRecipeTableModel(Map<Integer, Component> data){
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
    public Object getValueAt(int rowIndex, int columnIndex) {
	switch(columnIndex){
	    case 0:
		return ((Component)data.values().toArray()[rowIndex]).getIngredient();
	    case 1:
		return ((Component)data.values().toArray()[rowIndex]).getQuantity();
	    case 2:
		return ((Component)data.values().toArray()[rowIndex]).getPieces();
	    default:
		return "<ERROR>";
	}
    }
    
    @Override
    public String getColumnName(int column) {
	switch(column){
	    case 0:
		return "Ingredient";
	    case 1:
		return "Hoeveelheid";
	    case 2:
		return "Stuks";
	    default:
		return "<ERROR>";
	}
    }

    // add a custom cell renderer for Double.class type to use proper formatting
    // add a custom cell renderer for Ingredient type (with hyperlink)
    @Override
    public Class<?> getColumnClass(int columnIndex) {
	switch(columnIndex){
	    case 0:
		return Ingredient.class;
	    case 1:
		return Double.class;
	    case 2:
		return Component.class;
	    default:
		return Object.class;
	}
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
	return col!=2;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
	System.out.println("Called 'set value' at "+row+", "+col+": "+value);
	try{
	    switch(col){
		case 0:
		    if (value instanceof String) {
			((Component)data.values().toArray()[row]).setIngredient(null);
		    } else {
			((Component)data.values().toArray()[row]).setIngredient((Ingredient)value);
		    }
		    break;
		case 1:
		    ((Component)data.values().toArray()[row]).setQuantity(Double.parseDouble(value.toString()));
		    break;
    //	    case 2:
    //		// nothing
    //		break;
		default:
		    break;
	    }

	    fireTableCellUpdated(row, col);
	} catch (Exception e){
	    System.err.println("Something went wrong setting the value at "+row+", "+col+":\n"+e.getMessage());
	}
    }
    
    public void removeRow(int row){
	data.remove((Integer)data.keySet().toArray()[row]);
	
	Map<Integer, Component> temp = new TreeMap<Integer, Component>();
	int expected = 1;
	for (Component component : data.values()) {
	    if(component.getRank()>expected){
		component.setRank(expected);
	    }
	    temp.put(expected, component);
	    expected++;
	}
	
	data = temp;
	
	fireTableRowsDeleted(row, row);
    }
    
    public void addRow(){
	Component c = new Component();
	c.setQuantity(0.0);
	int key = 1;
	if(data.size()>0) {
	    key = Collections.max(data.keySet())+1;
	}
	
	c.setRank(key);
//	c.setIngredient((Ingredient)Database.driver().getIngredients().values().toArray()[0]);
	c.setIngredient(null);
	data.put(key, c);
	
	fireTableRowsInserted(0, data.size());
    }

    @Override
    public void reorder(int fromIndex, int toIndex) {
	ArrayList<Component> list = new ArrayList(data.values());
	Component from = list.remove(fromIndex);
	if (toIndex>fromIndex) {
	    toIndex--;
	}
	list.add(toIndex, from);
	
	int rank = 1;
	Map<Integer, Component> temp = new TreeMap<Integer, Component>(data);
	for (Component component : list) {
	    component.setRank(rank);
	    temp.put(rank, component);
	    rank++;
	}
	
	data = temp;
	fireTableDataChanged();
    }
}
