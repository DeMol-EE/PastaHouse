/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.combobox;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Robin jr
 */
public class ComboBoxModelFactory {
    public static ComboBoxModel createSupplierComboBoxModel(Object[] data){
	return new SupplierComboBoxModel(data);
    }
    
    private static class SupplierComboBoxModel extends AbstractListModel implements ComboBoxModel{

	private Object[] data;
	private Object selection;
	
	public SupplierComboBoxModel(Object[] data){
	    this.data = data;
	}
	
	@Override
	public int getSize() {
	    return data.length;
	}

	@Override
	public Object getElementAt(int index) {
	    return data[index];
	}

	@Override
	public void setSelectedItem(Object anItem) {
	    selection = anItem;
	}

	@Override
	public Object getSelectedItem() {
	    return selection;
	}
	
    }
}
