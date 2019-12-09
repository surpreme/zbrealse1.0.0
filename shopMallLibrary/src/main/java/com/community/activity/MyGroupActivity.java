package com.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableScrollView;
import com.aiteshangcheng.a.R;
import com.community.model.MyGroupAdapter;
import com.community.model.MyGroupInfo;


/**
 * 联系人
 * Created by mayn on 2018/10/12.
 */
public class MyGroupActivity extends BaseActivity implements View.OnClickListener, Mark {
    private ImageView iv_goback;
    private PullToRefreshLayout refresh_view;
    private PullableScrollView sc_scroll;
    private EditText et_search;
    private RelativeLayout rl_newfriend, rl_newgroup, rl_group1, rl_group2, rl_group3;
    private LinearLayout ll_group;
    private TextView tv_menu1, tv_menu2,tv_num1,tv_num2,tv_num3;
    private View v_menu1, v_menu2;
    private MyListView lv_list, lv_group1, lv_group2, lv_group3;
    private MyGroupInfo myGroupInfo;
    private MyGroupAdapter groupAdapter1, groupAdapter2, groupAdapter3;
    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1;

    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case my_group_id://我的群组
                    if (msg.obj != null) {
                        myGroupInfo = (MyGroupInfo) msg.obj;
                        Log.i("---------------","长度  "+myGroupInfo.is_identity3.size());
                        groupAdapter1 = new MyGroupAdapter(MyGroupActivity.this, myGroupInfo.is_identity1);
                        lv_group1.setAdapter(groupAdapter1);
                        groupAdapter2 = new MyGroupAdapter(MyGroupActivity.this, myGroupInfo.is_identity2);
                        lv_group2.setAdapter(groupAdapter2);
                        groupAdapter3 = new MyGroupAdapter(MyGroupActivity.this, myGroupInfo.is_identity3);
                        lv_group3.setAdapter(groupAdapter3);
                        tv_num1.setText(myGroupInfo.is_identity1_num);
                        tv_num2.setText(myGroupInfo.is_identity2_num);
                        tv_num3.setText(myGroupInfo.is_identity3__num);
                    }
                    break;
                case my_group_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygroup);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        sc_scroll = (PullableScrollView) findViewById(R.id.sc_scroll);
        et_search = (EditText) findViewById(R.id.et_search);
        rl_newfriend = (RelativeLayout) findViewById(R.id.rl_newfriend);
        rl_newgroup = (RelativeLayout) findViewById(R.id.rl_newgroup);
        rl_group1 = (RelativeLayout) findViewById(R.id.rl_group1);
        rl_group2 = (RelativeLayout) findViewById(R.id.rl_group2);
        rl_group3 = (RelativeLayout) findViewById(R.id.rl_group3);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
        tv_num3 = (TextView) findViewById(R.id.tv_num3);
        v_menu1 = findViewById(R.id.v_menu1);
        v_menu2 = findViewById(R.id.v_menu2);
        lv_list = (MyListView) findViewById(R.id.lv_list);
        lv_group1 = (MyListView) findViewById(R.id.lv_group1);
        lv_group2 = (MyListView) findViewById(R.id.lv_group2);
        lv_group3 = (MyListView) findViewById(R.id.lv_group3);
        ll_group = (LinearLayout) findViewById(R.id.ll_group);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        rl_newfriend.setOnClickListener(this);
        rl_newgroup.setOnClickListener(this);
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        rl_group1.setOnClickListener(this);
        rl_group2.setOnClickListener(this);
        rl_group3.setOnClickListener(this);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.MyGroup();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.rl_newfriend){
            //新朋友
            Intent intent = new Intent(MyGroupActivity.this, NewFriendActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.tv_menu1){
            //好友 ff3c81
            lv_list.setVisibility(View.VISIBLE);
            ll_group.setVisibility(View.GONE);
            tv_menu1.setTextColor(0xffff3c81);
            tv_menu2.setTextColor(0xff000000);
            v_menu1.setVisibility(View.VISIBLE);
            v_menu2.setVisibility(View.GONE);
        }else if(v.getId()==R.id.tv_menu2){
            //群组
            lv_list.setVisibility(View.GONE);
            ll_group.setVisibility(View.VISIBLE);
            tv_menu2.setTextColor(0xffff3c81);
            tv_menu1.setTextColor(0xff000000);
            v_menu2.setVisibility(View.VISIBLE);
            v_menu1.setVisibility(View.GONE);
        }else if(v.getId()==R.id.rl_group1){
            //创建的群聊
            if (lv_group1.getVisibility() == View.VISIBLE) {
                lv_group1.setVisibility(View.GONE);
            } else {
                lv_group1.setVisibility(View.VISIBLE);
            }
        }else if(v.getId()==R.id.rl_group2){
            //管理的群聊
            if (lv_group2.getVisibility() == View.VISIBLE) {
                lv_group2.setVisibility(View.GONE);
            } else {
                lv_group2.setVisibility(View.VISIBLE);
            }
        }else if(v.getId()==R.id.rl_group3){
            //加入的群聊
            if (lv_group3.getVisibility() == View.VISIBLE) {
                lv_group3.setVisibility(View.GONE);
            } else {
                lv_group3.setVisibility(View.VISIBLE);
            }
        }
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.rl_newfriend://新朋友
//                Intent intent = new Intent(MyGroupActivity.this, NewFriendActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.rl_newgroup://创建群组
//                break;
//            case R.id.tv_menu1://好友 ff3c81
//                lv_list.setVisibility(View.VISIBLE);
//                ll_group.setVisibility(View.GONE);
//                tv_menu1.setTextColor(0xffff3c81);
//                tv_menu2.setTextColor(0xff000000);
//                v_menu1.setVisibility(View.VISIBLE);
//                v_menu2.setVisibility(View.GONE);
//                break;
//            case R.id.tv_menu2://群组
//                lv_list.setVisibility(View.GONE);
//                ll_group.setVisibility(View.VISIBLE);
//                tv_menu2.setTextColor(0xffff3c81);
//                tv_menu1.setTextColor(0xff000000);
//                v_menu2.setVisibility(View.VISIBLE);
//                v_menu1.setVisibility(View.GONE);
//                break;
//            case R.id.rl_group1://创建的群聊
//                if (lv_group1.getVisibility() == View.VISIBLE) {
//                    lv_group1.setVisibility(View.GONE);
//                } else {
//                    lv_group1.setVisibility(View.VISIBLE);
//                }
//                break;
//            case R.id.rl_group2://管理的群聊
//                if (lv_group2.getVisibility() == View.VISIBLE) {
//                    lv_group2.setVisibility(View.GONE);
//                } else {
//                    lv_group2.setVisibility(View.VISIBLE);
//                }
//                break;
//            case R.id.rl_group3://加入的群聊
//                if (lv_group3.getVisibility() == View.VISIBLE) {
//                    lv_group3.setVisibility(View.GONE);
//                } else {
//                    lv_group3.setVisibility(View.VISIBLE);
//                }
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
            Log.i("-----------------", "刷新  ");
            netRun.MyGroup();
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            Toast.makeText(MyGroupActivity.this, getString(R.string.find_reminder3), Toast.LENGTH_SHORT).show();
            loadMoreSucceed();
        }
    }

    @Override
    public void ReGetData() {

    }
}
