package ru.irfr.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class SwipeableViewPager extends ViewPager {

    private boolean swipe;

    public SwipeableViewPager(Context context) {
        super(context);
    }

    public SwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipe(boolean swipe) {
        this.swipe = swipe;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swipe && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swipe && super.onTouchEvent(event);
    }
}