/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.delegates;

import database.tables.BasicIngredient;

/**
 *
 * @author Warkst
 */
public interface EditBasicIngredientDelegate {
    public void editBasicIngredient(BasicIngredient oldObj, BasicIngredient newObj);
}
