/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.delegates;

import database.tables.Recipe;

/**
 *
 * @author Warkst
 */
public interface EditRecipeDelegate {
    public void editRecipe(Recipe oldObj, Recipe newObj);
}
