/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import database.tables.Contact;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.AbstractListModel;

/**
 *
 * @author Warkst
 */
public class ContactListModel extends AbstractListModel{

    private final Map<String, Contact> persistentData;
    private Map<String, Contact> filteredData;
    private String filter;
    
    public ContactListModel(Map<String, Contact> data){
	this.persistentData = data;
	this.filteredData = new TreeMap<String, Contact>(data);
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
	filterData();
	fireContentsChanged(this, 0, filteredData.size());
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
    
    public void edit(Contact newObj, Contact oldObj) {
	if (persistentData.get(oldObj.getSortKey())!=null) {
	    persistentData.remove(oldObj.getFirm());
	    persistentData.put(newObj.getSortKey(), newObj);
	}
	fireContentsChanged(this, 0, getSize());
    }

    private void filterData(){
	if (filter == null) {
	    filteredData = new TreeMap<String, Contact>(persistentData);
	} else {
	    filteredData = new TreeMap<String, Contact>();
	    for (Contact contact : persistentData.values()) {
		if (contact.filterable().toLowerCase().contains(filter.toLowerCase())) {
		    if (contact.isSupplier()) {
			filteredData.put(contact.getFirm(), contact);
		    } else {
			filteredData.put(contact.getContact(), contact);
		    }
		}
	    }
	}
    }
}
