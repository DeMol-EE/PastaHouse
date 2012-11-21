/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import utilities.Configuration;
import utilities.StringTools;
import utilities.Utilities;

/**
 *
 * @author Warkst
 */
public class Supplier implements Record{
    private final String table_id = Configuration.center().getDB_TABLE_SUP();
    
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
    
    private boolean deleted;

    private Supplier(String firm, String address, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String notes, String contact, boolean deleted) {
	this.firm = firm;
	this.address = address;
	this.municipality = municipality;
	this.telephone = telephone;
	this.cellphone = cellphone;
	this.fax = fax;
	this.email = email;
	this.notes = notes;
	this.contact = contact;
	this.deleted = deleted;
        this.telephone2 = telephone2;
    }
    
    public Supplier(Supplier s){
	this.firm = s.getFirm();
	this.address = s.getAddress();
	this.municipality = s.getMunicipality();
	this.telephone = s.getTelephone();
        this.telephone2 = s.getTelephone2();
	this.cellphone = s.getCellphone();
	this.fax = s.getFax();
	this.email = s.getEmail();
	this.notes = s.getNotes();
	this.contact = s.getContact();
	this.deleted = s.isDeleted();
    }
    
    public void load(Supplier s){
	this.firm = s.getFirm();
	this.address = s.getAddress();
	this.municipality = s.getMunicipality();
	this.telephone = s.getTelephone();
        this.telephone2 = s.getTelephone2();
	this.cellphone = s.getCellphone();
	this.fax = s.getFax();
	this.email = s.getEmail();
	this.notes = s.getNotes();
	this.contact = s.getContact();
	this.deleted = s.isDeleted();
    }
    
    public static Supplier loadWithValues(String firm, String address, String municipality, String telephone,String telephone2, String cellphone, String fax, String email, String notes, String contact, boolean verwijderd) {
	return new Supplier(firm, address, municipality, telephone, telephone2, cellphone, fax, email, notes, contact, verwijderd);
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

    public boolean isDeleted() {
	return deleted;
    }

    public void setVerwijderd(boolean verwijderd) {
	this.deleted = verwijderd;
    }
    
    @Override
    public String toString(){
	return StringTools.capitalize(firm);
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }
    
    

    @Override
    public boolean create() {
	return Database.driver().executeInsert(table_id, 
		"firma = "+ firm +", "
		+ "adres = "+ address +", "
		+ "gemeente = "+municipality +", "
		+ "tel = "+telephone+", "
                + "tel = "+telephone2+", "
		+ "gsm = "+cellphone+", "
		+ "fax = "+fax+", "
		+ "email = "+email+", "
		+ "opmerking = "+notes+", "
		+ "contactpersoon = "+contact+", "
		+ "verwijderd = 0");
    }

    /**
     * Tells the database object proxy to synchronize the database with the actual values. This will
     * cause an UPDATE command saving the current values to the database.
     * 
     * @return <code>true</code> if the UPDATE was succesful, <code>false</code> if it failed.
     */
    @Override
    public boolean update() {
	return Database.driver().executeUpdate(table_id, getPrimaryKey(), firm,  
		"firma = \""+ firm +"\", "
		+ "adres = "+(address.length()>0 ? "\""+ address +"\"":"NULL")+", "
		+ "gemeente = "+(municipality.length()>0 ?"\""+municipality +"\"":"NULL")+", "
		+ "tel = "+(telephone.length()>0? "\""+telephone +"\"":"NULL")+", "
                + "tel2 = "+(telephone2.length()>0? "\""+telephone2 +"\"":"NULL")+", "
		+ "gsm = "+(cellphone.length()>0? "\""+cellphone +"\"":"NULL")+", "
		+ "fax = "+(fax.length()>0? "\""+fax +"\"":"NULL")+", "
		+ "email = "+(email.length()>0? "\""+email +"\"":"NULL")+", "
		+ "opmerking = "+(notes.length()>0? "\""+notes +"\"":"NULL")+", "
		+ "contactpersoon = "+(contact.length()>0 ? "\""+contact +"\"":"NULL")+", "
		+ "verwijderd = 0");
    }

    @Override
    public boolean delete() {
	return false;
    }
    
    @Override
    public String getPrimaryKey(){
	return "firma";
    }
    
    @Override
    public String getPrimaryKeyValue(){
	return firm;
    }
}
