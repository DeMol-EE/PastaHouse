/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.extra;

import database.FunctionResult;

/**
 *
 * @author Robin jr
 */
public abstract class Record<Type extends Record> {
    
    private final String table;
    private final int primaryKey;
    
    public Record(int primaryKey, String table){
	this.primaryKey = primaryKey;
	this.table = table;
    }
    
    // return the column name for the primary key
    public String getPrimaryKey(){
	return "id";
    }
    
    public int getPrimaryKeyValue(){
	return primaryKey;
    }
    
    public String getTableName(){
	return table;
    }
    
    // save changes to the db
    public abstract FunctionResult<Type> update();
    
    // save changes to the db
    public abstract boolean delete();
}
