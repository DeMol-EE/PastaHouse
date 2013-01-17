/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities.table.invoicetable;

import java.util.ArrayList;
import java.util.Date;
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
    private Date fromDate;
    private Date toDate;
    private RowFilter<Object, Object> clientFilter;
    private RowFilter<Object, Object> numberFilter;
    private RowFilter<Object, Object> fromDateFilter;
    private RowFilter<Object, Object> toDateFilter;

    public InvoiceFiltering(JXTable invoiceTable) {
        this.invoiceTable = invoiceTable;
    }

    public boolean isFilteringByClient() {
        return !isEmpty(getClientString());
    }

    public boolean isFilteringByNumber() {
        return !isEmpty(getNumberString());
    }

    public boolean isFilteringByFromDate() {
        return !isEmpty(getNumberString());
    }

    public boolean isFilteringByToDate() {
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
        firePropertyChange("numberString", oldValue, getNumberString());
    }

    public void setFromDate(Date date) {
        Date oldValue = getFromDate();
        this.fromDate = date;
        updateFromDateFilter();
        firePropertyChange("numberString", oldValue, getFromDate());
    }

    public void setToDate(Date date) {
        Date oldValue = getToDate();
        this.toDate = date;
        updateToDateFilter();
        firePropertyChange("toDate", oldValue, getToDate());
    }

    public String getClientString() {
        return clientString;
    }

    public String getNumberString() {
        return numberString;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
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

    private void updateToDateFilter() {
        if (toDate != null) {
            toDateFilter = (createToDateFilter(toDate));
        } else {
            toDateFilter = null;
        }
        updateFilters();
    }

    private void updateFromDateFilter() {
        if (fromDate != null) {
            fromDateFilter = (createFromDateFilter(fromDate));
        } else {
            fromDateFilter = null;
        }
        updateFilters();
    }

    private void updateFilters() {
        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
        if (clientFilter != null) {
            filters.add(clientFilter);
        }
        if (numberFilter != null) {
            filters.add(numberFilter);
        }
        if (fromDateFilter != null) {
            filters.add(fromDateFilter);
        }
        if (toDateFilter != null) {
            filters.add(toDateFilter);
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

    private RowFilter<Object, Object> createToDateFilter(final Date filterdate) {
        return new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
                String stringdate = entry.getStringValue(2);
                Date date = new Date(stringdate);
                boolean matches = false;
                int test = filterdate.compareTo(date);
                if (test >= 0) {
                    matches = true;
                }
                return matches;
            }
        };
    }

    private RowFilter<Object, Object> createFromDateFilter(final Date filterdate) {
        return new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
                String stringdate = entry.getStringValue(2);
                Date date = new Date(stringdate);
                boolean matches = false;
                int test = filterdate.compareTo(date);
                if (test <= 0) {
                    matches = true;
                }
                return matches;
            }
        };
    }
    
    public void clearFilters(){
        invoiceTable.setRowFilter(null);
        
    }
}
