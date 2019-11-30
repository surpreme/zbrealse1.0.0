package com.aite.mainlibrary.activity.allshopcard.seviceshopbook;


import com.aite.mainlibrary.R;
import com.lzy.basemodule.base.BaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SeviceShopBookActivity extends BaseActivity<SeviceShopBookContract.View, SeviceShopBookPresenter> implements SeviceShopBookContract.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.service_shopbook_layout;
    }

    @Override
    protected void initView() {
        initToolbar("服务反馈");

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
