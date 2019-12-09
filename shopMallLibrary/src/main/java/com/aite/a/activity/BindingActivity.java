package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 绑定
 *
 * @author Administrator
 */
public class BindingActivity extends BaseActivity implements OnClickListener {

    private ImageView _iv_back;
    private TextView _tv_name, tv_send, tv_prompt1, tv_prompt2, tv_prompt3,
            tv_verify, tv_ts;
    private EditText ed_email, ed_phone;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private RelativeLayout rl_phone;
    private String isbinding;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case email_bunding_id:
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {
                            Toast.makeText(BindingActivity.this,
                                    getString(R.string.binding_prompt),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BindingActivity.this, re,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case email_bunding_err:
                    Toast.makeText(BindingActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case binding_phone_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(BindingActivity.this, str, Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
                case binding_phone_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(BindingActivity.this,getString(R.string.systembusy));
                    break;
                case binding_phone2_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        if (str.equals("1")) {
                            Toast.makeText(BindingActivity.this, getString(R.string.binding_prompt2), Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        } else {
                            Toast.makeText(BindingActivity.this, str, Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                case binding_phone2_err:
                    CommonTools.showShortToast(BindingActivity.this, getString(R.string.systembusy));
                    break;
            }
        }

        ;
    };

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        findViewById();
    }


    @Override
    protected void findViewById() {
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_prompt1 = (TextView) findViewById(R.id.tv_prompt1);
        tv_prompt2 = (TextView) findViewById(R.id.tv_prompt2);
        tv_prompt3 = (TextView) findViewById(R.id.tv_prompt3);
        tv_verify = (TextView) findViewById(R.id.tv_verify);
        tv_ts = (TextView) findViewById(R.id.tv_ts);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        rl_phone = (RelativeLayout) findViewById(R.id.rl_phone);
        _iv_back.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        tv_verify.setOnClickListener(this);

        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        isbinding = getIntent().getStringExtra("isbinding");
        if (type.equals("1")) {
            _tv_name.setText(getString(R.string.email_verification));
            rl_phone.setVisibility(View.GONE);
        } else if (type.equals("2")) {
            _tv_name.setText(getString(R.string.phonebinding));
            tv_ts.setText(getString(R.string.binding_prompt3));
            tv_send.setText(getString(R.string.binding_prompt4));
            ed_email.setHint(getString(R.string.binding_prompt5));
            tv_prompt1.setText(getString(R.string.binding_prompt6));
            tv_prompt2.setText(getString(R.string.binding_prompt7));
            tv_prompt3.setText(getString(R.string.binding_prompt3));
            rl_phone.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void ReGetData() {

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
            tv_verify.setText((millisUntilFinished / 1000) + getString(R.string.timing));
        }

        @Override
        public void onFinish() {
            tv_verify.setClickable(true);
            tv_verify.setText(getString(R.string.get_verification_code));
//        	tv_verify.setBackgroundColor(0xffFF5186);
        }
    };

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_send:
//                String string = ed_email.getText().toString();
//                if (TextUtils.isEmpty(string)) {
//                    Toast.makeText(BindingActivity.this, getString(R.string.release_goods144),
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (type.equals("2")) {
//                    String str = ed_phone.getText().toString();
//                    if (TextUtils.isEmpty(str)) {
//                        Toast.makeText(BindingActivity.this, getString(R.string.release_goods144),
//                                Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    netRun.BindingPhone2(str, string);
//                } else {
//                    netRun.YYEmailBinding(string);
//                }
//                break;
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_verify:
//                // 验证
//                String string2 = ed_phone.getText().toString();
//                if (TextUtils.isEmpty(string2)) {
//                    Toast.makeText(BindingActivity.this, getString(R.string.release_goods144),
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                tv_verify.setClickable(false);
//                timer.start();
////			tv_verify.setBackgroundColor(0xff808080);
//                netRun.BindingPhone(string2);
//                break;
//        }
        if(R.id.tv_send==v.getId()){
            String string = ed_email.getText().toString();
            if (TextUtils.isEmpty(string)) {
                Toast.makeText(BindingActivity.this, getString(R.string.release_goods144),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (type.equals("2")) {
                String str = ed_phone.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    Toast.makeText(BindingActivity.this, getString(R.string.release_goods144),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                netRun.BindingPhone2(str, string);
            } else {
                netRun.YYEmailBinding(string);
            }
        }else  if(R.id._iv_back==v.getId()){
            finish();
        }else if(R.id.tv_verify==v.getId()){
            // 验证
            String string2 = ed_phone.getText().toString();
            if (TextUtils.isEmpty(string2)) {
                Toast.makeText(BindingActivity.this, getString(R.string.release_goods144),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            tv_verify.setClickable(false);
            timer.start();
//			tv_verify.setBackgroundColor(0xff808080);
            netRun.BindingPhone(string2);
        }
    }
}
