/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import gui.Utilities;

/**
 *
 * @author Warkst
 */
public class Supplier extends Record{
    private final String table_id = Configuration.center().getDB_TABLE_SUP();
    
    private String firm;
    private String address;
    private String municipality;
    private String telephone;
    private String cellphone;
    private String fax;
    private String email;
    private String notes;
    private String contact;
    
    private boolean verwijderd;

    public Supplier(int id, String firm, String address, String municipality, String telephone, String cellphone, String fax, String email, String notes, String contact, boolean verwijderd) {
	super(id);
	this.firm = firm;
	this.address = address;
	this.municipality = municipality;
	this.telephone = telephone;
	this.cellphone = cellphone;
	this.fax = fax;
	this.email = email;
	this.notes = notes;
	this.contact = contact;
	this.verwijderd = verwijderd;
    }
    
    public static Supplier loadWithValues(int id, String firm, String address, String municipality, String telephone, String cellphone, String fax, String email, String notes, String contact, boolean verwijderd) {
	return new Supplier(id, firm, address, municipality, telephone, cellphone, fax, email, notes, contact, verwijderd);
    }
    
    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCellphone() {
	return cellphone;
    }

    public void setCellphone(String cellphone) {
	this.cellphone = cellphone;
    }

    public String getContact() {
	return contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getFirm() {
	return firm;
    }

    public void setFirm(String firm) {
	this.firm = firm;
    }

    public String getMunicipality() {
	return municipality;
    }

    public void setMunicipality(String municipality) {
	this.municipality = municipality;
    }

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
    }

    public String getTelephone() {
	return telephone;
    }

    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    public boolean isVerwijderd() {
	return verwijderd;
    }

    public void setVerwijderd(boolean verwijderd) {
	this.verwijderd = verwijderd;
    }
    
    @Override
    public String toString(){
	return Utilities.capitalize(firm);
    }

    @Override
    public void create() throws Exception {
	Database.driver().executeInsert(table_id, 
		"firma = "+ firm +", "
		+ "adres = "+ address +", "
		+ "gemeente = "+municipality +", "
		+ "tel = "+telephone+", "
		+ "gsm = "+cellphone+", "
		+ "fax = "+fax+", "
		+ "email = "+email+", "
		+ "opmerking = "+notes+", "
		+ "contactpersoon = "+contact+", "
		+ "verwijderd = 0");
    }

    @Override
    public void update() throws Exception {
	
    }

    @Override
    public void delete() throws Exception {
	
    }
}