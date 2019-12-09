package com.aite.a.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.APPSingleton.OnLocationListener;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.Area_list;
import com.aite.a.model.CategoryList;
import com.aite.a.model.NearbyList;
import com.aite.a.model.NearbyList.NearbyStoreList;
import com.aite.a.model.SelectCityinfo;
import com.aite.a.parse.JsonParse;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.Nearpopu;
import com.aite.a.utils.Nearpopu.calsshuidiao1;
import com.aite.a.utils.Nearpopu.huidiao;
import com.aite.a.utils.Nearpopu.julihuidiao1;
import com.aite.a.utils.Nearpopu.paixuhuidiao1;
import com.aite.a.utils.lingshi;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 周边列表
 *
 * @author CAOYOU
 */
public class NearbyActivity extends BaseActivity implements OnClickListener,
        OnLocationListener, AMapLocationListener {
    private ListView reriphery_list;
    private NetRun netRun;
    private TextView _tv_name, tv_address, _tv_back, tv_city1;
    private View tv_city;
    private ImageView _iv_right, _iv_back;
    private List<NearbyStoreList> nearbyStoreLists;
    private FrameLayout fl_cover;
    private LinearLayout sv_dizhi, ll_classify;
    private LocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private ListView lv_addresslist;
    private MyGridView gv_letter;
    private TextView tv_city11;
    private addressadapder addadapter;
    private SelectCityadapder selectcityadapder;
    private Button bt_returntop;
    private List<Area_list> arealist;
    private List<CategoryList> categoryOne;
    private String pointss = null;
    private SelectCityadapderlist selectCityadapderlist;
    public String city_id = "";
    public String area_idd = "";
    public String class_idd = "";
    public String sort_typee = "";
    public String sort_dis_typee = "";
    private List<SelectCityinfo> sc;
    private RelativeLayout il_touu;

    // 权限信息
    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;

    private List<SelectCityinfo> SelectCity = new ArrayList<SelectCityinfo>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case nearby__id:
                    try {
                        if (((List<NearbyStoreList>) msg.obj).size() >= 0) {
                            nearbyStoreLists = (List<NearbyStoreList>) msg.obj;
//						 peripheryAdapter
//						 .setNearbyStoreLists(processingData((List<NearbyStoreList>)
//						 msg.obj));
//						 peripheryAdapter.notifyDataSetChanged();
                            nearbyAdapter.setNearbyStoreLists(nearbyStoreLists);
                            nearbyAdapter.notifyDataSetChanged();
                        } else {
                            CommonTools.showShortToast(NearbyActivity.this,
                                    getI18n(R.string.act_no_data));
                        }
                    } catch (Exception e) {
                        CommonTools.showShortToast(NearbyActivity.this,
                                msg.obj.toString());
                    }
                    mdialog.dismiss();
                    break;
                case nearby__err:
                    CommonTools.showShortToast(NearbyActivity.this,
                            getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case nearby__start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
//				mdialog.show();
                    break;
//			case 0:
//				fl_cover.setVisibility(View.VISIBLE);
//				break;
//			case 1:
//				fl_cover.setVisibility(View.GONE);
//				break;
                case MSG_LOCATION_START:
                    _tv_back.setText(getString(R.string.member_centre27));
                    break;
                // 定位完成
                case MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation) msg.obj;
                    // String result = getLocationStr(loc);
                    List<String> locationStr = getLocationStr(loc);
                    if (locationStr != null && locationStr.size() != 0) {
                        if (!locationStr.get(0).equals(getString(R.string.member_centre28))) {
                            city = locationStr.get(0);
                            // tv_city1.setText(locationStr.get(0));
                            tv_city11.setText(locationStr.get(0));
                            _tv_back.setText(locationStr.get(0));
                            tv_address.setVisibility(View.VISIBLE);
                            tv_address.setText(locationStr.get(1));
//						locationClient.stopLocation();
                            netRun.getSelectCity();
                        }
                    }
                    break;
                case MSG_LOCATION_STOP:
                    // tv_1.setText("定位停止");
                    break;
                case nearbySelectCity__id:
                    if (msg.obj != null) {
                        Map<String, Object> map = (Map<String, Object>) msg.obj;
                        selectcityadapder = new SelectCityadapder(map);
                        lv_addresslist.setAdapter(selectcityadapder);
                        // 遍历得到定位的ID
                        for (int i = 0; i < zm.length; i++) {
                            sc = (List<SelectCityinfo>) map.get(zm[i]);
                            for (int j = 0; j < sc.size(); j++) {
                                if (_tv_back.getText().toString()
                                        .equals(sc.get(j).getArea_name())) {
                                    netRun.getArealist(sc.get(j).getArea_id());
                                }
                            }
                        }
                    }
                    break;
                case nearbySelectCity__err:
                    CommonTools.showShortToast(NearbyActivity.this,
                            getI18n(R.string.act_no_data_load));
                    break;
                case nearbySelectArea__id:
                    // TODO
                    if (msg.obj != null) {
                        arealist = (List<Area_list>) msg.obj;
                        lingshi.getInstance().setArealist(arealist);
                    }
                    break;
                case nearbySelectArea__err:

                    break;
                case two_category_id:
                    if (msg.obj != null) {
                        categoryOne = (List<CategoryList>) msg.obj;
                        lingshi.getInstance().setCategoryOne(categoryOne);
                    }
                    break;
                case two_category_err:

                    break;
            }
        }

        ;
    };

    public void getSelectCity() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, celectcity, null,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> arg0) {
                        Map<String, Object> map = (Map<String, Object>) JsonParse.getNearbySelectCity(arg0.result);
                        selectcityadapder = new SelectCityadapder(map);
                        lv_addresslist.setAdapter(selectcityadapder);
                        // 遍历得到定位的ID
                        for (int i = 0; i < zm.length; i++) {
                            sc = BeanConvertor.convertList(map.get(zm[i]), SelectCityinfo.class);
                            if (sc != null) {
                                for (int j = 0; j < sc.size(); j++) {
                                    if (_tv_back.getText().toString()
                                            .equals(sc.get(j).getArea_name())) {
                                        netRun.getArealist(sc.get(j).getArea_id());
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {

                    }
                });
    }

    private NearbyAdapter nearbyAdapter;
    private String city;
    private TextView categoryTv;
    private TextView distanceTv;
    private TextView distrinctTv;
    private TextView sortTv;

    /**
     * 距离分段排序
     *
     * @param nearbyStores
     * @return
     */
    private List<NearbyList> segmentedSorting(List<NearbyStoreList> nearbyStores) {
        List<NearbyList> nearbyLists = new ArrayList<NearbyList>();
        for (int i = 0; i < 11; i++) {
            NearbyList nearbyList = null;
            if (i == 0)
                nearbyList = new NearbyList(getI18n(R.string.m100),
                        processingDtata(0, 100, nearbyStores));
            if (i == 1)
                nearbyList = new NearbyList(getI18n(R.string.m200),
                        processingDtata(100, 200, nearbyStores));
            if (i == 2)
                nearbyList = new NearbyList(getI18n(R.string.m300),
                        processingDtata(200, 300, nearbyStores));
            if (i == 3)
                nearbyList = new NearbyList(getI18n(R.string.m400),
                        processingDtata(300, 400, nearbyStores));
            if (i == 4)
                nearbyList = new NearbyList(getI18n(R.string.m500),
                        processingDtata(400, 500, nearbyStores));
            if (i == 5)
                nearbyList = new NearbyList(getI18n(R.string.m600),
                        processingDtata(500, 600, nearbyStores));
            if (i == 6)
                nearbyList = new NearbyList(getI18n(R.string.m700),
                        processingDtata(600, 700, nearbyStores));
            if (i == 7)
                nearbyList = new NearbyList(getI18n(R.string.m800),
                        processingDtata(700, 800, nearbyStores));
            if (i == 8)
                nearbyList = new NearbyList(getI18n(R.string.m900),
                        processingDtata(800, 900, nearbyStores));
            if (i == 9)
                nearbyList = new NearbyList(getI18n(R.string.m1000),
                        processingDtata(900, 1000, nearbyStores));
            if (i == 10)
                nearbyList = new NearbyList(getI18n(R.string.m1000_more),
                        processingDtata(1000, 1000000000, nearbyStores));
            if (nearbyList != null)
                nearbyLists.add(nearbyList);
        }
        return nearbyLists;
    }

    /**
     * 删减排序的数据
     *
     * @param d
     * @param d2
     * @param nearbyStores
     * @return
     */
    private List<NearbyStoreList> processingDtata(double d, double d2,
                                                  List<NearbyStoreList> nearbyStores) {
        List<NearbyStoreList> storeLists = new ArrayList<NearbyStoreList>();
        for (int j = 0; j < nearbyStores.size(); j++) {
            double distance = Double.valueOf(nearbyStores.get(j).distance);
            if (distance > d && distance <= d2)
                storeLists.add(nearbyStores.get(j));
        }
        return storeLists;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reriphery_activity);
        getPersimmions();

//		AMapLocationClient.setApiKey("87b8c5522777a28c4808aea9bff7da28");
//		locationClient = new AMapLocationClient(this.getApplicationContext());
//		locationOption = new AMapLocationClientOption();
////		 设置定位模式为低功耗模式
//		locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
////		 设置定位监听
//		locationClient.setLocationListener(this);
        initLocationOption();
        findViewById();
        initView();
        initData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (locationClient != null) {
            locationClient.stop();
        }
    }

    @Override
    public void ReGetData() {
    }

    @Override
    protected void findViewById() {
        reriphery_list = (ListView) findViewById(R.id.reriphery_list);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        _tv_back = (TextView) findViewById(R.id._tv_back);
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_city = findViewById(R.id.tv_city);
        tv_address = (TextView) findViewById(R.id.tv_address);
        View tv_classification = findViewById(R.id.tv_classification);
        View tv_typee = findViewById(R.id.tv_typee);
        View tv_distance = findViewById(R.id.tv_distance);
//		 tv_city1 = (TextView) findViewById(R.id.tv_city1);
        _tv_name.setText(getI18n(R.string.tab_periphery));
        _iv_right = (ImageView) findViewById(R.id._iv_right);
        fl_cover = (FrameLayout) findViewById(R.id.fl_cover);
        sv_dizhi = (LinearLayout) findViewById(R.id.sv_dizhi);
        lv_addresslist = (ListView) findViewById(R.id.lv_addresslist);
        bt_returntop = (Button) findViewById(R.id.bt_returntop);
        il_touu = (RelativeLayout) findViewById(R.id.il_touu);
        ll_classify = (LinearLayout) findViewById(R.id.ll_classify);
        categoryTv = findViewById(R.id.nearby_category_tv);
        distanceTv = findViewById(R.id.nearby_distance_tv);
        distrinctTv = findViewById(R.id.nearby_distrinct_tv);
        sortTv = findViewById(R.id.nearby_sort_tv);
//		 gv_letter = (MyGridView) findViewById(R.id.gv_letter);
        _iv_back.setVisibility(View.GONE);
        _tv_back.setVisibility(View.VISIBLE);
        _iv_right.setImageResource(R.drawable.nearby_map);
        _iv_right.setOnClickListener(this);
        _tv_back.setOnClickListener(this);
        fl_cover.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        tv_classification.setOnClickListener(this);
        tv_typee.setOnClickListener(this);
        tv_distance.setOnClickListener(this);
        bt_returntop.setOnClickListener(this);

        _tv_back.setTextColor(0xFFFFFFFF);
        addadapter = new addressadapder();
//		 gv_letter.setAdapter(addadapter);
        View inflate = View.inflate(this, R.layout.zmlistitemm, null);
        GridView gv_letter1 = (GridView) inflate.findViewById(R.id.gv_letter1);
        tv_city11 = (TextView) inflate.findViewById(R.id.tv_city11);
        tv_city11.setOnClickListener(this);
        gv_letter1.setAdapter(addadapter);
        lv_addresslist.addHeaderView(inflate);
        lv_addresslist.setOnScrollListener(sl);
    }

    private OnScrollListener sl = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            setreturntop(firstVisibleItem);
        }
    };

    private void setreturntop(int item) {
        if (item >= 3) {
            bt_returntop.setVisibility(View.VISIBLE);
        } else {
            bt_returntop.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView() {
        // getScreenWidth(this);
        // //顶部适配
        // RelativeLayout.LayoutParams layoutParams =
        // (android.widget.RelativeLayout.LayoutParams)
        // il_touu.getLayoutParams();
        // layoutParams.height=getScreenWidth(this)/9;
        // il_touu.setLayoutParams(layoutParams);
        // //分类适配
        // RelativeLayout.LayoutParams Params =
        // (android.widget.RelativeLayout.LayoutParams)
        // ll_classify.getLayoutParams();
        // Params.height=(int) ((getScreenWidth(this)/10)*0.8);
        // ll_classify.setLayoutParams(Params);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        nearbyAdapter = new NearbyAdapter(this);
        reriphery_list.setAdapter(nearbyAdapter);
        // peripheryAdapter = new PeripheryAdapter(this);
        // reriphery_list.setAdapter(peripheryAdapter);
        // reriphery_list.setDividerHeight(30);
        // reriphery_list.setDivider(getResources().getDrawable(
        // R.color.result_view));
        appSingleton.setOnLocationListener(this);
        reriphery_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (nearbyStoreLists.get(position).store_points != null
                        && !nearbyStoreLists.get(position).store_points
                        .equals("")) {
                    Bundle bundle = new Bundle();
                    List<NearbyStoreList> StoreLists = new ArrayList<NearbyStoreList>();
                    StoreLists.add(nearbyStoreLists.get(position));
                    bundle.putSerializable("nearby", (Serializable) StoreLists);
                    openActivity(BaiduMapActivity.class, bundle);
                } else {
                    CommonTools.showShortToast(NearbyActivity.this,
                            getI18n(R.string.shop_position_empty));
                }
            }
        });
    }

    @Override
    protected void initData() {
        netRun.getCategoryTeo(null, 0);
        if (appSingleton.mlocation != null) {
            String points = appSingleton.mlocation.getLongitude() + ","
                    + appSingleton.mlocation.getLatitude();
            pointss = points;

            _tv_back.setText(appSingleton.mlocation.getCity());
            Positioning();
        } else {
            LocationDialog();
        }
        initOption();
        // // 只有持续定位设置定位间隔才有效，单次定位无效
        // // 设置为不是单次定位
//		 locationOption.setOnceLocation(false);
//		 // // 设置定位参数
//		 locationClient.setLocationOption(locationOption);
//		 // // 启动定位
//		 locationClient.startLocation();
        handler.sendEmptyMessage(MSG_LOCATION_START);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id._iv_right){
            Bundle bundle = new Bundle();
            bundle.putSerializable("nearby", (Serializable) nearbyStoreLists);
            bundle.putString("pointss", pointss);
            openActivity(BaiduMapActivity.class, bundle);
        }else if(v.getId()==R.id._tv_back){
            _tv_back.setVisibility(View.GONE);
            _iv_back.setVisibility(View.VISIBLE);
            sv_dizhi.setVisibility(View.VISIBLE);
            _iv_right.setVisibility(View.GONE);
        }else if(v.getId()==R.id.tv_city){
            showPopup(1);
        } else if (v.getId()==R.id.tv_classification) {
            showPopup(2);
        }else if(v.getId()== R.id.tv_typee){
            showPopup(3);
        }else if(v.getId()==R.id.tv_distance){
            showPopup(4);
        }else if(v.getId()==R.id.fl_cover){
            nearpopu.dismiss();
        }else if(v.getId()==R.id._iv_back){
            _iv_right.setVisibility(View.VISIBLE);
            sv_dizhi.setVisibility(View.GONE);
            _tv_back.setVisibility(View.VISIBLE);
            _iv_back.setVisibility(View.GONE);
        }else if(v.getId()== R.id.tv_city11){
            sv_dizhi.setVisibility(View.GONE);
            _iv_right.setVisibility(View.VISIBLE);
            _tv_back.setVisibility(View.VISIBLE);
            _iv_back.setVisibility(View.GONE);
            bt_returntop.setVisibility(View.GONE);
            _tv_back.setText(tv_city11.getText().toString());
        }else if(v.getId()==R.id.bt_returntop){
            lv_addresslist.smoothScrollToPosition(0);
        }


//        switch (v.getId()) {
//            case R.id._iv_right:
////			 Bundle bundle = new Bundle();
////			 bundle.putSerializable("nearby", (Serializable)
////			 nearbyStoreLists);
////			 openActivity(BaiduMapActivity.class, bundle);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("nearby", (Serializable) nearbyStoreLists);
//                bundle.putString("pointss", pointss);
//                openActivity(BaiduMapActivity.class, bundle);
//                break;
//            case R.id._tv_back:
//                _tv_back.setVisibility(View.GONE);
//                _iv_back.setVisibility(View.VISIBLE);
//                sv_dizhi.setVisibility(View.VISIBLE);
//                _iv_right.setVisibility(View.GONE);
//                break;
//            case R.id.tv_city:
//                showPopup(1);
//                break;
//            case R.id.tv_classification:
//                showPopup(2);
//                break;
//            case R.id.tv_typee:
//                showPopup(3);
//                break;
//            case R.id.tv_distance:
//                showPopup(4);
//                break;
//            case R.id.fl_cover:
//                nearpopu.dismiss();
//                break;
//            case R.id._iv_back:
//                _iv_right.setVisibility(View.VISIBLE);
//                sv_dizhi.setVisibility(View.GONE);
//                _tv_back.setVisibility(View.VISIBLE);
//                _iv_back.setVisibility(View.GONE);
//                break;
//            case R.id.tv_city11:
//                sv_dizhi.setVisibility(View.GONE);
//                _iv_right.setVisibility(View.VISIBLE);
//                _tv_back.setVisibility(View.VISIBLE);
//                _iv_back.setVisibility(View.GONE);
//                bt_returntop.setVisibility(View.GONE);
//                _tv_back.setText(tv_city11.getText().toString());
//                break;
//            case R.id.bt_returntop:
//                lv_addresslist.smoothScrollToPosition(0);
//                break;
//        }
    }

    public int popwstyle = 0;
    private Nearpopu nearpopu;

    private void showPopup(int pos) {
//		handler.sendEmptyMessageDelayed(0, 500);
        nearpopu = new Nearpopu(this, pos);
        nearpopu.sethuidiao(new huidiao() {

            @Override
            public void onItemClick(String area_id, String area_name) {
                distrinctTv.setText(area_name);
                area_idd = area_id;
                netRun.getNearby(pointss, city_id, area_idd, class_idd,
                        sort_typee, sort_dis_typee);
            }
        });
        nearpopu.setcalsshuidiao1(new calsshuidiao1() {

            @Override
            public void onItemClick(String class_id, String categoryName) {
                categoryTv.setText(categoryName);
                class_idd = class_id;
                netRun.getNearby(pointss, city_id, area_idd, class_idd,
                        sort_typee, sort_dis_typee);
            }
        });
        nearpopu.setpaixuhuidiao1(new paixuhuidiao1() {

            @Override
            public void onItemClick(String sort_type, String name) {
                sortTv.setText(name);
                sort_typee = sort_type;
                netRun.getNearby(pointss, city_id, area_idd, class_idd,
                        sort_typee, sort_dis_typee);
            }
        });
        nearpopu.setjulihuidiao1(new julihuidiao1() {

            @Override
            public void onItemClick(String sort_dis_type, String name) {
                distanceTv.setText(name);
                sort_dis_typee = sort_dis_type;
                netRun.getNearby(pointss, city_id, area_idd, class_idd,
                        sort_typee, sort_dis_typee);
            }
        });

        nearpopu.showAsDropDown(tv_city);
        nearpopu.setOnDismissListener(l);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nearpopu != null)
            nearpopu.dismiss();
    }

    private OnDismissListener l = new OnDismissListener() {

        @Override
        public void onDismiss() {
//			handler.sendEmptyMessageDelayed(1, 500);
        }
    };

    @Override
    public void getLocation(BDLocation location) {
        if (location != null) {
            String points = location.getLongitude() + ","
                    + location.getLatitude();
            netRun.getNearby(points, "", "", "", "", "");
            _tv_back.setText(location.getCity());
        } else {
            LocationDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (appSingleton.mlocation != null) {
            String points = appSingleton.mlocation.getLongitude() + ","
                    + appSingleton.mlocation.getLatitude();
            netRun.getNearby(points, "", "", "", "", "");
            _tv_back.setText(appSingleton.mlocation.getCity());
        } else {
            LocationDialog();
        }
    }

    private void LocationDialog() {
        _tv_back.setText(getI18n(R.string.no_position));
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle(getI18n(R.string.tip));// 设置对话框标题
        builder2.setMessage(getI18n(R.string.no_position_get));
        builder2.setNegativeButton(getI18n(R.string.cancel), null);
        builder2.setPositiveButton(getI18n(R.string.confirm),
                new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appSingleton.startLocation();
                    }
                });
    }

    // 全部城市开头字母
    String[] zm = new String[]{"A", "B", "C", "D", "F", "G", "H", "J",
            "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z",};
    int[] numm = new int[]{2, 1, 0, 1, 4, 3, 3, 2, 0, 1, 0, 0, 0, 3, 2, 5, 3,
            2, 0, 0, 1, 0};

    // 地址适配器
    public class addressadapder extends BaseAdapter {

        @Override
        public int getCount() {
            return zm.length;
        }

        @Override
        public Object getItem(int position) {
            return zm == null ? null : zm[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(NearbyActivity.this,
                        R.layout.zmitem, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tv_zm.setText(zm[position]);
            holder.tv_zm.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    lv_addresslist.smoothScrollToPositionFromTop(position + 1,
                            0);
                    // lv_addresslist.setSelection(position + 1);
                    // sv_dizhi.setScrollY(zb.get(position));
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_zm;

            public ViewHolder(View convertView) {
                tv_zm = (TextView) convertView.findViewById(R.id.tv_zm);
                convertView.setTag(this);
            }
        }
    }

    /**
     * 地址选择
     *
     * @author Administrator
     */
    public class SelectCityadapder extends BaseAdapter {
        // TODO
        List<SelectCityinfo> SelectCityy = new ArrayList<SelectCityinfo>();
        Map<String, Object> map = new HashMap<String, Object>();

        public SelectCityadapder(Map<String, Object> map) {
            this.map = map;
        }

        @Override
        public int getCount() {
            return zm.length;
        }

        @Override
        public Object getItem(int position) {
            return zm == null ? null : zm[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(NearbyActivity.this,
                        R.layout.selectcityitem, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            for (int i = 0; i < zm.length; i++) {
                SelectCityy = (List<SelectCityinfo>) map.get(zm[position]);
            }
            if (SelectCityy != null) {
                selectCityadapderlist = new SelectCityadapderlist(SelectCityy,
                        numm[position]);
            }
            holder.mgv_selsectcity.setAdapter(selectCityadapderlist);
            holder.tv_shouzimu.setText(zm[position]);
            holder.tv_shouzimu.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });

            return convertView;
        }

        class ViewHolder {
            MyGridView mgv_selsectcity;
            TextView tv_shouzimu;

            public ViewHolder(View convertView) {
                mgv_selsectcity = (MyGridView) convertView
                        .findViewById(R.id.mgv_selsectcity);
                tv_shouzimu = (TextView) convertView
                        .findViewById(R.id.tv_shouzimu);
                convertView.setTag(this);
            }
        }
    }


    /**
     * 选择城市列表
     *
     * @author Administrator
     */
    public class SelectCityadapderlist extends BaseAdapter {
        List<SelectCityinfo> SelectCity = new ArrayList<SelectCityinfo>();
        int num = 0;

        public SelectCityadapderlist(List<SelectCityinfo> SelectCity, int num) {
            this.SelectCity = SelectCity;
            this.num = num;
        }

        @Override
        public int getCount() {
            if (SelectCity.size() < 4) {
                return SelectCity.size() + 1 + num;
            } else {
                return SelectCity.size() + 2 + num;
            }

        }

        @Override
        public Object getItem(int position) {
            return SelectCity == null ? null : SelectCity.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(NearbyActivity.this,
                        R.layout.citylistitem, null);
                new ViewHolder(convertView);
            }
            final ViewHolder holder = (ViewHolder) convertView.getTag();
            try {
                if (position > 0 && position < 4) {
                    holder.tv_city.setText(SelectCity.get(position - 1)
                            .getArea_name());
                } else if (position > 4) {
                    holder.tv_city.setText(SelectCity.get(position - 2)
                            .getArea_name());
                }
            } catch (Exception e) {
                holder.tv_city.setText("");
            }
            holder.tv_city.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    sv_dizhi.setVisibility(View.GONE);
                    tv_address.setVisibility(View.GONE);
                    _iv_right.setVisibility(View.VISIBLE);
                    _tv_back.setVisibility(View.VISIBLE);
                    _iv_back.setVisibility(View.GONE);
                    bt_returntop.setVisibility(View.GONE);
                    _tv_back.setText(holder.tv_city.getText().toString());
                    // TODO
                    try {
                        if (position > 0 && position < 4) {
                            netRun.getArealist(SelectCity.get(position - 1)
                                    .getArea_id());
                            city_id = SelectCity.get(position - 1).getArea_id();
                        } else if (position > 4) {
                            netRun.getArealist(SelectCity.get(position - 2)
                                    .getArea_id());
                            city_id = SelectCity.get(position - 2).getArea_id();
                        }
                    } catch (Exception e) {

                    }
                    // SelectCity.get(position).getArea_id();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tv_city;

            public ViewHolder(View convertView) {
                tv_city = (TextView) convertView.findViewById(R.id.tv_city);
                convertView.setTag(this);
            }
        }

    }

    /**
     * 初始化定位参数配置
     */

    private void initLocationOption() {
        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("gcj02");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
        locationOption.setIsNeedLocationDescribe(true);
        locationOption.setNeedDeviceDirect(false);
        locationOption.setLocationNotify(true);
        locationOption.setIgnoreKillProcess(true);
        locationOption.setIsNeedLocationDescribe(true);
        locationOption.setIsNeedLocationPoiList(true);
        locationOption.SetIgnoreCacheException(false);
        locationOption.setOpenGps(true);
        locationClient.setLocOption(locationOption);
        locationClient.start();
    }

    /**
     * 实现定位回调
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();
            if (location.getCity() != null && !location.getCity().equals("")) {
                locationClient.stop();
                System.out.println("------------------定位  " + location.getCity());
                _tv_back.setText(location.getCity());
//               netRun.getSelectCity();
                getSelectCity();

                netRun.getNearby(location.getLongitude() + "," + location.getLatitude(), "", "", "", "", "");
                locationClient.stop();
            }

        }
    }

    /********* 定位 ************/
    private void Positioning() {
        final LocationClient locationClient = new LocationClient(
                getApplicationContext());
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 是否打开GPS
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
        option.setIsNeedAddress(true);
        option.setScanSpan(1000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        locationClient.setLocOption(option);
        // 注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(final BDLocation location) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (location.getCity() != null && !location.getCity().equals("")) {
                            System.out.println("------------------定位  " + location.getCity());
                            _tv_back.setText(location.getCity());
//                            netRun.getSelectCity();
                            getSelectCity();
                            netRun.getNearby(pointss, "", "", "", "", "");
                            locationClient.stop();
                        }

                    }
                });
            }
        });
        locationClient.start();
    }

    /**
     * 开始定位
     */
    public final static int MSG_LOCATION_START = 100;
    /**
     * 定位完成
     */
    public final static int MSG_LOCATION_FINISH = 101;
    /**
     * 停止定位
     */
    public final static int MSG_LOCATION_STOP = 102;

    public final static String KEY_URL = "URL";
    public final static String URL_H5LOCATION = "file:///android_asset/location.html";

    // 根据控件的选择，重新设置定位参数
    private void initOption() {
        if (locationOption == null) locationOption = new AMapLocationClientOption();
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        String strInterval = "1000";
        if (!TextUtils.isEmpty(strInterval)) {
            /**
             * 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算 只有持续定位设置定位间隔才有效，单次定位无效
             */
            locationOption.setInterval(Long.valueOf(strInterval));
        }
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = handler.obtainMessage();
            msg.obj = loc;
            msg.what = MSG_LOCATION_FINISH;
            handler.sendMessage(msg);
        }
    }

    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @param
     * @return
     */
    public synchronized List<String> getLocationStr(AMapLocation location) {
        List<String> list = new ArrayList<String>();
        if (null == location) {
            return null;
        }
        if (location.getErrorCode() == 0) {
            if (location.getProvider().equalsIgnoreCase(
                    android.location.LocationManager.GPS_PROVIDER)) {
            } else {
                list.add(location.getCity());
                list.add(location.getAddress());
            }
        } else {
            // 定位失败
            list.add(getString(R.string.member_centre28));
            list.add(getString(R.string.member_centre29) + location.getLocationDetail());
        }
        return list;
    }

    // 获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        assert manager != null;
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }


    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }

            if (permissions.size() > 0) {
                requestPermissions(
                        permissions.toArray(new String[permissions.size()]),
                        SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList,
                                  String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}
