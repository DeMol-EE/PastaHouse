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
    
    /**
     * Returns the primary key of this database proxy-object.
     * @return The primary key of the object.
     */
    public String getPrimaryKey();
    
    public int getPrimaryKeyValue();
    
    // save changes to the db
    public boolean create();
    
    // save changes to the db
    public boolean update();
    
    // save changes to the db
    public boolean delete();
}
