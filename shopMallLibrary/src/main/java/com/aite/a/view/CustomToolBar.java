package com.aite.a.view;


import com.aiteshangcheng.a.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 公共标题栏
 * 
 * @author 郭鹏辉
 * */
public class CustomToolBar extends LinearLayout {

	private Boolean isLeftBtnVisible;
	private int leftResId;

	private Boolean isRightBtnVisible;
	private int rightResId;

	private Boolean isRightTvVisible;
	private String rightTvText;

	private Boolean isTitleVisible;
	private String titleText;

	private int backgroundResId;

	public CustomToolBar(Context context) {
		this(context, null);
	}

	public CustomToolBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(attrs);
	}
	
	/**
	 * 初始化属性
	 * */
	private void initView(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
				R.styleable.CustomToolBar);
		/*-------------获取左边按钮属性------------*/
		isLeftBtnVisible = typedArray.getBoolean(
				R.styleable.CustomToolBar_left_btn_visible, false);
		leftResId = typedArray.getResourceId(
				R.styleable.CustomToolBar_left_btn_src, -1);
		/*-------------获取右边按钮属性------------*/
		isRightBtnVisible = typedArray.getBoolean(
				R.styleable.CustomToolBar_right_btn_visible, false);
		rightResId = typedArray.getResourceId(
				R.styleable.CustomToolBar_right_btn_src, -1);
		/*-------------获取右边文本属性------------*/
		isRightTvVisible = typedArray.getBoolean(
				R.styleable.CustomToolBar_right_tv_visible, false);
		if (typedArray.hasValue(R.styleable.CustomToolBar_right_tv_text)) {
			rightTvText = typedArray
					.getString(R.styleable.CustomToolBar_right_tv_text);
		}
		/*-------------获取标题属性------------*/
		isTitleVisible = typedArray.getBoolean(
				R.styleable.CustomToolBar_title_visible, false);
		if (typedArray.hasValue(R.styleable.CustomToolBar_title_text)) {
			titleText = typedArray
					.getString(R.styleable.CustomToolBar_title_text);
		}
		/*-------------背景颜色------------*/
		backgroundResId = typedArray.getResourceId(
				R.styleable.CustomToolBar_barBackground, -1);

		typedArray.recycle();
		/*-------------设置内容------------*/
		View barLayoutView = View.inflate(getContext(),
				R.layout.worker_common_toolbar, null);
		Button leftBtn = (Button) barLayoutView
				.findViewById(R.id.toolbar_left_btn);
		TextView titleTv = (TextView) barLayoutView
				.findViewById(R.id.toolbar_title);
		Button rightBtn = (Button) barLayoutView
				.findViewById(R.id.toolbar_right_btn);
		TextView rightTv = (TextView) barLayoutView
				.findViewById(R.id.toolbar_right_tv);
		RelativeLayout barRlyt = (RelativeLayout) barLayoutView
				.findViewById(R.id.toolbar_content_rlyt);
		if (isLeftBtnVisible) {
			leftBtn.setVisibility(VISIBLE);
		}
		if (isRightBtnVisible) {
			rightBtn.setVisibility(VISIBLE);
		}
		if (isRightTvVisible) {
			rightTv.setVisibility(VISIBLE);
		}
		if (isTitleVisible) {
			titleTv.setVisibility(VISIBLE);
		}
		rightTv.setText(rightTvText);
		titleTv.setText(titleText);
		if (leftResId != -1) {
			leftBtn.setBackgroundResource(leftResId);
		}
		if (rightResId != -1) {
			rightBtn.setBackgroundResource(rightResId);
		}
		if (backgroundResId != -1) {
			barRlyt.setBackgroundColor(getResources().getColor(backgroundResId));
		}

		// 为按钮添加点击事件
		leftBtn.setOnClickListener(clickListener);
		rightBtn.setOnClickListener(clickListener);
		rightTv.setOnClickListener(clickListener);

		// 将设置完成之后的View添加到此LinearLayout中
		addView(barLayoutView, 0);
	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(v.getId()==R.id.toolbar_left_btn){
				if (leftBtnClickListener != null) {
					leftBtnClickListener.onLeftBtnClick();
				}
			} else if(v.getId()==R.id.toolbar_right_btn){
				if (rightBtnClickListener != null) {
					rightBtnClickListener.onRightBtnClick();
				}
			}else if(v.getId()==R.id.toolbar_right_tv){
				if (rightBtnClickListener != null) {
					rightBtnClickListener.onRightBtnClick();
				}
			}

//			switch (v.getId()) {
//			case R.id.toolbar_left_btn:
//				if (leftBtnClickListener != null) {
//					leftBtnClickListener.onLeftBtnClick();
//				}
//				break;
//			case R.id.toolbar_right_btn:
//				if (rightBtnClickListener != null) {
//					rightBtnClickListener.onRightBtnClick();
//				}
//				break;
//			case R.id.toolbar_right_tv:
//				if (rightBtnClickListener != null) {
//					rightBtnClickListener.onRightBtnClick();
//				}
//				break;
//			default:
//				break;
//			}
		}
	};

	private onLeftBtnClickListener leftBtnClickListener;

	public interface onLeftBtnClickListener {
		void onLeftBtnClick();
	}

	public void setOnLeftBtnClickListener(
			onLeftBtnClickListener leftBtnClickListener) {
		this.leftBtnClickListener = leftBtnClickListener;
	}

	private onRightBtnClickListener rightBtnClickListener;

	public interface onRightBtnClickListener {
		void onRightBtnClick();
	}

	public void setOnRightBtnClickListener(
			onRightBtnClickListener rightBtnClickListener) {
		this.rightBtnClickListener = rightBtnClickListener;
	}
}
