/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.invoices.delegates;

import database.tables.Invoice;

/**
 *
 * @author Warkst
 */
public interface EditInvoiceDelegate {
    public void editInvoice(Invoice oldInvoice, Invoice newInvoice);
}
