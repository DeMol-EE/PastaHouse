/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import database.Database;
import database.extra.Contact;
import database.models.ClientModel;
import tools.Configuration;
import tools.StringTools;

/**
 *
 * @author Robin jr
 */
public class Client extends Contact{
    
    private String name;
    
    
    private Client(int id, String name, String contact, String address, int zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String taxnumber, String pricecode, String notes) {
	super(id, Configuration.center().getDB_TABLE_CL(), name, contact, address, zipcode, municipality, telephone, telephone2, cellphone, fax, email, taxnumber, pricecode, notes);
    }
    
    private Client(int id, ClientModel s){
	super(id, Configuration.center().getDB_TABLE_CL(), s.getName(), s.getContact(), s.getAddress(), s.getZipcode(), s.getMunicipality(), s.getTelephone(), s.getTelephone2(), s.getCellphone(), s.getFax(), s.getEmail(), s.getTaxnumber(), s.getPricecode(), s.getNotes());
    }
    
    public Client(Client copy){
	super(copy.getPrimaryKeyValue(), copy.getTableName(), copy.getName(), copy.getContact(), copy.getAddress(), copy.getZipcode(), copy.getMunicipality(), copy.getTelephone(), copy.getTelephone2(), copy.getCellphone(), copy.getFax(), copy.getEmail(), copy.getTaxnumber(), copy.getPricecode(), copy.getNotes());
    }
    
    public void copy(Client copy){
	setName(copy.getName());
	setContact(copy.getContact());
	setAddress(copy.getAddress());
        setZipcode(copy.getZipcode());
	setMunicipality(copy.getMunicipality());
	setTelephone(copy.getTelephone());
        setTelephone2(copy.getTelephone2());
	setCellphone(copy.getCellphone());
	setFax(copy.getFax());
	setEmail(copy.getEmail());
	setTaxnumber(copy.getTaxnumber());
	setPricecode(copy.getPricecode());
	setNotes(copy.getNotes());
    }
    
    public static Client loadWithValues(int id, String name, String contact, String address, int zipcode, String municipality, String telephone, String telephone2, String cellphone, String fax, String email, String taxnumber, String pricecode, String notes){
	return new Client(id, name, contact, address, zipcode, municipality, telephone, telephone2, cellphone, fax, email, taxnumber, pricecode, notes);
    }
    
    public static Client createFromModel(int id, ClientModel model){
	return new Client(id, model);
    }
    
    @Override
    public String toString() {
	return StringTools.capitalize(name);
    }

    @Override
    public String getType() {
	return "Klant";
    }

    @Override
    public boolean isSupplier() {
	return false;
    }

    @Override
    public boolean update() {
	return Database.driver().executeUpdate(getTableName(), getPrimaryKey(), getPrimaryKeyValue(),  
		"naam = \""+ getName() +"\", "
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
		+ "btwnummer = "+(getTaxnumber().length()>0 ? "\""+getTaxnumber() +"\"":"NULL")+", "
		+ "prijscode = "+(getPricecode().length()>0 ? "\""+getPricecode() +"\"":"NULL"));
    }
}
