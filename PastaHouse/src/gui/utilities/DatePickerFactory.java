/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

/**
 *
 * @author Hannes
 */
public class DatePickerFactory {

    private static final String[] days = {"Zon", "Maa", "Din", "Woe", "Don", "Vri", "Zat"};

    public static JXDatePicker makeStandardDatePicker() {
        JXDatePicker dp = new JXDatePicker();
	dp.getEditor().setFocusable(false);
        dp.setFormats(new SimpleDateFormat("dd/MM/yyyy"));
        dp.getLinkPanel().setVisible(false);
        JXMonthView mv = dp.getMonthView();
        mv.setSelectionMode(SelectionMode.SINGLE_SELECTION);
        mv.setDaysOfTheWeek(days);
        mv.setFirstDayOfWeek(Calendar.MONDAY);
        mv.setShowingWeekNumber(false);
        mv.setShowingLeadingDays(false);
        mv.setShowingTrailingDays(false);
        mv.setTodayBackground(Color.BLUE);
        mv.setTraversable(true);

        return dp;
    }
}
