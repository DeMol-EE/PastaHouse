/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import database.BasicIngredient;
import database.Component;
import database.Recipe;
import database.Supplier;
import java.util.Map;
import javax.swing.AbstractListModel;

/**
 *
 * @author Warkst
 */
public class ListModelFactory {
    public static AbstractListModel createBasicIngredientModel(final Map<Integer, BasicIngredient> data){
	return new EditableListModel<BasicIngredient>() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	    @Override
	    public void add(BasicIngredient o){
		data.put(o.getPrimaryKeyValue(), o);
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
    
    public static AbstractListModel createSupplierListModel(final Map<Integer, Supplier> data){
	return new EditableListModel<Supplier>() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	    @Override
	    public void add(Supplier o){
		data.put(o.getPrimaryKeyValue(), o);
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
    
    public static AbstractListModel createRecipeListModel(final Map<Integer, Recipe> data){
	return new EditableListModel<Recipe>() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	    @Override
	    public void add(Recipe o){
		data.put(o.getPrimaryKeyValue(), o);
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
    
    public static AbstractListModel createComponentListModel(final Map<Integer, Component> data){
	return new EditableListModel<Component>() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	    @Override
	    public void add(Component o){
		data.put(o.getRank(), o);
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
}
