package livestream.fragment;


import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.a.view.CircleImageView;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;


/**
 * 附近动态
 * Created by Administrator on 2017/9/14.
 */
public class FragmentVicinity2 extends com.aite.a.fargment.BaseFragment {
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

    @Override
    protected int layoutResID() {
        return R.layout.fragment_vicinity2;
    }

    private void findviewbyid() {
        lv_list = (PullableListView) layout.findViewById(R.id.lv_list);
        refresh_view = (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);
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
                convertView = View.inflate(getActivity(), R.layout.item_zblife, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();
            holder.rv_photo.setNestedScrollingEnabled(false);
            holder.rv_photo.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            return convertView;
        }

        class ViewHodler {
            CircleImageView cv_icon;
            TextView tv_name, tv_time, tv_con, tv_number, tv_praise, tv_discuss;
            RecyclerView rv_photo;
            ImageView iv_praise;
            LinearLayout ll_praise, ll_discuss, ll_sharing, ll_more;

            public ViewHodler(View convertView) {
                cv_icon = (CircleImageView) convertView.findViewById(R.id.cv_icon);
                tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                tv_con = (TextView) convertView.findViewById(R.id.tv_con);
                tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                tv_praise = (TextView) convertView.findViewById(R.id.tv_praise);
                tv_discuss = (TextView) convertView.findViewById(R.id.tv_discuss);
                rv_photo = (RecyclerView) convertView.findViewById(R.id.rv_photo);
                iv_praise = (ImageView) convertView.findViewById(R.id.iv_praise);
                ll_praise = (LinearLayout) convertView.findViewById(R.id.ll_praise);
                ll_discuss = (LinearLayout) convertView.findViewById(R.id.ll_discuss);
                ll_sharing = (LinearLayout) convertView.findViewById(R.id.ll_sharing);
                ll_more = (LinearLayout) convertView.findViewById(R.id.ll_more);
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
