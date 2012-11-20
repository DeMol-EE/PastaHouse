/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.dialogs;

import gui.utilities.combobox.ComboBoxModelFactory;
import gui.utilities.combobox.AutocompleteCombobox;
import database.BasicIngredient;
import database.Database;
import database.Supplier;
import gui.ingredients.controllers.MasterDetailViewController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Robin jr
 */
public class AddIngredientDialog extends javax.swing.JDialog {

    private final MasterDetailViewController delegate;
    
    /**
     * Creates new form AddIngredientDialog
     */
    public AddIngredientDialog(java.awt.Frame parent, boolean modal, MasterDetailViewController delegate) {
	super(parent, modal);
	initComponents();
	
	this.delegate = delegate;
	
	setLocationRelativeTo(null);
	setTitle("Ingrediënt toevoegen");
	
	supplierOutlet.setModel(ComboBoxModelFactory.createSupplierComboBoxModel(Database.driver().getSuppliers().values().toArray()));
	
	supplierParent.removeAll();
	List suppliers = new ArrayList();
	suppliers.add("");
	suppliers.addAll(Database.driver().getSuppliers().values());
	AutocompleteCombobox supplierBox = new AutocompleteCombobox(suppliers);
	supplierParent.add(supplierBox, BorderLayout.CENTER);
	
	taxesOutlet.setText(""+21.0);
	taxesFormattedOutlet.setText(new DecimalFormat("0.00").format(new Double(21.0))+" %");
	lossOutlet.setText(""+0.0);
	lossFormattedOutlet.setText(new DecimalFormat("0.00").format(new Double(0.0))+" %");
	pricePerUnitOutlet.setText(""+0.0);
	pricePerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(new Double(0.0))+" euro/");
	weightPerUnitOutlet.setText(""+0.0);
	weightPerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(new Double(0.0))+" kg/");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fixedFields = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameOutlet = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        brandOutlet = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        packagingOutlet = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        supplierParent = new javax.swing.JPanel();
        supplierOutlet = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pricePerUnitOutlet = new javax.swing.JTextField();
        pricePerUnitFormattedOutlet = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        weightPerUnitOutlet = new javax.swing.JTextField();
        weightPerUnitFormattedOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lossOutlet = new javax.swing.JTextField();
        lossFormattedOutlet = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        taxesOutlet = new javax.swing.JTextField();
        taxesFormattedOutlet = new javax.swing.JLabel();
        stretchableFields = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notesOutlet = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel4 = new javax.swing.JPanel();
        add = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(430, 380));

        fixedFields.setLayout(new java.awt.GridLayout(8, 2, -1, 0));

        jLabel1.setText("Naam");
        jLabel1.setFocusable(false);
        fixedFields.add(jLabel1);
        fixedFields.add(nameOutlet);

        jLabel2.setText("Merk");
        jLabel2.setFocusable(false);
        fixedFields.add(jLabel2);
        fixedFields.add(brandOutlet);

        jLabel3.setText("Verpakking");
        jLabel3.setFocusable(false);
        fixedFields.add(jLabel3);

        packagingOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                packagingOutletKeyReleased(evt);
            }
        });
        fixedFields.add(packagingOutlet);

        jLabel4.setText("Leverancier");
        jLabel4.setFocusable(false);
        fixedFields.add(jLabel4);

        supplierParent.setLayout(new java.awt.BorderLayout());

        supplierOutlet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        supplierParent.add(supplierOutlet, java.awt.BorderLayout.CENTER);

        fixedFields.add(supplierParent);

        jLabel5.setText("Prijs per verpakking (BTW excl)");
        jLabel5.setFocusable(false);
        fixedFields.add(jLabel5);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        pricePerUnitOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pricePerUnitOutletKeyReleased(evt);
            }
        });
        jPanel1.add(pricePerUnitOutlet);

        pricePerUnitFormattedOutlet.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        pricePerUnitFormattedOutlet.setText("<pricePerUnitFormattedOutlet>");
        pricePerUnitFormattedOutlet.setFocusable(false);
        jPanel1.add(pricePerUnitFormattedOutlet);

        fixedFields.add(jPanel1);

        jLabel6.setText("Gewicht per verpakking");
        jLabel6.setFocusable(false);
        fixedFields.add(jLabel6);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        weightPerUnitOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                weightPerUnitOutletKeyReleased(evt);
            }
        });
        jPanel2.add(weightPerUnitOutlet);

        weightPerUnitFormattedOutlet.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        weightPerUnitFormattedOutlet.setText("<weightPerUnitFormattedOutlet>");
        weightPerUnitFormattedOutlet.setFocusable(false);
        jPanel2.add(weightPerUnitFormattedOutlet);

        fixedFields.add(jPanel2);

        jLabel7.setText("Verliespercentage");
        jLabel7.setFocusable(false);
        fixedFields.add(jLabel7);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        lossOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lossOutletKeyReleased(evt);
            }
        });
        jPanel5.add(lossOutlet);

        lossFormattedOutlet.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lossFormattedOutlet.setText("<lossFormattedOutlet>");
        lossFormattedOutlet.setFocusable(false);
        jPanel5.add(lossFormattedOutlet);

        fixedFields.add(jPanel5);

        jLabel11.setText("BTW");
        jLabel11.setFocusable(false);
        fixedFields.add(jLabel11);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        taxesOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                taxesOutletKeyReleased(evt);
            }
        });
        jPanel6.add(taxesOutlet);

        taxesFormattedOutlet.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        taxesFormattedOutlet.setText("<taxesFormattedOutlet>");
        taxesFormattedOutlet.setFocusable(false);
        jPanel6.add(taxesFormattedOutlet);

        fixedFields.add(jPanel6);

        getContentPane().add(fixedFields, java.awt.BorderLayout.NORTH);

        stretchableFields.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Opmerkingen:"));

        notesOutlet.setColumns(20);
        notesOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        notesOutlet.setRows(5);
        jScrollPane1.setViewportView(notesOutlet);

        stretchableFields.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(stretchableFields, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(filler1, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        add.setText("Aanmaken");
        add.setFocusable(false);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel4.add(add);

        cancel.setText("Terug");
        cancel.setFocusable(false);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel4.add(cancel);

        jPanel3.add(jPanel4, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void taxesOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taxesOutletKeyReleased
       try{
	   taxesFormattedOutlet.setText(new DecimalFormat("0.00").format(Double.parseDouble(taxesOutlet.getText())) +" %");
	   taxesFormattedOutlet.setForeground(Color.black);
	   taxesOutlet.setForeground(Color.black);
       } catch(Exception e){
	   taxesFormattedOutlet.setForeground(Color.red);
	   taxesOutlet.setForeground(Color.red);
	   taxesFormattedOutlet.setText("??? %");
	   System.err.println("Exception: "+e.getMessage());
       }
    }//GEN-LAST:event_taxesOutletKeyReleased

    private void packagingOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_packagingOutletKeyReleased
        String ppu = pricePerUnitFormattedOutlet.getText();
	pricePerUnitFormattedOutlet.setText(ppu.substring(0, ppu.lastIndexOf("/")+1)+packagingOutlet.getText().toLowerCase());
	String wpu = weightPerUnitFormattedOutlet.getText();
	weightPerUnitFormattedOutlet.setText(wpu.substring(0, wpu.lastIndexOf("/")+1)+packagingOutlet.getText().toLowerCase());
    }//GEN-LAST:event_packagingOutletKeyReleased

    private void lossOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lossOutletKeyReleased
        try{
	   lossFormattedOutlet.setText(new DecimalFormat("0.00").format(Double.parseDouble(lossOutlet.getText())) +" %");
	   lossOutlet.setForeground(Color.black);
	   lossFormattedOutlet.setForeground(Color.black);
       } catch(Exception e){
	   lossFormattedOutlet.setForeground(Color.red);
	   lossOutlet.setForeground(Color.red);
	   lossFormattedOutlet.setText("??? %");
	   System.err.println("Exception: "+e.getMessage());
       }
    }//GEN-LAST:event_lossOutletKeyReleased

    private void weightPerUnitOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weightPerUnitOutletKeyReleased
        try{
	   weightPerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(Double.parseDouble(weightPerUnitOutlet.getText())) +" kg/"+packagingOutlet.getText());
	   weightPerUnitFormattedOutlet.setForeground(Color.black);
	   weightPerUnitOutlet.setForeground(Color.black);
       } catch(Exception e){
	   weightPerUnitFormattedOutlet.setText("??? kg/"+packagingOutlet.getText());
	   weightPerUnitFormattedOutlet.setForeground(Color.red);
	   weightPerUnitOutlet.setForeground(Color.red);
	   System.err.println("Exception: "+e.getMessage());
       }
    }//GEN-LAST:event_weightPerUnitOutletKeyReleased

    private void pricePerUnitOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pricePerUnitOutletKeyReleased
        try{
	   pricePerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(Double.parseDouble(pricePerUnitOutlet.getText())) +" euro/"+packagingOutlet.getText());
	   pricePerUnitFormattedOutlet.setForeground(Color.black);
	   pricePerUnitOutlet.setForeground(Color.black);
       } catch(Exception e){
	   pricePerUnitFormattedOutlet.setForeground(Color.red);
	   pricePerUnitOutlet.setForeground(Color.red);
	   pricePerUnitFormattedOutlet.setText("??? euro/"+packagingOutlet.getText());
	   System.err.println("Exception: "+e.getMessage());
       }
    }//GEN-LAST:event_pricePerUnitOutletKeyReleased

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        BasicIngredient b = BasicIngredient.loadWithValues(
		(supplierOutlet.getSelectedItem() instanceof Supplier)? (Supplier)supplierOutlet.getSelectedItem(): null, 
		brandOutlet.getText(), 
		packagingOutlet.getText(), 
		Double.parseDouble(weightPerUnitOutlet.getText()), 
		Double.parseDouble(pricePerUnitOutlet.getText()), 
		Double.parseDouble(lossOutlet.getText()), 
		Double.parseDouble(taxesOutlet.getText()), 
		nameOutlet.getText(), 
		new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 
		notesOutlet.getText());
	if (Database.driver().addIngredient(b)) {
	    delegate.updateListAndSelect(b);
	    this.dispose();
	}
    }//GEN-LAST:event_addActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JTextField brandOutlet;
    private javax.swing.JButton cancel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel fixedFields;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lossFormattedOutlet;
    private javax.swing.JTextField lossOutlet;
    private javax.swing.JTextField nameOutlet;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JTextField packagingOutlet;
    private javax.swing.JLabel pricePerUnitFormattedOutlet;
    private javax.swing.JTextField pricePerUnitOutlet;
    private javax.swing.JPanel stretchableFields;
    private javax.swing.JComboBox supplierOutlet;
    private javax.swing.JPanel supplierParent;
    private javax.swing.JLabel taxesFormattedOutlet;
    private javax.swing.JTextField taxesOutlet;
    private javax.swing.JLabel weightPerUnitFormattedOutlet;
    private javax.swing.JTextField weightPerUnitOutlet;
    // End of variables declaration//GEN-END:variables
}