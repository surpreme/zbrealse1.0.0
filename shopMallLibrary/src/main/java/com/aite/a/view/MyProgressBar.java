package com.aite.a.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 条形进度
 * 
 * @author Administrator
 *
 */
public class MyProgressBar extends View {
	private Paint mPaint;
	int kuandu, gaodu, progresss = 10;

	public MyProgressBar(Context context) {
		this(context, null);
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		kuandu = MeasureSpec.getSize(widthMeasureSpec);
		gaodu = MeasureSpec.getSize(heightMeasureSpec);
	}

	/**
	 * 修改进度
	 * 
	 * @param progress
	 */
	public void setProgress(float progress) {
		progresss = (int) (kuandu * (progress / 5));
//		System.out.println("-------------------原始进度   "+progress+"  计算后  "+progresss+"   宽度 "+kuandu);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(0xffD8D8D8);
		mPaint.setStrokeWidth(10);
		mPaint.setStrokeCap(Cap.ROUND);
		canvas.drawLine(10, gaodu / 2, kuandu - 10, gaodu / 2, mPaint);// 画背景
		mPaint.setColor(0xff0092DD);
		canvas.drawLine(10, gaodu / 2, progresss-10, gaodu / 2, mPaint);// 画线
	}
}
