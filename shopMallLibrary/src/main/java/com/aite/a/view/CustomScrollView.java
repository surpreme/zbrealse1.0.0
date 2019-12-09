package com.aite.a.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

/**
 * ScrollView
 * 
 * @author xiaoyu
 *
 */
public class CustomScrollView extends ScrollView {

	private View inner; // 孩子View
	private static final int DEFAULT_POSITION = -1;
	private float y = DEFAULT_POSITION;// 点击时y的坐标
	private Rect normal = new Rect();
	private OnScrollListener onScrollListener;
	// 用在用户手指离开MyScrollView，MyScrollView还在继续滑动，用来保存Y的距离
	private int lastScrollY;
	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 根据XML生成视图工作完成，该函数在生成视图的最后调用，在所有子视图添加完成之后， 即使子类覆盖了onFinishInflate
	 * 方法，也应该调用父类的方法， 使得该方法得以执行
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner == null) {
			return super.onTouchEvent(ev);
		} else {
			commOnTouchEvent(ev);
		}
		if (onScrollListener != null) {
			onScrollListener.onScroll(lastScrollY = this.getScrollY());
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			handler.sendMessageDelayed(handler.obtainMessage(), 5);
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			y = ev.getY();
			break;
		case MotionEvent.ACTION_UP:

			if (isNeedAnimation()) {
				animation();
			}

			y = DEFAULT_POSITION;
			break;

		/**
		 * 排除第一次移动计算，因为第一次无法得知y左边，在MotionEvent.ACTION_DOWN中获取不到，
		 * 因为此时是MyScrollView的Tocuh时间传递到了ListView的孩子item上面。所以从第二次开始计算
		 * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归零，之后记录准确了就正常执行
		 */
		case MotionEvent.ACTION_MOVE:
			float preY = y;
			float nowY = ev.getY();
			if (isDefaultPosition(y)) {
				preY = nowY;
			}
			int deltaY = (int) (preY - nowY);
			scrollBy(0, deltaY);
			y = nowY;
			// 当滚动到最上或者最下时就不会再滚动，这时移动布局
			if (isNeedMove()) {
				if (normal.isEmpty()) {
					// 保存正常的布局位置
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());

				}
				// 移动布局
				inner.layout(inner.getLeft(), inner.getTop() - deltaY,
						inner.getRight(), inner.getBottom() - deltaY);
			}
			break;

		default:
			break;
		}
	}

	// 开启动画移动

	public void animation() {
		// 开启移动动画
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 设置回到正常的布局位置
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);

		normal.setEmpty();

	}

	// 是否需要开启动画
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	// 是否需要移动布局
	public boolean isNeedMove() {

		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		if (scrollY == 0 || scrollY == offset) {
			return true;
		}
		return false;
	}

	// 检查是否处于默认位置
	private boolean isDefaultPosition(float position) {
		return position == DEFAULT_POSITION;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}

	// 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int scrollY = CustomScrollView.this.getScrollY();

			// 此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
			if (lastScrollY != scrollY) {
				lastScrollY = scrollY;
				handler.sendEmptyMessage(0);
			}
			if (onScrollListener != null) {
				onScrollListener.onScroll(scrollY);
			}

		};

	};

	// 滚动的回调接口
	public interface OnScrollListener {
		// 返回滑动的Y的距离
		public void onScroll(int scrollY);
	}

	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}
}
