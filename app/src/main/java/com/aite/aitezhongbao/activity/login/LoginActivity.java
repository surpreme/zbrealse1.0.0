package com.aite.aitezhongbao.activity.login;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aite.aitezhongbao.MainActivity;
import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.BaseWelcomeActivity;
import com.aite.aitezhongbao.activity.FindKeyActivity;
import com.aite.aitezhongbao.activity.NewUserActivity;
import com.lzy.basemodule.LoadingPopwindow;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends BaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.new_user_tv)
    TextView new_user_tv;
    @BindView(R.id.find_key_tv)
    TextView find_key_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.login_layout;
    }

    @OnClick({R.id.login_btn, R.id.new_user_tv, R.id.find_key_tv})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                mPresenter.login("lzy", "123456");
                LoadingPopwindow.getmInstance().showloaddingPopupWindow(context);

//                mPresenter.attachView();
//                startActivity(MainActivity.class);
//                finish();
                break;
            case R.id.new_user_tv:
                startActivity(NewUserActivity.class);
                break;
            case R.id.find_key_tv:
                startActivity(FindKeyActivity.class);
                break;

        }
    }

    @Override
    protected void initView() {

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

    @Override
    public void logInSuccess(Object msg) {
        if (msg.equals("ok")) {
            LoadingPopwindow.getmInstance().dismissPopWindow();
            startActivity(MainActivity.class);
            finish();

        }


    }

    @Override
    public void logInFail(String error) {

    }
}
