package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.BindingPopu;
import com.aiteshangcheng.a.R;

import java.util.List;


/**
 * 更换邮箱/手机绑定
 * Created by mayn on 2018/4/26.
 */

public class EditBindingActivity extends BaseActivity implements View.OnClickListener {
    private TextView _tv_name, tv_type, tv_code, tv_binding, tv_agree, tv_code2;
    private ImageView _iv_back;
    private RelativeLayout ll_phone, ll_confirmsaw;
    private LinearLayout ll_binding, ll_code;
    private EditText ed_code, ed_binding, ed_registered_confirmsaw;
    private String type;
    private List<String> menu;
    private int typeid = 0;
    private boolean isVerify = false;//是否通过第一步验证
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case pay_psw2_id://资料
                    if (msg.obj != null) {
                        menu = (List<String>) msg.obj;
                        if (menu.size() != 0) {
                            tv_type.setText(menu.get(0));
                        }
                    }
                    break;
                case pay_psw2_err:
                    Toast.makeText(EditBindingActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case send_verifycode_id://统一发送验证码
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        Toast.makeText(EditBindingActivity.this, re, Toast.LENGTH_SHORT).show();
                        long time = Long.valueOf(60);
                        new TimeCount(time * 1000, 1000, 0).start();
                    }
                    break;
                case send_verifycode_err:
                    Toast.makeText(EditBindingActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case binding_phone_id://获取手机验证码
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(EditBindingActivity.this, str, Toast.LENGTH_SHORT)
                                .show();
                        long time = Long.valueOf(60);
                        new TimeCount(time * 1000, 1000, 1).start();
                    }
                    break;
                case binding_phone_err:
                    CommonTools.showShortToast(EditBindingActivity.this, getString(R.string.systembusy));
                    break;
                case binding_phone2_id://绑定手机
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            Toast.makeText(EditBindingActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        } else {
                            Toast.makeText(EditBindingActivity.this, str, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                case binding_phone2_err:
                    CommonTools.showShortToast(EditBindingActivity.this, getString(R.string.systembusy));
                    break;
                case email_bunding_id://绑定邮箱
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {
                            Toast.makeText(EditBindingActivity.this, getString(R.string.binding_prompt),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditBindingActivity.this, re,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case email_bunding_err:
                    Toast.makeText(EditBindingActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case verify_verifycode_id://验证验证码
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            ll_binding.setVisibility(View.VISIBLE);
                            ll_confirmsaw.setVisibility(View.GONE);
                            ll_code.setVisibility(View.GONE);
                            if (type.equals("2")) {
                                ll_phone.setVisibility(View.VISIBLE);
                            } else {
                                ll_phone.setVisibility(View.GONE);
                            }
                            isVerify = true;
                        } else {
                            Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case verify_verifycode_err:
                    Toast.makeText(EditBindingActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editbinding);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_code = (TextView) findViewById(R.id.tv_code);
        tv_binding = (TextView) findViewById(R.id.tv_binding);
        tv_agree = (TextView) findViewById(R.id.tv_agree);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        ed_code = (EditText) findViewById(R.id.ed_code);
        ed_binding = (EditText) findViewById(R.id.ed_binding);
        ll_phone = (RelativeLayout) findViewById(R.id.ll_phone);
        tv_code2 = (TextView) findViewById(R.id.tv_code2);
        ll_binding = (LinearLayout) findViewById(R.id.ll_binding);
        ll_code = (LinearLayout) findViewById(R.id.ll_code);
        ll_confirmsaw = (RelativeLayout) findViewById(R.id.ll_confirmsaw);
        ed_registered_confirmsaw = (EditText) findViewById(R.id.ed_registered_confirmsaw);
        initView();
    }

    @Override
    protected void initView() {
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            _tv_name.setText(getString(R.string.verification_prompt11));
            tv_binding.setText(getString(R.string.verification_prompt12));
        } else {
            _tv_name.setText(getString(R.string.verification_prompt13));
            tv_binding.setText(getString(R.string.verification_prompt14));
        }
        _iv_back.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        tv_code.setOnClickListener(this);
        tv_agree.setOnClickListener(this);
        tv_code2.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.PayPsw2("modify_paypwd");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_type) {
            if (menu != null) {
                showpopu(menu);
            }
        } else if (v.getId() == R.id.tv_code) {
            if (typeid == 0) {
                netRun.SendVerifycode("mobile");
            } else {
                netRun.SendVerifycode("email");
            }
        } else if (v.getId() == R.id.tv_code2) {
            String phone = ed_binding.getText().toString();
            if (TextUtils.isEmpty(phone)) {
                Toast.makeText(appSingleton, getString(R.string.type_in_phone_regist), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.BindingPhone(phone);
        } else if (v.getId() == R.id.tv_agree) {
            if (!isVerify) {//验证
                String code2 = ed_code.getText().toString();
                if (TextUtils.isEmpty(code2)) {
                    Toast.makeText(appSingleton, getString(R.string.release_goods144), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type.equals("2")) {
                    netRun.VerifyVerifyCode("modify_mobile", code2);
                } else {
                    netRun.VerifyVerifyCode("modify_email", code2);
                }
            } else {//更换
                String binding = ed_binding.getText().toString();
                if (TextUtils.isEmpty(binding)) {
                    Toast.makeText(appSingleton, getString(R.string.pleasefillinthecompletedata), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type.equals("2")) {//手机验证
                    String code = ed_registered_confirmsaw.getText().toString();
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(appSingleton, getString(R.string.binding_prompt5), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    netRun.BindingPhone2(binding, code);
                } else {//邮箱验证
                    netRun.YYEmailBinding(binding);
                }
            }
        }
//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_type://类型
//                if (menu != null) {
//                    showpopu(menu);
//                }
//                break;
//            case R.id.tv_code://获取验证码
//                if (typeid == 0) {
//                    netRun.SendVerifycode("mobile");
//                } else {
//                    netRun.SendVerifycode("email");
//                }
//                break;
//            case R.id.tv_code2://发送手机验证码
//                String phone = ed_binding.getText().toString();
//                if (TextUtils.isEmpty(phone)) {
//                    Toast.makeText(appSingleton, getString(R.string.type_in_phone_regist), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.BindingPhone(phone);
//                break;
//            case R.id.tv_agree://提交
//                if (!isVerify){//验证
//                    String code2 = ed_code.getText().toString();
//                    if ( TextUtils.isEmpty(code2)) {
//                        Toast.makeText(appSingleton, getString(R.string.release_goods144), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (type.equals("2")) {
//                        netRun.VerifyVerifyCode("modify_mobile", code2);
//                    } else {
//                        netRun.VerifyVerifyCode("modify_email", code2);
//                    }
//                }else{//更换
//                    String binding = ed_binding.getText().toString();
//                    if (TextUtils.isEmpty(binding)) {
//                        Toast.makeText(appSingleton, getString(R.string.pleasefillinthecompletedata), Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (type.equals("2")) {//手机验证
//                        String code = ed_registered_confirmsaw.getText().toString();
//                        if (TextUtils.isEmpty(code)) {
//                            Toast.makeText(appSingleton, getString(R.string.binding_prompt5), Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        netRun.BindingPhone2(binding, code);
//                    } else {//邮箱验证
//                        netRun.YYEmailBinding(binding);
//                    }
//                }
//                break;
//
//        }
    }

    /**
     * 显示弹窗
     */
    private void showpopu(List<String> data) {
        BindingPopu bindingPopu = new BindingPopu(EditBindingActivity.this, data);
        bindingPopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        bindingPopu.setmenu(new BindingPopu.menu() {
            @Override
            public void onItemClick(int id) {
                typeid = id;
                tv_type.setText(menu.get(id));
            }
        });
        bindingPopu.showAsDropDown(tv_type, 0, 0);
    }

    @Override
    public void ReGetData() {

    }

    class TimeCount extends CountDownTimer {
        int id;

        public TimeCount(long millisInFuture, long countDownInterval, int id) {
            super(millisInFuture, countDownInterval);
            this.id = id;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (id == 0) {
                tv_code.setTextColor(Color.BLACK);
                tv_code.setBackgroundResource(R.drawable.button_gray);
                tv_code.setClickable(false);
                tv_code.setText(millisUntilFinished / 1000 + getString(R.string.verification_prompt15));
            } else {
                tv_code2.setTextColor(Color.BLACK);
                tv_code2.setBackgroundResource(R.drawable.button_gray);
                tv_code2.setClickable(false);
                tv_code2.setText(millisUntilFinished / 1000 + getString(R.string.verification_prompt15));
            }
        }

        @Override
        public void onFinish() {
            if (id == 0) {
                tv_code.setText(getString(R.string.get_verification_code));
                tv_code.setClickable(true);
                tv_code.setTextColor(Color.parseColor("#FFFFFF"));
                tv_code.setBackgroundResource(R.drawable.solid_fred_corners);
            } else {
                tv_code2.setText(getString(R.string.get_verification_code));
                tv_code2.setClickable(true);
                tv_code2.setTextColor(Color.parseColor("#FFFFFF"));
                tv_code2.setBackgroundResource(R.drawable.solid_fred_corners);
            }

        }
    }
}
