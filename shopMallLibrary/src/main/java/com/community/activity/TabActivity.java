package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.community.fragment.CircleoffriendsFragment;
import com.community.fragment.HomeFragment;
import com.community.fragment.MeFragment;
import com.community.fragment.MessageFragment;


/**
 * 首页
 * Created by mayn on 2018/10/10.
 */
public class TabActivity extends FragmentActivity implements View.OnClickListener {
    private FrameLayout fm_main;
    private LinearLayout ll_menu1, ll_menu2, ll_menu3, ll_menu4;
    private TextView tv_menu1, tv_menu2, tv_menu3, tv_menu4;
    private ImageView iv_menu1, iv_menu2, iv_menu3, iv_menu4, iv_add;
    private Fragment[] fragments = new Fragment[4];//页面数组
    private int lastIndex = -1;//页面状态
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_findtab);
        findViewById();
    }

    protected void findViewById() {
        fm_main = (FrameLayout) findViewById(R.id.fm_main);
        ll_menu1 = (LinearLayout) findViewById(R.id.ll_menu1);
        ll_menu2 = (LinearLayout) findViewById(R.id.ll_menu2);
        ll_menu3 = (LinearLayout) findViewById(R.id.ll_menu3);
        ll_menu4 = (LinearLayout) findViewById(R.id.ll_menu4);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
        tv_menu4 = (TextView) findViewById(R.id.tv_menu4);
        iv_menu1 = (ImageView) findViewById(R.id.iv_menu1);
        iv_menu2 = (ImageView) findViewById(R.id.iv_menu2);
        iv_menu3 = (ImageView) findViewById(R.id.iv_menu3);
        iv_menu4 = (ImageView) findViewById(R.id.iv_menu4);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        initView();
    }

    protected void initView() {
        ll_menu1.setOnClickListener(this);
        ll_menu2.setOnClickListener(this);
        ll_menu3.setOnClickListener(this);
        ll_menu4.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        showFragment(0);
        netRun = new NetRun(this, handler);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_menu1) {
            showFragment(0);
            iv_menu1.setImageResource(R.drawable.fandmenu_a2);
            iv_menu2.setImageResource(R.drawable.fandmenu_b1);
            iv_menu3.setImageResource(R.drawable.fandmenu_c1);
            iv_menu4.setImageResource(R.drawable.fandmenu_d1);
            tv_menu1.setTextColor(0xffff3c81);
            tv_menu2.setTextColor(0xff6D6D6D);
            tv_menu3.setTextColor(0xff6D6D6D);
            tv_menu4.setTextColor(0xff6D6D6D);
        } else if (v.getId() == R.id.ll_menu2) {
            showFragment(1);
            iv_menu1.setImageResource(R.drawable.fandmenu_a1);
            iv_menu2.setImageResource(R.drawable.fandmenu_b2);
            iv_menu3.setImageResource(R.drawable.fandmenu_c1);
            iv_menu4.setImageResource(R.drawable.fandmenu_d1);
            tv_menu1.setTextColor(0xff6D6D6D);
            tv_menu2.setTextColor(0xffff3c81);
            tv_menu3.setTextColor(0xff6D6D6D);
            tv_menu4.setTextColor(0xff6D6D6D);
        } else if (v.getId() == R.id.ll_menu3) {
            showFragment(2);
            iv_menu1.setImageResource(R.drawable.fandmenu_a1);
            iv_menu2.setImageResource(R.drawable.fandmenu_b1);
            iv_menu3.setImageResource(R.drawable.fandmenu_c2);
            iv_menu4.setImageResource(R.drawable.fandmenu_d1);
            tv_menu1.setTextColor(0xff6D6D6D);
            tv_menu2.setTextColor(0xff6D6D6D);
            tv_menu3.setTextColor(0xffff3c81);
            tv_menu4.setTextColor(0xff6D6D6D);
        } else if (v.getId() == R.id.ll_menu4) {
            showFragment(3);
            iv_menu1.setImageResource(R.drawable.fandmenu_a1);
            iv_menu2.setImageResource(R.drawable.fandmenu_b1);
            iv_menu3.setImageResource(R.drawable.fandmenu_c1);
            iv_menu4.setImageResource(R.drawable.fandmenu_d2);
            tv_menu1.setTextColor(0xff6D6D6D);
            tv_menu2.setTextColor(0xff6D6D6D);
            tv_menu3.setTextColor(0xff6D6D6D);
            tv_menu4.setTextColor(0xffff3c81);
        } else if (v.getId() == R.id.iv_add) {
            Intent intent = new Intent(TabActivity.this, ReleaseCircleoffriendsActivity.class);
            startActivity(intent);
        }

//        switch (v.getId()) {
//            case R.id.ll_menu1:
//                showFragment(0);
//                iv_menu1.setImageResource(R.drawable.fandmenu_a2);
//                iv_menu2.setImageResource(R.drawable.fandmenu_b1);
//                iv_menu3.setImageResource(R.drawable.fandmenu_c1);
//                iv_menu4.setImageResource(R.drawable.fandmenu_d1);
//                tv_menu1.setTextColor(0xffff3c81);
//                tv_menu2.setTextColor(0xff6D6D6D);
//                tv_menu3.setTextColor(0xff6D6D6D);
//                tv_menu4.setTextColor(0xff6D6D6D);
//                break;
//            case R.id.ll_menu2:
//                showFragment(1);
//                iv_menu1.setImageResource(R.drawable.fandmenu_a1);
//                iv_menu2.setImageResource(R.drawable.fandmenu_b2);
//                iv_menu3.setImageResource(R.drawable.fandmenu_c1);
//                iv_menu4.setImageResource(R.drawable.fandmenu_d1);
//                tv_menu1.setTextColor(0xff6D6D6D);
//                tv_menu2.setTextColor(0xffff3c81);
//                tv_menu3.setTextColor(0xff6D6D6D);
//                tv_menu4.setTextColor(0xff6D6D6D);
//                break;
//            case R.id.ll_menu3:
//                showFragment(2);
//                iv_menu1.setImageResource(R.drawable.fandmenu_a1);
//                iv_menu2.setImageResource(R.drawable.fandmenu_b1);
//                iv_menu3.setImageResource(R.drawable.fandmenu_c2);
//                iv_menu4.setImageResource(R.drawable.fandmenu_d1);
//                tv_menu1.setTextColor(0xff6D6D6D);
//                tv_menu2.setTextColor(0xff6D6D6D);
//                tv_menu3.setTextColor(0xffff3c81);
//                tv_menu4.setTextColor(0xff6D6D6D);
//                break;
//            case R.id.ll_menu4:
//                showFragment(3);
//                iv_menu1.setImageResource(R.drawable.fandmenu_a1);
//                iv_menu2.setImageResource(R.drawable.fandmenu_b1);
//                iv_menu3.setImageResource(R.drawable.fandmenu_c1);
//                iv_menu4.setImageResource(R.drawable.fandmenu_d2);
//                tv_menu1.setTextColor(0xff6D6D6D);
//                tv_menu2.setTextColor(0xff6D6D6D);
//                tv_menu3.setTextColor(0xff6D6D6D);
//                tv_menu4.setTextColor(0xffff3c81);
//                break;
//            case R.id.iv_add:
//                Intent intent = new Intent(TabActivity.this, ReleaseCircleoffriendsActivity.class);
//                startActivity(intent);
//                break;
//        }
    }

    /**
     * 加载Fragment
     *
     * @param index
     */
    public void showFragment(int index) {
        if (index == lastIndex) {
            return;
        }
        FragmentTransaction bt = getSupportFragmentManager().beginTransaction();
        if (lastIndex != -1) {
            bt.hide(fragments[lastIndex]);
//            bt.remove(fragments[lastIndex]);
//            fragments[lastIndex]=null;
        }
        if (fragments[index] == null) {
            bt.add(R.id.fm_main, createFragment(index));
        } else {
            bt.show(fragments[index]);
//            bt.replace(R.id.fm_main,fragments[index]);
        }
        bt.commit();
        lastIndex = index;
        handler.sendEmptyMessage(1000000);
    }

    private Fragment createFragment(int index) {
        switch (index) {
            case 0:
                return (fragments[index] = new HomeFragment());
            case 1:
                return (fragments[index] = new CircleoffriendsFragment());
            case 2:
                return (fragments[index] = new MessageFragment());
            case 3:
                return (fragments[index] = new MeFragment());
        }
        return null;
    }


}
