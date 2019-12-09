package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ConsultingTypeinfo;
import com.aite.a.model.CustomerDetailsif;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyConsultingpopu;
import com.aite.a.view.MyConsultingpopu.huidiao;
import com.aite.a.view.MyShiGuangZhou;
import com.aiteshangcheng.a.R;

import java.util.List;

/**
 * 咨询平台客服
 *
 * @author Administrator
 */
public class ConsultingActivity extends BaseActivity implements OnClickListener {
    private TextView tv_type1, tv_type2, tv_type3, tv_xztype, tv_ts, tv_submit,
            tv_cancel, _tv_name, tv_customertype, tv_customercentent,
            tv_customertime, tv_customerstat, tv_replycentent;
    private MyShiGuangZhou msgz_progress;
    private EditText ed_centent;
    private ImageView _iv_back;
    private NetRun netRun;
    private List<CustomerDetailsif> customerDetailsif;
    private LinearLayout ll_details, ll_tj, ll_hfnr;
    private View v_hf;
    private List<ConsultingTypeinfo> consultingTypeinfo;
    private MyConsultingpopu myConsultingpopu;
    private ConsultingTypeinfo zxleix;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case customer_details_id:
                    if (msg.obj != null) {
                        customerDetailsif = (List<CustomerDetailsif>) msg.obj;
                        if (customerDetailsif.get(0).is_reply.equals("1")) {
                            msgz_progress.setProgress(3);
                            tv_type2.setTextColor(0xffFD4646);
                            tv_type3.setTextColor(0xffFD4646);
                        }
                        tv_customertype.setText(customerDetailsif.get(0).mct_name);
                        tv_customercentent
                                .setText(customerDetailsif.get(0).mc_content);
                        tv_customertime.setText(TimeStamp2Date(
                                customerDetailsif.get(0).mc_addtime,
                                "yyyy-MM-dd HH:mm:ss"));
                        tv_replycentent.setText(customerDetailsif.get(0).mc_reply);
                        if (customerDetailsif.get(0).is_reply.equals("1")) {
                            tv_customerstat.setText(getString(R.string.integral_prompt9));
                        } else {
                            tv_customerstat.setText(getString(R.string.integral_prompt10));
                            ll_hfnr.setVisibility(View.GONE);
                            v_hf.setVisibility(View.GONE);
                        }
                    }
                    break;
                case customer_details_err:
                    Toast.makeText(ConsultingActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case customer_add_id:
                    if (msg.obj != null) {
                        consultingTypeinfo = (List<ConsultingTypeinfo>) msg.obj;
                    }
                    break;
                case customer_add_err:
                    Toast.makeText(ConsultingActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case submit_customer_id:
                    if (msg.obj != null) {
                        String re = (String) msg.obj;
                        if (re.equals("1")) {
                            Toast.makeText(ConsultingActivity.this,
                                    getString(R.string.customerservice30),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
                case submit_customer_err:
                    Toast.makeText(ConsultingActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting);
        findViewById();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        tv_type1 = (TextView) findViewById(R.id.tv_type1);
        tv_type2 = (TextView) findViewById(R.id.tv_type2);
        tv_type3 = (TextView) findViewById(R.id.tv_type3);
        tv_xztype = (TextView) findViewById(R.id.tv_xztype);
        findViewById(R.id.tv_3).setOnClickListener(this);
        tv_ts = (TextView) findViewById(R.id.tv_ts);
        tv_submit = (TextView) findViewById(R.id.tv_submittj);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_customertype = (TextView) findViewById(R.id.tv_customertype);
        tv_customercentent = (TextView) findViewById(R.id.tv_customercentent);
        tv_customertime = (TextView) findViewById(R.id.tv_customertime);
        tv_customerstat = (TextView) findViewById(R.id.tv_customerstat);
        tv_replycentent = (TextView) findViewById(R.id.tv_replycentent);
        ll_details = (LinearLayout) findViewById(R.id.ll_details);
        ll_tj = (LinearLayout) findViewById(R.id.ll_tj);
        ll_hfnr = (LinearLayout) findViewById(R.id.ll_hfnr);
        v_hf = findViewById(R.id.v_hf);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        msgz_progress = (MyShiGuangZhou) findViewById(R.id.msgz_progress);
        ed_centent = (EditText) findViewById(R.id.ed_centent);
        tv_xztype.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(R.string.customerservice);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {
        Intent intent2 = getIntent();
        String mc_id = intent2.getStringExtra("mc_id");
        if (mc_id != null) {
            netRun.CustomerDetails(mc_id);
            ll_tj.setVisibility(View.GONE);
            tv_submit.setVisibility(View.GONE);
            ll_details.setVisibility(View.VISIBLE);
            tv_cancel.setText(getString(R.string.customerservice27));
        } else {
            ll_details.setVisibility(View.GONE);
            tv_submit.setVisibility(View.VISIBLE);
            ll_tj.setVisibility(View.VISIBLE);
            tv_cancel.setText(getString(R.string.customerservice19));
            netRun.AddCustomer();
        }
    }

    private void showpopu() {
        if (consultingTypeinfo != null) {
            myConsultingpopu = new MyConsultingpopu(ConsultingActivity.this,
                    consultingTypeinfo);
            myConsultingpopu.showAtLocation(tv_submit, Gravity.BOTTOM, 0, 0);// 设置显示位置
            myConsultingpopu.sethuidiao(new huidiao() {

                @Override
                public void onItemClick(ConsultingTypeinfo data) {
                    zxleix = data;
                    tv_xztype.setText(zxleix.mct_name);
                    tv_ts.setVisibility(View.VISIBLE);
                    String mct_introduce = zxleix.mct_introduce;
                    String replace = mct_introduce.replace("&lt;/p&gt;", "\n");
                    String replace3 = replace.replace("&lt;p&gt;", "");
                    tv_ts.setText(replace3);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_xztype) {
            showpopu();
        } else if (v.getId() == R.id.tv_3) {
            showpopu();
        } else if (v.getId() == R.id.tv_submittj) {
            if (zxleix == null) {
                Toast.makeText(ConsultingActivity.this,
                        getString(R.string.customerservice28),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(ed_centent.getText().toString())) {
                Toast.makeText(ConsultingActivity.this,
                        getString(R.string.customerservice29),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            System.out.println("---------------   " + zxleix.mct_id + "      "
                    + ed_centent.getText().toString());
            netRun.SubmitCustomer(zxleix.mct_id, ed_centent.getText()
                    .toString());
        } else if (v.getId() == R.id.tv_cancel) {
            finish();
        } else if (v.getId() == R.id._iv_back) {
            finish();
        }

//        switch (v.getId()) {
//            case R.id.tv_xztype:
//                showpopu();
//                break;
//            case R.id.tv_3:
//                showpopu();
//                break;
//            case R.id.tv_submittj:
//                if (zxleix == null) {
//                    Toast.makeText(ConsultingActivity.this,
//                            getString(R.string.customerservice28),
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(ed_centent.getText().toString())) {
//                    Toast.makeText(ConsultingActivity.this,
//                            getString(R.string.customerservice29),
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                System.out.println("---------------   " + zxleix.mct_id + "      "
//                        + ed_centent.getText().toString());
//                netRun.SubmitCustomer(zxleix.mct_id, ed_centent.getText()
//                        .toString());
//                break;
//            case R.id.tv_cancel:
//                finish();
//                break;
//            case R.id._iv_back:
//                finish();
//                break;
//        }
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
