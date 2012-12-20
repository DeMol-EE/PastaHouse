/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.dialogs;

import com.michaelbaranov.microba.calendar.DatePicker;
import database.Database;
import database.FunctionResult;
import database.extra.Component;
import database.extra.Ingredient;
import database.tables.BasicIngredient;
import database.tables.Recipe;
import gui.ingredients.delegates.AddBasicIngredientDelegate;
import gui.ingredients.delegates.ComboCoxDelegate;
import gui.ingredients.delegates.EditRecipeDelegate;
import gui.utilities.AcceleratorAdder;
import gui.utilities.KeyAction;
import gui.utilities.cell.CellEditorFactory;
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.table.EditableRecipeTableModel;
import gui.utilities.table.TableRowTransferHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.text.DateFormatter;
import tools.Utilities;

/**
 *
 * @author Robin jr
 */
public class EditRecipeDialog extends javax.swing.JDialog implements ComboCoxDelegate, AddBasicIngredientDelegate{

    private final Recipe model;
    private final Recipe defaultModel;
    private final DatePicker dp;
    private final EditRecipeDelegate delegate;
    private final EditableRecipeTableModel tableModel;
    /**
     * Creates new form EditRecipeDialog
     */
    public EditRecipeDialog(java.awt.Frame parent, boolean modal, Recipe model, EditRecipeDelegate delegate) {
	super(parent, modal);
	initComponents();
	
	dp = new DatePicker(new Date(), new SimpleDateFormat("dd/MM/yyyy"));
	jPanel5.add(dp);
	
	setTitle("Recept wijzigen");
	setLocationRelativeTo(null);
	
	this.delegate = delegate;
	this.model = model;
	this.defaultModel = new Recipe(model);
	
	/*
	 * Configure the ingredient list. Add a listener that when the table's data changes, the proper fields are updated.
	 */
	this.ingredientsOutlet.setRowHeight(ingredientsOutlet.getRowHeight()+Utilities.fontSize()-10);
	this.ingredientsOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	Map<Integer, Component> m = model.getComponents();
//	if (m.remove.) {
//	}
	
	this.tableModel = new EditableRecipeTableModel(model.getComponents());
	
	this.ingredientsOutlet.setModel(tableModel);
	this.ingredientsOutlet.getModel().addTableModelListener(new TableModelListener() {

	    @Override
	    public void tableChanged(TableModelEvent e) {
		updateGrossWeightOutlet();
		updatePricePerWeightOutlet();
	    }
	});
	
	preparationOutlet.setFont(new Font(preparationOutlet.getFont().getName(), Font.PLAIN, Utilities.fontSize()));
	
	List ingredients = new ArrayList();
	ingredients.add("");
	/*
	 * Add all ingredients and recipes that are not and do not contain the model to prevent loops in recipes
	 */
	Collection<Ingredient> c = new TreeSet<Ingredient>();
	for (Ingredient ingredient : Database.driver().getIngredients()) {
	    if (ingredient.isBasicIngredient() ||
		    (!ingredient.isBasicIngredient() && !((Recipe)ingredient).containsRecipe(model))) {
		c.add(ingredient);
	    }
	}
	
	ingredients.addAll(c);
	
	@SuppressWarnings("LeakingThisInConstructor")
	TableCellEditor ce = CellEditorFactory.createComboBoxEditor(ingredients, this);
	this.ingredientsOutlet.setDefaultEditor(Ingredient.class, ce);
	this.ingredientsOutlet.setDefaultEditor(Double.class, CellEditorFactory.createDoubleEditor());
	this.ingredientsOutlet.setDefaultRenderer(Ingredient.class, CellRendererFactory.createCapitalizedStringCellRenderer());
	this.ingredientsOutlet.setDefaultRenderer(Double.class, CellRendererFactory.createThreeDecimalDoubleCellRenderer());
	this.ingredientsOutlet.setDefaultRenderer(Component.class, CellRendererFactory.createTwoDecimalDoubleCellRenderer());
	
	this.ingredientsOutlet.setDragEnabled(true);
	this.ingredientsOutlet.setDropMode(DropMode.INSERT_ROWS);
	this.ingredientsOutlet.setTransferHandler(new TableRowTransferHandler(this.ingredientsOutlet)); 
	
	loadModel();
	
	// addProxy accelerator to "addProxy" button
	AcceleratorAdder.addAccelerator(addComponent, KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), new KeyAction() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		addComponentActionPerformed(e);
	    }
	});
	AcceleratorAdder.addAccelerator(removeComponent, KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), new KeyAction() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		removeComponentActionPerformed(e);
	    }
	});
	
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
    
    @Override
    public void ingredientBoxCallback(){
	ingredientsOutlet.editingStopped(null);
    }
    
    private void disposeLater(){
	SwingUtilities.invokeLater(new Runnable() {

	    @Override
	    public void run() {
		dispose();
	    }
	});
    }
    
    private void loadModel(){
	nameOutlet.setText(model.getName());
	netWeightOutlet.setText(""+model.getNetWeight());
	preparationOutlet.setText(model.getPreparation());
	
	updateGrossWeightOutlet();
	updatePricePerWeightOutlet();
	updateNetWeightFormattedOutlet();
    }
    
    private void updateGrossWeightOutlet(){
	grossWeightOutlet.setText(new DecimalFormat("0.000").format(model.getGrossWeight())+" kg");
    }
    
    private void updatePricePerWeightOutlet(){
	pricePerWeightOutlet.setText(new DecimalFormat("0.000").format(model.getPricePerWeight())+" euro/kg");
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
    
    /** 
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        nameOutlet = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        addComponent = new javax.swing.JButton();
        removeComponent = new javax.swing.JButton();
        newBasicIngredient = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        preparationOutlet = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grossWeightOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        netWeightOutlet = new javax.swing.JTextField();
        netWeightFormattedOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pricePerWeightOutlet = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel2 = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 500));
        setPreferredSize(new java.awt.Dimension(462, 500));

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        nameOutlet.setText("<nameOutlet>");
        jPanel5.add(nameOutlet);

        jPanel10.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingrediënten:"));

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

        jPanel4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new java.awt.GridLayout(1, 3));

        addComponent.setText("Toevoegen");
        addComponent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addComponentActionPerformed(evt);
            }
        });
        jPanel9.add(addComponent);

        removeComponent.setText("Verwijderen");
        removeComponent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeComponentActionPerformed(evt);
            }
        });
        jPanel9.add(removeComponent);

        jPanel8.add(jPanel9, java.awt.BorderLayout.EAST);

        newBasicIngredient.setText("Nieuw...");
        newBasicIngredient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBasicIngredientActionPerformed(evt);
            }
        });
        jPanel8.add(newBasicIngredient, java.awt.BorderLayout.WEST);

        jPanel4.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel10.add(jPanel4, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel10);

        jPanel7.setPreferredSize(new java.awt.Dimension(176, 250));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Bereiding:"));

        preparationOutlet.setColumns(20);
        preparationOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        preparationOutlet.setRows(3);
        jScrollPane3.setViewportView(preparationOutlet);

        jPanel7.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.GridLayout(3, 2));

        jLabel2.setText("  Totaalgewicht ingrediënten");
        jPanel6.add(jLabel2);

        grossWeightOutlet.setText("<grossWeightOutlet>");
        jPanel6.add(grossWeightOutlet);

        jLabel4.setText("  Gewicht na bereiding *");
        jPanel6.add(jLabel4);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        netWeightOutlet.setText("<netWeightOutlet>");
        netWeightOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                netWeightOutletKeyReleased(evt);
            }
        });
        jPanel3.add(netWeightOutlet);

        netWeightFormattedOutlet.setText("<netWeightFormattedOutlet>");
        jPanel3.add(netWeightFormattedOutlet);

        jPanel6.add(jPanel3);

        jLabel7.setText("  Kostprijs per kg (BTW excl)");
        jPanel6.add(jLabel7);

        pricePerWeightOutlet.setText("<pricePerWeightOutlet>");
        jPanel6.add(pricePerWeightOutlet);

        jPanel7.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(filler1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        save.setText("Opslaan");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        jPanel2.add(save);

        cancel.setText("Terug");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        jPanel2.add(cancel);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel7.add(jPanel1, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel7);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // revert the model to the default (undo all changes)
	model.copy(defaultModel);
	disposeLater();
    }//GEN-LAST:event_cancelActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
	try {
	    if (nameOutlet.getText().isEmpty()
		    || netWeightOutlet.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, tools.Utilities.incompleteFormMessage, "Fout!", JOptionPane.WARNING_MESSAGE);
		return;
	    }
	    
	    model.setName(nameOutlet.getText());
	    model.setDate(new DateFormatter(dp.getDateFormat()).valueToString(dp.getDate()));
	    model.setNetWeight(Double.parseDouble(netWeightOutlet.getText()));
	    model.setPreparation(preparationOutlet.getText());
	    
	    for (Component component : model.getComponents().values()) {
		if (component.getIngredient() == null) {
		    JOptionPane.showMessageDialog(null, "Ingrediënt <Kies een item> is ongeldig!", "Fout!", JOptionPane.WARNING_MESSAGE);
		    
		    /*
		     * Select invalid row
		     */
		    
		    ingredientsOutlet.setRowSelectionInterval(component.getRank()-1, component.getRank()-1);
		    
		    /*
		     * Eventueel: delete alle null-rows?
		     */
		    
		    return;
		}
		
		if (component.getQuantity() <= 0) {
		    JOptionPane.showMessageDialog(null, "Hoeveelheid "+component.getQuantity()+" is ongeldig!", "Fout!", JOptionPane.WARNING_MESSAGE);
		    ingredientsOutlet.setRowSelectionInterval(component.getRank()-1, component.getRank()-1);
		    return;
		}
	    }
	    
	    if (!defaultModel.getName().equalsIgnoreCase(model.getName())) {
		if (Database.driver().getRecipesAlphabetically().containsKey(model.getName().toLowerCase())) {
		    JOptionPane.showMessageDialog(null, "Er is al een recept met deze naam.", "Fout!", JOptionPane.ERROR_MESSAGE);
		    return;
		}
	    }
	    
	    FunctionResult res = model.update();
	    if(res.getCode() == 0){
		delegate.editRecipe(model, defaultModel);
		disposeLater();
	    } else {
		String msg;
		switch(res.getCode()){
		    case 1: case 2:
			msg = res.getMessage();
			break;
		    default:
			msg = "Er is een fout opgetreden bij het opslaan van dit recept in de databank (code "+res.getCode()+"). Contacteer de ontwikkelaars met deze informatie.";
		}
		JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
	    }
	} catch (ParseException ex) {
	    System.err.println(ex.getMessage());
	    JOptionPane.showMessageDialog(null, Utilities.incorrectFormMessage, "Fout!", JOptionPane.ERROR_MESSAGE);
	}
    }//GEN-LAST:event_saveActionPerformed

    private void netWeightOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_netWeightOutletKeyReleased
	updateNetWeightFormattedOutlet();
	updatePricePerWeightOutlet();
    }//GEN-LAST:event_netWeightOutletKeyReleased

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

    private void addComponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addComponentActionPerformed
        tableModel.addRow();
	int lastIndex = ingredientsOutlet.getModel().getRowCount()-1;
	ingredientsOutlet.scrollRectToVisible(ingredientsOutlet.getCellRect(ingredientsOutlet.getRowCount()-1, 0, true));
	ingredientsOutlet.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
	ingredientsOutlet.editCellAt(lastIndex, 0);
	ingredientsOutlet.transferFocus();
    }//GEN-LAST:event_addComponentActionPerformed

    private void ingredientsOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingredientsOutletKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
	    int r = ingredientsOutlet.getSelectedRow();
	    int c = ingredientsOutlet.getSelectedColumn();
	    ingredientsOutlet.editCellAt(r, c);
	    ingredientsOutlet.transferFocus();
	}
    }//GEN-LAST:event_ingredientsOutletKeyReleased

    private void newBasicIngredientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBasicIngredientActionPerformed
        new AddBasicIngredientDialog(null, true, this).setVisible(true);
    }//GEN-LAST:event_newBasicIngredientActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addComponent;
    private javax.swing.JButton cancel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel grossWeightOutlet;
    private final javax.swing.JTable ingredientsOutlet = new javax.swing.JTable();
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField nameOutlet;
    private javax.swing.JLabel netWeightFormattedOutlet;
    private javax.swing.JTextField netWeightOutlet;
    private javax.swing.JButton newBasicIngredient;
    private javax.swing.JTextArea preparationOutlet;
    private javax.swing.JLabel pricePerWeightOutlet;
    private javax.swing.JButton removeComponent;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addBasicIngredient(BasicIngredient bi) {
	/*
	 * Add to table model, add new component and set ingredient to be "bi"
	 */
	List ingredients = new ArrayList();
	ingredients.add("");
	/*
	 * Add all ingredients and recipes that are not and do not contain the model to prevent loops in recipes
	 */
	Collection<Ingredient> c = new TreeSet<Ingredient>();
	for (Ingredient ingredient : Database.driver().getIngredients()) {
	    if (ingredient.isBasicIngredient() ||
		    (!ingredient.isBasicIngredient() && !((Recipe)ingredient).containsRecipe(model))) {
		c.add(ingredient);
	    }
	}
	
	ingredients.addAll(c);
	TableCellEditor ce = CellEditorFactory.createComboBoxEditor(ingredients, this);
	this.ingredientsOutlet.setDefaultEditor(Ingredient.class, ce);
	
	tableModel.addRow();
	int lastIndex = ingredientsOutlet.getModel().getRowCount()-1;
	ingredientsOutlet.scrollRectToVisible(ingredientsOutlet.getCellRect(ingredientsOutlet.getRowCount()-1, 0, true));
	ingredientsOutlet.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
	
	tableModel.setValueAt(bi, ingredientsOutlet.getSelectedRow(), 0);
    }
}
