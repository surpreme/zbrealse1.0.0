package livestream.fragment;


import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.a.view.PullToRefreshLayout;
import com.aiteshangcheng.a.R;

import livestream.adapter.ZbHomeListAdapter;

/**
 * 附近的人
 * Created by Administrator on 2017/9/14.
 */
public class FragmentVicinity1 extends com.aite.a.fargment.BaseFragment {
    private RecyclerView rv_list;
    private ZbHomeListAdapter zbHomeListAdapter;
    private int refreshtype = 0, curpage = 1;
    private MyListener myListenr;
    private PullToRefreshLayout refresh_view;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (refresh_view == null) {
            return;
        }
        if (!isVisibleToUser) {
            //不可见
                refresh_view.hide();
        } else {
            //可见
            refresh_view.hide();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_vicinity1;
    }

    private void findviewbyid() {
        rv_list = (RecyclerView) layout.findViewById(R.id.rv_list);
        refresh_view = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
    }

    @Override
    protected void initView() {
        findviewbyid();
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        zbHomeListAdapter = new ZbHomeListAdapter(getActivity());
//        rv_list.setAdapter(zbHomeListAdapter);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
    }

    @Override
    protected void initData() {

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
//            netRun.RichList("20", curpage+"");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
//            if (richInfo.hasmore.equals("true")) {
//                refreshtype = 2;
//                curpage++;
//                netRun.RichList("20", curpage+"");
//            } else {
//                loadMoreSucceed();
//                Toast.makeText(getActivity(),
//                        getString(R.string.act_no_data_load),
//                        Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
