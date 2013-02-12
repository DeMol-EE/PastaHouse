/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices.dialogs;

import database.FunctionResult;
import database.tables.Article;
import gui.invoices.delegates.EditArticleDelegate;
import gui.utilities.AcceleratorAdder;
import gui.utilities.KeyAction;
import gui.utilities.TextFieldAutoHighlighter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXTitledPanel;

/**
 *
 * @author Warkst
 */
public class EditArticleDialog extends javax.swing.JDialog {

    private final EditArticleDelegate delegate;
    private final Article model;
    private final Article defaultModel;
    
    /**
     * Creates new form EditArticleDialog
     */
    public EditArticleDialog(java.awt.Frame parent, boolean modal, EditArticleDelegate delegate, Article model) {
	super(parent, modal);
	initComponents();
	
	this.delegate = delegate;
	this.model = model;
	this.defaultModel = new Article(model);
	
	setLocationRelativeTo(null);
	
	TextFieldAutoHighlighter.installHighlighter(nameOutlet);
	TextFieldAutoHighlighter.installHighlighter(codeOutlet);
	TextFieldAutoHighlighter.installHighlighter(priceAOutlet);
	TextFieldAutoHighlighter.installHighlighter(priceBOutlet);
	TextFieldAutoHighlighter.installHighlighter(unitOutlet);
	TextFieldAutoHighlighter.installHighlighter(taxesOutlet);
	
	AcceleratorAdder.addAccelerator(cancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), new KeyAction() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		cancelActionPerformed(e);
	    }
	});
	
	container.add(new JXTitledPanel("Details", jPanel1), BorderLayout.NORTH);
	container.add(new JXTitledPanel("Opmerkingen", jScrollPane2), BorderLayout.CENTER);
	
	loadModel();
    }
    
    private void loadModel(){
	nameOutlet.setText(model.getName());
	codeOutlet.setText(model.getCode());
	priceAOutlet.setText(""+model.getPriceA());
	priceBOutlet.setText(""+model.getPriceB());
	unitOutlet.setText(model.getUnit());
	taxesOutlet.setText(""+model.getTaxes());
	
	updatePriceAFormattedOutlet();
	updatePriceBFormattedOutlet();
	updateTaxesFormattedOutlet();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameOutlet = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        codeOutlet = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        priceAOutlet = new javax.swing.JTextField();
        priceAFormattedOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        priceBOutlet = new javax.swing.JTextField();
        priceBFormattedOutlet = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        unitOutlet = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        taxesOutlet = new javax.swing.JTextField();
        taxesFormattedOutlet = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        container = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        jPanel1.setLayout(new java.awt.GridLayout(6, 2));

        jLabel1.setText("Naam");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel1.setFocusable(false);
        jPanel1.add(jLabel1);
        jPanel1.add(nameOutlet);

        jLabel3.setText("Code");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel3.setFocusable(false);
        jLabel3.setOpaque(true);
        jPanel1.add(jLabel3);
        jPanel1.add(codeOutlet);

        jLabel5.setText("Prijs A (Excl BTW)");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel5.setFocusable(false);
        jPanel1.add(jLabel5);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        priceAOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                priceAOutletKeyReleased(evt);
            }
        });
        jPanel4.add(priceAOutlet);

        priceAFormattedOutlet.setText("<priceAFormattedOutlet>");
        priceAFormattedOutlet.setFocusable(false);
        jPanel4.add(priceAFormattedOutlet);

        jPanel1.add(jPanel4);

        jLabel7.setText("Prijs B (Excl BTW)");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel7.setFocusable(false);
        jLabel7.setOpaque(true);
        jPanel1.add(jLabel7);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        priceBOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                priceBOutletKeyReleased(evt);
            }
        });
        jPanel5.add(priceBOutlet);

        priceBFormattedOutlet.setText("<priceBFormattedOutlet>");
        priceBFormattedOutlet.setFocusable(false);
        jPanel5.add(priceBFormattedOutlet);

        jPanel1.add(jPanel5);

        jLabel9.setText("Eenheid");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel9.setFocusable(false);
        jPanel1.add(jLabel9);
        jPanel1.add(unitOutlet);

        jLabel11.setText("BTW tarief");
        jLabel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel11.setFocusable(false);
        jLabel11.setOpaque(true);
        jPanel1.add(jLabel11);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        taxesOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                taxesOutletKeyReleased(evt);
            }
        });
        jPanel6.add(taxesOutlet);

        taxesFormattedOutlet.setText("<taxesFormattedOutlet>");
        taxesFormattedOutlet.setFocusable(false);
        jPanel6.add(taxesFormattedOutlet);

        jPanel1.add(jPanel6);

        jScrollPane2.setBorder(null);

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(1);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 500));

        container.setLayout(new java.awt.BorderLayout());
        getContentPane().add(container, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        save.setText("Opslaan");
        save.setFocusable(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel3.add(save);

        cancel.setText("Terug");
        cancel.setFocusable(false);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel3.add(cancel);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void priceAOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceAOutletKeyReleased
        updatePriceAFormattedOutlet();
    }//GEN-LAST:event_priceAOutletKeyReleased

    private void priceBOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_priceBOutletKeyReleased
        updatePriceBFormattedOutlet();
    }//GEN-LAST:event_priceBOutletKeyReleased

    private void taxesOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taxesOutletKeyReleased
        updateTaxesFormattedOutlet();
    }//GEN-LAST:event_taxesOutletKeyReleased

    private void updatePriceAFormattedOutlet(){
	try{
	    double d = Double.parseDouble(priceAOutlet.getText());
	    priceAOutlet.setForeground(Color.black);
	    priceAFormattedOutlet.setForeground(Color.black);
	    priceAFormattedOutlet.setText(new DecimalFormat("0.000").format(d)+" euro");
	} catch (Exception e) {
	    priceAOutlet.setForeground(Color.red);
	    priceAFormattedOutlet.setForeground(Color.red);
	    priceAFormattedOutlet.setText("???");
	}
    }
    
    private void updatePriceBFormattedOutlet(){
	try{
	    double d = Double.parseDouble(priceBOutlet.getText());
	    priceBOutlet.setForeground(Color.black);
	    priceBFormattedOutlet.setForeground(Color.black);
	    priceBFormattedOutlet.setText(new DecimalFormat("0.000").format(d)+" euro");
	} catch (Exception e) {
	    priceBOutlet.setForeground(Color.red);
	    priceBFormattedOutlet.setForeground(Color.red);
	    priceBFormattedOutlet.setText("???");
	}
    }
    
    private void updateTaxesFormattedOutlet(){
	try{
	    double d = Double.parseDouble(taxesOutlet.getText());
	    taxesOutlet.setForeground(Color.black);
	    taxesFormattedOutlet.setForeground(Color.black);
	    taxesFormattedOutlet.setText(new DecimalFormat("0.00").format(d)+" %");
	} catch (Exception e) {
	    taxesOutlet.setForeground(Color.red);
	    taxesFormattedOutlet.setForeground(Color.red);
	    taxesFormattedOutlet.setText("???");
	}
    }
    
    private void disposeLater(){
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		dispose();
	    }
	});
    }
    
    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try{
            if (nameOutlet.getText().isEmpty()
                || codeOutlet.getText().isEmpty()
                || priceAOutlet.getText().isEmpty()
                || priceBOutlet.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, tools.Utilities.incompleteFormMessage, "Fout!", JOptionPane.WARNING_MESSAGE);
                return;
            }
	    
	    if (!database.Database.driver().isArticleCodeUnique(codeOutlet.getText(), model)) {
		JOptionPane.showMessageDialog(null, "Deze artikelcode is niet uniek!", "Fout!", JOptionPane.WARNING_MESSAGE);
		return;
	    }

            /*
            * Set values on the model
            */
            model.setName(nameOutlet.getText());
            model.setCode(codeOutlet.getText());
            model.setPriceA(Double.parseDouble(priceAOutlet.getText()));
            model.setPriceB(Double.parseDouble(priceBOutlet.getText()));
            model.setUnit(unitOutlet.getText());
            model.setTaxes(Double.parseDouble(taxesOutlet.getText()));

	    FunctionResult<Article> res = model.update();
	    if (res.getCode() == 0) {
		delegate.editArticle(defaultModel, model);
		disposeLater();
	    } else {
		String msg;
		switch(res.getCode()){
		    case 1: case 2:
			msg = res.getMessage();
			break;
		    default:
			msg = "Er is een fout opgetreden bij het opslaan van dit artikel in de databank (code "+res.getCode()+"). Contacteer de ontwikkelaars met deze informatie.";
		}
		JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
	    }
        } catch (Exception e){
            System.err.println("Error: \n"+e.getMessage());
            JOptionPane.showMessageDialog(null, tools.Utilities.incorrectFormMessage, "Fout!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_saveActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        model.copy(defaultModel);
	disposeLater();
    }//GEN-LAST:event_cancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JTextField codeOutlet;
    private javax.swing.JPanel container;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField nameOutlet;
    private javax.swing.JLabel priceAFormattedOutlet;
    private javax.swing.JTextField priceAOutlet;
    private javax.swing.JLabel priceBFormattedOutlet;
    private javax.swing.JTextField priceBOutlet;
    private javax.swing.JButton save;
    private javax.swing.JLabel taxesFormattedOutlet;
    private javax.swing.JTextField taxesOutlet;
    private javax.swing.JTextField unitOutlet;
    // End of variables declaration//GEN-END:variables
}
