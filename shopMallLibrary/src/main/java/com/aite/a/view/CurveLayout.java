package com.aite.a.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 个人中心曲线
 * Created by Administrator on 2017/5/28.
 */
public class CurveLayout extends View{
    private Paint p;
    private int width = 0;
    private int height = 0;

    public CurveLayout(Context context) {
        super(context);
    }

    public CurveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p = new Paint();
        p.setColor(Color.WHITE);// 颜色
        p.setStrokeWidth(1); // 设置线宽
        p.setAntiAlias(true);// 设置是否使用抗锯齿功能
        p.setDither(true);// 设定是否使用图像抖动处理
        p.setStyle(Paint.Style.FILL);// 设置风格 实心或者空心
        Path path2=new Path();
        path2.moveTo(0, 0);//设置Path的起点
        path2.quadTo(width/2, (int)(height*0.15), width, 0); //设置贝塞尔曲线的控制点坐标和终点坐标

        path2.quadTo(width, height/2, width, height);

        path2.quadTo(width/2,height, 0, height);

        path2.quadTo(0, height/2, 0, 0);

        canvas.drawPath(path2, p);//画出贝塞尔曲线
    }
}
