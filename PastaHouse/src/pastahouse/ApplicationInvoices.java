/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pastahouse;

import gui.contacts.ContactsTabbedViewController;
import gui.ingredients.RecipeTabbedViewController;
import gui.invoices.InvoiceTabbedViewController;
import java.awt.Font;
import javax.swing.UIManager;
import utilities.Configuration;
import utilities.Utilities;

/**
 *
 * @author Hannes
 */
public class ApplicationInvoices extends javax.swing.JFrame {

    private final RecipeTabbedViewController rtvc;
    private final InvoiceTabbedViewController itvc;
    private final ContactsTabbedViewController ctvc;


    /**
     * Creates new form ApplicationInvoices
     */
    public ApplicationInvoices() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Pasta House");

        rtvc = new RecipeTabbedViewController();
        itvc = new InvoiceTabbedViewController();
        ctvc = new ContactsTabbedViewController();


        getContentPane().removeAll();
        getContentPane().add(rtvc);
        getContentPane().add(itvc);
        getContentPane().add(ctvc);
        validate();

        getContentPane().removeAll();
        getContentPane().add(buttonPanel);
        validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonPanel = new javax.swing.JPanel();
        fillerWest = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        fillerEast = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        fillerNorth = new javax.swing.Box.Filler(new java.awt.Dimension(0, 40), new java.awt.Dimension(0, 40), new java.awt.Dimension(32767, 40));
        fillerSouth = new javax.swing.Box.Filler(new java.awt.Dimension(0, 40), new java.awt.Dimension(0, 40), new java.awt.Dimension(32767, 40));
        jPanel1 = new javax.swing.JPanel();
        btnRecipes = new javax.swing.JButton();
        btnInvoices = new javax.swing.JButton();
        btnContacts = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ResizeHandler(evt);
            }
        });

        buttonPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        buttonPanel.add(fillerWest, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        buttonPanel.add(fillerEast, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        buttonPanel.add(fillerNorth, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        buttonPanel.add(fillerSouth, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        btnRecipes.setText("Recepten");
        btnRecipes.setFocusable(false);
        btnRecipes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecipesActionPerformed(evt);
            }
        });
        jPanel1.add(btnRecipes);

        btnInvoices.setText("Facturatie");
        btnInvoices.setFocusable(false);
        btnInvoices.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvoicesActionPerformed(evt);
            }
        });
        jPanel1.add(btnInvoices);

        btnContacts.setText("Adressenboek");
        btnContacts.setFocusable(false);
        btnContacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContactsActionPerformed(evt);
            }
        });
        jPanel1.add(btnContacts);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        buttonPanel.add(jPanel1, gridBagConstraints);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        jMenuItem1.setText("Home");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecipesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecipesActionPerformed
        getContentPane().removeAll();
        getContentPane().add(rtvc);
        validate();
        repaint();
    }//GEN-LAST:event_btnRecipesActionPerformed

    private void ResizeHandler(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ResizeHandler
    }//GEN-LAST:event_ResizeHandler

    private void btnInvoicesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvoicesActionPerformed
        getContentPane().removeAll();
        getContentPane().add(itvc);
        validate();
        repaint();
    }//GEN-LAST:event_btnInvoicesActionPerformed

    private void btnContactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContactsActionPerformed
        getContentPane().removeAll();
        getContentPane().add(ctvc);
        validate();
        repaint();
    }//GEN-LAST:event_btnContactsActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        getContentPane().removeAll();
        getContentPane().add(buttonPanel);
        validate();
        repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApplicationInvoices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplicationInvoices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplicationInvoices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplicationInvoices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        database.Database.driver();

        UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Arial", Font.PLAIN, Utilities.fontSize()));

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ApplicationInvoices().setVisible(true);
            }
        });

        System.out.println(Configuration.center().getDB_URL());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContacts;
    private javax.swing.JButton btnInvoices;
    private javax.swing.JButton btnRecipes;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.Box.Filler fillerEast;
    private javax.swing.Box.Filler fillerNorth;
    private javax.swing.Box.Filler fillerSouth;
    private javax.swing.Box.Filler fillerWest;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
