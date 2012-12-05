/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices;

import database.tables.Supplier;
import gui.TabbedViewController;
import gui.ingredients.controllers.MasterDetailViewController;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 *
 * @author Hannes
 */
public class InvoiceTabbedViewController extends javax.swing.JPanel implements TabbedViewController{

    private final int articlesTabIndex = 0;
    private final int clientsTabIndex = 1;
    private HashMap<Integer, MasterDetailViewController> tabs;
    
    /**
     * Creates new form RecipeTabbedViewController
     */
    public InvoiceTabbedViewController() {
        initComponents();
        
        tabs = new HashMap<Integer, MasterDetailViewController>();
        
    }
    
    public void selectAndSwitchToSupplier(Supplier supplier){
    }
    
    @Override
    public JPanel view(){
        return this;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
