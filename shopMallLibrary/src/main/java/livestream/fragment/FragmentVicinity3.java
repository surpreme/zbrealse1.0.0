package livestream.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;

/**
 * 附近的人
 * Created by Administrator on 2017/9/14.
 */
public class FragmentVicinity3 extends com.aite.a.fargment.BaseFragment {
    private PullableListView lv_list;
    private int refreshtype = 0, curpage = 1;
    private MyListener myListenr;
    private PullToRefreshLayout refresh_view;
    private MyAdapter myAdapter;

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

    private void findviewbyid() {
        lv_list = (PullableListView) layout.findViewById(R.id.lv_list);
        refresh_view = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
    }

    @Override
    protected int layoutResID() {
        return R.layout.fragment_vicinity3;
    }

    @Override
    protected void initView() {
        findviewbyid();
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        myAdapter = new MyAdapter();
        lv_list.setAdapter(myAdapter);
    }

    @Override
    protected void initData() {

    }

    /**
     * 列表
     */
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_zbpeople, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();

            return convertView;
        }

        class ViewHodler {
            ImageView iv_icon, iv_sex;
            TextView tv_name, tv_level, tv_motto, tv_distance;

            public ViewHodler(View convertView) {
                iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                iv_sex = (ImageView) convertView.findViewById(R.id.iv_sex);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_level = (TextView) convertView.findViewById(R.id.tv_level);
                tv_motto = (TextView) convertView.findViewById(R.id.tv_motto);
                tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
                convertView.setTag(this);
            }
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
