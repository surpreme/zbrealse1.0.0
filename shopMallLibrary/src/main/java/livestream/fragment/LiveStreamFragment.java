package livestream.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aiteshangcheng.a.R;

import livestream.adapter.ZbHomeListAdapter;
import livestream.mode.LiveBroadcastListInfo;

/**
 * 直播
 * Created by Administrator on 2017/9/12.
 */
public class LiveStreamFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView rv_list;
    private PullToRefreshLayout refresh_view;
    private ImageView iv_service;
    private TextView tv_menu1, tv_menu2;
    private LinearLayout ll_null;
    private LiveBroadcastListInfo liveBroadcastListInfo;

    private ZbHomeListAdapter zbHomeListAdapter;
    private int refreshtype = 0, curpage = 1;
    private MyListener myListenr;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case livebroadcast_list_id://直播列表
                    if (msg.obj != null) {
                        liveBroadcastListInfo = (LiveBroadcastListInfo) msg.obj;
                        if (liveBroadcastListInfo.datas.list == null || liveBroadcastListInfo.datas.list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype == 0 || zbHomeListAdapter == null) {
                                zbHomeListAdapter = new ZbHomeListAdapter(getActivity(), liveBroadcastListInfo.datas.list);
                                rv_list.setAdapter(zbHomeListAdapter);
                            } else if (refreshtype == 1) {
                                zbHomeListAdapter.refreshData(liveBroadcastListInfo.datas.list);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                zbHomeListAdapter.addData(liveBroadcastListInfo.datas.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case livebroadcast_list_err:
                    Toast.makeText(getActivity(), getActivity().getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected int getlayoutResId() {
        return R.layout.fragment_zb_home;
    }

    private void findviewbyid() {
        rv_list = layout.findViewById(R.id.rv_list);
        iv_service = layout.findViewById(R.id.iv_service);
        tv_menu1 = layout.findViewById(R.id.tv_menu1);
        tv_menu2 = layout.findViewById(R.id.tv_menu2);
        refresh_view = layout.findViewById(R.id.refresh_view);
        ll_null = layout.findViewById(R.id.ll_null);
    }

    @Override
    protected void initView() {
        findviewbyid();
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        iv_service.setOnClickListener(this);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        netRun = new NetRun(getActivity(), handler);
    }

    @Override
    protected void initData() {
        netRun.LiveBroadcastList(curpage + "");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();//搜索
        if (id == R.id.tv_menu1) {//热门
            tv_menu1.setTextColor(Color.BLACK);
            tv_menu2.setTextColor(0XFF808080);
        } else if (id == R.id.tv_menu2) {//最新
            tv_menu2.setTextColor(Color.BLACK);
            tv_menu1.setTextColor(0XFF808080);
        } else if (id == R.id.iv_service) {
        }
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        private PullToRefreshLayout pullToRefreshLayout;

        private Handler refreshHandler = new Handler() {

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
            netRun.LiveBroadcastList(curpage + "");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (liveBroadcastListInfo != null && liveBroadcastListInfo.hasmore.equals("true")) {
                refreshtype = 2;
                curpage++;
                netRun.LiveBroadcastList(curpage + "");
            } else {
                loadMoreSucceed();
                Toast.makeText(getActivity(),
                        getString(R.string.act_no_data_load),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
