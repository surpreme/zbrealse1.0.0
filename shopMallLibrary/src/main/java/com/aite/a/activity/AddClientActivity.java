package com.aite.a.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


import com.aite.a.base.Mark;
import com.aite.a.model.AreaInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CustomToolBar;
import com.aite.a.view.CustomToolBar.onLeftBtnClickListener;
import com.aiteshangcheng.a.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 添加客户
 */
public class AddClientActivity extends Activity implements Mark {

    private Context mContext;
    private CustomToolBar mCustomToolBar;
    private NetRun mNetRun;
    private EditText et_customer_name, et_customer_phone;
    private LinearLayout rel_client_address;
    private Button btn_save;
    private TextView tv_customer_address;
    private String username, mobile, area_info, province_id, city_id, area_id;
    private List<AreaInfo> mProvinceInfoList;// 省级
    private List<AreaInfo> mCityInfoList;// 市级
    private List<AreaInfo> mAreaInfoList;// 县级
    private int provinceCount = 0, cityCount = 0;
    private List<String> provinceList, cityList, areaList;// 存储省市县名称
    private HashMap<String, List<String>> cityMap = new HashMap<String, List<String>>();// 市级
    private HashMap<String, List<String>> areaMap = new HashMap<String, List<String>>();// 县级
    private Spinner sp_province, sp_city, sp_county;
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private CityAdapter2 cityAdapter2;
    private int islevel2address = 0;
    private List<String[]> address, address2, address3;

    @SuppressLint("HandlerLeak")
    private Handler hanlder = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case get_province_id:// 省级
                    if (msg.obj != null) {
                        provinceList = new ArrayList<String>();
                        mProvinceInfoList = (List<AreaInfo>) msg.obj;
                        for (int i = 0; i < mProvinceInfoList.size(); i++) {
                            provinceList.add(mProvinceInfoList.get(i).area_name);
                        }
                        mNetRun.getCityList(mProvinceInfoList.get(provinceCount).area_id);
                    }
                    break;
                case get_province_err:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case get_city_id:// 市级
                    if (msg.obj != null) {
                        cityList = new ArrayList<String>();
                        mCityInfoList = (List<AreaInfo>) msg.obj;
                        for (int i = 0; i < mCityInfoList.size(); i++) {
                            cityList.add(mCityInfoList.get(i).area_name);
                        }
                        System.out.println("--------------市级   " + cityList.size());
                        provinceAdapter = new ProvinceAdapter(
                                AddClientActivity.this,
                                android.R.layout.simple_spinner_item, cityList);
                        sp_province.setAdapter(provinceAdapter);

                        // cityMap.put(mProvinceInfoList.get(provinceCount).area_name,
                        // cityList);
                        // mNetRun.getAreaList(mCityInfoList.get(cityCount).area_id);
                    }
                    break;
                case get_city_err:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case get_area_id:// 县级
                    if (msg.obj != null) {
                        areaList = new ArrayList<String>();
                        mAreaInfoList = (List<AreaInfo>) msg.obj;
                        for (int i = 0; i < mAreaInfoList.size(); i++) {
                            areaList.add(mAreaInfoList.get(i).area_name);
                        }
                        areaMap.put(cityList.get(cityCount), areaList);
                        cityCount++;
                        if (cityCount < mCityInfoList.size()) {
                            mNetRun.getAreaList(mCityInfoList.get(cityCount).area_id);
                        } else {
                            cityCount = 0;
                            provinceCount++;
                            if (provinceCount < mProvinceInfoList.size()) {
                                mNetRun.getCityList(mProvinceInfoList
                                        .get(provinceCount).area_id);
                            } else {
                                rel_client_address.setClickable(true);
                            }
                        }
                    }
                    break;
                case get_area_err:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case add_customer_id:
                    String result = (String) msg.obj;
                    Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                    if (result.equals(getString(R.string.added_successfully))) {
                        finish();
                    }
                    break;
                case add_customer_err:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.systembusy),
                            Toast.LENGTH_SHORT).show();
                    break;
                case get_region_id:
                    if (msg.obj != null) {
                        if (islevel2address == 2) {
                            address3 = (List<String[]>) msg.obj;
                            // 地址2
                            List<String> adds = new ArrayList<String>();
                            adds.add(getString(R.string.bc_alldecorat12));
                            for (int i = 0; i < address3.size(); i++) {
                                adds.add(address3.get(i)[1]);
                            }
                            cityAdapter2 = new CityAdapter2(AddClientActivity.this,
                                    android.R.layout.simple_spinner_item, adds);
                            sp_county.setAdapter(cityAdapter2);
                            // cityAdapter.updata(adds);
                            islevel2address = 0;
                        } else if (islevel2address == 1) {
                            address2 = (List<String[]>) msg.obj;
                            // 地址2
                            List<String> adds = new ArrayList<String>();
                            adds.add(getString(R.string.bc_alldecorat12));
                            for (int i = 0; i < address2.size(); i++) {
                                adds.add(address2.get(i)[1]);
                            }
                            cityAdapter = new CityAdapter(AddClientActivity.this,
                                    android.R.layout.simple_spinner_item, adds);
                            sp_city.setAdapter(cityAdapter);
                            // cityAdapter.updata(adds);
                            islevel2address = 0;
                        } else if (islevel2address == 0) {
                            address = (List<String[]>) msg.obj;
                            // 地址
                            List<String> adds = new ArrayList<String>();
                            adds.add(getString(R.string.bc_alldecorat12));
                            for (int i = 0; i < address.size(); i++) {
                                adds.add(address.get(i)[1]);
                            }
                            System.out.println("--------------地址   " + adds.size());
                            provinceAdapter = new ProvinceAdapter(
                                    AddClientActivity.this,
                                    android.R.layout.simple_spinner_item, adds);
                            sp_province.setAdapter(provinceAdapter);
                        }
                    }
                    break;
                case get_region_err:
                    Toast.makeText(AddClientActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_client);
        init();
        setListener();
    }

    private void init() {
        mContext = AddClientActivity.this;
        mCustomToolBar = (CustomToolBar) findViewById(R.id.custom_toolbar);
        et_customer_name = (EditText) findViewById(R.id.et_customer_name);
        et_customer_phone = (EditText) findViewById(R.id.et_customer_phone);
        rel_client_address = (LinearLayout) findViewById(R.id.rel_client_address);
        tv_customer_address = (TextView) findViewById(R.id.tv_customer_address);
        sp_province = (Spinner) findViewById(R.id.sp_province);
        sp_city = (Spinner) findViewById(R.id.sp_city);
        sp_county = (Spinner) findViewById(R.id.sp_county);
        btn_save = (Button) findViewById(R.id.btn_save);
        mNetRun = new NetRun(mContext, hanlder);
        // mNetRun.getProvinceList(province_id);
        mNetRun.getSregionList("");
        // 省监听
        sp_province.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 地址监听
                String getaddressid = getaddressid(provinceAdapter
                        .getItem(position));
                if (getaddressid != null) {
                    islevel2address = 1;
                    mNetRun.getSregionList(getaddressid);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 市监听
        sp_city.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // 地址监听
                String getaddressid = getaddress2id(cityAdapter
                        .getItem(position));
                if (getaddressid != null) {
                    islevel2address = 2;
                    mNetRun.getSregionList(getaddressid);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setListener() {
        rel_client_address.setOnClickListener(clickListener);
        btn_save.setOnClickListener(clickListener);

        mCustomToolBar.setOnLeftBtnClickListener(new onLeftBtnClickListener() {

            @Override
            public void onLeftBtnClick() {
                // 返回
                finish();
            }
        });
    }

    private OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_save) {
                username = et_customer_name.getText().toString();
                mobile = et_customer_phone.getText().toString();
                String address1 = sp_province.getSelectedItem().toString();
                String address2 = sp_city.getSelectedItem().toString();
                String address3 = sp_county.getSelectedItem().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(mobile)) {
                    if (isChinaPhoneLegal(mobile)) {
                        if (address1 == null || address2 == null
                                || address3 == null) {
                            Toast.makeText(
                                    mContext,
                                    getResources().getString(
                                            R.string.please_choose_address),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            area_info = address1 + "-" + address2 + "-"
                                    + address3;
                            province_id = getaddressid(address1);
                            city_id = getaddress2id(address2);
                            area_id = getaddress3id(address3);
                            System.out.println("------------------"
                                    + "province_id=" + province_id
                                    + ",city_id=" + city_id + ",area_id="
                                    + area_id + ",area_info=" + area_info);

                            mNetRun.addCustomer(username, mobile, area_info,
                                    province_id, city_id, area_id);

                        }
                    } else {
                        Toast.makeText(
                                mContext,
                                getResources().getString(
                                        R.string.correct_phone_number),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext,
                            getResources().getString(R.string.edit_phone_name),
                            Toast.LENGTH_SHORT).show();
                }
            }

//			switch (v.getId()) {
//			// case R.id.rel_client_address:
//			// chooseThreeDialog("请选择", provinceList, cityMap, areaMap);
//			// for (int i = 0; i < provinceList.size(); i++) {
//			// Log.d("aaa", provinceList.get(i));
//			// // for (int j = 0; j <
//			// // cityMap.get(provinceList.get(i)).size(); j++) {
//			// // Log.d("aaa", cityMap.get(provinceList.get(i)).get(j));
//			// // }
//			// }
//			//
//			// break;
//			case R.id.btn_save:
//				username = et_customer_name.getText().toString();
//				mobile = et_customer_phone.getText().toString();
//				String address1 = sp_province.getSelectedItem().toString();
//				String address2 = sp_city.getSelectedItem().toString();
//				String address3 = sp_county.getSelectedItem().toString();
//				if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(mobile)) {
//					if (isChinaPhoneLegal(mobile)) {
//						if (address1 == null || address2 == null
//								|| address3 == null) {
//							Toast.makeText(
//									mContext,
//									getResources().getString(
//											R.string.please_choose_address),
//									Toast.LENGTH_SHORT).show();
//						} else {
//							area_info = address1 + "-" + address2 + "-"
//									+ address3;
//							province_id = getaddressid(address1);
//							city_id = getaddress2id(address2);
//							area_id = getaddress3id(address3);
//							System.out.println("------------------"
//									+ "province_id=" + province_id
//									+ ",city_id=" + city_id + ",area_id="
//									+ area_id + ",area_info=" + area_info);
//
//							mNetRun.addCustomer(username, mobile, area_info,
//									province_id, city_id, area_id);
//
//						}
//					} else {
//						Toast.makeText(
//								mContext,
//								getResources().getString(
//										R.string.correct_phone_number),
//								Toast.LENGTH_SHORT).show();
//					}
//				} else {
//					Toast.makeText(mContext,
//							getResources().getString(R.string.edit_phone_name),
//							Toast.LENGTH_SHORT).show();
//				}
//				break;
//			default:
//				break;
//			}
        }
    };

    /**
     * 省
     *
     * @author Administrator
     */
    private class ProvinceAdapter extends ArrayAdapter<String> {
        Context context;
        List<String> items = new ArrayList<String>();

        public ProvinceAdapter(final Context context,
                               final int textViewResourceId, final List<String> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getDropDownView(final int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xff808080);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(12);
            int px2dip = dip2px(context, 10);
            tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xff808080);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(12);
            // int px2dip = dip2px(context, 10);
            // tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }
    }

    /**
     * 市
     *
     * @author Administrator
     */
    private class CityAdapter extends ArrayAdapter<String> {
        Context context;
        List<String> items = new ArrayList<String>();

        /**
         * 更新数据
         *
         * @param items
         */
        public void updata(List<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        public CityAdapter(final Context context, final int textViewResourceId,
                           final List<String> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xff808080);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(12);
            int px2dip = dip2px(context, 10);
            tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xff808080);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(12);
            // int px2dip = dip2px(context, 10);
            // tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }
    }

    /**
     * 县
     *
     * @author Administrator
     */
    private class CityAdapter2 extends ArrayAdapter<String> {
        Context context;
        List<String> items = new ArrayList<String>();

        /**
         * 更新数据
         *
         * @param items
         */
        public void updata(List<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        public CityAdapter2(final Context context,
                            final int textViewResourceId, final List<String> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xff808080);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(12);
            int px2dip = dip2px(context, 10);
            tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items.get(position));
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(0xff808080);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(12);
            // int px2dip = dip2px(context, 10);
            // tv.setPadding(0, px2dip, 0, px2dip);
            return convertView;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 判断手机格式
     */
    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private String str1, str2, str3;


    /**
     * 获取地址ID
     *
     * @param name
     * @return
     */
    private String getaddressid(String name) {
        for (int i = 0; i < address.size(); i++) {
            if (name.equals(address.get(i)[1])) {
                return address.get(i)[0];
            }
        }
        return null;
    }

    /**
     * 获取地址2ID
     *
     * @param name
     * @return
     */
    private String getaddress2id(String name) {
        for (int i = 0; i < address2.size(); i++) {
            if (name.equals(address2.get(i)[1])) {
                return address2.get(i)[0];
            }
        }
        return null;
    }

    /**
     * 获取地址3ID
     *
     * @param name
     * @return
     */
    private String getaddress3id(String name) {
        for (int i = 0; i < address3.size(); i++) {
            if (name.equals(address3.get(i)[1])) {
                return address3.get(i)[0];
            }
        }
        return null;
    }

    /**
     * 获得城市名map集合
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public HashMap<String, List<String>> getCityNameMap(
            HashMap<String, List<AreaInfo>> areaInfos) {
        HashMap<String, List<String>> maps = new HashMap<String, List<String>>();
        Iterator iter = areaInfos.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            List<AreaInfo> areas = (List<AreaInfo>) entry.getValue();
            List<String> areaNames = getCityNameList(areas);
            maps.put(key, areaNames);
        }
        return maps;
    }

    /**
     * 获得城市名数组
     */
    public List<String> getCityNameList(List<AreaInfo> areaInfos) {
        List<String> listCityNames = new ArrayList<String>();
        for (int i = 0; i < areaInfos.size(); i++) {
            listCityNames.add(areaInfos.get(i).area_name);
        }
        return listCityNames;
    }

    /**
     * 获得城市ID
     */
    public String getCityId(List<AreaInfo> areaInfos, String area_name) {
        String cityId = null;
        for (int i = 0; i < areaInfos.size(); i++) {
            if (areaInfos.get(i).area_name.equals(area_name)) {
                cityId = areaInfos.get(i).area_id;
            }
        }
        return cityId;
    }
}
