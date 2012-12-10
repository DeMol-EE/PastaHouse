/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.dialogs;

import database.Database;
import database.tables.Supplier;
import gui.ingredients.delegates.EditSupplierDelegate;
import gui.utilities.AcceleratorAdder;
import gui.utilities.KeyAction;
import gui.utilities.combobox.AutocompleteCombobox;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import tools.Utilities;

/**
 *
 * @author Warkst
 */
public class EditSupplierDialog extends javax.swing.JDialog {

    private final EditSupplierDelegate delegate;
    private final Supplier model;
    private final Supplier defaultModel;

    /**
     * Creates new form EditSupplierDialog
     */
    public EditSupplierDialog(java.awt.Frame parent, boolean modal, EditSupplierDelegate delegate, Supplier model) {
        super(parent, modal);
        initComponents();

        setTitle("Leverancier wijzigen");

        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(null);

        this.delegate = delegate;
        this.model = model;

        this.defaultModel = new Supplier(model);

        loadModel();
	
	AcceleratorAdder.addAccelerator(save, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), new KeyAction() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		saveActionPerformed(e);
	    }
	});
	
	AcceleratorAdder.addAccelerator(cancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), new KeyAction() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		cancelActionPerformed(e);
	    }
	});
    }

    private void loadModel() {
        txtFirma.setText(model.getFirm());
        txtContact.setText(model.getContact());
        txtAdres.setText(model.getAddress());
        // copy municipale
        txtTel.setText(model.getTelephone());
	txttel2.setText(model.getTelephone2());
        txtGSM.setText(model.getCellphone());
        txtFax.setText(model.getFax());
        txtEmail.setText(model.getEmail());
        notesOutlet.setText(model.getNotes());
        txtGemeente.setText(""+model.getZipcode());

        TreeMap<String, Integer> munies = (TreeMap<String, Integer>) Database.driver().getMunicipales();
        ArrayList items = new ArrayList();
        items.add("");
        items.addAll(munies.keySet());
        comboGemeentes = new AutocompleteCombobox(items);
        comboGemeentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMunicipal();
            }
        });
        comboGemeentes.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                setMunicipal();
            }
        });

//        jPanel2.addProxy(comboGemeentes, 9);
	muniParent.add(comboGemeentes, BorderLayout.CENTER);
        comboGemeentes.setSelectedItem(model.getMunicipality());
    }
    
    private void setMunicipal() {
        TreeMap<String, Integer> munies = (TreeMap<String, Integer>) Database.driver().getMunicipales();
        String munie = comboGemeentes.getSelectedItem().toString();
        if (munies.containsKey(munie)) {
            txtGemeente.setText(munies.get((String)comboGemeentes.getSelectedItem()).toString());
        } else {
            txtGemeente.setText("");
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

        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtFirma = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtAdres = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtGemeente = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        muniParent = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txttel2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtGSM = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtFax = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesOutlet = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel4 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.GridLayout(10, 2));

        jLabel11.setText("Firma *");
        jLabel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel11.setFocusable(false);
        jPanel2.add(jLabel11);
        jPanel2.add(txtFirma);

        jLabel10.setText("Contactpersoon");
        jLabel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel10.setFocusable(false);
        jPanel2.add(jLabel10);
        jPanel2.add(txtContact);

        jLabel13.setText("Adres");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel13.setFocusable(false);
        jPanel2.add(jLabel13);
        jPanel2.add(txtAdres);

        jLabel1.setText("Postcode");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel1.setFocusable(false);
        jPanel2.add(jLabel1);

        txtGemeente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGemeenteKeyReleased(evt);
            }
        });
        jPanel2.add(txtGemeente);

        jLabel12.setText("Gemeente");
        jLabel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel12.setFocusable(false);
        jPanel2.add(jLabel12);

        muniParent.setLayout(new java.awt.BorderLayout());
        jPanel2.add(muniParent);

        jLabel9.setText("Telefoon");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel9.setFocusable(false);
        jPanel2.add(jLabel9);
        jPanel2.add(txtTel);

        jLabel2.setText("Telefoon 2");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jPanel2.add(jLabel2);
        jPanel2.add(txttel2);

        jLabel14.setText("GSM");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel14.setFocusable(false);
        jPanel2.add(jLabel14);
        jPanel2.add(txtGSM);

        jLabel15.setText("Fax");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel15.setFocusable(false);
        jPanel2.add(jLabel15);
        jPanel2.add(txtFax);

        jLabel16.setText("Email");
        jLabel16.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel16.setFocusable(false);
        jPanel2.add(jLabel16);
        jPanel2.add(txtEmail);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setPreferredSize(new java.awt.Dimension(528, 300));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opmerking:"));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(800, 300));
        jScrollPane2.setName(""); // NOI18N

        notesOutlet.setColumns(20);
        notesOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        notesOutlet.setRows(5);
        jScrollPane2.setViewportView(notesOutlet);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(filler1, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(200, 30));
        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 0, 5));

        save.setText("Opslaan");
        save.setFocusable(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel4.add(save);

        cancel.setText("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel4.add(cancel);

        jPanel1.add(jPanel4, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try{
	    if (txtFirma.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, tools.Utilities.incompleteFormMessage, "Fout!", JOptionPane.WARNING_MESSAGE);
		return;
	    }
	    
	    model.setFirm(txtFirma.getText());
	    model.setContact(txtContact.getText());
	    model.setAddress(txtAdres.getText());
	    model.setZipcode(Integer.parseInt(txtGemeente.getText()));
	    model.setMunicipality(comboGemeentes.getSelectedItem().toString());
	    model.setTelephone(txtTel.getText());
	    model.setTelephone2(txttel2.getText());
	    model.setCellphone(txtGSM.getText());
	    model.setFax(txtFax.getText());
	    model.setEmail(txtEmail.getText());
	    model.setNotes(notesOutlet.getText());

	    if (model.update()) {
		delegate.editSupplier(model, defaultModel);
		disposeLater();
	    } else {
		JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van deze leverancier in de databank.", "Fout!", JOptionPane.ERROR_MESSAGE);
	    }
	} catch (Exception e){
	    JOptionPane.showMessageDialog(null, Utilities.incorrectFormMessage, "Fout!", JOptionPane.ERROR_MESSAGE);
	}
        
    }//GEN-LAST:event_saveActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // reset values to default
        model.copy(defaultModel);
	disposeLater();
    }//GEN-LAST:event_cancelActionPerformed

    private void disposeLater(){
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		dispose();
	    }
	});
    }
    
    private void txtGemeenteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGemeenteKeyReleased
        TreeMap<String, Integer> munies = (TreeMap<String, Integer>) Database.driver().getMunicipales();
        ArrayList items = new ArrayList();
        if (!txtGemeente.getText().isEmpty()) {
            int code = Integer.parseInt(txtGemeente.getText());
            if (munies.containsValue(code)) {
                for (String munie : munies.keySet()) {
                    if (munies.get(munie) == code) {
                        items.add(munie);
                    }
                }
            }

        } else {
            items.add("");
            items.addAll(munies.keySet());
        }
        comboGemeentes.setDataList(items);
    }//GEN-LAST:event_txtGemeenteKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel muniParent;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JButton save;
    private javax.swing.JTextField txtAdres;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtFirma;
    private javax.swing.JTextField txtGSM;
    private javax.swing.JTextField txtGemeente;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txttel2;
    // End of variables declaration//GEN-END:variables
    private AutocompleteCombobox comboGemeentes;
}
