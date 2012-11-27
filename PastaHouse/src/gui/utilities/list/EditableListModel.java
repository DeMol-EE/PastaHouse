/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.list;

import javax.swing.AbstractListModel;

/**
 *
 * @author Robin jr
 */
public abstract class EditableListModel<Type> extends AbstractListModel {
    public abstract void add(Type o);
    
    public abstract void edit(Type newObj, Type oldObj);
    
    public void update(){
	fireContentsChanged(this, 0, getSize());
    }
}
