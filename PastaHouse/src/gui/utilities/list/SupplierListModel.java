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
public class SupplierListModel extends AbstractListModel {

    private Map<String, Contact> data;
    private Map<String, Contact> suppliers;

    public SupplierListModel(Map<String, Contact> data){
	this.data = data;
	this.suppliers = new TreeMap<String, Contact>();
	
	update();
    }
    
    public void update() {
	suppliers = new TreeMap<String, Contact>();
	for (Contact contact : data.values()) {
	    if (contact.isSupplier()) {
		suppliers.put(contact.getSortKey(), contact);
	    }
	}
	fireContentsChanged(this, 0, getSize());
    }

    @Override
    public int getSize() {
	return suppliers.size();
    }

    @Override
    public Object getElementAt(int index) {
	return suppliers.values().toArray()[index];
    }

    public void add(Contact o) {
	data.put(o.getSortKey(), o);
	update();
    }

    public void edit(Contact newObj, Contact oldObj) {
	if (data.get(oldObj.getSortKey()) != null) {
	    data.remove(oldObj.getFirm());
	    data.put(newObj.getSortKey(), newObj);
//	    System.out.println("Removed "+oldObj.getSortKey()+", added "+newObj.getSortKey());
	}
	update();
    }
}
