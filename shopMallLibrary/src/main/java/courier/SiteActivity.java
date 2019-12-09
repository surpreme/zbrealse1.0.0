package courier;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.AddresSregionListActivity;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aiteshangcheng.a.R;

import java.util.List;

import courier.View.SmsModerPopu;
import courier.View.TimePopu;
import courier.model.MyStoreDataInfo;
import courier.model.SmsModerInfo;

/**
 * 设置站点信息
 * Created by Administrator on 2018/1/10.
 */
public class SiteActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rl_menu3, rl_menu4, rl_menu6;
    private TextView tv_sms, tv_name, tv_determine, tv_address, tv_time;
    private LinearLayout ll_break;
    private EditText et_name, et_phone, et_address;
    private String city_id, area_id;
    private List<SmsModerInfo> smsModerInfo;
    private String sms;
    private String starttime, endtime;
    private MyStoreDataInfo myStoreDataInfo;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case sms_mode_id:
                    //短信模板
                    if (msg.obj != null) {
                        smsModerInfo = (List<SmsModerInfo>) msg.obj;
                        if (smsModerInfo!=null&& smsModerInfo.size()!=0){
                            tv_sms.setText(smsModerInfo.get(0).mmt_name);
                            sms = smsModerInfo.get(0).mmt_code;
                            netRun.getDeliveryInfo();
                        }else{
                            tv_sms.setText("暂无短信模板");
                        }
                    }
                    break;
                case sms_mode_err:
                    Toast.makeText(SiteActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case edit_delivery_id:
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(SiteActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case edit_delivery_err:
                    Toast.makeText(SiteActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
                case getdelivery_info_id:
                    if (msg.obj!=null){//站点信息
                        myStoreDataInfo= (MyStoreDataInfo) msg.obj;
                        et_name.setText(myStoreDataInfo.dlyp_address_name);
                        et_phone.setText(myStoreDataInfo.dlyp_mobile);
                        if (myStoreDataInfo.business_store_time!=null&&myStoreDataInfo.business_colse_time!=null){
                            tv_time.setText(myStoreDataInfo.business_store_time+"至"+myStoreDataInfo.business_colse_time);
                        }
                        if (myStoreDataInfo.dlyp_area_info!=null){
                            tv_address.setText(myStoreDataInfo.dlyp_area_info);
                            city_id=myStoreDataInfo.dlyp_area_2;
                            area_id=myStoreDataInfo.dlyp_area_3;
                        }
                        if (myStoreDataInfo.dlyp_address!=null){
                            et_address.setText(myStoreDataInfo.dlyp_address);
                        }
                        if (myStoreDataInfo.sms_code!=null){
                            if (smsModerInfo!=null&& smsModerInfo.size()!=0){
                                for (int i=0;i<smsModerInfo.size();i++){
                                    if (myStoreDataInfo.sms_code.equals(smsModerInfo.get(i))){
                                        tv_sms.setText(smsModerInfo.get(i).mmt_name);
                                    }
                                }
                            }
                        }else{
                            tv_sms.setText("");
                        }
                    }
                    break;
                case getdelivery_info_err:
                    Toast.makeText(SiteActivity.this, "系统繁忙", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        findViewById();
    }

    @Override
    protected void findViewById() {
        rl_menu3 = (RelativeLayout) findViewById(R.id.rl_menu3);
        rl_menu4 = (RelativeLayout) findViewById(R.id.rl_menu4);
        rl_menu6 = (RelativeLayout) findViewById(R.id.rl_menu6);
        tv_sms = (TextView) findViewById(R.id.tv_sms);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_determine = (TextView) findViewById(R.id.tv_determine);
        tv_address = (TextView) findViewById(R.id.tv_address);
        ll_break = (LinearLayout) findViewById(R.id.ll_break);
        tv_time = (TextView) findViewById(R.id.tv_time);
        et_name= (EditText) findViewById(R.id.et_name);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_address= (EditText) findViewById(R.id.et_address);
        initView();
    }

    @Override
    protected void initView() {
        tv_name.setText("设置站点信息");
        ll_break.setVisibility(View.VISIBLE);
        rl_menu3.setOnClickListener(this);
        rl_menu4.setOnClickListener(this);
        rl_menu6.setOnClickListener(this);
        ll_break.setOnClickListener(this);
        tv_determine.setOnClickListener(this);
        netRun = new NetRun(this, handler);
        initData();
    }

    @Override
    protected void initData() {

        netRun.SmsMode();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_break) {
            finish();
        } else if (id == R.id.rl_menu3) {//时间
            showTimepopu();
        } else if (id == R.id.rl_menu4) {//地区
            Intent intent = new Intent(this, AddresSregionListActivity.class);
            startActivityForResult(intent, Sregion_Result);
        } else if (id == R.id.rl_menu6) {//短信模板
            showpopu();
        } else if (id == R.id.tv_determine) {//确认
            String phone = et_phone.getText().toString();
            String name = et_name.getText().toString();
            String area_info = tv_address.getText().toString();
            String address = et_address.getText().toString();
            if (TextUtils.isEmpty(phone) || phone.length() == 0) {
                Toast.makeText(SiteActivity.this, "手机号码没填", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(name) || name.length() == 0) {
                Toast.makeText(SiteActivity.this, "名称没填", Toast.LENGTH_SHORT).show();
                return;
            }
            if (area_info == null || address == null) {
                Toast.makeText(SiteActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                return;
            }
            if (starttime == null || endtime == null) {
                Toast.makeText(SiteActivity.this, "请选择时间", Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.EditDelivery(phone, name, city_id, area_id, area_info, address, starttime, endtime, sms);
        }
    }

    @Override
    public void ReGetData() {

    }


    /**
     * 弹出短信选择
     */
    private void showpopu() {
        if (smsModerInfo == null) return;
        SmsModerPopu smsModerPopu = new SmsModerPopu(SiteActivity.this, smsModerInfo);
        smsModerPopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        smsModerPopu.setmenu(new SmsModerPopu.menu() {
            @Override
            public void onItemClick(int id) {
                sms = smsModerInfo.get(id).mmt_code;
                tv_sms.setText(smsModerInfo.get(id).mmt_name);
            }
        });
        smsModerPopu.showAsDropDown(rl_menu6, 0, 0);
    }


    /**
     * 弹出时间选择
     */
    private void showTimepopu() {
        TimePopu timePopu = new TimePopu(SiteActivity.this);
        timePopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        timePopu.setmenu(new TimePopu.menu() {
            @Override
            public void onItemClick(String s1, String s2) {
                starttime = s1;
                endtime = s2;
                tv_time.setText(s1 + "至" + s2);
            }
        });
        timePopu.showAsDropDown(rl_menu3, 0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Sregion_Result://地址
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    String district = bundle.getString("district_name");
                    String city = bundle.getString("city_name");
                    String province = bundle.getString("province_name");
                    city_id = bundle.getString("city_id");
                    area_id = bundle.getString("area_id");
                    if (district != null && city != null && province != null
                            && city_id != null && area_id != null) {
                        tv_address.setText(province + city + district);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
