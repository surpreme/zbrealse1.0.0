package com.aite.mainlibrary.activity.allsetting.addadrress;


import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.aite.mainlibrary.Mainbean.AllAreaBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddAdrressActivity extends BaseActivity<AddAdrressContract.View, AddAdrressPresenter> implements AddAdrressContract.View {

    @BindView(R2.id.name_edit)
    TextInputEditText nameEdit;
    @BindView(R2.id.phone_edit)
    TextInputEditText phoneEdit;
    @BindView(R2.id.all_lactionaddress_edit)
    TextInputEditText allLactionaddressEdit;
    @BindView(R2.id.switch_often_user)
    SwitchMaterial switchOftenUser;
    @BindView(R2.id.area_choice_ll)
    LinearLayout areaChoiceLl;
    @BindView(R2.id.area_tv)
    TextView areaTv;
    private String PROVINCE_ID = "";
    private String CITY_ID = "";
    private String AREA_ID = "";
    private String ALL_AREA_NAME = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.add_address;
    }

    @Override
    protected void initView() {
        initToolbar("添加地址", "保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.postMsg(initParams());


            }
        });
        initChoiceArea(new OnOptionsSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                areaTv.setTextColor(getColor(R.color.black));
                areaTv.setTextSize(16);
                PROVINCE_ID = options1Itemsnumber.get(options1);
                CITY_ID = options2Itemsnumber.get(options1).get(options2);
                AREA_ID = options3Itemsnumber.get(options1).get(options2).get(options3);
                ALL_AREA_NAME = String.format("%s %s %s",
                        options1Items.get(options1),
                        options2Items.get(options1).get(options2),
                        options3Items.get(options1).get(options2).get(options3));
                areaTv.setText(String.format("%s省--%s--%s",
                        options1Items.get(options1),
                        options2Items.get(options1).get(options2),
                        options3Items.get(options1).get(options2).get(options3)));
                LogUtils.e(
                        options1Itemsnumber.get(options1) + options1Items.get(options1)
                                + options2Itemsnumber.get(options1).get(options2) + options2Items.get(options1).get(options2)
                                + options3Itemsnumber.get(options1).get(options2).get(options3) + options3Items.get(options1).get(options2).get(options3));
            }
        }, new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {

            }
        });
        switchOftenUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        areaChoiceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!options1Items.isEmpty() || !options2Items.isEmpty() || !options3Items.isEmpty())
                    pvOptions.show();
                else showToast("数据错误");

            }
        });

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("address", getEditString(allLactionaddressEdit));
        httpParams.put("true_name", getEditString(nameEdit));
        httpParams.put("mob_phone", getEditString(phoneEdit));
        httpParams.put("tel_phone", 120);
        httpParams.put("city_id", CITY_ID);
        httpParams.put("area_id", AREA_ID);
        httpParams.put("area_info", ALL_AREA_NAME);
        return httpParams;
    }

    @Override
    protected void initDatas() {
        mPresenter.getAreachoice();

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onPostMsgSuccess(Object msg) {
        if (!isStringEmpty((String) msg.toString())) {
            showToast(msg.toString(), Gravity.TOP);
            onBackPressed();
        }

    }

    @Override
    public void onGetAreaChoiceSuccess(Object msg) {
        if (((AllAreaBean) msg) == null) return;
        for (int i = 0; i < ((AllAreaBean) msg).getList().size(); i++) {
            options1Items.add(((AllAreaBean) msg).getList().get(i).getArea_name());
            options1Itemsnumber.add(((AllAreaBean) msg).getList().get(i).getArea_id());
            city = new ArrayList<>();
            citynumber = new ArrayList<>();
            area = new ArrayList<>();
            areanumber = new ArrayList<>();
//            LogUtils.d(i + ((AllAreaBean) msg).getList().get(i).getArea_name());
            List<AllAreaBean.ListBean.CitylistBean> citylist = ((AllAreaBean) msg).getList().get(i).getCitylist();
            for (int o = 0; o < ((AllAreaBean) msg).getList().get(i).getCitylist().size(); o++) {
                city.add(citylist.get(o).getArea_name());
                citynumber.add(citylist.get(o).getArea_id());
                chirendenarea = new ArrayList<>();
                chirendenareanumber = new ArrayList<>();
//                LogUtils.d(i + ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArea_name());

                List<AllAreaBean.ListBean.CitylistBean.ArealistBean> arealist = ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArealist();
                for (int u = 0; u < ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArealist().size(); u++) {
                    chirendenarea.add(arealist.get(u).getArea_name());
                    chirendenareanumber.add(arealist.get(u).getArea_id());

//                    LogUtils.d(i + ((AllAreaBean) msg).getList().get(i).getCitylist().get(o).getArealist().get(u).getArea_name());
                }
                area.add(chirendenarea);
                areanumber.add(chirendenareanumber);

            }

            options2Items.add(city);
            options2Itemsnumber.add(citynumber);

            options3Items.add(area);
            options3Itemsnumber.add(areanumber);

        }
        if (!options1Items.isEmpty() && !options1Items.isEmpty() && !options3Items.isEmpty())
            pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
    }

}
