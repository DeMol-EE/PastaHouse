/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import database.extra.Component;
import database.tables.BasicIngredient;
import database.tables.Recipe;
import database.tables.Supplier;
import java.util.Map;
import javax.swing.AbstractListModel;

/**
 * 
 * @TODO: ALTER MODELS TO SORT ALPHABETICALLY!
 *
 * @author Warkst
 */
public class ListModelFactory {
    public static AbstractListModel createBasicIngredientModel(final Map<String, BasicIngredient> data){
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
		data.put(o.getName(), o);
		fireContentsChanged(this, 0, getSize());
	    }

	    @Override
	    public void edit(BasicIngredient newObj, BasicIngredient oldObj) {
		if (data.get(oldObj.getName()) != null) {
		    data.remove(oldObj.getName());
		    data.put(newObj.getName(), newObj);
		}
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
    
    public static AbstractListModel createSupplierListModel(final Map<String, Supplier> data){
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
		data.put(o.getFirm(), o);
		fireContentsChanged(this, 0, getSize());
	    }

	    @Override
	    public void edit(Supplier newObj, Supplier oldObj) {
		if (data.get(oldObj.getFirm())!=null) {
		    data.remove(oldObj.getFirm());
		    data.put(newObj.getFirm(), newObj);
		}
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
    
    public static AbstractListModel createRecipeListModel(final Map<String, Recipe> data){
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
		data.put(o.getName(), o);
		fireContentsChanged(this, 0, getSize());
	    }

	    @Override
	    public void edit(Recipe newObj, Recipe oldObj) {
		if (data.get(oldObj.getName())!=null) {
		    data.remove(oldObj.getName());
		    data.put(newObj.getName(), newObj);
		}
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

	    @Override
	    public void edit(Component newObj, Component oldObj) {
		if (data.get(oldObj.getRank())!=null) {
		    data.remove(oldObj.getRank());
		    data.put(newObj.getRank(), newObj);
		}
		fireContentsChanged(this, 0, getSize());
	    }
	};
    }
}
