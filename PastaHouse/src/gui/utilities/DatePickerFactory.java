/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.utilities;

import java.awt.Color;
import java.util.Calendar;
import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode;

/**
 *
 * @author Hannes
 */
public class DatePickerFactory {
    
    public JXDatePicker makeStandardDatePicker(){
        JXDatePicker dp = new JXDatePicker();
        dp.getLinkPanel().setVisible(false);
        JXMonthView mv = dp.getMonthView();
        mv.setSelectionMode(SelectionMode.SINGLE_SELECTION);
        mv.setFirstDayOfWeek(Calendar.MONDAY);
        mv.setShowingWeekNumber(false);
        mv.setShowingLeadingDays(true);
        mv.setShowingTrailingDays(true);
        mv.setTodayBackground(Color.BLUE);
        mv.setTraversable(true);
        
        return dp;
    }
    
}
