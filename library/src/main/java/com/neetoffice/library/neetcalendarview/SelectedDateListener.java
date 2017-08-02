package com.neetoffice.library.neetcalendarview;

import java.util.Date;
import java.util.List;

/**
 * Created by Deo-chainmeans on 2016/7/31.
 */
public interface SelectedDateListener {

    void onSelectedDate(Date selectedBeginDate);

    void onSelectedDates(List<Date> selectedDates);
}
