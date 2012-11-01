/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import database.BasicIngredient;
import database.Supplier;
import java.util.Map;
import javax.swing.AbstractListModel;

/**
 *
 * @author Warkst
 */
public class ListModelFactory {
    public static AbstractListModel createBasicIngredientModel(final Map<String, BasicIngredient> data){
	return new AbstractListModel() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	    public void add(BasicIngredient o){
		data.put(o.getName(), o);
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
    
    public static AbstractListModel createSupplierListModel(final Map<String, Supplier> data){
	return new AbstractListModel() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	    public void add(Supplier o){
		data.put(o.getFirm(), o);
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
}
