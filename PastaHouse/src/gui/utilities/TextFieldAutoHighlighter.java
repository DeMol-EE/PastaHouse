/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author Warkst
 */
public class TextFieldAutoHighlighter {
    public static void installHighlighter(final JTextField f){
	f.addFocusListener(new FocusListener() {

	    @Override
	    public void focusGained(FocusEvent e) {
		f.setSelectionStart(0);
		f.setSelectionEnd(f.getText().length());
	    }

	    @Override
	    public void focusLost(FocusEvent e) {
		
	    }
	});
    }
}
