/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.michaelbaranov.microba.calendar.DatePicker;
import database.Recipe;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;

/**
 *
 * @author Robin jr
 */
public class EditRecipeDialog extends javax.swing.JDialog {

    private final Recipe model;
    /**
     * Creates new form EditRecipeDialog
     */
    public EditRecipeDialog(java.awt.Frame parent, boolean modal, Recipe model) {
	super(parent, modal);
	initComponents();
	
	jPanel5.add(new DatePicker(new Date(), new SimpleDateFormat("dd/MM/yyyy")));
	
	setTitle("Recept wijzigen");
	setLocationRelativeTo(null);
	
	this.model = model;
	
	this.ingredientsOutlet.setModel(new EditableTableModel(model.getIngredients()));
	
	loadModel();
    }

    private void loadModel(){
	nameOutlet.setText(model.getName());
	netWeightOutlet.setText(""+model.getNetWeight());
	
	updateGrossWeightOutlet();
    }
    
    private void updateGrossWeightOutlet(){
	
    }
    
    private void updatePricePerWeightOutlet(){
	
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        nameOutlet = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        preparationOutlet = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grossWeightOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        netWeightOutlet = new javax.swing.JTextField();
        netWeightFormattedOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pricePerWeightOutlet = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel2 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ingredientsOutlet = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        addComponent = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(430, 400));

        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        nameOutlet.setText("<nameOutlet>");
        jPanel5.add(nameOutlet);

        getContentPane().add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel7.setPreferredSize(new java.awt.Dimension(176, 250));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Bereiding:"));

        preparationOutlet.setColumns(20);
        preparationOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        preparationOutlet.setRows(5);
        jScrollPane3.setViewportView(preparationOutlet);

        jPanel7.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.GridLayout(3, 2));

        jLabel2.setText("Totaalgewicht ingrediënten");
        jPanel6.add(jLabel2);

        grossWeightOutlet.setText("<grossWeightOutlet>");
        jPanel6.add(grossWeightOutlet);

        jLabel4.setText("Gewicht na bereiding");
        jPanel6.add(jLabel4);

        jPanel3.setLayout(new java.awt.GridLayout());

        netWeightOutlet.setText("<netWeightOutlet>");
        netWeightOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                netWeightOutletKeyReleased(evt);
            }
        });
        jPanel3.add(netWeightOutlet);

        netWeightFormattedOutlet.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        netWeightFormattedOutlet.setText("<netWeightFormattedOutlet>");
        jPanel3.add(netWeightFormattedOutlet);

        jPanel6.add(jPanel3);

        jLabel7.setText("Kostprijs per kg (BTW excl)");
        jPanel6.add(jLabel7);

        pricePerWeightOutlet.setText("<pricePerWeightOutlet>");
        jPanel6.add(pricePerWeightOutlet);

        jPanel7.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(filler1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout());

        save.setText("Opslaan");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel2.add(save);

        cancel.setText("Terug");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel2.add(cancel);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel7.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel7, java.awt.BorderLayout.SOUTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingrediënten:"));

        ingredientsOutlet.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        ingredientsOutlet.setModel(new javax.swing.table.DefaultTableModel(
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
        ingredientsOutlet.setRowSelectionAllowed(false);
        ingredientsOutlet.setSurrendersFocusOnKeystroke(true);
        jScrollPane4.setViewportView(ingredientsOutlet);

        jPanel4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.BorderLayout());

        addComponent.setText("Toevoegen...");
        jPanel8.add(addComponent, java.awt.BorderLayout.EAST);

        jPanel4.add(jPanel8, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveActionPerformed

    private void netWeightOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_netWeightOutletKeyReleased
        //
	
	updatePricePerWeightOutlet();
    }//GEN-LAST:event_netWeightOutletKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addComponent;
    private javax.swing.JButton cancel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel grossWeightOutlet;
    private javax.swing.JTable ingredientsOutlet;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField nameOutlet;
    private javax.swing.JLabel netWeightFormattedOutlet;
    private javax.swing.JTextField netWeightOutlet;
    private javax.swing.JTextArea preparationOutlet;
    private javax.swing.JLabel pricePerWeightOutlet;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
