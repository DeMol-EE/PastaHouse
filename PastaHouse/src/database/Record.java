/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Robin jr
 */
public abstract class Record {
    private final int id;
    
    public Record(int id){
	this.id = id;
    }
    
    public int getId() {
	return id;
    }
    
    // save changes to the db
    public abstract void create() throws Exception;
    
    // save changes to the db
    public abstract void update() throws Exception;
    
    // save changes to the db
    public abstract void delete() throws Exception;
}
