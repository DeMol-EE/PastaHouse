/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Warkst
 */
public abstract class Component extends Record implements Comparable<Component> {
    private String name;
    private String date;
    
    public Component(int id, String name, String date){
	super(id);
	this.name = name;
	this.date = date;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int compareTo(Component o) {
	return getName().compareTo(o.getName());
    }
}
