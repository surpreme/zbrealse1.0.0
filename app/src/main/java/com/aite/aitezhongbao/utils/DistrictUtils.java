package com.aite.aitezhongbao.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.View.AddressSelector;
import com.aite.aitezhongbao.View.CityInterface;
import com.aite.aitezhongbao.View.OnItemClickListener;
import com.aite.aitezhongbao.bean.City;
import com.aite.mainlibrary.Mainbean.AllAreaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间 2019/12/9 19:32
 * 描述:
 */
public class DistrictUtils {


    private ArrayList<City> mProvinceList = new ArrayList<>();
    private ArrayList<City> mCityList = new ArrayList<>();
    private ArrayList<City> mDistrictList = new ArrayList<>();


    //集合省的位置
    private int mProvinceId = 0;

    //集合市的位置
    private int mCityId = 0;

    //集合区域的位置
    private int mDistrictId = 0;

    private Dialog mShowDialogCity;

    private Context mContext;

    private TextView mTextView;

    private List<AllAreaBean.ListBean> mDataList;

    public DistrictUtils(Context context, List<AllAreaBean.ListBean> dataList, TextView textView) {
        mContext = context;
        mDataList = dataList;
        mTextView = textView;
    }

    /**
     * 省市区
     */
    public void showDialogCity() {
        mShowDialogCity = new Dialog(mContext, R.style.ShareDialogStyle);
        View inflate = View.inflate(mContext, R.layout.city_dialog, null);
        AddressSelector addressSelector = (AddressSelector) inflate.findViewById(R.id.address);
        //设置属性
        addressSelector.setTabAmount(3);
        addressSelector.setCities(getProvinceList());
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface cityInterface, int tabPosition) {
                if (tabPosition == 0) {
                    //将信息清空
                    mTextView.setText("");
                    String provinceName = cityInterface.getCityName();
                    for (int i = 0; i < mProvinceList.size(); i++) {
                        if (provinceName.equals(mProvinceList.get(i).getName())) {
                            mProvinceId = i;
                            //选中的城市
                            List<AllAreaBean.ListBean.CitylistBean> citylist = mDataList.get(mProvinceId).getCitylist();
                            ArrayList<City> cities = setCities(citylist);
                            addressSelector.setCities(cities);
                            break;
                        }
                    }
                    mTextView.setText(mProvinceList.get(mProvinceId).getName());
                } else if (tabPosition == 1) {
                    String cityName = cityInterface.getCityName();
                    for (int i = 0; i < mCityList.size(); i++) {
                        if (cityName.equals(mCityList.get(i).getName())) {
                            mCityId = i;
                            List<AllAreaBean.ListBean.CitylistBean.ArealistBean> arealist = mDataList.get(mProvinceId).getCitylist().get(mCityId).getArealist();
                            ArrayList<City> cities = setDistrict(arealist);
                            addressSelector.setCities(cities);
                            break;
                        }
                    }
                    mTextView.setText(mProvinceList.get(mProvinceId).getName() + "-" + mCityList.get(mCityId).getName());
                } else if (tabPosition == 2 && mDistrictList.size() > 0) {
                    String cityName = cityInterface.getCityName();
                    for (int i = 0; i < mDistrictList.size(); i++) {
                        if (cityName.equals(mDistrictList.get(i).getName())) {
                            mDistrictId = i;
                            break;
                        }
                    }
                    mTextView.setText(mProvinceList.get(mProvinceId).getName() + "-" + mCityList.get(mCityId).getName() + "-" + mDistrictList.get(mDistrictId).getName());
                    mShowDialogCity.dismiss();
                } else {
                    mDistrictId = 0;
                    mTextView.setText(mProvinceList.get(mProvinceId).getName() + "-" + mCityList.get(mCityId).getName());
                    mShowDialogCity.dismiss();
                }
            }
        });

        //点击头部
        addressSelector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
            @Override
            public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
                switch (tab.getIndex()) {
                    case 0:
                        if (mProvinceList.size() > 0) {
                            addressSelector.setCities(mProvinceList);
                        }
                        break;
                    case 1:
                        if (mCityList.size() > 0) {
                            addressSelector.setCities(mCityList);
                        }
                        break;
                    case 2:
                        if (mDistrictList.size() > 0) {
                            addressSelector.setCities(mDistrictList);
                        }
                        break;
                }
            }

            @Override
            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

            }
        });
        //将布局设置给Dialog
        mShowDialogCity.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mShowDialogCity.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (WindowManager.LayoutParams.MATCH_PARENT);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp); //将属性设置给窗体
        mShowDialogCity.show();//显示对话框
    }


    /*
       获取省
     */
    private ArrayList getProvinceList() {
        mProvinceList.clear();
        if (mDataList.size() != 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                City mCity = new City();
                mCity.setName(mDataList.get(i).getArea_name());
                mCity.setId(mDataList.get(i).getArea_id());
                mProvinceList.add(mCity);
            }
        }
        return mProvinceList;
    }

    /**
     * 获取市
     */
    private ArrayList<City> setCities(List<AllAreaBean.ListBean.CitylistBean> citylist) {
        mCityList.clear();
        if (citylist.size() != 0) {
            for (int i = 0; i < citylist.size(); i++) {
                City mCity = new City();
                mCity.setId(citylist.get(i).getArea_id());
                mCity.setName(citylist.get(i).getArea_name());
                mCityList.add(mCity);
            }
        }
        return mCityList;
    }


    /*
        获取区
     */
    private ArrayList<City> setDistrict(List<AllAreaBean.ListBean.CitylistBean.ArealistBean> arealist) {
        mDistrictList.clear();
        if (arealist.size() != 0) {
            for (int i = 0; i < arealist.size(); i++) {
                City mCity = new City();
                mCity.setId(arealist.get(i).getArea_id());
                mCity.setName(arealist.get(i).getArea_name());
                mDistrictList.add(mCity);
            }
        } else {
            mShowDialogCity.dismiss();
        }
        return mDistrictList;
    }

    /**
     * 获取省市区 ID
     */
    public String[] getSiteId() {
        String[] strings = new String[3];
        if (mProvinceList.size() != 0) {
            strings[0] = mProvinceList.get(mProvinceId).getId();
        }
        if (mCityList.size() != 0) {
            strings[1] = mCityList.get(mCityId).getId();
        }
        if (mDistrictList.size() != 0) {
            strings[2] = mDistrictList.get(mDistrictId).getId();
        }
        return strings;
    }


}
