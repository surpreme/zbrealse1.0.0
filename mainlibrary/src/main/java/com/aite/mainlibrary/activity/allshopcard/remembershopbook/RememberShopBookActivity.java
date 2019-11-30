package com.aite.mainlibrary.activity.allshopcard.remembershopbook;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.sureshopbook.SureShopBookActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RememberShopBookActivity extends BaseActivity<RememberShopBookContract.View, RememberShopBookPresenter> implements RememberShopBookContract.View {

    @BindView(R2.id.bottom_btn)
    Button bottomBtn;


    @Override
    protected int getLayoutResId() {
        return R.layout.remembershop;
    }

    @Override
    protected void initView() {
        initToolbar("预约订餐");


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

    @OnClick({R2.id.bottom_btn})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bottom_btn) {
            startActivity(SureShopBookActivity.class);
        }
    }


    //    @OnClick({ R2.id.bottom_btn})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.bottom_btn:
//                break;
////            case R.id.bottom_btn:
////                break;
//        }
//    }
}
