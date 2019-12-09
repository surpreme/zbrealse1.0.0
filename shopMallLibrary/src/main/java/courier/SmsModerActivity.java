package courier;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import java.util.List;

import courier.adapter.SmsModerAdapter;
import courier.model.SmsModerInfo;

/**
 * 短信模板
 * Created by Administrator on 2018/1/9.
 */
public class SmsModerActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_break,ll_null;
    private TextView tv_name;
    private ListView lv_sms;
    private SmsModerAdapter smsModerAdapter;
    private List<SmsModerInfo>smsModerInfo;
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case sms_mode_id:
                    //短信模板
                    if (msg.obj!=null){
                        smsModerInfo= (List<SmsModerInfo>) msg.obj;
                        if (smsModerInfo==null||smsModerInfo.size()==0){
                            ll_null.setVisibility(View.VISIBLE);
                        }else{
                            ll_null.setVisibility(View.GONE);
                            smsModerAdapter=new SmsModerAdapter(SmsModerActivity.this,smsModerInfo,handler);
                            lv_sms.setAdapter(smsModerAdapter);
                        }
                    }
                    break;
                case sms_mode_err:
                    Toast.makeText(SmsModerActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 106://选择短信
                    if (msg.obj!=null){
                        boolean isrk = getIntent().getBooleanExtra("isrk", false);
                        if (isrk){
                            SmsModerInfo smsModerInfo = (SmsModerInfo) msg.obj;
                            Intent intent = new Intent(SmsModerActivity.this, CourierHomeTabActivity.class);
                            intent.putExtra("id",smsModerInfo.mmt_code);
                            intent.putExtra("name",smsModerInfo.mmt_short_content);
                            setResult(997,intent);
                            finish();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsmoder);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        ll_null = (LinearLayout) findViewById(R.id.ll_null);
        tv_name = (TextView) findViewById(R.id.tv_name);
        lv_sms = (ListView) findViewById(R.id.lv_sms);
        initView();
    }

    @Override
    protected void initView() {
        ll_break.setVisibility(View.VISIBLE);
        tv_name.setText("短信模板");
        ll_break.setOnClickListener(this);
        netRun=new NetRun(this,handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.SmsMode();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_break) {
            finish();
        }
    }

    @Override
    public void ReGetData() {

    }
}
