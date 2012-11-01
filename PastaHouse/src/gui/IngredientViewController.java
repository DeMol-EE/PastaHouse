/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IngredientViewController.java
 *
 * Created on Oct 24, 2012, 3:50:31 PM
 */
package gui;

import database.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import pastahouse.Application;

/**
 *
 * @author Warkst
 */
public class IngredientViewController extends javax.swing.JPanel implements ViewController{

    private Application application;
    
    private boolean editing = false;
    
    /** Creates new form IngredientViewController */
    public IngredientViewController(Application application) {
	this.application = application;
	
	initComponents();
	
	listOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	listOutlet.addListSelectionListener(new ListSelectionListener() {

	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
		    updateDetail(listOutlet.getSelectedValue());
		}
	    }
	});

	// load from the db
	Map<Integer, BasicIngredient> bi = Database.driver().getBasicIngredients();
	Map<String, BasicIngredient> bis = new TreeMap<String, BasicIngredient>();
	
	for (Map.Entry<Integer, BasicIngredient> entry : bi.entrySet()) {
	    bis.put(entry.getValue().getName(), entry.getValue());
	}

	listOutlet.setModel(ListModelFactory.createBasicIngredientModel(bis));
	listOutlet.setSelectedIndex(0);
    }
    
    private void updateDetail(Object value){
	BasicIngredient bi = (BasicIngredient)value;
	
	DecimalFormat threeFormatter = new DecimalFormat("0.000");
	DecimalFormat twoFormatter = new DecimalFormat("0.00");
	
	supplierOutlet.setText(Utilities.capitalize(bi.getSupplier().getFirm()));
	if(bi.getSupplier().isDeleted()){
	    supplierOutlet.setForeground(Color.RED);
	    supplierOutlet.setCursor(Cursor.getDefaultCursor());
	    for (MouseListener mouseListener : supplierOutlet.getMouseListeners()) {
		supplierOutlet.removeMouseListener(mouseListener);
	    }
	} else {
	    supplierOutlet.setForeground(Color.BLUE);
	    supplierOutlet.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    supplierOutlet.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
		    supplierOutletMouseReleased(e);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		    supplierOutletMouseEntered(e);
		}
		@Override
		public void mouseExited(MouseEvent e) {
		    supplierOutletMouseExited(e);
		}
	    });
	}
	
	//€-sign?
	nameOutlet.setText(Utilities.capitalize(bi.getName()));
	brandOutlet.setText(Utilities.capitalize(bi.getBrand()));
	packagingOutlet.setText(Utilities.capitalize(bi.getPackaging()));
	pricePerUnitOutlet.setText(""+threeFormatter.format(bi.getPricePerUnit())+" euro / "+bi.getPackaging());
	weightPerUnitOutlet.setText(""+threeFormatter.format(bi.getWeightPerUnit())+" kg / "+bi.getPackaging());
	pricePerWeightOutlet.setText(""+threeFormatter.format(bi.getPricePerWeight())+" euro / kg");
	lossPercentOutlet.setText(""+twoFormatter.format(bi.getLossPercent())+" %");
	grossPriceOutlet.setText(""+threeFormatter.format(bi.getGrossPrice())+" euro / kg");
	taxesOutlet.setText(""+twoFormatter.format(bi.getTaxes())+" %");
	netPriceOutlet.setText(""+twoFormatter.format(bi.getNetPrice())+" euro / kg");
	dateOutlet.setText(bi.getDate());
    }
    
    @Override
    public JPanel view(){
	return this;
    }
    
    private void supplierOutletMouseReleased(java.awt.event.MouseEvent evt) {                                             
	BasicIngredient selectedIngredient = (BasicIngredient)listOutlet.getSelectedValue();
	application.selectAndSwitchToSupplier(selectedIngredient.getSupplier());
    }                                            

    private void supplierOutletMouseEntered(java.awt.event.MouseEvent evt) {                                            
        supplierOutlet.setText("<html><u>"+supplierOutlet.getText()+"</u></html>");
    }                                           

    private void supplierOutletMouseExited(java.awt.event.MouseEvent evt) {                                           
        BasicIngredient bi = (BasicIngredient)listOutlet.getSelectedValue();
	String firm = bi.getSupplier().getFirm();
	supplierOutlet.setText(firm.substring(0,1).toUpperCase()+firm.substring(1).toLowerCase());
    }       

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editOutlet = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        master = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOutlet = new javax.swing.JList();
        detail = new javax.swing.JPanel();
        fixedFields = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameOutlet = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        brandOutlet = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        packagingOutlet = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        supplierOutlet = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        pricePerUnitOutlet = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        weightPerUnitOutlet = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        pricePerWeightOutlet = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lossPercentOutlet = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        grossPriceOutlet = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        taxesOutlet = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        netPriceOutlet = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        dateOutlet = new javax.swing.JLabel();
        stretchableFields = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesOutlet = new javax.swing.JTextArea();

        editOutlet.setText("Edit");
        editOutlet.setFocusable(false);
        editOutlet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOutletActionPerformed(evt);
            }
        });

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);

        master.setBackground(new java.awt.Color(255, 255, 153));
        master.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Toevoegen...");
        jButton1.setFocusable(false);
        master.add(jButton1, java.awt.BorderLayout.SOUTH);

        listOutlet.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listOutlet);

        master.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(master);

        detail.setBackground(new java.awt.Color(153, 153, 255));
        detail.setLayout(new java.awt.BorderLayout());

        fixedFields.setFocusable(false);
        fixedFields.setLayout(new java.awt.GridLayout(12, 2, 0, 5));

        jLabel1.setText("Naam");
        jLabel1.setFocusable(false);
        fixedFields.add(jLabel1);

        nameOutlet.setText("<nameOutlet>");
        nameOutlet.setFocusable(false);
        fixedFields.add(nameOutlet);

        jLabel2.setText("Merk");
        jLabel2.setFocusable(false);
        fixedFields.add(jLabel2);

        brandOutlet.setText("<brandOutlet>");
        brandOutlet.setFocusable(false);
        fixedFields.add(brandOutlet);

        jLabel6.setText("Verpakking");
        jLabel6.setFocusable(false);
        fixedFields.add(jLabel6);

        packagingOutlet.setText("<packagingOutlet>");
        packagingOutlet.setFocusable(false);
        fixedFields.add(packagingOutlet);

        jLabel3.setText("Leverancier");
        jLabel3.setFocusable(false);
        fixedFields.add(jLabel3);

        supplierOutlet.setForeground(new java.awt.Color(0, 0, 255));
        supplierOutlet.setText("<supplierOutlet>");
        supplierOutlet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        supplierOutlet.setFocusable(false);
        fixedFields.add(supplierOutlet);

        jLabel10.setText("Prijs per verpakking (BTW excl)");
        jLabel10.setFocusable(false);
        fixedFields.add(jLabel10);

        pricePerUnitOutlet.setText("<pricePerUnitOutlet>");
        pricePerUnitOutlet.setFocusable(false);
        fixedFields.add(pricePerUnitOutlet);

        jLabel8.setText("Gewicht per verpakking");
        jLabel8.setFocusable(false);
        fixedFields.add(jLabel8);

        weightPerUnitOutlet.setText("<weightPerUnitOutlet>");
        weightPerUnitOutlet.setFocusable(false);
        fixedFields.add(weightPerUnitOutlet);

        jLabel12.setText("Prijs per kg (BTW excl)");
        jLabel12.setFocusable(false);
        fixedFields.add(jLabel12);

        pricePerWeightOutlet.setText("<pricePerWeightOutlet>");
        pricePerWeightOutlet.setFocusable(false);
        fixedFields.add(pricePerWeightOutlet);

        jLabel14.setText("Verliespercentage");
        jLabel14.setFocusable(false);
        fixedFields.add(jLabel14);

        lossPercentOutlet.setText("<lossPercentOutlet>");
        lossPercentOutlet.setFocusable(false);
        fixedFields.add(lossPercentOutlet);

        jLabel16.setText("Totaalprijs (BTW excl)");
        jLabel16.setFocusable(false);
        fixedFields.add(jLabel16);

        grossPriceOutlet.setText("<grossPriceOutlet>");
        grossPriceOutlet.setFocusable(false);
        fixedFields.add(grossPriceOutlet);

        jLabel18.setText("BTW");
        jLabel18.setFocusable(false);
        fixedFields.add(jLabel18);

        taxesOutlet.setText("<taxesOutlet>");
        taxesOutlet.setFocusable(false);
        fixedFields.add(taxesOutlet);

        jLabel20.setText("Totaalprijs");
        jLabel20.setFocusable(false);
        fixedFields.add(jLabel20);

        netPriceOutlet.setText("<netPriceOutlet>");
        netPriceOutlet.setFocusable(false);
        fixedFields.add(netPriceOutlet);

        jLabel22.setText("Datum");
        jLabel22.setFocusable(false);
        fixedFields.add(jLabel22);

        dateOutlet.setText("<dateOutlet>");
        dateOutlet.setFocusable(false);
        fixedFields.add(dateOutlet);

        detail.add(fixedFields, java.awt.BorderLayout.NORTH);

        stretchableFields.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opmerking:"));

        notesOutlet.setColumns(20);
        notesOutlet.setRows(5);
        notesOutlet.setFocusable(false);
        jScrollPane2.setViewportView(notesOutlet);

        stretchableFields.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        detail.add(stretchableFields, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(detail);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void editOutletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editOutletActionPerformed
        
	// input test:
//	try{
//	    Double.parseDouble(priceOutlet.getText());
//	} catch(Exception e){
//	    // show error dialog and return
//	    JOptionPane.showMessageDialog(null, "Enter a valid price (\""+priceOutlet.getText()+"\" is not a valid value)!", "Error: invalid price", JOptionPane.ERROR_MESSAGE);
//	    return;
//	}
	
	
	editing = !editing;
	listOutlet.setFocusable(!editing);
//	nameOutlet.setEnabled(editing);
//	priceOutlet.setEnabled(editing);
	if(editing) {
//	    nameOutlet.requestFocus();
	    editOutlet.setText("Save");
	} else {
	    listOutlet.requestFocus();
	    editOutlet.setText("Edit");
	    // save changes to dynamic memory
	    // get values from outlets and save them to the selected object
	    
	    if(listOutlet.getSelectedValue().getClass() == BasicIngredient.class){
		BasicIngredient c = (BasicIngredient)listOutlet.getSelectedValue();
//		c.setName(nameOutlet.getText());
//		c.setPrice(Double.parseDouble(priceOutlet.getText()));
	    }
	    
	    // save changes to the db!
	    Ingredient c = (Ingredient)listOutlet.getSelectedValue();
	    try {
		c.update();
	    } catch (Exception ex) {
		Logger.getLogger(IngredientViewController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }//GEN-LAST:event_editOutletActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel brandOutlet;
    private javax.swing.JLabel dateOutlet;
    private javax.swing.JPanel detail;
    private javax.swing.JButton editOutlet;
    private javax.swing.JPanel fixedFields;
    private javax.swing.JLabel grossPriceOutlet;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList listOutlet;
    private javax.swing.JLabel lossPercentOutlet;
    private javax.swing.JPanel master;
    private javax.swing.JLabel nameOutlet;
    private javax.swing.JLabel netPriceOutlet;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JLabel packagingOutlet;
    private javax.swing.JLabel pricePerUnitOutlet;
    private javax.swing.JLabel pricePerWeightOutlet;
    private javax.swing.JPanel stretchableFields;
    private javax.swing.JLabel supplierOutlet;
    private javax.swing.JLabel taxesOutlet;
    private javax.swing.JLabel weightPerUnitOutlet;
    // End of variables declaration//GEN-END:variables
}