/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import database.Database;
import database.FunctionResult;
import database.tables.Supplier;
import java.sql.SQLException;

/**
 *
 * @author Warkst
 */
public class SupplierModel implements Model{
    private String firm;
    private String address;
    private String municipality;
    private String telephone;
    private String telephone2;
    private String cellphone;
    private String fax;
    private String email;
    private String notes;
    private String contact;
    private int zipcode;

    public SupplierModel(){
	
    }
    
    public String getFirm() {
	return firm;
    }

    public void setFirm(String firm) {
	this.firm = firm;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
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

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
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

    /**
     * Tries to create this Supplier in the database. Returns a FunctionResult with code 0 if successful (and a pointer to the new supplier) and any other code if not.
     */
    @Override
    public FunctionResult<Supplier> create(){
	try {
	    return Database.driver().addSupplier(this);
	} catch (SQLException ex) {
	    return new FunctionResult<Supplier>(3, null);
	}
        
    }
}
