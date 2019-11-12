package com.aite.aitezhongbao.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.login.LoginActivity;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.view.StatusBarUtils;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class UserTypeActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.next_btn)
    Button next_btn;

    @Override
    protected int getLayoutResId() {
        return R.layout.user_type_layout;
    }

    @OnClick({R.id.iv_back, R.id.next_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.next_btn:
                AppManager.getInstance().killAllActivity();
                startActivity(LoginActivity.class);
                break;
//                AppManager.getInstance().killActivity(NewUserActivity.class);
        }
    }

    @Override
    protected void initView() {
        StatusBarUtils.setColor(context, Color.WHITE);

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
