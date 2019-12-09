package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.VirtualConfirmOrderStep1Info;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.util.regex.Pattern;

/**
 * 虚拟订单确认订单  virtualconfirmorder
 * Created by mayn on 2018/8/14.
 */

public class VirtualConfirmOrderActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_goodsimg;
    private EditText et_phone, et_password;
    private TextView tv_storename, tv_goodsname, tv_goodsprice, tv_goodsnum, tv_totalamount, tv_save, tv_balance;
    private CheckBox cb_1, cb_2;

    private VirtualConfirmOrderStep1Info virtualConfirmOrderStep1Info;
    private String cart_id, quantity, pay_password = "";
    private boolean isMultipleSelection = false;//是否能多选支付方式
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mdialog.dismiss();
            switch (msg.what) {
                case virtualconfirmorder_step1_id:
                    if (msg.obj != null) {
                        virtualConfirmOrderStep1Info = (VirtualConfirmOrderStep1Info) msg.obj;
                        if (virtualConfirmOrderStep1Info.error != null) {
                            Toast.makeText(appSingleton, virtualConfirmOrderStep1Info.error, Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                        Glide.with(VirtualConfirmOrderActivity.this).load(virtualConfirmOrderStep1Info.goods_info.goods_image_url).into(iv_goodsimg);
                        tv_storename.setText(virtualConfirmOrderStep1Info.store_info.store_name);
                        tv_goodsname.setText(virtualConfirmOrderStep1Info.goods_info.goods_name);
                        tv_goodsnum.setText(getString(R.string.order_reminder32) + virtualConfirmOrderStep1Info.goods_info.quantity);
                        tv_totalamount.setText(getString(R.string.order_reminder33) + virtualConfirmOrderStep1Info.goods_info.goods_total);
                        tv_goodsprice.setText(getString(R.string.order_reminder34) + virtualConfirmOrderStep1Info.goods_info.goods_price);
                        if (virtualConfirmOrderStep1Info.member_info.member_mobile != null) {
                            et_phone.setText(virtualConfirmOrderStep1Info.member_info.member_mobile);
                        }
                        float money1 = Float.parseFloat(virtualConfirmOrderStep1Info.member_info.available_predeposit);//余额
                        float money2 = Float.parseFloat(virtualConfirmOrderStep1Info.member_info.available_rc_balance);//充值卡余额
                        float money3 = Float.parseFloat(virtualConfirmOrderStep1Info.goods_info.goods_total);//总金额
                        if (money1 > 0 && money2 > 0) {
                            tv_balance.setVisibility(View.VISIBLE);
                            tv_balance.setText(getString(R.string.order_reminder145) + virtualConfirmOrderStep1Info.member_info.available_predeposit +
                                    getString(R.string.order_reminder146) + virtualConfirmOrderStep1Info.member_info.available_rc_balance);
                            cb_1.setVisibility(View.VISIBLE);
                            cb_2.setVisibility(View.VISIBLE);
                            if (money2 < money3) {
                                isMultipleSelection = true;
                            }
                        } else {
                            if (money1 > 0) {
                                cb_1.setVisibility(View.VISIBLE);
                                tv_balance.setVisibility(View.VISIBLE);
                                tv_balance.setText(getString(R.string.order_reminder145) + virtualConfirmOrderStep1Info.member_info.available_predeposit);
                            }
                            if (money2 > 0) {
                                cb_2.setVisibility(View.VISIBLE);
                                tv_balance.setVisibility(View.VISIBLE);
                                tv_balance.setText(getString(R.string.order_reminder147) + virtualConfirmOrderStep1Info.member_info.available_rc_balance);
                                if (money2 < money3) {
                                    isMultipleSelection = true;
                                }
                            }
                        }
                    }
                    break;
                case virtualconfirmorder_step1_err:
                    Toast.makeText(appSingleton, getString(R.string.virtualorders), Toast.LENGTH_SHORT).show();
                    break;
                case check_password_id://支付密码验证
                    if (msg.obj.equals("1")) {
                        netRun.VirtualConfirmOrderStep3(virtualConfirmOrderStep1Info.goods_info.goods_id, virtualConfirmOrderStep1Info.goods_info.quantity, et_phone.getText().toString(),
                                cb_1.isChecked() ? "1" : "0", pay_password, cb_2.isChecked() ? "1" : "0");
                    } else {
                        Toast.makeText(appSingleton, getString(R.string.payment_password_error), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case check_password_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case virtualconfirmorder_step3_id://购买第三步
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (isInteger(str)) {
                            if (pay_password.length() == 0) {//下单
                                Intent intent = new Intent(appSingleton, PayListActivity.class);
                                intent.putExtra("goods", getString(R.string.app_name));
                                intent.putExtra("describe", APPSingleton.getContext().getString(R.string.no).toString());
                                intent.putExtra("pay_sn", str);
                                intent.putExtra("isvr", true);
                                startActivity(intent);
                            } else {//完成
                                Intent intentvr = new Intent(appSingleton, WebActivity.class);
                                intentvr.putExtra("url", "http://aitecc.com/wap/index.php?act=member_vr_order");
                                startActivity(intentvr);
                            }
                            finish();
                        } else {
                            Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case virtualconfirmorder_step3_err:
                    Toast.makeText(appSingleton, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtualconfirmorder);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_goodsimg = (ImageView) findViewById(R.id.iv_goodsimg);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_storename = (TextView) findViewById(R.id.tv_storename);
        tv_goodsname = (TextView) findViewById(R.id.tv_goodsname);
        tv_goodsprice = (TextView) findViewById(R.id.tv_goodsprice);
        tv_goodsnum = (TextView) findViewById(R.id.tv_goodsnum);
        tv_totalamount = (TextView) findViewById(R.id.tv_totalamount);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        cb_1 = (CheckBox) findViewById(R.id.cb_1);
        cb_2 = (CheckBox) findViewById(R.id.cb_2);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_goback.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        cb_1.setOnClickListener(this);
        cb_2.setOnClickListener(this);
        Intent intent = getIntent();
        cart_id = intent.getStringExtra("cart_id");
        quantity = intent.getStringExtra("quantity");
        initData();
    }

    @Override
    protected void initData() {
        mdialog.show();
        netRun.VirtualConfirmOrderStep1(cart_id, quantity);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.iv_goback) {
            finish();
        } else if (v.getId() == R.id.tv_save) {
            String phone = et_phone.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(appSingleton, getString(R.string.type_in_phone_regist), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(et_password.getText().toString())) {
                Toast.makeText(appSingleton, getString(R.string.password_is_empty), Toast.LENGTH_SHORT).show();
                return;
            }
            if (cb_1.isChecked() || cb_2.isChecked()) {
                netRun.checkPassword(et_password.getText().toString());
            } else {
                Toast.makeText(appSingleton, getString(R.string.select_pay_way), Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.cb_1) {
            if (!isMultipleSelection) {
                cb_2.setChecked(false);
            }
        } else if (v.getId() == R.id.cb_2) {
            if (!isMultipleSelection) {
                cb_1.setChecked(false);
            }
        }

//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_save://提交
//                String phone = et_phone.getText().toString();
//                if (TextUtils.isEmpty(phone)) {
//                    Toast.makeText(appSingleton, getString(R.string.type_in_phone_regist), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(et_password.getText().toString())) {
//                    Toast.makeText(appSingleton, getString(R.string.password_is_empty), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (cb_1.isChecked() || cb_2.isChecked()) {
//                    netRun.checkPassword(et_password.getText().toString());
//                }else {
//                    Toast.makeText(appSingleton, getString(R.string.select_pay_way), Toast.LENGTH_SHORT).show();
//                }
////                netRun.VirtualConfirmOrderStep3(virtualConfirmOrderStep1Info.goods_info.goods_id, virtualConfirmOrderStep1Info.goods_info.quantity, phone,
////                        "0", "", "0");
//                break;
//            case R.id.cb_1:
//                if (!isMultipleSelection) {
//                    cb_2.setChecked(false);
//                }
//                break;
//            case R.id.cb_2:
//                if (!isMultipleSelection) {
//                    cb_1.setChecked(false);
//                }
//                break;
//        }
    }

//    /**
//     * 支付密码输入
//     */
//    private void showPopup() {
//        Mypopu mypopu = new Mypopu(this);
//        mypopu.showAtLocation(tv_save, Gravity.BOTTOM, 0, 0);
//        mypopu.sethuidiao(new Mypopu.huidiao() {
//            @Override
//            public void onItemClick(String psw) {
//                pay_password = psw;
//                if (psw != null && !psw.equals("")) {
//                    mdialog.show();
//                    netRun.checkPassword(psw);
//                }
//            }
//        });
//    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    public void ReGetData() {

    }
}
