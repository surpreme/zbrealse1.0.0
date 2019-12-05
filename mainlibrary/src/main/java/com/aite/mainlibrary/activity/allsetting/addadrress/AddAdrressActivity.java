package com.aite.mainlibrary.activity.allsetting.addadrress;


import android.view.View;

import com.aite.mainlibrary.R;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddAdrressActivity extends BaseActivity<AddAdrressContract.View, AddAdrressPresenter> implements AddAdrressContract.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.add_address;
    }

    @Override
    protected void initView() {
        initToolbar("添加地址", "保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
