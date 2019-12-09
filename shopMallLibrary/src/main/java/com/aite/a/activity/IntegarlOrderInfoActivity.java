package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegarlOrderInfo;
import com.aite.a.model.IntegarlOrderInfo.prod_list;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 积分订单详情
 *
 * @author Administrator
 */
public class IntegarlOrderInfoActivity extends BaseActivity implements
        OnClickListener {
    private TextView _tv_name, tv_consignee, tv_phone, tv_address, tv_time,
            tv_num, tv_state, tv_total, tv_company, tv_number, tv_cancel;
    private ImageView _iv_back;
    private MyListView mlv_goods;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private IntegarlOrderInfo integarlOrderInfo;
    private Gadapter gadapter;
    private String stringExtra;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case integarlorder_info_id:
                    if (msg.obj != null) {
                        integarlOrderInfo = (IntegarlOrderInfo) msg.obj;
                        tv_consignee
                                .setText(getString(R.string.evaluation_tips29)
                                        + integarlOrderInfo.orderaddress_info.point_truename);
                        tv_phone.setText(getString(R.string.evaluation_tips30)
                                + integarlOrderInfo.orderaddress_info.point_mobphone
                                + " "
                                + integarlOrderInfo.orderaddress_info.point_telphone);
                        tv_address
                                .setText(getString(R.string.evaluation_tips31)
                                        + integarlOrderInfo.orderaddress_info.point_areainfo
                                        + " "
                                        + integarlOrderInfo.orderaddress_info.point_address);
                        tv_time.setText(getString(R.string.evaluation_tips32)
                                + TimeStamp2Date(
                                integarlOrderInfo.order_info.point_addtime,
                                "yyyy-MM-dd HH:mm"));
                        tv_num.setText(getString(R.string.verification_prompt17)
                                + integarlOrderInfo.order_info.point_ordersn);
                        tv_state.setText(integarlOrderInfo.order_info.point_orderstatetext);
                        tv_total.setText(getString(R.string.verification_prompt18) + integarlOrderInfo.order_info.point_allpoint);
                        tv_company.setText("物流公司: " + (integarlOrderInfo.express_info == null ? "暂无" : integarlOrderInfo.express_info.e_name));
                        tv_number.setText("物流单号: " + (integarlOrderInfo.express_info == null ? "暂无" : integarlOrderInfo.order_info.point_shippingcode));
                        if (integarlOrderInfo.order_info.point_orderallowcancel.equals("true")) {
                            tv_cancel.setVisibility(View.VISIBLE);
                        } else {
                            tv_cancel.setVisibility(View.GONE);
                        }
                        gadapter = new Gadapter(integarlOrderInfo.prod_list,
                                integarlOrderInfo.order_info.point_addtime);
                        mlv_goods.setAdapter(gadapter);
                    }
                    break;
                case integarlorder_info_err:
                    Toast.makeText(IntegarlOrderInfoActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case jfcancel_order_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("success")) {
                            netRun.IntegarlOrderInfo(stringExtra);
                        } else {
                            Toast.makeText(IntegarlOrderInfoActivity.this, str,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case jfcancel_order_err:
                    Toast.makeText(IntegarlOrderInfoActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integralorderinfo);
        findViewById();
    }


    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_consignee = (TextView) findViewById(R.id.tv_consignee);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_time = (TextView) findViewById(R.id.tv_time);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_company = (TextView) findViewById(R.id.tv_company);
        tv_number = (TextView) findViewById(R.id.tv_number);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        mlv_goods = (MyListView) findViewById(R.id.mlv_goods);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.evaluation_tips33));
        _iv_back.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        stringExtra = getIntent().getStringExtra("order_id");
        netRun.IntegarlOrderInfo(stringExtra);
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
//            case R.id.tv_cancel:
//                netRun.CancelExchange(stringExtra);
//                break;
//        }
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_cancel) {
            netRun.CancelExchange(stringExtra);
        }
    }

    /**
     * 商品适配
     *
     * @author Administrator
     */
    private class Gadapter extends BaseAdapter {
        List<prod_list> prod_list;
        String point_addtime = "";

        public Gadapter(List<prod_list> prod_list, String point_addtime) {
            this.prod_list = prod_list;
            this.point_addtime = point_addtime;
        }

        @Override
        public int getCount() {
            return prod_list.size();
        }

        @Override
        public Object getItem(int position) {
            return prod_list == null ? null : prod_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(IntegarlOrderInfoActivity.this,
                        R.layout.exchangerecord2_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            prod_list prod_list2 = prod_list.get(position);
            bitmapUtils.display(holder.iv_gimg, prod_list2.point_goodsimage);
            holder.tv_jfgname.setText(prod_list2.point_goodsname);
            holder.tv_jfnum.setText(getString(R.string.evaluation_tips34) + prod_list2.point_goodsnum);
            holder.tv_jfintegral.setText(getString(R.string.verification_prompt20) + prod_list2.point_goodspoints
                    + "");
            // holder.tv_jftime.setText("兑换时间 : "
            // + TimeStamp2Date(point_addtime, "yyyy-MM-dd HH:mm"));
            holder.tv_jftime.setVisibility(View.GONE);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_gimg;
            TextView tv_jfgname, tv_jfnum, tv_jfintegral, tv_jftime;

            public ViewHolder(View convertView) {
                iv_gimg = (ImageView) convertView.findViewById(R.id.iv_gimg);
                tv_jfgname = (TextView) convertView
                        .findViewById(R.id.tv_jfgname);
                tv_jfnum = (TextView) convertView.findViewById(R.id.tv_jfnum);
                tv_jfintegral = (TextView) convertView
                        .findViewById(R.id.tv_jfintegral);
                tv_jftime = (TextView) convertView.findViewById(R.id.tv_jftime);
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

}
