package com.aite.mainlibrary.activity.allmain.sendtimebank;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SendTimeBankActivity extends BaseActivity<SendTimeBankContract.View, SendTimeBankPresenter> implements SendTimeBankContract.View {

    @BindView(R2.id.user_number_edit)
    EditText userNumberEdit;
    @BindView(R2.id.time_long_edit)
    EditText timeLongEdit;
    @BindView(R2.id.sure_btn)
    Button sureBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.send_user_time;
    }

    @Override
    protected void initView() {
        initToolbar("积分转增");

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
