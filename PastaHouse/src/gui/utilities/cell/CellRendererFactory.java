/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.cell;

import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import utilities.Utilities;

/**
 *
 * @author Warkst
 */
public class CellRendererFactory {
    public static TableCellRenderer createTwoDecimalDoubleCellRenderer(){
	return new TwoDecimalDoubleCellRenderer();
    }
    
    public static TableCellRenderer createIngredientCellRenderer(){
	return new IngredientCellRenderer();
    }
    
    public static TableCellRenderer createThreeDecimalDoubleCellRenderer(){
	return new ThreeDecimalDoubleCellRenderer();
    }
    
    public static TableCellRenderer createCapitalizedStringCellRenderer(){
	return new CapitalizedStringCellRenderer();
    }
    
    public static TableCellRenderer createComboBoxCellRenderer(){
	return new ComboBoxCellRenderer();
    }
    
    public static TableCellRenderer createButtonCellRenderer(){
	return new ButtonCellRenderer();
    }
    
    private static class TwoDecimalDoubleCellRenderer extends JLabel implements TableCellRenderer{

	public TwoDecimalDoubleCellRenderer(){
	    setOpaque(true);
	    setHorizontalAlignment(JLabel.CENTER);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//	    
//	    System.out.println("CALLING "+row+","+column);
//	    
	    TableCellRenderer dtcr = new DefaultTableCellRenderer();
	    Component defaultComponent = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    setBackground(defaultComponent.getBackground());
	    
	    DecimalFormat twoFormatter = new DecimalFormat("0.00");
	    if(value!=null) this.setText(twoFormatter.format(value));
	    return this;
	}
	
    }
    
    private static class IngredientCellRenderer extends JLabel implements TableCellRenderer{

	public IngredientCellRenderer(){
	    setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer dtcr = new DefaultTableCellRenderer();
	    Component defaultComponent = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    setBackground(defaultComponent.getBackground());
	    
	    if(value!=null) this.setText(value.toString());
	    
	    return this;
	}
	
    }
    
    private static class ThreeDecimalDoubleCellRenderer extends JLabel implements TableCellRenderer{

	public ThreeDecimalDoubleCellRenderer(){
	    setOpaque(true);
	    setHorizontalAlignment(JLabel.CENTER);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer dtcr = new DefaultTableCellRenderer();
	    Component defaultComponent = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    setBackground(defaultComponent.getBackground());
	    
	    DecimalFormat threeFormatter = new DecimalFormat("0.000");
	    if(value!=null) this.setText(threeFormatter.format(value));
	    
	    return this;
	}
	
    }
    
    private static class CapitalizedStringCellRenderer extends JLabel implements TableCellRenderer{

	public CapitalizedStringCellRenderer(){
	    setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer dtcr = new DefaultTableCellRenderer();
	    Component defaultComponent = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    setBackground(defaultComponent.getBackground());
//	    this.setHorizontalAlignment(JLabel.CENTER)
	    if (value == null) {
		setText("<Kies een item>");
	    } else {
		setText(" "+Utilities.capitalizeEach(value.toString()));
	    }
	    return this;
	}
	
    }
    
    private static class ComboBoxCellRenderer extends JComboBox implements TableCellRenderer{

	public ComboBoxCellRenderer(){
	    setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer dtcr = new DefaultTableCellRenderer();
	    Component defaultComponent = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    setBackground(defaultComponent.getBackground());
	    
	    return this;
	}
	
    }
    
    private static class ButtonCellRenderer extends JButton implements TableCellRenderer{

	public ButtonCellRenderer(){
	    setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer dtcr = new DefaultTableCellRenderer();
	    Component defaultComponent = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    setBackground(defaultComponent.getBackground());
	    
	    setText("Verwijderen");
	    
	    return this;
	}
	
    }
}
