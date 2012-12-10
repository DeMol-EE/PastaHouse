/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.ingredients.controllers.ViewController;
import javax.swing.JMenu;

/**
 *
 * @author Robin jr
 */
public interface MasterDetailViewController<Type> extends ViewController {
    /**
     * Should implement functionality that show the detail in the detail-component of the selected value in the master-component.
     * 
     * @param value The value selected in the master-component.
     */
    public void updateDetail(Type value);
    /**
     * User by external sources to trigger correct focus traversal initialization. This is called every time the controller is displayed.
     */
    public void electFirstResponder();
    
    public JMenu menu();
}
