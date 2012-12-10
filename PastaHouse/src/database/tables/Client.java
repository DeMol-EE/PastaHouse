/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.extra.Contact;

/**
 *
 * @author Robin jr
 */
public class Client extends Contact{
    
    private String name;
    
    public Client(String name){
	super(-1, "clients", name, "address", "municipality", "telephone", "cellphone", "email");
    }
    
    @Override
    public String toString() {
	return name;
    }

    @Override
    public String getType() {
	return "Klant";
    }

    @Override
    public boolean isSupplier() {
	return false;
    }

    @Override
    public String filterable() {
	return super.filterable();
    }
}
