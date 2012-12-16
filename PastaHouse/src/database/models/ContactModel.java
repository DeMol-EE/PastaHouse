/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import database.FunctionResult;

/**
 *
 * @author Warkst
 */
public class ContactModel implements Model{

    private String sortkey;
    private String firm;
    private String contact;
    private String address;
    private String zipcode;
    private String municipality;
    private String telephone;
    private String telephone2;
    private String cellphone;
    private String fax;
    private String email;
    private String taxnumber;
    private String pricecode;
    private String notes;
    private String type;
    
    public ContactModel(){
	
    }
    
    public ContactModel(String type){
	this.type = type;
    }

    public String getFirm() {
	return firm;
    }

    public void setFirm(String firm) {
	this.firm = firm;
    }

    public String getContact() {
	return contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getZipcode() {
	return zipcode;
    }

    public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
    }

    public String getMunicipality() {
	return municipality;
    }

    public void setMunicipality(String municipality) {
	this.municipality = municipality;
    }

    public String getTelephone() {
	return telephone;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    public String getTelephone2() {
	return telephone2;
    }

    public void setTelephone2(String telephone2) {
	this.telephone2 = telephone2;
    }

    public String getCellphone() {
	return cellphone;
    }

    public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
    }

    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
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

    public String getSortKey() {
	return sortkey;
    }

    public void setSortKey(String sortkey) {
	this.sortkey = sortkey;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }
    
    public boolean isSupplier(){
	return type.equalsIgnoreCase("supplier");
    }
    
    @Override
    public FunctionResult create() {
	try{
	    return database.Database.driver().addContact(this);
	} catch (Exception e){
	    System.err.println("Error caught");
	    e.printStackTrace();
	    return new FunctionResult(3, null, e.getMessage());
	}
    }
    
}
