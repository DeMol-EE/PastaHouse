/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.cell;

import database.Database;
import gui.ingredients.dialogs.EditRecipeDialog;
import gui.utilities.combobox.AutocompleteCombobox;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Robin jr
 */
public class CellEditorFactory {

    public static TableCellEditor createButtonEditor() {
	return new ButtonEditor();
    }

    public static TableCellEditor createDoubleEditor() {
	return new DoubleEditor();
    }

    public static TableCellEditor createComboBoxEditor(List components, EditRecipeDialog callback) {
	return new ComboBoxEditor(components, callback);
    }

    private static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

	private JButton button;
	private String title;

	@SuppressWarnings("LeakingThisInConstructor")
	public ButtonEditor() {
	    button = new JButton();
	    button.addActionListener(this);
	    button.setBorderPainted(false);
	}

	@Override
	public Object getCellEditorValue() {
	    return title;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    title = value.toString();
	    return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    fireEditingStopped();
	}
    }

    private static class DoubleEditor extends AbstractCellEditor implements TableCellEditor {

	private JTextField input;

	public DoubleEditor() {
	    input = new JTextField();
	    input.setHorizontalAlignment(JTextField.CENTER);
	}

	@Override
	public Object getCellEditorValue() {
	    return input.getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	    input.setText(value.toString());
	    return input;
	}
    }

    private static class ComboBoxEditor extends AbstractCellEditor implements TableCellEditor {

	private AutocompleteCombobox acb;

	public ComboBoxEditor(List data, final EditRecipeDialog callback) {
	    this.acb = new AutocompleteCombobox(data);
	    this.acb.setOpaque(true);
//	    this.acb.setBorder(BorderFactory.createEmptyBorder());

	    this.acb.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    callback.ingredientBoxCallback();
		}
	    });
	    this.acb.addFocusListener(new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
		    //
		}

		@Override
		public void focusLost(FocusEvent e) {
		    callback.ingredientBoxCallback();
		}
	    });
	}

	@Override
	public Object getCellEditorValue() {
	    System.out.println("selected index: " + acb.getSelectedIndex());
	    if (acb.getSelectedIndex() == 0) {
		return null;
	    } else {
//		return acb.getDataList().toArray()[acb.getSelectedIndex()];
//		return acb.getSelectedItem();

		Object sel = acb.getSelectedItem();
		System.out.println("selected item: " + acb.getSelectedItem());

		if (sel instanceof String) {
		    return Database.driver().getIngredients().get(((String) sel).toLowerCase());
		} else {
		    return sel;
		}
	    }
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

	    JTextField tf = (JTextField) acb.getEditor().getEditorComponent();
	    tf.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
	    
	    if (isSelected) {
		acb.setBackground(table.getSelectionBackground());
	    } else {
		acb.setBackground(table.getBackground());
	    }
	    
	    //	    input.setText(value.toString());
	    if (value == null) {
		acb.setSelectedIndex(0);
	    } else {
//		if (value instanceof Component) {
		acb.setSelectedItem(value);
//		}
	    }
	    return acb;
	}

	@Override
	public boolean isCellEditable(EventObject event) {
	    if (event == null || !(event instanceof MouseEvent)
		    || (((MouseEvent) event).getClickCount() >= 2)) {
		return true;
	    }
	    return false;
	}
    }
}
