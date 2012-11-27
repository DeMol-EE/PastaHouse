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

    public FunctionResult(int code, Type obj) {
	this.code = code;
	this.obj = obj;
    }

    public int getCode() {
	return code;
    }

    public Type getObj() {
	return obj;
    }
}
