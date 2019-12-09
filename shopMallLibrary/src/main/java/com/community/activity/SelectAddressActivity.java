package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aiteshangcheng.a.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.community.adapter.PostedAddressAdapter;
import com.community.model.SelectAddressInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择位置
 * Created by Administrator on 2017/12/16.
 */
public class SelectAddressActivity extends BaseActivity implements View.OnClickListener, OnGetPoiSearchResultListener {
    private TextView tv_break;
    private ListView lv_address;
    private EditText et_search;
    private ImageView iv_search;
    private String chooseaddress;
    private double Latitude, Longitude;
    private List<SelectAddressInfo> address;
    private PostedAddressAdapter postedAddressAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 105:
                    if (postedAddressAdapter==null){
                        postedAddressAdapter = new PostedAddressAdapter(SelectAddressActivity.this, handler, address);
                        lv_address.setAdapter(postedAddressAdapter);
                    }else{
                        postedAddressAdapter.refreshData(address);
                    }
                    break;
                case 106:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Intent intent = getIntent();
                        intent.putExtra("address", str);
                        setResult(107, intent);
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapaddress);
        findViewById();
    }

    @Override
    protected void findViewById() {
        tv_break = (TextView) findViewById(R.id.tv_break);
        lv_address = (ListView) findViewById(R.id.lv_address);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);

        initView();
    }

    @Override
    protected void initView() {
        et_search.setOnEditorActionListener(action);
        tv_break.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        chooseaddress = getIntent().getStringExtra("chooseaddress");
        address = new ArrayList<>();
//        if (chooseaddress.equals("所在位置")) {
//            address.add(new SelectAddressInfo("不显示", true));
//        } else {
//            address.add(new SelectAddressInfo("不显示", false));
//        }
        initData();
    }

    @Override
    protected void initData() {
        Positioning();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_break:
//                finish();
//                break;
//            case R.id.iv_search://搜索
//                String s = et_search.getText().toString();
//                if (TextUtils.isEmpty(s)) {
//                    s = getString(R.string.find_reminder113);
//                }
//                address.clear();
//                searchNeayBy(Latitude, Longitude, s);
//                break;
//        }
        if(v.getId()==R.id.tv_break){
            finish();
        }else if(v.getId()==R.id.iv_search){
            //搜索
            String s = et_search.getText().toString();
            if (TextUtils.isEmpty(s)) {
                s = getString(R.string.find_reminder113);
            }
            address.clear();
            searchNeayBy(Latitude, Longitude, s);
        }
    }


    private void Positioning() {
        LocationClient locationClient = new LocationClient(getApplicationContext());
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 是否打开GPS
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        // 注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
//				System.out.println("AddrStr  " + location.getAddrStr());
//				System.out.println("City  " + location.getCity());
//				System.out.println("Altitude  " + location.getAltitude());
//				System.out.println("Latitude  " + location.getLatitude());
                Latitude = location.getLatitude();
                Longitude = location.getLongitude();
                searchNeayBy(Latitude, Longitude, getString(R.string.find_reminder113));
            }
        });
        locationClient.start();
    }

    private PoiSearch mPoiSearch;

    private void searchNeayBy(double latitude, double longitude, String keyword) {
        // POI初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();

        poiNearbySearchOption.keyword(keyword);
        poiNearbySearchOption.location(new LatLng(latitude, longitude));
        poiNearbySearchOption.radius(100);  // 检索半径，单位是米
        poiNearbySearchOption.pageCapacity(20);  // 默认每页10条
        mPoiSearch.searchNearby(poiNearbySearchOption);  // 发起附近检索请求
    }

    @Override
    public void onGetPoiResult(PoiResult result) {
        // 获取POI检索结果
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            Toast.makeText(SelectAddressActivity.this, getString(R.string.find_reminder114), Toast.LENGTH_LONG).show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            if (result != null) {
                if (result.getAllPoi() != null && result.getAllPoi().size() > 0) {
                    for (int i = 0; i < result.getAllPoi().size(); i++) {
                        if (chooseaddress.equals(result.getAllPoi().get(i).name)) {
                            address.add(new SelectAddressInfo(result.getAllPoi().get(i).name, true,result.getAllPoi().get(i).address));
                        } else {
                            address.add(new SelectAddressInfo(result.getAllPoi().get(i).name, false,result.getAllPoi().get(i).address));
                        }
                    }
                    handler.sendEmptyMessage(105);
                }
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
    }

    @Override
    public void ReGetData() {
    }


    private TextView.OnEditorActionListener action = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEND || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("--------------------", "搜索  " + textView.getText().toString());
                    String s=textView.getText().toString();
                    if (TextUtils.isEmpty(s)) {
                        s = getString(R.string.find_reminder113);
                    }
                    address.clear();
                    searchNeayBy(Latitude, Longitude, s);
                }
                return true;
            }
            return false;
        }
    };
}
