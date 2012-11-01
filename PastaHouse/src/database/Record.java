/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Robin jr
 */
public interface Record {
    
    // save changes to the db
    public void create() throws Exception;
    
    // save changes to the db
    public void update() throws Exception;
    
    // save changes to the db
    public void delete() throws Exception;
}
