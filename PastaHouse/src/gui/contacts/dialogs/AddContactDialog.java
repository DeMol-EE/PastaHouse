/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.contacts.dialogs;

import database.Database;
import database.FunctionResult;
import database.models.ContactModel;
import database.tables.Contact;
import gui.contacts.delegates.AddContactDelegate;
import gui.utilities.AcceleratorAdder;
import gui.utilities.KeyAction;
import gui.utilities.combobox.AutocompleteCombobox;
import gui.utilities.validation.AbstractValidator;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.TreeMap;
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
 * @author Hannes
 */
public class AddContactDialog extends javax.swing.JDialog {

    private AddContactDelegate delegate;
    private final ContactModel model;
    private int type;
    private JComboBox typeBox;
    private boolean flag = false;
    private final TreeMap<String, Integer> munies = (TreeMap<String, Integer>) Database.driver().getMunicipales();

    /**
     * Creates new form AddContactDialog
     */
    private AddContactDialog(java.awt.Frame parent, boolean modal, AddContactDelegate delegate, int type) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(null);
        this.delegate = delegate;
        this.model = new ContactModel("supplier");
        this.type = type;
        loadModel();
        this.typeBox = new JComboBox(new String[]{"Leverancier", "Klant"});

        typeParent.add(new JLabel(type == Contact.client ? "Klant" : "Leverancier"));

	notesOutlet.setFont(new Font(notesOutlet.getFont().getName(), Font.PLAIN, Utilities.fontSize()));
	
        AcceleratorAdder.addAccelerator(add, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), new KeyAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addActionPerformed(e);
            }
        });

        AcceleratorAdder.addAccelerator(cancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), new KeyAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelActionPerformed(e);
            }
        });
	
	txtFirma.setInputVerifier(new AbstractValidator(this, txtFirma, "De firma moet uniek en niet leeg zijn!") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		if (txtFirma.getText().isEmpty()) {
		    return false;
		}
		for (String sortkey : Database.driver().getContactsAlphabetically().keySet()) {
		    if (sortkey.equalsIgnoreCase(txtFirma.getText())) {
			return false;
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

    private AddContactDialog(java.awt.Frame parent, boolean modal, AddContactDelegate delegate) {
        super(parent, modal);
        initComponents();
        setTitle("Contactpersoon toevoegen");
        this.setLocationRelativeTo(null);
        this.delegate = delegate;
        this.model = new ContactModel("supplier");
        this.type = Contact.both;
        loadModel();
        this.typeBox = new JComboBox(new String[]{"Leverancier", "Klant"});
        typeParent.add(typeBox);

        AcceleratorAdder.addAccelerator(add, KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), new KeyAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addActionPerformed(e);
            }
        });

        AcceleratorAdder.addAccelerator(cancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), new KeyAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelActionPerformed(e);
            }
        });
	
	txtFirma.setInputVerifier(new AbstractValidator(this, txtFirma, "De firma moet uniek en niet leeg zijn!") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		if (txtFirma.getText().isEmpty()) {
		    return false;
		}
		for (String sortkey : Database.driver().getContactsAlphabetically().keySet()) {
		    if (sortkey.equalsIgnoreCase(txtFirma.getText())) {
			return false;
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

    public static AddContactDialog createSupplierDialog(AddContactDelegate delegate) {
        return new AddContactDialog(null, true, delegate, Contact.supplier);
    }

    public static AddContactDialog createClientDialog(AddContactDelegate delegate) {
        return new AddContactDialog(null, true, delegate, Contact.client);
    }

    public static AddContactDialog createContactDialog(AddContactDelegate delegate) {
        return new AddContactDialog(null, true, delegate);
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
        firmLabel = new javax.swing.JLabel();
        txtFirma = new javax.swing.JTextField();
        contactLabel = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtAdres = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        HolderGemeentes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPostcode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTel2 = new javax.swing.JTextField();
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
        add = new javax.swing.JButton();
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

        jPanel2.setLayout(new java.awt.GridLayout(12, 2));

        firmLabel.setText("Firma");
        firmLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(firmLabel);
        jPanel2.add(txtFirma);

        contactLabel.setText("Contactpersoon");
        contactLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(contactLabel);
        jPanel2.add(txtContact);

        jLabel13.setText("Adres");
        jLabel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel13);
        jPanel2.add(txtAdres);

        jLabel12.setText("Gemeente");
        jLabel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel12);

        HolderGemeentes.setLayout(new java.awt.BorderLayout());
        jPanel2.add(HolderGemeentes);

        jLabel1.setText("Postcode");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel1);

        txtPostcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPostcodeKeyReleased(evt);
            }
        });
        jPanel2.add(txtPostcode);

        jLabel9.setText("Telefoon");
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel9);
        jPanel2.add(txtTel);

        jLabel2.setText("Telefoon 2");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel2);
        jPanel2.add(txtTel2);

        jLabel14.setText("GSM");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel14);
        jPanel2.add(txtGSM);

        jLabel15.setText("Fax");
        jLabel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
        jPanel2.add(jLabel15);
        jPanel2.add(txtFax);

        jLabel16.setText("Email");
        jLabel16.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 1));
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
        pricecodeOutlet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B" }));
        jPanel2.add(pricecodeOutlet);

        detailsContainer.add(jPanel2, java.awt.BorderLayout.CENTER);

        notesContainer.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);
        jScrollPane2.setMinimumSize(new java.awt.Dimension(800, 600));
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
        setModal(true);
        setPreferredSize(new java.awt.Dimension(600, 800));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        add.setText("Aanmaken");
        add.setFocusable(false);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel4.add(add);

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

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
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

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        try {
	    /*
	     * Validity check
	     */
	    if (!valid()) {
		return;
	    }

            System.out.println("\"" + comboGemeentes.getSelectedItem() + "\"");

            model.setFirm(txtFirma.getText());
            model.setSortKey(txtFirma.getText());
            model.setAddress(txtAdres.getText());
            model.setMunicipality(comboGemeentes.getSelectedItem().toString());
            model.setZipcode(txtPostcode.getText());
            model.setTelephone(txtTel.getText());
            model.setTelephone2(txtTel2.getText());
            model.setFax(txtFax.getText());
            model.setCellphone(txtGSM.getText());
            model.setEmail(txtEmail.getText());
            model.setContact(txtContact.getText());
            model.setNotes(notesOutlet.getText());
            model.setTaxnumber(taxnrOutlet.getText());
            model.setPricecode(pricecodeOutlet.getSelectedItem().toString());
            if (type == Contact.both) {
                model.setType(typeBox.getSelectedItem().toString());
            } else if (type == Contact.supplier) {
                model.setType("supplier");
            } else if (type == Contact.client) {
                model.setType("client");
            }

            FunctionResult<Contact> result = model.create();
            if (result.getCode() == 0 && result.getObj() != null) {
//		delegate.addAndSelect(result.getObj());
                delegate.addContact(result.getObj());
                disposeLater();
            } else {
                // switch case the return code
                String msg;
                switch (result.getCode()) {
                    case 1:
                        msg = "Controleer of alle velden uniek zijn. Informatie van de databank:\n" + result.getMessage();
                        break;
                    case 4:
                    case 5:
                        msg = result.getMessage();
                        break;
                    default:
                        msg = "Het toevoegen van de contactpersoon is foutgelopen (code " + result.getCode() + "). Contacteer de ontwikkelaars met deze informatie.";
                }
                JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
                System.err.println("\"Het toevoegen van de contactpersoon heeft foutcode " + result.getCode() + " opgeleverd. Contacteer de ontwikkelaars met deze informatie.\"");
//		disposeLater();
            }
        } catch (Exception ex) {
            System.err.println("Error caught");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, tools.Utilities.incorrectFormMessage, "Fout!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_addActionPerformed

    private void txtPostcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPostcodeKeyReleased
        if (txtPostcode.getText().length() == 4) {
            int code = Integer.parseInt(txtPostcode.getText());
            if (munies.containsValue(code)) {
                for (String munie : munies.keySet()) {
                    if (munies.get(munie) == code) {
                        comboGemeentes.setSelectedItem(munie);
                        break;
                    }
                }
            } 
        } else {
            flag = true;
            comboGemeentes.setSelectedItem("");
        }
    }//GEN-LAST:event_txtPostcodeKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HolderGemeentes;
    private javax.swing.JButton add;
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
    private javax.swing.JPanel notesContainer;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JComboBox pricecodeOutlet;
    private javax.swing.JTextField taxnrOutlet;
    private javax.swing.JTextField txtAdres;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtFirma;
    private javax.swing.JTextField txtGSM;
    private javax.swing.JTextField txtPostcode;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txtTel2;
    private javax.swing.JPanel typeParent;
    private javax.swing.JLabel typeTF;
    // End of variables declaration//GEN-END:variables
    private AutocompleteCombobox comboGemeentes;

    private void loadModel() {
        ArrayList items = new ArrayList();
        items.add("");
        items.addAll(munies.keySet());
        comboGemeentes = new AutocompleteCombobox(items);

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

        HolderGemeentes.add(comboGemeentes);

    }

    private void setMunicipal() {
        String munie = comboGemeentes.getSelectedItem().toString();
        if (munies.containsKey(munie)) {
            txtPostcode.setText(munies.get((String) comboGemeentes.getSelectedItem()).toString());
        } else {
            txtPostcode.setText("");
        }
    }
    
    private boolean valid(){
	if (!txtFirma.getInputVerifier().verify(txtFirma)) {
	    JOptionPane.showMessageDialog(null, "Geef een geldige firma in. Die dient uniek te zijn en mag niet leeg zijn.", "Fout!", JOptionPane.ERROR_MESSAGE);
	    txtFirma.requestFocus();
	    return false;
	}
	
	if(!taxnrOutlet.getInputVerifier().verify(taxnrOutlet)) {
	    JOptionPane.showMessageDialog(null, "Het BTW-nummer is ongeldig, een geldig nummer bevat 9 cijfers.\nVan alle ingevoerde tekens worden enkel de cijfers gecontroleerd.", "Fout!", JOptionPane.ERROR_MESSAGE);
	    taxnrOutlet.requestFocus();
	    taxnrOutlet.selectAll();
	    return false;
	}
	
	return true;
    }
}
