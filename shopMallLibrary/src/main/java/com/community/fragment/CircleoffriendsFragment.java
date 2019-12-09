package com.community.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.fargment.BaseFragment;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.activity.MessageActivity;
import com.community.adapter.CircleoffriendsInfo;
import com.community.adapter.Dynamic2Adapter;

/**
 * 朋友圈
 * Created by mayn on 2018/10/10.
 */
public class CircleoffriendsFragment extends BaseFragment implements View.OnClickListener{
    private ImageView iv_goback, iv_msg;
    private TextView tv_type1, tv_type2,tv_sendmessage;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;
    private MyListener myListenr;
    private RelativeLayout rl_input;
    private EditText et_input;
    private int refreshtype = 0, curpage = 1;
    private Dynamic2Adapter dynamic2Adapter;
    private String dynamicid;//评论ID
    private CircleoffriendsInfo circleoffriendsInfo;
    private String type = "web", keyword;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case circleoffriends_home_id:
                    if (msg.obj != null) {
                        circleoffriendsInfo = (CircleoffriendsInfo) msg.obj;
                        if (circleoffriendsInfo.list == null || circleoffriendsInfo.list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || dynamic2Adapter == null) {
                                dynamic2Adapter = new Dynamic2Adapter(getActivity(), circleoffriendsInfo.list,
                                        handler, getActivity());
                                lv_list.setAdapter(dynamic2Adapter);
                            } else if (refreshtype == 1) {
                                dynamic2Adapter.refreshData(circleoffriendsInfo.list);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                dynamic2Adapter.addData(circleoffriendsInfo.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case circleoffriends_home_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case dynamic_comment_id://评论
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            rl_input.setVisibility(View.GONE);
                            refreshtype = 1;
                            curpage = 1;
                            netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
                        }
                    }
                    break;
                case dynamic_comment_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case praise_circleoffriends_id://点赞
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            refreshtype = 1;
                            curpage = 1;
                            netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
                        }
                    }
                    break;
                case praise_circleoffriends_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case DYNAMIC://动态评论
                    if (msg.obj != null) {
                        dynamicid = (String) msg.obj;
                        rl_input.setVisibility(View.VISIBLE);
                    }
                    break;
                case PRAISECIRCLEOFFRIENDS://动态点赞
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        netRun.PraiseCircleoffriends(str);
                    }
                    break;
            }
        }
    };

    @Override
    protected int layoutResID() {
        return R.layout.fragment_circleoffriends;
    }

    @Override
    protected void initView() {
        iv_goback = (ImageView) layout.findViewById(R.id.iv_goback);
        iv_msg = (ImageView) layout.findViewById(R.id.iv_msg);
        tv_type1 = (TextView) layout.findViewById(R.id.tv_type1);
        tv_type2 = (TextView) layout.findViewById(R.id.tv_type2);
        tv_sendmessage = (TextView) layout.findViewById(R.id.tv_sendmessage);
        lv_list = (PullableListView) layout.findViewById(R.id.lv_list);
        refresh_view = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
        ll_null = (LinearLayout) layout.findViewById(R.id.ll_null);
        et_input = (EditText) layout.findViewById(R.id.et_input);
        rl_input = (RelativeLayout) layout.findViewById(R.id.rl_input);
        iv_goback.setOnClickListener(this);
        tv_type1.setOnClickListener(this);
        tv_type2.setOnClickListener(this);
        iv_msg.setOnClickListener(this);
        rl_input.setOnClickListener(this);
        tv_sendmessage.setOnClickListener(this);
        myListenr = new MyListener();
        if (keyword == null) {
            keyword = "";
        }
        refresh_view.setOnRefreshListener(myListenr);
        netRun = new NetRun(getActivity(), handler);
    }

    @Override
    protected void initData() {
        netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_type1){
            type = "web";
            tv_type1.setBackgroundResource(R.drawable.drawmoney_typea1);
            tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb2);
            tv_type1.setTextColor(Color.WHITE);
            tv_type2.setTextColor(0xffED5457);
            refreshtype = 1;
            curpage = 1;
            netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
        }else if(v.getId()==R.id.tv_type2){
            type = "app";
            tv_type1.setBackgroundResource(R.drawable.drawmoney_typea2);
            tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb1);
            tv_type2.setTextColor(Color.WHITE);
            tv_type1.setTextColor(0xffED5457);
            refreshtype = 1;
            curpage = 1;
            netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
        }else if(v.getId()==R.id.iv_msg){
            //消息
            Intent intent = new Intent(getActivity(), MessageActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.rl_input){
            //评论
            et_input.setText("");
            rl_input.setVisibility(View.GONE);
        }else if(v.getId()==R.id.tv_sendmessage){
            //发送
            String s = et_input.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(getActivity(), getString(R.string.input_comments), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.DynamicComment(dynamicid, s);
            et_input.setText("");
        }


//        switch (v.getId()) {
//            case R.id.iv_goback:
//                break;
//            case R.id.tv_type1:
//                type = "web";
//                tv_type1.setBackgroundResource(R.drawable.drawmoney_typea1);
//                tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb2);
//                tv_type1.setTextColor(Color.WHITE);
//                tv_type2.setTextColor(0xffED5457);
//                refreshtype = 1;
//                curpage = 1;
//                netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
//                break;
//            case R.id.tv_type2:
//                type = "app";
//                tv_type1.setBackgroundResource(R.drawable.drawmoney_typea2);
//                tv_type2.setBackgroundResource(R.drawable.drawmoney_typeb1);
//                tv_type2.setTextColor(Color.WHITE);
//                tv_type1.setTextColor(0xffED5457);
//                refreshtype = 1;
//                curpage = 1;
//                netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
//                break;
//            case R.id.iv_msg://消息
//                Intent intent = new Intent(getActivity(), MessageActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.rl_input://评论
//                et_input.setText("");
//                rl_input.setVisibility(View.GONE);
//                break;
//            case R.id.tv_sendmessage://发送
//                String s = et_input.getText().toString();
//                if (TextUtils.isEmpty(s)) {
//                    Toast.makeText(getActivity(), getString(R.string.input_comments), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.DynamicComment(dynamicid, s);
//                et_input.setText("");
//                break;
//        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        private PullToRefreshLayout pullToRefreshLayout;

        @SuppressLint("HandlerLeak")
        private android.os.Handler refreshHandler = new android.os.Handler() {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case pull_down_refresh_success:// 下拉刷新成功
                        if (pullToRefreshLayout != null) {
                            pullToRefreshLayout
                                    .refreshFinish(PullToRefreshLayout.SUCCEED);
                        }
                        break;
                    case pull_up_loaded_success:// 上拉加载成功
                        if (pullToRefreshLayout != null) {
                            pullToRefreshLayout
                                    .loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        }
                        break;
                }
            }

            ;
        };

        /**
         * 下拉式刷新成功
         */
        public void refreshSucceed() {
            refreshHandler.sendEmptyMessageDelayed(pull_down_refresh_success,
                    400);
        }

        /**
         * 上拉加载成功
         */
        public void loadMoreSucceed() {
            refreshHandler.sendEmptyMessageDelayed(pull_up_loaded_success, 400);
        }

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            refreshtype = 1;
            curpage = 1;
            netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (circleoffriendsInfo != null && circleoffriendsInfo.is_nextpage.equals("1")) {
                refreshtype = 2;
                curpage++;
                netRun.CircleoffriendsHome("20", curpage + "", type, keyword);
            } else {
                Toast.makeText(getActivity(), getString(R.string.find_reminder3), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
            }
        }
    }
}
