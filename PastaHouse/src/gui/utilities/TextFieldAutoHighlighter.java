/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Warkst
 */
public class TextFieldAutoHighlighter {
    public static void installHighlighter(final JTextField f){
	f.addFocusListener(new FocusListener() {

	    @Override
	    public void focusGained(FocusEvent e) {
		SwingUtilities.invokeLater(new Runnable() {

		    @Override
		    public void run() {
			f.selectAll();
		    }
		});
	    }

	    @Override
	    public void focusLost(FocusEvent e) {
//		SwingUtilities.invokeLater(new Runnable() {
//
//		    @Override
//		    public void run() {
//			f.setSelectionStart(0);
//			f.setSelectionEnd(0);
//		    }
//		});
	    }
	});
    }
}
