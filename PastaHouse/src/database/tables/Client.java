/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

/**
 *
 * @author Robin jr
 */
public class Client {
    
    private String name;
    
    public Client(String str){
	name = str;
    }
    
    public String getName(){
	return name;
    }

    @Override
    public String toString() {
	return name;
    }
}
