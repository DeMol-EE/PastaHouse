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
    private String name;
    private String address;
    private String municipality;
    private String telephone;
    private String cellphone;
    private String email;

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

    public void setName(String name) {
	this.name = name;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public void setMunicipality(String municipality) {
	this.municipality = municipality;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
    }

    public void setEmail(String email) {
	this.email = email;
    }
    
    public abstract String getType();
    
    public abstract boolean isSupplier();

    @Override
    public String filterable(){
	return getType()+";"+name+";"+address+";"+municipality+";"+telephone+";"+cellphone+";"+email;
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
