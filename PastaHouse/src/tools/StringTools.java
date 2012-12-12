/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Warkst
 */
public class StringTools {
    public static String clip(String s, int length){
	if (s.length() < length) {
	    return s;
	} else {
	    if (length > 3) {
		return s.substring(0, length-3).concat("...");
	    } else {
		return s.substring(0, length);
	    }
	}
    }
    
    public static String pad(String s, char c, int length){
	if (s.length() >= length) {
	    return s;
	} else {
	    return pad(s, c, length-1).concat(""+c);
	}
    }
    
    public static String padClip(String s, char c, int length){
	if (s.length() >= length) {
	    return clip(s, length);
	} else {
	    return pad(s, c, length-1).concat(""+c);
	}
    }

    public static String capitalize(String s) {
	if (s == null) {
	    return "";
	}
	if (s.length() > 1) {
//	    return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	    return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	return s;
    }

    public static String capitalizeEach(String s) {
	if (s==null || s.isEmpty()) {
	    return "";
	}
	String[] parts = s.split(" ");
	String returnMe = "";
	if (s.length() > 1) {
	    for (String string : parts) {
		returnMe += capitalize(string) + " ";
	    }
	}
	return returnMe.length() > 0 ? returnMe.substring(0, returnMe.length()-1) : "";
    }
}
