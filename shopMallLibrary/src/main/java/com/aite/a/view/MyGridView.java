package com.aite.a.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;

import com.aiteshangcheng.a.R;

public class MyGridView extends GridView {

	private boolean needDispachEvent;


	public MyGridView(Context context) {
		this(context,null);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		needDispachEvent = attrs.getAttributeBooleanValue(R.styleable.MyGridView_needDispachEvent, true);
	}
	/**
	 * 设置不滚动
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.i("---------DisparchEvent", " " + needDispachEvent);
		return  super.dispatchTouchEvent(ev);
	}
/*	@Override
	public boolean onTouchEvent(MotionEvent ev) {
//		return super.onTouchEvent(ev);
		return false ;
	}*/

}
