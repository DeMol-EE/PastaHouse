/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import database.extra.Ingredient;
import gui.utilities.combobox.AutocompleteCombobox;
import gui.utilities.validation.AbstractValidator;
import gui.utilities.validation.DoubleValidator;
import gui.utilities.validation.NotEmptyValidator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author Warkst
 */
public class TextFieldValidator {
    public static void installDoubleValidator(JDialog parent, JTextField textField){
	textField.setInputVerifier(new DoubleValidator(parent, textField, "Ongeldige waarde! Verwacht formaat: x.y"));
    }
    
    public static void installQuantityValidator(JDialog parent, JTextField textField, AutocompleteCombobox combobox){
	textField.setInputVerifier(new QuantityValidator(parent, textField, combobox, "Ongeldige waarde! Verwacht formaat: x.y"));
    }
    
    public static void installPositiveDoubleValidator(JDialog parent, JTextField textField){
	textField.setInputVerifier(new PositiveDoubleValidator(parent, textField, "Ongeldige waarde! Verwacht formaat: x.y, groter dan 0.0"));
    }
    
    public static void installNotEmptyValidator(JDialog parent, JTextField textField){
	textField.setInputVerifier(new NotEmptyValidator(parent, textField, "Dit veld mag niet leeg zijn!"));
    }
    
    public static void installUniqueIngredientValidator(JDialog parent, JTextField textField){
	textField.setInputVerifier(new UniqueIngredientValidator(parent, textField, "Dit veld moet uniek en niet leeg zijn."));
    }
    
    private static class UniqueIngredientValidator extends AbstractValidator{

	public UniqueIngredientValidator(JDialog parent, JComponent c, String message) {
	    super(parent, c, message);
	}
	
	@Override
	protected boolean validationCriteria(JComponent c) {
	    try{
		JTextField f = (JTextField)c;
		if (f.getText().isEmpty()) {
		    return false;
		}
		List<Ingredient> ingrs = database.Database.driver().getIngredients();
		for (Ingredient ingredient : ingrs) {
		    if (ingredient.getName().equalsIgnoreCase(f.getText())) {
			return false;
		    }
		}
		return true;
	    } catch(Exception e){
		return false;
	    }
	}
	
    }
    
    private static class PositiveDoubleValidator extends AbstractValidator {
	public PositiveDoubleValidator(JDialog parent, JTextField c, String message) {
	    super(parent, c, message);
	}

	@Override
	protected boolean validationCriteria(JComponent c) {
	    try{
		return Double.parseDouble(((JTextField)c).getText())>0;
	    } catch(Exception e){
		return false;
	    }
	}
    }
    
    private static class QuantityValidator extends AbstractValidator {
	private final AutocompleteCombobox acb;
	
	public QuantityValidator(JDialog parent, JTextField c, AutocompleteCombobox b, String message) {
	    super(parent, c, message);
	    acb = b;
	}

	@Override
	protected boolean validationCriteria(JComponent c) {
	    System.out.println("SI: "+acb.getSelectedIndex());
	    if (acb.getSelectedIndex()==0) {
		return true;
	    }
	    try{
		return Double.parseDouble(((JTextField)c).getText())>0;
	    } catch(Exception e){
		return false;
	    }
	}
    }
}
