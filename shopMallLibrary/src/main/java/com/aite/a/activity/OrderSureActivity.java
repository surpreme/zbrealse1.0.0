package com.aite.a.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.adapter.SureOrderGiftAdapter;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.AddressInfo;
import com.aite.a.model.BuySteOneInfo;
import com.aite.a.model.BuySteOneInfo.StoreCartlList;
import com.aite.a.model.BuySteOneInfo.StoreCartlList.GoodsList2;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BooleanLogin;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.SPUtils;
import com.aite.a.view.EditTextWithDel;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 订单确认界面
 *
 * @author xiaoyu
 */
public class OrderSureActivity extends BaseActivity implements OnClickListener {
    private ImageView iv_back, iv_right;
    private TextView tv_title, tv_updata, tv_desc;
    private MyListView listView;
    private RelativeLayout rl_receiver,rl_invoice;
    private TextView tv_name, tv_phone, tv_address;
    private CheckBox cb_zhi, cb_yin;
    private EditText et_password;
    private int PAY_METHODS_TAG = 1;
    private CheckBox Advance_yes, Advance_no;
    private int BILL_TAG = 1;
    private String cart_id, ifcart, advance_pay, pay_name, pay_password,invoice_id="";
    private Button btn_sub;
    private Activity activity = OrderSureActivity.this;
    private SharedPreferences sp;
    private SPUtils spUtils;
    private EditTextWithDel ed_fcoding;
    private String client = "android";
    private BuySteOneInfo buySteOneInfo = new BuySteOneInfo();
    private LinearLayout order_ll_h;
    private boolean ifChange = false;
    private SureOrderGiftAdapter giftAdapter;
    private NetRun netRun;
    private String s;
    private String isfcode;
    private boolean isdeposit = false;
    private boolean isaddress = true;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case buy_step1_id:
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    if (map.get("1").equals("1")) {
                        buySteOneInfo = (BuySteOneInfo) map.get("2");
                        if (buySteOneInfo.is_vat_deny.equals("1")) {
                            rl_invoice.setVisibility(View.VISIBLE);
                        } else {
                            rl_invoice.setVisibility(View.GONE);
                        }
                        if (BooleanLogin.getInstance().hasAddress(activity,
                                buySteOneInfo)) {
                            giftAdapter.setStoreInfos(buySteOneInfo.storeInfos);
                            GoodsList2 goodsList2 = buySteOneInfo.storeInfos.get(0).goods_list
                                    .get(0);
                            s = goodsList2.goods_image_url;
                            giftAdapter.notifyDataSetChanged();
                            if (isaddress) {
                                setData(buySteOneInfo);
                                netRun.changeAddress(sp.getString("freight_hash", ""),
                                        buySteOneInfo.addressInfo.city_id,
                                        buySteOneInfo.addressInfo.area_id);
                                isaddress = false;
                            }
                        }
                    } else {
                        if (map.get("2") != null) {
                            CommonTools.showShortToast(OrderSureActivity.this, map
                                    .get("2").toString());
                        } else {
                            CommonTools.showShortToast(OrderSureActivity.this,
                                    getString(R.string.order_reminder28));
                        }
                        finish();
                    }
                    mdialog.dismiss();
                    break;
                case buy_step1_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(OrderSureActivity.this,
                            getI18n(R.string.systembusy));
                    break;
                case buy_step1_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
                    mdialog.show();
                    break;
                case change_address_id:
                    try {
                        AddressInfo addressInfo = (AddressInfo) msg.obj;
                        Editor editor = sp.edit();
                        editor.putString("offpay_hash",
                                addressInfo.getOffpay_hash());
                        editor.putString("offpay_hash_batch",
                                addressInfo.getOffpay_hash_batch());
                        editor.commit();
                        if (ifChange == true)
                            CommonTools.showShortToast(OrderSureActivity.this,
                                    getI18n(R.string.update_address_success));
                        ifChange = false;
                    } catch (Exception e) {
                        CommonTools.showShortToast(OrderSureActivity.this,
                                msg.obj.toString());
                        e.printStackTrace();
                    }
                    mdialog.dismiss();
                    break;
                case change_address_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(OrderSureActivity.this,
                            getI18n(R.string.act_net_error));
                    break;
                case change_address_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
                    mdialog.show();
                    break;
                case buy_step2_id:
                    try {
                        String pay_sn = (String) msg.obj;

                        if (pay_name.equals("online"))
                            if (advance_pay.equals("0"))
                                pay(pay_sn);
                        /*CommonTools.showShortToast(OrderSureActivity.this,
                                getI18n(R.string.commit_order_success));*/
                        if (isdeposit) {
                            openActivity(BuyerOrderFgActivity.class);
                            finish();
                        }
                        if (pay_name.equals("offline")) {
                            openActivity(BuyerOrderFgActivity.class);
                        }
                    } catch (Exception e) {
                        CommonTools.showShortToast(OrderSureActivity.this,
                                msg.obj.toString());
                    }
                    mdialog.dismiss();
                    break;
                case buy_step2_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(OrderSureActivity.this,
                            getI18n(R.string.act_net_error));
                    break;
                case buy_step2_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
                    mdialog.show();
                    break;
                case check_password_id:
                    if (msg.obj.equals("1")) {
                        buyStepTwo(pay_name, advance_pay);
                        isdeposit = true;
                        // CommonTools.showShortToast(OrderSureActivity.this,
                        // getI18n(R.string.payment_password_correct));
                    } else {
                        CommonTools.showShortToast(OrderSureActivity.this,
                                getI18n(R.string.payment_password_error));
                    }
                    mdialog.dismiss();
                    break;
                case check_password_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(OrderSureActivity.this,
                            getI18n(R.string.act_net_error));
                    break;
                case check_password_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
                    mdialog.show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_sure);
        sp = getSharedPreferences("OrderSureActivity", MODE_PRIVATE);
        spUtils = new SPUtils(SPFileName.Order_cart_id, this);
        netRun = new NetRun(activity, handler);
        findViewById();
    }

    @Override
    protected void findViewById() {
        // tv_fact_pay = (TextView) findViewById(R.id.order_tv_fact_pay);
        btn_sub = (Button) findViewById(R.id.order_btn_submit);
        Advance_yes = (CheckBox) findViewById(R.id.Advance_yes);
        Advance_no = (CheckBox) findViewById(R.id.Advance_no);
        cb_zhi = (CheckBox) findViewById(R.id.order_sure_cb_zhi);
        cb_yin = (CheckBox) findViewById(R.id.order_sure_cb_yin);
        tv_address = (TextView) findViewById(R.id.order_sure_tv_address_);
        tv_phone = (TextView) findViewById(R.id.order_sure_tv_address_phone);
        tv_name = (TextView) findViewById(R.id.order_sure_tv_address_name);
        tv_updata = (TextView) findViewById(R.id.tv_updata);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        rl_receiver = (RelativeLayout) findViewById(R.id.order_sure_rl_receiver);
        rl_invoice = (RelativeLayout) findViewById(R.id.rl_invoice);
        listView = (MyListView) findViewById(R.id.order_sure_mylistview);
        tv_title = (TextView) findViewById(R.id._tv_name);
        iv_right = (ImageView) findViewById(R.id._iv_right);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        order_ll_h = (LinearLayout) findViewById(R.id.order_ll_h);
        ed_fcoding = (EditTextWithDel) findViewById(R.id.ed_fcoding);
        et_password = findViewById(R.id.et_password);
        initView();
    }

    @Override
    protected void initView() {
        btn_sub.setOnClickListener(this);
        Advance_yes.setOnClickListener(this);
        Advance_no.setOnClickListener(this);
        cb_zhi.setOnClickListener(this);
        cb_zhi.setChecked(true);
        cb_yin.setOnClickListener(this);
        rl_receiver.setOnClickListener(this);
        tv_title.setText(getI18n(R.string.confirm_order));
        iv_right.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_updata.setOnClickListener(this);
        giftAdapter = new SureOrderGiftAdapter(activity);
        listView.setAdapter(giftAdapter);
        Advance_no.performClick();
        // 显示F码输入框
        Bundle bundle = getIntent().getExtras();
        try {
            isfcode = bundle.getString("isfcode", "0");
        } catch (Exception e) {
            isfcode = "0";
        }
        if (isfcode.equals("1")) {
            ed_fcoding.setVisibility(View.VISIBLE);
        }

        Advance_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    et_password.setVisibility(View.VISIBLE);
                }else{
                    et_password.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 添加数据
     */
    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cart_id = bundle.getString("cart_id");
            ifcart = bundle.getString("ifcart");
            netRun.buyStepOne(ifcart, cart_id);
        } else {
            finish();
        }

    }
    private String password;
    /**
     * @param pay_name 付款方式，可选值 online(线上付款) offline(货到付款
     * @param pd_pay   是否使用预存款支付 1-使用 0-不使用
     */
    private void buyStepTwo(final String pay_name, final String pd_pay) {

        String address_id = sp.getString("address_id", "");
        String vat_hash = sp.getString("vat_hash", "");
        String offpay_hash = sp.getString("offpay_hash", "");
        String offpay_hash_batch = sp.getString("offpay_hash_batch", "");
//        String invoice_id = sp.getString("invoice_id", "");
        String fcode = ed_fcoding.getText().toString();

        String message = giftAdapter.getMessage();
        Log.i("---------------", "留言 " + message);
        if (isfcode.equals("1")) {
            if (TextUtils.isEmpty(fcode)) {
                CommonTools.showShortToast(OrderSureActivity.this,
                        getI18n(R.string.inputfcoding));
            } else {
                netRun.buyStepTwo(ifcart, cart_id, address_id, vat_hash,
                        offpay_hash, offpay_hash_batch, pay_name, invoice_id,
                        pd_pay, password, fcode,message);
            }
        } else {
            netRun.buyStepTwo(ifcart, cart_id, address_id, vat_hash,
                    offpay_hash, offpay_hash_batch, pay_name, invoice_id,
                    pd_pay, password, "",message);
        }
    }

    protected void pay(String pay_sn) {
        String goods_name = "";
        float goods_price = 0;
        int goods_num = 0;
        for (StoreCartlList cartlList : buySteOneInfo.storeInfos) {
            goods_price = goods_price
                    + Float.valueOf(cartlList.store_goods_total);
            goods_num = goods_num + cartlList.goods_list.size();
            for (int i = 0; i < cartlList.goods_list.size(); i++) {
                String name = replaceBlank(cartlList.goods_list.get(i).goods_name);
                if (i == 0) {
                    goods_name = name;
                } else {
                    goods_name = goods_name + name;
                }
            }
        }

        Intent intent = new Intent(this, PayListActivity.class);
        intent.putExtra("goods", goods_name);
        intent.putExtra("describe", getI18n(R.string.no));
        intent.putExtra("price", String.valueOf(goods_price));
        intent.putExtra("pay_sn", pay_sn);
        intent.putExtra("goods_num", String.valueOf(goods_num));
        intent.putExtra("url", s);
        startActivity(intent);
        OrderSureActivity.this.finish();
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t\r\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    protected void setData(BuySteOneInfo buySteOneInfo) {
        Editor editor = sp.edit();
        editor.putString("address_id", buySteOneInfo.addressInfo.address_id);
        editor.putString("freight_hash", buySteOneInfo.freight_hash);
        editor.putString("vat_hash", buySteOneInfo.vat_hash);
//        editor.putString("invoice_id", buySteOneInfo.inv_info.toString());
        editor.commit();
        if (buySteOneInfo.addressInfo != null) {
            tv_name.setText(getString(R.string.order_reminder29) + (buySteOneInfo.addressInfo.true_name == null ? "" : buySteOneInfo.addressInfo.true_name));
            tv_phone.setText(getString(R.string.order_reminder30) + (buySteOneInfo.addressInfo.mob_phone == null ? "" : buySteOneInfo.addressInfo.mob_phone));
            tv_address.setText(getString(R.string.order_reminder31) + (buySteOneInfo.addressInfo.area_info == null ? "" : buySteOneInfo.addressInfo.area_info)
                    + "-" + (buySteOneInfo.addressInfo.address == null ? "" : buySteOneInfo.addressInfo.address));
        } else {
            tv_name.setText("");
            tv_phone.setText("");
            tv_address.setText("");
        }

    }

    /**
     * 提交数据
     */
    private void subData() {
        if (PAY_METHODS_TAG == 1) {
            pay_name = "online";
        } else if (PAY_METHODS_TAG == 2) {
            pay_name = "offline";
        } else {
            pay_name = null;
        }
        if (BILL_TAG == 1) {
            advance_pay = "1";
        } else if (BILL_TAG == 2) {
            advance_pay = "0";
        } else {
            advance_pay = null;
        }
        if (pay_name == null) {
            CommonTools.showShortToast(activity,
                    getI18n(R.string.select_pay_way));
            return;
        }
        if (advance_pay == null) {
            CommonTools.showShortToast(activity,
                    getI18n(R.string.advance_payment_or_not));
            return;
        }
        if (advance_pay.equals("1")) {
            if (pay_name.equals("online")) {
                // getPayCipher(pay_name, advance_pay);
                password = et_password.getText().toString();
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(this, getString(R.string.put_in_payment_password), Toast.LENGTH_SHORT).show();
                    return;
                }
                netRun.checkPassword(password);
            } else {
                buyStepTwo(pay_name, advance_pay);
            }
        } else {
            buyStepTwo(pay_name, advance_pay);
        }
    }

    /**
     * 支付方式Check变化
     */
    private void setCheckChangePay(int tag, CheckBox cb1, CheckBox cb2) {
        if (cb1.isChecked() == true) {
            PAY_METHODS_TAG = tag;
            cb2.setChecked(false);
        } else {
            PAY_METHODS_TAG = 3;
        }
        if (PAY_METHODS_TAG == 2) {
            BILL_TAG = 2;
            order_ll_h.setVisibility(View.GONE);
        } else {
            setCheckChangeBill(1, Advance_yes, Advance_no);
            order_ll_h.setVisibility(View.VISIBLE);
            Advance_yes.setChecked(false);
            Advance_no.setChecked(false);
        }
    }

    /**
     * 预付款Check变化
     */
    private void setCheckChangeBill(int tag, CheckBox cb1, CheckBox cb2) {
        if (cb1.isChecked() == true) {
            BILL_TAG = tag;
            cb2.setChecked(false);
        } else {
            BILL_TAG = 3;
        }
    }

//    private Mypopu mypopu;

//    /**
//     * 支付密码输入
//     */
//    private void showPopup(final String bill) {
//        mypopu = new Mypopu(this);
//        mypopu.showAtLocation(Advance_yes, Gravity.BOTTOM, 0, 0);
//        mypopu.setOnDismissListener(new OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//                String pay2 = lingshi.getInstance().getPay();
//                pay_password = pay2;
//                if (pay2 != null && !pay2.equals("")) {
//                    netRun.checkPassword(pay2, bill, "");
//                }
//                lingshi.getInstance().setPay("");
//                android.view.WindowManager.LayoutParams lp = OrderSureActivity.this
//                        .getWindow().getAttributes();
//                lp.alpha = 1.0f;
//                OrderSureActivity.this.getWindow().setAttributes(lp);
//            }
//        });
//    }

    /**
     * 支付密码输入框
     *
     * @param pay
     */
    private void getPayCipher(final String bill, final String pay) {
        final EditTextWithDel inputServer = new EditTextWithDel(this);
        inputServer.setTransformationMethod(PasswordTransformationMethod
                .getInstance());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getI18n(R.string.put_in_payment_password))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(inputServer);
        builder.setNegativeButton(getI18n(R.string.cancel), null);
        builder.setPositiveButton(getI18n(R.string.confirm),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        pay_password = inputServer.getText().toString();
                        netRun.checkPassword(pay_password);
                    }
                });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Addres_Result:
                // 获取 activity的返回数据
                if (data != null) {
                    if (data.getStringExtra("addres").equals("addres")) {
                        Bundle bundle = data.getExtras();
                        tv_name.setText(getString(R.string.order_reminder29) + bundle.getString("address_name"));
                        tv_phone.setText(getString(R.string.order_reminder30) + bundle.getString("address_phone"));
                        tv_address.setText(getString(R.string.order_reminder31) + bundle.getString("address"));
                        String city_id = bundle.getString("city_id");
                        String area_id = bundle.getString("area_id");
                        // 记录地址ID
                        Editor editor = sp.edit();
                        editor.putString("address_id",
                                bundle.getString("address_id"));
                        editor.commit();
                        ifChange = true;
                        netRun.changeAddress(sp.getString("freight_hash", ""),
                                city_id, area_id);
                    }
                }
                break;
            case PICK_INVOICE://发票
                if (data != null) {
                    invoice_id = data.getStringExtra("id");
                    String desc = data.getStringExtra("desc");
                    tv_desc.setText(desc);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.order_btn_submit) {
            // 提交
            subData();
        }else if(v.getId()==R.id.Advance_yes){
            setCheckChangeBill(1, Advance_yes, Advance_no);
        }else if(v.getId()==R.id.Advance_no){
            setCheckChangeBill(2, Advance_no, Advance_yes);
        }else if(v.getId()==R.id.order_sure_cb_zhi){
            setCheckChangePay(1, cb_zhi, cb_yin);
        }else if(v.getId()== R.id.order_sure_cb_yin){
            setCheckChangePay(2, cb_yin, cb_zhi);
        }else if(v.getId()==R.id.order_sure_rl_receiver){
            Intent intent = new Intent(this, AddressManageActivity.class);
            intent.putExtra("cb_out", "out");
            startActivityForResult(intent, Addres_Result);
            overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
        }else if(v.getId()==R.id._iv_back){
            finish();
            overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
        }else if(R.id._iv_right==v.getId()){
            CommonTools.showShortToast(this,
                    getI18n(R.string.confirm_order_right_button));
        }else if(v.getId()==R.id.tv_updata){
            //选择发票
            Intent intent2 = new Intent(this, InvoiceActivity.class);
            intent2.putExtra("vat_hash", buySteOneInfo.vat_hash);
            startActivityForResult(intent2, PICK_INVOICE);
        }
//        switch (v.getId()) {
//            case R.id.order_btn_submit:
//                // 提交
//                subData();
//                break;
//            case R.id.Advance_yes:
//                setCheckChangeBill(1, Advance_yes, Advance_no);
//                break;
//            case R.id.Advance_no:
//                setCheckChangeBill(2, Advance_no, Advance_yes);
//                break;
//            case R.id.order_sure_cb_zhi:
//                setCheckChangePay(1, cb_zhi, cb_yin);
//                break;
//            case R.id.order_sure_cb_yin:
//                setCheckChangePay(2, cb_yin, cb_zhi);
//                break;
//            case R.id.order_sure_rl_receiver:
//                Intent intent = new Intent(this, AddressManageActivity.class);
//                intent.putExtra("cb_out", "out");
//                startActivityForResult(intent, Addres_Result);
//                overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
//                break;
//            case R.id._iv_back:
//                finish();
//                overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
//                break;
//            case R.id._iv_right:
//                CommonTools.showShortToast(this,
//                        getI18n(R.string.confirm_order_right_button));
//                break;
//            case R.id.tv_updata:
//                //选择发票
//                Intent intent2 = new Intent(this, InvoiceActivity.class);
//                intent2.putExtra("vat_hash", buySteOneInfo.vat_hash);
//                startActivityForResult(intent2, PICK_INVOICE);
//                break;
//        }
    }

    @Override
    public void ReGetData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();

    }

}
