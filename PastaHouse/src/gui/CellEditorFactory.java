/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Robin jr
 */
public class CellEditorFactory {
    public static TableCellEditor createButtonEditor(){
	return new ButtonEditor();
    }
    
    public static TableCellEditor createDoubleEditor(){
	return new DoubleEditor();
    }
    
    private static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{

	private JButton button;
	private String title;
	
	@SuppressWarnings("LeakingThisInConstructor")
	public ButtonEditor(){
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
    
    private static class DoubleEditor extends AbstractCellEditor implements TableCellEditor{

	private JTextField input;
	
	public DoubleEditor(){
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
}
