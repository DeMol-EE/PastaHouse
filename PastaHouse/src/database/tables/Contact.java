/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.Filterable;
import database.FunctionResult;
import database.extra.Record;
import database.models.ContactModel;
import tools.Configuration;
import tools.StringTools;

/**
 *
 * @author Robin jr
 */
public class Contact extends Record<Contact> implements Filterable{
    
    public static final int both = 0;
    public static final int supplier = 1;
    public static final int client = 2;
    
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

    private Contact(int id, String sortKey, String firm, String contact, String address, String zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String taxnumber, String pricecode, String notes, String type){
	super(id, Configuration.center().getDB_TABLE_CON());
	this.sortkey = sortKey;
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
	this.type = type;
    }
    
    public Contact(Contact copy){
	this(copy.getPrimaryKeyValue(), copy.getSortKey(), copy.getFirm(), copy.getContact(), copy.getAddress(), copy.getZipcode(), copy.getMunicipality(), copy.getTelephone(), copy.getTelephone2(), copy.getCellphone(), copy.getFax(), copy.getEmail(), copy.getTaxnumber(), copy.getPricecode(), copy.getNotes(), copy.getType());
    }
    
    public static Contact loadWithValues(int id, String sortName, String firm, String contact, String address, String zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String taxnumber, String pricecode, String notes, String type){
	return new Contact(id, sortName, firm, contact, address, zipcode, municipality, telephone, telephone2, cellphone, fax, email, taxnumber, pricecode, notes, type);
    }
    
    public static Contact createFromModel(int id, ContactModel model){
	return new Contact(id, model.getSortKey(), model.getFirm(), model.getContact(), model.getAddress(), model.getZipcode(), model.getMunicipality(), model.getTelephone(), model.getTelephone2(), model.getCellphone(), model.getFax(), model.getEmail(), model.getTaxnumber(), model.getPricecode(), model.getNotes(), model.getType());
    }
    
    public void copy(Contact copy){
	firm = copy.getFirm();
	sortkey = copy.getSortKey();
	contact = copy.getContact();
	address = copy.getAddress();
	zipcode = copy.getZipcode(); 
	municipality = copy.getMunicipality();
	telephone = copy.getTelephone();
	telephone2 = copy.getTelephone2();
	cellphone = copy.getCellphone();
	fax = copy.getFax();
	email = copy.getEmail();
	taxnumber = copy.getTaxnumber();
	pricecode = copy.getPricecode(); 
	notes = copy.getNotes();
	type = copy.getType();
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

    public String getZipcode() {
	return zipcode;
    }

    public void setZipcode(String zipcode) {
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
    
    public void setType(String type){
	this.type = type;
    }
    
    public String getType(){
	return type;
    }
    
    public boolean isSupplier(){
	return type.equalsIgnoreCase("supplier");
    }
    
    public String getSortKey(){
	return sortkey;
	//return StringTools.capitalizeEach(isSupplier() ? firm : contact);
    }
    
    public void setSortKey(String sortKey){
	this.sortkey = sortKey;
    }
    
    @Override
    public String toString(){
	return StringTools.capitalizeEach(getSortKey());
    }
    
    public String getFullRepresentation(){
	return StringTools.capitalizeEach(getSortKey());
//	return StringTools.capitalizeEach((isSupplier() ? "(L) " : "(K) ") + getSortKey());
    }

    @Override
    public String filterable(){
	return firm+";"+address+";"+municipality+";"+telephone+";"+cellphone+";"+email+";"+zipcode+";"+telephone2+";"+fax+";"+notes+";"+contact+";"+type+";"+(isSupplier()?"(L)":"(K)");
    }
    
    @Override
    public FunctionResult<Contact> update() {
	return database.Database.driver().executeUpdate(Configuration.center().getDB_TABLE_CON(), getPrimaryKey(), getPrimaryKeyValue(), 
	    "firm = "+ (firm.length()>0 ? "\""+ firm +"\"":"\"\"")+", "
	    + "sortkey = "+(sortkey.length()>0 ? "\""+ sortkey +"\"":"NULL")+", "
	    + "contact = "+(contact.length()>0 ? "\""+ contact +"\"":"\"\"")+", "
	    + "address = "+(address.length()>0 ?"\""+address +"\"":"\"\"")+", "
	    + "zipcode = "+(zipcode.length()>0? "\""+zipcode +"\"":"\"\"")+", "
	    + "municipality = "+(municipality.length()>0? "\""+municipality +"\"":"\"\"")+", "
	    + "telephone = "+(telephone.length()>0? "\""+telephone +"\"":"\"\"")+", "
	    + "telephone2 = "+(telephone2.length()>0? "\""+telephone2 +"\"":"\"\"")+", "
	    + "cellphone = "+(cellphone.length()>0? "\""+cellphone +"\"":"\"\"")+", "
	    + "fax = "+(fax.length()>0? "\""+fax +"\"":"\"\"")+", "
	    + "email = "+(email.length()>0? "\""+email +"\"":"\"\"")+", "
	    + "taxnr = "+(taxnumber.length()>0? "\""+taxnumber +"\"":"\"\"")+", "
	    + "pricecode = "+((pricecode != null && pricecode.length()>0)? "\""+pricecode +"\"":"\"\"")+", "
	    + "notes = "+(notes.length()>0? "\""+notes +"\"":"\"\"")+", "
	    + "type = "+(type.length()>0 ? "\""+type +"\"":"\"\""));
    }

    @Override
    public boolean delete() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getFilterKey() {
	return sortkey;
    }
}
