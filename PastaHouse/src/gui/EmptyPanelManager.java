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
    
    public static JPanel instance(){
	return new EmptyPanel("Klik op \"Toevoegen...\" om te beginnen, of druk op ctrl+N.");
    }
    
    public static JPanel instance(String msg){
	return new EmptyPanel(msg);
    }
    
    private static class EmptyPanel extends JPanel{
	private EmptyPanel(String msg){
	    super();
	    setLayout(new BorderLayout());
	    JLabel l = new JLabel(msg);
	    l.setHorizontalAlignment(JLabel.CENTER);
	    add(l, BorderLayout.CENTER);
	}
    }
}
