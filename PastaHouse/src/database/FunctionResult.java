/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import database.extra.Record;

/**
 *
 * @author Warkst
 */
public class FunctionResult<Type extends Record> {
    private final int code;
    private final Type obj;
    private final String msg;

    public FunctionResult(int code, Type obj, String msg){
	this.code = code;
	this.obj = obj;
	this.msg = msg;
    }

    public int getCode() {
	return code;
    }

    public Type getObj() {
	return obj;
    }
    
    public String getMessage(){
	return msg;
    }
}
