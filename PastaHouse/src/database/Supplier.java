/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Warkst
 */
public class Supplier {
    private int id;
    private String firm;
    private String address;
    
    private Supplier(int id, String firm, String address){
	this.id = id;
	this.firm = firm;
	this.address = address;
    }
    
    public static Supplier loadWithValues(int id, String firm, String address) {
	return new Supplier(id, firm, address);
    }

    public int getId() {
	return id;
    }

    public String getFirm() {
	return firm;
    }

    public String getAddress() {
	return address;
    }
    
    @Override
    public String toString(){
	return "("+id+") "+firm+" @ "+address;
    }
}
