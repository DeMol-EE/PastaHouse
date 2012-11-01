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

    public static String capitalize(String s) {
        if (s.length() > 1) {
            return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return s;
    }

    public static String capitalizeEach(String s) {
        String[] parts = s.split(" ");
        String returnMe = "";
        if (s.length() > 1) {
            for (String string : parts) {
                returnMe += capitalize(string) + " ";
            }
        }
        return returnMe;
    }
}
