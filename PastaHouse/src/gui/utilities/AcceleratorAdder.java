/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ActionMapUIResource;

/**
 *
 * @author Warkst
 */
public class AcceleratorAdder {
    public static void addAccelerator(JComponent comp, KeyStroke ks, Action a){
	InputMap keyMap = new ComponentInputMap(comp);
	keyMap.put(ks, "action");

	ActionMap actionMap = new ActionMapUIResource();
	actionMap.put("action", a);

	SwingUtilities.replaceUIActionMap(comp, actionMap);
	SwingUtilities.replaceUIInputMap(comp, JComponent.WHEN_IN_FOCUSED_WINDOW, keyMap);
    }
}
