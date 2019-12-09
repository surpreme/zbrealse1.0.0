package courier;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableListView;
import com.aiteshangcheng.a.R;

import courier.adapter.NoticeAdapter;
import courier.model.NoticeInfo;

/**
 * 通知
 * Created by Administrator on 2018/1/11.
 */
public class NoticeActivity  extends BaseActivity implements View.OnClickListener{

    private LinearLayout ll_break;
    private TextView tv_name,tv_right;
    private PullableListView lv_notice;
    private PullToRefreshLayout refresh_view;
    private NoticeAdapter noticeAdapter;
    private NetRun netRun;
    private String pagesize="20";
    private int curpage=1,refreshtype = 0;
    private MyListener myListenr;
    private NoticeInfo noticeInfo;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case delivery_inform_list_id://通知列表
                    if (msg.obj!=null){
                        noticeInfo= (NoticeInfo) msg.obj;
                        if (refreshtype == 0) {
                            noticeAdapter=new NoticeAdapter(NoticeActivity.this,noticeInfo.datas.list);
                            lv_notice.setAdapter(noticeAdapter);
                        }else if (refreshtype == 1){//刷新
                            noticeAdapter.refreshData(noticeInfo.datas.list);
                            myListenr.refreshSucceed();
                        }else  if (refreshtype == 2){//加载
                            noticeAdapter.addData(noticeInfo.datas.list);
                            myListenr.loadMoreSucceed();
                        }
                    }
                    break;
                case delivery_inform_list_err:
                    Toast.makeText(NoticeActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break= (LinearLayout) findViewById(R.id.ll_break);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_right= (TextView) findViewById(R.id.tv_right);

        lv_notice= (PullableListView) findViewById(R.id.lv_notice);
        refresh_view= (PullToRefreshLayout) findViewById(R.id.refresh_view);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("通知");
        tv_right.setText("全部已读");
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        tv_right.setOnClickListener(this);

        netRun=new NetRun(this,handler);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);

        initData();
    }

    @Override
    protected void initData() {
        netRun.DeliveryInformList(pagesize,curpage+"");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_right) {//全部已读
        } else if (id == R.id.ll_break) {
            finish();
        }
    }

    @Override
    public void ReGetData() {

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
            netRun.DeliveryInformList(pagesize,curpage+"");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (noticeInfo.hasmore.equals("true")) {
                refreshtype = 2;
                curpage++;
                netRun.DeliveryInformList(pagesize,curpage+"");
            } else {
                loadMoreSucceed();
                Toast.makeText(NoticeActivity.this,
                        getString(R.string.act_no_data_load),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
