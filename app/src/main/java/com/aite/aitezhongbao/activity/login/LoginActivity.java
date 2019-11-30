package com.aite.aitezhongbao.activity.login;


import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.aitezhongbao.MainActivity;
import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.findkey.FindKeyActivity;
import com.aite.aitezhongbao.activity.newuser.NewUserActivity;
import com.aite.aitezhongbao.activity.newusermsg.NewusermsgActivity;
import com.aite.aitezhongbao.activity.usertype.UserTypeActivity;
import com.aite.aitezhongbao.bean.LogInBean;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.util.SystemUtil;
import com.lzy.okgo.model.HttpParams;

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
    @BindView(R.id.number_get_edit)
    TextInputEditText numberGetEdit;
    @BindView(R.id.key_get_edit)
    TextInputEditText keyGetEdit;
    @BindView(R.id.log_wechat)
    ImageView logWechat;

    @Override
    protected int getLayoutResId() {
        return R.layout.login_layout;
    }

    @OnClick({R.id.login_btn, R.id.new_user_tv, R.id.find_key_tv})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (isEditTextEmpty(numberGetEdit) || isEditTextEmpty(keyGetEdit)) {
                    showTopToasts("请检查账号密码");
                    return;
                }
                if (!SystemUtil.isNetworkConnected()) {
                    showToast("请检查网络", Gravity.TOP);
                    return;
                }
                mPresenter.login(initParams());
                showLoading();
                break;
            case R.id.new_user_tv:
                startActivity(NewUserActivity.class);
                break;
            case R.id.find_key_tv:
                startActivity(FindKeyActivity.class);
                break;

        }
    }

    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        params.put("username", getEditString(numberGetEdit));
        params.put("password", getEditString(keyGetEdit));
        params.put("client", AppConstant.CLIENT);
        return params;
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
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        AppConstant.KEY = ((LogInBean) msg).getDatas().getKey();
        AppConstant.USERNAME = ((LogInBean) msg).getDatas().getUsername();
        AppConstant.USERNAME = ((LogInBean) msg).getDatas().getUsername();
        AppConstant.FRIEND_VALID = ((LogInBean) msg).getDatas().getConfig().getFriend_valid();
        AppConstant.MEMBER_ID = ((LogInBean) msg).getDatas().getConfig().getMember_id();
        dimissLoading();
        startActivity(MainActivity.class);
        finish();
//            }
//        });


    }

    @Override
    public void showError(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dimissLoading();
            }
        });
        super.showError(msg);

    }

    @Override
    public void logInFail(String error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dimissLoading();
                showTopToasts(error);
            }
        });


    }

    @Override
    public void logInNeedMoreMsg(String number, String key) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dimissLoading();
                if (number.equals("2"))
                    startActivity(NewusermsgActivity.class, "key", key);
                if (number.equals("3"))
                    startActivity(UserTypeActivity.class, "key", key);
            }
        });


    }


}
