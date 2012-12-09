/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import gui.EmptyPanel;

/**
 *
 * @author Warkst
 */
public class EmptyPanelManager {
    private static EmptyPanel panel;
    
    public static EmptyPanel panel(){
	if (panel == null) {
	    panel = new EmptyPanel();
	}
	return panel;
    }
}
