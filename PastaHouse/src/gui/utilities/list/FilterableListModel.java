/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import database.Filterable;
import database.tables.Recipe;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.AbstractListModel;

/**
 *
 * @author Warkst
 */
public class FilterableListModel<Type extends Filterable> extends AbstractListModel<Type> {
    private final Map<String, Type> persistentData;
    private Map<String, Type> filteredData;
    private String filter;
    
    public FilterableListModel(Map<String, Type> data){
	this.persistentData = data;
	this.filteredData = new TreeMap<String, Type>(data);
	this.filter = null;
    }
    
    public void edit(Type newObj, Type oldObj) {
	if (persistentData.get(oldObj.getFilterKey())!=null) {
	    persistentData.remove(oldObj.getFilterKey());
	    persistentData.put(newObj.getFilterKey(), newObj);
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
    public Type getElementAt(int index) {
	return filter == null ? (Type)persistentData.values().toArray()[index] : (Type)filteredData.values().toArray()[index];
    }
    
    private void filterData(){
	if (filter == null) {
	    filteredData = new TreeMap<String, Type>(persistentData);
	} else {
	    filteredData = new TreeMap<String, Type>();
	    for (Type type : persistentData.values()) {
		if (type.filterable().toLowerCase().contains(filter.toLowerCase())) {
		    filteredData.put(type.getFilterKey(), type);
		}
	    }
	}
    }
}
