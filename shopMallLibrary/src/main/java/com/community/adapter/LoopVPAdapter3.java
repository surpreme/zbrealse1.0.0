package com.community.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.aite.a.base.Mark;
import com.community.utils.DefaultTransformer;
import com.community.utils.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Created by mayn on 2018/6/5.
 */

public abstract class LoopVPAdapter3 extends PagerAdapter implements ViewPager.OnPageChangeListener, Mark {
    //    当前页面
    private int currentPosition = 0;
    protected Context mContext;
    public Timer timer;
    protected ArrayList<View> views;
    protected ViewPager mViewPager;
    private boolean isLoop = true;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RAISEFUNDSHOME_UPDATE_IMGINDEX://切换顶部图
                    mViewPager.setCurrentItem(currentPosition + 1);
                    break;
            }
        }
    };

    /**
     * 头条
     *
     * @param context
     * @param datas         数据
     * @param viewPager
     * @param ScrollerSpeed 滚动速度，时间越长，速度越慢
     * @param period        滚动间隔
     */
    public LoopVPAdapter3(Context context, List<String> datas, ViewPager viewPager, int ScrollerSpeed, int period) {
        mContext = context;
        views = new ArrayList<>();
        List<Boolean> indexdata = new ArrayList<>();
//        如果数据大于一条
        if (datas.size() > 1) {
            //设置滚动速度
            ViewPagerScroller pagerScroller = new ViewPagerScroller(mContext);
            pagerScroller.setScrollDuration(ScrollerSpeed);
            pagerScroller.initViewPagerScroll(viewPager);

            //添加最后一页到第一页
            datas.add(0, datas.get(datas.size() - 1));
            //添加第一页(经过上行的添加已经是第二页了)到最后一页
            datas.add(datas.get(1));

            viewPager.setOnTouchListener(touch);//触摸监听

            //设置切换动画 ZoomOutPageTransformer 或 DepthPageTransformer
//            viewPager.setPageTransformer(true, new DepthPageTransformer());

            viewPager.setPageTransformer(true, new DefaultTransformer());
            viewPager.setOverScrollMode(OVER_SCROLL_NEVER);
            if (timer==null){
                timer = new Timer();
                timer.schedule(new TimerTaskTest(), period, period);//开始滚动
            }
        }

        for (String data : datas) {
            views.add(getItemView(data));
        }
        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1, false);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    protected abstract View getItemView(String data);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) return;
//        若当前为第一张，设置页面为倒数第二张
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(views.size() - 2, false);
        } else if (currentPosition == views.size() - 1) {
//        若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1, false);
        }
    }

    class TimerTaskTest extends TimerTask {//定时任务

        @Override
        public void run() {
            if (!isLoop) return;
            mhandler.sendEmptyMessage(RAISEFUNDSHOME_UPDATE_IMGINDEX);
        }
    }

    private View.OnTouchListener touch = new View.OnTouchListener() {//按压时不滚动
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isLoop = false;
                    break;
                case MotionEvent.ACTION_UP:
                    isLoop = true;
                    break;
            }
            return false;
        }
    };

}