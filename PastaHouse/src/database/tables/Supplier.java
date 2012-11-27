/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.Database;
import database.extra.Record;
import database.models.SupplierModel;
import utilities.Configuration;
import utilities.StringTools;

/**
 *
 * @author Warkst
 */
public class Supplier extends Record{
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
    
    private boolean deleted;

    private Supplier(int id, String firm, String address, int zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String notes, String contact, boolean deleted) {
	super(id, Configuration.center().getDB_TABLE_SUP());
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
        this.zipcode = zipcode;
    }
    
    private Supplier(int id, SupplierModel s){
	super(id, Configuration.center().getDB_TABLE_SUP());
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
	this.deleted = false;
        this.zipcode = s.getZipcode();
    }
    
    /**
     * Copy constructor.
     * @param s 
     */
    public Supplier(Supplier s){
	super(s.getPrimaryKeyValue(), s.getTableName());
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
	this.deleted = false;
        this.zipcode = s.getZipcode();
    }
    
    public void copy(Supplier s){
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
        this.zipcode = s.getZipcode();
    }
    
    /**
     * Creates a Supplier object mirroring a database record.
     * 
     * @param id
     * @param firm
     * @param address
     * @param municipality
     * @param zipcode
     * @param telephone
     * @param telephone2
     * @param cellphone
     * @param fax
     * @param email
     * @param notes
     * @param contact
     * @param verwijderd
     * @return 
     */
    public static Supplier loadWithValues(int id, String firm, String address, String municipality, int zipcode, String telephone,String telephone2, String cellphone, String fax, String email, String notes, String contact, boolean verwijderd) {
	return new Supplier(id, firm, address, zipcode, municipality, telephone, telephone2, cellphone, fax, email, notes, contact, verwijderd);
    }
    
    /**
     * Creates a Supplier object mirroring a database record from a SupplierModel.
     * 
     * @param id
     * @param s
     * @return 
     */
    public static Supplier createFromModel(int id, SupplierModel s){
	return new Supplier(id, s);
    }
    
    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
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

    /**
     * Tells the database object proxy to synchronize the database with the actual values. This will
     * cause an UPDATE command saving the current values to the database.
     * 
     * @return <code>true</code> if the UPDATE was successful, <code>false</code> if it failed.
     */
    @Override
    public boolean update() {
	return Database.driver().executeUpdate(getTableName(), getPrimaryKey(), getPrimaryKeyValue(),  
		"firma = \""+ firm +"\", "
//		"firma = "+(firm.length()>0 ? "\""+ firm +"\"":"NULL")+", "
		+ "adres = "+(address.length()>0 ? "\""+ address +"\"":"NULL")+", "
		+ "postcode = "+ zipcode +", "
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
}
