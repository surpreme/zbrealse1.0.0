package com.aite.mainlibrary.activity.allmoney;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBankcarActvity extends BaseActivity {


    @Override
    protected int getLayoutResId() {
        return R.layout.addbank_carlayout;
    }

    @Override
    protected void initView() {
        initToolbar("添加银行卡");
    }

    @Override
    public void onClick(View v) {

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

}
