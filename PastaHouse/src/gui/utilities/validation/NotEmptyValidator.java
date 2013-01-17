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
public class NotEmptyValidator extends AbstractValidator {
    public NotEmptyValidator(JDialog parent, JTextField c, String message) {
        super(parent, c, message);
    }
	
    @Override
    protected boolean validationCriteria(JComponent c) {
        return !((JTextField)c).getText().isEmpty();
            
    }
}
