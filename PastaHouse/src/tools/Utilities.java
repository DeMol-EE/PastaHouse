/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Robin jr
 */
public class Utilities {
    
    public static final String incorrectFormMessage = "Zorg ervoor dat alle velden correct ingevuld zijn.";
    public static final String incompleteFormMessage = "Vul alle vereiste velden (aangegeven met een asterisk) in.";
    
    public static int fontSize(){
	return 16;
    }
    
    /**
     * Checks the validity of a given tax number using the Check97 algorithm.
     * The tax number should contain 9 numbers. All dots, commas and dotcommas are removed.
     * Let left be the first 7 digits of the tax number and right be the last 2.
     * The check97 algorithm specifies the number is valid if left%97 + right = 97
     * (or thus if the right part is the 97-complement of left mod 97).
     * 
     * @param taxnr The tax number to be validated.
     * @return True if valid, false if not.
     */
    
    public static String getDigits(String taxnr){
        taxnr = taxnr.replaceAll("[^0-9]", "");
        return taxnr;
    }
    
    public static boolean validTaxNr(String taxnr){
	if (taxnr.length()!=9) {
	    System.out.println("Tax nr isn't 9 digits long ("+taxnr.length()+")");
	    return false;
	}
	String left = taxnr.substring(0, 7);
	String right = taxnr.substring(7);
	
	try{
	    int l = Integer.parseInt(left);
	    int r = Integer.parseInt(right);
	    
	    if (l%97 + r == 97) {
		return true;
	    } else {
		System.out.println(left+"%97="+l%97);
		System.out.println("+ right="+(r+(l%97)));
		return false;
	    }
	} catch (Exception e){
	    System.err.println("Error validating taxnr:\n");
	    e.printStackTrace();
	    return false;
	}
    }
}
