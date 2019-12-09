package com.aite.a.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

import com.aite.a.AppManager;
import com.aite.a.HomeTabActivity;
import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 向导界面
 * 
 * @author xiaoyu 只运行一次
 */
public class GuideActivity extends BaseActivity implements OnGestureListener,
		OnTouchListener {
	/**
	 * ViewPager展示引导页内容
	 */
	private ViewPager mPager;
	private View mPage1, mPage2, mPage3, mPage4,mPage5;
	private Button btn;
	private ImageView circular_01,circular_02,circular_03,circular_04,circular_05;
	/**
	 * 存放显示内容的View
	 */

	private List<View> mViews = new ArrayList<View>();
	// 记录是否显示向导界面
	private SharedPreferences sp;
	private Editor editor;
	private int pager_num;
	private int total_page = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_activity);
		

		mPage1 = LayoutInflater.from(this).inflate(
				R.layout.splash_activity_page1, null);
		mPage2 = LayoutInflater.from(this).inflate(
				R.layout.splash_activity_page2, null);
		mPage3 = LayoutInflater.from(this).inflate(
				R.layout.splash_activity_page3, null);
		mPage4 = LayoutInflater.from(this).inflate(
				R.layout.splash_activity_page4, null);
		mPage5 = LayoutInflater.from(this).inflate(
				R.layout.splash_activity_page5, null);
		findViewById();
		mViews.add(mPage1);
		mViews.add(mPage2);
		mViews.add(mPage3);
		mViews.add(mPage4);
		mViews.add(mPage5);
		mPager.setAdapter(new ViewPagerAdapter());
		//第五页按钮
		//TextView tv_starttt=(TextView) mPage5.findViewById(R.id.tv_starttt);
		mPage5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AppManager.getInstance().killAllActivity();
				Intent intent = new Intent(GuideActivity.this,
						HomeTabActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				editor.putBoolean("guide", false);
				editor.commit();
				finish();
			}
		});
	}

	@Override
	protected void findViewById() {
		mPager = (ViewPager) findViewById(R.id.guide_activity_viewpager);

		circular_01=(ImageView) findViewById(R.id.circular_01);
		circular_02=(ImageView) findViewById(R.id.circular_02);
		circular_03=(ImageView) findViewById(R.id.circular_03);
		circular_04=(ImageView) findViewById(R.id.circular_04);
		circular_05=(ImageView) findViewById(R.id.circular_05);
		
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				pager_num = position + 1;
				switch(position){
				case 0:
					circular_01.setImageDrawable(getResources().getDrawable(R.drawable.circular_selected));
					circular_02.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_03.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_04.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_05.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					break;
				case 1:
					circular_01.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_02.setImageDrawable(getResources().getDrawable(R.drawable.circular_selected));
					circular_03.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_04.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_05.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					break;
				case 2:
					circular_01.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_02.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_03.setImageDrawable(getResources().getDrawable(R.drawable.circular_selected));
					circular_04.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_05.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					break;
				case 3:
					circular_01.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_02.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_03.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_04.setImageDrawable(getResources().getDrawable(R.drawable.circular_selected));
					circular_05.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					break;
				case 4:
					circular_01.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_02.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_03.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_04.setImageDrawable(getResources().getDrawable(R.drawable.circular_normal));
					circular_05.setImageDrawable(getResources().getDrawable(R.drawable.circular_selected));
					break;
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});

		mPager.setOnTouchListener(this);
		initView();
	}
	
	@Override
	protected void initView() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		editor = sp.edit();
	}

	GestureDetector mygesture = new GestureDetector(this);

	/**
	 * ViewPager适配器
	 * 
	 * @author xiaoyu
	 * 
	 */
	private class ViewPagerAdapter extends PagerAdapter {

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mViews.get(arg1));
		}

		public void finishUpdate(View arg0) {

		}

		public int getCount() {

			return mViews.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mViews.get(arg1));
			return mViews.get(arg1);

		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {

		}

	}

	public boolean onTouch(View v, MotionEvent event) {
		return mygesture.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}
	
	private void setCircularBGColor(){
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > 120) {
			if (pager_num == 5) {
				AppManager.getInstance().killAllActivity();
				Intent intent = new Intent(GuideActivity.this,
						HomeTabActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				editor.putBoolean("guide", false);
				editor.commit();
				finish();
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	protected void initData() {
		startActivity(new Intent(this, SplashActivity.class));
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
		editor.putBoolean("update", false);
		editor.commit();
		GuideActivity.this.finish();
	}

	@Override
	public void ReGetData() {
		initData();
	}

}
