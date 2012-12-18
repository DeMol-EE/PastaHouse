/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Warkst
 */
public class DatabaseMap<Key extends Comparable, Value>  {
    
    private final Map<Key, Value> data;
    private List<DatabaseMapObserver> observers;
    
    public DatabaseMap(Map<Key, Value> data){
	this.data = data;
	this.observers = new ArrayList<DatabaseMapObserver>();
    }
    
    public void addObserver(DatabaseMapObserver obs){
	this.observers.add(obs);
    }
    
    /*
     * Notify all observers the data has changed
     */
    private void update(){
	for (DatabaseMapObserver obs : observers) {
	    obs.dataChanged();
	}
    }
    
    public void add(Key key, Value value){
	this.data.put(key, value);
	update();
    }
    
    public void reinsert(Key oldKey, Key newKey, Value oldValue, Value newValue){
	if (data.containsKey(oldKey)) {
	    data.remove(oldKey);
	    data.put(newKey, newValue);
	}
	update();
    }
    
    public void remove(Key key){
	data.remove(key);
	update();
    }
}
