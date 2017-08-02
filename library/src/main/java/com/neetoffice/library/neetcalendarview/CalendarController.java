package com.neetoffice.library.neetcalendarview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Deo-chainmeans on 2016/7/21.
 */
public class CalendarController {
    final CalendarControllerCallBack calendarControllerCallBack;
    final int MonthTitleTextColor;
    final int MonthTitleTextSize;
    final boolean MonthTitleEnabled;
    final int WeekTextColor;
    final int WeekTextSize;
    final boolean WeekEnabled;
    final int NormalDayTextColor;
    final int NormalDayTextSize;
    final int ToDayTextColor;
    final int ToDayBackgroundColor;
    final boolean ToDayTextBold;
    final int SelectedDayTextColor;
    final int SelectedDayBackgroundColor;
    final int SecondSelectedDayTextColor;
    final int SecondSelectedDayBackgroundColor;
    final int SelectedDayShape;
    final int LinkSelectedDayTextColor;
    final int LinkSelectedDayBackgroundColor;
    final boolean LinkSelectedDayBackground;
    final int padding;
    final int orientation;
    boolean isDoubleClick;
    Date toDay;
    Date selectedBeginDate = null;
    Date selectedLastDate = null;
    MonthTitleInsert monthTitleInsert;
    SelectedDateListener selectedDateListener;

    CalendarController(Context context, CalendarControllerCallBack calendarControllerCallBack, TypedArray typedArray) {
        toDay = Calendar.getInstance().getTime();
        this.calendarControllerCallBack = calendarControllerCallBack;
        if (context != null && typedArray != null) {
            final Resources r = context.getResources();
            final Resources.Theme theme = context.getTheme();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                MonthTitleTextColor = typedArray.getColor(R.styleable.NeetCalendarView_MonthTitleTextColor, r.getColor(R.color.DefaultTextColor, theme));
                WeekTextColor = typedArray.getColor(R.styleable.NeetCalendarView_WeekTextColor, r.getColor(R.color.DefaultTextColor, theme));
                NormalDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_NormalDayTextColor, r.getColor(R.color.DefaultTextColor, theme));
                ToDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_ToDayTextColor, r.getColor(R.color.ToDayTextColor, theme));
                ToDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_ToDayBackgroundColor, r.getColor(android.R.color.transparent, theme));
                SelectedDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_SelectedDayTextColor, r.getColor(R.color.SelectedDayTextColor, theme));
                SelectedDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_SelectedDayBackgroundColor, r.getColor(R.color.SelectedDayBackground, theme));
                SecondSelectedDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_SecondSelectedDayTextColor, SelectedDayTextColor);
                SecondSelectedDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_SecondSelectedDayBackgroundColor, SelectedDayBackgroundColor);
                LinkSelectedDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_LinkSelectedDayTextColor, r.getColor(R.color.SelectedDayTextColor, theme));
                LinkSelectedDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_LinkSelectedDayBackgroundColor, r.getColor(R.color.SelectedDayBackground, theme));
            } else {
                MonthTitleTextColor = typedArray.getColor(R.styleable.NeetCalendarView_MonthTitleTextColor, r.getColor(R.color.DefaultTextColor));
                WeekTextColor = typedArray.getColor(R.styleable.NeetCalendarView_WeekTextColor, r.getColor(R.color.DefaultTextColor));
                NormalDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_NormalDayTextColor, r.getColor(R.color.DefaultTextColor));
                ToDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_ToDayTextColor, r.getColor(R.color.ToDayTextColor));
                ToDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_ToDayBackgroundColor, r.getColor(android.R.color.transparent));
                SelectedDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_SelectedDayBackgroundColor, r.getColor(R.color.SelectedDayBackground));
                SelectedDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_SelectedDayTextColor, r.getColor(R.color.SelectedDayTextColor));
                SecondSelectedDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_SecondSelectedDayTextColor, SelectedDayTextColor);
                SecondSelectedDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_SecondSelectedDayBackgroundColor, SelectedDayBackgroundColor);
                LinkSelectedDayTextColor = typedArray.getColor(R.styleable.NeetCalendarView_LinkSelectedDayTextColor, r.getColor(R.color.SelectedDayTextColor));
                LinkSelectedDayBackgroundColor = typedArray.getColor(R.styleable.NeetCalendarView_LinkSelectedDayBackgroundColor, r.getColor(R.color.SelectedDayBackground));
            }
            MonthTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.NeetCalendarView_MonthTitleTextSize, r.getDimensionPixelOffset(R.dimen.TextSize));
            MonthTitleEnabled = typedArray.getBoolean(R.styleable.NeetCalendarView_MonthTitleEnabled, true);
            WeekTextSize = typedArray.getDimensionPixelSize(R.styleable.NeetCalendarView_WeekTextSize, r.getDimensionPixelOffset(R.dimen.TextSize));
            WeekEnabled = typedArray.getBoolean(R.styleable.NeetCalendarView_WeekEnabled, true);
            NormalDayTextSize = typedArray.getDimensionPixelSize(R.styleable.NeetCalendarView_NormalDayTextSize, r.getDimensionPixelOffset(R.dimen.TextSize));
            ToDayTextBold = typedArray.getBoolean(R.styleable.NeetCalendarView_ToDayTextBold, true);
            SelectedDayShape = typedArray.getInt(R.styleable.NeetCalendarView_SelectedDayShape, 0);
            LinkSelectedDayBackground = typedArray.getBoolean(R.styleable.NeetCalendarView_LinkSelectedDayBackground, true);
            orientation = typedArray.getInt(R.styleable.NeetCalendarView_orientation, CalendarView.VERTICAL);
            padding = typedArray.getDimensionPixelSize(R.styleable.NeetCalendarView_Padding, r.getDimensionPixelOffset(R.dimen.TextSize));
            isDoubleClick = typedArray.getBoolean(R.styleable.NeetCalendarView_DoubleClick, true);
            try {
                final String classPath = typedArray.getString(R.styleable.NeetCalendarView_MonthTitleInsert);
                if (classPath != null) {
                    final Class clz = Class.forName(classPath);
                    monthTitleInsert = (MonthTitleInsert) clz.newInstance();
                }
            } catch (ClassNotFoundException e) {
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
        } else {
            MonthTitleTextColor = Color.BLACK;
            WeekTextColor = Color.BLACK;
            NormalDayTextColor = Color.BLACK;
            ToDayTextColor = Color.RED;
            ToDayBackgroundColor = Color.TRANSPARENT;
            SelectedDayTextColor = Color.WHITE;
            SelectedDayBackgroundColor = Color.parseColor("#FFAAAA");
            SecondSelectedDayTextColor = SelectedDayTextColor;
            SecondSelectedDayBackgroundColor = SelectedDayBackgroundColor;
            LinkSelectedDayTextColor = Color.WHITE;
            LinkSelectedDayBackgroundColor = SelectedDayBackgroundColor;
            MonthTitleTextSize = 16;
            MonthTitleEnabled = true;
            WeekTextSize = 16;
            WeekEnabled = true;
            NormalDayTextSize = 16;
            ToDayTextBold = true;
            SelectedDayShape = 0;
            LinkSelectedDayBackground = true;
            orientation = CalendarView.VERTICAL;
            padding = 16;
            isDoubleClick = true;
        }
    }

    public void setToDay(Date toDay) {
        this.toDay = toDay;
        calendarControllerCallBack.notifyDataSetChanged();
    }

    public void setSelectedBeginDate(Date date) {
        selectedBeginDate = date;
        calendarControllerCallBack.notifyDataSetChanged();
        if (selectedDateListener != null && selectedBeginDate != null) {
            selectedDateListener.onSelectedDate(selectedBeginDate);
        }
    }

    public void setSelectedLastDate(Date date) {
        if (!isDoubleClick) {
            return;
        }
        if (selectedBeginDate == null) {
            setSelectedBeginDate(date);
            return;
        }
        if (selectedBeginDate.after(date)) {
            selectedLastDate = selectedBeginDate;
            selectedBeginDate = date;
        } else {
            selectedLastDate = date;
        }
        calendarControllerCallBack.notifyDataSetChanged();
        if (selectedDateListener != null) {
            final ArrayList<Date> dates = new ArrayList<>();
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedBeginDate);
            while (CalendarUtil.isBafterA(calendar.getTime(), selectedLastDate)) {
                dates.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            calendar.setTime(selectedLastDate);
            dates.add(calendar.getTime());
            selectedDateListener.onSelectedDates(dates);
        }
    }

    public void setIsDoubleClick(boolean isDoubleClick) {
        this.isDoubleClick = isDoubleClick;
        calendarControllerCallBack.notifyDataSetChanged();
    }

    public void setSelectedDateListener(SelectedDateListener selectedDateListener) {
        this.selectedDateListener = selectedDateListener;
    }
}
