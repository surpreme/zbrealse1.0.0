package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.GoodsEvaluate2Adapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.GoodsEvaluateInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;

/**
 * 商品评价
 * Created by mayn on 2018/11/14.
 */
public class GoodsEvaluateActivity extends BaseActivity implements View.OnClickListener {
    private TextView _tv_name;
    private ImageView _iv_back;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;

    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1;

    private GoodsEvaluateInfo goodsEvaluateInfo;
    private GoodsEvaluate2Adapter goodsEvaluate2Adapter;
    private String goods_id, type = "0";
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case goodsevaluate_list_id://列表
                    if (msg.obj != null) {
                        goodsEvaluateInfo = (GoodsEvaluateInfo) msg.obj;
                        if (goodsEvaluateInfo.goodsevallist == null || goodsEvaluateInfo.goodsevallist.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || goodsEvaluate2Adapter == null) {
                                int h = (ClutterUtils.getScreenWidth(GoodsEvaluateActivity.this)-
                                        ClutterUtils.dp2px(GoodsEvaluateActivity.this,22)) / 3;
                                goodsEvaluate2Adapter = new GoodsEvaluate2Adapter(GoodsEvaluateActivity.this, goodsEvaluateInfo.goodsevallist, h);
                                lv_list.setAdapter(goodsEvaluate2Adapter);
                            } else if (refreshtype == 1) {
                                goodsEvaluate2Adapter.refreshData(goodsEvaluateInfo.goodsevallist);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                goodsEvaluate2Adapter.addData(goodsEvaluateInfo.goodsevallist);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case goodsevaluate_list_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsevaluate);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = findViewById(R.id._tv_name);
        _iv_back = findViewById(R.id._iv_back);
        refresh_view = findViewById(R.id.refresh_view);
        lv_list = findViewById(R.id.lv_list);
        ll_null = findViewById(R.id.ll_null);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.goodsdatails_reminder3));
        _iv_back.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        goods_id = getIntent().getStringExtra("goods_id");

        initData();
    }

    @Override
    protected void initData() {
        netRun.GoodsEvaluateList(goods_id, type, curpage + "");
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//        }
        if(view.getId()== R.id._iv_back){
            finish();
        }
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
            netRun.GoodsEvaluateList(goods_id, type, curpage + "");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (goodsEvaluateInfo.is_nextpage.equals("0")) {
                Toast.makeText(GoodsEvaluateActivity.this, getString(R.string.find_reminder3), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
                return;
            }
            refreshtype = 2;
            curpage++;
            netRun.GoodsEvaluateList(goods_id, type, curpage + "");
        }
    }

    @Override
    public void ReGetData() {

    }
}
