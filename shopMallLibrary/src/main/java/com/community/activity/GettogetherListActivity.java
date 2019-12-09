package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.GettogetherListInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.adapter.GettogetherAdapter;
import com.community.utils.MenuPopu;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动列表
 * Created by mayn on 2018/9/14.
 */
public class GettogetherListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback;
    private TextView tv_new, tv_menu1, tv_menu2;
    private LinearLayout ll_menu1, ll_menu2, ll_null, ll_menu;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1;
    private String class_id = "", last_time = "", keyword = "", pagesize = "20", menutype = "1";

    private GettogetherListInfo gettogetherListInfo;
    private GettogetherAdapter gettogetherAdapter;
    private List<String> classifylist, timelist;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case circleoffriends_list_id:
                    if (msg.obj != null) {
                        gettogetherListInfo = (GettogetherListInfo) msg.obj;
                        if (gettogetherListInfo.class_list != null && gettogetherListInfo.class_list.size() != 0) {
                            for (int i = 0; i < gettogetherListInfo.class_list.size(); i++) {
                                classifylist.add(gettogetherListInfo.class_list.get(i).class_name);
                            }
                        }

                        if (gettogetherListInfo.list == null && gettogetherListInfo.list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || gettogetherAdapter == null) {
                                gettogetherAdapter = new GettogetherAdapter(GettogetherListActivity.this, gettogetherListInfo.list);
                                lv_list.setAdapter(gettogetherAdapter);
                            } else if (refreshtype == 1) {
                                gettogetherAdapter.refreshData(gettogetherListInfo.list);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                gettogetherAdapter.addData(gettogetherListInfo.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case circleoffriends_list_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gettogetherlist);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        tv_new = (TextView) findViewById(R.id.tv_new);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        ll_menu1 = (LinearLayout) findViewById(R.id.ll_menu1);
        ll_menu2 = (LinearLayout) findViewById(R.id.ll_menu2);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_list = (PullableListView) findViewById(R.id.lv_list);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_goback.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        ll_menu1.setOnClickListener(this);
        ll_menu2.setOnClickListener(this);
        myListenr=new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        keyword=getIntent().getStringExtra("keyword");
        if (keyword==null){
            keyword="";
        }
        classifylist = new ArrayList<>();
        timelist = new ArrayList<>();
        timelist.add(getString(R.string.communitymenu1));
        timelist.add(getString(R.string.find_reminder63));
        timelist.add(getString(R.string.find_reminder64));
        timelist.add(getString(R.string.find_reminder65));
        initData();
    }

    @Override
    protected void initData() {
        netRun.CircleoffriendsList(class_id, last_time, keyword, pagesize, curpage + "");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.tv_new){
            //创建
            Intent intent = new Intent(GettogetherListActivity.this, CreateGettogetherActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.ll_menu1){
            //活动分类
            menutype = "1";
            showpopu(classifylist);
        }else if(v.getId()==R.id.ll_menu2){
            //活动时间
            menutype = "2";
            showpopu(timelist);
        }
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_new://创建
//                Intent intent = new Intent(GettogetherListActivity.this, CreateGettogetherActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.ll_menu1://活动分类
//                menutype = "1";
//                showpopu(classifylist);
//                break;
//            case R.id.ll_menu2://活动时间
//                menutype = "2";
//                showpopu(timelist);
//                break;
//        }
    }

    private void showpopu(List<String> data) {
        MenuPopu menuPopu = new MenuPopu(GettogetherListActivity.this, data);
        menuPopu.setmenu(new MenuPopu.menu() {
            @Override
            public void onItemClick(int id) {
                if (menutype.equals("1")) {
                    class_id = gettogetherListInfo.class_list.get(id).class_id;
                    tv_menu1.setText(gettogetherListInfo.class_list.get(id).class_name);
                } else {
                    last_time = timelist.get(id);
                    tv_menu2.setText(timelist.get(id));
                }
                netRun.CircleoffriendsList(class_id, last_time, keyword, pagesize, curpage + "");
            }
        });
        menuPopu.showAsDropDown(ll_menu);
    }


    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        private PullToRefreshLayout pullToRefreshLayout;

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
            netRun.CircleoffriendsList(class_id, last_time, keyword, pagesize, curpage + "");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (gettogetherListInfo!=null&&gettogetherListInfo.is_nextpage.equals("1")){
                refreshtype = 2;
                curpage++;
                netRun.CircleoffriendsList(class_id, last_time, keyword, pagesize, curpage + "");
            }else{
                Toast.makeText(GettogetherListActivity.this, getString(R.string.find_reminder3), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
            }
        }
    }


    @Override
    public void ReGetData() {

    }
}
