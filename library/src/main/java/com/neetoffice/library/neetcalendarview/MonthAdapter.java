package com.neetoffice.library.neetcalendarview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Deo-chainmeans on 2016/7/21.
 */
public class MonthAdapter extends RecyclerView.Adapter<MonthViewHolder> {
    private final Context context;
    private final CalendarAdapter calendarAdapter;
    private final CalendarController calendarController;

    public MonthAdapter(Context context, CalendarAdapter calendarAdapter, CalendarController calendarController) {
        this.context = context;
        this.calendarAdapter = calendarAdapter;
        this.calendarController = calendarController;
    }

    @Override
    public MonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MonthView monthView = new MonthView(context, calendarAdapter, calendarController);
        return new MonthViewHolder(monthView);
    }

    @Override
    public void onBindViewHolder(MonthViewHolder holder, int position) {
        final int year = position / 12;
        final int month = position % 12;
        holder.setYrarMonth(calendarAdapter.getBeginYear() + year, month);
    }

    @Override
    public int getItemCount() {
        final int year = calendarAdapter.getLastYear() - calendarAdapter.getBeginYear() + 1;
        return year * 12;
    }
}
