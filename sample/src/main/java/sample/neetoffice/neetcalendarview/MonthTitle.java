package sample.neetoffice.neetcalendarview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.neetoffice.library.neetcalendarview.MonthTitleInsert;

/**
 * Created by Deo-chainmeans on 2016/7/31.
 */
public class MonthTitle extends MonthTitleInsert {
    @Override
    public void drawMonthTitle(View child, Canvas canvas, float width, float height, Paint paint, int year, int month) {
        paint.setTextAlign(Paint.Align.LEFT);
        final Paint yearPaint = new Paint(paint);
        yearPaint.setTextSize(paint.getTextSize() / 2);
        final String monthWorld = (month + 1) + "月";
        final String yearWorld = String.valueOf(year);
        final float montWidth = countStringWidth(monthWorld, paint);
        canvas.drawText(monthWorld, child.getPaddingLeft(), height, paint);
        canvas.drawText(yearWorld, child.getPaddingLeft() + montWidth + 10, height, yearPaint);
    }
}
