package com.neetoffice.library.neetcalendarview;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Deo-chainmeans on 2016/7/21.
 */
public class CalendarAdapter {

    public String getMonthTitle(int year, int month) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return CalendarUtil.MONTH_DATE_FORMAT.format(calendar.getTime());
    }

    public String getDayOfWeekText(int dayOfWeek) {
        return CalendarUtil.DATE_FORMAT_SYMBOLS.getShortWeekdays()[dayOfWeek].toUpperCase(Locale.getDefault());
    }

    public String getDayOfMonthText(int dayOfMonth) {
        return String.format("%d", dayOfMonth);
    }

    public int getBeginYear() {
        return Calendar.getInstance().get(Calendar.YEAR) - 2;
    }

    public int getLastYear() {
        return Calendar.getInstance().get(Calendar.YEAR) + 2;
    }
}
