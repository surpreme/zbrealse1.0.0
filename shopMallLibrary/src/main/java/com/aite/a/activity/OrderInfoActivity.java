package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.aite.a.model.OrderInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * 订单详情
 *
 * @author Administrator
 */
public class OrderInfoActivity extends BaseActivity implements OnClickListener {
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private ImageView _iv_back;
    private TextView _tv_name, order_time, order_state, order_serialnumber,
            tv_refund, tv_complaints, tv_gtotalprices, tv_freight,
            tv_totalprices, tv_consignoraddress, tv_consignorphone,
            tv_companyname, tv_consignor, tv_storeaddress, tv_storephone,
            tv_storename, tv_invoice, tv_send_time, tv_leaveamessage,
            tv_address, tv_phone, tv_consignee;
    private OrderInfo orderInfo;
    private MyListView mlv_goods;
    private mAdapter mdapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case order_info_id:
                    if (msg.obj != null) {
                        orderInfo = (OrderInfo) msg.obj;
                        if (orderInfo.add_time != null) {
                            order_time.setText(getString(R.string.order_reminder1) + TimeStamp2Date(orderInfo.add_time, "yyyy-MM-dd HH:mm"));
                        } else {
                            order_time.setText(getString(R.string.order_reminder1));
                        }

                        order_serialnumber.setText(getString(R.string.order_reminder2) + orderInfo.order_sn);
                        order_state.setText(orderInfo.state_desc);
                        tv_gtotalprices.setText("￥" + orderInfo.goods_amount);
                        tv_freight.setText("￥" + orderInfo.shipping_fee);
                        tv_totalprices.setText("￥" + orderInfo.order_amount);
                        if (orderInfo.extend_order_common != null) {
                            tv_consignee
                                    .setText(orderInfo.extend_order_common.reciver_name);
                            tv_phone.setText(orderInfo.extend_order_common.reciver_info.mob_phone);
                            tv_address
                                    .setText(orderInfo.extend_order_common.reciver_info.address);
                            tv_leaveamessage
                                    .setText(orderInfo.extend_order_common.order_message);
                        }
                        tv_send_time.setText("");
                        tv_invoice.setText("");
                        if (orderInfo.extend_store != null) {
                            tv_storename.setText(orderInfo.extend_store.store_name);
                        }
                        tv_storephone.setText("");
                        tv_storeaddress.setText("");
                        tv_consignor.setText("");
                        tv_companyname.setText("");
                        tv_consignorphone.setText("");
                        tv_consignoraddress.setText("");
                        if (orderInfo.if_refund_cancel.equals("false")) {
                            tv_refund.setVisibility(View.GONE);
                        } else if (orderInfo.if_refund_cancel.equals("true")) {
                            tv_refund.setVisibility(View.VISIBLE);
                        }
                        if (orderInfo.if_complain.equals("false")) {
                            tv_complaints.setVisibility(View.GONE);
                        } else if (orderInfo.if_complain.equals("false")) {
                            tv_complaints.setVisibility(View.VISIBLE);
                        }

                        mdapter = new mAdapter(orderInfo.goods_list);
                        mlv_goods.setAdapter(mdapter);

                    }
                    break;
                case order_info_err:
                    Toast.makeText(OrderInfoActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    private String orderid;
    private String order_sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderinfo);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        order_time = (TextView) findViewById(R.id.order_time);
        order_state = (TextView) findViewById(R.id.order_state);
        order_serialnumber = (TextView) findViewById(R.id.order_serialnumber);
        tv_refund = (TextView) findViewById(R.id.tv_refund);
        tv_complaints = (TextView) findViewById(R.id.tv_complaints);
        tv_gtotalprices = (TextView) findViewById(R.id.tv_gtotalprices);
        tv_freight = (TextView) findViewById(R.id.tv_freight);
        tv_totalprices = (TextView) findViewById(R.id.tv_totalprices);
        tv_consignoraddress = (TextView) findViewById(R.id.tv_consignoraddress);
        tv_consignorphone = (TextView) findViewById(R.id.tv_consignorphone);
        tv_companyname = (TextView) findViewById(R.id.tv_companyname);
        tv_consignor = (TextView) findViewById(R.id.tv_consignor);
        tv_storeaddress = (TextView) findViewById(R.id.tv_storeaddress);
        tv_storephone = (TextView) findViewById(R.id.tv_storephone);
        tv_storename = (TextView) findViewById(R.id.tv_storename);
        tv_invoice = (TextView) findViewById(R.id.tv_invoice);
        tv_send_time = (TextView) findViewById(R.id.tv_send_time);
        tv_leaveamessage = (TextView) findViewById(R.id.tv_leaveamessage);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_consignee = (TextView) findViewById(R.id.tv_consignee);
        mlv_goods = (MyListView) findViewById(R.id.mlv_goods);
        _iv_back.setOnClickListener(this);
        tv_refund.setOnClickListener(this);
        tv_complaints.setOnClickListener(this);

        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.order_datails));
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        orderid = getIntent().getStringExtra("goods_id");
        order_sn = getIntent().getStringExtra("order_sn");
        initData();
    }

    @Override
    protected void initData() {
        netRun.OrderInfo(orderid);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_refund) {
            // 退款
            Intent intent = new Intent(OrderInfoActivity.this,
                    OrderRefundActivity.class);
            intent.putExtra("order_sn", order_sn);
            startActivity(intent);
        } else if (v.getId() == R.id.tv_complaints) {
            // 投诉
            Intent intent2 = new Intent(OrderInfoActivity.this,
                    ComplaintsApplyForActivity.class);
            intent2.putExtra("goods_id", orderInfo.goods_list.get(0).rec_id);
            intent2.putExtra("order_id", orderInfo.order_id);
            startActivity(intent2);
        }
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_refund:
//			// 退款
//			Intent intent = new Intent(OrderInfoActivity.this,
//					OrderRefundActivity.class);
//			intent.putExtra("order_sn", order_sn);
//			startActivity(intent);
//			break;
//		case R.id.tv_complaints:
//			// 投诉
//			Intent intent2 = new Intent(OrderInfoActivity.this,
//					ComplaintsApplyForActivity.class);
//			intent2.putExtra("goods_id", orderInfo.goods_list.get(0).rec_id);
//			intent2.putExtra("order_id", orderInfo.order_id);
//			startActivity(intent2);
//			break;
//		}
    }

    /**
     * 商品适配
     *
     * @author Administrator
     */
    private class mAdapter extends BaseAdapter {
        List<OrderInfo.goods_list> goods_list;

        public mAdapter(List<OrderInfo.goods_list> goods_list) {
            this.goods_list = goods_list;
        }

        @Override
        public int getCount() {
            return goods_list == null ? 0 : goods_list.size();
        }

        public String getGoodsID() {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < goods_list.size(); i++) {
                if (i == goods_list.size() - 1) {
                    stringBuffer.append(goods_list.get(i).goods_id);
                } else {
                    stringBuffer.append(goods_list.get(i).goods_id + ",");
                }
            }
            return stringBuffer.toString();
        }

        @Override
        public Object getItem(int position) {
            return goods_list == null ? null : goods_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(OrderInfoActivity.this,
                        R.layout.orderinfo_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            OrderInfo.goods_list goods_list2 = goods_list
                    .get(position);
            bitmapUtils.display(holder.iv_orderimg, goods_list2.image_240_url);
            holder.order_name.setText(goods_list2.goods_name);
            holder.tv_orderprice.setText("￥" + goods_list2.goods_price);
            holder.tv_goodsnum.setText(getString(R.string.order_reminder3) + goods_list2.goods_num);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_orderimg;
            TextView order_name, tv_orderprice, tv_goodsnum;

            public ViewHolder(View convertView) {
                iv_orderimg = (ImageView) convertView
                        .findViewById(R.id.iv_orderimg);
                order_name = (TextView) convertView
                        .findViewById(R.id.order_name);
                tv_orderprice = (TextView) convertView
                        .findViewById(R.id.tv_orderprice);
                tv_goodsnum = (TextView) convertView
                        .findViewById(R.id.tv_goodsnum);
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
