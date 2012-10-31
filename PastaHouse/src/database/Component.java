/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Warkst
 */
public abstract class Component implements Comparable<Component> {
    private final int id;
    private String name;
    
    public Component(int id, String name){
	this.id = id;
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
    }

    @Override
    public int compareTo(Component o) {
	return getName().compareTo(o.getName());
    }
    
    // save changes to the db
    public abstract void create() throws Exception;
    
    // save changes to the db
    public abstract void update() throws Exception;
    
    // save changes to the db
    public abstract void delete() throws Exception;
}
