/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Warkst
 */
public class Clipboard {
    
//    private static String clipboard;
    
    public static void installKeyListener(final JTextField textField){
	textField.addKeyListener(new KeyAdapter() {

	    @Override
	    public void keyReleased(KeyEvent e) {
		if (e.isControlDown()) {
		    if (e.getKeyCode() == KeyEvent.VK_C) {
			textField.copy();
		    } else if (e.getKeyCode() == KeyEvent.VK_V) {
//			textField.paste();
		    } else if (e.getKeyCode() == KeyEvent.VK_X) {
			textField.cut();
		    } else {
			//
		    }
		}
	    }

	});
    }
}
