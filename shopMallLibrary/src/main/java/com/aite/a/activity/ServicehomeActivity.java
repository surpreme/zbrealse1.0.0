package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.base.Mark;
import com.aite.a.fargment.Fragment_Hall;
import com.aite.a.fargment.Fragment_Mine;
import com.aite.a.fargment.Fragment_ServiceHome;
import com.aite.a.fargment.Fragment_Talent;
import com.aiteshangcheng.a.R;

//服务首页
public class ServicehomeActivity extends FragmentActivity implements
        OnClickListener, Mark {
    private FrameLayout fm_service;
    private RelativeLayout rl_mall, rl_market, rl_service_hall, rl_mine,
            rl_add;
    private ImageView iv_add;
    private Fragment[] fragments = new Fragment[4];
    private int width;
    private int lastIndex = -1;
    private ImageView iv_market, service_hall, iv_service_mine, iv_menu1;
    private TextView tv_market, tv_service_hall, tv_service_mine, tv_menu_mall;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case change_fragment:
                    // 服务大厅
                    showFragment(1);
                    iv_market.setImageResource(R.drawable.talent_market1);
                    iv_menu1.setImageResource(R.drawable.service_mall1);
                    service_hall.setImageResource(R.drawable.service_hall2);
                    iv_service_mine.setImageResource(R.drawable.service_mine1);

                    tv_market.setTextColor(0xff888888);
                    tv_menu_mall.setTextColor(0xff888888);
                    tv_service_hall.setTextColor(0xff83B226);
                    tv_service_mine.setTextColor(0xff888888);
                    break;
                case change_fragment2:
                    // 人才大厅
                    rl_market.performClick();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setContentView(R.layout.activity_servicehome);
        findview();
    }

    private void findview() {
        fm_service = (FrameLayout) findViewById(R.id.fm_service);
        rl_mall = (RelativeLayout) findViewById(R.id.rl_mall);
        rl_market = (RelativeLayout) findViewById(R.id.rl_market);
        rl_service_hall = (RelativeLayout) findViewById(R.id.rl_service_hall);
        rl_mine = (RelativeLayout) findViewById(R.id.rl_mine);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        rl_add = (RelativeLayout) findViewById(R.id.rl_add);
        iv_market = (ImageView) findViewById(R.id.iv_market);
        service_hall = (ImageView) findViewById(R.id.service_hall);
        iv_service_mine = (ImageView) findViewById(R.id.iv_service_mine);
        iv_menu1 = (ImageView) findViewById(R.id.iv_menu1);
        tv_market = (TextView) findViewById(R.id.tv_market);
        tv_service_hall = (TextView) findViewById(R.id.tv_service_hall);
        tv_service_mine = (TextView) findViewById(R.id.tv_service_mine);
        tv_menu_mall = (TextView) findViewById(R.id.tv_menu_mall);
        initView();
    }

    private void initView() {
        rl_mall.setOnClickListener(this);
        rl_market.setOnClickListener(this);
        rl_service_hall.setOnClickListener(this);
        rl_mine.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        setreleasewidth();
    }

    /**
     * 适配发布宽度
     */
    private void setreleasewidth() {
        // 获得屏幕宽
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels; // 屏幕宽度（像素）
        rl_add.post(new Runnable() {

            @Override
            public void run() {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rl_add
                        .getLayoutParams();
                layoutParams.width = width / 5;
                rl_add.setLayoutParams(layoutParams);
            }
        });
        showFragment(3);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_mall) {
            // 商城
            iv_market.setImageResource(R.drawable.talent_market1);
            iv_menu1.setImageResource(R.drawable.service_mall2);
            service_hall.setImageResource(R.drawable.service_hall1);
            iv_service_mine.setImageResource(R.drawable.service_mine1);

            tv_market.setTextColor(0xff888888);
            tv_menu_mall.setTextColor(0xff83B226);
            tv_service_hall.setTextColor(0xff888888);
            tv_service_mine.setTextColor(0xff888888);
            showFragment(3);
        } else if (v.getId() == R.id.rl_market) {
            // 人才市场
            showFragment(0);
            iv_market.setImageResource(R.drawable.talent_market2);
            iv_menu1.setImageResource(R.drawable.service_mall1);
            service_hall.setImageResource(R.drawable.service_hall1);
            iv_service_mine.setImageResource(R.drawable.service_mine1);

            tv_market.setTextColor(0xff83B226);
            tv_menu_mall.setTextColor(0xff888888);
            tv_service_hall.setTextColor(0xff888888);
            tv_service_mine.setTextColor(0xff888888);
        } else if (v.getId() == R.id.rl_service_hall) {
            // 服务大厅
            showFragment(1);
            iv_market.setImageResource(R.drawable.talent_market1);
            iv_menu1.setImageResource(R.drawable.service_mall1);
            service_hall.setImageResource(R.drawable.service_hall2);
            iv_service_mine.setImageResource(R.drawable.service_mine1);

            tv_market.setTextColor(0xff888888);
            tv_menu_mall.setTextColor(0xff888888);
            tv_service_hall.setTextColor(0xff83B226);
            tv_service_mine.setTextColor(0xff888888);
        } else if (v.getId() == R.id.iv_add) {
            // 发表
            Intent intent = new Intent(ServicehomeActivity.this,
                    ReleaseTaskActivity.class);
            startActivity(intent);
        }


//		switch (v.getId()) {
//		case R.id.rl_mall:
//			// 商城
//			iv_market.setImageResource(R.drawable.talent_market1);
//			iv_menu1.setImageResource(R.drawable.service_mall2);
//			service_hall.setImageResource(R.drawable.service_hall1);
//			iv_service_mine.setImageResource(R.drawable.service_mine1);
//
//			tv_market.setTextColor(0xff888888);
//			tv_menu_mall.setTextColor(0xff83B226);
//			tv_service_hall.setTextColor(0xff888888);
//			tv_service_mine.setTextColor(0xff888888);
//			showFragment(3);
//			break;
//		case R.id.rl_market:
//			// 人才市场
//			showFragment(0);
//			iv_market.setImageResource(R.drawable.talent_market2);
//			iv_menu1.setImageResource(R.drawable.service_mall1);
//			service_hall.setImageResource(R.drawable.service_hall1);
//			iv_service_mine.setImageResource(R.drawable.service_mine1);
//
//			tv_market.setTextColor(0xff83B226);
//			tv_menu_mall.setTextColor(0xff888888);
//			tv_service_hall.setTextColor(0xff888888);
//			tv_service_mine.setTextColor(0xff888888);
//
//			break;
//		case R.id.rl_service_hall:
//			// 服务大厅
//			showFragment(1);
//			iv_market.setImageResource(R.drawable.talent_market1);
//			iv_menu1.setImageResource(R.drawable.service_mall1);
//			service_hall.setImageResource(R.drawable.service_hall2);
//			iv_service_mine.setImageResource(R.drawable.service_mine1);
//
//			tv_market.setTextColor(0xff888888);
//			tv_menu_mall.setTextColor(0xff888888);
//			tv_service_hall.setTextColor(0xff83B226);
//			tv_service_mine.setTextColor(0xff888888);
//			break;
//		case R.id.rl_mine:
//			// 我的
//
//			break;
//		case R.id.iv_add:
//			// 发表
//			Intent intent = new Intent(ServicehomeActivity.this,
//					ReleaseTaskActivity.class);
//			startActivity(intent);
//			break;
//		}
    }

    /**
     * 显示Fragment
     *
     * @param index
     */
    private void showFragment(int index) {
        if (index == lastIndex) {
            return;
        }
        FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
        if (lastIndex != -1) {
            bt.hide(fragments[lastIndex]);
        }
        if (fragments[index] == null) {
            bt.add(R.id.fm_service, createFragment(index));
        } else {
            bt.show(fragments[index]);
        }
        bt.commit();
        lastIndex = index;
    }

    /**
     * 创建Fragment
     *
     * @param index
     * @return
     */
    private Fragment createFragment(int index) {
        switch (index) {
            case 0:
                return (fragments[index] = new Fragment_Hall());
            case 1:
                return (fragments[index] = new Fragment_Talent());
            case 2:
                return (fragments[index] = new Fragment_Mine());
            case 3:
                return (fragments[index] = new Fragment_ServiceHome(handler));
        }
        return null;
    }
}
