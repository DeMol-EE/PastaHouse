/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices.controllers;

import database.tables.Invoice;
import gui.MasterDetailViewController;
import javax.swing.JPanel;

/**
 *
 * @author Warkst
 */
public class InvoiceViewController extends javax.swing.JPanel implements MasterDetailViewController<Invoice> {

    /**
     * Creates new form InvoiceViewController
     */
    public InvoiceViewController() {
	initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableOutlet = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        tableOutlet.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableOutlet);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableOutlet;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateDetail(Invoice value) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addAndSelect(Invoice newObj) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void editAndSelect(Invoice newObj, Invoice oldObj) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addProxy() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void editProxy() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void electFirstResponder() {
	tableOutlet.requestFocus();
    }

    @Override
    public JPanel view() {
	return this;
    }
}
