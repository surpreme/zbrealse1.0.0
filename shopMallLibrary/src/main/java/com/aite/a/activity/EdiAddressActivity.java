package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.AddressInfo;
import com.aite.a.model.GooglePositionInfo;
import com.aite.a.model.gpsInfoToAppInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.EditTextWithDel;
import com.aiteshangcheng.a.R;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * 编辑和新增地址
 *
 * @author CAOYOU
 */
public class EdiAddressActivity extends BaseActivity implements
        OnClickListener, AMapLocationListener {
    // ==标题
    private ImageView iv_back, iv_right;
    private TextView tv_title;
    private Dialog dialog;
    private EditTextWithDel et_name, et_phone, et_address;
    private ImageView iv_contacts;
    private Button btn_submit;
    private RelativeLayout rl_dialog_show;
    private RelativeLayout rl_all;
    private gpsInfoToAppInfo gpsInfo;
    private TextView et_current_address, tv_shouhuo, tv_dianhua;
    private String city_id, province_id, district_id;
    private String area_id;
    private EditTextWithDel et_mobile;
    private SharedPreferences sp;
    private AddressInfo addressInfo = new AddressInfo();
    private String areaLat,areaLng;
    private NetRun netRun;
    // 声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    // 声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case edit_address_id:
                    if (msg.obj.equals("1")) {
                        if (addressInfo != null) {
                            openActivity(OrderSureActivity.class);
                            CommonTools.showShortToast(EdiAddressActivity.this,
                                    getI18n(R.string.update_success));
                        } else {
                            CommonTools.showShortToast(EdiAddressActivity.this,
                                    getI18n(R.string.add_success));
                        }
                        EdiAddressActivity.this.finish();
                    } else {
                        if (addressInfo != null)
                            CommonTools.showShortToast(EdiAddressActivity.this,
                                    getI18n(R.string.update_fail));
                        else
                            CommonTools.showShortToast(EdiAddressActivity.this,
                                    getI18n(R.string.add_fail));
                    }
                    mdialog.dismiss();
                    break;
                case edit_address_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(EdiAddressActivity.this,
                            getI18n(R.string.act_net_error));
                    break;
                case edit_address_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
                    mdialog.show();
                    break;
                case gpsinfoto_app_id://定位地址
                    if (msg.obj != null) {
                        gpsInfo = (gpsInfoToAppInfo) msg.obj;
                        et_current_address.setText(gpsInfo.province_name + "-" + gpsInfo.city_name + "-" + gpsInfo.area_name);
                        city_id = gpsInfo.city_id;
                        area_id = gpsInfo.area_id;
                    }
                    break;
                case gpsinfoto_app_err:

                    break;
            }
        }

        ;
    };
    private boolean edit;

    // ===============================pop===
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_address_activity);
        netRun = new NetRun(this, handler);
        sp = getSharedPreferences("AddressManageActivity", MODE_PRIVATE);
        if (getIntent().getExtras() != null) {
            addressInfo = (AddressInfo) getIntent().getExtras()
                    .getSerializable("addressInfo");
            edit = (boolean) getIntent().getExtras().get("edit");
        }
        findViewById();
        initView();
        initData();
    }

    @Override
    protected void findViewById() {
        rl_all = (RelativeLayout) findViewById(R.id.new_address_rl_all);
        rl_dialog_show = (RelativeLayout) findViewById(R.id.new_address_rl_dialog_show);
        btn_submit = (Button) findViewById(R.id.new_btn_submit);
        et_name = (EditTextWithDel) findViewById(R.id.new_et_name);
        et_phone = (EditTextWithDel) findViewById(R.id.new_et_phone);
        et_address = (EditTextWithDel) findViewById(R.id.new_et_address);
        et_mobile = (EditTextWithDel) findViewById(R.id.new_tv_mobile);
        et_current_address = (TextView) findViewById(R.id.current_address);
        // iv_contacts = (ImageView) findViewById(R.id.new_address_iv_contacts);
        iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_right = (ImageView) findViewById(R.id._iv_right);
        tv_title = (TextView) findViewById(R.id._tv_name);
        tv_shouhuo = (TextView) findViewById(R.id.tv_shouhuo);
        tv_dianhua = (TextView) findViewById(R.id.tv_dianhua);

        tv_dianhua.post(new Runnable() {

            @Override
            public void run() {
                width = tv_dianhua.getWidth();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        client(tv_shouhuo);
                    }
                });
            }
        });
    }

    private int width;

    private void client(TextView view) {
        LayoutParams para = (LayoutParams) view.getLayoutParams();
        para.width = width;
        view.setLayoutParams(para);
    }

    @Override
    protected void initView() {
        rl_dialog_show.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        // iv_contacts.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        // iv_right.setBackgroundResource(R.drawable.guanbi);
        iv_right.setOnClickListener(this);
        et_current_address.setOnClickListener(this);

        // 初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        dingwei();
    }

    @Override
    public void initData() {
        if (addressInfo == null) {
            tv_title.setText(getI18n(R.string.new_address));
        } else {
            tv_title.setText(getI18n(R.string.update_address));
            if (addressInfo.getTrue_name() == null || addressInfo.getTrue_name().length() == 0) {
                tv_title.setText(getI18n(R.string.new_address));
                netRun.gpsInfoToApp();
            }
            et_name.setText(addressInfo.getTrue_name());
            et_phone.setText(addressInfo.getTel_phone());
            et_mobile.setText(addressInfo.getMob_phone());
            et_current_address.setText(addressInfo.getArea_info());
            et_address.setText(addressInfo.getAddress());
            area_id = addressInfo.area_id;
            city_id = addressInfo.city_id;
            Editor editor = sp.edit();
            editor.putString("address_id", addressInfo.getAddress_id());
            editor.commit();
        }

    }

    private void dingwei() {
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        // mLocationOption.setOnceLocationLatest(true);
        // 设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocationClient.startLocation();
    }

    // 声明定位回调监听器
    AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                System.out.println("---------------code--" + amapLocation.getErrorCode());
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    et_current_address.setText(amapLocation.getProvince() + "  " + amapLocation.getCity() + "  " + amapLocation.getDistrict());
                    et_address.setText(amapLocation.getStreet() + amapLocation.getStreetNum());
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    /**
     * 提交数据
     *
     * @param add_address_port
     */
    private void SubmitPdu(String add_address_port) {
        String name = et_name.getText().toString();
        String phone = et_phone.getText().toString();
        String mobile = et_mobile.getText().toString();
        String address = et_address.getText().toString();
        String current_address = et_current_address.getText().toString();
        if (name.equals("")) {
            CommonTools.showShortToast(this, getI18n(R.string.contact_empty));
        } else {
            if (city_id == null || area_id == null) {
                CommonTools.showShortToast(this,
                        getI18n(R.string.location_empty));
            } else {
                if (current_address.equals("")) {
                    CommonTools.showShortToast(this,
                            getI18n(R.string.detail_empty));
                } else {
                    if (mobile.equals("") && phone.equals("")) {
                        CommonTools.showShortToast(this,
                                getI18n(R.string.phone_empty));
                    } else {
                        netRun.postAddress(addressInfo.address_id, city_id,
                                area_id,province_id ,district_id,name, phone, mobile, address,
                                current_address, add_address_port, areaLat+","+areaLng);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.new_btn_submit){
            if (edit) {
                SubmitPdu(update_address);
            } else {
                SubmitPdu(add_address);
            }
        }else if(v.getId()== R.id._iv_back){
            finish();
            overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
        }else if(v.getId()==R.id._iv_right){
            finish();
            overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
        }else if(v.getId()==R.id.current_address){
            Intent intent = new Intent();
            intent.setClass(this, AddresSregionListActivity.class);
            startActivityForResult(intent, Sregion_Result);
        }

//        switch (v.getId()) {
//            case R.id.new_address_rl_dialog_show:
//                break;
//            case R.id.new_btn_submit:// 确认
//                if (edit) {
//                    SubmitPdu(update_address);
//                } else {
//                    SubmitPdu(add_address);
//                }
//                break;
//            // case R.id.new_address_iv_contacts:// 跳转到联系人界面
//            // // intent = new Intent(Intent.ACTION_PICK);
//            // // intent.setType("vnd.android.cursor.dir/phone");
//            // // startActivityForResult(intent, 0);
//            // break;
//            case R.id._iv_back:
//                finish();
//                overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
//                break;
//
//            case R.id._iv_right:
//                finish();
//                overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
//                break;
//            case R.id.current_address:
//                Intent intent = new Intent();
//                intent.setClass(this, AddresSregionListActivity.class);
//                startActivityForResult(intent, Sregion_Result);
//                break;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            String district = bundle.getString("district_name");
            String city = bundle.getString("city_name");
            String province = bundle.getString("province_name");
            String area = bundle.getString("area_name");
            province_id = bundle.getString("province_id");//国家
            city_id = bundle.getString("city_id");//省
            district_id = bundle.getString("district_id");//市
            area_id = bundle.getString("area_id");//区
            if (district != null && city != null && province != null && area != null
                    && city_id != null && area_id != null) {
                et_current_address.setText(province + city + district + area);
                getAddressPosition(et_current_address.getText().toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void ReGetData() {
        initData();
    }

    @Override
    public void onLocationChanged(AMapLocation arg0) {
        // TODO Auto-generated method stub

    }

    private void getAddressPosition(String address){
        netRun.getAddressPosition(address, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                GooglePositionInfo positionInfo = BeanConvertor.convertBean(responseInfo.result,GooglePositionInfo.class);
                areaLat= String.valueOf(positionInfo.getResults().get(0).getGeometry().getLocation().getLat());
                areaLng= String.valueOf(positionInfo.getResults().get(0).getGeometry().getLocation().getLng());
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
