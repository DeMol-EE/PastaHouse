/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table;

import database.extra.Component;
import database.extra.Ingredient;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Warkst
 */
public class StaticRecipeTableModel extends AbstractTableModel{

    private final Map<Integer, Component> data;
    
    public StaticRecipeTableModel(Map<Integer, Component> data){
	this.data = data;
    }
    
    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public int getColumnCount() {
	return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	switch(columnIndex){
	    case 0:
		return ((Component)data.values().toArray()[rowIndex]).getIngredient().getName();
	    case 1:
		return ((Component)data.values().toArray()[rowIndex]).isIngredientType();
	    case 2:
		return ((Component)data.values().toArray()[rowIndex]).getQuantity();
	    case 3:
//		return ((Component)data.values().toArray()[rowIndex]).getUnits();
		return ((Component)data.values().toArray()[rowIndex]).getFormattedUnits();
	    default:
		return "<empty>";
	}
    }

    @Override
    public String getColumnName(int column) {
	switch(column){
	    case 0:
		return "Naam";
	    case 1:
		return "Type";
	    case 2:
		return "Kg";
	    case 3:
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
		return String.class;
	    case 1:
		return Ingredient.class;
	    case 2:
		return Component.class;
	    case 3:
		return Double.class;
//		return String.class;
	    default:
		return Object.class;
	}
    }
}
