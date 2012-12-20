/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table.invoicetable;

import database.tables.Invoice;
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
    private String clientString;
    private String numberString;
    private RowFilter<Object, Object> clientFilter;
    private RowFilter<Object, Object> numberFilter;
    private RowFilter<Object, Object> dateFilter;

    public InvoiceFiltering(JXTable invoiceTable) {
        this.invoiceTable = invoiceTable;
    }

    public boolean isFilteringByClient() {
        return !isEmpty(getClientString());
    }

    private boolean isEmpty(String filterString) {
        return filterString == null || filterString.length() == 0;
    }

    public void setClientString(String filterString) {
        String oldValue = getClientString();
        this.clientString = filterString;
        updateClientFilter();
        firePropertyChange("filterString", oldValue, getClientString());
    }

    public String getClientString() {
        return clientString;
    }

    private void updateClientFilter() {
        if ((clientString != null) && (clientString.length() > 0)) {
            clientFilter = createClientFilter(".*" + clientString + ".*");
        } else {
            clientFilter = null;
        }
        updateFilters();
    }

    private void updateFilters() {
        // <snip> Filter control 
        // set the filters to the table  
        invoiceTable.setRowFilter(clientFilter);
    }

    private RowFilter<Object, Object> createClientFilter(final String filterString) {
        return new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                InvoiceTableModel invoiceModel = (InvoiceTableModel) entry.getModel();
                String klant = entry.getStringValue(1);

                boolean matches = false;
                Pattern p = Pattern.compile(filterString, Pattern.CASE_INSENSITIVE);
                // match against movie title 
                if (klant != null) {
                    matches = p.matcher(klant).matches();
                }
                if(matches) {
                    
                    System.out.println(klant);
                }
                return matches;
            }
            //                </snip> 
        };
    }
}
