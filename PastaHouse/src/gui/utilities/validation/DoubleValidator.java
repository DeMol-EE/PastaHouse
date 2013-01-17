/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.validation;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author Warkst
 */
public class DoubleValidator extends AbstractValidator {
    public DoubleValidator(JDialog parent, JTextField c, String message) {
        super(parent, c, message);
    }
	
    @Override
    protected boolean validationCriteria(JComponent c) {
        try{
	    Double.parseDouble(((JTextField)c).getText());
	    return true;
	} catch(Exception e){
	    return false;
	}
    }
}
