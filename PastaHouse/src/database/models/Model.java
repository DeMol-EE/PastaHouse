/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.models;

import database.FunctionResult;

/**
 * This interface simply flags implementing classes as being models for MVC.
 * 
 * @author Warkst
 */
public interface Model {
    
    /**
     * Tries to create a Record from this Model.
     */
    public abstract FunctionResult create();
}
