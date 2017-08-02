package com.neetoffice.library.neetcalendarview;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.Calendar;

/**
 * Created by Deo-chainmeans on 2016/7/21.
 */
public class CalendarView extends FrameLayout {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    MonthAdapter monthAdapter;
    private final CalendarControllerCallBack callBack = new CalendarControllerCallBack() {
        @Override
        public void notifyDataSetChanged() {
            monthAdapter.notifyDataSetChanged();
        }
    };
    private RecyclerView recyclerView;
    private CalendarAdapter calendarAdapter;
    private CalendarController calendarController;
    private int orientation = HORIZONTAL;

    public CalendarView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        recyclerView = new RecyclerView(context);
        addView(recyclerView);
        final TypedArray typedArray;
        if (attrs != null) {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.NeetCalendarView, defStyleAttr, defStyleRes);
        } else {
            typedArray = null;
        }
        calendarController = new CalendarController(context, callBack, typedArray);
        calendarAdapter = new CalendarAdapter();
        setOrientation(calendarController.orientation);
        monthAdapter = new MonthAdapter(context, calendarAdapter, calendarController);
        recyclerView.setAdapter(monthAdapter);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR) - calendarAdapter.getBeginYear();
        recyclerView.scrollToPosition(year * 12 + calendar.get(Calendar.MONTH) + 1);
        recyclerView.scrollToPosition(year * 12 + calendar.get(Calendar.MONTH));
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
        if (orientation == HORIZONTAL) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }
    }

    public CalendarController getCalendarController() {
        return calendarController;
    }
}
