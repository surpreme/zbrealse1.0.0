package com.aite.a.view;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {
	private static final String TAG = "MyViewPager";
	private boolean result = false;

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (result)
			return super.onInterceptTouchEvent(arg0);
		else
			return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (result)
			return super.onTouchEvent(arg0);
		else
			return false;
	}
}
