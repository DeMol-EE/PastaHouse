/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices;

import database.tables.Contact;
import gui.MasterDetailViewController;
import gui.TabbedViewController;
import gui.invoices.controllers.ArticleViewController;
import gui.invoices.controllers.ClientViewController;
import gui.invoices.controllers.InvoiceViewController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import pastahouse.ApplicationInvoices;

/**
 *
 * @author Hannes
 */
public class InvoiceTabbedViewController extends javax.swing.JPanel implements TabbedViewController{

    private final int invoicesTabIndex = 0;
    private final int articlesTabIndex = 1;
    private final int clientsTabIndex = 2;
    
    private HashMap<Integer, MasterDetailViewController> tabs;
    
    /**
     * Creates new form RecipeTabbedViewController
     */
    public InvoiceTabbedViewController(final ApplicationInvoices frame) {
        initComponents();
        
        tabs = new HashMap<Integer, MasterDetailViewController>();
	
	tabs.put(invoicesTabIndex, new InvoiceViewController());
	tabs.put(articlesTabIndex, new ArticleViewController());
	tabs.put(clientsTabIndex, new ClientViewController(this));
	
	invoiceTab.add(tabs.get(invoicesTabIndex).view());
	articleTab.add(tabs.get(articlesTabIndex).view());
	clientTab.add(tabs.get(clientsTabIndex).view());
	
	tabController.addChangeListener(new ChangeListener() {

	    @Override
	    public void stateChanged(ChangeEvent e) {
		tabs.get(tabController.getSelectedIndex()).electFirstResponder();
		frame.updateMenus();
	    }
	});
        
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

        tabMenu = new javax.swing.JMenu();
        invoiceMenuItem = new javax.swing.JMenuItem();
        articleMenuItem = new javax.swing.JMenuItem();
        clientMenuItem = new javax.swing.JMenuItem();
        tabController = new javax.swing.JTabbedPane();
        invoiceTab = new javax.swing.JPanel();
        articleTab = new javax.swing.JPanel();
        clientTab = new javax.swing.JPanel();

        tabMenu.setText("Tabs");

        invoiceMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        invoiceMenuItem.setText("Facturen");
        invoiceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invoiceMenuItemActionPerformed(evt);
            }
        });
        tabMenu.add(invoiceMenuItem);

        articleMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        articleMenuItem.setText("Artikels");
        articleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                articleMenuItemActionPerformed(evt);
            }
        });
        tabMenu.add(articleMenuItem);

        clientMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        clientMenuItem.setText("Klanten");
        clientMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientMenuItemActionPerformed(evt);
            }
        });
        tabMenu.add(clientMenuItem);

        setFocusable(false);
        setLayout(new java.awt.BorderLayout());

        tabController.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        tabController.setFocusable(false);

        invoiceTab.setFocusable(false);
        invoiceTab.setLayout(new java.awt.BorderLayout());
        tabController.addTab("Facturen", invoiceTab);

        articleTab.setFocusable(false);
        articleTab.setLayout(new java.awt.BorderLayout());
        tabController.addTab("Artikels", articleTab);

        clientTab.setFocusable(false);
        clientTab.setLayout(new java.awt.BorderLayout());
        tabController.addTab("Klanten", clientTab);

        add(tabController, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void invoiceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invoiceMenuItemActionPerformed
        tabController.setSelectedIndex(invoicesTabIndex);
    }//GEN-LAST:event_invoiceMenuItemActionPerformed

    private void articleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_articleMenuItemActionPerformed
        tabController.setSelectedIndex(articlesTabIndex);
    }//GEN-LAST:event_articleMenuItemActionPerformed

    private void clientMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientMenuItemActionPerformed
        tabController.setSelectedIndex(clientsTabIndex);
    }//GEN-LAST:event_clientMenuItemActionPerformed

    public void switchToInvoicesAndFilterByClient(Contact c){
	tabController.setSelectedIndex(invoicesTabIndex);
	((InvoiceViewController)tabs.get(invoicesTabIndex)).filterByClient(c);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem articleMenuItem;
    private javax.swing.JPanel articleTab;
    private javax.swing.JMenuItem clientMenuItem;
    private javax.swing.JPanel clientTab;
    private javax.swing.JMenuItem invoiceMenuItem;
    private javax.swing.JPanel invoiceTab;
    private javax.swing.JTabbedPane tabController;
    private javax.swing.JMenu tabMenu;
    // End of variables declaration//GEN-END:variables

    @Override
    public List<JMenu> menus() {
	List<JMenu> l = new ArrayList<JMenu>();
	l.add(tabMenu);
	l.add(tabs.get(tabController.getSelectedIndex()).menu());
	return l;
    }
}
