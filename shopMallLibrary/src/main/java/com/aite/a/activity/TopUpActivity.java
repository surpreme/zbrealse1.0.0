package com.aite.a.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.base.BaseFargmentActivity;
import com.aite.a.fargment.OnlineTopUpFragment;
import com.aite.a.fargment.TopUpDetailFragment;
import com.aite.a.utils.CommonTools;
import com.aiteshangcheng.a.R;

import java.util.ArrayList;

/**
 * @author:TQX
 * @Date: 2019/10/25
 * @description:
 */
public class TopUpActivity extends BaseFargmentActivity implements View.OnClickListener {
    private ImageView iv_back, iv_right;
    private TextView tv_name;// 标题名字
    private ViewPager viewPager;
    private TextView online_top_up;
    private TextView top_up_detail;
    private TextView card_balance;
    private TextView card_top_up;
    private View sliding;
    private int imageWidth;
    private static final int TAB_COUNT = 4;
    private ArrayList<Fragment> fragmentList;
    private int currentIndex = 0;
    private OnlineTopUpFragment orderFargment1, orderFargment4;
    private TopUpDetailFragment orderFargment2, orderFargment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        init();

    }

    private void init() {
        findViewById();
        initView();
        initCursor(TAB_COUNT);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            i = bundle.getInt("viewPager");
            viewPager.setCurrentItem(i);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    protected void findViewById() {
        iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_right = (ImageView) findViewById(R.id._iv_right);
        tv_name = (TextView) findViewById(R.id._tv_name);
        cursor = (ImageView) findViewById(R.id.goods_evaluation_iv_cursor);
        viewPager = (ViewPager) findViewById(R.id.personal_order_viewpager);
        online_top_up = (TextView) findViewById(R.id.online_top_up);
        top_up_detail = (TextView) findViewById(R.id.top_up_detail);
        card_balance = (TextView) findViewById(R.id.card_balance);
        card_top_up = (TextView) findViewById(R.id.card_top_up);
        sliding = findViewById(R.id.sliding);
        // 初始化滑块位置
        sliding.post(new Runnable() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Params.leftMargin = (int) (sliding.getWidth() * (i));
                        sliding.setLayoutParams(Params);
                    }
                });
            }
        });
    }

    int i;
    LinearLayout.LayoutParams Params;

    /**
     * 初始化滑块视图
     */
    private void initsliding() {
        Params = (LinearLayout.LayoutParams) sliding.getLayoutParams();
        Params.width = getResources().getDisplayMetrics().widthPixels / 4;
        Params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        sliding.setLayoutParams(Params);
    }


    private void initView() {
        iv_back.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_name.setText("在线充值");
        online_top_up.setOnClickListener(this);
        top_up_detail.setOnClickListener(this);
        card_balance.setOnClickListener(this);
        card_top_up.setOnClickListener(this);
        fragmentList = new ArrayList<Fragment>();
        orderFargment1 = new OnlineTopUpFragment(1);
        orderFargment2 = new TopUpDetailFragment(1);
        orderFargment3 = new TopUpDetailFragment(2);
        orderFargment4 = new OnlineTopUpFragment(2);
        orderFargment1.setCurrentIndex(0);
        orderFargment2.setCurrentIndex(1);
        orderFargment3.setCurrentIndex(2);
        orderFargment4.setCurrentIndex(3);
        fragmentList.add(orderFargment1);
        fragmentList.add(orderFargment2);
        fragmentList.add(orderFargment3);
        fragmentList.add(orderFargment4);
        initsliding();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.online_top_up) {
            viewPager.setCurrentItem(0);
            orderFargment1.setCurrentIndex(currentIndex);
        } else if (v.getId() == R.id.top_up_detail) {
            viewPager.setCurrentItem(1);
            orderFargment2.setCurrentIndex(currentIndex);
        } else if (v.getId() == R.id.card_balance) {
            viewPager.setCurrentItem(2);
            orderFargment3.setCurrentIndex(currentIndex);
        } else if (v.getId() == R.id.card_top_up) {
            viewPager.setCurrentItem(3);
            orderFargment4.setCurrentIndex(currentIndex);
        } else if (v.getId() == R.id._iv_back) {
            finish();     overrPre();

        }else if(v.getId()==R.id._iv_right){
            CommonTools.showShortToast(this, getI18n(R.string.refresh));
        }

//        switch (v.getId()) {
//            case R.id.online_top_up:
//                viewPager.setCurrentItem(0);
//                orderFargment1.setCurrentIndex(currentIndex);
//                break;
//            case R.id.top_up_detail:
//                viewPager.setCurrentItem(1);
//                orderFargment2.setCurrentIndex(currentIndex);
//                break;
//            case R.id.card_balance:
//                viewPager.setCurrentItem(2);
//                orderFargment3.setCurrentIndex(currentIndex);
//                break;
//            case R.id.card_top_up:
//                viewPager.setCurrentItem(3);
//                orderFargment4.setCurrentIndex(currentIndex);
//                break;
//            case R.id._iv_back:
//                finish();
//                overrPre();
//                break;
//            case R.id._iv_right:
//                CommonTools.showShortToast(this, getI18n(R.string.refresh));
//                break;
//        }
    }

    @Override
    protected void initCursor(int tagNum) {
        imageWidth = BitmapFactory.decodeResource(getResources(),
                R.drawable.cursor).getWidth();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();
        display.getMetrics(displayMetrics);
        offset = (displayMetrics.widthPixels / tagNum - imageWidth) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),
                fragmentList));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new PageChangeListener());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 滑动监听
     */
    public class PageChangeListener implements ViewPager.OnPageChangeListener {
        int one = offset * 2 + imageWidth; // 一个页卡占的偏移量


        @Override
        public void onPageSelected(int position) {
            Animation animation = new TranslateAnimation(one * currentIndex,
                    one * position, 0, 0);
            currentIndex = position;
            animation.setFillAfter(true);
            animation.setDuration(300);
            cursor.startAnimation(animation);
            switch (position) {
                case 0:
                    orderFargment1.setCurrentIndex(position);
                    online_top_up.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    top_up_detail.setTextColor(getResources()
                            .getColor(R.color.black));
                    card_balance.setTextColor(getResources().getColor(
                            R.color.black));
                    card_top_up.setTextColor(getResources().getColor(
                            R.color.black));
                    break;
                case 1:
                    orderFargment2.setCurrentIndex(position);
                    online_top_up.setTextColor(getResources().getColor(
                            R.color.black));
                    top_up_detail.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    card_balance.setTextColor(getResources().getColor(
                            R.color.black));
                    card_top_up.setTextColor(getResources().getColor(
                            R.color.black));
                    break;
                case 2:
                    orderFargment3.setCurrentIndex(position);
                    online_top_up.setTextColor(getResources().getColor(
                            R.color.black));
                    top_up_detail.setTextColor(getResources()
                            .getColor(R.color.black));
                    card_balance.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    card_top_up.setTextColor(getResources().getColor(
                            R.color.black));
                    break;
                case 3:
                    orderFargment4.setCurrentIndex(position);
                    online_top_up.setTextColor(getResources().getColor(
                            R.color.black));
                    top_up_detail.setTextColor(getResources()
                            .getColor(R.color.black));
                    card_balance.setTextColor(getResources().getColor(
                            R.color.black));
                    card_top_up.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    break;
            }
        }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // 当前页面被滑动时调用
            Params.leftMargin = (int) (sliding.getWidth() * (arg0 + arg1));
            sliding.setLayoutParams(Params);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    /**
     * 适配器
     *
     * @author xiaoyu
     */
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
}
