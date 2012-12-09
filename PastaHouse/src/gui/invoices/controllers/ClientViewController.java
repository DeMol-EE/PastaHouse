/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices.controllers;

import database.Database;
import database.tables.Client;
import gui.MasterDetailViewController;
import gui.EmptyPanelManager;
import gui.utilities.list.EditableListModel;
import gui.utilities.list.ListModelFactory;
import java.awt.BorderLayout;
import java.util.Map;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Warkst
 */
public class ClientViewController extends javax.swing.JPanel implements MasterDetailViewController<Client> {
    
    private boolean showingDetails = true;
    
    /**
     * Creates new form ClientViewController
     */
    public ClientViewController() {
	initComponents();

	listOutlet.setModel(ListModelFactory.createClientListModel(Database.driver().getClients()));
	listOutlet.setSelectedIndex(0);
	listOutlet.addListSelectionListener(new ListSelectionListener() {

	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
		    updateDetail((Client)listOutlet.getSelectedValue());
		}
	    }
	});
	
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

        jSplitPane1 = new javax.swing.JSplitPane();
        master = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOutlet = new javax.swing.JList();
        add = new javax.swing.JButton();
        detail = new javax.swing.JPanel();
        container = new javax.swing.JPanel();
        stretchableFields = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        invoicesParent = new javax.swing.JPanel();
        invoicesScroller = new javax.swing.JScrollPane();
        invoicesOutlet = new javax.swing.JList();
        jSeparator2 = new javax.swing.JSeparator();
        fixedWrapper = new javax.swing.JPanel();
        fixedFields = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameOutlet = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        detailsWrapper = new javax.swing.JPanel();
        details = new javax.swing.JLabel();
        details2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

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

        stretchableFields.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opmerking:"));
        jScrollPane2.setFocusable(false);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        stretchableFields.add(jScrollPane2, java.awt.BorderLayout.SOUTH);

        invoicesParent.setLayout(new java.awt.BorderLayout());

        invoicesScroller.setBorder(javax.swing.BorderFactory.createTitledBorder("Facturen:"));

        invoicesOutlet.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        invoicesScroller.setViewportView(invoicesOutlet);

        invoicesParent.add(invoicesScroller, java.awt.BorderLayout.CENTER);
        invoicesParent.add(jSeparator2, java.awt.BorderLayout.SOUTH);

        stretchableFields.add(invoicesParent, java.awt.BorderLayout.CENTER);

        container.add(stretchableFields, java.awt.BorderLayout.CENTER);

        fixedWrapper.setLayout(new java.awt.BorderLayout());

        fixedFields.setFocusable(false);
        fixedFields.setLayout(new java.awt.GridLayout(10, 2));

        jLabel1.setText("Naam");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel1.setFocusable(false);
        fixedFields.add(jLabel1);

        nameOutlet.setText("<nameOutlet>");
        nameOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        nameOutlet.setFocusable(false);
        fixedFields.add(nameOutlet);

        jLabel3.setBackground(new java.awt.Color(239, 239, 239));
        jLabel3.setText("Adres");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel3.setFocusable(false);
        jLabel3.setOpaque(true);
        fixedFields.add(jLabel3);

        jLabel4.setBackground(new java.awt.Color(239, 239, 239));
        jLabel4.setText("jLabel4");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel4.setFocusable(false);
        jLabel4.setOpaque(true);
        fixedFields.add(jLabel4);

        jLabel5.setText("Postcode");
        jLabel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel5.setFocusable(false);
        fixedFields.add(jLabel5);

        jLabel6.setText("jLabel6");
        jLabel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel6.setFocusable(false);
        fixedFields.add(jLabel6);

        jLabel7.setBackground(new java.awt.Color(239, 239, 239));
        jLabel7.setText("Gemeente");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel7.setFocusable(false);
        jLabel7.setOpaque(true);
        fixedFields.add(jLabel7);

        jLabel8.setBackground(new java.awt.Color(239, 239, 239));
        jLabel8.setText("jLabel8");
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel8.setFocusable(false);
        jLabel8.setOpaque(true);
        fixedFields.add(jLabel8);

        jLabel9.setText("Telefoon");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel9.setFocusable(false);
        fixedFields.add(jLabel9);

        jLabel10.setText("jLabel10");
        jLabel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel10.setFocusable(false);
        fixedFields.add(jLabel10);

        jLabel11.setBackground(new java.awt.Color(239, 239, 239));
        jLabel11.setText("Telefoon 2");
        jLabel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel11.setFocusable(false);
        jLabel11.setOpaque(true);
        fixedFields.add(jLabel11);

        jLabel12.setBackground(new java.awt.Color(239, 239, 239));
        jLabel12.setText("jLabel12");
        jLabel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel12.setFocusable(false);
        jLabel12.setOpaque(true);
        fixedFields.add(jLabel12);

        jLabel13.setText("GSM");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel13.setFocusable(false);
        fixedFields.add(jLabel13);

        jLabel14.setText("jLabel14");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel14.setFocusable(false);
        fixedFields.add(jLabel14);

        jLabel15.setBackground(new java.awt.Color(239, 239, 239));
        jLabel15.setText("Fax");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel15.setFocusable(false);
        jLabel15.setOpaque(true);
        fixedFields.add(jLabel15);

        jLabel16.setBackground(new java.awt.Color(239, 239, 239));
        jLabel16.setText("jLabel16");
        jLabel16.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel16.setFocusable(false);
        jLabel16.setOpaque(true);
        fixedFields.add(jLabel16);

        jLabel17.setText("Email");
        jLabel17.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel17.setFocusable(false);
        fixedFields.add(jLabel17);

        jLabel18.setText("jLabel18");
        jLabel18.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel18.setFocusable(false);
        fixedFields.add(jLabel18);

        jLabel20.setBackground(new java.awt.Color(239, 239, 239));
        jLabel20.setText("jLabel20");
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel20.setFocusable(false);
        jLabel20.setOpaque(true);
        fixedFields.add(jLabel20);

        jLabel19.setBackground(new java.awt.Color(239, 239, 239));
        jLabel19.setText("jLabel19");
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        jLabel19.setFocusable(false);
        jLabel19.setOpaque(true);
        fixedFields.add(jLabel19);

        fixedWrapper.add(fixedFields, java.awt.BorderLayout.CENTER);

        detailsWrapper.setLayout(new java.awt.BorderLayout());

        details.setText("Details");
        details.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 10, 3, 0));
        details.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        detailsWrapper.add(details, java.awt.BorderLayout.WEST);

        details2.setText("(verbergen)");
        details2.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        details2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        details2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                detailsMouseReleased(evt);
            }
        });
        detailsWrapper.add(details2, java.awt.BorderLayout.CENTER);
        detailsWrapper.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        fixedWrapper.add(detailsWrapper, java.awt.BorderLayout.NORTH);

        container.add(fixedWrapper, java.awt.BorderLayout.NORTH);

        detail.add(container, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(detail);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void detailsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailsMouseReleased
        showingDetails = !showingDetails;
	updateDetailsOutlet();
    }//GEN-LAST:event_detailsMouseReleased

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
	Map<Integer, Client> cl = Database.driver().getClients();
	Client c = new Client("Client_"+cl.size()+1);
	cl.put(cl.size()+1, c);
	
	addAndSelect(c);
	
	validate();
	repaint();
    }//GEN-LAST:event_addActionPerformed

    private void updateDetailsOutlet(){
	fixedWrapper.removeAll();
	if (showingDetails) {
	    details2.setText("(verbergen)");
	    fixedWrapper.add(detailsWrapper, BorderLayout.NORTH);
	    fixedWrapper.add(fixedFields, BorderLayout.CENTER);
	} else {
	    details2.setText("(weergeven)");
	    fixedWrapper.add(detailsWrapper, BorderLayout.NORTH);
	}
	
	fixedWrapper.validate();
	fixedWrapper.repaint();
	detail.validate();
	detail.repaint();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JPanel container;
    private javax.swing.JPanel detail;
    private javax.swing.JLabel details;
    private javax.swing.JLabel details2;
    private javax.swing.JPanel detailsWrapper;
    private javax.swing.JPanel fixedFields;
    private javax.swing.JPanel fixedWrapper;
    private javax.swing.JList invoicesOutlet;
    private javax.swing.JPanel invoicesParent;
    private javax.swing.JScrollPane invoicesScroller;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JList listOutlet;
    private javax.swing.JPanel master;
    private javax.swing.JLabel nameOutlet;
    private javax.swing.JPanel stretchableFields;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateDetail(Client value) {
	nameOutlet.setText(value.getName());
    }

    @Override
    public void addAndSelect(Client newObj) {
	EditableListModel<Client> elm = (EditableListModel<Client>)listOutlet.getModel();
	elm.update();
	if (elm.getSize() == 1) {
	    detail.remove(EmptyPanelManager.instance());
	    detail.add(container, BorderLayout.CENTER);
	}
	listOutlet.setSelectedValue(newObj, true);
	updateDetail(newObj);
    }

    @Override
    public void editAndSelect(Client newObj, Client oldObj) {
	throw new UnsupportedOperationException("Not supported yet.");
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
	return null;
    }
}
