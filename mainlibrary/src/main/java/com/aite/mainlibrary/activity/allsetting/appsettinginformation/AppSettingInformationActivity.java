package com.aite.mainlibrary.activity.allsetting.appsettinginformation;


import android.os.Bundle;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.util.VersionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AppSettingInformationActivity extends BaseActivity<AppsettinginformationContract.View, AppsettinginformationPresenter> implements AppsettinginformationContract.View {

    @BindView(R2.id.app_version_tv)
    TextView appVersionTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.appsetting_information;
    }

    @Override
    protected void initView() {
        initToolbar("设置");
        appVersionTv.setText(String.format("当前版本号:%s", VersionUtils.getAppVersionName(context)));
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
