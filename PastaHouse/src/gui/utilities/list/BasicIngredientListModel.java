/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import database.tables.BasicIngredient;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.AbstractListModel;

/**
 *
 * @author Warkst
 */
public class BasicIngredientListModel extends AbstractListModel{

    private final Map<String, BasicIngredient> persistentData;
    private Map<String, BasicIngredient> filteredData;
    private String filter;
    
    public BasicIngredientListModel(Map<String, BasicIngredient> data){
	this.persistentData = data;
	this.filteredData = new TreeMap<String, BasicIngredient>(data);
	this.filter = null;
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
	update();
    }
    
    @Override
    public int getSize() {
	return filter==null ? persistentData.size() : filteredData.size();
    }

    @Override
    public Object getElementAt(int index) {
	if (index > filteredData.size()) {
	    return null;
	} else {
	    return filter == null ? persistentData.values().toArray()[index] : filteredData.values().toArray()[index];
	}
    }
    
    public void edit(BasicIngredient newObj, BasicIngredient oldObj) {
	if (persistentData.get(oldObj.getName())!=null) {
	    persistentData.remove(oldObj.getName());
	    persistentData.put(newObj.getName(), newObj);
	}
	update();
    }

    private void filterData(){
	if (filter == null) {
	    filteredData = new TreeMap<String, BasicIngredient>(persistentData);
	} else {
	    filteredData = new TreeMap<String, BasicIngredient>();
	    for (BasicIngredient bi : persistentData.values()) {
		if (bi.filterable().toLowerCase().contains(filter.toLowerCase())) {
		    filteredData.put(bi.getName(), bi);
		}
	    }
	}
    }
}
