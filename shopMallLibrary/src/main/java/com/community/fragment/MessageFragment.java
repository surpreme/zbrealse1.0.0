package com.community.fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.fargment.BaseFragment;
import com.aite.a.model.StationLetterInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.adapter.LetterAdapter;

/**
 * 消息
 * Created by mayn on 2018/10/10.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {
    private ImageView iv_goback;
    private TextView tv_empty, tv_privateletter, tv_ordinary;
    private View v_ordinary, v_privateletter;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;
    private StationLetterInfo letterInfo;

    private LetterAdapter letterAdapter;
    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1, type = 1;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case station_letter_id: // 系统消息列表
                    if (msg.obj != null) {
                        letterInfo = (StationLetterInfo) msg.obj;
                        if (letterInfo.message_array == null || letterInfo.message_array.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || letterAdapter == null) {
                                letterAdapter = new LetterAdapter(getActivity(), letterInfo.message_array);
                                lv_list.setAdapter(letterAdapter);
                            }
                        }
                    }
                    break;
                case station_letter_err:
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected int layoutResID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        iv_goback = (ImageView) layout.findViewById(R.id.iv_goback);
        tv_empty = (TextView) layout.findViewById(R.id.tv_empty);
        tv_privateletter = (TextView) layout.findViewById(R.id.tv_privateletter);
        tv_ordinary = (TextView) layout.findViewById(R.id.tv_ordinary);
        v_ordinary = layout.findViewById(R.id.v_ordinary);
        v_privateletter = layout.findViewById(R.id.v_privateletter);
        refresh_view = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
        lv_list = (PullableListView) layout.findViewById(R.id.lv_list);
        ll_null = (LinearLayout) layout.findViewById(R.id.ll_null);
        iv_goback.setOnClickListener(this);
        tv_empty.setOnClickListener(this);
        tv_ordinary.setOnClickListener(this);
        tv_privateletter.setOnClickListener(this);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        netRun = new NetRun(getActivity(), handler);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();//清空
        if (id == R.id.iv_goback) {
        } else if (id == R.id.tv_empty) {
        } else if (id == R.id.tv_ordinary) {//普通消息
            type = 1;
            tv_ordinary.setTextColor(0xffff3c81);
            tv_privateletter.setTextColor(0xff000000);
            v_ordinary.setVisibility(View.VISIBLE);
            v_privateletter.setVisibility(View.GONE);
        } else if (id == R.id.tv_privateletter) {//站内私信
            type = 2;
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


}
