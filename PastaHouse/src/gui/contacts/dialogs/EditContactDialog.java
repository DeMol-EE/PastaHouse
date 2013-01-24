/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.contacts.dialogs;

import database.Database;
import database.FunctionResult;
import database.tables.Contact;
import gui.contacts.delegates.EditContactDelegate;
import gui.utilities.AcceleratorAdder;
import gui.utilities.KeyAction;
import gui.utilities.combobox.AutocompleteCombobox;
import gui.utilities.validation.AbstractValidator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXTitledPanel;
import tools.Utilities;

/**
 *
 * @author Warkst
 */
public class EditContactDialog extends javax.swing.JDialog {

    private final EditContactDelegate delegate;
    private final Contact model;
    private final Contact defaultModel;
    private int type;
    private JComboBox typeBox;
    private boolean flag = false;
    private final TreeMap<String, Integer> munies = (TreeMap<String, Integer>) Database.driver().getMunicipales();
    
    /**
     * Creates new form EditContactDialog
     */
    private EditContactDialog(java.awt.Frame parent, boolean modal, EditContactDelegate delegate, Contact model, int type) {
        super(parent, modal);
        initComponents();

        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(null);

        this.delegate = delegate;
        this.model = model;
        this.defaultModel = new Contact(model);
        this.type = type;

        loadModel();
        this.typeBox = new JComboBox(new String[]{"Leverancier", "Klant"});

        typeParent.add(new JLabel(type == Contact.client ? "Klant" : "Leverancier"));

	notesOutlet.setFont(new Font(notesOutlet.getFont().getName(), Font.PLAIN, Utilities.fontSize()));
	
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
	
	sortkeyOutlet.setInputVerifier(new AbstractValidator(this, sortkeyOutlet, "De toonnaam moet uniek en niet leeg zijn!") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		if (sortkeyOutlet.getText().isEmpty()) {
		    return false;
		}
		if (!defaultModel.getSortKey().equalsIgnoreCase(sortkeyOutlet.getText())) {
		    for (String sortkey : Database.driver().getContactsAlphabetically().keySet()) {
			if (sortkey.equalsIgnoreCase(sortkeyOutlet.getText())) {
			    return false;
			}
		    }
		}
		return true;
	    }
	});
	
	taxnrOutlet.setInputVerifier(new AbstractValidator(this, taxnrOutlet, "Het BTW-nummer is ongeldig (enkel cijfers worden gecontroleerd).") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		return taxnrOutlet.getText().isEmpty() ? true : Utilities.validTaxNr(taxnrOutlet.getText());
	    }
	});
	
	add(new JXTitledPanel("Details", detailsContainer), BorderLayout.NORTH);
	add(new JXTitledPanel("Opmerkingen", notesContainer), BorderLayout.CENTER);
    }

    private EditContactDialog(java.awt.Frame parent, boolean modal, EditContactDelegate delegate, Contact model) {
        super(parent, modal);
        initComponents();

        setTitle("Contactpersoon wijzigen");

        setModalityType(ModalityType.APPLICATION_MODAL);
        setLocationRelativeTo(null);

        this.delegate = delegate;
        this.model = model;
        this.defaultModel = new Contact(model);
        this.type = Contact.both;

        loadModel();

        typeBox = new JComboBox(new String[]{"Leverancier", "Klant"});
        if (model.isSupplier()) {
            typeBox.setSelectedIndex(0);
        } else {
            typeBox.setSelectedIndex(1);
        }
        typeParent.add(typeBox);

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
	
	sortkeyOutlet.setInputVerifier(new AbstractValidator(this, sortkeyOutlet, "De toonnaam moet uniek en niet leeg zijn!") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		if (sortkeyOutlet.getText().isEmpty()) {
		    return false;
		}
		if (!defaultModel.getSortKey().equalsIgnoreCase(sortkeyOutlet.getText())) {
		    for (String sortkey : Database.driver().getContactsAlphabetically().keySet()) {
			if (sortkey.equalsIgnoreCase(sortkeyOutlet.getText())) {
			    return false;
			}
		    }
		}
		return true;
	    }
	});
	
	add(new JXTitledPanel("Details", detailsContainer), BorderLayout.NORTH);
	add(new JXTitledPanel("Opmerkingen", notesContainer), BorderLayout.CENTER);
    }

    public static EditContactDialog createSupplierDialog(EditContactDelegate delegate, Contact model) {
        return new EditContactDialog(null, true, delegate, model, Contact.supplier);
    }

    public static EditContactDialog createClientDialog(EditContactDelegate delegate, Contact model) {
        return new EditContactDialog(null, true, delegate, model, Contact.client);
    }

    public static EditContactDialog createContactDialog(EditContactDelegate delegate, Contact model) {
        return new EditContactDialog(null, true, delegate, model);
    }

    private void loadModel() {
        txtFirma.setText(model.getFirm());
        sortkeyOutlet.setText(model.getSortKey());
        txtContact.setText(model.getContact());
        txtAdres.setText(model.getAddress());
        // copy municipale

        txtTel.setText(model.getTelephone());
        txttel2.setText(model.getTelephone2());
        txtGSM.setText(model.getCellphone());
        txtFax.setText(model.getFax());
        txtEmail.setText(model.getEmail());
        notesOutlet.setText(model.getNotes());
        txtPostcode.setText("" + model.getZipcode());

        taxnrOutlet.setText(model.getTaxnumber());
        if (model.getPricecode() != null) {
            pricecodeOutlet.setSelectedItem(model.getPricecode());
        }
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
        comboGemeentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!flag){
                setMunicipal();
                } else {
                    flag = false;
                }
                
            }
        });
        comboGemeentes.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPostcode.requestFocus();
                } else {
                    setMunicipal();
                }

            }
        });

//        jPanel2.addProxy(comboGemeentes, 9);
        muniParent.add(comboGemeentes, BorderLayout.CENTER);
//	System.out.println("mun: '"+model.getMunicipality()+"'");
        if (items.contains(model.getMunicipality())) {
            comboGemeentes.setSelectedItem(model.getMunicipality());
        } else {
            comboGemeentes.setSelectedIndex(0);
        }
    }

    private void setMunicipal() {
        String munie = comboGemeentes.getSelectedItem().toString();
        if (munies.containsKey(munie)) {
            txtPostcode.setText(munies.get((String) comboGemeentes.getSelectedItem()).toString());
        } else {
            txtPostcode.setText("");
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

        detailsContainer = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        typeTF = new javax.swing.JLabel();
        typeParent = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        sortkey = new javax.swing.JLabel();
        sortkeyOutlet = new javax.swing.JTextField();
        firmLabel = new javax.swing.JLabel();
        txtFirma = new javax.swing.JTextField();
        contactLabel = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtAdres = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        muniParent = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPostcode = new javax.swing.JTextField();
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
        jLabel3 = new javax.swing.JLabel();
        taxnrOutlet = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pricecodeOutlet = new javax.swing.JComboBox();
        notesContainer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesOutlet = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        detailsContainer.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        typeTF.setText("Type");
        typeTF.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 1));
        jPanel7.add(typeTF);

        typeParent.setLayout(new java.awt.BorderLayout());
        jPanel7.add(typeParent);

        jPanel6.add(jPanel7, java.awt.BorderLayout.CENTER);
        jPanel6.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        detailsContainer.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridLayout(13, 2));

        sortkey.setText("Toonnaam *");
        sortkey.setToolTipText("Het veld \"toonnaam\" wordt gebruikt om contactpersonen te sorteren. Het dient uniek te zijn. Contactpersonen zullen in alle lijsten verschijnen onder de waarde van dit attribuut.");
        sortkey.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        sortkey.setFocusable(false);
        jPanel2.add(sortkey);
        jPanel2.add(sortkeyOutlet);

        firmLabel.setText("Firma");
        firmLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        firmLabel.setFocusable(false);
        jPanel2.add(firmLabel);
        jPanel2.add(txtFirma);

        contactLabel.setText("Contactpersoon");
        contactLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        contactLabel.setFocusable(false);
        jPanel2.add(contactLabel);
        jPanel2.add(txtContact);

        jLabel13.setText("Adres");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel13.setFocusable(false);
        jPanel2.add(jLabel13);
        jPanel2.add(txtAdres);

        jLabel12.setText("Gemeente");
        jLabel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel12.setFocusable(false);
        jPanel2.add(jLabel12);

        muniParent.setLayout(new java.awt.BorderLayout());
        jPanel2.add(muniParent);

        jLabel1.setText("Postcode");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel1.setFocusable(false);
        jPanel2.add(jLabel1);

        txtPostcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPostcodeKeyReleased(evt);
            }
        });
        jPanel2.add(txtPostcode);

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

        jLabel3.setText("BTWNummer");
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel3);
        jPanel2.add(taxnrOutlet);

        jLabel4.setText("Prijscode");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel4);

        pricecodeOutlet.setMaximumRowCount(3);
        pricecodeOutlet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "A", "B" }));
        jPanel2.add(pricecodeOutlet);

        detailsContainer.add(jPanel2, java.awt.BorderLayout.CENTER);

        notesContainer.setPreferredSize(new java.awt.Dimension(528, 300));
        notesContainer.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);
        jScrollPane2.setMinimumSize(new java.awt.Dimension(800, 300));
        jScrollPane2.setName(""); // NOI18N

        notesOutlet.setColumns(20);
        notesOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        notesOutlet.setLineWrap(true);
        notesOutlet.setRows(1);
        notesOutlet.setWrapStyleWord(true);
        jScrollPane2.setViewportView(notesOutlet);

        notesContainer.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMaximumSize(null);
        setMinimumSize(new java.awt.Dimension(600, 700));
        setPreferredSize(new java.awt.Dimension(600, 800));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 0, 5));

        save.setText("Opslaan");
        save.setFocusable(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel4.add(save);

        cancel.setText("Terug");
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
        try {
            if (!valid()) {
		return;
	    }

            System.out.println("\"" + comboGemeentes.getSelectedItem() + "\"");

            model.setFirm(txtFirma.getText());
            model.setSortKey(sortkeyOutlet.getText());
            model.setContact(txtContact.getText());
            model.setAddress(txtAdres.getText());
            model.setZipcode(txtPostcode.getText());
            model.setMunicipality(comboGemeentes.getSelectedItem().toString());
            model.setTelephone(txtTel.getText());
            model.setTelephone2(txttel2.getText());
            model.setCellphone(txtGSM.getText());
            model.setFax(txtFax.getText());
            model.setEmail(txtEmail.getText());
            model.setNotes(notesOutlet.getText());
            model.setTaxnumber(taxnrOutlet.getText());
            model.setPricecode(pricecodeOutlet.getSelectedIndex() == 0 ? null : pricecodeOutlet.getSelectedItem().toString());
            if (type == Contact.both) {
                model.setType(typeBox.getSelectedIndex() == 0 ? "supplier" : "client");
            } else if (type == Contact.supplier) {
                model.setType("supplier");
            } else if (type == Contact.client) {
                model.setType("client");
            }

//            if (!defaultModel.getSortKey().equalsIgnoreCase(model.getSortKey())) {
//                if (Database.driver().getContactsAlphabetically().containsKey(model.getSortKey().toLowerCase())) {
//                    JOptionPane.showMessageDialog(null, "Er is al een contactpersoon met deze toonnaam.", "Fout!", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//            }

            FunctionResult res = model.update();
            if (res.getCode() == 0) {
                delegate.editContact(defaultModel, model);
                disposeLater();
            } else {
                String msg;
                switch (res.getCode()) {
                    case 1:
                    case 2:
                    case 3:
                        msg = res.getMessage();
                        break;
                    default:
                        msg = "Er is een fout opgetreden bij het opslaan van deze contactpersoon in de databank (code " + res.getCode() + "). Contacteer de ontwikkelaars met deze informatie.";
                }
                JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error:");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, Utilities.incorrectFormMessage, "Fout!", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_saveActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // reset values to default
        model.copy(defaultModel);
        disposeLater();
    }//GEN-LAST:event_cancelActionPerformed

    private void disposeLater() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dispose();
            }
        });
    }

    private void txtPostcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPostcodeKeyReleased
        TreeMap<String, Integer> munies = (TreeMap<String, Integer>) Database.driver().getMunicipales();
        ArrayList items = new ArrayList();
        if (!txtPostcode.getText().isEmpty()) {
            int code = Integer.parseInt(txtPostcode.getText());
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
    }//GEN-LAST:event_txtPostcodeKeyReleased

    private boolean valid(){
	if (!sortkeyOutlet.getInputVerifier().verify(sortkeyOutlet)) {
	    JOptionPane.showMessageDialog(null, "Geef een geldige toonnaam in. Die dient uniek te zijn en mag niet leeg zijn.", "Fout!", JOptionPane.ERROR_MESSAGE);
	    sortkeyOutlet.requestFocus();
	    return false;
	}
	return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JLabel contactLabel;
    private javax.swing.JPanel detailsContainer;
    private javax.swing.JLabel firmLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel muniParent;
    private javax.swing.JPanel notesContainer;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JComboBox pricecodeOutlet;
    private javax.swing.JButton save;
    private javax.swing.JLabel sortkey;
    private javax.swing.JTextField sortkeyOutlet;
    private javax.swing.JTextField taxnrOutlet;
    private javax.swing.JTextField txtAdres;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtFirma;
    private javax.swing.JTextField txtGSM;
    private javax.swing.JTextField txtPostcode;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txttel2;
    private javax.swing.JPanel typeParent;
    private javax.swing.JLabel typeTF;
    // End of variables declaration//GEN-END:variables
    private AutocompleteCombobox comboGemeentes;
}
