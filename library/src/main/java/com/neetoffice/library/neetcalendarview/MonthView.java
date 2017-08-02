package com.neetoffice.library.neetcalendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Deo-chainmeans on 2016/7/21.
 */
public class MonthView extends View {
    private static final String TAG = MonthView.class.getSimpleName();
    private final CalendarAdapter calendarAdapter;
    private final CalendarController calendarController;
    private final Paint monthPaint;
    private final Paint dayOfWeekPaint;
    private final Paint dayOfMonthPaint;
    private final Paint toDayPaint;
    private final Paint toDayBackgroundPaint;
    private final Paint selectedCirclePaint;
    private final Paint secondSelectedCirclePaint;
    private final Paint linkselectedPaint;
    private final int selectedTextColor;
    private final int secondSelectedTextColor;
    private final int unselectedTextColor;
    private final int betweenTextColor;
    private final Calendar calendar;
    private final int monthPaintSize;
    private final int dayOfWeekPaintSize;
    private final int dayOfMonthPaintSize;
    private final int selectedDayShape;
    private final boolean linkSelectedDays;
    private int thisYear;
    private int thisMonth;
    private boolean isDoubleClick;

    public MonthView(Context context, CalendarAdapter calendarAdapter, CalendarController calendarController) {
        super(context);
        this.calendarAdapter = calendarAdapter;
        this.calendarController = calendarController;
        calendar = Calendar.getInstance();
        monthPaintSize = calendarController.MonthTitleTextSize;
        dayOfWeekPaintSize = calendarController.WeekTextSize;
        dayOfMonthPaintSize = calendarController.NormalDayTextSize;
        selectedDayShape = calendarController.SelectedDayShape;
        secondSelectedTextColor = calendarController.SecondSelectedDayTextColor;
        linkSelectedDays = calendarController.LinkSelectedDayBackground;
        selectedTextColor = calendarController.SelectedDayTextColor;
        unselectedTextColor = calendarController.NormalDayTextColor;
        betweenTextColor = calendarController.LinkSelectedDayTextColor;
        isDoubleClick = calendarController.isDoubleClick;
        if (calendarController.orientation == CalendarView.VERTICAL) {
            setPadding(0, calendarController.padding, 0, calendarController.padding);
        } else {
            setPadding(calendarController.padding, 0, calendarController.padding, 0);
        }
        monthPaint = new Paint();
        monthPaint.setAntiAlias(true);
        monthPaint.setTextSize(calendarController.MonthTitleTextSize);
        monthPaint.setStyle(Paint.Style.FILL);
        monthPaint.setTextAlign(Paint.Align.CENTER);
        monthPaint.setFakeBoldText(false);
        monthPaint.setColor(calendarController.MonthTitleTextColor);

        dayOfWeekPaint = new Paint();
        dayOfWeekPaint.setAntiAlias(true);
        dayOfWeekPaint.setTextSize(calendarController.WeekTextSize);
        dayOfWeekPaint.setStyle(Paint.Style.FILL);
        dayOfWeekPaint.setTextAlign(Paint.Align.CENTER);
        dayOfWeekPaint.setFakeBoldText(false);
        dayOfWeekPaint.setColor(calendarController.WeekTextColor);

        dayOfMonthPaint = new Paint();
        dayOfMonthPaint.setAntiAlias(true);
        dayOfMonthPaint.setTextSize(calendarController.NormalDayTextSize);
        dayOfMonthPaint.setStyle(Paint.Style.FILL);
        dayOfMonthPaint.setTextAlign(Paint.Align.CENTER);
        dayOfMonthPaint.setFakeBoldText(false);

        toDayPaint = new Paint();
        toDayPaint.setAntiAlias(true);
        toDayPaint.setTextSize(calendarController.NormalDayTextSize);
        toDayPaint.setColor(calendarController.ToDayTextColor);
        toDayPaint.setStyle(Paint.Style.FILL);
        toDayPaint.setTextAlign(Paint.Align.CENTER);
        toDayPaint.setFakeBoldText(calendarController.ToDayTextBold);

        toDayBackgroundPaint = new Paint();
        toDayBackgroundPaint.setAntiAlias(true);
        toDayBackgroundPaint.setStyle(Paint.Style.FILL);
        toDayBackgroundPaint.setColor(calendarController.ToDayBackgroundColor);
        toDayBackgroundPaint.setTextAlign(Paint.Align.CENTER);
        toDayBackgroundPaint.setFakeBoldText(false);

        selectedCirclePaint = new Paint();
        selectedCirclePaint.setFakeBoldText(true);
        selectedCirclePaint.setAntiAlias(true);
        selectedCirclePaint.setColor(calendarController.SelectedDayBackgroundColor);
        selectedCirclePaint.setTextAlign(Paint.Align.CENTER);
        selectedCirclePaint.setStyle(Paint.Style.FILL);

        secondSelectedCirclePaint = new Paint();
        secondSelectedCirclePaint.setFakeBoldText(true);
        secondSelectedCirclePaint.setAntiAlias(true);
        secondSelectedCirclePaint.setColor(calendarController.SecondSelectedDayBackgroundColor);
        secondSelectedCirclePaint.setTextAlign(Paint.Align.CENTER);
        selectedCirclePaint.setStyle(Paint.Style.FILL);

        linkselectedPaint = new Paint();
        linkselectedPaint.setFakeBoldText(true);
        linkselectedPaint.setAntiAlias(true);
        linkselectedPaint.setColor(calendarController.LinkSelectedDayBackgroundColor);
        linkselectedPaint.setTextAlign(Paint.Align.CENTER);
        linkselectedPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            final int selectedDate = getDayFromLocation(event.getX(), event.getY());
            if (selectedDate > 0 && selectedDate <= CalendarUtil.getDaysInMonth(thisYear, thisMonth)) {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, thisYear);
                calendar.set(Calendar.MONTH, thisMonth);
                calendar.set(Calendar.DAY_OF_MONTH, selectedDate);
                if (!isDoubleClick || calendarController.selectedLastDate != null) {
                    calendarController.selectedLastDate = null;
                    calendarController.setSelectedBeginDate(calendar.getTime());
                } else {
                    calendarController.setSelectedLastDate(calendar.getTime());
                }
            }
        }
        return true;
    }

    void setYrarMonth(int year, int month) {
        thisYear = year;
        thisMonth = month;
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), countMeasuredHeight());
    }

    private int countMeasuredHeight() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        final int dayOfWek = calendar.get(Calendar.DAY_OF_WEEK);
        final int daysInMonth = CalendarUtil.getDaysInMonth(calendar);
        final int dayRow = (daysInMonth + dayOfWek - 1) / 7;
        int height = 0;
        if (calendarController.MonthTitleEnabled) {
            height += monthRowHeight();
        }
        if (calendarController.WeekEnabled) {
            height += dayOfWeekRowHeight();
        }
        return height + (dayRow + 1) * dayOfMonthRowHeight() + getPaddingTop() + getPaddingBottom();
    }

    private int monthRowHeight() {
        return monthPaintSize * 2;
    }

    private int dayOfWeekRowHeight() {
        return dayOfWeekPaintSize * 2;
    }

    private int dayOfMonthRowHeight() {
        return dayOfMonthPaintSize * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (calendarController.MonthTitleEnabled) {
            drawMonth(canvas);
        }
        if (calendarController.WeekEnabled) {
            drawDayOfWeek(canvas);
        }
        drawDayOfMonth(canvas);
    }

    private void drawMonth(Canvas canvas) {
        final float width = getWidth() - getPaddingRight() - getPaddingLeft();
        final float height = monthRowHeight();
        if (calendarController.monthTitleInsert != null) {
            calendarController.monthTitleInsert.drawMonthTitle(this, canvas, width, height, monthPaint, thisYear, thisMonth);
        } else {
            final float x = width / 2;
            final float y = height + getPaddingTop();
            final String text = calendarAdapter.getMonthTitle(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
            canvas.drawText(text, x, y, monthPaint);
        }

    }

    private void drawDayOfWeek(Canvas canvas) {
        final int paddingDay = (getWidth() - getPaddingRight() - getPaddingLeft()) / 7;
        int y = 0;
        if (calendarController.MonthTitleEnabled) {
            y += monthRowHeight();
        }
        y += dayOfWeekRowHeight() / 2 + getPaddingTop();
        for (int i = 0; i <= 7; i++) {
            final int x = paddingDay * i + getPaddingRight() - paddingDay / 2;
            final String text = calendarAdapter.getDayOfWeekText(i);
            canvas.drawText(text, x, y, dayOfWeekPaint);
        }
    }

    private void drawDayOfMonth(Canvas canvas) {
        final float paddingDay = (getWidth() - getPaddingRight() - getPaddingLeft()) / 7;
        int y = 0;
        if (calendarController.MonthTitleEnabled) {
            y += monthRowHeight();
        }
        if (calendarController.WeekEnabled) {
            y += dayOfWeekRowHeight();
        }
        y += dayOfMonthRowHeight() / 2 + getPaddingTop();
        int day = 1;
        final int daysInMonth = CalendarUtil.getDaysInMonth(calendar);
        final float radius = paddingDay > dayOfMonthRowHeight() ? dayOfMonthRowHeight() / 2 : paddingDay / 2;
        while (day <= daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day);
            final int i = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            final float x = paddingDay * i + getPaddingRight() + paddingDay / 2;
            final Date selectedBeginDate = calendarController.selectedBeginDate;
            final Date selectedLastDate = calendarController.selectedLastDate;
            boolean selectedBegin = false;
            boolean selectedLast = false;
            boolean isBetween = false;

            if (CalendarUtil.equal(selectedBeginDate, calendar.getTime())) {
                selectedBegin = true;
                final RectF ova = new RectF(x - radius, y - radius, x + radius, y + radius);
                if (linkSelectedDays && selectedLastDate != null) {
                    canvas.drawRect(x, y - dayOfMonthRowHeight() / 2, x + paddingDay / 2, y + dayOfMonthRowHeight() / 2, selectedCirclePaint);
                    canvas.drawArc(ova, 90, 180, selectedDayShape == 0, selectedCirclePaint);
                } else {
                    if(selectedLastDate != null) {
                        canvas.drawRect(x, y - dayOfMonthRowHeight() / 2, x + paddingDay / 2, y + dayOfMonthRowHeight() / 2, linkselectedPaint);
                    };
                    canvas.drawArc(ova, 0, 360, selectedDayShape == 0, selectedCirclePaint);
                }
            } else {
                selectedBegin = false;
            }
            //
            if (CalendarUtil.equal(selectedLastDate, calendar.getTime())) {
                selectedLast = true;
                final RectF ova = new RectF(x - radius, y - radius, x + radius, y + radius);
                if (linkSelectedDays && selectedBeginDate != null) {
                    canvas.drawArc(ova, 270, 180, selectedDayShape == 0, secondSelectedCirclePaint);
                    canvas.drawRect(x - paddingDay / 2, y - dayOfMonthRowHeight() / 2, x, y + dayOfMonthRowHeight() / 2, secondSelectedCirclePaint);
                } else {
                    if(selectedBeginDate != null){
                        canvas.drawRect(x - paddingDay / 2, y - dayOfMonthRowHeight() / 2, x, y + dayOfMonthRowHeight() / 2, linkselectedPaint);
                    }
                    canvas.drawArc(ova, 0, 360, selectedDayShape == 0, secondSelectedCirclePaint);
                }
            } else {
                selectedLast = false;
            }
            //
            if (CalendarUtil.isBafterA(selectedBeginDate, calendar.getTime()) && CalendarUtil.isBafterA(calendar.getTime(), selectedLastDate)) {
                canvas.drawRect(x - paddingDay / 2, y - dayOfMonthRowHeight() / 2, x + paddingDay / 2, y + dayOfMonthRowHeight() / 2, linkselectedPaint);
                isBetween = true;
            }
            if (CalendarUtil.equal(calendar.getTime(), calendarController.toDay)) {
                final RectF ova = new RectF(x - radius, y - radius, x + radius, y + radius);
                canvas.drawArc(ova, 0, 360, selectedDayShape == 0, toDayBackgroundPaint);
            }
            //
            if (selectedBegin) {
                dayOfMonthPaint.setColor(selectedTextColor);
            } else if (selectedLast) {
                dayOfMonthPaint.setColor(secondSelectedTextColor);
            } else if (isBetween) {
                dayOfMonthPaint.setColor(betweenTextColor);
            } else {
                dayOfMonthPaint.setColor(unselectedTextColor);
            }
            final String text = calendarAdapter.getDayOfMonthText(day);
            if (CalendarUtil.equal(calendar.getTime(), calendarController.toDay) && !selectedBegin && !selectedLast) {
                canvas.drawText(text, x, y + dayOfMonthPaintSize / 2, toDayPaint);
            } else {
                canvas.drawText(text, x, y + dayOfMonthPaintSize / 2, dayOfMonthPaint);
            }
            //
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                y += dayOfMonthRowHeight();
            }
            day++;
        }
    }

    private int getDayFromLocation(float x, float y) {
        int defy = 0;
        if (calendarController.MonthTitleEnabled) {
            defy += monthRowHeight();
        }
        if (calendarController.WeekEnabled) {
            defy += dayOfWeekRowHeight();
        }
        defy += dayOfMonthRowHeight() / 2 + getPaddingTop();
        final int yDay = (int) (y - defy) / dayOfMonthRowHeight();
        final int paddingDay = (getWidth() - getPaddingRight() - getPaddingLeft()) / 7;
        final int day = 1 + (int) ((x - getPaddingLeft()) / paddingDay - getDayOffset() + yDay * 7);
        return day;
    }

    private int getDayOffset() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, thisYear);
        calendar.set(Calendar.MONTH, thisMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
    }
}
