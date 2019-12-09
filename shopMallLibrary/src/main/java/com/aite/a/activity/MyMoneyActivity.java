package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.MyMoneyInfo;
import com.aite.a.model.MyMoneyInfo.datas.list_log;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 我的余额
 *
 * @author Administrator
 */
public class MyMoneyActivity extends BaseActivity implements OnClickListener {
    private TextView tv_bctitle, tv_deposit, tv_commission, tv_freeze,
            tv_commission2, tv_nodata;
    private ImageView iv_bcreturn;
    private View v_pointer1, v_pointer2;
    private ListView lv_deposit;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private MyMoneyInfo myMoneyInfo;
    private MyAdapter myAdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case deposit_info_id:
                    // 会员预存款
                    if (msg.obj != null) {
                        myMoneyInfo = (MyMoneyInfo) msg.obj;
                        if (myMoneyInfo.datas.list_log == null
                                || myMoneyInfo.datas.list_log.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                        }
                        tv_freeze
                                .setText(myMoneyInfo.datas.member_info.freeze_predeposit);
                        tv_commission2
                                .setText(myMoneyInfo.datas.member_info.available_predeposit);
                        myAdapter = new MyAdapter(myMoneyInfo.datas.list_log);
                        lv_deposit.setAdapter(myAdapter);
                    }
                    break;
                case deposit_info_err:
                    Toast.makeText(MyMoneyActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case commission_info_id:
                    // 会员佣金明细
                    if (msg.obj != null) {
                        myMoneyInfo = (MyMoneyInfo) msg.obj;
                        if (myMoneyInfo.datas.list_log == null
                                || myMoneyInfo.datas.list_log.size() == 0) {
                            tv_nodata.setVisibility(View.VISIBLE);
                        } else {
                            tv_nodata.setVisibility(View.GONE);
                        }
                        tv_freeze
                                .setText(myMoneyInfo.datas.member_info.freeze_predeposit);
                        tv_commission2
                                .setText(myMoneyInfo.datas.member_info.available_predeposit);
                        myAdapter = new MyAdapter(myMoneyInfo.datas.list_log);
                        lv_deposit.setAdapter(myAdapter);
                    }
                    break;
                case commission_info_err:
                    Toast.makeText(MyMoneyActivity.this,
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
        setContentView(R.layout.activity_mymoney);
        findViewById();
    }


    @Override
    protected void findViewById() {
        iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
        tv_deposit = (TextView) findViewById(R.id.tv_deposit);
        tv_commission = (TextView) findViewById(R.id.tv_commission);
        tv_freeze = (TextView) findViewById(R.id.tv_freeze);
        tv_commission2 = (TextView) findViewById(R.id.tv_commission2);
        tv_bctitle = (TextView) findViewById(R.id._tv_name);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata);
        v_pointer1 = findViewById(R.id.v_pointer1);
        v_pointer2 = findViewById(R.id.v_pointer2);
        lv_deposit = (ListView) findViewById(R.id.lv_deposit);
        tv_bctitle.setText(getString(R.string.deposit_info));
        initView();
    }

    @Override
    protected void initView() {
        iv_bcreturn.setOnClickListener(this);
        tv_deposit.setOnClickListener(this);
        tv_commission.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        boolean booleanExtra = getIntent().getBooleanExtra("isfreeze", false);
        if (!booleanExtra) {
            tv_commission.setTextColor(Color.BLACK);
            v_pointer2.setBackgroundColor(0XFF808080);
            tv_deposit.setTextColor(Color.RED);
            v_pointer1.setBackgroundColor(Color.RED);
            netRun.DepositInfo("20", "1");
            type = "1";
        } else {
            tv_deposit.setTextColor(Color.BLACK);
            v_pointer1.setBackgroundColor(0XFF808080);
            tv_commission.setTextColor(Color.RED);
            v_pointer2.setBackgroundColor(Color.RED);
            netRun.CommissionInfo("20", "1");
            type = "2";
        }

    }

    @Override
    public void ReGetData() {

    }

    private String type = "1";

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_deposit:
//			// 预存款
//			type = "1";
//			tv_commission.setTextColor(Color.BLACK);
//			v_pointer2.setBackgroundColor(0XFF808080);
//			tv_deposit.setTextColor(Color.RED);
//			v_pointer1.setBackgroundColor(Color.RED);
//			netRun.DepositInfo("20", "1");
//			break;
//		case R.id.tv_commission:
//			// 佣金
//			type = "2";
//			tv_deposit.setTextColor(Color.BLACK);
//			v_pointer1.setBackgroundColor(0XFF808080);
//			tv_commission.setTextColor(Color.RED);
//			v_pointer2.setBackgroundColor(Color.RED);
//			netRun.CommissionInfo("20", "1");
//			break;
//		}
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_deposit) {
            // 预存款
            type = "1";
            tv_commission.setTextColor(Color.BLACK);
            v_pointer2.setBackgroundColor(0XFF808080);
            tv_deposit.setTextColor(Color.RED);
            v_pointer1.setBackgroundColor(Color.RED);
            netRun.DepositInfo("20", "1");
        } else if (v.getId() == R.id.tv_commission) {
            // 佣金
            type = "2";
            tv_deposit.setTextColor(Color.BLACK);
            v_pointer1.setBackgroundColor(0XFF808080);
            tv_commission.setTextColor(Color.RED);
            v_pointer2.setBackgroundColor(Color.RED);
            netRun.CommissionInfo("20", "1");
        }

    }

    /**
     * 明细
     *
     * @author Administrator
     */
    private class MyAdapter extends BaseAdapter {
        List<list_log> list_log;

        public MyAdapter(List<list_log> list_log) {
            this.list_log = list_log;
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
                convertView = View.inflate(MyMoneyActivity.this,
                        R.layout.item_mymoney, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            list_log list_log2 = list_log.get(position);
            if (type.equals("1")) {
                holder.tv_keyong.setText(getString(R.string.item_mymoney2)
                        + " " + list_log2.lg_av_amount);
                holder.tv_dongjie.setText(getString(R.string.item_mymoney4)
                        + " " + list_log2.lg_freeze_amount);
                holder.tv_dingdanhao.setText(list_log2.lg_desc);
                if (list_log2.lg_add_time != null) {
                    holder.tv_time.setText(TimeStamp2Date(
                            list_log2.lg_add_time, "yyyy-MM-dd HH:mm"));
                } else {
                    holder.tv_time.setVisibility(View.VISIBLE);
                }
            } else if (type.equals("2")) {
                int parseInt = Integer.parseInt(list_log2.type);
                if (parseInt > 0) {
                    holder.tv_keyong.setText(getString(R.string.item_mymoney2)
                            + " -" + list_log2.commission);
                } else {
                    holder.tv_keyong.setText(getString(R.string.item_mymoney2)
                            + " +" + list_log2.commission);
                }

                holder.tv_dongjie.setText(list_log2.log_desc);
                holder.tv_dingdanhao.setVisibility(View.GONE);
                if (list_log2.lg_add_time != null) {
                    holder.tv_time.setText(TimeStamp2Date(
                            list_log2.lg_add_time, "yyyy-MM-dd HH:mm"));
                } else {
                    holder.tv_time.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView tv_keyong, tv_dingdanhao, tv_time, tv_dongjie;

            public ViewHolder(View convertView) {
                tv_keyong = (TextView) convertView.findViewById(R.id.tv_keyong);
                tv_dongjie = (TextView) convertView
                        .findViewById(R.id.tv_dongjie);
                tv_dingdanhao = (TextView) convertView
                        .findViewById(R.id.tv_dingdanhao);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(this);
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
        try {
            Long timestamp = Long.parseLong(timestampString) * 1000;
            String date = new java.text.SimpleDateFormat(formats)
                    .format(new java.util.Date(timestamp));
            return date;
        } catch (Exception e) {
            return timestampString;
        }

    }

}
