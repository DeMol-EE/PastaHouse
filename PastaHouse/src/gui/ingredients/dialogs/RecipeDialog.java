/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.dialogs;

import database.Database;
import database.FunctionResult;
import database.extra.Component;
import database.extra.Ingredient;
import database.models.RecipeModel;
import database.tables.BasicIngredient;
import database.tables.Recipe;
import gui.ingredients.delegates.AddBasicIngredientDelegate;
import gui.ingredients.delegates.AddRecipeDelegate;
import gui.ingredients.delegates.RecipeDelegate;
import gui.utilities.DatePickerFactory;
import gui.utilities.TextFieldAutoHighlighter;
import gui.utilities.TextFieldValidator;
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.combobox.AutocompleteCombobox;
import gui.utilities.table.EditableRecipeTableModel;
import gui.utilities.table.TableRowTransferHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTitledPanel;
import tools.StringTools;
import tools.Utilities;

/**
 *
 * @author Warkst
 */
public class RecipeDialog extends javax.swing.JDialog implements AddBasicIngredientDelegate{
    private final RecipeDelegate delegate;
    private final RecipeModel model;
    private final EditableRecipeTableModel tableModel;
    private final JXDatePicker datePicker;
    private AutocompleteCombobox componentSelectionBox;
    private boolean editing;
    
    private RecipeDialog(java.awt.Frame parent, boolean modal, RecipeDelegate delegate, RecipeModel model) {
	super(parent, modal);
	initComponents();
	
	this.delegate = delegate;
	
	this.editing = true;
	this.model = model;
	this.tableModel = new EditableRecipeTableModel(model.getComponents());
	
	this.ingredientsOutlet.setRowHeight(ingredientsOutlet.getRowHeight()+Utilities.fontSize()-10);
	this.ingredientsOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	this.ingredientsOutlet.setModel(tableModel);
	this.ingredientsOutlet.getModel().addTableModelListener(new TableModelListener() {

	    @Override
	    public void tableChanged(TableModelEvent e) {
		updateGrossWeightOutlet();
		updatePricePerWeightOutlet();
	    }
	});
	
	this.ingredientsOutlet.setDefaultRenderer(Ingredient.class, CellRendererFactory.createCapitalizedStringCellRenderer());
	this.ingredientsOutlet.setDefaultRenderer(Double.class, CellRendererFactory.createThreeDecimalDoubleCellRenderer());
	this.ingredientsOutlet.setDefaultRenderer(Component.class, CellRendererFactory.createTwoDecimalDoubleCellRenderer());
	this.ingredientsOutlet.setDefaultRenderer(String.class, CellRendererFactory.createCapitalizedStringCellRenderer(true));
	this.ingredientsOutlet.setDefaultRenderer(Integer.class, CellRendererFactory.createIndexCellRenderer());
	
	this.ingredientsOutlet.setDragEnabled(true);
	this.ingredientsOutlet.setDropMode(DropMode.INSERT_ROWS);
	this.ingredientsOutlet.setTransferHandler(new TableRowTransferHandler(this.ingredientsOutlet)); 
	
	this.ingredientsOutlet.setFillsViewportHeight(true);
	this.ingredientsOutlet.setShowGrid(true);
	this.ingredientsOutlet.setShowHorizontalLines(true);
	this.ingredientsOutlet.setShowVerticalLines(true);
	
	this.ingredientsOutlet.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
	this.ingredientsOutlet.getColumnModel().getColumn(0).setPreferredWidth(15);

	preparationOutlet.setFont(new Font(preparationOutlet.getFont().getName(), Font.PLAIN, Utilities.fontSize()));
	
	jPanel5.add(new JXTitledPanel("Ingrediënten", componentContainer), BorderLayout.CENTER);
	jPanel5.add(new JXTitledPanel("Totaal", totalContainer), BorderLayout.SOUTH);
	
	jPanel7.add(new JXTitledPanel("Detail", detailContainer), BorderLayout.NORTH);
	jPanel7.add(new JXTitledPanel("Bereiding", preparationContainer), BorderLayout.CENTER);
	
	List ingredients = new ArrayList();
	ingredients.add("");
	ingredients.addAll(Database.driver().getIngredients());
	this.componentSelectionBox = new AutocompleteCombobox(ingredients);
	this.componentSelectionBox.setOpaque(true);
	jPanel14.add(componentSelectionBox, BorderLayout.CENTER);
	
	this.componentSelectionBox.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		quantityOutlet.requestFocus();
	    }
	});
	
	quantityOutlet.addFocusListener(new FocusListener() {

	    @Override
	    public void focusGained(FocusEvent e) {
		quantityOutlet.getInputVerifier().verify(quantityOutlet);
	    }

	    @Override
	    public void focusLost(FocusEvent e) {
		//throw new UnsupportedOperationException("Not supported yet.");
	    }
	});
	
	datePicker = DatePickerFactory.makeStandardDatePicker();
	datePicker.setDate(new Date());
	dateParent.add(datePicker);
	
	/*
	 * Add validators
	 */
	TextFieldValidator.installQuantityValidator(this, quantityOutlet, componentSelectionBox);
	TextFieldValidator.installPositiveDoubleValidator(this, netWeightOutlet);
	TextFieldValidator.installUniqueIngredientValidator(this, nameOutlet);
	
	/*
	 * Add highlighters to the textfields
	 */
	TextFieldAutoHighlighter.installHighlighter(quantityOutlet); 
	TextFieldAutoHighlighter.installHighlighter(netWeightOutlet); 
	
	/*
	 * Set dialog properties
	 */
	loadModel();
	setTitle("Recept toevoegen");
	setLocationRelativeTo(null);
    }
    
    /**
     * Creates new form RecipeDialog
     */
    private RecipeDialog(java.awt.Frame parent, boolean modal, RecipeDelegate delegate) {
	this(parent, modal, delegate, new RecipeModel());
	this.editing = false;
    }
    
    /*
     * Field initialisation
     */
    
    private void loadModel(){
	nameOutlet.setText(StringTools.capitalizeEach(model.getName()));
	quantityOutlet.setText(""+model.getNetWeight());
	preparationOutlet.setText(model.getPreparation());
	netWeightOutlet.setText(""+0);
	
	updateGrossWeightOutlet();
	updateNetWeightFormattedOutlet();
	updatePricePerWeightOutlet();
    }
    
    /*
     * Public methods
     */
    
    public static void showAddRecipeDialog(RecipeDelegate delegate){
	new RecipeDialog(null, true, delegate).setVisible(true);
    }
    
    public static void showEditRecipeDialog(RecipeDelegate delegate, Recipe model){
	new RecipeDialog(null, true, delegate, new RecipeModel(model)).setVisible(true);
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

        componentContainer = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        newBasicIngredient = new javax.swing.JButton();
        quantityOutlet = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        addComponent = new javax.swing.JButton();
        removeComponent = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jSeparator1 = new javax.swing.JSeparator();
        totalContainer = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grossWeightOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        netWeightOutlet = new javax.swing.JTextField();
        netWeightFormattedOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pricePerWeightOutlet = new javax.swing.JLabel();
        preparationContainer = new javax.swing.JScrollPane();
        preparationOutlet = new javax.swing.JTextArea();
        detailContainer = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameOutlet = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        dateParent = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel2 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        componentContainer.setLayout(new java.awt.BorderLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 0, 0, 0));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.GridLayout(1, 0));

        jPanel14.setLayout(new java.awt.BorderLayout());

        newBasicIngredient.setText("+");
        newBasicIngredient.setToolTipText("");
        newBasicIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBasicIngredientActionPerformed(evt);
            }
        });
        jPanel14.add(newBasicIngredient, java.awt.BorderLayout.WEST);

        jPanel12.add(jPanel14);

        quantityOutlet.setText("<quantityOutlet>");
        quantityOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantityOutletKeyReleased(evt);
            }
        });
        jPanel12.add(quantityOutlet);

        jPanel8.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new java.awt.GridLayout(1, 2));

        addComponent.setText("Toevoegen");
        addComponent.setFocusable(false);
        addComponent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addComponentActionPerformed(evt);
            }
        });
        jPanel9.add(addComponent);

        removeComponent.setText("Verwijderen");
        removeComponent.setFocusable(false);
        removeComponent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeComponentActionPerformed(evt);
            }
        });
        jPanel9.add(removeComponent);

        jPanel13.add(jPanel9, java.awt.BorderLayout.EAST);

        jPanel8.add(jPanel13, java.awt.BorderLayout.SOUTH);

        componentContainer.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 7, 0));
        jScrollPane4.setFocusable(false);

        ingredientsOutlet.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        ingredientsOutlet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ingredientsOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ingredientsOutletKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(ingredientsOutlet);

        jPanel15.add(jScrollPane4, java.awt.BorderLayout.CENTER);
        jPanel15.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        componentContainer.add(jPanel15, java.awt.BorderLayout.CENTER);

        totalContainer.setMinimumSize(new java.awt.Dimension(50, 84));
        totalContainer.setLayout(new java.awt.GridLayout(3, 2));

        jLabel2.setText("Totaalgewicht ingrediënten");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        totalContainer.add(jLabel2);

        grossWeightOutlet.setText("<grossWeightOutlet>");
        totalContainer.add(grossWeightOutlet);

        jLabel4.setText("Gewicht na bereiding *");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        totalContainer.add(jLabel4);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        netWeightOutlet.setText("<netWeightOutlet>");
        netWeightOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                netWeightOutletKeyReleased(evt);
            }
        });
        jPanel3.add(netWeightOutlet);

        netWeightFormattedOutlet.setText("<netWeightFormattedOutlet>");
        netWeightFormattedOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        jPanel3.add(netWeightFormattedOutlet);

        totalContainer.add(jPanel3);

        jLabel7.setText("Kostprijs per kg (BTW excl)");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        totalContainer.add(jLabel7);

        pricePerWeightOutlet.setText("<pricePerWeightOutlet>");
        totalContainer.add(pricePerWeightOutlet);

        preparationContainer.setBorder(null);
        preparationContainer.setMinimumSize(new java.awt.Dimension(400, 20));

        preparationOutlet.setColumns(50);
        preparationOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        preparationOutlet.setLineWrap(true);
        preparationOutlet.setRows(5);
        preparationOutlet.setWrapStyleWord(true);
        preparationContainer.setViewportView(preparationOutlet);

        detailContainer.setLayout(new java.awt.GridLayout(2, 2));

        nameLabel.setText("Naam *");
        nameLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        detailContainer.add(nameLabel);

        nameOutlet.setText("<nameOutlet>");
        detailContainer.add(nameOutlet);

        jLabel1.setText("Datum");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        detailContainer.add(jLabel1);

        dateParent.setLayout(new java.awt.BorderLayout());
        detailContainer.add(dateParent);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel10.setLayout(new java.awt.GridBagLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(176, 250));
        jPanel7.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        jPanel10.add(jPanel7, gridBagConstraints);

        jPanel5.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 1.0;
        jPanel10.add(jPanel5, gridBagConstraints);

        getContentPane().add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(filler1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        save.setText("Aanmaken");
        save.setFocusable(false);
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel2.add(save);

        cancel.setText("Terug");
        cancel.setFocusable(false);
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel2.add(cancel);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ingredientsOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingredientsOutletKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            int r = ingredientsOutlet.getSelectedRow();
            int c = ingredientsOutlet.getSelectedColumn();
            ingredientsOutlet.editCellAt(r, c);
            ingredientsOutlet.transferFocus();
        }
    }//GEN-LAST:event_ingredientsOutletKeyReleased

    private void addComponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addComponentActionPerformed
        try{
	    Ingredient i = null;
	    String naam = componentSelectionBox.getSelectedItem().toString().toLowerCase();
            ArrayList<Ingredient> ingrs = (ArrayList<Ingredient>) Database.driver().getIngredients();
            for(Ingredient ingr : ingrs){
                if(naam.equals(ingr.getName().toLowerCase())) {
                    i = ingr;
		    break;
                }
            }
	    if (i==null) {
		JOptionPane.showMessageDialog(null, "Selecteer een geldig ingrediënt om toe te voegen!", "Error", JOptionPane.ERROR_MESSAGE);
		componentSelectionBox.requestFocus();
		return;
	    }
	    
	    if (Double.parseDouble(this.quantityOutlet.getText())<=0) {
		JOptionPane.showMessageDialog(null, "Voer een geldige hoeveelheid in.", "Error", JOptionPane.ERROR_MESSAGE);
		quantityOutlet.requestFocus();
		return;
	    }
	    
	    /*
	     * No errors: add component and update UI
	     */
	    
	    tableModel.addComponent(i, Double.parseDouble(this.quantityOutlet.getText()));
	    
	    updateGrossWeightOutlet();
	    componentSelectionBox.setSelectedIndex(0);
	    quantityOutlet.setText("");
	    componentSelectionBox.requestFocus();
	} catch(Exception e){
	    JOptionPane.showMessageDialog(null, "Voeg een geldige hoeveelheid in.", "Error", JOptionPane.ERROR_MESSAGE);
	    quantityOutlet.transferFocus();
	}
    }//GEN-LAST:event_addComponentActionPerformed

    private void removeComponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeComponentActionPerformed
        int selected = ingredientsOutlet.getSelectedRow();
        if(ingredientsOutlet.getSelectedRow()>-1 && ingredientsOutlet.getSelectedRow() < ingredientsOutlet.getModel().getRowCount()){
            tableModel.removeRow(ingredientsOutlet.getSelectedRow());
            int row = Math.max(0, Math.min(selected, ingredientsOutlet.getModel().getRowCount()-1));
            if (ingredientsOutlet.getModel().getRowCount() > 0) {
                ingredientsOutlet.getSelectionModel().setSelectionInterval(row, row);
            }
        }
    }//GEN-LAST:event_removeComponentActionPerformed

    private void newBasicIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBasicIngredientActionPerformed
        new AddBasicIngredientDialog(null, true, this).setVisible(true);
    }//GEN-LAST:event_newBasicIngredientActionPerformed

    private void netWeightOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_netWeightOutletKeyReleased
        updateNetWeightFormattedOutlet();
        updatePricePerWeightOutlet();
    }//GEN-LAST:event_netWeightOutletKeyReleased

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
	    /*
	     * Errors: check validity
	     */
	    if (!valid()) {
		return;
	    }

	    /*
	     * Warnings
	     */
	    if (Double.parseDouble(netWeightOutlet.getText())>model.getGrossWeight()) {
		int result = JOptionPane.showOptionDialog(null, "Bent u zeker dat het netto gewicht hoger is dan het bruto gewicht?", "Waarschuwing!", 0, JOptionPane.WARNING_MESSAGE, null, new String[]{"Ja", "Aanpassen"}, "Ja");
		if (result!=0) {
		    netWeightOutlet.requestFocus();
		    return;
		}
	    }
	    
	    /*
	     * Save changes 
	     * (note that the model's components are already set due to the fact 
	     * the table model uses the model's components map as a data model)
	     */
            model.setName(nameOutlet.getText());
            model.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            model.setNetWeight(Double.parseDouble(netWeightOutlet.getText()));
            model.setPreparation(preparationOutlet.getText());

	    if (!editing) {
		FunctionResult<Recipe> res = model.create();
		if (res.getCode() == 0 && res.getObj() != null) {
		    delegate.addRecipe(res.getObj());
		    disposeLater();
		} else {

		    String msg;
		    switch(res.getCode()){
			case 1:
			msg = "Controleer of alle velden uniek zijn. Informatie van de databank:\n"+res.getMessage();
			break;
			case 4:
			msg = res.getMessage();
			break;
			default: msg = "Het toevoegen van het recept is foutgelopen (code "+res.getCode()+"). Contacteer de ontwikkelaars met deze informatie.";
		    }

		    JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
		    //		disposeLater();
		}
	    } else {
//		FunctionResult res = model.update();
//		if(res.getCode() == 0){
//		    delegate.editRecipe(model, defaultModel);
//		    disposeLater();
//		} else {
//		    String msg;
//		    switch(res.getCode()){
//			case 1: case 2:
//			    msg = res.getMessage();
//			    break;
//			default:
//			    msg = "Er is een fout opgetreden bij het opslaan van dit recept in de databank (code "+res.getCode()+"). Contacteer de ontwikkelaars met deze informatie.";
//		    }
//		    JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
//		}
	    }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, Utilities.incorrectFormMessage, "Fout!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveActionPerformed

    private void updateGrossWeightOutlet(){
	grossWeightOutlet.setText(new DecimalFormat("0.000").format(model.getGrossWeight())+" kg");
    }
    
    private void updatePricePerWeightOutlet(){
	try{
	    Double.parseDouble(netWeightOutlet.getText());
	    pricePerWeightOutlet.setText(new DecimalFormat("0.000").format(model.getPricePerWeight())+" euro/kg");
	    pricePerWeightOutlet.setForeground(Color.black);
	} catch (Exception e){
	    pricePerWeightOutlet.setText("???");
	    pricePerWeightOutlet.setForeground(Color.red);
	}
    }
    
    private void disposeLater(){
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		dispose();
	    }
	});
    }
    
    private void updateNetWeightFormattedOutlet(){
	try {
	    double d = Double.parseDouble(netWeightOutlet.getText());
	    model.setNetWeight(d);
	    netWeightFormattedOutlet.setText(new DecimalFormat("0.00").format(d)+" kg");
	    netWeightFormattedOutlet.setForeground(Color.black);
	    netWeightOutlet.setForeground(Color.black);
	} catch (Exception e){
	    netWeightFormattedOutlet.setForeground(Color.red);
	    netWeightFormattedOutlet.setText("???");
	    netWeightOutlet.setForeground(Color.red);
	}
    }
    
    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        disposeLater();
    }//GEN-LAST:event_cancelActionPerformed

    private void quantityOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantityOutletKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
	    addComponentActionPerformed(null);
	}
    }//GEN-LAST:event_quantityOutletKeyReleased

    @Override
    public void addBasicIngredient(BasicIngredient bi) {
	/*
	 * Update the ingredient selection combo box's model
	 */
	List ingredients = new ArrayList();
	ingredients.add("");
	ingredients.addAll(Database.driver().getIngredients());
	this.componentSelectionBox.setDataList(ingredients);
	this.componentSelectionBox.setSelectedItem(bi);
    }
    
    private boolean valid(){
	if (!nameOutlet.getInputVerifier().verify(nameOutlet)) {
	    JOptionPane.showMessageDialog(null, "De naam van het recept moet uniek en niet leeg zijn!", "Fout!", JOptionPane.WARNING_MESSAGE);
	    nameOutlet.requestFocus();
	    return false;
	}
	
	if (model.getComponents().isEmpty()) {
	    JOptionPane.showMessageDialog(null, "Het recept moet minstens 1 ingrediënt bevatten", "Fout!", JOptionPane.WARNING_MESSAGE);
	    componentSelectionBox.requestFocus();
	    return false;
	}
	
	if (!netWeightOutlet.getInputVerifier().verify(netWeightOutlet)) {
	    JOptionPane.showMessageDialog(null, "Kijk het nettogewicht na!", "Fout!", JOptionPane.WARNING_MESSAGE);
	    netWeightOutlet.requestFocus();
	    return false;
	}
	
	return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addComponent;
    private javax.swing.JButton cancel;
    private javax.swing.JPanel componentContainer;
    private javax.swing.JPanel dateParent;
    private javax.swing.JPanel detailContainer;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel grossWeightOutlet;
    private final javax.swing.JTable ingredientsOutlet = new javax.swing.JTable();
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameOutlet;
    private javax.swing.JLabel netWeightFormattedOutlet;
    private javax.swing.JTextField netWeightOutlet;
    private javax.swing.JButton newBasicIngredient;
    private javax.swing.JScrollPane preparationContainer;
    private javax.swing.JTextArea preparationOutlet;
    private javax.swing.JLabel pricePerWeightOutlet;
    private javax.swing.JTextField quantityOutlet;
    private javax.swing.JButton removeComponent;
    private javax.swing.JButton save;
    private javax.swing.JPanel totalContainer;
    // End of variables declaration//GEN-END:variables

}
