package com.aite.a.fargment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aite.a.activity.GoodsDatailsActivity;
import com.aite.a.adapter.GoodsEvaluate2Adapter;
import com.aite.a.model.GoodsEvaluateInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;

/**
 * 评论列表
 * Created by mayn on 2018/11/14.
 */
public class EvaluationFragment extends BaseFragment {
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_list;
    private LinearLayout ll_null;

    private MyListener myListenr;
    private int refreshtype = 0, curpage = 1;
    private GoodsDatailsActivity activity;
    private GoodsEvaluateInfo goodsEvaluateInfo;
    private GoodsEvaluate2Adapter goodsEvaluate2Adapter;
    public String goods_id, type = "0";
    private NetRun netRun;
    public Handler handler = new Handler() {
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
                                int h = (ClutterUtils.getScreenWidth(getActivity())-ClutterUtils.dp2px(getActivity(),22)) / 3;
                                goodsEvaluate2Adapter = new GoodsEvaluate2Adapter(getActivity(), goodsEvaluateInfo.goodsevallist, h);
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
                    Toast.makeText(getActivity(), getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected int layoutResID() {
        return R.layout.activity_goodsevaluate;
    }

    @Override
    protected void initView() {
        refresh_view = layout.findViewById(R.id.refresh_view);
        lv_list = layout.findViewById(R.id.lv_list);
        ll_null = layout.findViewById(R.id.ll_null);
        netRun = new NetRun(getActivity(), handler);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
    }

    @Override
    protected void initData() {
        activity= (GoodsDatailsActivity) getActivity();
        goods_id=activity.goods_id;
        netRun.GoodsEvaluateList(goods_id, type, curpage + "");
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
            netRun.GoodsEvaluateList(goods_id, type, curpage + "");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (goodsEvaluateInfo.is_nextpage.equals("0")) {
                Toast.makeText(getActivity(), getString(R.string.find_reminder3), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
                return;
            }
            refreshtype = 2;
            curpage++;
            netRun.GoodsEvaluateList(goods_id, type, curpage + "");
        }
    }

}
