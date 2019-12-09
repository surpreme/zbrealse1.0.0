package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

/**
 * 绑定支付密码第二部
 *
 * @author Administrator
 */
public class PayPsw2Activity extends BaseActivity implements OnClickListener {

    private EditText ed_psw1, ed_psw2;
    private TextView tv_confirm, _tv_name;
    private ImageView _iv_back;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case pay_psw_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(PayPsw2Activity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.contains("成功")) {
                            finish();
                        }
                    }
                    break;
                case pay_psw_err:
                    Toast.makeText(PayPsw2Activity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };
    private long auth_modify_paypwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypsw2);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ed_psw1 = (EditText) findViewById(R.id.ed_psw1);
        ed_psw2 = (EditText) findViewById(R.id.ed_psw2);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _iv_back.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.order_reminder39));
        auth_modify_paypwd = System.currentTimeMillis();
        netRun = new NetRun(this, handler);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id._iv_back){
            finish();
        }else if(v.getId()== R.id.tv_confirm){
            //提交
            String string = ed_psw1.getText().toString();
            String string2 = ed_psw2.getText().toString();
            if (TextUtils.isEmpty(string)) {
                Toast.makeText(PayPsw2Activity.this, getString(R.string.inputpsw), Toast.LENGTH_SHORT).show();
                return;
            } else if (string.length() == 0 || string.length() < 21) {
                Toast.makeText(PayPsw2Activity.this, "请输入6-21位密码", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(string2)) {
                Toast.makeText(PayPsw2Activity.this, getString(R.string.order_reminder40), Toast.LENGTH_SHORT).show();
                return;
            } else if (string.length() == 0 || string.length() < 21) {
                Toast.makeText(PayPsw2Activity.this, "请输入6-21位密码", Toast.LENGTH_SHORT).show();
            }
            netRun.PayPsw(string, string2, auth_modify_paypwd + "");
        }

//        switch (v.getId()) {
//            case R.id._iv_back:
//                finish();
//                break;
//            case R.id.tv_confirm:
//                //提交
//                String string = ed_psw1.getText().toString();
//                String string2 = ed_psw2.getText().toString();
//                if (TextUtils.isEmpty(string)) {
//                    Toast.makeText(PayPsw2Activity.this, getString(R.string.inputpsw), Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (string.length() == 0 || string.length() < 21) {
//                    Toast.makeText(PayPsw2Activity.this, "请输入6-21位密码", Toast.LENGTH_SHORT).show();
//                }
//                if (TextUtils.isEmpty(string2)) {
//                    Toast.makeText(PayPsw2Activity.this, getString(R.string.order_reminder40), Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (string.length() == 0 || string.length() < 21) {
//                    Toast.makeText(PayPsw2Activity.this, "请输入6-21位密码", Toast.LENGTH_SHORT).show();
//                }
//                netRun.PayPsw(string, string2, auth_modify_paypwd + "");
//                break;
//        }
    }
}
