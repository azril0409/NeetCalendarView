package com.neetoffice.library.neetcalendarview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Deo-chainmeans on 2016/7/31.
 */
public abstract class MonthTitleInsert {
    public MonthTitleInsert() {
    }

    public abstract void drawMonthTitle(View child, Canvas canvas, float width, float height, Paint paint, int year, int month);

    public final float countStringWidth(@NonNull String text, @NonNull Paint paint) {
        final Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    public final float countStringHeight(@NonNull String text, @NonNull Paint paint) {
        final Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }
}
