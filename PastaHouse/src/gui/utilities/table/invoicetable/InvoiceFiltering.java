/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table.invoicetable;

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

    public boolean isFilteringByNumber() {
        return !isEmpty(getNumberString());
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

    public void setNumberString(String filterString) {
        String oldValue = getClientString();
        this.numberString = filterString;
        updateNumberFilter();
        firePropertyChange("filterString", oldValue, getClientString());
    }

    public String getClientString() {
        return clientString;
    }

    public String getNumberString() {
        return numberString;
    }

    private void updateClientFilter() {
        if ((clientString != null) && (clientString.length() > 0)) {
            clientFilter = createClientFilter(".*" + clientString + ".*");
        } else {
            clientFilter = null;
        }
        updateFilters();
    }

    private void updateNumberFilter() {
        if ((numberString != null) && (numberString.length() > 0)) {
            numberFilter = createNumberFilter(numberString + ".*");
        } else {
            numberFilter = null;
        }
        updateFilters();
    }

    private void updateFilters() {
        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
        if(clientFilter != null){
            filters.add(clientFilter);
        }
        if(numberFilter != null){
            filters.add(numberFilter);
        }
        if(dateFilter != null){
            filters.add(dateFilter);
        }
        RowFilter<Object, Object> comboFilter = RowFilter.andFilter(filters);
        invoiceTable.setRowFilter(comboFilter);
    }

    private RowFilter<Object, Object> createClientFilter(final String filterString) {
        return new RowFilter<Object, Object>() {
            @Override
            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                String klant = entry.getStringValue(1);
                boolean matches = false;
                Pattern p = Pattern.compile(filterString, Pattern.CASE_INSENSITIVE);
                if (klant != null) {
                    matches = p.matcher(klant).matches();
                }
                return matches;
            }
        };
    }

    private RowFilter<Object, Object> createNumberFilter(final String filterString) {
        return new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
                String number = entry.getStringValue(0);
                boolean matches = false;
                Pattern p = Pattern.compile(filterString, Pattern.CASE_INSENSITIVE);
                if (number != null) {
                    matches = p.matcher(number).matches();
                }
                return matches;
            }
        };
    }
}
