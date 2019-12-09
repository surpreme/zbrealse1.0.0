package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.CreditsExchangeInfo;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 积分兑换
 *
 * @author Administrator
 */
public class CreditsExchangeActivity extends BaseActivity implements
        OnClickListener {
    private NetRun netRun;
    private TextView _tv_name, tv_present, tv_describe, tv_time, tv_limit;
    private ImageView _iv_back, iv_img;
    private BitmapUtils bitmapUtils;
    private CreditsExchangeInfo creditsExchangeInfo;
    private String vid;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case credits_exchange_id:
                    if (msg.obj != null) {
                        try {
                            creditsExchangeInfo = (CreditsExchangeInfo) msg.obj;
                            bitmapUtils.display(iv_img,
                                    creditsExchangeInfo.voucher_t_customimg);
                            tv_describe.setText(getString(R.string.integral_prompt11)
                                    + creditsExchangeInfo.voucher_t_points + getString(R.string.integrall) + " "
                                    + getString(R.string.integral_prompt12) + creditsExchangeInfo.voucher_t_storename
                                    + creditsExchangeInfo.voucher_t_price + getString(R.string.integral_prompt13)
                                    + creditsExchangeInfo.voucher_t_limit + getString(R.string.integral_prompt14)
                                    + creditsExchangeInfo.voucher_t_price + ")");
                            tv_time.setText(getString(R.string.integral_prompt15)
                                    + TimeStamp2Date(
                                    creditsExchangeInfo.voucher_t_end_date,
                                    "yyyy-MM-dd"));
                        } catch (Exception e) {
                            Toast.makeText(CreditsExchangeActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
                case credits_exchange_err:
                    Toast.makeText(CreditsExchangeActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case creditsexchange_submit_id:
                    if (msg.obj != null) {
//					ExchangeConfirmInfo
                        String str = (String) msg.obj;
                        Toast.makeText(CreditsExchangeActivity.this, str, Toast.LENGTH_SHORT).show();
                        if (str.equals(getString(R.string.integral_prompt16))) {
                            finish();
                        }
                    }
                    break;
                case creditsexchange_submit_err:
                    Toast.makeText(CreditsExchangeActivity.this, getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integral_exchange);
        findViewById();
    }


    @Override
    protected void findViewById() {
        tv_present = (TextView) findViewById(R.id.tv_present);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        tv_present = (TextView) findViewById(R.id.tv_present);
        tv_describe = (TextView) findViewById(R.id.tv_describe);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_limit = (TextView) findViewById(R.id.tv_limit);

        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.integral_prompt17));
        _iv_back.setOnClickListener(this);
        tv_present.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        initData();
    }

    @Override
    protected void initData() {
        vid = getIntent().getStringExtra("vid");
        netRun.CreditsExchange(vid);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_present:
//			// 提交
//			netRun.CreditsExchangeSubmit(vid);
//			break;
//		}
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_present) {
            // 提交
            netRun.CreditsExchangeSubmit(vid);
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
