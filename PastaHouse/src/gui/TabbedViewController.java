/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.List;
import javax.swing.JMenu;
import javax.swing.JPanel;

/**
 *
 * @author Hannes
 */
public interface TabbedViewController {
    public JPanel view();
    public List<JMenu> menus();
}
