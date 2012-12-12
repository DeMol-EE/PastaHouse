/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.contacts.delegates;

import database.tables.Contact;

/**
 *
 * @author Warkst
 */
public interface EditContactDelegate {
    public void editContact(Contact o, Contact n);
}
