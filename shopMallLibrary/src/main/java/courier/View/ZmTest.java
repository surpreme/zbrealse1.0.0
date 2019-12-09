package courier.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 字符
 * Created by Administrator on 2017/12/6.
 */
public class ZmTest extends View {
    private int width, height, gd, txxtw, index = 0;
    private Paint paint;
    private String[] data = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};

    public ZmTest(Context context) {
        super(context);
        init();
    }

    public ZmTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZmTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        gd = height - (height / 52);//高度差
        txxtw = (width - 12) / 2;//字体位置
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);// 颜色
        paint.setStrokeWidth(1); // 设置线宽
        paint.setAntiAlias(true);// 设置是否使用抗锯齿功能
        paint.setDither(true);// 设定是否使用图像抖动处理
        paint.setStyle(Paint.Style.FILL);// 设置风格 实心或者空心
        paint.setTextSize(24);//px为单位
    }

    private int bg = 0x00ffffff;//按压时背景

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < data.length; i++) {
            canvas.drawText(data[i], txxtw, ((gd / 27) * (i + 1)), paint);
        }
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL);//充满
        p.setColor(bg);
        p.setAntiAlias(true);// 设置画笔的锯齿效果
        RectF oval3 = new RectF(0, 0, width, height);
        canvas.drawRoundRect(oval3, 10, 10, p);//第二个参数是x半径，第三个参数是y半径
    }

    int lastindex = -1;//上一次的坐标

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            bg = 0x55000000;
            int v = (int) event.getY() / (gd / 27);
            if (mmenu != null&&v<27) {
                mmenu.onItemClick(data[v]);
            }
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int v = (int) event.getY() / (gd / 27);
            index = v;//当前坐标
            if (lastindex != index) {
                if (mmenu != null&&v<27) {
                    mmenu.onItemClick(data[v]);
                    lastindex = v;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            bg = 0x00ffffff;
            lastindex = -1;
            index = 0;
            mmenu.onItemClick("");
            invalidate();
        }
        return true;
    }

    private menu mmenu = null;

    public void setmenu(menu p) {
        mmenu = p;
    }

    public interface menu {
        void onItemClick(String id);
    }

}
