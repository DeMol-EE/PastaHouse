/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Robin jr
 */
public interface MasterDetailViewController extends ViewController {
    public void updateDetail(Object value);
    public void updateList();
    public void updateListAndSelect(Object select);
    public void add();
    public void edit();
}
