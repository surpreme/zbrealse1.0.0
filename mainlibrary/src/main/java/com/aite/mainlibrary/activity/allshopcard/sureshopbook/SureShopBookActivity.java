package com.aite.mainlibrary.activity.allshopcard.sureshopbook;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.seviceshopbook.SeviceShopBookActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SureShopBookActivity extends BaseActivity<SureShopBookContract.View, SureShopBookPresenter> implements SureShopBookContract.View {

    @BindView(R2.id.sure_buy_btn)
    Button sureBuyBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.sureshop;
    }

    @Override
    protected void initView() {
        initToolbar("确认订单");

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }


    @Override
    protected void initReStart() {

    }

    @OnClick({ R2.id.sure_buy_btn})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure_buy_btn) {
            startActivity(SeviceShopBookActivity.class);
        }
    }

}
