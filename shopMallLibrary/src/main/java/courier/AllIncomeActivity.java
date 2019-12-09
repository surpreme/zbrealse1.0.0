package courier;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.BalanceTxActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;

import java.util.Calendar;

import courier.View.MonthChoosePopu;
import courier.View.PieChartView;
import courier.adapter.AllIncome2Adapter;
import courier.adapter.AllIncomeAdapter;
import courier.model.AllIncomeInfo;

/**
 * 总收入
 * Created by Administrator on 2018/1/11.
 */
public class AllIncomeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_break;
    private TextView tv_name, tv_rith, tv_allincome, tv_lasttime, tv_settlement, tv_extract, tv_pj, tv_jj, tv_mxname;
    private LinearLayout ll_sr1, ll_sr2, ll_null;
    private PieChartView pc_t;
    private MyListView lv_mx;
    private AllIncomeInfo allIncomeInfo;
    private AllIncomeAdapter allIncomeAdapter;
    private AllIncome2Adapter allIncome2Adapter;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case delivery_money_id://总收入
                    if (msg.obj != null) {
                        allIncomeInfo = (AllIncomeInfo) msg.obj;
                        tv_allincome.setText(allIncomeInfo.money);
                        tv_lasttime.setText("最后更新日期 : " + allIncomeInfo.time_name);
                        tv_settlement.setText(allIncomeInfo.jiesuan);
                        tv_extract.setText(allIncomeInfo.express_deposit);
                        tv_pj.setText(allIncomeInfo.order_money);
                        tv_jj.setText(allIncomeInfo.express_money);
                        pc_t.setData(allIncomeInfo.jiesuan, allIncomeInfo.express_deposit);
                        if (allIncomeInfo.list.order_list == null || allIncomeInfo.list.order_list.size() == 0) {
                            ll_null.setVisibility(View.VISIBLE);
                            lv_mx.setVisibility(View.GONE);
                        } else {
                            ll_null.setVisibility(View.GONE);
                            lv_mx.setVisibility(View.VISIBLE);
                            allIncomeAdapter = new AllIncomeAdapter(AllIncomeActivity.this, allIncomeInfo.list.order_list);
                            lv_mx.setAdapter(allIncomeAdapter);
                        }
                    }
                    break;
                case delivery_money_err:
                    Toast.makeText(AllIncomeActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allincome);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_rith = (TextView) findViewById(R.id.tv_rith);
        tv_allincome = (TextView) findViewById(R.id.tv_allincome);
        tv_lasttime = (TextView) findViewById(R.id.tv_lasttime);
        tv_settlement = (TextView) findViewById(R.id.tv_settlement);
        tv_extract = (TextView) findViewById(R.id.tv_extract);
        tv_pj = (TextView) findViewById(R.id.tv_pj);
        tv_jj = (TextView) findViewById(R.id.tv_jj);
        tv_mxname = (TextView) findViewById(R.id.tv_mxname);
        pc_t = (PieChartView) findViewById(R.id.pc_t);
        lv_mx = (MyListView) findViewById(R.id.lv_mx);
        ll_sr1 = (LinearLayout) findViewById(R.id.ll_sr1);
        ll_sr2 = (LinearLayout) findViewById(R.id.ll_sr2);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        initView();
    }

    @Override
    protected void initView() {
        tv_rith.setOnClickListener(this);
        ll_break.setOnClickListener(this);
        ll_sr1.setOnClickListener(this);
        ll_sr2.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        ll_break.setFocusable(true);
        ll_break.setFocusableInTouchMode(true);
        ll_break.requestFocus();
//        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        tv_name.setText(year + "-" + month);
        netRun.DeliveryMoney(year + "", month + "");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_rith) {//提现
//                Intent intent2 = new Intent(AllIncomeActivity.this, IncomeDatailsActivity.class);
//                startActivity(intent2);
            Intent intent2 = new Intent(AllIncomeActivity.this, BalanceTxActivity.class);
            intent2.putExtra("predepoit", allIncomeInfo.express_deposit);
            startActivity(intent2);
        } else if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.ll_sr1) {//商城收入
            tv_mxname.setText("商城收入明细");
            if (allIncomeInfo.list.order_list == null || allIncomeInfo.list.order_list.size() == 0) {
                ll_null.setVisibility(View.VISIBLE);
                lv_mx.setVisibility(View.GONE);
            } else {
                ll_null.setVisibility(View.GONE);
                lv_mx.setVisibility(View.VISIBLE);
                if (allIncomeAdapter == null) {
                    allIncomeAdapter = new AllIncomeAdapter(AllIncomeActivity.this, allIncomeInfo.list.order_list);
                    lv_mx.setAdapter(allIncomeAdapter);
                } else {
                    lv_mx.setAdapter(allIncomeAdapter);
//                        allIncomeAdapter.refreshData(allIncomeInfo.list.order_list);
                }
            }
        } else if (id == R.id.ll_sr2) {//派件收入
            tv_mxname.setText("派件收入明细");
            if (allIncomeInfo.list.express_list == null || allIncomeInfo.list.express_list.size() == 0) {
                ll_null.setVisibility(View.VISIBLE);
                lv_mx.setVisibility(View.GONE);
            } else {
                ll_null.setVisibility(View.GONE);
                lv_mx.setVisibility(View.VISIBLE);
                if (allIncome2Adapter == null) {
                    allIncome2Adapter = new AllIncome2Adapter(AllIncomeActivity.this, allIncomeInfo.list.express_list);
                    lv_mx.setAdapter(allIncome2Adapter);
                } else {
                    lv_mx.setAdapter(allIncome2Adapter);
//                        allIncome2Adapter.refreshData(allIncomeInfo.list.express_list);
                }
            }
        } else if (id == R.id.tv_name) {
            showpopu();
        }
    }

    /**
     * 弹出时间
     */
    private void showpopu() {
        MonthChoosePopu monthChoosePopu = new MonthChoosePopu(AllIncomeActivity.this);
        monthChoosePopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        monthChoosePopu.setmenu(new MonthChoosePopu.menu() {
            @Override
            public void onItemClick(String year, String month) {
                netRun.DeliveryMoney(year, month);
                tv_name.setText(year + "-" + month);
            }
        });
        monthChoosePopu.showAsDropDown(tv_name, 0, 0);
    }

    @Override
    public void ReGetData() {

    }
}
