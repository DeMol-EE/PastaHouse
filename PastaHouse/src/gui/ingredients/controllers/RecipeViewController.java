/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.controllers;

import database.Database;
import database.extra.Component;
import database.extra.Ingredient;
import database.tables.Recipe;
import gui.EmptyPanelManager;
import gui.MasterDetailViewController;
import gui.NoResultsPanel;
import gui.ingredients.delegates.RecipeDelegate;
import gui.ingredients.dialogs.RecipeDialog;
import gui.ingredients.dialogs.RecipePrintDialog;
import gui.utilities.TextFieldAutoHighlighter;
import gui.utilities.cell.CellRendererFactory;
import gui.utilities.list.FilterableListModel;
import gui.utilities.table.StaticRecipeTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.JXTitledPanel;
import printer.printables.PrintableRecipe;
import tools.StringTools;
import tools.Utilities;

/**
 *
 * @author Warkst
 */
public class RecipeViewController extends javax.swing.JPanel implements MasterDetailViewController<Recipe>, RecipeDelegate{
    
    private final JXTitledPanel d = new JXTitledPanel("Receptuur");
    private final FilterableListModel<Recipe> listModel;
    private final JXTextField xfilter = new JXTextField("Zoeken");
    
    /**
     * Creates new form RecipeViewController
     */
    public RecipeViewController() {
	initComponents();
	preparationOutlet.setBackground(new Color(213,216,222));
        preparationOutlet.setCaretPosition(0);
	listModel = new FilterableListModel<Recipe>(Database.driver().getRecipesAlphabetically());
	recipeListOutlet.setModel(listModel);
    //	recipeListOutlet.setModel(ListModelFactory.createRecipeListModel(Database.driver().getRecipesAlphabetically()));
	recipeListOutlet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	recipeListOutlet.addListSelectionListener(new ListSelectionListener() {

       
	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
		    updateDetail((Recipe)recipeListOutlet.getSelectedValue());
		}
	    }
	});
	
	recipeListOutlet.setSelectedIndex(0);
	
	preparationOutlet.setFont(new Font(preparationOutlet.getFont().getName(), Font.PLAIN, Utilities.fontSize()));
	
	ingredientsOutlet.setRowHeight(ingredientsOutlet.getRowHeight()+Utilities.fontSize()-10);
	
	/*
	 * If there are no ingredients, hide the ugly right detail view
	 */
	if (Database.driver().getRecipesAlphabetically().isEmpty()) {
	    detail.remove(container);
	    detail.add(EmptyPanelManager.instance(), BorderLayout.CENTER);
	}
	final JButton ber = new JButton("Bereiding");
	final JButton det = new JButton("Receptuur");
	ber.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		d.setContentContainer(preparationContainer);
		d.setRightDecoration(det);
		d.setTitle("Bereiding");
		d.validate();
		d.repaint();
	    }
	});
	det.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		d.setContentContainer(componentContainer);
		d.setRightDecoration(ber);
		d.setTitle("Receptuur");
		d.validate();
		d.repaint();
	    }
	});
	
	/*
	 * Default content = component table
	 */
	
	d.setRightDecoration(ber);
	d.setContentContainer(componentContainer);
	titledContainer.add(d, BorderLayout.CENTER);
	
	TextFieldAutoHighlighter.installHighlighter(xfilter);
	
	master.add(xfilter, BorderLayout.NORTH);
	
	xfilter.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
	    public void keyReleased(java.awt.event.KeyEvent evt) {
                filterKeyReleased(evt);
            }
        });
    }
    
    @Override
    public JPanel view(){
	return this;
    }
    
    @Override
    public void updateDetail(Recipe r){
	
	try{
	    d.setTitle("Receptuur - "+r.getName()+" - Laatste update: "+r.getDate());
	} catch(Exception e){
	    d.setTitle("Receptuur");
	}
	
	
	nameOutlet.setText(StringTools.capitalize(r.getName()));
//	dateOutlet.setText("Laatste update: "+r.getDate());
//	dateOutlet.setText(r.getDate());
	
	DecimalFormat threeFormatter = new DecimalFormat("0.000");
	grossWeightOutlet.setText(threeFormatter.format(r.getGrossWeight())+" kg");
	netWeightOutlet.setText(new DecimalFormat("0.00").format(r.getNetWeight())+" kg");
	pricePerWeightOutlet.setText(threeFormatter.format(r.getPricePerWeight())+" euro / kg");
	
	preparationOutlet.setText(r.getPreparation());
	
	ingredientsOutlet.setDefaultRenderer(String.class, CellRendererFactory.createCapitalizedStringCellRenderer());
//	ingredientsOutlet.setDefaultRenderer(Double.class, CellRendererFactory.createTwoDecimalDoubleCellRenderer());
	ingredientsOutlet.setDefaultRenderer(Double.class, CellRendererFactory.createCapitalizedStringCellRenderer(true));
	ingredientsOutlet.setDefaultRenderer(Ingredient.class, CellRendererFactory.createIngredientCellRenderer());
	ingredientsOutlet.setDefaultRenderer(Component.class, CellRendererFactory.createThreeDecimalDoubleCellRenderer());
	
	ingredientsOutlet.setModel(new StaticRecipeTableModel(r.getComponents()));
	
//	ingredientListOutlet.setModel(ListModelFactory.createComponentListModel(r.getComponents()));
        preparationOutlet.setCaretPosition(0);
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
        printMenuItem = new javax.swing.JMenuItem();
        searchMenuItem = new javax.swing.JMenuItem();
        componentContainer = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ingredientsOutlet = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        grossWeightOutlet = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        netWeightOutlet = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pricePerWeightOutlet = new javax.swing.JLabel();
        preparationContainer = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        preparationOutlet = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        nameOutlet = new javax.swing.JLabel();
        dateOutlet = new javax.swing.JLabel();
        filter = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        master = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recipeListOutlet = new javax.swing.JList();
        add = new javax.swing.JButton();
        detail = new javax.swing.JPanel();
        container = new javax.swing.JPanel();
        titledContainer = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel2 = new javax.swing.JPanel();
        print = new javax.swing.JButton();
        edit = new javax.swing.JButton();

        editMenu.setText("Acties");

        addMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        addMenuItem.setText("Toevoegen...");
        addMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(addMenuItem);

        editMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        editMenuItem.setText("Wijzigen...");
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(editMenuItem);

        printMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printMenuItem.setText("Afdrukken");
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(printMenuItem);

        searchMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        searchMenuItem.setText("Zoeken");
        searchMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(searchMenuItem);

        componentContainer.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(null);
        jScrollPane4.setFocusable(false);
        jScrollPane4.setMinimumSize(new java.awt.Dimension(33, 200));

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
        ingredientsOutlet.setFocusable(false);
        ingredientsOutlet.setRowSelectionAllowed(false);
        ingredientsOutlet.setSurrendersFocusOnKeystroke(true);
        jScrollPane4.setViewportView(ingredientsOutlet);

        jPanel3.add(jScrollPane4, java.awt.BorderLayout.CENTER);
        jPanel3.add(jSeparator1, java.awt.BorderLayout.SOUTH);

        componentContainer.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel6.setFocusable(false);
        jPanel6.setLayout(new java.awt.GridLayout(3, 2));

        jLabel2.setText("Totaalgewicht ingrediënten");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel2.setFocusable(false);
        jPanel6.add(jLabel2);

        grossWeightOutlet.setText("<grossWeightOutlet>");
        grossWeightOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        grossWeightOutlet.setFocusable(false);
        jPanel6.add(grossWeightOutlet);

        jLabel4.setBackground(new java.awt.Color(239, 239, 239));
        jLabel4.setText("Gewicht na bereiding");
        jLabel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel4.setFocusable(false);
        jLabel4.setOpaque(true);
        jPanel6.add(jLabel4);

        netWeightOutlet.setBackground(new java.awt.Color(239, 239, 239));
        netWeightOutlet.setText("<netWeightOutlet>");
        netWeightOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        netWeightOutlet.setFocusable(false);
        netWeightOutlet.setOpaque(true);
        jPanel6.add(netWeightOutlet);

        jLabel7.setText("Kostprijs per kg (BTW excl)");
        jLabel7.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 5, 3, 0));
        jLabel7.setFocusable(false);
        jPanel6.add(jLabel7);

        pricePerWeightOutlet.setText("<pricePerWeightOutlet>");
        pricePerWeightOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 0, 3, 0));
        pricePerWeightOutlet.setFocusable(false);
        jPanel6.add(pricePerWeightOutlet);

        componentContainer.add(jPanel6, java.awt.BorderLayout.SOUTH);

        preparationContainer.setFocusable(false);
        preparationContainer.setPreferredSize(new java.awt.Dimension(176, 250));
        preparationContainer.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(null);
        jScrollPane3.setFocusable(false);

        preparationOutlet.setBackground(new java.awt.Color(240, 240, 240));
        preparationOutlet.setColumns(20);
        preparationOutlet.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        preparationOutlet.setLineWrap(true);
        preparationOutlet.setRows(1);
        preparationOutlet.setWrapStyleWord(true);
        preparationOutlet.setFocusable(false);
        preparationOutlet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                preparationOutletKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                preparationOutletKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                preparationOutletKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(preparationOutlet);

        preparationContainer.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel5.setFocusable(false);
        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        nameOutlet.setText("<nameOutlet>");
        nameOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 0));
        nameOutlet.setFocusable(false);
        jPanel5.add(nameOutlet);

        dateOutlet.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        dateOutlet.setText("<dateOutlet>");
        dateOutlet.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        dateOutlet.setFocusable(false);

        filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterKeyReleased(evt);
            }
        });

        setFocusable(false);
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setFocusable(false);

        master.setFocusable(false);
        master.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setFocusable(false);

        recipeListOutlet.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(recipeListOutlet);

        master.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add.setText("Toevoegen...");
        add.setFocusable(false);
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        master.add(add, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setLeftComponent(master);

        detail.setFocusable(false);
        detail.setLayout(new java.awt.BorderLayout());

        container.setLayout(new java.awt.BorderLayout());

        titledContainer.setLayout(new java.awt.BorderLayout());
        container.add(titledContainer, java.awt.BorderLayout.CENTER);

        jPanel1.setFocusable(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        filler1.setFocusable(false);
        jPanel1.add(filler1, java.awt.BorderLayout.CENTER);

        jPanel2.setFocusable(false);
        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        print.setText("Afdrukken...");
        print.setFocusable(false);
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel2.add(print);

        edit.setText("Wijzigen...");
        edit.setFocusable(false);
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        jPanel2.add(edit);

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        container.add(jPanel1, java.awt.BorderLayout.SOUTH);

        detail.add(container, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(detail);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
//	new AddRecipeDialog(null, true, this).setVisible(true);
	listModel.setFilter(null);
//	filter.setText("");
	xfilter.setText("");
	
	RecipeDialog.showAddRecipeDialog(this);
	
//	new RecipeDialog(null, true).setVisible(true);
    }//GEN-LAST:event_addActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
//        new EditRecipeDialog(null, true, (Recipe)recipeListOutlet.getSelectedValue(), this).setVisible(true);
	listModel.setFilter(null);
//	filter.setText("");
	xfilter.setText("");
	
	RecipeDialog.showEditRecipeDialog(this, (Recipe)recipeListOutlet.getSelectedValue());
    }//GEN-LAST:event_editActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
//	new RecipePrintDialog(null, true, this, new PrintableRecipe((Recipe)recipeListOutlet.getSelectedValue())).setVisible(true);
	RecipePrintDialog.getInstance().showDialog(new PrintableRecipe((Recipe)recipeListOutlet.getSelectedValue()));
    }//GEN-LAST:event_printActionPerformed

    private void preparationOutletKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preparationOutletKeyPressed
        notesKeyEvent(evt);
    }//GEN-LAST:event_preparationOutletKeyPressed

    private void preparationOutletKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preparationOutletKeyReleased
        notesKeyEvent(evt);
    }//GEN-LAST:event_preparationOutletKeyReleased

    private void preparationOutletKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_preparationOutletKeyTyped
        notesKeyEvent(evt);
    }//GEN-LAST:event_preparationOutletKeyTyped

    private void addMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMenuItemActionPerformed
        addActionPerformed(null);
    }//GEN-LAST:event_addMenuItemActionPerformed

    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editActionPerformed(null);
    }//GEN-LAST:event_editMenuItemActionPerformed

    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
        printActionPerformed(null);
    }//GEN-LAST:event_printMenuItemActionPerformed

    private void filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterKeyReleased
//        if (filter.getText().isEmpty()) {
        if (xfilter.getText().isEmpty()) {
	    listModel.setFilter(null);
	} else {
//	    listModel.setFilter(filter.getText());
	    listModel.setFilter(xfilter.getText());
	}
	if (listModel.getSize()==0) {
	    System.out.println("No results");
	    detail.removeAll();
	    detail.add(new NoResultsPanel(), BorderLayout.CENTER);
	    validate();
	    repaint();
	    return;
	}
	detail.removeAll();
	detail.add(container);
	recipeListOutlet.setSelectedIndex(0);
	if (recipeListOutlet.getSelectedValue()!=null) {
//	    updateDetail((Recipe)recipeListOutlet.getSelectedValue());
	    updateDetail(listModel.getElementAt(recipeListOutlet.getSelectedIndex()));
	}
	validate();
	repaint();
    }//GEN-LAST:event_filterKeyReleased

    private void searchMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMenuItemActionPerformed
//        filter.requestFocus();
        xfilter.requestFocus();
    }//GEN-LAST:event_searchMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JMenuItem addMenuItem;
    private javax.swing.JPanel componentContainer;
    private javax.swing.JPanel container;
    private javax.swing.JLabel dateOutlet;
    private javax.swing.JPanel detail;
    private javax.swing.JButton edit;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField filter;
    private javax.swing.JLabel grossWeightOutlet;
    private javax.swing.JTable ingredientsOutlet;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel master;
    private javax.swing.JLabel nameOutlet;
    private javax.swing.JLabel netWeightOutlet;
    private javax.swing.JPanel preparationContainer;
    private javax.swing.JTextArea preparationOutlet;
    private javax.swing.JLabel pricePerWeightOutlet;
    private javax.swing.JButton print;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JList recipeListOutlet;
    private javax.swing.JMenuItem searchMenuItem;
    private javax.swing.JPanel titledContainer;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addRecipe(Recipe select) {
//	EditableListModel<Recipe> dlm = (EditableListModel)recipeListOutlet.getModel();
//	dlm.update();
	listModel.update();
	if (listModel.getSize() == 1) {
	    detail.removeAll();
	    detail.add(container);
	}
	recipeListOutlet.setSelectedValue(select, true);
	updateDetail(select);
    }
    
    @Override
    public void editRecipe(Recipe n, Recipe o){
//	EditableListModel<Recipe> dlm = (EditableListModel)recipeListOutlet.getModel();
//	dlm.edit(n, o);
	listModel.update();
	listModel.edit(n, o);
	recipeListOutlet.setSelectedValue(n, true);
	updateDetail(n);
    }
    
    private void updateDetail(){
	updateDetail(listModel.getElementAt(recipeListOutlet.getSelectedIndex()));
    }

    @Override
    public void electFirstResponder() {
	/*
	 * Update the list model to reflect any changes in the underlying data structure (contacts list)
	 */
	listModel.update();
	
	if (listModel.getSize()>0) {
	    if (listModel.getSize() == 1) {
		detail.removeAll();
		detail.add(container);
	    }
	    recipeListOutlet.setSelectedIndex(0);
	    if(recipeListOutlet.getSelectedValue()!=null)updateDetail();
	} else {
	    System.out.println("No results");
	    detail.removeAll();
	    detail.add(new NoResultsPanel(), BorderLayout.CENTER);
	    validate();
	    repaint();
	    return;
	}
	
	/*
	 * Elect first responder
	 */
	recipeListOutlet.requestFocus();
	if(recipeListOutlet.getSelectedValue()!=null) updateDetail((Recipe)recipeListOutlet.getSelectedValue());
    }
    
    private void notesKeyEvent(KeyEvent evt){
	if (!(evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_C)
                && !(evt.getKeyCode() == KeyEvent.VK_F1)
                && !(evt.getKeyCode() == KeyEvent.VK_F2)
                && !(evt.getKeyCode() == KeyEvent.VK_F3)) {
            evt.consume();
        }
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            evt.consume();
            recipeListOutlet.requestFocus();
        }
    }

    @Override
    public JMenu menu() {
	return editMenu;
    }
}
