/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pastahouse;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hannes
 */
public class updatedb {
    public static void main(String[] args) {
        try {
            database.Database.driver();
            database.Database.driver().updateDB();
        } catch (SQLException ex) {
            Logger.getLogger(updatedb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
