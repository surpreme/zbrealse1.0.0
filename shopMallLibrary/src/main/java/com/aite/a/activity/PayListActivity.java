package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.Orderdetails;
import com.aite.a.model.Orderdetails2;
import com.aite.a.model.PayListInfo;
import com.aite.a.model.ZfbGetConfigInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.lingshi;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.aiteshangcheng.a.wxapi.WXPayEntryActivity;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;
import java.util.Map;

/**
 * 支付列表
 *
 * @author CAOYOU
 */
public class PayListActivity extends BaseActivity implements OnClickListener {
    private ListView pay_list;
    private Button pay_bt;
    private String pay_sn, goods_name, describe, price, type;
    private boolean isvr;
    private ScrollView sv_vr;
    public String payment = null;
    public Context context = this;
    public PayListAdapter payListAdapter;
    private TextView tv_namees, tv_jieshaoos, tv_time1, tv_time2, tv_time3,
            tv_time4, tv_names, tv_pricee, tv_zongjia, tv_storename, tv_goodsname, tv_goodsprice, tv_goodsnum, tv_totalamount;
    private LinearLayout ll_timing;
    private ImageView iv_touxiang, iv_goodsimg;
    private Button bt_zhifubaopay;
    private NetRun netRun;
    private Orderdetails orderdetails;
    // private RelativeLayout rl_storebh;
    private PreviewAdapder previewadapder;
    private ListView lv_preview;
    private TextView tv_pricee2, tv_zongj;
    private View goodsbt = null;
    private Orderdetails2 orderdetails2;
    private ZfbGetConfigInfo zfbGetConfigInfo;
    private List<PayListInfo> payListInfo;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case 0:
//                    if (msg.obj != null) {
//                        payList = (List<String[]>) msg.obj;
//                        setPayment(payList.get(0)[0]);
//                        setListViewHeightBasedOnChildren(pay_list);
//                        payListAdapter.notifyDataSetChanged();
//                    } else {
//                        CommonTools.showShortToast(context,
//                                getI18n(R.string.act_no_data));
//                    }
//                    break;
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    @SuppressWarnings("unused")
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(
                                PayListActivity.this,
                                APPSingleton.getContext()
                                        .getString(R.string.pay_success).toString(),
                                Toast.LENGTH_SHORT).show();
                        if (type != null && type.equals("order")) {
                            finish();
                        } else {
                            Intent intent2 = new Intent(PayListActivity.this, BuyerOrderFgActivity.class);
                            startActivity(intent2);
                            finish();
                        }
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(
                                    PayListActivity.this,
                                    APPSingleton
                                            .getContext()
                                            .getString(
                                                    R.string.payment_result_confirm)
                                            .toString(), Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(
                                    PayListActivity.this,
                                    APPSingleton.getContext()
                                            .getString(R.string.payment_fail)
                                            .toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(
                            PayListActivity.this,
                            APPSingleton.getContext()
                                    .getString(R.string.check_result).toString()
                                    + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                case orderdetails_id:
                    if (msg.obj != null) {
                        orderdetails = (Orderdetails) msg.obj;
                        LogUtils.d(msg.obj);
                        // 订单总价和邮费
                        tv_pricee2.setText("￥" + orderdetails.fee_all);
                        tv_zongj.setText("￥" + orderdetails.price_all);
                        price = orderdetails.price_all;
                        //商品列表
                        previewadapder = new PreviewAdapder(orderdetails);
                        lv_preview.setAdapter(previewadapder);
                    }
                    break;
                case orderdetails_err:

                    break;
                case zfb_getConfig_id:
                    //支付宝
                    if (msg.obj != null) {
                        zfbGetConfigInfo = (ZfbGetConfigInfo) msg.obj;
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(PayListActivity.this);
                                Map<String, String> result = alipay.payV2("", true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                    break;
                case zfb_getConfig_err:
                    Toast.makeText(PayListActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case get_paymentList_id:
                    //支付列表
                    if (msg.obj != null) {
                        payListInfo = (List<PayListInfo>) msg.obj;
                        if (payListInfo != null && payListInfo.size() != 0) {
                            payListInfo.get(0).ischoose = true;
                            payListAdapter = new PayListAdapter(payListInfo);
                            pay_list.setAdapter(payListAdapter);
                        }
                    }
                    break;
                case get_paymentList_err:
                    Toast.makeText(PayListActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case zfb_pay_id:
                    //支付宝
                    if (msg.obj != null) {
                        Map map = (Map) msg.obj;
                        String key = (String) map.get("0");
                        final String orderinfo = (String) map.get("1");
                        if (key.equals("0")) {
                            Toast.makeText(PayListActivity.this, orderinfo, Toast.LENGTH_SHORT).show();
                        } else if (key.equals("1")) {
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(PayListActivity.this);
                                    Map<String, String> result = alipay.payV2(orderinfo, true);
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    handler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                    }
                    break;
                case zfb_pay_err:
                    Toast.makeText(PayListActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case orderdetails2_id://虚拟订单
                    if (msg.obj != null) {
                        sv_vr.setVisibility(View.VISIBLE);
                        lv_preview.setVisibility(View.GONE);
                        orderdetails2 = (Orderdetails2) msg.obj;
                        Glide.with(PayListActivity.this).load(orderdetails2.goods_image_url).into(iv_goodsimg);
                        tv_storename.setText(orderdetails2.store_name);
                        tv_goodsname.setText(orderdetails2.goods_name);
                        tv_goodsnum.setText(getString(R.string.order_reminder32) + orderdetails2.goods_num);
                        tv_totalamount.setText(getString(R.string.order_reminder33) + orderdetails2.order_amount);
                        tv_goodsprice.setText(getString(R.string.order_reminder34) + orderdetails2.goods_price);
                        price = orderdetails2.order_amount;
                    }
                    break;
                case orderdetails2_err:
                    Toast.makeText(PayListActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_list);
        findViewById();
        initView();
        initData();

    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        pay_list = (ListView) findViewById(R.id.pay_list);
        pay_bt = (Button) findViewById(R.id.pay_bt);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_goodsimg = (ImageView) findViewById(R.id.iv_goodsimg);
        tv_title_name = (TextView) findViewById(R.id._tv_name);
        tv_title_name.setText(getI18n(R.string.pay_wayfcf));
        // tv_namees = (TextView) findViewById(R.id.tv_namees);
        // tv_jieshaoos = (TextView) findViewById(R.id.tv_jieshaoos);
        tv_time1 = (TextView) findViewById(R.id.tv_time1);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        tv_time3 = (TextView) findViewById(R.id.tv_time3);
        tv_time4 = (TextView) findViewById(R.id.tv_time4);
        tv_storename = (TextView) findViewById(R.id.tv_storename);
        tv_goodsname = (TextView) findViewById(R.id.tv_goodsname);
        tv_goodsprice = (TextView) findViewById(R.id.tv_goodsprice);
        tv_goodsnum = (TextView) findViewById(R.id.tv_goodsnum);
        tv_totalamount = (TextView) findViewById(R.id.tv_totalamount);
        ll_timing = (LinearLayout) findViewById(R.id.ll_timing);
        sv_vr = (ScrollView) findViewById(R.id.sv_vr);
        // iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
        // rl_storebh = (RelativeLayout) findViewById(R.id.rl_storebh);
        // tv_names = (TextView) findViewById(R.id.tv_names);
        // tv_pricee = (TextView) findViewById(R.id.tv_pricee);
        // tv_zongjia = (TextView) findViewById(R.id.tv_zongjia);
        lv_preview = (ListView) findViewById(R.id.lv_preview);
        bt_zhifubaopay = (Button) findViewById(R.id.bt_zhifubaopay);
        goodsbt = View.inflate(this, R.layout.paygoodsbuttom, null);
        lv_preview.addFooterView(goodsbt);
        tv_pricee2 = (TextView) goodsbt.findViewById(R.id.tv_pricee2);
        tv_zongj = (TextView) goodsbt.findViewById(R.id.tv_zongj);

    }


    private int a = 2;
    private int b = 9;
    private int c = 5;
    private int d = 9;
    private int msgg = 0;

    /**
     * 倒计时
     */
    private void timing() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (a != 0) {
                    if (msgg == 0) {
                        if (d != 0) {
                            d--;
                        } else {
                            d = 9;
                            msgg = 1;
                        }
                    }
                    if (msgg == 1) {

                        if (c != 0) {
                            c--;
                            msgg = 0;
                        } else {
                            c = 5;
                            msgg = 2;
                        }
                    }
                    if (msgg == 2) {
                        if (b != 0) {
                            b--;
                            msgg = 0;
                        } else {
                            b = 9;
                            msgg = 3;
                        }
                    }
                    if (msgg == 2) {
                        if (a != 0) {
                            a--;
                            msgg = 0;
                        } else {
                            CommonTools.showShortToast(context, getString(R.string.order_reminder35));
                            finish();
                        }
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            settexttt();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void settexttt() {
        tv_time1.setText(a + "");
        tv_time2.setText(b + "");
        tv_time3.setText(c + "");
        tv_time4.setText(d + "");
    }

    @Override
    protected void initView() {
        pay_bt.setOnClickListener(this);
        // rl_storebh.setOnClickListener(this);

        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    BitmapUtils bt;

    @Override
    protected void initData() {
        bt = new BitmapUtils(context);
        pay_sn = getIntent().getStringExtra("pay_sn");
        goods_name = getIntent().getStringExtra("goods");
        describe = getIntent().getStringExtra("describe");
        isvr = getIntent().getBooleanExtra("isvr", false);
        type = getIntent().getStringExtra("type");
        netRun = new NetRun(this, handler);
        if (isvr) {//虚拟订单
            netRun.order_del2(pay_sn);
        } else {
            netRun.order_del(pay_sn);
        }
        netRun.GetPaymentList();
//        timing();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 商品列表组
     *
     * @author Administrator
     */
    public class PreviewAdapder extends BaseAdapter {
        private Orderdetails orderdetails;

        public PreviewAdapder(Orderdetails orderdetails) {
            this.orderdetails = orderdetails;
        }

        @Override
        public int getCount() {
            return orderdetails == null ? 0 : orderdetails.order_list.size();
        }

        @Override
        public Object getItem(int position) {
            return orderdetails == null ? null : orderdetails.order_list
                    .get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.paypreview_item,
                        null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            final Orderdetails.order_list data = orderdetails.order_list.get(position);
            holder.tv_names1.setText(data.store_name);
            PreviewChildAdapder previewChildAdapder = new PreviewChildAdapder(data.goods_list);
            holder.mlv_goodsl.setAdapter(previewChildAdapder);
            holder.rl_storebh1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String store_id = lingshi.getInstance().getStore_id();
                    Intent intent = new Intent(PayListActivity.this,
                            StoreDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("store_name", data.store_name);
                    bundle.putString("store_id", store_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder {
            MyListView mlv_goodsl;
            RelativeLayout rl_storebh1;
            TextView tv_names1;

            public ViewHolder(View convertView) {
                mlv_goodsl = (MyListView) convertView
                        .findViewById(R.id.mlv_goodsl);
                rl_storebh1 = (RelativeLayout) convertView.findViewById(R.id.rl_storebh1);
                tv_names1 = (TextView) convertView.findViewById(R.id.tv_names1);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 商品列表子
     *
     * @author Administrator
     */
    public class PreviewChildAdapder extends BaseAdapter {
        List<Orderdetails.goods_list> goods_list;

        public PreviewChildAdapder(List<Orderdetails.goods_list> goods_list) {
            this.goods_list = goods_list;
        }

        @Override
        public int getCount() {
            return goods_list.size();
        }

        @Override
        public Object getItem(int position) {
            return goods_list == null ? null : goods_list
                    .get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.paygoodslist_item,
                        null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            Orderdetails.goods_list goods_list2 = goods_list.get(position);
            bt.display(holder.iv_touxiang1, goods_list2.goods_image_url);
            holder.tv_jieshaoos1.setText(goods_list2.goods_name);
            holder.tv_namees1.setText("￥" + goods_list2.goods_price);
            holder.tv_goods_num1.setText(getString(R.string.num) + goods_list2.goods_num);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_touxiang1;
            TextView tv_jieshaoos1, tv_namees1, tv_goods_num1;

            public ViewHolder(View convertView) {
                tv_jieshaoos1 = (TextView) convertView.findViewById(R.id.tv_jieshaoos1);
                tv_namees1 = (TextView) convertView.findViewById(R.id.tv_namees1);
                tv_goods_num1 = (TextView) convertView.findViewById(R.id.tv_goods_num1);
                iv_touxiang1 = (ImageView) convertView.findViewById(R.id.iv_touxiang1);
                convertView.setTag(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pay_bt) {
            pay_bt.setEnabled(false);
            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
            builder2.setTitle(getI18n(R.string.tip));// 设置对话框标题
            builder2.setItems(new String[]{getI18n(R.string.pay_or_not)},
                    null);
            builder2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    pay_bt.setEnabled(true);
                }
            });
            builder2.setNegativeButton(getI18n(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    pay_bt.setEnabled(true);
                }
            });
            builder2.setPositiveButton(getI18n(R.string.confirm),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (payListAdapter.getchoose().equals("app_wxpay")) {
                                Intent intent = new Intent(PayListActivity.this,
                                        WXPayEntryActivity.class);
                                intent.putExtra("goods", goods_name);
                                intent.putExtra("describe", describe);
                                intent.putExtra("price", price);
                                intent.putExtra("pay_sn", pay_sn);
                                intent.putExtra("isvr", isvr);
                                intent.putExtra("payment_code", payListAdapter.getchoose());
                                startActivityForResult(intent, 10010);
                            } else if (payListAdapter.getchoose().equals("alipay")) {
                                //TODO
                                if (isvr) {
                                    netRun.vrPay(pay_sn, payListAdapter.getchoose());
                                } else {
                                    netRun.ZfbPay(pay_sn, payListAdapter.getchoose());
                                }
//								bt_zhifubaopay.performClick();
                                // Intent intent = new Intent(context,
                                // AlipayActivity.class);
                                // intent.putExtra("goods", goods_name);
                                // intent.putExtra("describe", describe);
                                // intent.putExtra("price", price);
                                // intent.putExtra("pay_sn", pay_sn);
                                // intent.putExtra("payment_code", "alipay");
                                // startActivity(intent);
                            }
                        }
                    });
            builder2.show();
        }

//        switch (v.getId()) {
//            case R.id.pay_bt:
////                if (payment == null) {
////                    CommonTools.showShortToast(context,
////                            getI18n(R.string.select_pay_way));
////                    return;
////                }
//                pay_bt.setEnabled(false);
//                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
//                builder2.setTitle(getI18n(R.string.tip));// 设置对话框标题
//                builder2.setItems(new String[]{getI18n(R.string.pay_or_not)},
//                        null);
//                builder2.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialogInterface) {
//                        pay_bt.setEnabled(true);
//                    }
//                });
//                builder2.setNegativeButton(getI18n(R.string.cancel), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        pay_bt.setEnabled(true);
//                    }
//                });
//                builder2.setPositiveButton(getI18n(R.string.confirm),
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (payListAdapter.getchoose().equals("app_wxpay")) {
//                                    Intent intent = new Intent(PayListActivity.this,
//                                            WXPayEntryActivity.class);
//                                    intent.putExtra("goods", goods_name);
//                                    intent.putExtra("describe", describe);
//                                    intent.putExtra("price", price);
//                                    intent.putExtra("pay_sn", pay_sn);
//                                    intent.putExtra("isvr", isvr);
//                                    intent.putExtra("payment_code", payListAdapter.getchoose());
//                                    startActivityForResult(intent, 10010);
//                                } else if (payListAdapter.getchoose().equals("alipay")) {
//                                    //TODO
//                                    if (isvr){
//                                        netRun.vrPay(pay_sn,payListAdapter.getchoose());
//                                    }else{
//                                        netRun.ZfbPay(pay_sn,payListAdapter.getchoose());
//                                    }
////								bt_zhifubaopay.performClick();
//                                    // Intent intent = new Intent(context,
//                                    // AlipayActivity.class);
//                                    // intent.putExtra("goods", goods_name);
//                                    // intent.putExtra("describe", describe);
//                                    // intent.putExtra("price", price);
//                                    // intent.putExtra("pay_sn", pay_sn);
//                                    // intent.putExtra("payment_code", "alipay");
//                                    // startActivity(intent);
//                                }
//                            }
//                        });
//                builder2.show();
//                break;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 10010:
                if (type != null && type.equals("order")) {
                    finish();
                } else {
                    Intent intent2 = new Intent(PayListActivity.this, BuyerOrderFgActivity.class);
                    startActivity(intent2);
                    finish();
                }
                break;

        }
    }

    /**
     * 支付列表
     */
    public class PayListAdapter extends BaseAdapter {
        List<PayListInfo> payListInfo;

        public PayListAdapter(List<PayListInfo> payListInfo) {
            this.payListInfo = payListInfo;
        }

        /**
         * 获取选中方式
         *
         * @return
         */
        private String getchoose() {
            for (int i = 0; i < payListInfo.size(); i++) {
                if (payListInfo.get(i).ischoose == true) {
                    return payListInfo.get(i).payment_code;
                }
            }
            return "alipay";
        }

        @Override
        public int getCount() {
            return payListInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return payListInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.pay_list_item, null);
                new ViewHodler(convertView);
            }
            ViewHodler holder = (ViewHodler) convertView.getTag();
            PayListInfo payInfo = this.payListInfo.get(position);
            bt.display(holder.iv_zhifubao, payInfo.payment_image);
            holder.tv_biaoti.setText(payInfo.payment_name);
            holder.rb_statefcf.setChecked(payInfo.ischoose);
            holder.rl_item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < payListInfo.size(); i++) {
                        payListInfo.get(i).ischoose = false;
                    }
                    payListInfo.get(position).ischoose = true;
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHodler {
            ImageView iv_zhifubao;
            TextView tv_biaoti;
            CheckBox rb_statefcf;
            RelativeLayout rl_item;

            public ViewHodler(View convertView) {
                iv_zhifubao = (ImageView) convertView.findViewById(R.id.iv_zhifubao);
                tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
                rb_statefcf = (CheckBox) convertView.findViewById(R.id.rb_statefcf);
                rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 支付宝支付
     */
    // 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
    public static final String PARTNER = "2088911494702106";
    // 商户收款的支付宝账号
    public static final String SELLER = "caiwu@cnaite.cn";
    // 商户（RSA）私钥(注意一定要转PKCS8格式，否则在Android4.0及以上系统会支付失败)
    // public static final String RSA_PRIVATE =
    // "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKiKbb9W9zUVQZFc3JxEa+2USc+3lvRdNRVodUzxVipGvlpyayp34hDL9sRJg9isaH5oRigqUY90+4dHZ/O+Q47/m8ODcUrrRwi1eck4YhftDRXVkbZuXN40TZQQLRNa0AO6rwEuHXqm54dlC8COnesDZvO5uu/Hfs+SwwicjbKFAgMBAAECgYBeY4BJG0xBF1FsNmR6w7rqCBU6VmgdHKIhBipDWBlYfdwXTWMFGrSpkYD2tvE3BH9U49L+pYyd2fcP8QaftBui7owWNUh//CRQSxCOuepI525ceIaHQWZ2MiMafkoWYpf2er0pfGX0fK37+TLT6IuPYFWa60M0TwAb2u/4aeiidQJBANCftJ4URSTdg26Pc0P5i6kJyPcg0TOBGD1N2gmRcS7hKwXam39NzmsDjv4JDDd4Gf2NQvgJ+9/qA+NbFSybwD8CQQDO0IBo9DkD5NdFePzuQaZuN1kDVM8bI3pokbpdZk484SzYxFNQY4qm/m9/ZW76toaCuq2h/UoD8DPq+RUT/pw7AkBajIbbedbM+mwaZ57S6RPqCXo1cQP8A9MegJTbREpI26hNwSVuqrmmDwRYVFr+FF3LhC2F9Odit3J6ksTs+KkHAkEAtXb9NRoVbPU+bhba17OC9PJbiDDCCG342ggEHi/GNmTdlNWuxLqSzjQyYPX/irzdCZgDAj/c0dfRtjsX45NkQQJAatfeHxcdWBb5jP9ApRT5EiHPYaFpuyrKsYNTw7T0dWwFT1ihDA23Gao76yqvvEhIkR0MWEhcgjH2n6uXAsTXvA==";
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALNqaQchugJV9qSPKVPrI/N60myWbhIqZoduc46IQ6snjteDby0nqclyCm4tvz5A5+q+/TjsUvhdVa6unDWiJyfhlQBPOCinRnQoo27Hsu++ufH81BJsKASCGrVT7BzI5c5Qv2efXUv684hCLygc5ZHATQUz9uEFEz+MbztxwYFfAgMBAAECgYEAju8gWzkoSkIX64POJff3vTrFNxMk/73dztlUoGG4nzs1lkY0pSGrJzx+SMaTTFJDMQYVEGtdslYRM8gR1LypOcvfJkhXmEEf/Hsacc52z0hc7g9GK3mkvcRSrrCv9uX+D301FYrQ/QoGEM1l0sXokz9WOxG+0mBmjvBrfFgbLhkCQQDc+/f5yB1XtiFkR++L778RRg40eNlWYGzAYV9W24h/cVxq1SuyRe5rvIxFLOHzM/muBSma4GFD1A8stH1bfbjzAkEAz9g/np5F+o5ebmZtODgm4VFH8uAnhJSoEv80xwiMxAKUtFiNT0PbjnHIvO0XbBI/6uQexY4s4BQPFNnUcUyw5QJAVOJcsI/T5ZtKXFDdjS08gZAdaL57DZjgbU15581QM1QCIe9cZ5BBMxUr9G53JIp0gAnvn1RNSFautYdnF7vFTQJBAJaH7yPJS7N48ymQI2BJQteDT9G2yMg8BjKkBSx1o8W+fMbVL5sN8XEMNa+nI4SQ/xv3FdZM73Fm9blLPpLEUYUCQEwy84ilK0ogsjgH3HBOkKd1qVtCHL1K8geStMWFktK0MHhuZ+Wx6A2awOdKxM+Jqju7rXKG/zMPNUfLVtrSFSA=";
    // 支付宝（RSA）公钥用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
    // public static final String RSA_ALIPAY_PUBLIC =
    // "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

//	/**
//	 * call alipay sdk pay. 调用SDK支付
//	 *
//	 */
//	public void pay(View v) {
//		// 订单
//		String orderInfo = getOrderInfo(goods_name, describe,
//				String.valueOf(price), pay_sn);
//		// 对订单做RSA 签名
//		String sign = sign(orderInfo);
//		try {
//			// 仅需对sign 做URL编码
//			sign = URLEncoder.encode(sign, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//
//		// 完整的符合支付宝参数规范的订单信息
//		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
//				+ getSignType();
//
//		Runnable payRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask alipay = new PayTask(PayListActivity.this);
//				// 调用支付接口，获取支付结果
//				String result = alipay.pay(payInfo);
//				Message msg = new Message();
//				msg.what = SDK_PAY_FLAG;
//				msg.obj = result;
//				handler.sendMessage(msg);
//			}
//		};
//
//		// 必须异步调用
//		Thread payThread = new Thread(payRunnable);
//		payThread.start();
//	}
//
//	/**
//	 * check whether the device has authentication alipay account.
//	 * 查询终端设备是否存在支付宝认证账户
//	 *
//	 */
//	public void check(View v) {
//		Runnable checkRunnable = new Runnable() {
//
//			@Override
//			public void run() {
//				// 构造PayTask 对象
//				PayTask payTask = new PayTask(PayListActivity.this);
//				// 调用查询接口，获取查询结果
//				boolean isExist = payTask.checkAccountIfExist();
//
//				Message msg = new Message();
//				msg.what = SDK_CHECK_FLAG;
//				msg.obj = isExist;
//				handler.sendMessage(msg);
//			}
//		};
//
//		Thread checkThread = new Thread(checkRunnable);
//		checkThread.start();
//
//	}
//
//	/**
//	 * get the sdk version. 获取SDK版本号
//	 *
//	 */
//	public void getSDKVersion() {
//		PayTask payTask = new PayTask(this);
//		String version = payTask.getVersion();
//		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
//	}
//
//	private String payment_code;
//
//	/**
//	 * create the order info. 创建订单信息
//	 *
//	 */
//	public String getOrderInfo(String subject, String body, String price,
//			String pay_sn) {
//		// 签约合作者身份ID
//		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
//		// 签约卖家支付宝账号
//		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
//		// 商户网站唯一订单号
//		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo(pay_sn) + "\"";
//		// 商品名称
//		orderInfo += "&subject=" + "\"" + subject + "\"";
//		// 商品详情
//		orderInfo += "&body=" + "\"" + body + "\"";
//		// 商品金额
//		orderInfo += "&total_fee=" + "\"" + price + "\"";
//		payment_code = State.getNotifyUrl("alipay");
//		// 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + "\"" + payment_code + "\"";
//		// 服务接口名称， 固定值
//		orderInfo += "&service=\"mobile.securitypay.pay\"";
//		// 支付类型， 固定值
//		orderInfo += "&payment_type=\"1\"";
//		// 参数编码， 固定值
//		orderInfo += "&_input_charset=\"utf-8\"";
//		// 设置未付款交易的超时时间
//		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
//		// 取值范围：1m～15d。
//		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
//		// 该参数数值不接受小数点，如1.5h，可转换为90m。
//		orderInfo += "&it_b_pay=\"30m\"";
//		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
//		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
//		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
//		orderInfo += "&return_url=\"m.alipay.com\"";
//		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
//		// orderInfo += "&paymethod=\"expressGateway\"";
//		return orderInfo;
//	}
//
//	/**
//	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
//	 *
//	 */
//	public String getOutTradeNo(String pay_sn) {
//		// SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//		// Locale.getDefault());
//		// Date date = new Date();
//		// String key = format.format(date);
//		//
//		// Random r = new Random();
//		// key = key + r.nextInt();
//		// key = key.substring(0, 15);
//		return pay_sn;
//	}
//
//	/**
//	 * sign the order info. 对订单信息进行签名
//	 *
//	 * @param content
//	 *            待签名订单信息
//	 */
//	public String sign(String content) {
//		return SignUtils.sign(content, RSA_PRIVATE);
//	}
//
//	/**
//	 * get the sign type we use. 获取签名方式
//	 *
//	 */
//	public String getSignType() {
//		return "sign_type=\"RSA\"";
//	}

}