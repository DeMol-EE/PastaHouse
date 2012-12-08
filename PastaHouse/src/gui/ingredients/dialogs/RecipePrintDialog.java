/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.dialogs;

import database.extra.Component;
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.table.PrintableTableModel;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import printer.PrintableRecipe;
import printer.Printer;
import tools.StringTools;
import tools.Utilities;

/**
 *
 * @author Warkst
 */
public class RecipePrintDialog extends javax.swing.JDialog {

    private static RecipePrintDialog reused;
    
    private PrintableRecipe model;
    private PrintableTableModel tableModel;
    private Map<Integer, Component> originalComponents;
    
    /**
     * Creates new form RecipePrintDialog
     */
    private RecipePrintDialog(java.awt.Frame parent, boolean modal) {
	super(parent, modal);
	initComponents();
	
	setTitle("Hoeveelheid:");
	
	setLocationRelativeTo(null);
	
	setResizable(false);
	
	buttonGroup1.add(pieces);
	buttonGroup1.add(weight);
	pieces.setSelected(true);
	
	componentsOutlet.setRowHeight(componentsOutlet.getRowHeight()+Utilities.fontSize()-10);
	componentsOutlet.setDefaultRenderer(String.class, CellRendererFactory.createCapitalizedStringCellRenderer());
	componentsOutlet.setDefaultRenderer(Double.class, CellRendererFactory.createTwoDecimalDoubleCellRenderer());
	componentsOutlet.setDefaultRenderer(Component.class, CellRendererFactory.createThreeDecimalDoubleCellRenderer());
    }
    
    public static RecipePrintDialog getInstance(){
	if (reused == null) {
	    reused = new RecipePrintDialog(null, true);
	} 
	return reused;
    }
    
    public void showDialog(PrintableRecipe model){
	setModel(model);
	updateTable();
	setVisible(true);
    }
    
    /*
     * Private methods follow
     */
    
    private void setModel(PrintableRecipe model){
	this.model = model;
	this.originalComponents = new TreeMap<Integer, Component>();
	for (Map.Entry<Integer, Component> entry : model.getRecipe().getComponents().entrySet()) {
	    originalComponents.put(entry.getKey(), new Component(entry.getValue().getIngredient(), entry.getValue().getRank(), entry.getValue().getQuantity()));
	}
	
	this.tableModel = new PrintableTableModel(model.getRecipe().getComponents());
	
	this.model.setIsWeight(weight.isSelected());
	
	loadModel();
	
	quantityOutlet.selectAll();
    }
    
    private void loadModel(){
	quantityOutlet.setText(""+model.getToMake());
	nameOutlet.setText(StringTools.capitalizeEach(model.getRecipe().getName()));
	componentsOutlet.setModel(tableModel);
	netPerUnitOutlet.setText(""+new DecimalFormat("0.00").format(model.getRecipe().getNetWeight())+" kg");
	grossTotalOutlet.setText(""+new DecimalFormat("0.000").format(model.getRecipe().getGrossWeight())+ " kg");
	priceTotalOutlet.setText(""+new DecimalFormat("0.000").format(model.getRecipe().getPricePerWeight() * model.getRecipe().getGrossWeight())+ " euro");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        print = new javax.swing.JButton();
        back = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        weight = new javax.swing.JRadioButton();
        pieces = new javax.swing.JRadioButton();
        quantityOutlet = new javax.swing.JTextField();
        qLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        netPerUnitOutlet = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        nameOutlet = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        componentsOutlet = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grossTotalOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        priceTotalOutlet = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(462, 370));

        jPanel3.setFocusable(false);
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        print.setText("Printen");
        print.setFocusable(false);
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel3.add(print);

        back.setText("Terug");
        back.setFocusable(false);
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel3.add(back);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        jPanel2.setFocusable(false);
        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        weight.setText("Kg");
        weight.setFocusable(false);
        weight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weightActionPerformed(evt);
            }
        });
        jPanel2.add(weight);

        pieces.setText("Stuks");
        pieces.setFocusable(false);
        pieces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piecesActionPerformed(evt);
            }
        });
        jPanel2.add(pieces);

        jPanel5.add(jPanel2, java.awt.BorderLayout.EAST);

        quantityOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantityOutletKeyReleased(evt);
            }
        });
        jPanel5.add(quantityOutlet, java.awt.BorderLayout.CENTER);

        qLabel.setText("Hoeveelheid om klaar te maken:");
        qLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 1));
        jPanel5.add(qLabel, java.awt.BorderLayout.WEST);

        jPanel4.add(jPanel5, java.awt.BorderLayout.SOUTH);

        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jPanel6.setLayout(new java.awt.GridLayout(1, 2));

        jLabel1.setText("Netto gewicht voor 1 eenheid");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jPanel6.add(jLabel1);

        netPerUnitOutlet.setText("<netPerUnitOutlet>");
        jPanel6.add(netPerUnitOutlet);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.BorderLayout());

        nameOutlet.setText("<nameOutlet>");
        nameOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 1));
        jPanel8.add(nameOutlet, java.awt.BorderLayout.CENTER);
        jPanel8.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel8, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingrediënten:"));

        componentsOutlet.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        componentsOutlet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        componentsOutlet.setFocusable(false);
        componentsOutlet.setRowSelectionAllowed(false);
        componentsOutlet.setShowVerticalLines(false);
        componentsOutlet.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(componentsOutlet);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel7.setLayout(new java.awt.GridLayout(2, 2, 0, 5));

        jLabel2.setText("Bruto gewicht voor totaal");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jPanel7.add(jLabel2);

        grossTotalOutlet.setText("<grossTotalOutlet>");
        jPanel7.add(grossTotalOutlet);

        jLabel4.setText("Bruto inkoopprijs voor totaal");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jPanel7.add(jLabel4);

        priceTotalOutlet.setText("<priceTotalOutlet>");
        jPanel7.add(priceTotalOutlet);

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        try{
	    final double q = Double.parseDouble(quantityOutlet.getText());
	    
	    if (q<=0) {
		throw new RuntimeException("Entered value makes no sense");
	    }
	    
//	    if (pieces.isSelected()){
//		new Thread(new Runnable() {
//
//		    @Override
//		    public void run() {
//			delegate.printAmount(q);
//		    }
//		}).start();
//	    } else {
//		new Thread(new Runnable() {
//
//		    @Override
//		    public void run() {
//			delegate.printQuantity(q);
//		    }
//		}).start();
//	    }
	    new Thread(new Runnable() {

		@Override
		public void run() {
		    Printer.driver().setPrintJob(model);
		    Printer.driver().tryPrint();
		}
	    }).start();

	    setVisible(false);
	} catch (Exception e){
	    System.err.println("Error:\n"+e.getMessage());
	    JOptionPane.showMessageDialog(null, "Geef een geldige waarde in!", "Error", JOptionPane.ERROR_MESSAGE);
	    quantityOutlet.setSelectionStart(0);
	    quantityOutlet.setSelectionEnd(quantityOutlet.getText().length());
	    quantityOutlet.requestFocus();
	}
	
	
    }//GEN-LAST:event_printActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        setVisible(false);
    }//GEN-LAST:event_backActionPerformed

    private void quantityOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantityOutletKeyReleased
        try{
	    double toMake = Double.parseDouble(quantityOutlet.getText());
	    quantityOutlet.setForeground(Color.black);
	    qLabel.setForeground(Color.black);
	    model.setToMake(toMake);
	} catch (Exception e){
	    quantityOutlet.setForeground(Color.red);
	    qLabel.setForeground(Color.red);
	    model.setToMake(0.0);
	}
	updateTable();
	if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
	    printActionPerformed(null);
	} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE){
	    backActionPerformed(null);
	}
    }//GEN-LAST:event_quantityOutletKeyReleased

    private void weightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightActionPerformed
        model.setIsWeight(true);
	updateTable();
    }//GEN-LAST:event_weightActionPerformed

    private void piecesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piecesActionPerformed
        model.setIsWeight(false);
	updateTable();
    }//GEN-LAST:event_piecesActionPerformed

    private void updateTable(){
	for (Map.Entry<Integer, Component> entry : model.getRecipe().getComponents().entrySet()) {
	    entry.getValue().setQuantity(model.getToMakeToNet() * originalComponents.get(entry.getKey()).getQuantity());
	}
	grossTotalOutlet.setText(""+new DecimalFormat("0.000").format(model.getRecipe().getGrossWeight())+" kg");
	priceTotalOutlet.setText(""+new DecimalFormat("0.000").format(model.getRecipe().getPricePerWeight() * model.getRecipe().getGrossWeight())+ " euro");
	
	tableModel.update();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable componentsOutlet;
    private javax.swing.JLabel grossTotalOutlet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nameOutlet;
    private javax.swing.JLabel netPerUnitOutlet;
    private javax.swing.JRadioButton pieces;
    private javax.swing.JLabel priceTotalOutlet;
    private javax.swing.JButton print;
    private javax.swing.JLabel qLabel;
    private javax.swing.JTextField quantityOutlet;
    private javax.swing.JRadioButton weight;
    // End of variables declaration//GEN-END:variables
}