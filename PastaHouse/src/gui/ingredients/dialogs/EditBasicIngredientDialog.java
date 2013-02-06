/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.dialogs;

import com.michaelbaranov.microba.calendar.DatePicker;
import database.Database;
import database.FunctionResult;
import database.extra.Ingredient;
import database.tables.BasicIngredient;
import database.tables.Contact;
import gui.contacts.delegates.AddContactDelegate;
import gui.contacts.dialogs.AddContactDialog;
import gui.ingredients.delegates.EditBasicIngredientDelegate;
import gui.utilities.AcceleratorAdder;
import gui.utilities.KeyAction;
import gui.utilities.TextFieldAutoHighlighter;
import gui.utilities.combobox.AutocompleteCombobox;
import gui.utilities.combobox.ComboBoxModelFactory;
import gui.utilities.validation.AbstractValidator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DateFormatter;
import org.jdesktop.swingx.JXTitledPanel;
import tools.Utilities;

/**
 *
 * @author Robin jr
 */
public class EditBasicIngredientDialog extends javax.swing.JDialog implements AddContactDelegate {

    private final BasicIngredient model;
    private final BasicIngredient defaultModel;
    private final EditBasicIngredientDelegate delegate;
    private final DatePicker dp;
    private final AutocompleteCombobox supplierBox;
    private final ButtonGroup buttons;

    /**
     * Creates new form EditBasicIngredientDialog
     */
    public EditBasicIngredientDialog(java.awt.Frame parent, boolean modal, EditBasicIngredientDelegate delegate, BasicIngredient model) {
	super(parent, modal);
	initComponents();

	this.delegate = delegate;
	this.model = model;
	this.defaultModel = new BasicIngredient(model);

	supplierParent.removeAll();
	List suppliers = new ArrayList();
	suppliers.add("");
	suppliers.addAll(Database.driver().getSuppliersAlphabetically().values());
	supplierBox = new AutocompleteCombobox(suppliers);
	supplierParent.add(supplierBox, BorderLayout.CENTER);
	supplierParent.add(addSupplier, BorderLayout.EAST);

	setLocationRelativeTo(null);
	setTitle("Ingrediënt wijzigen");

	buttons = new ButtonGroup();
	buttons.add(bulkOutlet);
	buttons.add(perUnitOutlet);
	bulkOutlet.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		updatePricePanel();
	    }
	});
	perUnitOutlet.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		updatePricePanel();
	    }
	});
	
	notesOutlet.setFont(new Font(notesOutlet.getFont().getName(), Font.PLAIN, Utilities.fontSize()));

	supplierOutlet.setModel(ComboBoxModelFactory.createSupplierComboBoxModel(Database.driver().getSuppliersAlphabetically().values().toArray()));

	dp = new DatePicker(new Date(), new SimpleDateFormat("dd/MM/yyyy"));
	datePanel.add(dp, BorderLayout.CENTER);

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

	loadModel();

	TextFieldAutoHighlighter.installHighlighter(taxesOutlet);
	TextFieldAutoHighlighter.installHighlighter(lossOutlet);
	TextFieldAutoHighlighter.installHighlighter(pricePerWeightOutlet);
	TextFieldAutoHighlighter.installHighlighter(pricePerUnitOutlet);
	TextFieldAutoHighlighter.installHighlighter(weightPerUnitOutlet);

	ingredientOutlet.setInputVerifier(new AbstractValidator(this, ingredientOutlet, "De naam moet uniek en niet leeg zijn!") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		try{
		    if (ingredientOutlet.getText().isEmpty()) {
			return false;
		    }
		    if (!ingredientOutlet.getText().equalsIgnoreCase(defaultModel.getName())) {
			List<Ingredient> ingrs = database.Database.driver().getIngredients();
			for (Ingredient ingredient : ingrs) {
			    if (ingredient.getName().equalsIgnoreCase(ingredientOutlet.getText())) {
				return false;
			    }
			}
		    }
		    return true;
		} catch(Exception e){
		    return false;
		}
	    }
	});
	lossOutlet.setInputVerifier(new AbstractValidator(this, lossOutlet, "Gelieve een geldige waarde tussen 0.0 en 100.0 (%) in te voeren.") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		try{
		    double d = Double.parseDouble(lossOutlet.getText());
		    return d>=0 && d<100;
		} catch (Exception e){
		    return false;
		}
	    }
	});
	taxesOutlet.setInputVerifier(new AbstractValidator(this, taxesOutlet, "Gelieve een geldige, positieve waarde in te geven.") {

	    @Override
	    protected boolean validationCriteria(JComponent c) {
		try{
		    double d = Double.parseDouble(taxesOutlet.getText());
		    return d>=0;
		} catch (Exception e){
		    return false;
		}
	    }
	});
	
	detail.add(new JXTitledPanel("Details", fixedFields), BorderLayout.NORTH);
	detail.add(new JXTitledPanel("Opmerkingen", stretchableFields), BorderLayout.CENTER);
	
	/*
	 * Hide the unformatted fields
	 */
	grossPriceOutlet.setVisible(false);
	netPriceOutlet.setVisible(false);

	ingredientOutlet.requestFocus();
    }

    private void updatePricePanel() {
	pricePerWeightOutlet.setForeground(Color.black);
	pricePerWeightFormattedOutlet.setForeground(Color.black);

	if (bulkOutlet.isSelected()) {
	    pricePerWeightOutlet.setText("" + model.getPricePerWeight());
	    pricePerWeightFormattedOutlet.setText(new DecimalFormat("0.000").format(model.getPricePerWeight()) + " euro/kg");

	    packagingOutlet.setEnabled(false);
	    weightPerUnitOutlet.setEnabled(false);
	    weightPerUnitOutlet.setText(null);
	    pricePerUnitOutlet.setEnabled(false);
	    pricePerUnitOutlet.setText(null);
	    packagingOutlet.setText(null);

	    packaging.setEnabled(false);
	    weightPerUnit.setEnabled(false);
	    pricePerUnit.setEnabled(false);

	    weightPerUnitFormattedOutlet.setText("In bulk");
	    pricePerUnitFormattedOutlet.setText("In bulk");
	    weightPerUnitFormattedOutlet.setForeground(Color.black);
	    pricePerUnitFormattedOutlet.setForeground(Color.black);

	    pricePerWeightOutlet.setEnabled(true);

	    pricePerWeightOutlet.requestFocus();
	} else {
	    pricePerWeightOutlet.setEnabled(false);

	    packaging.setEnabled(true);
	    weightPerUnit.setEnabled(true);
	    pricePerUnit.setEnabled(true);

	    packagingOutlet.setEnabled(true);
	    weightPerUnitOutlet.setEnabled(true);
	    pricePerUnitOutlet.setEnabled(true);
	    
	    weightPerUnitOutlet.setForeground(Color.black);
	    weightPerUnitFormattedOutlet.setForeground(Color.black);
	    
	    pricePerUnitOutlet.setForeground(Color.black);
	    pricePerUnitFormattedOutlet.setForeground(Color.black);

	    if (model.isInBulk()) {
		packagingOutlet.setText("");
		weightPerUnitOutlet.setText("" + 0.0);
		pricePerUnitOutlet.setText("" + 0.0);
		weightPerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(0.0) + " kg/" + packagingOutlet.getText());
		pricePerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(0.0) + " euro/" + packagingOutlet.getText());
		pricePerWeightOutlet.setText("0.0");
		pricePerWeightFormattedOutlet.setText(new DecimalFormat("0.000").format(0.0) + " euro/kg");
		
		packagingOutlet.requestFocus();
	    } else {
		packagingOutlet.setText(model.getPackaging());
		weightPerUnitOutlet.setText(""+model.getWeightPerUnit());
		pricePerUnitOutlet.setText("" +model.getPricePerUnit());
		weightPerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(model.getWeightPerUnit()) + " kg/" + packagingOutlet.getText());
		pricePerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(model.getPricePerUnit()) + " euro/" + packagingOutlet.getText());
		pricePerWeightOutlet.setText(""+model.getPricePerWeight());
		pricePerWeightFormattedOutlet.setText(new DecimalFormat("0.000").format(model.getPricePerWeight()) + " euro/kg");
		
		weightPerUnitOutlet.requestFocus();
	    }
	}

	updateGrossPriceOutlet();
    }

    private void loadModel() {
	ingredientOutlet.setText(model.getName());
	brandOutlet.setText(model.getBrand());
//	supplierOutlet.setSelectedItem(model.getSupplier());
	if (model.getSupplier() != null) {
	    supplierBox.setSelectedItem(model.getSupplier());
	}
	packagingOutlet.setText(model.getPackaging());

	DecimalFormat threeFormatter = new DecimalFormat("0.000");
	DecimalFormat twoFormatter = new DecimalFormat("0.00");

	/*
	 * check if it is bulk or not (bulk implies -1 for ppu and wpu)
	 */
	if (model.getPricePerUnit() >= 0 && model.getWeightPerUnit() >= 0) {
	    perUnitOutlet.setSelected(true);
	} else {
	    bulkOutlet.setSelected(true);
	}

	// compound fields
	lossOutlet.setText("" + model.getLossPercent());
	lossFormattedOutlet.setText(twoFormatter.format(model.getLossPercent()) + " %");
	grossPriceOutlet.setText("" + model.getGrossPrice());
	grossPriceFormattedOutlet.setText(threeFormatter.format(model.getGrossPrice()) + " euro");
	taxesOutlet.setText("" + model.getTaxes());
	taxesFormattedOutlet.setText(twoFormatter.format(model.getTaxes()) + " %");
	netPriceOutlet.setText("" + model.getNetPrice());
	netPriceFormattedOutlet.setText(twoFormatter.format(model.getNetPrice()) + " euro");
	notesOutlet.setText(model.getNotes());

	updatePricePanel();
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

        jSeparator4 = new javax.swing.JSeparator();
        fixedFields = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ingredientOutlet = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        brandOutlet = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        supplierParent = new javax.swing.JPanel();
        supplierOutlet = new javax.swing.JComboBox();
        addSupplier = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        bulkOutlet = new javax.swing.JRadioButton();
        perUnitOutlet = new javax.swing.JRadioButton();
        packaging = new javax.swing.JLabel();
        packagingOutlet = new javax.swing.JTextField();
        weightPerUnit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        weightPerUnitOutlet = new javax.swing.JTextField();
        weightPerUnitFormattedOutlet = new javax.swing.JLabel();
        pricePerUnit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pricePerUnitOutlet = new javax.swing.JTextField();
        pricePerUnitFormattedOutlet = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        pricePerWeightOutlet = new javax.swing.JTextField();
        pricePerWeightFormattedOutlet = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lossOutlet = new javax.swing.JTextField();
        lossFormattedOutlet = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        grossPriceOutlet = new javax.swing.JLabel();
        grossPriceFormattedOutlet = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        taxesOutlet = new javax.swing.JTextField();
        taxesFormattedOutlet = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        netPriceOutlet = new javax.swing.JLabel();
        netPriceFormattedOutlet = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        datePanel = new javax.swing.JPanel();
        stretchableFields = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesOutlet = new javax.swing.JTextArea();
        detail = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        fixedFields.setFocusable(false);
        fixedFields.setLayout(new java.awt.GridBagLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 5, 0));
        jPanel11.setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setText("Ingrediënt *");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel1.setFocusable(false);
        jPanel11.add(jLabel1);

        ingredientOutlet.setText("<ingredientOutlet>");
        jPanel11.add(ingredientOutlet);

        jLabel2.setText("Merk");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel2.setFocusable(false);
        jPanel11.add(jLabel2);

        brandOutlet.setText("<brandOutlet>");
        jPanel11.add(brandOutlet);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel4.setText("Leverancier *");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel4.setFocusable(false);
        jPanel10.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel10);

        supplierParent.setLayout(new java.awt.BorderLayout());

        supplierOutlet.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        supplierParent.add(supplierOutlet, java.awt.BorderLayout.CENTER);

        addSupplier.setText("+");
        addSupplier.setToolTipText("Klik hier om een nieuwe leverancier toe te voegen.");
        addSupplier.setFocusable(false);
        addSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSupplierActionPerformed(evt);
            }
        });
        supplierParent.add(addSupplier, java.awt.BorderLayout.EAST);

        jPanel11.add(supplierParent);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        fixedFields.add(jPanel11, gridBagConstraints);

        jPanel12.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        jPanel12.setLayout(new java.awt.GridLayout(5, 2));

        bulkOutlet.setText("Bulk");
        jPanel12.add(bulkOutlet);

        perUnitOutlet.setText("Verpakking");
        jPanel12.add(perUnitOutlet);

        packaging.setText("Verpakking");
        packaging.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        packaging.setFocusable(false);
        jPanel12.add(packaging);

        packagingOutlet.setText("<packagingOutlet>");
        packagingOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                packagingOutletKeyReleased(evt);
            }
        });
        jPanel12.add(packagingOutlet);

        weightPerUnit.setText("Gewicht per verpakking *");
        weightPerUnit.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        weightPerUnit.setFocusable(false);
        jPanel12.add(weightPerUnit);

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        weightPerUnitOutlet.setText("<weightPerUnitOutlet>");
        weightPerUnitOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                weightPerUnitOutletKeyReleased(evt);
            }
        });
        jPanel2.add(weightPerUnitOutlet);

        weightPerUnitFormattedOutlet.setText("<weightPerUnitFormattedOutlet>");
        jPanel2.add(weightPerUnitFormattedOutlet);

        jPanel12.add(jPanel2);

        pricePerUnit.setText("Prijs per verpakking (BTW excl) *");
        pricePerUnit.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        pricePerUnit.setFocusable(false);
        jPanel12.add(pricePerUnit);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        pricePerUnitOutlet.setText("<pricePerUnitOutlet>");
        pricePerUnitOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pricePerUnitOutletKeyReleased(evt);
            }
        });
        jPanel1.add(pricePerUnitOutlet);

        pricePerUnitFormattedOutlet.setText("<pricePerUnitFormattedOutlet>");
        jPanel1.add(pricePerUnitFormattedOutlet);

        jPanel12.add(jPanel1);

        jLabel12.setText("Prijs per kg (BTW excl)");
        jLabel12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel12.setFocusable(false);
        jPanel12.add(jLabel12);

        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        pricePerWeightOutlet.setText("<pricePerWeightOutlet>");
        pricePerWeightOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pricePerWeightOutletKeyReleased(evt);
            }
        });
        jPanel5.add(pricePerWeightOutlet);

        pricePerWeightFormattedOutlet.setText("<pricePerWeightFormattedOutlet>");
        jPanel5.add(pricePerWeightFormattedOutlet);

        jPanel12.add(jPanel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        fixedFields.add(jPanel12, gridBagConstraints);

        jPanel13.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        jPanel13.setLayout(new java.awt.GridLayout(5, 2));

        jLabel14.setText("Verliespercentage *");
        jLabel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel14.setFocusable(false);
        jPanel13.add(jLabel14);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        lossOutlet.setText("<lossOutlet>");
        lossOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lossOutletKeyReleased(evt);
            }
        });
        jPanel6.add(lossOutlet);

        lossFormattedOutlet.setText("<lossFormattedOutlet>");
        jPanel6.add(lossFormattedOutlet);

        jPanel13.add(jPanel6);

        jLabel16.setText("Totaalprijs (BTW excl)");
        jLabel16.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel16.setFocusable(false);
        jPanel13.add(jLabel16);

        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        grossPriceOutlet.setText("<grossPriceOutlet>");
        jPanel7.add(grossPriceOutlet);

        grossPriceFormattedOutlet.setText("<grossPriceFormattedOutlet>");
        jPanel7.add(grossPriceFormattedOutlet);

        jPanel13.add(jPanel7);

        jLabel18.setText("BTW *");
        jLabel18.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel18.setFocusable(false);
        jPanel13.add(jLabel18);

        jPanel8.setLayout(new java.awt.GridLayout(1, 2));

        taxesOutlet.setText("<taxesOutlet>");
        taxesOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                taxesOutletKeyReleased(evt);
            }
        });
        jPanel8.add(taxesOutlet);

        taxesFormattedOutlet.setText("<taxesFormattedOutlet>");
        jPanel8.add(taxesFormattedOutlet);

        jPanel13.add(jPanel8);

        jLabel20.setText("Totaalprijs");
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel20.setFocusable(false);
        jPanel13.add(jLabel20);

        jPanel9.setLayout(new java.awt.GridLayout(1, 2));

        netPriceOutlet.setText("<netPriceOutlet>");
        jPanel9.add(netPriceOutlet);

        netPriceFormattedOutlet.setText("<netPriceFormattedOutlet>");
        jPanel9.add(netPriceFormattedOutlet);

        jPanel13.add(jPanel9);

        jLabel22.setText("Datum");
        jLabel22.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jLabel22.setFocusable(false);
        jPanel13.add(jLabel22);

        datePanel.setLayout(new java.awt.BorderLayout());
        jPanel13.add(datePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.3;
        fixedFields.add(jPanel13, gridBagConstraints);

        stretchableFields.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);

        notesOutlet.setColumns(20);
        notesOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        notesOutlet.setLineWrap(true);
        notesOutlet.setRows(1);
        notesOutlet.setWrapStyleWord(true);
        jScrollPane2.setViewportView(notesOutlet);

        stretchableFields.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 412));
        setPreferredSize(new java.awt.Dimension(600, 800));

        detail.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        save.setText("Opslaan");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel4.add(save);

        cancel.setText("Terug");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel4.add(cancel);

        jPanel3.add(jPanel4, java.awt.BorderLayout.EAST);
        jPanel3.add(filler1, java.awt.BorderLayout.CENTER);

        detail.add(jPanel3, java.awt.BorderLayout.SOUTH);

        getContentPane().add(detail, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void weightPerUnitOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weightPerUnitOutletKeyReleased
//	updatePricePerWeightOutlet();
	try {
	    weightPerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(Double.parseDouble(weightPerUnitOutlet.getText())) + " kg/" + packagingOutlet.getText());
	    weightPerUnitFormattedOutlet.setForeground(Color.black);
	    weightPerUnitOutlet.setForeground(Color.black);
	} catch (Exception e) {
	    weightPerUnitFormattedOutlet.setText("??? kg/" + packagingOutlet.getText());
	    weightPerUnitFormattedOutlet.setForeground(Color.red);
	    weightPerUnitOutlet.setForeground(Color.red);
	    System.err.println("Exception: " + e.getMessage());
	}
	updatePricePerWeight();
    }//GEN-LAST:event_weightPerUnitOutletKeyReleased

    private void pricePerUnitOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pricePerUnitOutletKeyReleased
//	updatePricePerWeightOutlet();
	try {
	    pricePerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(Double.parseDouble(pricePerUnitOutlet.getText())) + " euro/" + packagingOutlet.getText());
	    pricePerUnitFormattedOutlet.setForeground(Color.black);
	    pricePerUnitOutlet.setForeground(Color.black);
	} catch (Exception e) {
	    pricePerUnitFormattedOutlet.setForeground(Color.red);
	    pricePerUnitOutlet.setForeground(Color.red);
	    pricePerUnitFormattedOutlet.setText("??? euro/" + packagingOutlet.getText());
	    System.err.println("Exception: " + e.getMessage());
	}
	updatePricePerWeight();
    }//GEN-LAST:event_pricePerUnitOutletKeyReleased

    private void updatePricePerUnit() {
	try {
	    double wpu = Double.parseDouble(weightPerUnitOutlet.getText());
	    double ppw = Double.parseDouble(pricePerWeightOutlet.getText());
	    double ppu = ppw * wpu;
	    pricePerUnitOutlet.setText("" + ppu);
	    pricePerUnitOutlet.setForeground(Color.black);
	    pricePerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(ppu) + " euro/" + packagingOutlet.getText());
	    pricePerUnitFormattedOutlet.setForeground(Color.black);
	} catch (Exception e) {
	    pricePerUnitOutlet.setText("");
	    pricePerUnitOutlet.setForeground(Color.red);
	    pricePerUnitFormattedOutlet.setText("??? euro/" + packagingOutlet.getText());
	    pricePerUnitFormattedOutlet.setForeground(Color.red);
	}
    }

    private void updatePricePerWeight() {
	boolean failed = false;

	double wpu;
	try {
	    wpu = Double.parseDouble(weightPerUnitOutlet.getText());
	} catch (Exception e) {
	    pricePerWeightOutlet.setText("");
	    pricePerWeightOutlet.setForeground(Color.red);
	    pricePerWeightFormattedOutlet.setText("??? euro/kg");
	    pricePerWeightFormattedOutlet.setForeground(Color.red);

	    failed = true;
	    wpu = 1.0;
	}

	double ppu;
	try {
	    ppu = Double.parseDouble(pricePerUnitOutlet.getText());
	} catch (Exception e) {
	    pricePerWeightOutlet.setText("");
	    pricePerWeightOutlet.setForeground(Color.red);
	    pricePerWeightFormattedOutlet.setText("??? euro/kg");
	    pricePerWeightFormattedOutlet.setForeground(Color.red);

	    failed = true;
	    ppu = 0.0;
	}

	if (!failed) {
	    double ppw = ppu / wpu;
	    pricePerWeightOutlet.setText("" + ppw);
	    pricePerWeightOutlet.setForeground(Color.black);
	    pricePerWeightFormattedOutlet.setText(new DecimalFormat("0.000").format(ppw) + " euro/kg");
	    pricePerWeightFormattedOutlet.setForeground(Color.black);
	}

	updateGrossPriceOutlet();
    }

    private void disposeLater() {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		dispose();
	    }
	});
    }

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
	model.copy(defaultModel);
	disposeLater();
    }//GEN-LAST:event_cancelActionPerformed

    private void updatePricePerWeightOutlet() {
	boolean failed = false;

	double ppu;
	try {
	    ppu = Double.parseDouble(pricePerUnitOutlet.getText());
	    pricePerUnitOutlet.setForeground(Color.black);
	    pricePerUnitFormattedOutlet.setForeground(Color.black);
	    pricePerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(ppu) + " euro/" + packagingOutlet.getText());
	} catch (Exception e) {
	    pricePerUnitOutlet.setForeground(Color.red);
	    pricePerUnitFormattedOutlet.setForeground(Color.red);
	    pricePerUnitFormattedOutlet.setText("??? euro/" + packagingOutlet.getText());
	    pricePerWeightOutlet.setForeground(Color.red);
	    pricePerWeightOutlet.setText("???");
	    pricePerWeightFormattedOutlet.setForeground(Color.red);
	    pricePerWeightFormattedOutlet.setText("??? euro");
	    updateGrossPriceOutlet();
	    failed = true;
	    ppu = 0.0;
	}

	double wpu;
	try {
	    wpu = Double.parseDouble(weightPerUnitOutlet.getText());
	    weightPerUnitOutlet.setForeground(Color.black);
	    weightPerUnitFormattedOutlet.setForeground(Color.black);
	    weightPerUnitFormattedOutlet.setText(new DecimalFormat("0.000").format(wpu) + " kg/" + packagingOutlet.getText());
	} catch (Exception e) {
	    weightPerUnitOutlet.setForeground(Color.red);
	    weightPerUnitFormattedOutlet.setForeground(Color.red);
	    weightPerUnitFormattedOutlet.setText("??? kg/" + packagingOutlet.getText());
	    pricePerWeightOutlet.setForeground(Color.red);
	    pricePerWeightOutlet.setText("???");
	    pricePerWeightFormattedOutlet.setForeground(Color.red);
	    pricePerWeightFormattedOutlet.setText("??? euro");
	    updateGrossPriceOutlet();
	    failed = true;
	    wpu = 0.0;
	}

	if (failed) {
	    return;
	}

	pricePerWeightOutlet.setText("" + (ppu / wpu));
	pricePerWeightOutlet.setForeground(Color.BLACK);
	pricePerWeightFormattedOutlet.setText(new DecimalFormat("0.000").format(ppu / wpu) + " euro/kg");
	pricePerWeightFormattedOutlet.setForeground(Color.BLACK);

	updateGrossPriceOutlet();
    }

    private void updateGrossPriceOutlet() {
	boolean failed = false;

	double loss;
	try {
	    loss = Double.parseDouble(lossOutlet.getText());
	    lossOutlet.setForeground(Color.black);
	    lossFormattedOutlet.setForeground(Color.black);
	    lossFormattedOutlet.setText(new DecimalFormat("0.00").format(loss) + " %");
	} catch (Exception e) {
	    lossOutlet.setForeground(Color.red);
	    lossFormattedOutlet.setForeground(Color.red);
	    lossFormattedOutlet.setText("??? %");
	    grossPriceOutlet.setForeground(Color.red);
	    grossPriceOutlet.setText("???");
	    grossPriceFormattedOutlet.setForeground(Color.red);
	    grossPriceFormattedOutlet.setText("??? euro");
	    updateNetPriceOutlet();
	    failed = true;
	    loss = 0.0;
	}

	double ppw;
	try {
	    ppw = Double.parseDouble(pricePerWeightOutlet.getText());
	    pricePerWeightOutlet.setForeground(Color.black);
	    pricePerWeightFormattedOutlet.setForeground(Color.black);
	    pricePerWeightFormattedOutlet.setText(new DecimalFormat("0.000").format(ppw) + " euro/kg");
	} catch (Exception e) {
	    pricePerWeightOutlet.setForeground(Color.red);
	    pricePerWeightFormattedOutlet.setForeground(Color.red);
	    pricePerWeightFormattedOutlet.setText("??? euro/kg");
	    grossPriceOutlet.setForeground(Color.red);
	    grossPriceOutlet.setText("???");
	    grossPriceFormattedOutlet.setForeground(Color.red);
	    grossPriceFormattedOutlet.setText("??? euro");
	    updateNetPriceOutlet();
	    failed = true;
	    ppw = 0.0;
	}

	if (failed) {
	    return;
	}

	grossPriceOutlet.setText("" + (ppw / (1.0 - (0.01 * loss))));
	grossPriceOutlet.setForeground(Color.BLACK);
	grossPriceFormattedOutlet.setForeground(Color.BLACK);
	grossPriceFormattedOutlet.setText(new DecimalFormat("0.000").format(ppw / (1.0 - (0.01 * loss))) + " euro");

	updateNetPriceOutlet();
    }

    private void updateNetPriceOutlet() {
	boolean failed = false;

	double taxes;
	try {
	    taxes = Double.parseDouble(taxesOutlet.getText());
	    taxesOutlet.setForeground(Color.black);
	    taxesFormattedOutlet.setForeground(Color.black);
	    taxesFormattedOutlet.setText(new DecimalFormat("0.00").format(taxes) + " %");
	} catch (Exception e) {
	    taxesOutlet.setForeground(Color.red);
	    taxesFormattedOutlet.setForeground(Color.red);
	    taxesFormattedOutlet.setText("??? %");
	    netPriceOutlet.setForeground(Color.red);
	    netPriceOutlet.setText("???");
	    netPriceFormattedOutlet.setForeground(Color.red);
	    netPriceFormattedOutlet.setText("??? euro");
	    failed = true;
	    taxes = 0.0;
	}

	double grossPrice;
	try {
	    grossPrice = Double.parseDouble(grossPriceOutlet.getText());
	    grossPriceOutlet.setForeground(Color.black);
	    grossPriceFormattedOutlet.setForeground(Color.black);
	    grossPriceFormattedOutlet.setText(new DecimalFormat("0.000").format(grossPrice) + " euro");
	} catch (Exception e) {
	    grossPriceOutlet.setForeground(Color.red);
	    grossPriceFormattedOutlet.setForeground(Color.red);
	    grossPriceFormattedOutlet.setText("??? euro");
	    netPriceOutlet.setForeground(Color.red);
	    netPriceOutlet.setText("???");
	    netPriceFormattedOutlet.setForeground(Color.red);
	    netPriceFormattedOutlet.setText("??? euro");
	    failed = true;
	    grossPrice = 0.0;
	}

	if (failed) {
	    return;
	}

	netPriceOutlet.setText("" + grossPrice * (1.0 + 0.01 * taxes));
	netPriceOutlet.setForeground(Color.BLACK);
	netPriceFormattedOutlet.setText(new DecimalFormat("0.00").format(grossPrice * (1.0 + 0.01 * taxes)) + " euro");
	netPriceFormattedOutlet.setForeground(Color.black);
    }

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
	try {
	    /*
	     * Validity test
	     */
	    if (!valid()) {
		return;
	    }
	    
	    /*
	     * Warnings
	     */
	    double ppw = Double.parseDouble(pricePerWeightOutlet.getText());
	    if (ppw==0) {
		int result = JOptionPane.showOptionDialog(null, "Bent u zeker dat de prijs 0 is?", "Opgelet!", 0, JOptionPane.WARNING_MESSAGE, null, new String[]{"Ja", "Aanpassen"}, "Ja");
		if (result!=0) {
		    if (bulkOutlet.isSelected()) {
			pricePerWeightOutlet.requestFocus();
		    } else {
			pricePerUnitOutlet.requestFocus();
		    }
		    return;
		}
	    }

	    /*
	     * Set values on the model
	     */
	    model.setName(ingredientOutlet.getText());
	    model.setDate(new DateFormatter(dp.getDateFormat()).valueToString(dp.getDate()));
	    model.setBrand(brandOutlet.getText());
	    if (bulkOutlet.isSelected()) {
		model.setPackaging("Bulk");
		model.setWeightPerUnit(-1);
		model.setPricePerUnit(-1);
	    } else {
		model.setPackaging(packagingOutlet.getText());
		model.setWeightPerUnit(Double.parseDouble(weightPerUnitOutlet.getText()));
		model.setPricePerUnit(Double.parseDouble(pricePerUnitOutlet.getText()));
	    }
	    model.setLossPercent(Double.parseDouble(lossOutlet.getText()));
	    model.setPricePerWeight(Double.parseDouble(pricePerWeightOutlet.getText()));
	    model.setTaxes(Double.parseDouble(taxesOutlet.getText()));
	    model.setNotes(notesOutlet.getText());

	    Contact supp = null;
	    if (supplierBox.getSelectedItem() instanceof Contact) {
		supp = (Contact) supplierBox.getSelectedItem();
	    } else if (supplierBox.getSelectedItem() instanceof String) {
		String s = (String) supplierBox.getSelectedItem();
		supp = Database.driver().getSuppliersAlphabetically().get(s.toLowerCase());
	    }
	    if (supp == null) {
		throw new Exception("Supplier was not found in the database!");
	    }

	    model.setSupplier(supp);

	    if (!defaultModel.getName().equalsIgnoreCase(model.getName())) {
		if (Database.driver().getBasicIngredientsAlphabetically().containsKey(model.getName().toLowerCase())) {
		    JOptionPane.showMessageDialog(null, "Er is al een basisingrediënt met deze naam.", "Fout!", JOptionPane.ERROR_MESSAGE);
		    return;
		}
	    }

	    FunctionResult res = model.update();
	    if (res.getCode() == 0) {
		delegate.editBasicIngredient(model, defaultModel);
		disposeLater();
	    } else {
		String msg;
		switch (res.getCode()) {
		    case 1:
		    case 2:
			msg = res.getMessage();
			break;
		    default:
			msg = "Er is een fout opgetreden bij het opslaan van dit basisingrediënt in de databank (code " + res.getCode() + "). Contacteer de ontwikkelaars met deze informatie.";
		}
		JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
	    }
	} catch (Exception ex) {
	    System.err.println(ex.getMessage());
	    JOptionPane.showMessageDialog(null, Utilities.incorrectFormMessage, "Fout!", JOptionPane.ERROR_MESSAGE);
	}
    }//GEN-LAST:event_saveActionPerformed

    private void lossOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lossOutletKeyReleased
	updateGrossPriceOutlet();
    }//GEN-LAST:event_lossOutletKeyReleased

    private void taxesOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taxesOutletKeyReleased
	updateNetPriceOutlet();
    }//GEN-LAST:event_taxesOutletKeyReleased

    private void packagingOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_packagingOutletKeyReleased
	//update all the formatted labels that use packaging
	String cppu = pricePerUnitFormattedOutlet.getText();
	pricePerUnitFormattedOutlet.setText(cppu.substring(0, cppu.lastIndexOf("/") + 1) + packagingOutlet.getText());
	String cwpu = weightPerUnitFormattedOutlet.getText();
	weightPerUnitFormattedOutlet.setText(cwpu.substring(0, cwpu.lastIndexOf("/") + 1) + packagingOutlet.getText());
    }//GEN-LAST:event_packagingOutletKeyReleased

    private void addSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSupplierActionPerformed
	AddContactDialog.createSupplierDialog(this).setVisible(true);
    }//GEN-LAST:event_addSupplierActionPerformed

    private void pricePerWeightOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pricePerWeightOutletKeyReleased
	try {
	    pricePerWeightFormattedOutlet.setText(new DecimalFormat("0.000").format(Double.parseDouble(pricePerWeightOutlet.getText())) + " euro/kg");
	    pricePerWeightFormattedOutlet.setForeground(Color.black);
	    pricePerWeightOutlet.setForeground(Color.black);
	} catch (Exception e) {
	    pricePerWeightFormattedOutlet.setForeground(Color.red);
	    pricePerWeightOutlet.setForeground(Color.red);
	    pricePerWeightFormattedOutlet.setText("??? euro/kg");
	    System.err.println("Exception: " + e.getMessage());
	}
//	updatePricePerUnit();
	updateGrossPriceOutlet();
    }//GEN-LAST:event_pricePerWeightOutletKeyReleased
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSupplier;
    private javax.swing.JTextField brandOutlet;
    private javax.swing.JRadioButton bulkOutlet;
    private javax.swing.JButton cancel;
    private javax.swing.JPanel datePanel;
    private javax.swing.JPanel detail;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel fixedFields;
    private javax.swing.JLabel grossPriceFormattedOutlet;
    private javax.swing.JLabel grossPriceOutlet;
    private javax.swing.JTextField ingredientOutlet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lossFormattedOutlet;
    private javax.swing.JTextField lossOutlet;
    private javax.swing.JLabel netPriceFormattedOutlet;
    private javax.swing.JLabel netPriceOutlet;
    private javax.swing.JTextArea notesOutlet;
    private javax.swing.JLabel packaging;
    private javax.swing.JTextField packagingOutlet;
    private javax.swing.JRadioButton perUnitOutlet;
    private javax.swing.JLabel pricePerUnit;
    private javax.swing.JLabel pricePerUnitFormattedOutlet;
    private javax.swing.JTextField pricePerUnitOutlet;
    private javax.swing.JLabel pricePerWeightFormattedOutlet;
    private javax.swing.JTextField pricePerWeightOutlet;
    private javax.swing.JButton save;
    private javax.swing.JPanel stretchableFields;
    private javax.swing.JComboBox supplierOutlet;
    private javax.swing.JPanel supplierParent;
    private javax.swing.JLabel taxesFormattedOutlet;
    private javax.swing.JTextField taxesOutlet;
    private javax.swing.JLabel weightPerUnit;
    private javax.swing.JLabel weightPerUnitFormattedOutlet;
    private javax.swing.JTextField weightPerUnitOutlet;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addContact(Contact s) {
	supplierParent.removeAll();
	List suppliers = new ArrayList();
	suppliers.add("");
	suppliers.addAll(Database.driver().getSuppliersAlphabetically().values());
	supplierBox.setDataList(suppliers);
	supplierParent.add(supplierBox, BorderLayout.CENTER);
	/*
	 * Select the newly created supplier
	 */
	supplierBox.setSelectedItem(s);

	pricePerUnitOutlet.requestFocus();
    }
    
    private boolean valid(){
	if (!ingredientOutlet.getInputVerifier().verify(ingredientOutlet)) {
	    JOptionPane.showMessageDialog(null, "Gelieve een geldige naam in te voeren!", "Fout!", JOptionPane.ERROR_MESSAGE);
	    ingredientOutlet.requestFocus();
	    return false;
	}
	
	if (supplierBox.getSelectedIndex()==0) {
	    JOptionPane.showMessageDialog(null, "Gelieve een geldige leverancier te kiezen!", "Fout!", JOptionPane.ERROR_MESSAGE);
	    supplierBox.requestFocus();
	    return false;
	}
	
	if (!bulkOutlet.isSelected()) {
	    /*
	     * Test packaging
	     */
	    if (packagingOutlet.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "Gelieve een geldige verpakking in te geven!", "Fout!", JOptionPane.ERROR_MESSAGE);
		packagingOutlet.requestFocus();
		return false;
	    }
	    /*
	     * Test PPU
	     */
	    try{
		double ppu = Double.parseDouble(pricePerUnitOutlet.getText());
		if (ppu<0) {
		    throw new Exception("derp");
		}
	    } catch(Exception e){
		JOptionPane.showMessageDialog(null, "Gelieve een geldige, positieve prijs per eenheid in te geven!", "Fout!", JOptionPane.ERROR_MESSAGE);
		pricePerUnitOutlet.requestFocus();
		return false;
	    }
	    /*
	     * Test WPU
	     */
	    try{
		double wpu = Double.parseDouble(weightPerUnitOutlet.getText());
		if (wpu<=0) {
		    throw new Exception("derp");
		}
	    } catch(Exception e){
		JOptionPane.showMessageDialog(null, "Gelieve een geldige, strikt positief gewicht per eenheid in te geven!\n(Gewicht mag niet 0.0 zijn)", "Fout!", JOptionPane.ERROR_MESSAGE);
		weightPerUnitOutlet.requestFocus();
		return false;
	    }
	} else {
	    /*
	     * Test PPW
	     */
	    try{
		double d = Double.parseDouble(pricePerWeightOutlet.getText());
		if (d<0.0) throw new Exception("Derp");
	    } catch(Exception e){
		JOptionPane.showMessageDialog(null, "Gelieve een geldige, positieve bulkprijs in te geven!", "Fout!", JOptionPane.ERROR_MESSAGE);
		pricePerWeightOutlet.requestFocus();
		return false;
	    }
	}
	
	if (!lossOutlet.getInputVerifier().verify(lossOutlet)) {
	    JOptionPane.showMessageDialog(null, "Kijk het verliespercentage na!", "Fout!", JOptionPane.ERROR_MESSAGE);
	    lossOutlet.requestFocus();
	    return false;
	}
	
	if (!taxesOutlet.getInputVerifier().verify(taxesOutlet)) {
	    JOptionPane.showMessageDialog(null, "Kijk het BTWpercentage na!", "Fout!", JOptionPane.ERROR_MESSAGE);
	    ingredientOutlet.requestFocus();
	    return false;
	}
	return true;
    }
}
