package com.community.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.adapter.Group2Adapter;
import com.community.model.PostGroupInfo;

/**
 * 群组首页
 * Created by mayn on 2018/9/4.
 */
public class PostGroupActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_search;
    private TextView tv_create, tv_menu1, tv_menu2, tv_menu3;
    private EditText et_search;
    private View v_slider1, v_slider2, v_slider3;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;
    private PostGroupInfo postGroupInfo;
    private Group2Adapter group2Adapter;
    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1;
    private String keyword = "", orderType = "1", pagesize = "20";
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case group_home_id:
                    if (msg.obj != null) {
                        postGroupInfo = (PostGroupInfo) msg.obj;
                        if (postGroupInfo.list == null || postGroupInfo.list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || group2Adapter == null) {
                                group2Adapter = new Group2Adapter(PostGroupActivity.this, postGroupInfo.list);
                                lv_list.setAdapter(group2Adapter);
                            } else if (refreshtype == 1) {
                                group2Adapter.refreshData(postGroupInfo.list);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                group2Adapter.addData(postGroupInfo.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case group_home_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postgroup);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        tv_create = (TextView) findViewById(R.id.tv_create);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
        et_search = (EditText) findViewById(R.id.et_search);
        v_slider1 = findViewById(R.id.v_slider1);
        v_slider2 = findViewById(R.id.v_slider2);
        v_slider3 = findViewById(R.id.v_slider3);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_list = (PullableListView) findViewById(R.id.lv_list);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        tv_create.setOnClickListener(this);
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        tv_menu3.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        keyword=getIntent().getStringExtra("keyword");
        if (keyword==null){
            keyword="";
        }
        initData();
    }

    @Override
    protected void initData() {
        netRun.GroupHome(keyword, orderType, pagesize, curpage + "");
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_menu1:
//                orderType = "1";
//                setmenu(tv_menu1, v_slider1);
//                break;
//            case R.id.tv_menu2:
//                orderType = "2";
//                setmenu(tv_menu2, v_slider2);
//                break;
//            case R.id.tv_menu3:
//                orderType = "3";
//                setmenu(tv_menu3, v_slider3);
//                break;
//            case R.id.iv_search://搜索
//                String s = et_search.getText().toString();
//                if (TextUtils.isEmpty(s)) {
//                    s = "";
//                }
//                keyword = s;
//                refreshtype = 1;
//                curpage = 1;
//                netRun.GroupHome(keyword, orderType, pagesize, curpage + "");
//                break;
//            case R.id.tv_create://创建
//                Intent intent = new Intent(PostGroupActivity.this, CreateGroupActivity.class);
//                startActivity(intent);
//                break;
//        }
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()== R.id.tv_menu1){
            orderType = "1";
            setmenu(tv_menu1, v_slider1);
        }else if(v.getId()==R.id.tv_menu2){
            orderType = "2";
            setmenu(tv_menu2, v_slider2);
        }else if(v.getId()==R.id.tv_menu3){
            orderType = "3";
            setmenu(tv_menu3, v_slider3);
        }else if(v.getId()==R.id.iv_search){
            //搜索
            String s = et_search.getText().toString();
            if (TextUtils.isEmpty(s)) {
                s = "";
            }
            keyword = s;
            refreshtype = 1;
            curpage = 1;
            netRun.GroupHome(keyword, orderType, pagesize, curpage + "");
        }else if(v.getId()==R.id.tv_create){
            //创建
            Intent intent = new Intent(PostGroupActivity.this, CreateGroupActivity.class);
            startActivity(intent);
        }
    }

    private void setmenu(TextView txt, View v) {
        tv_menu1.setTextColor(0xff000000);
        tv_menu2.setTextColor(0xff000000);
        tv_menu3.setTextColor(0xff000000);
        v_slider1.setVisibility(View.INVISIBLE);
        v_slider2.setVisibility(View.INVISIBLE);
        v_slider3.setVisibility(View.INVISIBLE);
        txt.setTextColor(0xffFF536B);
        v.setVisibility(View.VISIBLE);
        refreshtype = 1;
        curpage = 1;
        netRun.GroupHome(keyword, orderType, pagesize, curpage + "");
    }

    @Override
    public void ReGetData() {

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
            netRun.GroupHome(keyword, orderType, pagesize, curpage + "");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (postGroupInfo!=null&&postGroupInfo.is_nextpage.equals("1")){
                refreshtype = 2;
                curpage++;
                netRun.GroupHome(keyword, orderType, pagesize, curpage + "");
            }else{
                Toast.makeText(PostGroupActivity.this, getString(R.string.find_reminder3), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
            }

        }
    }
}
