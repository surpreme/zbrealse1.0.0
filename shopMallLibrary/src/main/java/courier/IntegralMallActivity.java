package courier;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.CreditsExchangeActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.model.IntegralMallInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

import courier.adapter.IntegralMallAdapter;

/**
 * 积分商城
 * Created by Administrator on 2018/2/1.
 */
public class IntegralMallActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_break;
    private TextView tv_name,tv_all;
    private ImageView iv_top;
    private MyGridView gv_list;
    private IntegralMallInfo integralMallInfo;
    private IntegralMallAdapter integralMallAdapter;
    private String voucher_t_id;
    private NetRun netRun;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case integral_mall_id:
                    if (msg.obj != null) {
                        integralMallInfo = (IntegralMallInfo) msg.obj;
                        tv_all.setText(integralMallInfo.member_info.member_points+"积分");
                        integralMallAdapter=new IntegralMallAdapter(IntegralMallActivity.this,integralMallInfo.recommend_voucher,handler);
                        gv_list.setAdapter(integralMallAdapter);
                    }
                    break;
                case integral_mall_err:
                    Toast.makeText(IntegralMallActivity.this, "系统繁忙",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 111:
                    if (msg.obj!=null){
                        voucher_t_id= (String) msg.obj;
                        netRun.CreditsExchange(voucher_t_id);
                    }
                    break;
                case credits_exchange_id:
                    if (msg.obj != null) {
                        try {
                            String str=(String) msg.obj;
                            Toast.makeText(IntegralMallActivity.this, str,
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Intent intent2 = new Intent(IntegralMallActivity.this,
                                    CreditsExchangeActivity.class);
                            intent2.putExtra("vid", voucher_t_id);
                            startActivity(intent2);
                        }
                    }
                    break;
                case credits_exchange_err:
                    Toast.makeText(IntegralMallActivity.this, "系统繁忙",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yzintegralmall);
        findViewById();
    }

    @Override
    protected void findViewById() {
        ll_break= (LinearLayout) findViewById(R.id.ll_break);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_all= (TextView) findViewById(R.id.tv_all);
        iv_top= (ImageView) findViewById(R.id.iv_top);
        gv_list= (MyGridView) findViewById(R.id.gv_list);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("积分商城");
        ll_break.setVisibility(View.VISIBLE);
        ll_break.setOnClickListener(this);
        netRun=new NetRun(this,handler);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) iv_top.getLayoutParams();
        layoutParams.height= (int) (wm.getDefaultDisplay().getWidth()*0.47);
        iv_top.setLayoutParams(layoutParams);
        initData();
    }

    @Override
    protected void initData() {
        netRun.IntegralMall();
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
