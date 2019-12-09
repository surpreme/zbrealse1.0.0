package courier.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 总收入饼状图
 *
 * Created by Administrator on 2018/1/11.
 */
public class PieChartView extends View{
    private Paint paint;
    private int wide=0,high=0;
    private int jsstart=90,jsend=360;//已结算角度
    private int txstart=-90,txend=0;//可提现角度
    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        wide=MeasureSpec.getSize(widthMeasureSpec);
        high=MeasureSpec.getSize(heightMeasureSpec);
    }

    /**
     * 修改数据
     * @param str1 已结算
     * @param str2 未结算
     */
    public void setData(String str1,String str2){
        if (str1==null||str2==null)return;
        float yj = Float.parseFloat(str1);
        float wj = Float.parseFloat(str2);
        float sum=yj+wj;
        txend= (int) ((wj/sum)*360);
        invalidate();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0XFF4BAFFA);// 颜色
        paint.setStrokeWidth(1); // 设置线宽
        paint.setAntiAlias(true);// 设置是否使用抗锯齿功能
        paint.setDither(true);// 设定是否使用图像抖动处理
        paint.setStyle(Paint.Style.FILL);// 设置风格 实心或者空心
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(0XFF4BAFFA);
        RectF ova = new RectF(0, 0, wide, high);
        canvas.drawArc(ova,jsstart,jsend,true,paint);
        paint.setColor(0XFFFFB325);
        canvas.drawArc(ova,txstart,txend,true,paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(wide/2,high/2,wide/5,paint);
    }
}
