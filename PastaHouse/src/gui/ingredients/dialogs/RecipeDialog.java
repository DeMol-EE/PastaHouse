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
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.combobox.AutocompleteCombobox;
import gui.utilities.table.EditableRecipeTableModel;
import gui.utilities.table.TableRowTransferHandler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTitledPanel;
import tools.Utilities;

/**
 *
 * @author Warkst
 */
public class RecipeDialog extends javax.swing.JDialog {
    private final RecipeModel model;
    private final Map<Integer, Component> components;
    private final EditableRecipeTableModel tableModel;
    
    private AutocompleteCombobox componentSelectionBox;
    
    /**
     * Creates new form RecipeDialog
     */
    public RecipeDialog(java.awt.Frame parent, boolean modal) {
	super(parent, modal);
	initComponents();
	
	this.model = new RecipeModel();
	this.components = new TreeMap<Integer, Component>();
	this.tableModel = new EditableRecipeTableModel(components);
	
	JXTable iTable = new JXTable();
	iTable.setRowHeight(ingredientsOutlet.getRowHeight()+Utilities.fontSize()-10);
	iTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	iTable.setModel(tableModel);
	iTable.getModel().addTableModelListener(new TableModelListener() {

	    @Override
	    public void tableChanged(TableModelEvent e) {
		updateGrossWeightOutlet();
		updatePricePerWeightOutlet();
	    }
	});
	
	iTable.setDefaultRenderer(Ingredient.class, CellRendererFactory.createCapitalizedStringCellRenderer());
	iTable.setDefaultRenderer(Double.class, CellRendererFactory.createThreeDecimalDoubleCellRenderer());
	iTable.setDefaultRenderer(Component.class, CellRendererFactory.createTwoDecimalDoubleCellRenderer());
	iTable.setDefaultRenderer(String.class, CellRendererFactory.createCapitalizedStringCellRenderer(true));
	
	iTable.setDragEnabled(true);
	iTable.setDropMode(DropMode.INSERT_ROWS);
	iTable.setTransferHandler(new TableRowTransferHandler(this.ingredientsOutlet)); 
	
	iTable.setRowHeight(ingredientsOutlet.getRowHeight()+Utilities.fontSize()-10);
	iTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	iTable.setShowGrid(true, true);
	iTable.setFillsViewportHeight(true);
	
//	jPanel4.remove(jScrollPane4);
//	jPanel4.add(iTable, BorderLayout.CENTER);
//	jScrollPane4.remove(ingredientsOutlet);
//	jScrollPane4.add(iTable);
//	jScrollPane4.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
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
	
	jPanel5.add(new JXTitledPanel("Ingrediënten", jPanel4), BorderLayout.CENTER);
	jPanel5.add(new JXTitledPanel("Totaal", jPanel6), BorderLayout.SOUTH);
	
	jPanel7.add(new JXTitledPanel("Detail", jPanel11), BorderLayout.NORTH);
	jPanel7.add(new JXTitledPanel("Bereiding", jScrollPane3), BorderLayout.CENTER);
	
	List ingredients = new ArrayList();
	ingredients.add("");
	ingredients.addAll(Database.driver().getIngredients());
	this.componentSelectionBox = new AutocompleteCombobox(ingredients);
	this.componentSelectionBox.setOpaque(true);
	jPanel14.add(componentSelectionBox, BorderLayout.CENTER);
	
	setLocationRelativeTo(null);
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

        jPanel4 = new javax.swing.JPanel();
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
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grossWeightOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        netWeightOutlet = new javax.swing.JTextField();
        netWeightFormattedOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pricePerWeightOutlet = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        preparationOutlet = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
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

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 0, 0, 0));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.GridLayout());

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

        jPanel4.add(jPanel8, java.awt.BorderLayout.SOUTH);

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

        jPanel4.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel6.setMinimumSize(new java.awt.Dimension(50, 84));
        jPanel6.setLayout(new java.awt.GridLayout(3, 2));

        jLabel2.setText("Totaalgewicht ingrediënten");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel6.add(jLabel2);

        grossWeightOutlet.setText("<grossWeightOutlet>");
        jPanel6.add(grossWeightOutlet);

        jLabel4.setText("Gewicht na bereiding *");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel6.add(jLabel4);

        jPanel3.setLayout(new java.awt.GridLayout());

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

        jScrollPane3.setBorder(null);
        jScrollPane3.setMinimumSize(new java.awt.Dimension(400, 20));

        preparationOutlet.setColumns(50);
        preparationOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        preparationOutlet.setLineWrap(true);
        preparationOutlet.setRows(5);
        preparationOutlet.setWrapStyleWord(true);
        jScrollPane3.setViewportView(preparationOutlet);

        jPanel11.setLayout(new java.awt.GridLayout(2, 2));

        nameLabel.setText("Naam *");
        nameLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel11.add(nameLabel);

        nameOutlet.setText("<nameOutlet>");
        nameOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameOutletKeyReleased(evt);
            }
        });
        jPanel11.add(nameOutlet);

        jLabel1.setText("Datum");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        jPanel11.add(jLabel1);

        dateParent.setLayout(new java.awt.BorderLayout());
        jPanel11.add(dateParent);

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

        jPanel2.setLayout(new java.awt.GridLayout());

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
		throw new Exception("Ingredient not found");
	    }
	    tableModel.addComponent(i, Double.parseDouble(this.quantityOutlet.getText()));
	} catch(Exception e){
	    JOptionPane.showMessageDialog(null, "Selecteer een geldig ingrediënt om toe te voegen!", "Error", JOptionPane.ERROR_MESSAGE);
	    componentSelectionBox.transferFocus();
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
//        new AddBasicIngredientDialog(null, true, this).setVisible(true);
    }//GEN-LAST:event_newBasicIngredientActionPerformed

    private void netWeightOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_netWeightOutletKeyReleased
        updateNetWeightFormattedOutlet();
        updatePricePerWeightOutlet();
    }//GEN-LAST:event_netWeightOutletKeyReleased

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        try {
            if (nameOutlet.getText().isEmpty()
                || netWeightOutlet.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, tools.Utilities.incompleteFormMessage, "Fout!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (Component component : components.values()) {
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

            model.setName(nameOutlet.getText());
            model.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            model.setNetWeight(Double.parseDouble(netWeightOutlet.getText()));
            model.setPreparation(preparationOutlet.getText());
            model.setComponents(components);

            FunctionResult<Recipe> res = model.create();
            if (res.getCode() == 0 && res.getObj() != null) {
//                delegate.addRecipe(res.getObj());
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
                    default: msg = "Het toevoegen van het basisingrediënt is foutgelopen (code "+res.getCode()+"). Contacteer de ontwikkelaars met deze informatie.";
                }

                JOptionPane.showMessageDialog(null, msg, "Fout!", JOptionPane.ERROR_MESSAGE);
                //		disposeLater();
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
    private javax.swing.JPanel dateParent;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel grossWeightOutlet;
    private final javax.swing.JTable ingredientsOutlet = new javax.swing.JTable();
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameOutlet;
    private javax.swing.JLabel netWeightFormattedOutlet;
    private javax.swing.JTextField netWeightOutlet;
    private javax.swing.JButton newBasicIngredient;
    private javax.swing.JTextArea preparationOutlet;
    private javax.swing.JLabel pricePerWeightOutlet;
    private javax.swing.JTextField quantityOutlet;
    private javax.swing.JButton removeComponent;
    private javax.swing.JButton save;
    // End of variables declaration//GEN-END:variables
}
