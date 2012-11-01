/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import database.Database;
import database.Supplier;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Warkst
 */
public class SupplierViewController extends javax.swing.JPanel implements ViewController{

    /**
     * Creates new form SupplierViewController
     */
    public SupplierViewController() {
	initComponents();
	
	Map<Integer, Supplier> s = Database.driver().getSuppliers();
	Map<String, Supplier> ss = new TreeMap<String, Supplier>();
	
	for (Map.Entry<Integer, Supplier> entry : s.entrySet()) {
	    if (!entry.getValue().isVerwijderd()) {
		ss.put(entry.getValue().getFirm(), entry.getValue());
	    }
	}
	
//	ArrayList<Supplier> data = new ArrayList<Supplier>();
//	
//	for (Map.Entry<String, Supplier> entry : ss.entrySet()) {
//	    data.add(entry.getValue());
//	}
//	
//	listOutlet.setModel(ListModelFactory.createSupplierListModel(data));
	
	listOutlet.setModel(ListModelFactory.createSupplierListModel(ss));
	
	listOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	listOutlet.addListSelectionListener(new ListSelectionListener() {

	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
		    updateDetail(listOutlet.getSelectedValue());
		}
	    }
	});
	
	listOutlet.setSelectedIndex(0);
    }
    
    private void updateDetail(Object value){
	Supplier s = (Supplier)value;
	firmOutlet.setText(s.getFirm());
	contactOutlet.setText(s.getContact());
	addressOutlet.setText(s.getAddress());
	municipalityOutlet.setText(s.getMunicipality());
	telephoneOutlet.setText(s.getTelephone());
	cellphoneOutlet.setText(s.getCellphone());
	faxOutlet.setText(s.getFax());
	emailOutlet.setText(s.getEmail());
	
	notesOutlet.setText(s.getNotes());
    }
    
    public void selectSupplier(Supplier supplier){
//	System.out.println("SVC::select index stub called with index "+);
	listOutlet.setSelectedValue(supplier, true);
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

        jSplitPane1 = new javax.swing.JSplitPane();
        detail = new javax.swing.JPanel();
        fixedFields = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        firmOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        contactOutlet = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        addressOutlet = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        municipalityOutlet = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        telephoneOutlet = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cellphoneOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        faxOutlet = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        emailOutlet = new javax.swing.JLabel();
        stretchableFields = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesOutlet = new javax.swing.JTextArea();
        master = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listOutlet = new javax.swing.JList();

        setFocusable(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setFocusable(false);

        detail.setFocusable(false);
        detail.setLayout(new java.awt.BorderLayout());

        fixedFields.setFocusable(false);
        fixedFields.setLayout(new java.awt.GridLayout(8, 2, 0, 5));

        jLabel1.setText("Firma");
        jLabel1.setFocusable(false);
        fixedFields.add(jLabel1);

        firmOutlet.setText("<firmOutlet>");
        firmOutlet.setFocusable(false);
        fixedFields.add(firmOutlet);

        jLabel4.setText("Contactpersoon");
        jLabel4.setFocusable(false);
        fixedFields.add(jLabel4);

        contactOutlet.setText("<contactOutlet>");
        contactOutlet.setFocusable(false);
        fixedFields.add(contactOutlet);

        jLabel2.setText("Adres");
        jLabel2.setFocusable(false);
        fixedFields.add(jLabel2);

        addressOutlet.setText("<addressOutlet>");
        addressOutlet.setFocusable(false);
        fixedFields.add(addressOutlet);

        jLabel3.setText("Gemeente");
        jLabel3.setFocusable(false);
        fixedFields.add(jLabel3);

        municipalityOutlet.setText("<municipalityOutlet>");
        municipalityOutlet.setFocusable(false);
        fixedFields.add(municipalityOutlet);

        jLabel5.setText("Telefoon");
        jLabel5.setFocusable(false);
        fixedFields.add(jLabel5);

        telephoneOutlet.setText("<telephoneOutlet>");
        telephoneOutlet.setFocusable(false);
        fixedFields.add(telephoneOutlet);

        jLabel6.setText("GSM");
        jLabel6.setFocusable(false);
        fixedFields.add(jLabel6);

        cellphoneOutlet.setText("<cellphoneOutlet>");
        cellphoneOutlet.setFocusable(false);
        fixedFields.add(cellphoneOutlet);

        jLabel7.setText("Fax");
        jLabel7.setFocusable(false);
        fixedFields.add(jLabel7);

        faxOutlet.setText("<faxOutlet>");
        faxOutlet.setFocusable(false);
        fixedFields.add(faxOutlet);

        jLabel8.setText("Email");
        jLabel8.setFocusable(false);
        fixedFields.add(jLabel8);

        emailOutlet.setText("<emailOutlet>");
        emailOutlet.setFocusable(false);
        fixedFields.add(emailOutlet);

        detail.add(fixedFields, java.awt.BorderLayout.NORTH);

        stretchableFields.setFocusable(false);
        stretchableFields.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opmerking:"));
        jScrollPane2.setFocusable(false);

        notesOutlet.setColumns(20);
        notesOutlet.setLineWrap(true);
        notesOutlet.setRows(5);
        notesOutlet.setWrapStyleWord(true);
        notesOutlet.setEnabled(false);
        notesOutlet.setFocusable(false);
        jScrollPane2.setViewportView(notesOutlet);

        stretchableFields.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        detail.add(stretchableFields, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(detail);

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

        jSplitPane1.setLeftComponent(master);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressOutlet;
    private javax.swing.JLabel cellphoneOutlet;
    private javax.swing.JLabel contactOutlet;
    private javax.swing.JPanel detail;
    private javax.swing.JLabel emailOutlet;
    private javax.swing.JLabel faxOutlet;
    private javax.swing.JLabel firmOutlet;
    private javax.swing.JPanel fixedFields;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList listOutlet;
    private javax.swing.JPanel master;
    private javax.swing.JLabel municipalityOutlet;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JPanel stretchableFields;
    private javax.swing.JLabel telephoneOutlet;
    // End of variables declaration//GEN-END:variables
}
