package com.community.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.adapter.PraiseListAdapter;
import com.community.model.PraiseListInfo;

/**
 * 点赞列表
 * Created by mayn on 2018/10/11.
 */
public class PraiseListActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;
    private String theme_id;
    private NetRun netRun;
    private PraiseListInfo praiseListInfo;
    private PraiseListAdapter praiseListAdapter;

    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case dynamic_praiselist_id:
                    if (msg.obj != null) {
                        praiseListInfo = (PraiseListInfo) msg.obj;
                        if (praiseListInfo.list == null || praiseListInfo.list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || praiseListAdapter == null) {
                                praiseListAdapter = new PraiseListAdapter(PraiseListActivity.this, praiseListInfo.list, handler);
                                lv_list.setAdapter(praiseListAdapter);
                            } else if (refreshtype == 1) {
                                praiseListAdapter.refreshData(praiseListInfo.list);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                praiseListAdapter.addData(praiseListInfo.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case dynamic_praiselist_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case add_friend_id://加好友
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case add_friend_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        netRun.AddFriend(str);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praiselist);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_list = (PullableListView) findViewById(R.id.lv_list);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        theme_id = getIntent().getStringExtra("theme_id");
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.DynamicPraiseList(theme_id, "20", curpage + "");
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//        }
        if(v.getId()==R.id.iv_goback){
            finish();
        }
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
            netRun.DynamicPraiseList(theme_id, "20", curpage + "");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (praiseListInfo.is_nextpage.equals("0")) {
                Toast.makeText(PraiseListActivity.this, getString(R.string.find_reminder3), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
                return;
            }
            refreshtype = 2;
            curpage++;
            netRun.DynamicPraiseList(theme_id, "20", curpage + "");
        }
    }


    @Override
    public void ReGetData() {

    }
}
