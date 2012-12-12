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
    
    private boolean deleted;

    private Supplier(int id, String name, String contact, String address, int zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String taxnumber, String pricecode, String notes, boolean deleted) {
	super(id, Configuration.center().getDB_TABLE_SUP(), name, contact, address, zipcode, municipality, telephone, telephone2, cellphone, fax, email, taxnumber, pricecode, notes);
	this.deleted = deleted;
    }
    
    private Supplier(int id, SupplierModel s){
	super(id, Configuration.center().getDB_TABLE_SUP(), s.getFirm(), s.getContact(), s.getAddress(), s.getZipcode(), s.getMunicipality(), s.getTelephone(), s.getTelephone2(), s.getCellphone(), s.getFax(), s.getEmail(), s.getTaxnumber(), s.getPricecode(), s.getNotes());
	this.deleted = false;
    }
    
    /**
     * Copy constructor.
     * @param s 
     */
    public Supplier(Supplier s){
	super(s.getPrimaryKeyValue(), s.getTableName(), s.getName(), s.getContact(), s.getAddress(), s.getZipcode(), s.getMunicipality(), s.getTelephone(), s.getTelephone2(), s.getCellphone(), s.getFax(), s.getEmail(), s.getTaxnumber(), s.getPricecode(), s.getNotes());
	this.deleted = false;
    }
    
    public void copy(Supplier s){
	setName(s.getName());
	setAddress(s.getAddress());
	setMunicipality(s.getMunicipality());
	setTelephone(s.getTelephone());
        setTelephone2(s.getTelephone2());
	setCellphone(s.getCellphone());
	setFax(s.getFax());
	setEmail(s.getEmail());
	setNotes(s.getNotes());
	setContact(s.getContact());
        setZipcode(s.getZipcode());
	setTaxnumber(s.getTaxnumber());
	setPricecode(s.getPricecode());
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
    public static Supplier loadWithValues(int id, String name, String contact, String address, int zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String taxnumber, String pricecode, String notes, boolean deleted) {
	return new Supplier(id, name, contact, address, zipcode, municipality, telephone, telephone2, cellphone, fax, email, taxnumber, pricecode, notes, deleted);
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
    
    public boolean isDeleted() {
	return deleted;
    }

    public void setDeleted(boolean verwijderd) {
	this.deleted = verwijderd;
    }
    
    public String getFirm(){
	return getName();
    }
    
    @Override
    public String toString(){
	return StringTools.capitalize(getName());
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
		+ "adres = "+(getAddress().length()>0 ? "\""+ getAddress() +"\"":"NULL")+", "
		+ "postcode = "+ getZipcode() +", "
		+ "gemeente = "+(getMunicipality().length()>0 ?"\""+getMunicipality() +"\"":"NULL")+", "
		+ "tel = "+(getTelephone().length()>0? "\""+getTelephone() +"\"":"NULL")+", "
                + "tel2 = "+(getTelephone2().length()>0? "\""+getTelephone2() +"\"":"NULL")+", "
		+ "gsm = "+(getCellphone().length()>0? "\""+getCellphone() +"\"":"NULL")+", "
		+ "fax = "+(getFax().length()>0? "\""+getFax() +"\"":"NULL")+", "
		+ "email = "+(getEmail().length()>0? "\""+getEmail() +"\"":"NULL")+", "
		+ "opmerking = "+(getNotes().length()>0? "\""+getNotes() +"\"":"NULL")+", "
		+ "contactpersoon = "+(getContact().length()>0 ? "\""+getContact() +"\"":"NULL")+", "
		+ "btwnr = "+(getTaxnumber().length()>0 ? "\""+getTaxnumber() +"\"":"NULL")+", "
		+ "prijscode = "+(getPricecode()!=null ? "\""+getPricecode() +"\"":"NULL")+", "
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
}
