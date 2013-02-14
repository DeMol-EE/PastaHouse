/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices.controllers;

import database.Database;
import database.tables.Invoice;
import gui.MasterDetailViewController;
import gui.invoices.delegates.AddInvoiceDelegate;
import gui.invoices.dialogs.AddInvoiceDialog;
import gui.invoices.dialogs.InvoiceDetailsDialog;
import gui.utilities.DatePickerFactory;
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.table.invoicetable.CustomColumnFactory;
import gui.utilities.table.invoicetable.InvoiceFiltering;
import gui.utilities.table.invoicetable.InvoiceRendering;
import gui.utilities.table.invoicetable.InvoiceTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.SortOrder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import printer.MultiPrintable;
import printer.printables.PrintableInvoiceNew;
import tools.Utilities;

/**
 *
 * @author Warkst
 */
public class InvoiceViewController extends javax.swing.JPanel implements MasterDetailViewController<Invoice>, AddInvoiceDelegate {

    private Map<String, RowFilter<Object, Object>> filters;
    private TableRowSorter<InvoiceTableModel> sorter;
    private InvoiceTableModel tableModel;
    private JXTable table;
    private Map<Integer, Invoice> invoicesByID;
    private JXDatePicker fromPicker;
    private JXDatePicker toPicker;
    private InvoiceFiltering filterController;
    private JXTitledPanel filterpanel;
    private JXTitledPanel invoicespanel;

    /**
     * Creates new form InvoiceViewController
     */
    public InvoiceViewController() {

        initComponents();
        invoicesByID = Database.driver().getInvoicesById();
        tablePanel.add(controlPanel, BorderLayout.NORTH);
        table = createXTable();
        JScrollPane scrollpane = new JScrollPane(table);
        table.setName("invoiceTable");
        table.setRowHeight(table.getRowHeight() + Utilities.fontSize() - 10);

        configureDisplayProperties();
        fromPicker = DatePickerFactory.makeStandardDatePicker();
        fromPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterController.setFromDate(fromPicker.getDate());
            }
        });
        toPicker = DatePickerFactory.makeStandardDatePicker();
        toPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterController.setToDate(toPicker.getDate());
            }
        });
        fromOutlet.add(fromPicker);
        toOutlet.add(toPicker);
        filterpanel = new JXTitledPanel("Filter");
        invoicespanel = new JXTitledPanel("Facturen");
        filterpanel.add(controlPanel);
        tablePanel.add(invoicespanel, BorderLayout.CENTER);
        invoicespanel.add(scrollpane, BorderLayout.CENTER);

        final JXCollapsiblePane filtercollapser = new JXCollapsiblePane(JXCollapsiblePane.Direction.UP);
        filtercollapser.setCollapsed(true);
        filtercollapser.setContentPane(filterpanel);
        tablePanel.add(filtercollapser, BorderLayout.NORTH);

        final JButton showFilter = new JButton("Filters");
        showFilter.setFocusable(false);

        final JButton hideFilter = new JButton("Sluiten");
        hideFilter.setFocusable(false);

        showFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtercollapser.setCollapsed(false);
                numberField.requestFocus();
                invoicespanel.setRightDecoration(null);
            }
        });

        hideFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtercollapser.setCollapsed(true);
                invoicespanel.setRightDecoration(showFilter);
            }
        });

        invoicespanel.setRightDecoration(showFilter);
        filterpanel.setRightDecoration(hideFilter);

        bind();
    }

    /**
     * This method is called fromPicker within the constructor toPicker
     * initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        noResultOutlet = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        numberField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        clientField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        fromOutlet = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        toOutlet = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnClearFilters = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        editMenu1 = new javax.swing.JMenu();
        addMenuItem1 = new javax.swing.JMenuItem();
        printMenuItem = new javax.swing.JMenuItem();
        tablePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        print = new javax.swing.JButton();
        details = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        add = new javax.swing.JButton();

        noResultOutlet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noResultOutlet.setText("Geen resultaten");

        controlPanel.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 15, 5));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Nummer");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jLabel1, gridBagConstraints);

        numberField.setVerifyInputWhenFocusTarget(false);
        numberField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numberFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(numberField, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Klant");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jLabel2, gridBagConstraints);

        clientField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                clientFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(clientField, gridBagConstraints);

        controlPanel.add(jPanel4);

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 15, 5));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Van");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 3));
        jLabel3.setVerifyInputWhenFocusTarget(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jLabel3, gridBagConstraints);

        fromOutlet.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(fromOutlet, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Tot");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 3));
        jLabel4.setVerifyInputWhenFocusTarget(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jLabel4, gridBagConstraints);

        toOutlet.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(toOutlet, gridBagConstraints);

        controlPanel.add(jPanel3);

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        btnClearFilters.setText("Wis filters");
        btnClearFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFiltersActionPerformed(evt);
            }
        });
        jPanel5.add(btnClearFilters);

        delete.setText("Verwijderen...");
        delete.setFocusable(false);
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        jPanel5.add(delete);

        controlPanel.add(jPanel5);

        controlPanel.getAccessibleContext().setAccessibleName("");

        editMenu1.setText("Acties");

        addMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        addMenuItem1.setText("Toevoegen...");
        addMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMenuItem1ActionPerformed(evt);
            }
        });
        editMenu1.add(addMenuItem1);

        printMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printMenuItem.setText("Afdrukken...");
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        editMenu1.add(printMenuItem);

        setLayout(new java.awt.BorderLayout());

        tablePanel.setLayout(new java.awt.BorderLayout());
        add(tablePanel, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        print.setText("Afdrukken...");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel2.add(print);

        details.setText("Details...");
        details.setFocusable(false);
        details.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detailsActionPerformed(evt);
            }
        });
        jPanel2.add(details);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel6.setLayout(new java.awt.GridLayout(1, 2));

        add.setText("Toevoegen...");
        add.setFocusable(false);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel6.add(add);

        jPanel1.add(jPanel6, java.awt.BorderLayout.WEST);

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        new AddInvoiceDialog(null, true, this).setVisible(true);
    }//GEN-LAST:event_addActionPerformed

    private void detailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detailsActionPerformed
        int row = table.getSelectedRow();
        int index = table.convertRowIndexToModel(row);
        Invoice inv = (Invoice) (invoicesByID.values().toArray())[index];
        new InvoiceDetailsDialog(null, true, inv).setVisible(true);
    }//GEN-LAST:event_detailsActionPerformed

    private void numberFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numberFieldKeyReleased
        filterController.setNumberString(numberField.getText());
    }//GEN-LAST:event_numberFieldKeyReleased

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        int[] rijen = table.getSelectedRows();
	
	if (rijen.length == 0) {
	    return;
	}
	
	int added = 0;
	for (int i : rijen) {
	    rijen[added++]=table.convertRowIndexToModel(i);
	}
	
        Book b = new Book();
        PageFormat pf = new PageFormat();
        pf.setPaper(new A4());

//	MultiPrintable mp = new MultiPrintable();
	
        for (int i : rijen) {
            b.append(new PrintableInvoiceNew(tableModel.getInvoiceAtRow(i)), pf);
//	    mp.add(new PrintableInvoiceNew(tableModel.getInvoiceAtRow(i)));
        }
	
        printer.Printer.driver().setPrintableBook(b);
	
//	Printable p = new PrintableInvoiceNew(tableModel.getInvoiceAtRow(rijen[0]));
//        printer.Printer.driver().setPrintableJob(p);
//        printer.Printer.driver().setPrintableJob(mp, pf);
        printer.Printer.driver().tryPrint();
    }//GEN-LAST:event_printActionPerformed

    private void btnClearFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFiltersActionPerformed
        clientField.setText("");
        numberField.setText("");
        fromPicker.setDate(null);
        toPicker.setDate(null);
        filterController.clearFilters();
    }//GEN-LAST:event_btnClearFiltersActionPerformed

    private void clientFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clientFieldKeyReleased
        filterController.setClientString(clientField.getText());
    }//GEN-LAST:event_clientFieldKeyReleased

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        int[] rijen = table.getSelectedRows();
	
	int added = 0;
	for (int i : rijen) {
	    rijen[added++]=table.convertRowIndexToModel(i);
	}
	
        if (rijen.length > 0) {
	    String msg = "Bent u zeker dat u deze " + (rijen.length == 1 ? "factuur" : "facturen") + " wilt verwijderen? Deze actie kan niet ongedaan gemaakt worden!";
            int choice = JOptionPane.showOptionDialog(this, msg, "Aandacht!", 0, JOptionPane.WARNING_MESSAGE, null, new String[]{"Verwijderen", "Terug"}, 1);
            if (choice == 0) {
                System.out.println("Deleting...");
                tableModel.removeInvoiceAtRows(rijen);
            }
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void addMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMenuItem1ActionPerformed
        addActionPerformed(null);
    }//GEN-LAST:event_addMenuItem1ActionPerformed

    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
        printActionPerformed(evt);
    }//GEN-LAST:event_printMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JMenuItem addMenuItem;
    private javax.swing.JMenuItem addMenuItem1;
    private javax.swing.JButton btnClearFilters;
    private javax.swing.JTextField clientField;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JButton delete;
    private javax.swing.JButton details;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu editMenu1;
    private javax.swing.JPanel fromOutlet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel noResultOutlet;
    private javax.swing.JTextField numberField;
    private javax.swing.JButton print;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JPanel toOutlet;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateDetail(Invoice value) {
//	clientOutlet.setText(value.getClient().getSortKey());
//	dateOutlet.setText(value.getDate());
//	priceOutlet.setText(value.getPriceCode());
//	
//	articleTableOutlet.setModel(new InvoiceItemTableModel(value.items()));
    }

    @Override
    public JPanel view() {
        return this;
    }

    @Override
    public JMenu menu() {
        return editMenu1;
    }

    private JXTable createXTable() {
        JXTable table_l = new JXTable() {
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
                };
            }
        };
        return table_l;
    }

    private void bind() {
        tableModel = new InvoiceTableModel(invoicesByID);
        table.setModel(tableModel);
        filterController = new InvoiceFiltering(table);


        table.getColumns().get(0).setCellRenderer(CellRendererFactory.createZeroDecimalDoubleCellRenderer());
        table.getColumns().get(1).setCellRenderer(CellRendererFactory.createIngredientCellRenderer());
        table.getColumns().get(2).setCellRenderer(CellRendererFactory.createDateRenderer());

        table.setSortOrder(0, SortOrder.DESCENDING);

    }

    @Override
    public void electFirstResponder() {
    }

    private void configureDisplayProperties() {
        table.setColumnControlVisible(true);
        table.setShowHorizontalLines(true);
        table.addHighlighter(HighlighterFactory.createAlternateStriping());
        CustomColumnFactory factory = new CustomColumnFactory();
        InvoiceRendering.configureColumnFactory(factory, getClass());
        table.setColumnFactory(factory);
    }

    @Override
    public void addInvoice(Invoice e) {
        tableModel.fireTableDataChanged();
    }


    private class A4 extends Paper {

        public A4() {
            super();
            setSize(594.992125984252, 841.8897637795276);
            setImageableArea(36.0, 36.0, 522.99212598425197, 769.8897637795276);
        }
    }
}
