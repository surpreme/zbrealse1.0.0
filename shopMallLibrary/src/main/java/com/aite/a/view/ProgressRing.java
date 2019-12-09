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
 * 环形进度
 * 
 * @author Administrator
 *
 */
public class ProgressRing extends View {
	private Paint mPaint;
	int kuandu, gaodu, angle = 0;

	public ProgressRing(Context context) {
		this(context, null);
	}

	public ProgressRing(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProgressRing(Context context, AttributeSet attrs, int defStyleAttr) {
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
		angle = (int) (360 * (progress / 5));
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(0xffD8D8D8);
		mPaint.setStrokeWidth(10);
		mPaint.setStrokeCap(Cap.ROUND);
		RectF oval = new RectF(12, 12, kuandu - 12, gaodu - 12);
		canvas.drawArc(oval, 360, -360, false, mPaint);
		mPaint.setColor(0xff0092DD);
		RectF oval2 = new RectF(12, 12, kuandu - 12, gaodu - 12);
		canvas.drawArc(oval2, -90, angle, false, mPaint);
	}
}
