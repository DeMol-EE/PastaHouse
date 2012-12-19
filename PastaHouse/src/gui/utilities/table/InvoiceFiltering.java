/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table;

import database.tables.Invoice;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.RowFilter;
import org.jdesktop.beans.AbstractBean;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Hannes
 */
public class InvoiceFiltering extends AbstractBean {

    private JXTable invoiceTable;
    private String filterString;
    private RowFilter<Object, Object> searchFilter;

    public InvoiceFiltering(JXTable invoiceTable) {
        this.invoiceTable = invoiceTable;
    }

    public boolean isFilteringByString() {
        return !isEmpty(getFilterString());
    }

    private boolean isEmpty(String filterString) {
        return filterString == null || filterString.length() == 0;
    }

    public void setFilterString(String filterString) {
        String oldValue = getFilterString();
        // <snip> Filter control 
        // set the filter string (bound to the input in the textfield) 
        // and update the search RowFilter 
        this.filterString = filterString;
        updateSearchFilter();
        //        </snip> 
        firePropertyChange("filterString", oldValue, getFilterString());
    }

    public String getFilterString() {
        return filterString;
    }

    private void updateSearchFilter() {
        if ((filterString != null) && (filterString.length() > 0)) {
            searchFilter = createSearchFilter(filterString + ".*");
        } else {
            searchFilter = null;
        }
        updateFilters();
    }

    private void updateFilters() {
        // <snip> Filter control 
        // set the filters to the table  
        invoiceTable.setRowFilter(searchFilter);
    }

    private RowFilter<Object, Object> createSearchFilter(final String filterString) {
        return new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                InvoiceTableModel invoiceModel = (InvoiceTableModel) entry.getModel();
                Invoice invoice = invoiceModel.getInvoice(((Integer) entry.getIdentifier()).intValue());
                boolean matches = false;
                Pattern p = Pattern.compile(filterString + ".*", Pattern.CASE_INSENSITIVE);
                // match against movie title 
                String klant = invoice.getClient().getFirm();
                if (klant != null) {
                    matches = p.matcher(klant).matches();
                }
                String number = "" + invoice.getNumber();

                if (number != null) {
                    matches = p.matcher(number).matches();
                }
                return matches;
            }
            //                </snip> 
        };
    }
}
