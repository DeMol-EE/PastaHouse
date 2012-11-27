/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import java.beans.PropertyChangeListener;
import javax.swing.Action;

/**
 *
 * @author Warkst
 */
public abstract class KeyAction implements Action {

    @Override
    public Object getValue(String key) {
	return null;
    }

    @Override
    public void putValue(String key, Object value) {
	//
    }

    @Override
    public void setEnabled(boolean b) {
	//
    }

    @Override
    public boolean isEnabled() {
	return true;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
	//
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
	//
    }
}
