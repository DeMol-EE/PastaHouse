/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.Database;
import database.extra.Contact;
import database.models.SupplierModel;
import tools.Configuration;
import tools.StringTools;

/**
 *
 * @author Warkst
 */
public class Supplier extends Contact{
    private String telephone2;
    private String fax;
    private String notes;
    private String contact;
    private int zipcode;
    
    private boolean deleted;

    private Supplier(int id, String firm, String address, int zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String notes, String contact, boolean deleted) {
	super(id, Configuration.center().getDB_TABLE_SUP(), firm, address, municipality, telephone, cellphone, email);
	this.fax = fax;
	this.notes = notes;
	this.contact = contact;
        this.telephone2 = telephone2;
        this.zipcode = zipcode;
	this.deleted = deleted;
    }
    
    private Supplier(int id, SupplierModel s){
	super(id, Configuration.center().getDB_TABLE_SUP(), s.getFirm(), s.getAddress(), s.getMunicipality(), s.getTelephone(), s.getCellphone(), s.getEmail());
	this.telephone2 = s.getTelephone2();
	this.fax = s.getFax();
	this.notes = s.getNotes();
	this.contact = s.getContact();
        this.zipcode = s.getZipcode();
	this.deleted = false;
    }
    
    /**
     * Copy constructor.
     * @param s 
     */
    public Supplier(Supplier s){
	super(s.getPrimaryKeyValue(), s.getTableName(), s.getFirm(), s.getAddress(), s.getMunicipality(), s.getTelephone(), s.getCellphone(), s.getEmail());
	this.telephone2 = s.getTelephone2();
	this.fax = s.getFax();
	this.notes = s.getNotes();
	this.contact = s.getContact();
        this.zipcode = s.getZipcode();
	this.deleted = false;
    }
    
    public void copy(Supplier s){
	setFirm(s.getFirm());
	setAddress(s.getAddress());
	setMunicipality(s.getMunicipality());
	setTelephone(s.getTelephone());
        this.telephone2 = s.getTelephone2();
	setCellphone(s.getCellphone());
	this.fax = s.getFax();
	setEmail(s.getEmail());
	this.notes = s.getNotes();
	this.contact = s.getContact();
        this.zipcode = s.getZipcode();
	this.deleted = s.isDeleted();
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
    
    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getContact() {
	return contact;
    }

    public void setContact(String contact) {
	this.contact = contact;
    }

    public String getFax() {
	return fax;
    }

    public void setFax(String fax) {
	this.fax = fax;
    }

    public String getFirm() {
	return getName();
    }

    public void setFirm(String firm) {
	setName(firm);
    }

    public String getNotes() {
	return notes;
    }

    public void setNotes(String notes) {
	this.notes = notes;
    }

    public boolean isDeleted() {
	return deleted;
    }

    public void setVerwijderd(boolean verwijderd) {
	this.deleted = verwijderd;
    }
    
    @Override
    public String toString(){
	return StringTools.capitalize(getName());
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
		"firma = \""+ getName() +"\", "
//		"firma = "+(firm.length()>0 ? "\""+ firm +"\"":"NULL")+", "
		+ "adres = "+(getAddress().length()>0 ? "\""+ getAddress() +"\"":"NULL")+", "
		+ "postcode = "+ zipcode +", "
		+ "gemeente = "+(getMunicipality().length()>0 ?"\""+getMunicipality() +"\"":"NULL")+", "
		+ "tel = "+(getTelephone().length()>0? "\""+getTelephone() +"\"":"NULL")+", "
                + "tel2 = "+(telephone2.length()>0? "\""+telephone2 +"\"":"NULL")+", "
		+ "gsm = "+(getCellphone().length()>0? "\""+getCellphone() +"\"":"NULL")+", "
		+ "fax = "+(fax.length()>0? "\""+fax +"\"":"NULL")+", "
		+ "email = "+(getEmail().length()>0? "\""+getEmail() +"\"":"NULL")+", "
		+ "opmerking = "+(notes.length()>0? "\""+notes +"\"":"NULL")+", "
		+ "contactpersoon = "+(contact.length()>0 ? "\""+contact +"\"":"NULL")+", "
		+ "verwijderd = 0");
    }
    
    @Override
    public String getType(){
	return "Leverancier";
    }
    
    @Override
    public boolean isSupplier(){
	return true;
    }

    @Override
    public boolean delete() {
	return false;
    }
    
    @Override
    public String filterable() {
	return super.filterable()+";"+zipcode+";"+telephone2+";"+fax+";"+notes+";"+contact;
    }
}
