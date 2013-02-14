/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import database.tables.Recipe;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.AbstractListModel;

/**
 *
 * @author Warkst
 */
public class RecipeListModel extends AbstractListModel {
    private final Map<String, Recipe> persistentData;
    private Map<String, Recipe> filteredData;
    private String filter;
    
    public RecipeListModel(Map<String, Recipe> data){
	this.persistentData = data;
	this.filteredData = new TreeMap<String, Recipe>(data);
	this.filter = null;
    }
    
    public void edit(Recipe newObj, Recipe oldObj) {
	if (persistentData.get(oldObj.getName())!=null) {
	    persistentData.remove(oldObj.getName());
	    persistentData.put(newObj.getName(), newObj);
	}
	update();
    }
    
    /**
     * Call to manually trigger the model to remake itself.
     */
    public void update(){
	filterData();
	fireContentsChanged(this, 0, filteredData.size());
    }
    
    /**
     * Sets a filter on the data being displayed. This will not modify the underlying data, only filter the results. Set to <code>null</code> to disable filtering.
     * 
     * @param filter The new filter to be applied, or <code>null</code> to disable filtering.
     */
    public void setFilter(String filter){
	this.filter = filter;
	filterData();
	fireContentsChanged(this, 0, filteredData.size());
    }
    
    @Override
    public int getSize() {
	return filter==null ? persistentData.size() : filteredData.size();
    }

    @Override
    public Object getElementAt(int index) {
	return filter == null ? persistentData.values().toArray()[index] : filteredData.values().toArray()[index];
    }
    
    private void filterData(){
	if (filter == null) {
	    filteredData = new TreeMap<String, Recipe>(persistentData);
	} else {
	    filteredData = new TreeMap<String, Recipe>();
	    for (Recipe r : persistentData.values()) {
		if (r.filterable().toLowerCase().contains(filter.toLowerCase())) {
		    filteredData.put(r.getName(), r);
		}
	    }
	}
    }
}
