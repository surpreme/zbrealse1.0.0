package com.community.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.activity.MyDoingsActivity;
import com.community.activity.MyGroupActivity;
import com.community.model.MeInfo;

import livestream.fragment.BaseFragment;

/**
 * 个人中心
 * Created by mayn on 2018/10/10.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    private CircleImageView iv_iocn;
    private ImageView iv_setting;
    private TextView tv_name, tv_level, tv_num1, tv_num2, tv_num3;
    private RelativeLayout rl_menu1, rl_menu2, rl_menu3, rl_menu4, rl_menu5, rl_menu6, rl_menu7, rl_menu8;
    private MeInfo meInfo;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case find_user_id:
                    if (msg.obj!=null){
                        meInfo= (MeInfo) msg.obj;
                        Glide.with(getActivity()).load(meInfo.member_info.member_avatar).into(iv_iocn);
                        tv_name.setText(meInfo.member_info.member_name);
                        tv_level.setText(getString(R.string.find_reminder127)+meInfo.member_info.member_grade);
                        tv_num1.setText(meInfo.member_info.activity_total);
                        tv_num2.setText(meInfo.member_info.circle_total);
                        tv_num3.setText(meInfo.member_info.cms_article_total);
                    }
                    break;
                case find_user_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected int getlayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        iv_iocn = (CircleImageView) layout.findViewById(R.id.iv_iocn);
        iv_setting = (ImageView) layout.findViewById(R.id.iv_setting);
        tv_name = (TextView) layout.findViewById(R.id.tv_name);
        tv_level = (TextView) layout.findViewById(R.id.tv_level);
        tv_num1 = (TextView) layout.findViewById(R.id.tv_num1);
        tv_num2 = (TextView) layout.findViewById(R.id.tv_num2);
        tv_num3 = (TextView) layout.findViewById(R.id.tv_num3);
        rl_menu1 = (RelativeLayout) layout.findViewById(R.id.rl_menu1);
        rl_menu2 = (RelativeLayout) layout.findViewById(R.id.rl_menu2);
        rl_menu3 = (RelativeLayout) layout.findViewById(R.id.rl_menu3);
        rl_menu4 = (RelativeLayout) layout.findViewById(R.id.rl_menu4);
        rl_menu5 = (RelativeLayout) layout.findViewById(R.id.rl_menu5);
        rl_menu6 = (RelativeLayout) layout.findViewById(R.id.rl_menu6);
        rl_menu7 = (RelativeLayout) layout.findViewById(R.id.rl_menu7);
        rl_menu8 = (RelativeLayout) layout.findViewById(R.id.rl_menu8);
        iv_setting.setOnClickListener(this);
        rl_menu1.setOnClickListener(this);
        rl_menu2.setOnClickListener(this);
        rl_menu3.setOnClickListener(this);
        rl_menu4.setOnClickListener(this);
        rl_menu5.setOnClickListener(this);
        rl_menu6.setOnClickListener(this);
        rl_menu7.setOnClickListener(this);
        rl_menu8.setOnClickListener(this);
        netRun = new NetRun(getActivity(), handler);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            netRun.FindUser();
        }
    }

    @Override
    protected void initData() {
        netRun.FindUser();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();//设置
//我的帖子
//订单管理
//我的购物车
//会员套餐
//意见反馈
//退出登陆
        if (id == R.id.iv_setting) {
        } else if (id == R.id.rl_menu1) {//我的群组
            Intent intent2 = new Intent(getActivity(), MyGroupActivity.class);
            startActivity(intent2);
        } else if (id == R.id.rl_menu2) {//我的活动
            Intent intent = new Intent(getActivity(), MyDoingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.rl_menu3) {
        } else if (id == R.id.rl_menu4) {
        } else if (id == R.id.rl_menu5) {
        } else if (id == R.id.rl_menu6) {
        } else if (id == R.id.rl_menu7) {
        } else if (id == R.id.rl_menu8) {
        }
    }
}
