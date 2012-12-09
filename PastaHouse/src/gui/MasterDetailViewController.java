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
     * Should provide functionality to add a new item to the master-component's data source and select it in the list (and consequently update the detail view).
     * 
     * @param newObj The object to be added and selected.
     */
    public void addAndSelect(Type newObj);
    /**
     * Should provide functionality to edit an existing item in the master-component's data source and select it in the list (and consequently update the detail view).
     * 
     * @param newObj The new version of the edited item.
     * @param oldObj The previous version of the edited item (can be used for sorting).
     */
    public void editAndSelect(Type newObj, Type oldObj);
    /**
     * User by external sources to trigger correct focus traversal initialization. This is called every time the controller is displayed.
     */
    public void electFirstResponder();
    
    public JMenu menu();
}
