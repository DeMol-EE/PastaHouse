/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import database.extra.Component;
import database.tables.Article;
import database.tables.BasicIngredient;
import database.tables.Contact;
import database.tables.Recipe;
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
    
    public static AbstractListModel createContactListModel(final Map<String, Contact> data){
	return new EditableListModel<Contact>() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	    @Override
	    public void add(Contact o){
		data.put(o.getSortKey(), o);
		fireContentsChanged(this, 0, getSize());
	    }

	    @Override
	    public void edit(Contact newObj, Contact oldObj) {
		if (data.get(oldObj.getSortKey())!=null) {
		    data.remove(oldObj.getFirm());
		    data.put(newObj.getSortKey(), newObj);
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
    
    public static AbstractListModel createArticleListModel(final Map<String, Article> data){
	return new EditableListModel<Article>(){

	    @Override
	    public void add(Article o) {
		data.put(o.getName(), o);
		fireContentsChanged(this, 0, getSize());
	    }

	    @Override
	    public void edit(Article newObj, Article oldObj) {
		if (data.get(oldObj.getName())!=null) {
		    data.remove(oldObj.getName());
		    data.put(newObj.getName(), newObj);
		}
		fireContentsChanged(this, 0, getSize());
	    }

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.values().toArray()[index];
	    }
	    
	};
    }
}
