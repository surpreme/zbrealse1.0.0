package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.community.utils.ClutterUtils;

/**
 * 提现
 *
 * @author Administrator
 */
public class BalanceTxActivity extends Activity implements OnClickListener, Mark {
    private Button bt_txstart;
    private TextView _tv_name, tv_ye;
    private EditText et_txmoney, et_yhname, et_yhcode, et_yhtruename, et_zfbuser;
    private ImageView _iv_back;
    private LinearLayout ll_top;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case delivery_yuer_deposit_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(BalanceTxActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.equals(getString(R.string.successful_application))) {
                            Intent intent = new Intent(BalanceTxActivity.this, BalanceTx2Activity.class);
                            intent.putExtra("money", et_txmoney.getText().toString());
                            intent.putExtra("bank", et_yhname.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast toast = Toast.makeText(BalanceTxActivity.this, "提交失败", Toast.LENGTH_LONG);
                        toast.setText("提交失败");
                        toast.show();
                    }
                    break;
                case delivery_yuer_deposit_err:
                    Toast.makeText(BalanceTxActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_withdrawal);
        findViewById();
        initView();
    }

    protected void findViewById() {
        bt_txstart = (Button) findViewById(R.id.bt_txstart);
        tv_ye = (TextView) findViewById(R.id.tv_ye);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        et_txmoney = (EditText) findViewById(R.id.et_txmoney);
        et_yhname = (EditText) findViewById(R.id.et_yhname);
        et_yhcode = (EditText) findViewById(R.id.et_yhcode);
        et_yhtruename = (EditText) findViewById(R.id.et_yhtruename);
        et_zfbuser = (EditText) findViewById(R.id.et_zfbuser);
        ll_top = findViewById(R.id.ll_top);

        bt_txstart.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
    }

    protected void initView() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_top.getLayoutParams();
        layoutParams.height = (int) ((ClutterUtils.getScreenWidth(this) - ClutterUtils.dp2px(this, 40)) * 0.34714);
        ll_top.setLayoutParams(layoutParams);
        _tv_name.setText(getString(R.string.biaoti));
        String predepoit = getIntent().getStringExtra("predepoit");
        if (predepoit != null) {
            tv_ye.setText(predepoit);
        }
        netRun = new NetRun(this, handler);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_txstart://提现
//                String txmoney = et_txmoney.getText().toString();
//                String yhname = et_yhname.getText().toString();
//                String yhcode = et_yhcode.getText().toString();
//                String yhtruename = et_yhtruename.getText().toString();
//                String zfbuser = et_zfbuser.getText().toString();
//                if (TextUtils.isEmpty(txmoney)) {
//                    Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money1), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(yhname)) {
//                    Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money2), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(yhcode)) {
//                    Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money3), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(yhtruename)) {
//                    Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money4), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(zfbuser)) {
//                    Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money5), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                float i = Float.parseFloat(txmoney);
//                if (i < 1) {
//                    Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money6), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.DeliveryYuerDeposit(txmoney, yhname, yhcode, yhtruename, zfbuser);
//                break;
//            case R.id._iv_back:
//                finish();
//                break;
//        }

        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.bt_txstart) {
            String txmoney = et_txmoney.getText().toString();
            String yhname = et_yhname.getText().toString();
            String yhcode = et_yhcode.getText().toString();
            String yhtruename = et_yhtruename.getText().toString();
            String zfbuser = et_zfbuser.getText().toString();
            if (TextUtils.isEmpty(txmoney)) {
                Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money1), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(yhname)) {
                Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money2), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(yhcode)) {
                Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money3), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(yhtruename)) {
                Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money4), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(zfbuser)) {
                Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money5), Toast.LENGTH_SHORT).show();
                return;
            }
            float i = Float.parseFloat(txmoney);
            if (i < 1) {
                Toast.makeText(BalanceTxActivity.this, getString(R.string.withdraw_money6), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.DeliveryYuerDeposit(txmoney, yhname, yhcode, yhtruename, zfbuser);
        }
    }

}
