package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegralTypeInfo;
import com.aite.a.model.MyIntegralInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.IntegralDetail2Popu;
import com.aite.a.view.IntegralDetailPopu;
import com.aite.a.view.PullToRefreshLayout;
import com.aite.a.view.PullableScrollView;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;

import java.util.Calendar;
import java.util.List;

/**
 * 会员积分明细
 *
 * @author Administrator
 */
public class IntegralInfoActivity extends BaseActivity implements
        OnClickListener {
    private ImageView iv_bcreturn, iv_iocn1, iv_iocn2;
    private TextView tv_bctitle, tv_all, tv_rules, tv_nodata, tv_date, tv_filter;
    private ListView lv_list;
    private LinearLayout ll_top, ll_menu1, ll_menu2;
    private PullToRefreshLayout refresh_view;
    private PullableScrollView sv_scro;
    private List<IntegralTypeInfo> integralTypeInfo;
    private NetRun netRun;
    private MyIntegralInfo myIntegralInfo;
    private String type = "", time = "";
    private int refreshtype = 0, curpage = 1;
    private MyListener myListenr;
    private MyAdapter myAdapter;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case integral_info_id:
                    // 会员预存款
                    if (msg.obj != null) {
                        myIntegralInfo = (MyIntegralInfo) msg.obj;
                        tv_all.setText(myIntegralInfo.datas.member_info.member_points);
                        List<MyIntegralInfo.datas.list_log> list_log = myIntegralInfo.datas.list_log;
                        if (list_log == null || list_log.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
                            lv_list.setVisibility(View.INVISIBLE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                            lv_list.setVisibility(View.VISIBLE);
                            if (myAdapter == null || refreshtype == 0) {
                                myAdapter = new MyAdapter(list_log);
                                lv_list.setAdapter(myAdapter);
                            } else if (refreshtype == 1) {
                                myAdapter.refreshData(list_log);
                                myListenr.refreshSucceed();
                            } else if (refreshtype == 2) {
                                myAdapter.addData(list_log);
                                myListenr.loadMoreSucceed();
                            }
                        }
                    }
                    break;
                case integral_info_err:
                    Toast.makeText(IntegralInfoActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case integral_type_id://积分类型
                    if (msg.obj != null) {
                        integralTypeInfo = (List<IntegralTypeInfo>) msg.obj;

                    }
                    break;
                case integral_type_err:
                    Toast.makeText(IntegralInfoActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        findViewById();
    }


    @Override
    protected void findViewById() {
        iv_bcreturn = findViewById(R.id._iv_back);
        tv_bctitle = findViewById(R.id._tv_name);
        tv_all = findViewById(R.id.tv_all);
        tv_rules = findViewById(R.id.tv_rules);
        tv_nodata = findViewById(R.id.tv_nodata);
        lv_list = findViewById(R.id.lv_list);
        ll_top = findViewById(R.id.ll_top);
        ll_menu1 = findViewById(R.id.ll_menu1);
        ll_menu2 = findViewById(R.id.ll_menu2);
        tv_date = findViewById(R.id.tv_date);
        iv_iocn1 = findViewById(R.id.iv_iocn1);
        iv_iocn2 = findViewById(R.id.iv_iocn2);
        tv_filter = findViewById(R.id.tv_filter);
        refresh_view = findViewById(R.id.refresh_view);
        sv_scro = findViewById(R.id.sv_scro);
        tv_bctitle.setText(getString(R.string.item_mymoney7));
        initView();
    }

    @Override
    protected void initView() {
        myListenr = new MyListener();
        refresh_view.setOnRefreshListener(myListenr);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_top.getLayoutParams();
        layoutParams.height = (int) (ClutterUtils.getScreenWidth(this) * 1.101);
        ll_top.setLayoutParams(layoutParams);
        iv_bcreturn.setOnClickListener(this);
        tv_rules.setOnClickListener(this);
        ll_menu1.setOnClickListener(this);
        ll_menu2.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        tv_date.setText(getData());
        initData();
    }

    private String getData() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String data = year + " 年 " + month + " 月";
        return data;
    }

    @Override
    protected void initData() {
        netRun.IntegralInfo("20", "1", type, time);
        netRun.IntegralType();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_rules:
//                //规则
//                Intent intent2 = new Intent(IntegralInfoActivity.this, IntegralRulesActivity.class);
//                startActivity(intent2);
//                break;
//            case R.id.ll_menu1://时间
//                showpopw();
//                break;
//            case R.id.ll_menu2://筛选
//                showpopw2();
//                break;
//        }
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_rules) {
            //规则
            Intent intent2 = new Intent(IntegralInfoActivity.this, IntegralRulesActivity.class);
            startActivity(intent2);
        } else if (v.getId() == R.id.ll_menu1) {
            showpopw();
        } else if (v.getId() == R.id.ll_menu2) {
            showpopw2();
        }
    }

    /**
     * 选时间
     */
    private void showpopw() {
        tv_date.setTextColor(0xffFD4249);
        iv_iocn1.setImageResource(R.drawable.integral_detail_icon2);
        IntegralDetailPopu integralDetailPopu = new IntegralDetailPopu(IntegralInfoActivity.this);
        integralDetailPopu.setmenu(new IntegralDetailPopu.menu() {
            @Override
            public void onItemClick(String tear, String month) {
                time = tear + "-" + (month.length() == 1 ? "0" + month : month) + "-01 00:00:00";
//                Log.i("-------------", "选择时间  " + time);
                refreshtype = 1;
                curpage = 1;
                netRun.IntegralInfo("20", "1", type, time);
                tv_date.setText(tear + getString(R.string.member_centre10) + month + getString(R.string.member_centre11));
            }
        });
        integralDetailPopu.showAsDropDown(ll_menu1, 0, 0);
        integralDetailPopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv_date.setTextColor(Color.BLACK);
                iv_iocn1.setImageResource(R.drawable.integral_detail_icon1);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 选时间
     */
    private void showpopw2() {
        tv_filter.setTextColor(0xffFD4249);
        iv_iocn2.setImageResource(R.drawable.integral_detail_icon2);
        IntegralDetail2Popu integralDetailPopu = new IntegralDetail2Popu(IntegralInfoActivity.this, integralTypeInfo);
        integralDetailPopu.setmenu(new IntegralDetail2Popu.menu() {
            @Override
            public void onItemClick(IntegralTypeInfo integralTypeInfo) {
                if (integralTypeInfo == null) return;
                type = integralTypeInfo.type;
                netRun.IntegralInfo("20", "1", type, time);
            }
        });
        integralDetailPopu.showAsDropDown(ll_menu1, 0, 0);
        integralDetailPopu.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tv_filter.setTextColor(Color.BLACK);
                iv_iocn2.setImageResource(R.drawable.integral_detail_icon1);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    /**
     * 明细
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {
        List<MyIntegralInfo.datas.list_log> list_log;

        public MyAdapter(
                List<MyIntegralInfo.datas.list_log> list_log) {
            this.list_log = list_log;
        }

        /**
         * 刷新
         *
         * @param list_log
         */
        public void refreshData(List<MyIntegralInfo.datas.list_log> list_log) {
            if (list_log == null) return;
            this.list_log = list_log;
            notifyDataSetChanged();
        }

        /**
         * 添加更多
         *
         * @param list_log
         */
        public void addData(List<MyIntegralInfo.datas.list_log> list_log) {
            if (list_log == null) return;
            this.list_log.addAll(list_log);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list_log == null ? 0 : list_log.size();
        }

        @Override
        public Object getItem(int position) {
            return list_log == null ? null : list_log.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(IntegralInfoActivity.this,
                        R.layout.item_integral, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            MyIntegralInfo.datas.list_log list_log2 = list_log
                    .get(position);

            if (list_log2.pl_points.contains("-")) {
                holder.tv_number.setText(list_log2.pl_points);
            } else {
                holder.tv_number.setText("+" + list_log2.pl_points);
            }
            String data = list_log2.pl_desc.replace("<br>", " ");
            holder.tv_instructions.setText(data);
            holder.tv_time.setText(TimeStamp2Date(list_log2.pl_addtime,
                    "yyyy-MM-dd"));
            return convertView;
        }

        class ViewHolder {
            TextView tv_number, tv_instructions, tv_time;

            public ViewHolder(View convertView) {
                tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                tv_instructions = (TextView) convertView
                        .findViewById(R.id.tv_instructions);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
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
            netRun.IntegralInfo("20", "1", type, time);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            this.pullToRefreshLayout = pullToRefreshLayout;
            if (myIntegralInfo.hasmore.equals("true")) {
                refreshtype = 2;
                curpage++;
                netRun.IntegralInfo("20", "1", type, time);
            } else {
                Toast.makeText(IntegralInfoActivity.this, getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
                loadMoreSucceed();
            }
        }
    }

    /**
     * 时间戳转时间
     *
     * @param timestampString 时间戳
     * @param formats         格式(yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }


}
