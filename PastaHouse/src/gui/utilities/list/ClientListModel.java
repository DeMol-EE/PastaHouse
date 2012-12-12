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
public class ClientListModel extends AbstractListModel{
    private Map<String, Contact> data;
    private Map<String, Contact> clients;
	  
    public ClientListModel(Map<String, Contact> data){
	this.data = data;
	this.clients = new TreeMap<String, Contact>();
	
	update();
    }
    
    public void update() {
	clients = new TreeMap<String, Contact>();
	for (Contact contact : data.values()) {
	    if (!contact.isSupplier()) {
		clients.put(contact.getSortKey(), contact);
	    }
	}
	fireContentsChanged(this, 0, getSize());
    }

    @Override
    public int getSize() {
	return clients.size();
    }

    @Override
    public Object getElementAt(int index) {
	return clients.values().toArray()[index];
    }

    public void add(Contact o){
	data.put(o.getSortKey(), o);
	fireContentsChanged(this, 0, getSize());
    }

    public void edit(Contact newObj, Contact oldObj) {
	if (data.get(oldObj.getSortKey())!=null) {
	    data.remove(oldObj.getFirm());
	    data.put(newObj.getSortKey(), newObj);
	}
	fireContentsChanged(this, 0, getSize());
    }
}
