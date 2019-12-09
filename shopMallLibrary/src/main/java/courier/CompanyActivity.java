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

import courier.adapter.CompanyAdapter;
import courier.model.DeliveryCourierInfo;

/**
 * 快递公司列表
 * Created by Administrator on 2018/1/30.
 */
public class CompanyActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ll_break,ll_null;
    private TextView tv_name;
    private ListView lv_list;
    private List<DeliveryCourierInfo> deliveryCourierInfo;
    private CompanyAdapter companyAdapter;
    private boolean isedit=false;
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case delivery_courier_id:
                    if (msg.obj!=null){
                        deliveryCourierInfo= (List<DeliveryCourierInfo>) msg.obj;
                        if (deliveryCourierInfo==null||deliveryCourierInfo.size()==0){
                            ll_null.setVisibility(View.VISIBLE);
                        }else{
                            ll_null.setVisibility(View.GONE);
                            companyAdapter=new CompanyAdapter(handler,CompanyActivity.this,deliveryCourierInfo);
                            lv_list.setAdapter(companyAdapter);
                        }
                    }
                    break;
                case delivery_courier_err:
                    Toast.makeText(CompanyActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case 104://选中
                    if (msg.obj!=null){
                        if (isedit){
                            String str= (String) msg.obj;
                            Intent intent = new Intent(CompanyActivity.this, EditCostActivity.class);
                            intent.putExtra("id",str);
                            setResult(104,intent);
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
        setContentView(R.layout.activity_company);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break= (LinearLayout) findViewById(R.id.ll_break);
        ll_null= (LinearLayout) findViewById(R.id.ll_null);
        tv_name= (TextView) findViewById(R.id.tv_name);
        lv_list= (ListView) findViewById(R.id.lv_list);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("快递公司列表");
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        isedit = getIntent().getBooleanExtra("isedit", false);
        netRun=new NetRun(this,handler);
        initData();
    }

    @Override
    protected void initData() {
        netRun.DeliveryCourier();
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
