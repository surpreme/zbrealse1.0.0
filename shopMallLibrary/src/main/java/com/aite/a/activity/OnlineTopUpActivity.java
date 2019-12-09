package com.aite.a.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.RechargeableCardInfo;
import com.aite.a.model.TopUpInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CustomScrollView;
import com.aite.a.view.CustomScrollView.OnScrollListener;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;

/**
 * 在线充值
 *
 * @author Administrator
 */
public class OnlineTopUpActivity extends BaseActivity implements
        OnClickListener, OnScrollListener {
    private TextView tv_tab1, tv_tab2, tv_tab3, tv_tab4, tv_zxsubmit, _tv_name,
            tv_remaining, tv_freeze, tv_search, norecord, tv_remaining2,
            tv_freeze2, tv_ts;
    private EditText ed_je;
    private ImageView _iv_back;
    private NetRun netRun;
    private TopUpInfo topUpInfo;
    private LinearLayout ll_pager1, ll_pager2, ll_con, ll_pager3;
    private EditText ed_order;
    private MyListView mlv_data;
    private Madapter madapter;
    private CustomScrollView csl_cs;
    private RechargeableCardInfo rechargeableCardInfo;
    private Cadapter cadapter;
    private ListView lv_card;
    private boolean isonline = true;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case topup_info_id:
                    if (msg.obj != null) {
                        topUpInfo = (TopUpInfo) msg.obj;
                        madapter = new Madapter(topUpInfo.datas.list);
                        mlv_data.setAdapter(madapter);
                    }
                    break;
                case topup_info_err:
                    Toast.makeText(OnlineTopUpActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case topup_infodel_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            madapter.deldata(p);
                        } else {
                            Toast.makeText(OnlineTopUpActivity.this, str,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case topup_infodel_err:
                    Toast.makeText(OnlineTopUpActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case online_topup_id:
                    //在线充值
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        ed_je.setText("");
                        tv_tab2.performClick();
                    }
                    break;
                case online_topup_err:
                    Toast.makeText(OnlineTopUpActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case rechargeable_card_id:
                    if (msg.obj != null) {
                        rechargeableCardInfo = (RechargeableCardInfo) msg.obj;
                        if (rechargeableCardInfo.datas.list.size() == 0) {
                            norecord.setVisibility(View.VISIBLE);
                        } else {
                            norecord.setVisibility(View.GONE);
                        }
                        tv_remaining2
                                .setText(rechargeableCardInfo.datas.member_info.available_rc_balance);
                        tv_freeze2
                                .setText(rechargeableCardInfo.datas.member_info.freeze_rc_balance);
                        cadapter = new Cadapter(rechargeableCardInfo.datas.list);
                        lv_card.setAdapter(cadapter);
                    }
                    break;
                case rechargeable_card_err:
                    Toast.makeText(OnlineTopUpActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case card_topup_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            Toast.makeText(OnlineTopUpActivity.this, getString(R.string.systembusy),
                                    Toast.LENGTH_SHORT).show();
                            ed_je.setText("");
                        } else {
                            Toast.makeText(OnlineTopUpActivity.this, str,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinetopup);
        findViewById();
    }

    @Override
    protected void findViewById() {
        tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
        tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
        tv_tab3 = (TextView) findViewById(R.id.tv_tab3);
        tv_tab4 = (TextView) findViewById(R.id.tv_tab4);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_zxsubmit = (TextView) findViewById(R.id.tv_zxsubmit);
        ed_je = (EditText) findViewById(R.id.ed_je);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        tv_remaining = (TextView) findViewById(R.id.tv_remaining);
        tv_freeze = (TextView) findViewById(R.id.tv_freeze);
        tv_search = (TextView) findViewById(R.id.tv_search);
        ll_pager1 = (LinearLayout) findViewById(R.id.ll_pager1);
        ll_pager2 = (LinearLayout) findViewById(R.id.ll_pager2);
        ed_order = (EditText) findViewById(R.id.ed_order);
        mlv_data = (MyListView) findViewById(R.id.mlv_data);
        ll_con = (LinearLayout) findViewById(R.id.ll_con);
        csl_cs = (CustomScrollView) findViewById(R.id.csl_cs);
        ll_pager3 = (LinearLayout) findViewById(R.id.ll_pager3);
        norecord = (TextView) findViewById(R.id.norecord);
        tv_remaining2 = (TextView) findViewById(R.id.tv_remaining2);
        tv_freeze2 = (TextView) findViewById(R.id.tv_freeze2);
        lv_card = (ListView) findViewById(R.id.lv_card);
        tv_ts = (TextView) findViewById(R.id.tv_ts);
        _iv_back.setOnClickListener(this);
        tv_tab1.setOnClickListener(this);
        tv_tab2.setOnClickListener(this);
        tv_tab3.setOnClickListener(this);
        tv_tab4.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_zxsubmit.setOnClickListener(this);
        csl_cs.setOnScrollListener(this);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        _tv_name.setText(getString(R.string.app_name));
        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            // 退出
            finish();
        } else if (v.getId() == R.id.tv_zxsubmit) {
            if (isonline) {
                // 充值
                if (TextUtils.isEmpty(ed_je.getText().toString())) {
                    Toast.makeText(OnlineTopUpActivity.this,
                            getString(R.string.topup7), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                netRun.OnlineTopup(ed_je.getText().toString());
            } else {
                // 充值卡
                if (TextUtils.isEmpty(ed_je.getText().toString())) {
                    Toast.makeText(OnlineTopUpActivity.this, getString(R.string.member_centre31),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                netRun.Cardtopup(ed_je.getText().toString());
            }
        } else if (v.getId() == R.id.tv_tab1) {
            isonline = true;
            tv_ts.setText(getString(R.string.member_centre32));
            ll_pager1.setVisibility(View.VISIBLE);
            ll_pager2.setVisibility(View.GONE);
            ll_pager3.setVisibility(View.GONE);
            tv_tab1.setBackgroundColor(Color.WHITE);
            tv_tab2.setBackgroundResource(R.drawable.gray_solid);
            tv_tab3.setBackgroundResource(R.drawable.gray_solid);
            tv_tab4.setBackgroundResource(R.drawable.gray_solid);
        } else if (v.getId() == R.id.tv_tab2) {
            ed_je.setText("");
            ll_pager2.setVisibility(View.VISIBLE);
            ll_pager1.setVisibility(View.GONE);
            ll_pager3.setVisibility(View.GONE);
            tv_tab2.setBackgroundColor(Color.WHITE);
            tv_tab1.setBackgroundResource(R.drawable.gray_solid);
            tv_tab3.setBackgroundResource(R.drawable.gray_solid);
            tv_tab4.setBackgroundResource(R.drawable.gray_solid);
            netRun.YYTopUp("1", "");
        } else if (v.getId() == R.id.tv_tab3) {
            ll_pager3.setVisibility(View.VISIBLE);
            ll_pager1.setVisibility(View.GONE);
            ll_pager2.setVisibility(View.GONE);
            tv_tab3.setBackgroundColor(Color.WHITE);
            tv_tab1.setBackgroundResource(R.drawable.gray_solid);
            tv_tab2.setBackgroundResource(R.drawable.gray_solid);
            tv_tab4.setBackgroundResource(R.drawable.gray_solid);
            netRun.RechargeableCard("1");
        } else if (v.getId() == R.id.tv_tab4) {
            isonline = false;
            tv_ts.setText(getString(R.string.member_centre33));
            ll_pager1.setVisibility(View.VISIBLE);
            ll_pager2.setVisibility(View.GONE);
            ll_pager3.setVisibility(View.GONE);
            tv_tab4.setBackgroundColor(Color.WHITE);
            tv_tab1.setBackgroundResource(R.drawable.gray_solid);
            tv_tab2.setBackgroundResource(R.drawable.gray_solid);
            tv_tab3.setBackgroundResource(R.drawable.gray_solid);
        } else if (v.getId() == R.id.tv_search) {
            // 搜索
            String string = ed_order.getText().toString();
            if (string.isEmpty()) {
                Toast.makeText(OnlineTopUpActivity.this, getString(R.string.member_centre34),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.YYTopUp("1", string);
        }
//		switch (v.getId()) {
//		case R.id._iv_back:
//			// 退出
//			finish();
//			break;
//		case R.id.tv_zxsubmit:
//			if (isonline) {
//				// 充值
//				if (TextUtils.isEmpty(ed_je.getText().toString())) {
//					Toast.makeText(OnlineTopUpActivity.this,
//							getString(R.string.topup7), Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				netRun.OnlineTopup(ed_je.getText().toString());
//			} else {
//				// 充值卡
//				if (TextUtils.isEmpty(ed_je.getText().toString())) {
//					Toast.makeText(OnlineTopUpActivity.this, getString(R.string.member_centre31),
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				netRun.Cardtopup(ed_je.getText().toString());
//			}
//			break;
//			case R.id.tv_tab1:
//			isonline = true;
//			tv_ts.setText(getString(R.string.member_centre32));
//			ll_pager1.setVisibility(View.VISIBLE);
//			ll_pager2.setVisibility(View.GONE);
//			ll_pager3.setVisibility(View.GONE);
//			tv_tab1.setBackgroundColor(Color.WHITE);
//			tv_tab2.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab3.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab4.setBackgroundResource(R.drawable.gray_solid);
//			break;
//		case R.id.tv_tab2:
//			ed_je.setText("");
//			ll_pager2.setVisibility(View.VISIBLE);
//			ll_pager1.setVisibility(View.GONE);
//			ll_pager3.setVisibility(View.GONE);
//			tv_tab2.setBackgroundColor(Color.WHITE);
//			tv_tab1.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab3.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab4.setBackgroundResource(R.drawable.gray_solid);
//			netRun.YYTopUp("1", "");
//			break;
//		case R.id.tv_tab3:
//			ll_pager3.setVisibility(View.VISIBLE);
//			ll_pager1.setVisibility(View.GONE);
//			ll_pager2.setVisibility(View.GONE);
//			tv_tab3.setBackgroundColor(Color.WHITE);
//			tv_tab1.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab2.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab4.setBackgroundResource(R.drawable.gray_solid);
//			netRun.RechargeableCard("1");
//			break;
//		case R.id.tv_tab4:
//			isonline = false;
//			tv_ts.setText(getString(R.string.member_centre33));
//			ll_pager1.setVisibility(View.VISIBLE);
//			ll_pager2.setVisibility(View.GONE);
//			ll_pager3.setVisibility(View.GONE);
//			tv_tab4.setBackgroundColor(Color.WHITE);
//			tv_tab1.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab2.setBackgroundResource(R.drawable.gray_solid);
//			tv_tab3.setBackgroundResource(R.drawable.gray_solid);
//			break;
//		case R.id.tv_search:
//			// 搜索
//			String string = ed_order.getText().toString();
//			if (string.isEmpty()) {
//				Toast.makeText(OnlineTopUpActivity.this, getString(R.string.member_centre34),
//						Toast.LENGTH_SHORT).show();
//				return;
//			}
//			netRun.YYTopUp("1", string);
//			break;
//		}
    }

    int p = 0;

    /**
     * 明细列表
     *
     * @author Administrator
     */
    class Madapter extends BaseAdapter {
        List<TopUpInfo.datas.list> list;

        public Madapter(List<TopUpInfo.datas.list> list) {
            this.list = list;
        }

        public void deldata(int pos) {
            this.list.remove(pos);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list == null ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(OnlineTopUpActivity.this,
                        R.layout.topupinfo_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final TopUpInfo.datas.list list2 = list
                    .get(position);
            holder.tv_order.setText(list2.pdr_sn);
            holder.tv_time.setText(TimeStamp2Date(list2.pdr_add_time,
                    "yyyy-MM-dd HH:mm"));
            holder.tv_pay.setText(list2.pdr_payment_name);
            holder.tv_money.setText("+" + list2.pdr_amount);
            holder.tv_state.setText(list2.pdr_payment_state.equals("0") ? getString(R.string.member_centre35)
                    : getString(R.string.m_paid));
            holder.tv_zf.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 支付
                    if (list2.pdr_payment_state.equals("0")) {
                        Intent intent2 = new Intent(OnlineTopUpActivity.this,
                                TopupPayActivity.class);
                        intent2.putExtra("pay_sn", list2.pdr_sn);
                        startActivity(intent2);
                    } else {
                        Toast.makeText(OnlineTopUpActivity.this, getString(R.string.m_paid), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.tv_del.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 删除
                    p = position;
                    netRun.YYTopUpdel(list2.pdr_id);
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_order, tv_time, tv_pay, tv_money, tv_state, tv_zf,
                    tv_del;

            public ViewHolder(View convertView) {
                tv_order = (TextView) convertView.findViewById(R.id.tv_order);
                tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
                tv_money = (TextView) convertView.findViewById(R.id.tv_money);
                tv_state = (TextView) convertView.findViewById(R.id.tv_state);
                tv_zf = (TextView) convertView.findViewById(R.id.tv_zf);
                tv_del = (TextView) convertView.findViewById(R.id.tv_del);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 充值卡余额
     *
     * @author Administrator
     */
    class Cadapter extends BaseAdapter {
        private List<RechargeableCardInfo.datas.list> list;

        public Cadapter(
                List<RechargeableCardInfo.datas.list> list) {
            this.list = list;
        }

        public void deldata(int pos) {
            this.list.remove(pos);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list == null ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(OnlineTopUpActivity.this,
                        R.layout.rechargeablecard_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_cj.setText(list.get(position).add_time);
            holder.tv_sr.setText("+" + list.get(position).available_amount);
            holder.tv_zc.setText("");
            holder.tv_dj.setText(list.get(position).freeze_amount);
            holder.tv_bg.setText(list.get(position).description);
            return convertView;
        }

        class ViewHolder {
            TextView tv_cj, tv_sr, tv_zc, tv_dj, tv_bg;

            public ViewHolder(View convertView) {
                tv_cj = (TextView) convertView.findViewById(R.id.tv_cj);
                tv_sr = (TextView) convertView.findViewById(R.id.tv_sr);
                tv_zc = (TextView) convertView.findViewById(R.id.tv_zc);
                tv_dj = (TextView) convertView.findViewById(R.id.tv_dj);
                tv_bg = (TextView) convertView.findViewById(R.id.tv_bg);
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
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }

    @Override
    public void onScroll(int scrollY) {
        View childView = ll_con.getChildAt(0);
        if (scrollY == (childView.getHeight() - csl_cs.getHeight())) {
            // 滑动到底部
        }
    }
}
