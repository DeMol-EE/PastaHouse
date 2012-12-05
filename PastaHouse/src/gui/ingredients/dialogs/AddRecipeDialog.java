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
import database.tables.Recipe;
import gui.ingredients.controllers.ComboCoxCallback;
import gui.MasterDetailViewController;
import gui.utilities.AcceleratorAdder;
import gui.utilities.KeyAction;
import gui.utilities.cell.CellEditorFactory;
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.table.EditableRecipeTableModel;
import gui.utilities.table.TableRowTransferHandler;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import utilities.Utilities;

/**
 *
 * @author Warkst
 */
public class AddRecipeDialog extends javax.swing.JDialog implements ComboCoxCallback{

    private final MasterDetailViewController delegate;
    private final RecipeModel model;
    private final Map<Integer, Component> components;
    
    /**
     * Creates new form AddRecipeDialog
     */
    public AddRecipeDialog(java.awt.Frame parent, boolean modal, MasterDetailViewController delegate) {
	super(parent, modal);
	initComponents();
	
	this.delegate = delegate;
	this.model = new RecipeModel();
	
	setTitle("Recept toevoegen");
	setLocationRelativeTo(null);
	
	this.components = new TreeMap<Integer, Component>();
	this.ingredientsOutlet.setRowHeight(ingredientsOutlet.getRowHeight()+Utilities.fontSize()-10);
	this.ingredientsOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	this.ingredientsOutlet.setModel(new EditableRecipeTableModel(components));
	this.ingredientsOutlet.getModel().addTableModelListener(new TableModelListener() {

	    @Override
	    public void tableChanged(TableModelEvent e) {
		updateGrossWeightOutlet();
		updatePricePerWeightOutlet();
	    }
	});
	List ingredients = new ArrayList();
	ingredients.add("");
	ingredients.addAll(Database.driver().getIngredients());
	this.model.setIngredients(components);
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
	
	
	nameOutlet.setText("");
	nameLabel.setForeground(Color.red);
	netWeightOutlet.setText("0");
	updateGrossWeightOutlet();
	updatePricePerWeightOutlet();
	updateNetWeightFormattedOutlet();
	
	nameOutlet.requestFocus();
	
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
	updateGrossWeightOutlet();
    }
    
    private void updateGrossWeightOutlet(){
	grossWeightOutlet.setText(new DecimalFormat("0.000").format(model.getGrossWeight())+" kg");
    }
    
    private void updatePricePerWeightOutlet(){
	try{
	    double d = Double.parseDouble(netWeightOutlet.getText());
	    pricePerWeightOutlet.setText(new DecimalFormat("0.000").format(model.getPricePerWeight())+" euro/kg");
	    pricePerWeightOutlet.setForeground(Color.black);
	} catch (Exception e){
	    pricePerWeightOutlet.setText("???");
	    pricePerWeightOutlet.setForeground(Color.red);
	}
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameOutlet = new javax.swing.JTextField();
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
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        addComponent = new javax.swing.JButton();
        removeComponent = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 256));
        setPreferredSize(new java.awt.Dimension(600, 500));

        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        jPanel10.setLayout(new java.awt.GridLayout(1, 2));

        nameLabel.setText("Naam *");
        nameLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel10.add(nameLabel);

        nameOutlet.setText("<nameOutlet>");
        nameOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameOutletKeyReleased(evt);
            }
        });
        jPanel10.add(nameOutlet);

        jPanel5.add(jPanel10);

        getContentPane().add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel7.setPreferredSize(new java.awt.Dimension(176, 250));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Bereiding:"));

        preparationOutlet.setColumns(20);
        preparationOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        preparationOutlet.setRows(5);
        jScrollPane3.setViewportView(preparationOutlet);

        jPanel7.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.GridLayout(3, 2));

        jLabel2.setText("Totaalgewicht ingrediënten");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel6.add(jLabel2);

        grossWeightOutlet.setText("<grossWeightOutlet>");
        jPanel6.add(grossWeightOutlet);

        jLabel4.setText("Gewicht na bereiding *");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel6.add(jLabel4);

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

        jPanel6.add(jPanel3);

        jLabel7.setText("Kostprijs per kg (BTW excl)");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel6.add(jLabel7);

        pricePerWeightOutlet.setText("<pricePerWeightOutlet>");
        jPanel6.add(pricePerWeightOutlet);

        jPanel7.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(filler1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        save.setText("Opslaan");
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

        jPanel7.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel7, java.awt.BorderLayout.SOUTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingrediënten:"));
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

        jPanel4.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

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

        jPanel8.add(jPanel9, java.awt.BorderLayout.EAST);

        jPanel4.add(jPanel8, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void netWeightOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_netWeightOutletKeyReleased
        updateNetWeightFormattedOutlet();
	updatePricePerWeightOutlet();
    }//GEN-LAST:event_netWeightOutletKeyReleased

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
	    if (nameOutlet.getText().isEmpty()
		    || netWeightOutlet.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, utilities.Utilities.incompleteFormMessage, "Fout!", JOptionPane.WARNING_MESSAGE);
		return;
	    }
            model.setName(nameOutlet.getText());
	    model.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            model.setNetWeight(Double.parseDouble(netWeightOutlet.getText()));
            model.setPreparation(preparationOutlet.getText());
	    model.setIngredients(components);

            FunctionResult<Recipe> res = model.create();
	    if (res.getCode() == 0 && res.getObj() != null) {
		delegate.addAndSelect(res.getObj());
                disposeLater();
            } else {
                JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het aanmaken van dit recept in de databank.", "Fout!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, Utilities.incorrectFormMessage, "Fout!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
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
    
    private void addComponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addComponentActionPerformed
        ((EditableRecipeTableModel)ingredientsOutlet.getModel()).addRow();
        int lastIndex = ingredientsOutlet.getModel().getRowCount()-1;
	ingredientsOutlet.scrollRectToVisible(ingredientsOutlet.getCellRect(ingredientsOutlet.getRowCount()-1, 0, true));
	ingredientsOutlet.getSelectionModel().setSelectionInterval(lastIndex, lastIndex);
	ingredientsOutlet.editCellAt(lastIndex, 0);
	ingredientsOutlet.transferFocus();
    }//GEN-LAST:event_addComponentActionPerformed

    private void removeComponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeComponentActionPerformed
        int selected = ingredientsOutlet.getSelectedRow();
        if(ingredientsOutlet.getSelectedRow()>-1 && ingredientsOutlet.getSelectedRow() < ingredientsOutlet.getModel().getRowCount()){
            ((EditableRecipeTableModel)ingredientsOutlet.getModel()).removeRow(ingredientsOutlet.getSelectedRow());
            int row = Math.max(0, Math.min(selected, ingredientsOutlet.getModel().getRowCount()-1));
            if (ingredientsOutlet.getModel().getRowCount() > 0) {
                ingredientsOutlet.getSelectionModel().setSelectionInterval(row, row);
            }
        }
    }//GEN-LAST:event_removeComponentActionPerformed

    private void ingredientsOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ingredientsOutletKeyReleased
	if (evt.getKeyCode() == KeyEvent.VK_TAB) {
	    int r = ingredientsOutlet.getSelectedRow();
	    int c = ingredientsOutlet.getSelectedColumn();
	    ingredientsOutlet.editCellAt(r, c);
	    ingredientsOutlet.transferFocus();
	}
    }//GEN-LAST:event_ingredientsOutletKeyReleased

    private void nameOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameOutletKeyReleased
        if (nameOutlet.getText().isEmpty()) {
	    nameLabel.setForeground(Color.red);
	} else {
	    nameLabel.setForeground(Color.black);
	}
    }//GEN-LAST:event_nameOutletKeyReleased

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
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameOutlet;
    private javax.swing.JLabel netWeightFormattedOutlet;
    private javax.swing.JTextField netWeightOutlet;
    private javax.swing.JTextArea preparationOutlet;
    private javax.swing.JLabel pricePerWeightOutlet;
    private javax.swing.JButton removeComponent;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
