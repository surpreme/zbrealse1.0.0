package com.aite.a.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

import com.aiteshangcheng.a.R;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;

/**
 * 地区列表
 *
 * @author CAOYOU
 */
public class AddresSregionListActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
    private static List<String[]> provinceList = new ArrayList<String[]>();
    private List<String[]> cityList = new ArrayList<String[]>();
    private List<String[]> districtList = new ArrayList<String[]>();
    private List<String[]> areaList = new ArrayList<String[]>();
    private TextView rb_province, rb_country;
    private TextView rb_city;
    private TextView rb_district;
    private ListView lv_city;
    private List<String[]> stringlist = new ArrayList<String[]>();
    private Button submit;
    private String district_name = null;
    private String city_name = null;
    private String province_name = null;
    private String area_name = null;
    /**
     * 选择次数
     */
    private int clickNumber = 0;
    private String city_id = null;
    private String district_id = null;
    private String area_id = null;
    private String province_id = null;
    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case get_region_id:
                    List<String[]> provinceList = (List<String[]>) msg.obj;
                    if (provinceList.size() > 0) {
                        setStringlist(provinceList);
                        initView(provinceList);
                        if (clickNumber == 0) {
                            AddresSregionListActivity.provinceList = provinceList;
                        }
                        if (clickNumber == 1) {
                            cityList = provinceList;
                        }
                        if (clickNumber == 2) {
                            districtList = provinceList;
                        }
                        if (clickNumber == 3) {
                            areaList = provinceList;
                        }
                    } else {
                        CommonTools.showShortToast(AddresSregionListActivity.this, getI18n(R.string.act_no_data_load));
                    }
                    mdialog.dismiss();
                    break;
                case get_region_err:
                    CommonTools.showShortToast(AddresSregionListActivity.this, getI18n(R.string.act_net_error));
                    mdialog.dismiss();
                    break;
                case get_region_start:
//				mdialog.setMessage(getI18n(R.string.act_loading));
                    mdialog.show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_new_selectcity);
        findViewById();
        initView();
        initData();
    }

    @Override
    protected void findViewById() {
        rb_country = (TextView) findViewById(R.id.rb_country);
        rb_province = (TextView) findViewById(R.id.rb_province);
        rb_city = (TextView) findViewById(R.id.rb_city);
        rb_district = (TextView) findViewById(R.id.rb_district);
        lv_city = (ListView) findViewById(R.id.lv_city);
        submit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        rb_country.setOnClickListener(this);
        rb_province.setOnClickListener(this);
        rb_city.setOnClickListener(this);
        rb_district.setOnClickListener(this);
        lv_city.setOnItemClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        clickNumber = 0;
        netRun.getSregionList(null);
        rb_province.setText(getI18n(R.string.province));
        rb_city.setText(getI18n(R.string.city));
        rb_district.setText(getI18n(R.string.area));
    }

    /**
     * 初始化地区列表
     *
     * @param provinceList
     */
    protected void initView(List<String[]> provinceList) {
        ArrayList<HashMap<String, Object>> imagelist = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map1;
        for (String[] list : provinceList) {
            map1 = new HashMap<String, Object>();
            map1.put("id", list[1]);
            imagelist.add(map1);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist, android.R.layout.test_list_item, new String[]{"id"}, new int[]{android.R.id.text1});
        simpleAdapter.setViewBinder(new ViewBinder() {
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if ((view instanceof ImageView)) {
                    ImageView iv = (ImageView) view;
                    bitmapUtils.display(iv, data.toString());
                    return true;
                }
                return false;
            }
        });
        lv_city.setAdapter(simpleAdapter);

    }

    public void setStringlist(List<String[]> stringlist) {
        this.stringlist = stringlist;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (clickNumber) {
            case 0:
                province_name = provinceList.get(position)[1];
                rb_country.setText(provinceList.get(position)[1]);
                province_id = provinceList.get(position)[0];
                netRun.getSregionList(provinceList.get(position)[0]);
                clickNumber = 1;
                break;
            case 1:
                city_name = cityList.get(position)[1];
                rb_province.setText(cityList.get(position)[1]);
                city_id = cityList.get(position)[0];
                netRun.getSregionList(city_id);
                clickNumber = 2;
                break;
            case 2:
                district_name = districtList.get(position)[1];
                district_id = districtList.get(position)[0];
                netRun.getSregionList(district_id);
                rb_city.setText(districtList.get(position)[1]);
                clickNumber = 3;
                break;
            case 3:
                if (areaList.size() > 0) {
                    area_name = areaList.get(position)[1];
                    area_id = areaList.get(position)[0];
                    rb_district.setText(areaList.get(position)[1]);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rb_country) {
            initData();
            clickNumber = 0;
            city_name = null;
            district_name = null;
            rb_city.setText(getI18n(R.string.city));
            cityList = null;
            districtList = null;
            city_id = null;
            province_id = null;
            area_id = null;
        } else if (v.getId() == R.id.rb_province) {
            if (cityList != null) {
                district_name = null;
                clickNumber = 1;
                initView(cityList);
                rb_district.setText(getI18n(R.string.area));
                districtList = null;
                area_id = null;
            } else {
                CommonTools.showShortToast(this, getI18n(R.string.province_not_choice));
            }
        } else if (v.getId() == R.id.rb_city) {
            if (districtList != null) {
                clickNumber = 2;
                initView(districtList);
            } else {
                CommonTools.showShortToast(this, getI18n(R.string.city_not_choice));
            }

        } else if (v.getId() == R.id.btn_submit) {
            if (province_name == null) {
                CommonTools.showShortToast(this, getI18n(R.string.province_not_choice));
                return;
            }
            if (city_name == null) {
                CommonTools.showShortToast(this, getI18n(R.string.city_not_choice));
                return;
            }
            if (district_name == null) {
                CommonTools.showShortToast(this, getI18n(R.string.area_not_choice));
                return;
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("province_name", province_name);
            bundle.putString("province_id", province_id);
            bundle.putString("district_name", district_name);
            bundle.putString("district_id", district_id);
            bundle.putString("city_name", city_name);
            bundle.putString("city_id", city_id);
            bundle.putString("area_name", area_name);
            bundle.putString("area_id", area_id);
            intent.putExtras(bundle);
            this.setResult(Sregion_Result, intent);
            AddresSregionListActivity.this.finish();

        }

//        switch (v.getId()) {
//            case R.id.rb_country:
//                initData();
//                clickNumber = 0;
//                city_name = null;
//                district_name = null;
//                rb_city.setText(getI18n(R.string.city));
//                cityList = null;
//                districtList = null;
//                city_id = null;
//                province_id = null;
//                area_id = null;
//                break;
//            case R.id.rb_province:
//                if (cityList != null) {
//                    district_name = null;
//                    clickNumber = 1;
//                    initView(cityList);
//                    rb_district.setText(getI18n(R.string.area));
//                    districtList = null;
//                    area_id = null;
//                } else {
//                    CommonTools.showShortToast(this, getI18n(R.string.province_not_choice));
//                }
//                break;
//            case R.id.rb_city:
//                if (districtList != null) {
//                    clickNumber = 2;
//                    initView(districtList);
//                } else {
//                    CommonTools.showShortToast(this, getI18n(R.string.city_not_choice));
//                }
//                break;
//            case R.id.rb_district:
//
//                break;
//            case R.id.btn_submit:
//                if (province_name == null) {
//                    CommonTools.showShortToast(this, getI18n(R.string.province_not_choice));
//                    return;
//                }
//                if (city_name == null) {
//                    CommonTools.showShortToast(this, getI18n(R.string.city_not_choice));
//                    return;
//                }
//                if (district_name == null) {
//                    CommonTools.showShortToast(this, getI18n(R.string.area_not_choice));
//                    return;
//                }
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("province_name", province_name);
//                bundle.putString("province_id", province_id);
//                bundle.putString("district_name", district_name);
//                bundle.putString("district_id", district_id);
//                bundle.putString("city_name", city_name);
//                bundle.putString("city_id", city_id);
//                bundle.putString("area_name", area_name);
//                bundle.putString("area_id", area_id);
//                intent.putExtras(bundle);
//                this.setResult(Sregion_Result, intent);
//                AddresSregionListActivity.this.finish();
//
//                break;
//        }
    }

    @Override
    public void ReGetData() {
    }

}
