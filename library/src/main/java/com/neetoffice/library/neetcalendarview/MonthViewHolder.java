package com.neetoffice.library.neetcalendarview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Deo-chainmeans on 2016/7/21.
 */
public class MonthViewHolder extends RecyclerView.ViewHolder {

    MonthViewHolder(View itemView) {
        super(itemView);
        setIsRecyclable(false);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    void setYrarMonth(int year, int month) {
        final MonthView monthView = (MonthView) itemView;
        monthView.setYrarMonth(year, month);
    }


}
