package com.aite.a.activity;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseFargmentActivity;
import com.aite.a.fargment.FavoriteFargment;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 收藏列表
 *
 * @author CAOYOU
 */
public class FavoriteListFargmentActivity extends BaseFargmentActivity {
    private ImageView roller; // 动画滚动图片
    private int imageWidth;
    private int offset = 0; // 图片偏移量
    private int currentIndex = 0; // 当前页卡编号
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList; // 装载显示内容
    private TextView goods_tx;
    private TextView store_tx;
    private ImageView iv_back;
    private ImageView iv_right;
    private TextView tv_name;
    private TextView _tx_right;
    private FavoriteFargment goodsFargment;
    private FavoriteFargment storeFargment;

    public ArrayList<Fragment> getFragmentList() {
        return fragmentList;
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.favorite_list_activity);
        findViewById();
        initView();
        initCursor(2);
    }

    protected void findViewById() {
        iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_back.setOnClickListener(new MyOnclickListener());
        iv_right = (ImageView) findViewById(R.id._iv_right);
        iv_right.setOnClickListener(new MyOnclickListener());
        _tx_right = (TextView) findViewById(R.id._tx_right);
        // _tx_right.setVisibility(View.VISIBLE);
        _tx_right.setText(getI18n(R.string.edit));
        _tx_right.setTextColor(getResources().getColor(R.color.white));
        _tx_right.setOnClickListener(new MyOnclickListener());
        tv_name = (TextView) findViewById(R.id._tv_name);
        tv_name.setText(getI18n(R.string.collection_list));
        roller = (ImageView) findViewById(R.id.goods_list_iv_cursor);
        goods_tx = (TextView) findViewById(R.id.goods_tx);
        store_tx = (TextView) findViewById(R.id.store_tx);
        viewPager = (ViewPager) findViewById(R.id.favorite_list_viewpager);
        goods_tx.setOnClickListener(new MyOnclickListener(0));
        store_tx.setOnClickListener(new MyOnclickListener(1));

    }

    @SuppressWarnings("static-access")
    private void initView() {
        fragmentList = new ArrayList<Fragment>();
        viewPager = (ViewPager) findViewById(R.id.favorite_list_viewpager);
        goodsFargment = new FavoriteFargment().newInstance(1);
        storeFargment = new FavoriteFargment().newInstance(2);
        fragmentList.add(goodsFargment);
        fragmentList.add(storeFargment);
    }

    /**
     * 根据tagd的数量初始化游标的位置
     *
     * @param tagNum
     */

    public void initCursor(int tagNum) {
        imageWidth = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        offset = (displayMetrics.widthPixels / tagNum - imageWidth) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        roller.setImageMatrix(matrix);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(new PageChangeListener());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getInt("i", 0) == 1) {
                viewPager.setCurrentItem(0);
            } else {
                viewPager.setCurrentItem(1);
            }
        } else {
            viewPager.setCurrentItem(0);
        }

    }

    class PageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + imageWidth; // 一个页卡占的偏移量

        @Override
        public void onPageSelected(int position) {
            Animation animation = new TranslateAnimation(one * currentIndex, one * position, 0, 0);
            currentIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(300);
            roller.startAnimation(animation);
            switch (position) {
                case 0:
                    goods_tx.setTextColor(getResources().getColor(R.color.cursor_text));
                    store_tx.setTextColor(getResources().getColor(R.color.black));
                    break;
                case 1:
                    goods_tx.setTextColor(getResources().getColor(R.color.black));
                    store_tx.setTextColor(getResources().getColor(R.color.cursor_text));
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // 当前页面被滑动时调用
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // 当前状态改变时调用
        }
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> list;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm); // 必须调用
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }

    }

    private boolean is_compile;

    class MyOnclickListener implements View.OnClickListener {
        int index = 0;

        public MyOnclickListener() {
            super();
        }

        public MyOnclickListener(int i) {
            this.index = i;
        }

        @Override
        public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.store_tx:
//				viewPager.setCurrentItem(index);
//				break;
//			case R.id.goods_tx:
//				viewPager.setCurrentItem(index);
//				break;
//			case R.id._iv_back:
//				finish();
//				break;
//			case R.id._tx_right:
//				goodsFargment.goodsAdapter.isShow();
//				break;
//			}
            if (v.getId() == R.id.store_tx) {
                viewPager.setCurrentItem(index);
            } else if (v.getId() == R.id.goods_tx) {
                viewPager.setCurrentItem(index);
            } else if (v.getId() == R.id._iv_back) {
                finish();
            } else if (v.getId() == R.id._tx_right) {
                goodsFargment.goodsAdapter.isShow();
            }
        }
    }

}
