package com.community.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.fargment.BaseFragment;
import com.aiteshangcheng.a.R;
import com.community.activity.SearchActivity;


/**
 * 首页
 * Created by Administrator on 2017/8/22.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TextView  tv_type1, tv_type2;
    private FrameLayout fm_main;
    private ImageView iv_search;

    private Fragment[] fragments = new Fragment[2];//页面数组
    private int lastIndex = -1;//页面状态

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
        }
    };

    @Override
    protected int layoutResID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        fm_main = (FrameLayout) layout.findViewById(R.id.fm_main);
        tv_type1 = (TextView) layout.findViewById(R.id.tv_type1);
        tv_type2 = (TextView) layout.findViewById(R.id.tv_type2);
        iv_search = (ImageView) layout.findViewById(R.id.iv_search);

        tv_type1.setOnClickListener(this);
        tv_type2.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        showFragment(0);
        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            Bundle bundle = new Bundle();
            bundle.putInt("index", 1);
            // 消息广播频道
            Intent intent = new Intent("refreshdata");
            intent.putExtras(bundle);
            // 有序
            getActivity().sendOrderedBroadcast(intent, null);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_type1) {//商学院
            showFragment(0);
        } else if (id == R.id.tv_type2) {//Boss圈
            showFragment(1);
        } else if (id == R.id.iv_search) {//搜索
            Intent intent2 = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent2);
        }
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
        FragmentTransaction bt = getChildFragmentManager().beginTransaction();
        if (lastIndex != -1) {
            bt.hide(fragments[lastIndex]);
        }
        if (fragments[index] == null) {
            bt.add(R.id.fm_main, createFragment(index));
        } else {
            bt.show(fragments[index]);
        }
        bt.commit();
        lastIndex = index;
        handler.sendEmptyMessage(1000000);
    }

    private Fragment createFragment(int index) {
        switch (index) {
            case 0:
                return (fragments[index] = new FindFragment());
            case 1:
                return (fragments[index] = new HotFindFragment());
        }
        return null;
    }

}
