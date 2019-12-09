package com.community.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.adapter.MyActivityAdapter;
import com.community.model.MyActivityInfo;

/**
 * 我的活动
 * Created by mayn on 2018/10/10.
 */
public class MyDoingsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback;
    private TextView tv_new;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;
    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1;
    private MyActivityInfo myActivityInfo;
    private MyActivityAdapter myActivityAdapter;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case my_activity_id://我的活动
                    if (msg.obj != null) {
                        myActivityInfo = (MyActivityInfo) msg.obj;
                        if (myActivityInfo.list == null || myActivityInfo.list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || myActivityAdapter == null) {
                                myActivityAdapter = new MyActivityAdapter(MyDoingsActivity.this, myActivityInfo.list);
                                lv_list.setAdapter(myActivityAdapter);
                            } else if (refreshtype == 1) {
                                myActivityAdapter.refreshDta(myActivityInfo.list);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                myActivityAdapter.addDta(myActivityInfo.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case my_activity_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydoings);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        tv_new = (TextView) findViewById(R.id.tv_new);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_list = (PullableListView) findViewById(R.id.lv_list);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        initView();
    }

    @Override
    protected void initView() {
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.MyActivity(curpage + "");
    }

    @Override
    public void onClick(View v) {

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
            netRun.MyActivity(curpage + "");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            refreshtype = 2;
            curpage++;
            if (myActivityInfo.is_nextpage.equals("0")){
                Toast.makeText(MyDoingsActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
                return;
            }
            netRun.MyActivity(curpage + "");
        }
    }


    @Override
    public void ReGetData() {

    }
}
