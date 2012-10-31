/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import database.BasicIngredient;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Warkst
 */
public class ListModelFactory {
    public static AbstractListModel createBasicIngredientModel(final ArrayList<BasicIngredient> data){
	return new AbstractListModel() {

	    @Override
	    public int getSize() {
		return data.size();
	    }

	    @Override
	    public Object getElementAt(int index) {
		return data.get(index);
	    }
	    
	    public void add(BasicIngredient o){
		if (data.add(o)) {
		    fireContentsChanged(this, 0, getSize());
		}
	    }
	};
    }
    
    
}
