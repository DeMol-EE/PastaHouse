/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices.controllers;

import database.Database;
import database.tables.Client;
import gui.EmptyPanelManager;
import gui.MasterDetailViewController;
import gui.invoices.delegates.AddClientDelegate;
import gui.invoices.delegates.EditClientDelegate;
import gui.invoices.dialogs.AddClientDialog;
import gui.invoices.dialogs.EditClientDialog;
import gui.utilities.list.EditableListModel;
import gui.utilities.list.ListModelFactory;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import tools.StringTools;

/**
 *
 * @author Warkst
 */
public class ClientViewController extends javax.swing.JPanel implements MasterDetailViewController<Client>, AddClientDelegate, EditClientDelegate {
    
    /**
     * Creates new form ClientViewController
     */
    public ClientViewController() {
	initComponents();

	listOutlet.setModel(ListModelFactory.createClientListModel(Database.driver().getClientsAlphabetically()));
	listOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	listOutlet.addListSelectionListener(new ListSelectionListener() {

	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
		    updateDetail((Client)listOutlet.getSelectedValue());
		}
	    }
	});
	listOutlet.setSelectedIndex(0);
	
	if (Database.driver().getClients().isEmpty()) {
	    detail.remove(container);
	    detail.add(EmptyPanelManager.instance(), BorderLayout.CENTER);
	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editMenu = new javax.swing.JMenu();
        addMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        jSplitPane1 = new javax.swing.JSplitPane();
        master = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOutlet = new javax.swing.JList();
        add = new javax.swing.JButton();
        detail = new javax.swing.JPanel();
        container = new javax.swing.JPanel();
        fixedWrapper = new javax.swing.JPanel();
        fixedFields = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        firmOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        contactOutlet = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        addressOutlet = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        zipcodeOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        municipalityOutlet = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        telephoneOutlet = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        telephone2Outlet = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cellphoneOutlet = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        faxOutlet = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        emailOutlet = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        taxnrOutlet = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        pricecodeOutlet = new javax.swing.JLabel();
        stretchableFields = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesOutlet = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        invoices = new javax.swing.JButton();
        edit = new javax.swing.JButton();

        editMenu.setText("Edit");

        addMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        addMenuItem.setText("Add");
        addMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(addMenuItem);

        editMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        editMenuItem.setText("Edit");
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(editMenuItem);

        setFocusable(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setFocusable(false);

        master.setFocusable(false);
        master.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setFocusable(false);

        listOutlet.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listOutlet);

        master.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add.setText("Toevoegen...");
        add.setFocusable(false);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        master.add(add, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(master);

        detail.setFocusable(false);
        detail.setLayout(new java.awt.BorderLayout());

        container.setLayout(new java.awt.BorderLayout());

        fixedWrapper.setLayout(new java.awt.BorderLayout());

        fixedFields.setFocusable(false);
        fixedFields.setLayout(new java.awt.GridLayout(12, 2));

        jLabel1.setText("Firma");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel1.setFocusable(false);
        fixedFields.add(jLabel1);

        firmOutlet.setText("<firmOutlet>");
        firmOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        firmOutlet.setFocusable(false);
        fixedFields.add(firmOutlet);

        jLabel4.setBackground(new java.awt.Color(239, 239, 239));
        jLabel4.setText("Contactpersoon");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel4.setFocusable(false);
        jLabel4.setOpaque(true);
        fixedFields.add(jLabel4);

        contactOutlet.setBackground(new java.awt.Color(239, 239, 239));
        contactOutlet.setText("<contactOutlet>");
        contactOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        contactOutlet.setFocusable(false);
        contactOutlet.setOpaque(true);
        fixedFields.add(contactOutlet);

        jLabel3.setBackground(new java.awt.Color(239, 239, 239));
        jLabel3.setText("Adres");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel3.setFocusable(false);
        fixedFields.add(jLabel3);

        addressOutlet.setBackground(new java.awt.Color(239, 239, 239));
        addressOutlet.setText("<addressOutlet>");
        addressOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        addressOutlet.setFocusable(false);
        fixedFields.add(addressOutlet);

        jLabel5.setBackground(new java.awt.Color(239, 239, 239));
        jLabel5.setText("Postcode");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel5.setFocusable(false);
        jLabel5.setOpaque(true);
        fixedFields.add(jLabel5);

        zipcodeOutlet.setBackground(new java.awt.Color(239, 239, 239));
        zipcodeOutlet.setText("<zipcodeOutlet>");
        zipcodeOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        zipcodeOutlet.setFocusable(false);
        zipcodeOutlet.setOpaque(true);
        fixedFields.add(zipcodeOutlet);

        jLabel7.setBackground(new java.awt.Color(239, 239, 239));
        jLabel7.setText("Gemeente");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel7.setFocusable(false);
        fixedFields.add(jLabel7);

        municipalityOutlet.setBackground(new java.awt.Color(239, 239, 239));
        municipalityOutlet.setText("<municipalityOutlet>");
        municipalityOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        municipalityOutlet.setFocusable(false);
        fixedFields.add(municipalityOutlet);

        jLabel9.setBackground(new java.awt.Color(239, 239, 239));
        jLabel9.setText("Telefoon");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel9.setFocusable(false);
        jLabel9.setOpaque(true);
        fixedFields.add(jLabel9);

        telephoneOutlet.setBackground(new java.awt.Color(239, 239, 239));
        telephoneOutlet.setText("<telephoneOutlet>");
        telephoneOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        telephoneOutlet.setFocusable(false);
        telephoneOutlet.setOpaque(true);
        fixedFields.add(telephoneOutlet);

        jLabel11.setBackground(new java.awt.Color(239, 239, 239));
        jLabel11.setText("Telefoon 2");
        jLabel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel11.setFocusable(false);
        fixedFields.add(jLabel11);

        telephone2Outlet.setBackground(new java.awt.Color(239, 239, 239));
        telephone2Outlet.setText("<telephone2Outlet>");
        telephone2Outlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        telephone2Outlet.setFocusable(false);
        fixedFields.add(telephone2Outlet);

        jLabel13.setBackground(new java.awt.Color(239, 239, 239));
        jLabel13.setText("GSM");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel13.setFocusable(false);
        jLabel13.setOpaque(true);
        fixedFields.add(jLabel13);

        cellphoneOutlet.setBackground(new java.awt.Color(239, 239, 239));
        cellphoneOutlet.setText("<cellphoneOutlet>");
        cellphoneOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        cellphoneOutlet.setFocusable(false);
        cellphoneOutlet.setOpaque(true);
        fixedFields.add(cellphoneOutlet);

        jLabel15.setBackground(new java.awt.Color(239, 239, 239));
        jLabel15.setText("Fax");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel15.setFocusable(false);
        fixedFields.add(jLabel15);

        faxOutlet.setBackground(new java.awt.Color(239, 239, 239));
        faxOutlet.setText("<faxOutlet>");
        faxOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        faxOutlet.setFocusable(false);
        fixedFields.add(faxOutlet);

        jLabel17.setBackground(new java.awt.Color(239, 239, 239));
        jLabel17.setText("Email");
        jLabel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel17.setFocusable(false);
        jLabel17.setOpaque(true);
        fixedFields.add(jLabel17);

        emailOutlet.setBackground(new java.awt.Color(239, 239, 239));
        emailOutlet.setText("<emailOutlet>");
        emailOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        emailOutlet.setFocusable(false);
        emailOutlet.setOpaque(true);
        fixedFields.add(emailOutlet);

        jLabel20.setBackground(new java.awt.Color(239, 239, 239));
        jLabel20.setText("BTWNummer");
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel20.setFocusable(false);
        fixedFields.add(jLabel20);

        taxnrOutlet.setBackground(new java.awt.Color(239, 239, 239));
        taxnrOutlet.setText("<taxnrOutlet>");
        taxnrOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        taxnrOutlet.setFocusable(false);
        fixedFields.add(taxnrOutlet);

        jLabel21.setBackground(new java.awt.Color(239, 239, 239));
        jLabel21.setText("Prijscode");
        jLabel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel21.setFocusable(false);
        jLabel21.setOpaque(true);
        fixedFields.add(jLabel21);

        pricecodeOutlet.setBackground(new java.awt.Color(239, 239, 239));
        pricecodeOutlet.setText("<pricecodeOutlet>");
        pricecodeOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        pricecodeOutlet.setFocusable(false);
        pricecodeOutlet.setOpaque(true);
        fixedFields.add(pricecodeOutlet);

        fixedWrapper.add(fixedFields, java.awt.BorderLayout.CENTER);

        container.add(fixedWrapper, java.awt.BorderLayout.NORTH);

        stretchableFields.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opmerking:"));
        jScrollPane2.setFocusable(false);

        notesOutlet.setColumns(20);
        notesOutlet.setRows(5);
        jScrollPane2.setViewportView(notesOutlet);

        stretchableFields.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        container.add(stretchableFields, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        invoices.setText("Facturen");
        jPanel2.add(invoices);

        edit.setText("Wijzigen...");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        jPanel2.add(edit);

        jPanel1.add(jPanel2);

        container.add(jPanel1, java.awt.BorderLayout.SOUTH);

        detail.add(container, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(detail);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
	final ClientViewController me = this;
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		new AddClientDialog(null, true, me).setVisible(true);
	    }
	});
    }//GEN-LAST:event_addActionPerformed

    private void addMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMenuItemActionPerformed
        addActionPerformed(null);
    }//GEN-LAST:event_addMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
//        editActionPerformed(null);
	throw new UnsupportedOperationException("Not yet implemented.");
    }//GEN-LAST:event_editMenuItemActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        final ClientViewController me = this;
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		if(listOutlet.getSelectedValue()!=null) new EditClientDialog(null, true, me, (Client)listOutlet.getSelectedValue()).setVisible(true);
	    }
	});
    }//GEN-LAST:event_editActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JMenuItem addMenuItem;
    private javax.swing.JLabel addressOutlet;
    private javax.swing.JLabel cellphoneOutlet;
    private javax.swing.JLabel contactOutlet;
    private javax.swing.JPanel container;
    private javax.swing.JPanel detail;
    private javax.swing.JButton edit;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JLabel emailOutlet;
    private javax.swing.JLabel faxOutlet;
    private javax.swing.JLabel firmOutlet;
    private javax.swing.JPanel fixedFields;
    private javax.swing.JPanel fixedWrapper;
    private javax.swing.JButton invoices;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList listOutlet;
    private javax.swing.JPanel master;
    private javax.swing.JLabel municipalityOutlet;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JLabel pricecodeOutlet;
    private javax.swing.JPanel stretchableFields;
    private javax.swing.JLabel taxnrOutlet;
    private javax.swing.JLabel telephone2Outlet;
    private javax.swing.JLabel telephoneOutlet;
    private javax.swing.JLabel zipcodeOutlet;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateDetail(Client client) {
	firmOutlet.setText(StringTools.capitalizeEach(client.getFirm()));
	contactOutlet.setText(StringTools.capitalizeEach(client.getContact()));
        addressOutlet.setText(StringTools.capitalizeEach(client.getAddress()));
        municipalityOutlet.setText(StringTools.capitalize(client.getMunicipality()));
        telephoneOutlet.setText(client.getTelephone());
        cellphoneOutlet.setText(client.getCellphone());
        faxOutlet.setText(client.getFax());
        emailOutlet.setText(client.getEmail());
        telephone2Outlet.setText(client.getTelephone2());
        notesOutlet.setText(client.getNotes());
        zipcodeOutlet.setText("" + client.getZipcode());
	taxnrOutlet.setText(client.getTaxnumber());
	String code = client.getPricecode();
	if (code != null) {
	    code = code.toUpperCase(); 
	}
	pricecodeOutlet.setText(code);
    }

    @Override
    public void addClient(Client newObj) {
	EditableListModel<Client> elm = (EditableListModel<Client>)listOutlet.getModel();
	elm.update();
	if (elm.getSize() == 1) {
	    detail.removeAll();
	    detail.add(container, BorderLayout.CENTER);
	}
	listOutlet.setSelectedValue(newObj, true);
	updateDetail(newObj);
    }

    @Override
    public void electFirstResponder() {
	listOutlet.requestFocus();
    }

    @Override
    public JPanel view() {
	return this;
    }

    @Override
    public JMenu menu() {
	return editMenu;
    }

    @Override
    public void updateClient(Client o, Client n) {
	EditableListModel<Client> dlm = (EditableListModel)listOutlet.getModel();
	dlm.edit(n, o);
	listOutlet.setSelectedValue(n, true);
	updateDetail(n);
    }
}
