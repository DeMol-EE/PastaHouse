/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Robin jr
 */
public class Utilities {
    public static String capitalize(String s){
	return s.substring(0, 1).toUpperCase()+s.substring(1).toLowerCase();
    }
    
    public static String capitalizeEach(String s){
	String[] parts = s.split(" ");
	String returnMe = "";
	for (String string : parts) {
	    returnMe+=capitalize(string)+" ";
	}
	return returnMe;
    }
}
