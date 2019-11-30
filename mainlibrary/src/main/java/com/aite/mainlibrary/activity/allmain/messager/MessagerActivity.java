package com.aite.mainlibrary.activity.allmain.messager;


import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.aite.mainlibrary.Constant.MainUIConstant;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.GridViewIconAdapter;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessagerActivity extends BaseActivity<MessagerContract.View, MessagerPresenter> implements MessagerContract.View {
    @BindView(R2.id.setting_gridview)
    GridView settingGridview;
    @BindView(R2.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutResId() {
        return R.layout.message_layout;
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(this);
        settingGridview.setAdapter(new GridViewIconAdapter(context, MainUIConstant.MessageConstant.settingImg, MainUIConstant.MessageConstant.settingTv));
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
