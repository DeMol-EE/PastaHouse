/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.contacts.controllers;

import database.extra.Contact;
import gui.MasterDetailViewController;
import gui.contacts.datasource.FilterPanelDataSource;
import gui.contacts.delegates.FilterPanelDelegate;
import gui.utilities.list.ContactListModel;
import java.util.Set;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Warkst
 */
public class ContactsViewController extends javax.swing.JPanel implements MasterDetailViewController<Contact>, FilterPanelDelegate, FilterPanelDataSource {

//    private Map<FilterPanel, RowFilter> filtersMap;
//    private Set<String> unusedFilterKeys;
//    private Set<String> usedFilterKeys;
    
    private ContactListModel listModel;
    
    /**
     * Creates new form ContactsViewController
     */
    public ContactsViewController() {
	initComponents();
	
	listModel = new ContactListModel(database.Database.driver().getContactsAlphabetically());
	listOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	listOutlet.setModel(listModel);
	
	listOutlet.addListSelectionListener(new ListSelectionListener() {

	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
		    if (listOutlet.getSelectedValue()!=null) {
			updateDetail((Contact)listOutlet.getSelectedValue());
		    }
		}
	    }
	});
	listOutlet.setSelectedIndex(0);
	
//	filtersMap = new HashMap<FilterPanel, RowFilter>();
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
        searchMenuItem = new javax.swing.JMenuItem();
        filteringPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filters = new javax.swing.JPanel();
        addFilterOutlet = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        jSplitPane1 = new javax.swing.JSplitPane();
        master = new javax.swing.JPanel();
        filter = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOutlet = new javax.swing.JList();
        add = new javax.swing.JButton();
        detail = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        typeOutlet = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        fixedFields = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        editMenu.setText("Edit");

        addMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        addMenuItem.setText("Toevoegen");
        addMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(addMenuItem);

        editMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        editMenuItem.setText("Wijzigen");
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(editMenuItem);

        searchMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        searchMenuItem.setText("Zoeken");
        editMenu.add(searchMenuItem);

        filteringPanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Filters");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 0));
        jPanel1.add(jLabel1, java.awt.BorderLayout.NORTH);

        filters.setLayout(new java.awt.GridLayout(1, 1));

        addFilterOutlet.setText("Toevoegen");
        addFilterOutlet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFilterOutletActionPerformed(evt);
            }
        });
        filters.add(addFilterOutlet);

        jPanel1.add(filters, java.awt.BorderLayout.CENTER);

        filteringPanel.add(jPanel1, java.awt.BorderLayout.NORTH);
        filteringPanel.add(filler1, java.awt.BorderLayout.CENTER);

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);

        master.setLayout(new java.awt.BorderLayout());

        filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterKeyReleased(evt);
            }
        });
        master.add(filter, java.awt.BorderLayout.NORTH);

        listOutlet.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listOutlet);

        master.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add.setText("Toevoegen...");
        master.add(add, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(master);

        detail.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        jLabel2.setText("Type");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 5));
        jPanel4.add(jLabel2);

        typeOutlet.setText("<typeOutlet>");
        jPanel4.add(typeOutlet);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);
        jPanel3.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        detail.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        fixedFields.setLayout(new java.awt.GridLayout(5, 2));

        jLabel4.setText("jLabel4");
        fixedFields.add(jLabel4);

        jLabel5.setText("jLabel5");
        fixedFields.add(jLabel5);

        jLabel7.setText("jLabel7");
        fixedFields.add(jLabel7);

        jLabel6.setText("jLabel6");
        fixedFields.add(jLabel6);

        jLabel8.setText("jLabel8");
        fixedFields.add(jLabel8);

        jLabel9.setText("jLabel9");
        fixedFields.add(jLabel9);

        jLabel11.setText("jLabel11");
        fixedFields.add(jLabel11);

        jLabel10.setText("jLabel10");
        fixedFields.add(jLabel10);

        jPanel2.add(fixedFields, java.awt.BorderLayout.NORTH);

        detail.add(jPanel2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(detail);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMenuItemActionPerformed
        throw new UnsupportedOperationException("Not supported yet.");
    }//GEN-LAST:event_addMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        throw new UnsupportedOperationException("Not supported yet.");
    }//GEN-LAST:event_editMenuItemActionPerformed

    private void addFilterOutletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFilterOutletActionPerformed
//        filters.setLayout(new GridLayout(filtersMap.size()+2, 1));
//	FilterPanel fp = new FilterPanel(this, this);
//	filters.add(fp, filtersMap.size());
//	filtersMap.put(fp, null);
//	
//	if (unusedFilterKeys().isEmpty()) {
//	    filters.remove(addFilterOutlet);
//	}
    }//GEN-LAST:event_addFilterOutletActionPerformed

    private void filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterKeyReleased
        if (filter.getText().isEmpty()) {
	    listModel.setFilter(null);
	} else {
	    listModel.setFilter(filter.getText());
	}
    }//GEN-LAST:event_filterKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton addFilterOutlet;
    private javax.swing.JMenuItem addMenuItem;
    private javax.swing.JPanel detail;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField filter;
    private javax.swing.JPanel filteringPanel;
    private javax.swing.JPanel filters;
    private javax.swing.JPanel fixedFields;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList listOutlet;
    private javax.swing.JPanel master;
    private javax.swing.JMenuItem searchMenuItem;
    private javax.swing.JLabel typeOutlet;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateDetail(Contact contact) {
	typeOutlet.setText(contact.getType());
    }

    @Override
    public void electFirstResponder() {
	/*
	 * Update the list model to reflect any changes in the underlying data structure (contacts list)
	 */
	listModel.update();
	
	/*
	 * Elect first responder
	 */
	listOutlet.requestFocus();
    }

    @Override
    public JMenu menu() {
	return editMenu;
    }

    @Override
    public JPanel view() {
	return this;
    }

    @Override
    public void deleteFilter(FilterPanel fp) {
//	filters.remove(fp);
//	filtersMap.remove(fp);
//	if (unusedFilterKeys().size() == 1) {
//	    filters.add(addFilterOutlet);
//	}
//	filters.validate();
//	filters.repaint();
    }

    @Override
    public Set<String> unusedFilterKeys() {
//	
//	Set<String> usedFilters = new TreeSet<String>();
//	
//	for (FilterPanel filterPanel : filtersMap.keySet()) {
//	    if (filterPanel.filterKey() != null) {
//		usedFilters.add(filterPanel.filterKey());
//	    }
//	}
//	
//	Set<String> unused = new TreeSet<String>(unusedFilterKeys);
//	unused.removeAll(usedFilters);
//	return unused;
	return null;
    }
}
