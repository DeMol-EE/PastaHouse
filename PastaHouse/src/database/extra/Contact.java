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
    private String firm;
    private String contact;
    private String address;
    private int zipcode;
    private String municipality;
    private String telephone;
    private String telephone2;
    private String cellphone;
    private String fax;
    private String email;
    private String taxnumber;
    private String pricecode;
    private String notes;

    public Contact(int id, String table, String firm, String contact, String address, int zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String taxnumber, String pricecode, String notes){
	super(id, table);
	this.firm = firm;
	this.contact = contact;
	this.address = address;
	this.zipcode = zipcode;
	this.municipality = municipality;
	this.telephone = telephone;
	this.telephone2 = telephone2;
	this.cellphone = cellphone;
	this.fax = fax;
	this.email = email;
	this.taxnumber = taxnumber;
	this.pricecode = pricecode;
	this.notes = notes;
    }

    public String getFirm() {
	return firm;
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

    public void setFirm(String firm) {
	this.firm = firm;
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

    public String getContact() {
	return contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public int getZipcode() {
	return zipcode;
    }

    public void setZipcode(int zipcode) {
	this.zipcode = zipcode;
    }

    public String getTelephone2() {
	return telephone2;
    }

    public void setTelephone2(String telephone2) {
	this.telephone2 = telephone2;
    }

    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getTaxnumber() {
	return taxnumber;
    }

    public void setTaxnumber(String taxnumber) {
	this.taxnumber = taxnumber;
    }

    public String getPricecode() {
	return pricecode;
    }

    public void setPricecode(String pricecode) {
	this.pricecode = pricecode;
    }

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
    }
    
    public abstract String getType();
    
    public abstract boolean isSupplier();

    @Override
    public String filterable(){
	return getType()+";"+firm+";"+address+";"+municipality+";"+telephone+";"+cellphone+";"+email+";"+zipcode+";"+telephone2+";"+fax+";"+notes+";"+contact;
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
