/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.AbstractListModel;

/**
 *
 * @author Robin jr
 */
public abstract class DynamicListModel<Type> extends AbstractListModel {
    public abstract void add(Type o);
    
    public void update(){
	fireContentsChanged(this, 0, getSize());
    }
}
