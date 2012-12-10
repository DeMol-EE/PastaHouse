/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.delegates;

import database.tables.Supplier;

/**
 *
 * @author Warkst
 */
public interface EditSupplierDelegate {
    public void editSupplier(Supplier oldObj, Supplier newObj);
}
