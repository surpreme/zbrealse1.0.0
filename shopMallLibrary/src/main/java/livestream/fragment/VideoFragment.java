package livestream.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aite.a.view.PullToRefreshLayout;
import com.aiteshangcheng.a.R;

import livestream.adapter.VideoAdapter;

/**
 * 视频
 * Created by Administrator on 2017/9/12.
 */
public class VideoFragment extends BaseFragment implements View.OnClickListener{
    private TextView tv_menu1,tv_menu2,tv_add;
    private RecyclerView rv_list;
    private VideoAdapter videoAdapter;
    private PullToRefreshLayout refresh_view;
    private int refreshtype = 0, curpage = 1;
    private MyListener myListenr;
    @Override
    protected int getlayoutResId() {
        return R.layout.fragment_zb_video;
    }

    private void findviewbyid() {
        tv_menu1= (TextView) layout.findViewById(R.id.tv_menu1);
        tv_menu2= (TextView) layout.findViewById(R.id.tv_menu2);
        tv_add= (TextView) layout.findViewById(R.id.tv_add);
        rv_list= (RecyclerView) layout.findViewById(R.id.rv_list);
        refresh_view= (PullToRefreshLayout) layout.findViewById(R.id.refresh_view);

    }

    @Override
    protected void initView() {
        findviewbyid();
        tv_menu1.setOnClickListener(this);
        tv_menu2.setOnClickListener(this);
        rv_list.setLayoutManager(new GridLayoutManager(getActivity(),2));
        videoAdapter=new VideoAdapter(getActivity());
        rv_list.setAdapter(videoAdapter);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_menu1) {
            tv_menu1.setTextColor(Color.BLACK);
            tv_menu2.setTextColor(0XFF808080);
        } else if (id == R.id.tv_menu2) {
            tv_menu2.setTextColor(Color.BLACK);
            tv_menu1.setTextColor(0XFF808080);
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
            };
        };

        /**
         * 下拉式刷新成功
         * */
        public void refreshSucceed() {
            refreshHandler.sendEmptyMessageDelayed(pull_down_refresh_success,
                    400);
        }

        /**
         * 上拉加载成功
         * */
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
