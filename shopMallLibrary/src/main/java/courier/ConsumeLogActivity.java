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

import courier.adapter.ConsumeLogAdapter;
import courier.model.ConsumeLogInfo;

/**
 * 消费记录
 * Created by Administrator on 2018/1/10.
 */
public class ConsumeLogActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_break,ll_null;
    private TextView tv_name;
    private ConsumeLogAdapter topUpLogAdapter;
    private PullToRefreshLayout refresh_view;
    private PullableListView lv_log;
    private String pagesize="20";
    private int curpage=1,refreshtype = 0;
    private MyListener myListenr;
    private ConsumeLogInfo topupLogInfo;
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case delivery_consume_id://充值记录
                    if (msg.obj!=null){
                        topupLogInfo= (ConsumeLogInfo) msg.obj;
                        if (topupLogInfo==null||topupLogInfo.datas.list==null||topupLogInfo.datas.list.size()==0){
                            ll_null.setVisibility(View.VISIBLE);
                        }else{
                            ll_null.setVisibility(View.GONE);
                            if (refreshtype==0){
                                topUpLogAdapter = new ConsumeLogAdapter(ConsumeLogActivity.this,topupLogInfo.datas.list);
                                lv_log.setAdapter(topUpLogAdapter);
                            }else if (refreshtype==1){
                                topUpLogAdapter.refreshData(topupLogInfo.datas.list);
                                myListenr.refreshSucceed();
                            }else if (refreshtype==2){
                                topUpLogAdapter.addData(topupLogInfo.datas.list);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case delivery_consume_err:
                    Toast.makeText(ConsumeLogActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumelog);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        tv_name = (TextView) findViewById(R.id.tv_name);
        lv_log = (PullableListView) findViewById(R.id.lv_log);
        refresh_view= (PullToRefreshLayout) findViewById(R.id.refresh_view);
        initView();
    }

    @Override
    protected void initView() {
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        tv_name.setText("消费记录");

        netRun=new NetRun(this,handler);
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        initData();
    }

    @Override
    protected void initData() {
        netRun.DeliveryConsume(pagesize,curpage+"");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_break) {
            finish();
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
            netRun.DeliveryConsume(pagesize,curpage+"");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (topupLogInfo.hasmore.equals("true")) {
                refreshtype = 2;
                curpage++;
                netRun.DeliveryConsume(pagesize,curpage+"");
            } else {
                loadMoreSucceed();
                Toast.makeText(ConsumeLogActivity.this,
                        getString(R.string.act_no_data_load),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void ReGetData() {

    }
}
