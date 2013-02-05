/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices.dialogs;

import database.Database;
import database.FunctionResult;
import database.extra.InvoiceItem;
import database.models.InvoiceModel;
import database.tables.Article;
import database.tables.Contact;
import database.tables.Invoice;
import gui.contacts.delegates.AddContactDelegate;
import gui.contacts.dialogs.AddContactDialog;
import gui.invoices.delegates.AddInvoiceDelegate;
import gui.utilities.DatePickerFactory;
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.combobox.AutocompleteCombobox;
import gui.utilities.table.invoicetable.InvoiceItemTableModel;
import gui.utilities.validation.AbstractValidator;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.JXTitledPanel;

/**
 *
 * @author Hannes
 */
public class AddInvoiceDialog extends javax.swing.JDialog implements AddContactDelegate {

    private AutocompleteCombobox clientBox;
    private JXDatePicker datepicker = DatePickerFactory.makeStandardDatePicker();
    private JXTable table;
    private ArrayList<InvoiceItem> data = new ArrayList<InvoiceItem>();
    private AutocompleteCombobox autobox;
    private InvoiceItemTableModel tablemodel;
    private String pricecode = "A";
    private Double saving = 0.0;
    private Contact client;
    private int number;

    public AddInvoiceDialog(java.awt.Frame parent, boolean modal, AddInvoiceDelegate delegate) {
        super(parent, modal);
        initComponents();
        comboPriceClass.addItem("A");
        comboPriceClass.addItem("B");
        DateOutlet.add(datepicker);
        datepicker.setDate(new Date());
        number = Database.driver().getInvoiceNumber(new Date());
        txtNumber.setText("" + number);
        table = createXTable();
        tablemodel = new InvoiceItemTableModel(data, pricecode);
        table.setModel(tablemodel);
        table.getColumns().get(0).setCellRenderer(CellRendererFactory.createIngredientCellRenderer());
        table.getColumns().get(1).setCellRenderer(CellRendererFactory.createZeroDecimalDoubleCellRenderer());
        table.getColumns().get(2).setCellRenderer(CellRendererFactory.createThreeDecimalDoubleCellRenderer());
        table.getColumns().get(3).setCellRenderer(CellRendererFactory.createThreeDecimalDoubleCellRenderer());
        table.getColumns().get(4).setCellRenderer(CellRendererFactory.createThreeDecimalDoubleCellRenderer());
        JScrollPane scrollpane = new JScrollPane(table);
        table.setName("invoiceTable");
        JXTitledPanel detailstitled = new JXTitledPanel("Details");
        detailstitled.add(detailspanel);
        detail.add(detailstitled, BorderLayout.NORTH);
        JXTitledPanel articlestitled = new JXTitledPanel("Artikelen");
        articlestitled.add(artikelspanel);
        detail.add(articlestitled, BorderLayout.CENTER);
        JXTitledPanel pricetitledpanel = new JXTitledPanel("Prijs");
        pricetitledpanel.add(pricepanel);
        detail.add(pricetitledpanel, BorderLayout.SOUTH);

        articlestablepanel.add(scrollpane, BorderLayout.CENTER);
        ArrayList clients = new ArrayList();
        clients.add("");
        clients.addAll(Database.driver().getClientsAlphabetically().values());
        clientBox = new AutocompleteCombobox(clients);
        ClientOutlet.add(clientBox, BorderLayout.CENTER);
        ArrayList articles = new ArrayList();
        articles.add(" ");
        articles.addAll(Database.driver().getArticlesAlphabetically().values());

        autobox = new AutocompleteCombobox(articles);
        choseartickleoutlet.add(autobox, BorderLayout.CENTER);
        this.pack();
        addValidators();
        setLocationRelativeTo(null);

        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientname = (String) clientBox.getSelectedItem();
                client = Database.driver().getClientsAlphabetically().get(clientname.toLowerCase());
                updatePriceClass(client.getPricecode());
            }
        });

        comboPriceClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePriceClass((String) comboPriceClass.getSelectedItem());
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detailspanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        DateOutlet = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel2 = new javax.swing.JLabel();
        ClientOutlet = new javax.swing.JPanel();
        addSupplier = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jLabel3 = new javax.swing.JLabel();
        comboPriceClass = new javax.swing.JComboBox();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel15 = new javax.swing.JLabel();
        txtReduction = new javax.swing.JTextField();
        artikelspanel = new javax.swing.JPanel();
        articlestablepanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        addArticlespanel = new javax.swing.JPanel();
        addarticlechooserpanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        choseartickleoutlet = new javax.swing.JPanel();
        quantityoutlet = new javax.swing.JTextField();
        addarticlesbuttonpanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        addArticle = new javax.swing.JButton();
        deleteArticle = new javax.swing.JButton();
        pricepanel = new javax.swing.JPanel();
        taxespanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        totalpricepanel = new javax.swing.JPanel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        detail = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        detailspanel.setLayout(new java.awt.GridLayout(0, 5));

        jLabel4.setText("Datum");
        detailspanel.add(jLabel4);

        DateOutlet.setLayout(new java.awt.BorderLayout());
        detailspanel.add(DateOutlet);
        detailspanel.add(filler1);

        jLabel2.setText("Klant");
        detailspanel.add(jLabel2);

        ClientOutlet.setLayout(new java.awt.BorderLayout());

        addSupplier.setText("+");
        addSupplier.setToolTipText("Klik hier om een nieuwe leverancier toe te voegen.");
        addSupplier.setFocusable(false);
        addSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSupplierActionPerformed(evt);
            }
        });
        ClientOutlet.add(addSupplier, java.awt.BorderLayout.EAST);

        detailspanel.add(ClientOutlet);

        jLabel6.setText("Nummer");
        detailspanel.add(jLabel6);
        detailspanel.add(txtNumber);
        detailspanel.add(filler2);

        jLabel3.setText("Prijsklasse");
        detailspanel.add(jLabel3);
        detailspanel.add(comboPriceClass);
        detailspanel.add(filler5);
        detailspanel.add(filler6);
        detailspanel.add(filler7);

        jLabel15.setText("Korting");
        detailspanel.add(jLabel15);

        txtReduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReductionActionPerformed(evt);
            }
        });
        detailspanel.add(txtReduction);

        artikelspanel.setLayout(new java.awt.BorderLayout());

        articlestablepanel.setLayout(new java.awt.BorderLayout());

        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        articlestablepanel.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        artikelspanel.add(articlestablepanel, java.awt.BorderLayout.CENTER);

        addArticlespanel.setLayout(new java.awt.BorderLayout());

        addarticlechooserpanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setLayout(new java.awt.BorderLayout());

        choseartickleoutlet.setLayout(new java.awt.BorderLayout());
        jPanel5.add(choseartickleoutlet, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5);

        quantityoutlet.setPreferredSize(new java.awt.Dimension(200, 20));
        quantityoutlet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityoutletActionPerformed(evt);
            }
        });
        jPanel2.add(quantityoutlet);

        addarticlechooserpanel.add(jPanel2, java.awt.BorderLayout.EAST);

        addArticlespanel.add(addarticlechooserpanel, java.awt.BorderLayout.PAGE_START);

        addarticlesbuttonpanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        addArticle.setText("Toevoegen");
        addArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addArticleActionPerformed(evt);
            }
        });
        jPanel1.add(addArticle);

        deleteArticle.setText("Verwijderen");
        deleteArticle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteArticleActionPerformed(evt);
            }
        });
        jPanel1.add(deleteArticle);

        addarticlesbuttonpanel.add(jPanel1, java.awt.BorderLayout.EAST);

        addArticlespanel.add(addarticlesbuttonpanel, java.awt.BorderLayout.CENTER);

        artikelspanel.add(addArticlespanel, java.awt.BorderLayout.SOUTH);

        pricepanel.setLayout(new java.awt.BorderLayout());

        taxespanel.setLayout(new java.awt.GridLayout(0, 1));

        jLabel1.setText("BTW%");
        taxespanel.add(jLabel1);

        jLabel5.setText("Bedrag");
        taxespanel.add(jLabel5);

        jLabel7.setText("Korting");
        taxespanel.add(jLabel7);

        jLabel8.setText("Exclu");
        taxespanel.add(jLabel8);

        jLabel9.setText("BTW");
        taxespanel.add(jLabel9);

        jLabel10.setText("Totaal");
        taxespanel.add(jLabel10);

        pricepanel.add(taxespanel, java.awt.BorderLayout.WEST);

        totalpricepanel.setLayout(new java.awt.GridLayout(0, 1));
        totalpricepanel.add(filler4);
        totalpricepanel.add(filler3);

        jLabel11.setText("Tot Korting");
        totalpricepanel.add(jLabel11);

        jLabel12.setText("Tot Exclu");
        totalpricepanel.add(jLabel12);

        jLabel13.setText("BTW");
        totalpricepanel.add(jLabel13);

        jLabel14.setText("Totaal");
        totalpricepanel.add(jLabel14);

        pricepanel.add(totalpricepanel, java.awt.BorderLayout.EAST);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        detail.setLayout(new java.awt.BorderLayout());
        getContentPane().add(detail, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        btnSave.setText("Opslaan");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel4.add(btnSave);

        btnBack.setText("Terug");
        jPanel4.add(btnBack);

        jPanel3.add(jPanel4, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSupplierActionPerformed
        AddContactDialog.createSupplierDialog(this).setVisible(true);
    }//GEN-LAST:event_addSupplierActionPerformed

    private void txtReductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReductionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtReductionActionPerformed

    private void deleteArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteArticleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteArticleActionPerformed

    private void quantityoutletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityoutletActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityoutletActionPerformed

    private void addArticleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addArticleActionPerformed
        Article art;
        art = Database.driver().getArticlesAlphabetically().get((String) autobox.getSelectedItem());
        double quantity = Double.parseDouble(quantityoutlet.getText());
        InvoiceItem item = new InvoiceItem(art, quantity);
        data.add(item);
        tablemodel.addComponent(item);
    }//GEN-LAST:event_addArticleActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        InvoiceModel model = new InvoiceModel();
        model.setDate(datepicker.getDate().toString());
        model.setClient(client);
        model.setNumber(number);
        model.setItems(data);
        model.setPriceCode(pricecode);
        model.setSave(saving);
        FunctionResult<Invoice> result = model.create();
        int code = result.getCode();
        if (code == 0){
            
        } else {
            //JOptionPane.showMessageDialog(ClientOutlet, result);
        }
    }//GEN-LAST:event_btnSaveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ClientOutlet;
    private javax.swing.JPanel DateOutlet;
    private javax.swing.JButton addArticle;
    private javax.swing.JPanel addArticlespanel;
    private javax.swing.JButton addSupplier;
    private javax.swing.JPanel addarticlechooserpanel;
    private javax.swing.JPanel addarticlesbuttonpanel;
    private javax.swing.JPanel articlestablepanel;
    private javax.swing.JPanel artikelspanel;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel choseartickleoutlet;
    private javax.swing.JComboBox comboPriceClass;
    private javax.swing.JButton deleteArticle;
    private javax.swing.JPanel detail;
    private javax.swing.JPanel detailspanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pricepanel;
    private javax.swing.JTextField quantityoutlet;
    private javax.swing.JPanel taxespanel;
    private javax.swing.JPanel totalpricepanel;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtReduction;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addContact(Contact c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private JXTable createXTable() {
        JXTable table = new JXTable() {
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new JXTableHeader(columnModel) {
                    @Override
                    public void updateUI() {
                        super.updateUI();
                        // need toPicker do in updateUI toPicker survive toggling of LAF 
                        if (getDefaultRenderer() instanceof JLabel) {
                            ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

                        }
                    }
                    //                    </snip> 
                };
            }
        };
        return table;
    }

    private void addValidators() {
        quantityoutlet.setInputVerifier(new AbstractValidator(this, quantityoutlet, "Ongeldige waarde! Verwacht formaat: x.y, groter dan 0.0") {
            @Override
            protected boolean validationCriteria(JComponent c) {
                if (autobox.getSelectedIndex() == 0) {
                    return true;
                }
                try {
                    return Double.parseDouble(((JTextField) c).getText()) > 0;
                } catch (Exception e) {
                    return false;
                }
            }
        });
    }

    private void updatePriceClass(String newprice) {
        String oldprice = pricecode;
        pricecode = newprice;
        if (!oldprice.equals(pricecode)) {
            comboPriceClass.setSelectedItem(pricecode);
            tablemodel.updatePricecode(pricecode);
        }


    }
}
