package com.community.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.StationLetterInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.adapter.LetterAdapter;

/**
 * 朋友圈消息
 * Created by mayn on 2018/9/20.
 */
public class MessageActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback;
    private TextView tv_empty, tv_privateletter, tv_ordinary;
    private View v_ordinary, v_privateletter;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;
    private StationLetterInfo letterInfo;

    private LetterAdapter letterAdapter;
    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1,type=1;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case station_letter_id: // 系统消息列表
                    if (msg.obj != null) {
                        letterInfo = (StationLetterInfo) msg.obj;
                        if (letterInfo.message_array==null||letterInfo.message_array.size()==0){
                            ll_null.setVisibility(View.VISIBLE);
                        }else{
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype==0||letterAdapter==null){
                                letterAdapter=new LetterAdapter(MessageActivity.this,letterInfo.message_array);
                                lv_list.setAdapter(letterAdapter);
                            }
                        }
                    }
                    break;
                case station_letter_err:
                    Toast.makeText(appSingleton, getString(R.string.sure_confirm), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        tv_privateletter = (TextView) findViewById(R.id.tv_privateletter);
        tv_ordinary = (TextView) findViewById(R.id.tv_ordinary);
        v_ordinary = findViewById(R.id.v_ordinary);
        v_privateletter = findViewById(R.id.v_privateletter);
        refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_list = (PullableListView) findViewById(R.id.lv_list);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        tv_empty.setOnClickListener(this);
        tv_ordinary.setOnClickListener(this);
        tv_privateletter.setOnClickListener(this);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        netRun=new NetRun(this,handler);
        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_empty://清空
//
//                break;
//            case R.id.tv_ordinary://普通消息
//                type=1;
//                tv_ordinary.setTextColor(0xffff3c81);
//                tv_privateletter.setTextColor(0xff000000);
//                v_ordinary.setVisibility(View.VISIBLE);
//                v_privateletter.setVisibility(View.GONE);
//                break;
//            case R.id.tv_privateletter://站内私信
//                type=2;
//                tv_privateletter.setTextColor(0xffff3c81);
//                tv_ordinary.setTextColor(0xff000000);
//                v_privateletter.setVisibility(View.VISIBLE);
//                v_ordinary.setVisibility(View.GONE);
//                refreshtype = 1;
//                curpage = 1;
//                netRun.stationLetterList(null);// 传null返回所有站内信
//                break;
//        }
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if (v.getId()==R.id.tv_ordinary){
            //普通消息
            type=1;
            tv_ordinary.setTextColor(0xffff3c81);
            tv_privateletter.setTextColor(0xff000000);
            v_ordinary.setVisibility(View.VISIBLE);
            v_privateletter.setVisibility(View.GONE);
        }else if(v.getId()==R.id.tv_privateletter){
            type=2;
            tv_privateletter.setTextColor(0xffff3c81);
            tv_ordinary.setTextColor(0xff000000);
            v_privateletter.setVisibility(View.VISIBLE);
            v_ordinary.setVisibility(View.GONE);
            refreshtype = 1;
            curpage = 1;
            netRun.stationLetterList(null);// 传null返回所有站内信
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
            Log.i("-----------------", "刷新  ");
//            netRun.ForumList(keyword, pagesize, curpage + "", orderType);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
//            if (forumListInfo != null && forumListInfo.is_nextpage.equals("1")) {
//                refreshtype = 2;
//                curpage++;
//                netRun.ForumList(keyword, pagesize, curpage + "", orderType);
//            } else {
//                Toast.makeText(MessageActivity.this, "没有更多了", Toast.LENGTH_SHORT).show();
//                loadMoreSucceed();
//            }

        }
    }


    @Override
    public void ReGetData() {

    }
}
