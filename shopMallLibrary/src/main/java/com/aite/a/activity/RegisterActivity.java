package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.li.util.LogUtils;
import com.aite.a.activity.li.util.PopupWindowUtil;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.User;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.StringUtils;
import com.aite.a.view.EditTextWithDel;
import com.aiteshangcheng.a.R;
import com.example.receive.SMSBroadcastReceiver;
import com.example.receive.SMSBroadcastReceiver.MessageListener;

import java.util.Map;

/**
 * 注册
 *
 * @author xiaoyu
 */
public class RegisterActivity extends BaseActivity implements OnClickListener, MessageListener {
    private Button btn_submit, btn_gain;
    private EditTextWithDel et_phone, et_email, et_confirm_password, et_password, et_code;
    private RelativeLayout rl_name, rl_code, rl_email, rl_password, rl_confirm_password;
    private TextView register_tv_name;
    private CheckBox protected_check;
    private SMSBroadcastReceiver smsReceiver;
    private NetRun netRun;
    private String reg = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case register_id:
                    if (msg.obj.equals("1")) {
//					openActivity(PersonalActivity.class);
                        CommonTools.showShortToast(RegisterActivity.this, State.User + "\n" + getI18n(R.string.register_success));
                        RegisterActivity.this.finish();
                    } else {
                        CommonTools.showShortToast(RegisterActivity.this, msg.obj.toString());
                    }
                    mdialog.dismiss();
                    break;
                case register_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.act_net_error));
                    break;
                case register_start:
//				mdialog.setMessage(getI18n(R.string.registing));
                    mdialog.show();
                    break;
                case get_secret_id:
                    if (StringUtils.isNumber(msg.obj.toString())) {
                        mdialog.dismiss();
                        CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.get_token_success));
                        long time = Long.valueOf(msg.obj.toString());
                        new TimeCount(time * 1000, 1000).start();
                    } else {
                        mdialog.dismiss();
                        CommonTools.showShortToast(RegisterActivity.this, msg.obj.toString());
                    }
                    break;
                case get_secret_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.act_net_error));
                    break;
                case get_secret_start:
//				mdialog.setMessage(getI18n(R.string.getting_token));
                    mdialog.show();
                    break;
                case sms_register_id:
                    try {
//					AppManager.getInstance().killActivity(LoginActivity.class);
                        User user = (User) msg.obj;
                        CommonTools.showShortToast(RegisterActivity.this, user.user_name + "\n" + getI18n(R.string.register_success));
//					Bundle bundle = new Bundle();
//					bundle.putString("username", user.user_name);
//					bundle.putString("password", user.password);
//					bundle.putString("login_tag", "home");
//					openActivity(LoginActivity.class, bundle);
                        RegisterActivity.this.finish();
                    } catch (Exception e) {
                        CommonTools.showShortToast(RegisterActivity.this, msg.obj.toString());
                    }
                    mdialog.dismiss();
                    break;
                case sms_register_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.act_net_error));
                    break;
                case sms_register_start:
//				mdialog.setMessage(getI18n(R.string.registing));
                    mdialog.show();
                    break;
                case find_pass_step1_id:
                    if (msg.obj != null) {
                        mdialog.dismiss();
                        CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.get_token_success));
                        new TimeCount(30 * 1000, 1000).start();
                    } else {
                        mdialog.dismiss();
                        CommonTools.showShortToast(RegisterActivity.this, msg.obj.toString());
                    }
                    break;
                case find_pass_step1_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.act_net_error));
                    break;
                case find_pass_step1_start:
//				mdialog.setMessage(getI18n(R.string.getting_token));
                    mdialog.show();
                    break;
                case find_pass_step2_id:
                    if (msg.obj != null) {
                        CommonTools.showShortToast(RegisterActivity.this, msg.obj.toString());
                    } else {
                        CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.get_back_password_fail));
                    }
                    mdialog.dismiss();
                    break;
                case find_pass_step2_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(RegisterActivity.this, getI18n(R.string.act_net_error));
                    break;
                case find_pass_step2_start:
//				mdialog.setMessage(getI18n(R.string.getting_back_password));
                    mdialog.show();
                    break;
                case forget_password_id:
                    // 找回密码
                    if (msg.obj != null) {
                        String str = msg.obj.toString();
                        CommonTools.showShortToast(RegisterActivity.this, str);
                    }
                    break;
                case forget_password_err:
                    CommonTools.showShortToast(RegisterActivity.this,
                            getI18n(R.string.systembusy));
                    break;
                case password_code_id:
                    // 找回密码验证
                    if (msg.obj != null) {
                        Map map = (Map) msg.obj;
                        String str = (String) map.get("0");
                        if (str.equals("1")) {
                            new TimeCount(120 * 1000, 1000).start();
                            CommonTools.showShortToast(RegisterActivity.this,
                                    map.get("1").toString());
                        } else {
                            CommonTools.showShortToast(RegisterActivity.this,
                                    "获取失败");
                        }

                    }
                    break;
                case password_code_err:
                    CommonTools.showShortToast(RegisterActivity.this,
                            getI18n(R.string.systembusy));
                    break;
            }
        }

        ;
    };
    private TextView smsRegistTv;
    private View emailRegistTv;
    private View registWayLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        netRun = new NetRun(this, handler);
        reg = getIntent().getStringExtra("reg");
        findViewById();
    }

    @Override
    protected void findViewById() {
        rl_name = (RelativeLayout) findViewById(R.id.rl_name);
        rl_code = (RelativeLayout) findViewById(R.id.rl_code);
        rl_email = (RelativeLayout) findViewById(R.id.rl_email);
        rl_password = (RelativeLayout) findViewById(R.id.rl_password);
        rl_confirm_password = (RelativeLayout) findViewById(R.id.rl_confirm_password);
        btn_submit = (Button) findViewById(R.id.register_btn_submit);
        et_confirm_password = (EditTextWithDel) findViewById(R.id.register_et_confirm_password);
        et_password = (EditTextWithDel) findViewById(R.id.register_et_password);
        et_email = (EditTextWithDel) findViewById(R.id.register_et_email);
        et_code = (EditTextWithDel) findViewById(R.id.register_et_code);
        et_phone = (EditTextWithDel) findViewById(R.id.register_et_phone);
        btn_gain = (Button) findViewById(R.id.register_btn_gain);
        tv_title_name = (TextView) findViewById(R.id._tv_name);
        register_tv_name = (TextView) findViewById(R.id.register_tv_name);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_right = (ImageView) findViewById(R.id._iv_right);
        registWayLl = findViewById(R.id.regist_way_ll);
        protected_check = findViewById(R.id.protected_check);
        smsRegistTv = findViewById(R.id.sms_regist_tv);
        smsRegistTv.setOnClickListener(this);

        emailRegistTv = findViewById(R.id.email_regist_tv);
        emailRegistTv.setOnClickListener(this);

        /** 短信验证码 通知 */
        IntentFilter filter = new IntentFilter();
        smsReceiver = new SMSBroadcastReceiver();
        filter.addAction(smsReceiver.SMS_RECEIVED_ACTION);
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(smsReceiver, filter);
        smsReceiver.setOnReceivedMessageListener(this);

        protected_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    PopupWindowUtil.getmInstance().setProtectedPop(RegisterActivity.this);
            }
        });
        btn_submit.setOnClickListener(this);
        btn_gain.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        iv_right.setBackgroundResource(R.drawable.guanbi);

        initView();
    }

    @Override
    protected void initView() {

        Log.e("==================", reg);
        if (reg.equals("reg")) {
            rl_name.setVisibility(View.VISIBLE);
            rl_code.setVisibility(View.GONE);
            rl_email.setVisibility(View.VISIBLE);
            rl_password.setVisibility(View.VISIBLE);
            rl_confirm_password.setVisibility(View.VISIBLE);
            smsRegistTv.setPressed(false);
            smsRegistTv.setSelected(false);
            emailRegistTv.setPressed(true);
            emailRegistTv.setSelected(true);
            tv_title_name.setText(getString(R.string.storehome2));
            register_tv_name.setText(getI18n(R.string.user_name));
            et_phone.setHint(getString(R.string.storehome17));
        } else if (reg.equals("sms")) {
            rl_name.setVisibility(View.VISIBLE);
            rl_code.setVisibility(View.VISIBLE);
            rl_email.setVisibility(View.GONE);
            rl_password.setVisibility(View.GONE);
            rl_confirm_password.setVisibility(View.GONE);
            smsRegistTv.setPressed(true);
            smsRegistTv.setSelected(true);
            emailRegistTv.setPressed(false);
            emailRegistTv.setSelected(false);
            tv_title_name.setText(getString(R.string.storehome2));
            register_tv_name.setText(getI18n(R.string.phone_number));
//			TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//			String strTelephoneNumber = mTelephonyManager.getLine1Number();
//			et_phone.setText(strTelephoneNumber);
        } else if (reg.equals("forget")) {
            rl_name.setVisibility(View.VISIBLE);
            rl_code.setVisibility(View.VISIBLE);
            rl_email.setVisibility(View.GONE);
            rl_password.setVisibility(View.GONE);
            registWayLl.setVisibility(View.GONE);
            rl_confirm_password.setVisibility(View.GONE);
            tv_title_name.setText(getI18n(R.string.get_back_password));
            register_tv_name.setText(getI18n(R.string.phone_number));
            btn_submit.setText(getString(R.string.sure_confirm));
        } else {
            finish();
        }
    }

    @Override
    protected void initData() {
        String username = et_phone.getText().toString();
        String email = et_email.getText().toString();
        String check_code = et_code.getText().toString();
        String password = et_password.getText().toString();
        String password_confirm = et_confirm_password.getText().toString();
        if (reg.equals("sms")) {
            if (CommonTools.isMobile(username) != null) {
                CommonTools.showShortToast(this, CommonTools.isMobile(username));
                return;
            }
            if (TextUtils.isEmpty(check_code)) {
                CommonTools.showShortToast(this, getI18n(R.string.check_code_is_empty));
                return;
            }
//			if (TextUtils.isEmpty(email)) {
//				CommonTools.showShortToast(this, "邮箱为空");
//				return;
//			}
            if (password.length() < 6 || password.length() > 24) {
                CommonTools.showShortToast(this, "请输入6-24个字符的密码");
                return;
            }
            if (TextUtils.isEmpty(password_confirm)) {
                CommonTools.showShortToast(this, "确认密码为空");
                return;
            }
            if (!password_confirm.equals(password)) {
                CommonTools.showShortToast(this, "两次密码输入不一致");
                return;
            }
            //短信注册
            netRun.smaRegister(username, check_code);
//			netRun.smaRegister("Ismobilereg",username, check_code,password,password_confirm,email);
        } else if (reg.equals("reg")) {
            if (password.length() < 6 || password.length() > 24) {
                CommonTools.showShortToast(this, "请输入6-24个字符的密码");
                return;
            }
            if (TextUtils.isEmpty(password_confirm)) {
                CommonTools.showShortToast(this, "确认密码为空");
                return;
            }
            if (!password_confirm.equals(password)) {
                LogUtils.d(password + ":dadsds" + password_confirm);
                CommonTools.showShortToast(this, "两次密码输入不一致");
                return;
            }
            if (TextUtils.isEmpty(username)) {
                CommonTools.showShortToast(this, getI18n(R.string.user_is_empty));
                return;
            }
            if (TextUtils.isEmpty(email)) {
                CommonTools.showShortToast(this, getI18n(R.string.email_empty));
                return;
            }
            if (TextUtils.isEmpty(password)) {
                CommonTools.showShortToast(this, getI18n(R.string.password_empty));
                return;
            }
            if (TextUtils.isEmpty(password_confirm)) {
                CommonTools.showShortToast(this, getI18n(R.string.repeat_password_not));
                return;
            }
            if (CommonTools.checkUserName(username) == false) {
                CommonTools.showShortToast(this, getI18n(R.string.type_in_correct_user));
                return;
            }
            if (CommonTools.checkEmail(email) == false) {
                CommonTools.showShortToast(this, getI18n(R.string.email_error));
                return;
            }
            if (!password.equals(password_confirm)) {
                CommonTools.showShortToast(this, getI18n(R.string.password_incorrect));
                return;
            }
            //普通注册
            netRun.register(username, password, password_confirm, email);
        } else if (reg.equals("forget")) {
            if (TextUtils.isEmpty(username)) {
                CommonTools.showShortToast(this, getI18n(R.string.user_is_empty));
                return;
            }
            if (TextUtils.isEmpty(check_code)) {
                CommonTools.showShortToast(this, getI18n(R.string.check_code_is_empty));
                return;
            }
//			if (TextUtils.isEmpty(password)) {
//				CommonTools.showShortToast(this, getI18n(R.string.password_empty));
//				return;
//			}
//			if (TextUtils.isEmpty(password_confirm)) {
//				CommonTools.showShortToast(this, getI18n(R.string.repeat_password_not));
//				return;
//			}
//			if (CommonTools.checkUserName(username) == false) {
//				CommonTools.showShortToast(this, getI18n(R.string.type_in_correct_user));
//				return;
//			}
//			if (!password.equals(password_confirm)) {
//				CommonTools.showShortToast(this, getI18n(R.string.password_incorrect));
//				return;
//			}
            //忘记密码
//			netRun.findPassStep2(username, check_code, password, password_confirm);
            netRun.ForgetPassword(username, check_code);
        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.register_btn_submit){
            if (!protected_check.isChecked()) {
                Toast.makeText(this, "请阅读隐私协议并同意", Toast.LENGTH_SHORT).show();
                return;
            }
            initData();
        }else if(v.getId()==R.id.register_btn_gain){
            String username = CommonTools.isMobile(et_phone.getText().toString());
            if (username != null) {
                CommonTools.showShortToast(this, username);
                return;
            }
            if (reg.equals("sms")) {
                netRun.getSecret(et_phone.getText().toString());
            } else if (reg.equals("forget")) {
                netRun.PasswordCode(et_phone.getText().toString());
//				netRun.findPassStep1(et_phone.getText().toString());
            }
        }else if(v.getId()==R.id.sms_regist_tv){
            smsRegistTv.setPressed(true);
            smsRegistTv.setSelected(true);
            emailRegistTv.setPressed(false);
            emailRegistTv.setSelected(false);
            reg = "sms";
            initView();
        }else if(v.getId()== R.id.email_regist_tv){
            smsRegistTv.setPressed(false);
            smsRegistTv.setSelected(false);
            emailRegistTv.setPressed(true);
            emailRegistTv.setSelected(true);
            reg = "reg";
            initView();
        }else if(v.getId()==R.id._iv_back||v.getId()==R.id._iv_right){
            finish();
            overrPre();
        }
//        switch (v.getId()) {
//            case R.id.register_btn_submit:
//                if (!protected_check.isChecked()) {
//                    Toast.makeText(this, "请阅读隐私协议并同意", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                initData();
//                break;
//
//            case R.id.register_btn_gain:// 获取验证码 name =
//                String username = CommonTools.isMobile(et_phone.getText().toString());
//                if (username != null) {
//                    CommonTools.showShortToast(this, username);
//                    return;
//                }
//                if (reg.equals("sms")) {
//                    netRun.getSecret(et_phone.getText().toString());
//                } else if (reg.equals("forget")) {
//                    netRun.PasswordCode(et_phone.getText().toString());
////				netRun.findPassStep1(et_phone.getText().toString());
//                }
//                break;
//            case R.id.sms_regist_tv:
//                smsRegistTv.setPressed(true);
//                smsRegistTv.setSelected(true);
//                emailRegistTv.setPressed(false);
//                emailRegistTv.setSelected(false);
//                reg = "sms";
//                initView();
//                break;
//            case R.id.email_regist_tv:
//                smsRegistTv.setPressed(false);
//                smsRegistTv.setSelected(false);
//                emailRegistTv.setPressed(true);
//                emailRegistTv.setSelected(true);
//                reg = "reg";
//                initView();
//                break;
//            case R.id._iv_back:
//            case R.id._iv_right:
//                finish();
//                overrPre();
//                break;
//        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_gain.setTextColor(Color.parseColor("#FFFFFF"));
            btn_gain.setBackgroundColor(Color.parseColor("#AAAAAA"));
            btn_gain.setClickable(false);
            btn_gain.setText(millisUntilFinished / 1000 + getI18n(R.string.seconds_resend));
        }

        @Override
        public void onFinish() {
            btn_gain.setText(getI18n(R.string.re_get_token));
            btn_gain.setClickable(true);
            btn_gain.setTextColor(Color.parseColor("#FFFFFF"));
            btn_gain.setBackgroundResource(R.drawable.gm_selector);

        }
    }

    @Override
    public void ReGetData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//		if (!reg.equals("reg"))
        unregisterReceiver(smsReceiver);
    }

    @Override
    public void onReceived(String message) {
        String code = CommonTools.patternCode(message);
        et_code.setText(code);
        if (reg.equals("sms"))
            initData();
    }
}
