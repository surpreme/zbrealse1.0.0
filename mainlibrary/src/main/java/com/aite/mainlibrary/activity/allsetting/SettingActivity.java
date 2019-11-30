package com.aite.mainlibrary.activity.allsetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.BindingUserActivity;
import com.aite.mainlibrary.activity.allsetting.PaySettingActivity;
import com.aite.mainlibrary.activity.allsetting.SosUserActivity;
import com.aite.mainlibrary.activity.allsetting.UserInformationActivity;
import com.aite.mainlibrary.activity.allsetting.UserSafetyActivity;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.user_information_ll)
    LinearLayout userInformationLl;
    @BindView(R2.id.exit_login_btn)
    Button exitLoginBtn;
    @BindView(R2.id.sos_user_ll)
    LinearLayout sosUserLl;
    @BindView(R2.id.pay_setting_ll)
    LinearLayout paySettingLl;
    @BindView(R2.id.badding_nuber_ll)
    LinearLayout baddingNuberLl;
    @BindView(R2.id.userSafety_ll)
    LinearLayout userSafetyLl;

    @Override
    protected int getLayoutResId() {
        return R.layout.setting_layout;
    }

    @Override
    protected void initView() {
        tvTitle.setText("账户设置");
        ivBack.setOnClickListener(this);
        userInformationLl.setOnClickListener(this);
        exitLoginBtn.setOnClickListener(this);
        sosUserLl.setOnClickListener(this);
        paySettingLl.setOnClickListener(this);
        baddingNuberLl.setOnClickListener(this);
        userSafetyLl.setOnClickListener(this);

    }

    /**
     * 子module跳转到父module
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.user_information_ll) startActivity(UserInformationActivity.class);
        if (v.getId() == R.id.sos_user_ll) startActivity(SosUserActivity.class);
        if (v.getId() == R.id.pay_setting_ll) startActivity(PaySettingActivity.class);
        if (v.getId() == R.id.badding_nuber_ll) startActivity(BindingUserActivity.class);
        if (v.getId() == R.id.userSafety_ll) startActivity(UserSafetyActivity.class);

        if (v.getId() == R.id.exit_login_btn) {
            Intent intent = new Intent();
            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
            AppManager.getInstance().killAllActivity();
            startActivity(intent);
        }
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
