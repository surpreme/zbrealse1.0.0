package com.aite.mainlibrary.activity.allsetting.adressfix;


import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.SettingAddressListBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.addadrress.AddAdrressActivity;
import com.aite.mainlibrary.activity.allshopcard.sureshopbook.SureShopBookActivity;
import com.aite.mainlibrary.adapter.AdrressFixRecyAdapter;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.bean.ContentValue;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AdressFixActivity extends BaseActivity<AdressFixContract.View, AdressFixPresenter> implements AdressFixContract.View {

    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    private AdrressFixRecyAdapter adrressFixRecyAdapter;
    private List<SettingAddressListBean.AddressListBean> addressListBeans = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.sos_user;
    }

    @Override
    protected void initView() {
        initToolbar("我的地址");
        initBottomBtn("添加新地址", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddAdrressActivity.class);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adrressFixRecyAdapter = new AdrressFixRecyAdapter(context, addressListBeans));
        adrressFixRecyAdapter.setOnItemRecyClickInterface(new OnClickLstenerInterface.OnThingClickInterface() {
            @Override
            public void getString(String msg) {
                Intent intent = getIntent();
                intent.putExtra("address_id", msg);
                // 设置返回码和返回携带的数据
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    protected void initDatas() {
        mPresenter.getAdressList(initParams());

    }

    @Override
    protected void initResume() {

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onGetAdressListSuccess(Object msg) {
        addressListBeans.addAll(((SettingAddressListBean) msg).getAddress_list());
        adrressFixRecyAdapter.notifyDataSetChanged();
    }
}