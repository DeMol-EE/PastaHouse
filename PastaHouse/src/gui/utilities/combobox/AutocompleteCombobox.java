package gui.utilities.combobox;

import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

public class AutocompleteCombobox extends JComboBox{

    private class AutoTextFieldEditor extends BasicComboBoxEditor {

        private AutoTextField getAutoTextFieldEditor() {
            return (AutoTextField) editor;
        }

        AutoTextFieldEditor(java.util.List list) {
            editor = new AutoTextField(list, AutocompleteCombobox.this);
        }
    }

    public AutocompleteCombobox(java.util.List list) {
        isFired = false;
        autoTextFieldEditor = new AutoTextFieldEditor(list);
        setEditable(true);
        setModel(new DefaultComboBoxModel(list.toArray()) {
            @Override
            protected void fireContentsChanged(Object obj, int i, int j) {
                if (!isFired) {
                    super.fireContentsChanged(obj, i, j);
                }
            }
        });
        setEditor(autoTextFieldEditor);
    }

    public boolean isCaseSensitive() {
        return autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
    }

    public void setCaseSensitive(boolean flag) {
        autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
    }

    public boolean isStrict() {
        return autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
    }

    public void setStrict(boolean flag) {
        autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
    }

    public java.util.List getDataList() {
        return autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
    }

    public void setDataList(java.util.List list) {
        autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
        setModel(new DefaultComboBoxModel(list.toArray()));
    }

    void setSelectedValue(Object obj) {
        if (isFired) {
        } else {
            isFired = true;
            setSelectedItem(obj);
            fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder,
                    1));
            isFired = false;
        }
    }

    @Override
    protected void fireActionEvent() {
        if (!isFired) {
            super.fireActionEvent();
        }
    }
    private AutoTextFieldEditor autoTextFieldEditor;
    private boolean isFired;
}