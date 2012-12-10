/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.extra;

import database.Filterable;

/**
 *
 * @author Robin jr
 */
public abstract class Contact extends Record implements Filterable{
    
    /*
     * Test fields
     */
    private final String name;
    private final String address;
    private final String municipality;
    private final String telephone;
    private final String cellphone;
    private final String email;

    public Contact(int id, String table, String name, String address, String municipality, String telephone, String cellphone, String email){
	super(id, table);
	this.name = name;
	this.address = address;
	this.municipality = municipality;
	this.telephone = telephone;
	this.cellphone = cellphone;
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public String getAddress() {
	return address;
    }

    public String getMunicipality() {
	return municipality;
    }

    public String getTelephone() {
	return telephone;
    }

    public String getCellphone() {
	return cellphone;
    }

    public String getEmail() {
	return email;
    }

    @Override
    public boolean update() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete() {
	throw new UnsupportedOperationException("Not supported yet.");
    }
}
