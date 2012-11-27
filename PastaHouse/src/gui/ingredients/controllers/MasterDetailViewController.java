/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.ingredients.controllers;

/**
 *
 * @author Robin jr
 */
public interface MasterDetailViewController<Type> extends ViewController {
    public void updateDetail(Type value);
    public void addAndSelect(Type newObj);
    public void editAndSelect(Type newObj, Type oldObj);
    public void add();
    public void edit();
}
