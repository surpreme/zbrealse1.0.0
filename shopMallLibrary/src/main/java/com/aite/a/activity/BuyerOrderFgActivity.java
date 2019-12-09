package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseFargmentActivity;
import com.aite.a.fargment.OrderFargment;
import com.aite.a.model.OrderGroupList;
import com.aite.a.utils.CommonTools;

/**
 * 买家订单界面
 *
 * @author CAOYOU
 */
public class BuyerOrderFgActivity extends BaseFargmentActivity implements
        OnClickListener {
    private static final int TAB_COUNT = 5;
    private ImageView iv_back, iv_right;
    private TextView tv_name;// 标题名字
    public List<OrderGroupList> groupLists = new ArrayList<OrderGroupList>();
    private TextView order_yes_cancel;
    private TextView order_no_pay;
    private TextView order_yes_pay;
    private TextView order_shipped;
    private TextView order_finish;
    private View sliding;
    private int imageWidth;
    private int currentIndex = 0;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentList;
    private OrderFargment orderFargment1, orderFargment2, orderFargment3,
            orderFargment4, orderFargment5;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.personal_order);
        init();
    }

    int i;

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
        order_yes_cancel = (TextView) findViewById(R.id.order_yes_cancel);
        order_no_pay = (TextView) findViewById(R.id.order_no_pay);
        order_yes_pay = (TextView) findViewById(R.id.order_yes_pay);
        order_shipped = (TextView) findViewById(R.id.order_shipped);
        order_finish = (TextView) findViewById(R.id.order_finish);
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

    private void initView() {
        iv_back.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        tv_name.setText(getI18n(R.string.all_order));
        order_yes_cancel.setOnClickListener(this);
        order_no_pay.setOnClickListener(this);
        order_yes_pay.setOnClickListener(this);
        order_shipped.setOnClickListener(this);
        order_finish.setOnClickListener(this);
        fragmentList = new ArrayList<Fragment>();
        orderFargment1 = new OrderFargment();
        orderFargment2 = new OrderFargment();
        orderFargment3 = new OrderFargment();
        orderFargment4 = new OrderFargment();
        orderFargment5 = new OrderFargment();
        orderFargment1.setCurrentIndex(0);
        orderFargment2.setCurrentIndex(1);
        orderFargment3.setCurrentIndex(2);
        orderFargment4.setCurrentIndex(3);
        orderFargment5.setCurrentIndex(4);
        fragmentList.add(orderFargment1);
        fragmentList.add(orderFargment2);
        fragmentList.add(orderFargment3);
        fragmentList.add(orderFargment4);
        fragmentList.add(orderFargment5);
        initsliding();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.order_yes_cancel:
//                viewPager.setCurrentItem(0);
//                orderFargment1.setCurrentIndex(currentIndex);
//                break;
//            case R.id.order_no_pay:
//                viewPager.setCurrentItem(1);
//                orderFargment2.setCurrentIndex(currentIndex);
//                break;
//            case R.id.order_yes_pay:
//                viewPager.setCurrentItem(2);
//                orderFargment3.setCurrentIndex(currentIndex);
//                break;
//            case R.id.order_shipped:
//                viewPager.setCurrentItem(3);
//                orderFargment4.setCurrentIndex(currentIndex);
//                break;
//            case R.id.order_finish:
//                viewPager.setCurrentItem(4);
//                orderFargment5.setCurrentIndex(currentIndex);
//                break;
//            case R.id._iv_back:
//                finish();
//                overrPre();
//                break;
//            case R.id._iv_right:
//                CommonTools.showShortToast(this, getI18n(R.string.refresh));
//                break;
//        }

        if(v.getId()==R.id.order_yes_cancel){
            viewPager.setCurrentItem(0);
            orderFargment1.setCurrentIndex(currentIndex);
        }else if(v.getId()==R.id.order_no_pay){
            viewPager.setCurrentItem(1);
            orderFargment2.setCurrentIndex(currentIndex);
        }else if(v.getId()==R.id.order_yes_pay){
            viewPager.setCurrentItem(2);
            orderFargment3.setCurrentIndex(currentIndex);
        }else if(v.getId()==R.id.order_shipped){
            viewPager.setCurrentItem(3);
            orderFargment4.setCurrentIndex(currentIndex);
        }else if(v.getId()==R.id.order_finish){
            viewPager.setCurrentItem(4);
            orderFargment5.setCurrentIndex(currentIndex);
        }else if(v.getId()==R.id._iv_back){
            finish();
            overrPre();
        }else if(v.getId()==R.id._iv_right){
            CommonTools.showShortToast(this, getI18n(R.string.refresh));
        }

    }

    @Override
    public void initCursor(int tagNum) {
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

    LayoutParams Params;

    /**
     * 初始化滑块视图
     */
    private void initsliding() {
        Params = (LayoutParams) sliding.getLayoutParams();
        Params.width = getResources().getDisplayMetrics().widthPixels / 5;
        Params.height = LayoutParams.MATCH_PARENT;
        sliding.setLayoutParams(Params);
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
                    order_yes_cancel.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    order_no_pay.setTextColor(getResources()
                            .getColor(R.color.black));
                    order_yes_pay.setTextColor(getResources().getColor(
                            R.color.black));
                    order_shipped.setTextColor(getResources().getColor(
                            R.color.black));
                    order_finish.setTextColor(getResources()
                            .getColor(R.color.black));
                    break;
                case 1:
                    orderFargment2.setCurrentIndex(position);
                    order_yes_cancel.setTextColor(getResources().getColor(
                            R.color.black));
                    order_no_pay.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    order_yes_pay.setTextColor(getResources().getColor(
                            R.color.black));
                    order_shipped.setTextColor(getResources().getColor(
                            R.color.black));
                    order_finish.setTextColor(getResources()
                            .getColor(R.color.black));
                    break;
                case 2:
                    orderFargment3.setCurrentIndex(position);
                    order_yes_cancel.setTextColor(getResources().getColor(
                            R.color.black));
                    order_no_pay.setTextColor(getResources()
                            .getColor(R.color.black));
                    order_yes_pay.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    order_shipped.setTextColor(getResources().getColor(
                            R.color.black));
                    order_finish.setTextColor(getResources()
                            .getColor(R.color.black));
                    break;
                case 3:
                    orderFargment4.setCurrentIndex(position);
                    order_yes_cancel.setTextColor(getResources().getColor(
                            R.color.black));
                    order_no_pay.setTextColor(getResources()
                            .getColor(R.color.black));
                    order_yes_pay.setTextColor(getResources().getColor(
                            R.color.black));
                    order_shipped.setTextColor(getResources().getColor(
                            R.color.cursor_text));
                    order_finish.setTextColor(getResources()
                            .getColor(R.color.black));
                    break;
                case 4:
                    orderFargment5.setCurrentIndex(position);
                    order_yes_cancel.setTextColor(getResources().getColor(
                            R.color.black));
                    order_no_pay.setTextColor(getResources()
                            .getColor(R.color.black));
                    order_yes_pay.setTextColor(getResources().getColor(
                            R.color.black));
                    order_shipped.setTextColor(getResources().getColor(
                            R.color.black));
                    order_finish.setTextColor(getResources().getColor(
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
        public void onPageScrollStateChanged(int arg0) {
            // 当前状态改变时调用
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
