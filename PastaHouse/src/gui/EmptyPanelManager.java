/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Warkst
 */
public class EmptyPanelManager {
    private static JPanel panel;
    
    public static JPanel instance(){
	if (panel == null) {
	    panel = new EmptyPanel();
	}
	return panel;
    }
    
    private static class EmptyPanel extends JPanel{
	private EmptyPanel(){
	    super();
	    setLayout(new BorderLayout());
	    JLabel l = new JLabel("Klik op \"Toevoegen...\" om te beginnen, of druk op ctrl+N.");
	    l.setHorizontalAlignment(JLabel.CENTER);
	    add(l, BorderLayout.CENTER);
	}
    }
}
