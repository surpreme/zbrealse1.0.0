package com.aite.a.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.CodeUtils;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 支付密码
 *
 * @author Administrator
 */
public class PayPswActivity extends BaseActivity implements OnClickListener {
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private TextView _tv_name, tv_send, tv_validation, tv_next;
    private ImageView _iv_back, iv_validation;
    private Spinner sp_menu;
    private EditText ed_email, ed_email2;
    private List<String> menu;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case pay_psw2_id:
                    if (msg.obj != null) {
                        menu = (List<String>) msg.obj;
                        // 定义适配器
                        SpinnerAdapter adapter = new SpinnerAdapter(PayPswActivity.this,
                                android.R.layout.simple_spinner_item, menu);
                        sp_menu.setAdapter(adapter);
                    }
                    break;
                case pay_psw2_err:
                    Toast.makeText(PayPswActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case binding_phone_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(PayPswActivity.this, str, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case binding_phone_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(PayPswActivity.this, getString(R.string.systembusy));
                    break;
                case send_verifycode_id://统一发送验证码
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        Toast.makeText(PayPswActivity.this, re, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case send_verifycode_err:
                    Toast.makeText(PayPswActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case authsu_bmit_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            Intent intent2 = new Intent(PayPswActivity.this, PayPsw2Activity.class);
                            startActivity(intent2);
                            finish();
                        } else {
                            Toast.makeText(PayPswActivity.this, str, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                case authsu_bmit_err:
                    Toast.makeText(PayPswActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };

    private CodeUtils instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypsw);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_validation = (TextView) findViewById(R.id.tv_validation);
        tv_next = (TextView) findViewById(R.id.tv_next);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_validation = (ImageView) findViewById(R.id.iv_validation);
        sp_menu = (Spinner) findViewById(R.id.sp_menu);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_email2 = (EditText) findViewById(R.id.ed_email2);
        _iv_back.setOnClickListener(this);
        iv_validation.setOnClickListener(this);
        tv_validation.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.order_reminder39));
        netRun = new NetRun(this, handler);


        instance = CodeUtils.getInstance();
        Bitmap createBitmap = instance.createBitmap();
        iv_validation.setImageBitmap(createBitmap);
        initData();
    }

    @Override
    protected void initData() {
        netRun.PayPsw2("modify_paypwd");
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id._iv_back){
            finish();
        }else if(v.getId()==R.id.iv_validation){
            Bitmap createBitmap = instance.createBitmap();
            iv_validation.setImageBitmap(createBitmap);
        }else if(v.getId()==R.id.tv_validation){
            iv_validation.performClick();
        }else if(v.getId()== R.id.tv_next){
            //下一步
            String string = ed_email2.getText().toString();
            String string3 = ed_email.getText().toString();
            if (TextUtils.isEmpty(string3)) {
                Toast.makeText(PayPswActivity.this, getString(R.string.binding_prompt5), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(string)) {
                Toast.makeText(PayPswActivity.this, getString(R.string.order_reminder46), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!instance.getCode().equals(string)) {
                Toast.makeText(PayPswActivity.this, getString(R.string.order_reminder47), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.AuthSubmit("modify_paypwd", string3);
        }else if(v.getId()==R.id.tv_send){
            String string2 = sp_menu.getSelectedItem().toString();
            int id = 0;
            for (int i = 0; i < menu.size(); i++) {
                if (string2.equals(menu.get(i))) {
                    id = i;
                    break;
                }
            }
            tv_send.setClickable(false);
            timer.start();
            tv_send.setBackgroundColor(0xff808080);

            if (id == 0) {
                netRun.SendVerifycode("mobile");
//				netRun.BindingPhone(string2);
            } else {
//				netRun.YYEmailBinding(string2);
                netRun.SendVerifycode("email");
            }
        }

//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.iv_validation:
//                Bitmap createBitmap = instance.createBitmap();
//                iv_validation.setImageBitmap(createBitmap);
//                break;
//            case R.id.tv_validation:
//                iv_validation.performClick();
//                break;
//            case R.id.tv_next:
//                //下一步
//                String string = ed_email2.getText().toString();
//                String string3 = ed_email.getText().toString();
//                if (TextUtils.isEmpty(string3)) {
//                    Toast.makeText(PayPswActivity.this, getString(R.string.binding_prompt5), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(string)) {
//                    Toast.makeText(PayPswActivity.this, getString(R.string.order_reminder46), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!instance.getCode().equals(string)) {
//                    Toast.makeText(PayPswActivity.this, getString(R.string.order_reminder47), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.AuthSubmit("modify_paypwd", string3);
//
//                break;
//            case R.id.tv_send:
//                String string2 = sp_menu.getSelectedItem().toString();
//                int id = 0;
//                for (int i = 0; i < menu.size(); i++) {
//                    if (string2.equals(menu.get(i))) {
//                        id = i;
//                        break;
//                    }
//                }
//                tv_send.setClickable(false);
//                timer.start();
//                tv_send.setBackgroundColor(0xff808080);
//
//                if (id == 0) {
//                    netRun.SendVerifycode("mobile");
////				netRun.BindingPhone(string2);
//                } else {
////				netRun.YYEmailBinding(string2);
//                    netRun.SendVerifycode("email");
//                }
//                break;
//        }
    }

    /**
     * 取消倒计时
     *
     * @param v
     */
    public void oncancel(View v) {
        timer.cancel();
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setText((millisUntilFinished / 1000) +getString(R.string.timing));
        }

        @Override
        public void onFinish() {
            tv_send.setClickable(true);
            tv_send.setText(getString(R.string.get_verification_code));
            tv_send.setBackgroundColor(0xffFF0000);
        }
    };


    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        List<String> items = new ArrayList<String>();

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, final List<String> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
//			tv.setTextColor(Color.BLUE);
//			tv.setTextSize(12, TypedValue.COMPLEX_UNIT_SP);
            tv.setTextSize(12);
            int px2dip = dip2px(context, 10);
            tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
//			tv.setTextColor(Color.BLUE);
            tv.setTextSize(12);
//			int px2dip = dip2px(context, 10);
//			tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
