package gui.utilities.table.invoicetable;

import database.tables.Invoice;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JComponent;

import org.jdesktop.swingx.decorator.AbstractHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.renderer.StringValue;
import org.jdesktop.swingx.renderer.StringValues;

public class InvoiceRendering {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(InvoiceRendering.class
            .getName());

    //<snip> JXTable column properties 
    // Note: the custom column factory is a feature enhanced factory 
    // which allows column configuration based on column identifier 
    public static void configureColumnFactory(CustomColumnFactory factory,
            Class<?> resourceBase) {
        // set location to load resources from 
        factory.setBaseClass(resourceBase);

        // add hints for column sizing 
        factory.addPrototypeValue("dateColumn", "11/11/1111");
        factory.addPrototypeValue("clientColumn", "jan de janner");
        factory.addPrototypeValue("numberColumn", "201648497");

    }

    public static class ToolTipHighlighter extends AbstractHighlighter {

        private List<StringValue> stringValues;
        private List<Object> sourceColumns;
        private String delimiter;

        /**
         * Adds a StringValue to use on the given sourceColumn.
         *
         * @param sv the StringValue to use.
         * @param sourceColumn the column identifier of the column to use.
         */
        public void addStringValue(StringValue sv, Object sourceColumn) {
            if (stringValues == null) {
                stringValues = new ArrayList<StringValue>();
                sourceColumns = new ArrayList<Object>();
            }
            stringValues.add(sv);
            sourceColumns.add(sourceColumn);
        }

        /**
         * Sets the delimiter to use between StringValues.
         *
         * @param delimiter the delimiter to use between StringValues, if there
         * are more than one.
         */
        public void setDelimiter(String delimiter) {
            this.delimiter = delimiter;
        }

        @Override
        protected Component doHighlight(Component component,
                ComponentAdapter adapter) {
            String toolTip = getToolTipText(component, adapter);
            // PENDING: treetableCellRenderer doesn't reset tooltip! 
            if (toolTip != null) {
                ((JComponent) component).setToolTipText(toolTip);
            }
            return component;
        }

        private String getToolTipText(Component component,
                ComponentAdapter adapter) {
            if ((stringValues == null) || stringValues.isEmpty()) {
                return null;
            }
            String text = "";
            for (int i = 0; i < stringValues.size(); i++) {
                int modelIndex = adapter.getColumnIndex(sourceColumns.get(i));
                if (modelIndex >= 0) {
                    text += stringValues.get(i).getString(adapter.getValue(modelIndex));
                    if ((i != stringValues.size() - 1) && !isEmpty(text)) {
                        text += delimiter;
                    }
                }
            }
            return isEmpty(text) ? null : text;
        }

        private boolean isEmpty(String text) {
            return text.length() == 0;
        }

        /**
         * Overridden to check for JComponent type.
         */
        @Override
        protected boolean canHighlight(Component component,
                ComponentAdapter adapter) {
            return component instanceof JComponent;
        }
    }

    public static class ListStringValue implements StringValue {

        boolean isToolTip;
        String singleToolTipPrefix;
        String multipleToolTipPrefix;

        public ListStringValue() {
            this(false, null, null);
        }

        public ListStringValue(boolean asToolTip, String singleItem, String multipleItems) {
            this.isToolTip = asToolTip;
            this.singleToolTipPrefix = singleItem;
            this.multipleToolTipPrefix = multipleItems;
        }

        @SuppressWarnings("unchecked")
        public String getString(Object value) {
            if (value instanceof List) {
                List<String> persons = (List<String>) value;
                if (isToolTip) {
                    return getStringAsToolTip(persons);
                }
                return getStringAsContent(persons);
            }
            return StringValues.TO_STRING.getString(value);
        }

        private String getStringAsToolTip(List<String> persons) {
            if (persons.size() > 1) {
                StringBuffer winners = new StringBuffer("");
                if (multipleToolTipPrefix != null) {
                    winners.append(multipleToolTipPrefix);
                }
                for (String person : persons) {
                    winners.append(person);
                    winners.append(", ");
                }
                winners = winners.delete(winners.lastIndexOf(","), winners.length());
                return winners.toString();
            }
            return StringValues.TO_STRING.getString(singleToolTipPrefix);
        }

        private String getStringAsContent(List<String> persons) {
            if (persons.isEmpty()) {
                return "unknown";
            }
            if (persons.size() > 1) {
                return persons.get(0) + " + more ...";
            }
            return persons.get(0);
        }
    }
}
