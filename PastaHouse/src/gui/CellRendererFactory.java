/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Warkst
 */
public class CellRendererFactory {
    public static TableCellRenderer createDoubleCellRenderer(){
	return new DoubleCellRenderer();
    }
    
    public static TableCellRenderer createIngredientCellRenderer(){
	return new IngredientCellRenderer();
    }
    
    public static TableCellRenderer createComponentCellRenderer(){
	return new ComponentCellRenderer();
    }
    
    public static TableCellRenderer createCapitalizedStringCellRenderer(){
	return new CapitalizedStringCellRenderer();
    }
    
    private static class DoubleCellRenderer extends JLabel implements TableCellRenderer{

	public DoubleCellRenderer(){
	    setOpaque(true);
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
	    this.setText(twoFormatter.format(value));
	    this.setHorizontalAlignment(JLabel.CENTER);
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
	    
	    this.setText(value.toString());
	    
	    return this;
	}
	
    }
    
    private static class ComponentCellRenderer extends JLabel implements TableCellRenderer{

	public ComponentCellRenderer(){
	    setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    TableCellRenderer dtcr = new DefaultTableCellRenderer();
	    Component defaultComponent = dtcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    setBackground(defaultComponent.getBackground());
	    
	    DecimalFormat threeFormatter = new DecimalFormat("0.000");
	    this.setText(threeFormatter.format(value));
	    this.setHorizontalAlignment(JLabel.CENTER);
	    
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
	    
	    this.setText(" "+Utilities.capitalizeEach(value.toString()));
	    return this;
	}
	
    }
}
