package com.aite.mainlibrary.activity.allmoney;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactMoneyActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.number_get_edit)
    TextInputEditText numberGetEdit;

    @Override
    protected int getLayoutResId() {
        return R.layout.getfact_moneylayout;
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
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
