package com.aite.a.view;

import com.aiteshangcheng.a.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
/**
 * 时光轴
 * @author Administrator
 *
 */
public class MyShiGuangZhou extends View {
	private Paint paint;
	private Context mcontext;
	int number, progress,radiuss, kuandu, gaodu;

	public MyShiGuangZhou(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyShiGuangZhou(Context context) {
		super(context);
	}

	public MyShiGuangZhou(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		TypedArray obtainStyledAttributes = context.obtainStyledAttributes(
				attrs, R.styleable.RoundImageView);
		if (obtainStyledAttributes != null) {
			number = obtainStyledAttributes.getInt(
					R.styleable.RoundImageView_number, 3);
			progress = obtainStyledAttributes.getInt(
					R.styleable.RoundImageView_progress, 1);
			radiuss = obtainStyledAttributes.getInt(R.styleable.RoundImageView_radiuss, 20);
		}
		System.out.println("----------------------" + number + "    "
				+ progress + "    "+radiuss+"    " + kuandu + "    " + gaodu);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		kuandu = MeasureSpec.getSize(widthMeasureSpec);
		gaodu = MeasureSpec.getSize(heightMeasureSpec);
	}
	/**
	 * 更改总数量
	 * @param Number
	 */
	public void setNumber(int Number){
		number=Number;
		invalidate();
	}
	
	/**
	 * 更改进度
	 * @param Progress
	 */
	public void setProgress(int Progress){
		progress=Progress;
		invalidate();
	}
	/**
	 * 更改半径
	 * @param Radiuss
	 */
	public void setRadiuss(int Radiuss){
		radiuss=Radiuss;
		invalidate();
	}
	/**
	 * 获得进度
	 * @return
	 */
	public int getProgress(){
		return progress;
	}
	
	
	private void init(Context context) {
		paint = new Paint();
		mcontext = context;
		paint.setColor(0XFFE7E7E7);// 颜色
		paint.setStrokeWidth(7); // 设置线宽
		paint.setAntiAlias(true);// 设置是否使用抗锯齿功能
		paint.setDither(true);// 设定是否使用图像抖动处理
		paint.setStyle(Style.FILL);// 设置风格 实心或者空心
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		HY(canvas);
		
	}

	private void HY(Canvas canvas) {
		int lastx=0;
		int x= kuandu/number;
		//背景横线
		canvas.drawRect(x/2, (gaodu/2)-5, (x*number)-(x/2), (gaodu/2)+5, paint);
		paint.setColor(0XFFFD4646);// 颜色
		//进度横线
		canvas.drawRect(x/2, (gaodu/2)-2, (x*progress)-(x/2), (gaodu/2)+2, paint);
		paint.setColor(0XFFE7E7E7);// 颜色
		//背景圆
		for (int i = number; i > 0; i--) {
			canvas.drawCircle((x/2)+lastx, gaodu/2, radiuss, paint);
			lastx+=x;
		}
		//获得图片
		Bitmap  bitmap = BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.whenopticalaxis_xz);
		bitmap = Bitmap.createScaledBitmap(bitmap, radiuss*2, radiuss*2, true);
		//画选中图片
		for (int i = progress; i > 0; i--) {
			canvas.drawBitmap(bitmap, null, new Rect((x*i)-(x/2)-radiuss, (gaodu/2)-radiuss,(x*i)-(x/2)+radiuss, (gaodu/2)+radiuss),null);
		}
//		canvas.drawCircle((x*progress)-(x/2), gaodu/2, 20, paint);
	}
}
