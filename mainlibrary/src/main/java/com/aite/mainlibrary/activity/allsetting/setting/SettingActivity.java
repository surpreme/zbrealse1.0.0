package com.aite.mainlibrary.activity.allsetting.setting;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.PaySettingActivity;
import com.aite.mainlibrary.activity.allsetting.adressfix.AdressFixActivity;
import com.aite.mainlibrary.activity.allsetting.appsettinginformation.AppSettingInformationActivity;
import com.aite.mainlibrary.activity.allsetting.bindinguser.BindingUserActivity;
import com.aite.mainlibrary.activity.allsetting.elderhelphouse.ElderHelpHouseActivity;
import com.aite.mainlibrary.activity.allsetting.sosuser.SosUserActivity;
import com.aite.mainlibrary.activity.allsetting.userinformation.UserInformationActivity;
import com.aite.mainlibrary.activity.allsetting.userinformation.usersafety.UserSafetyActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SettingActivity extends BaseActivity<SettingContract.View, SettingPresenter> implements SettingContract.View {
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
    @BindView(R2.id.address_ll)
    LinearLayout addressLl;
    @BindView(R2.id.app_setting_ll)
    LinearLayout appSettingLl;
    @BindView(R2.id.icon_iv)
    ImageView iconIv;
    @BindView(R2.id.user_name_tv)
    TextView userNameTv;
    @BindView(R2.id.elder_helpHouse_ll)
    LinearLayout elderHelpHouseLl;

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
        addressLl.setOnClickListener(this);
        appSettingLl.setOnClickListener(this);
        elderHelpHouseLl.setOnClickListener(this);
    }

    /**
     * 子module跳转到父module
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        //AdressFixActivity
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.user_information_ll) startActivity(UserInformationActivity.class);
        if (v.getId() == R.id.sos_user_ll) startActivity(SosUserActivity.class);
        if (v.getId() == R.id.pay_setting_ll) startActivity(PaySettingActivity.class);
        if (v.getId() == R.id.badding_nuber_ll) startActivity(BindingUserActivity.class);
        if (v.getId() == R.id.userSafety_ll) startActivity(UserSafetyActivity.class);
        if (v.getId() == R.id.address_ll) startActivity(AdressFixActivity.class);
        if (v.getId() == R.id.app_setting_ll) startActivity(AppSettingInformationActivity.class);
        if (v.getId() == R.id.elder_helpHouse_ll) startActivity(ElderHelpHouseActivity.class);


        if (v.getId() == R.id.exit_login_btn) {
            Intent intent = new Intent();
            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
            AppManager.getInstance().killAllActivity();
            startActivity(intent);
        }
    }

    @Override
    protected void initDatas() {
        if (AppConstant.ICON_URL != null)
            Glide.with(context).load(AppConstant.ICON_URL).apply(RequestOptions.circleCropTransform()).into(iconIv);
        if (AppConstant.USERNAME != null)
            userNameTv.setText(AppConstant.USERNAME);

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }


    @Override
    public void onGetInformationSuccess(Object msg) {

    }
}