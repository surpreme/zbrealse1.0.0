package com.aite.mainlibrary.activity.allsetting.userinformation.usersafety;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserSafetyActivity extends BaseActivity<UserSafetyContract.View, UserSafetyPresenter> implements UserSafetyContract.View {
    @BindView(R2.id.widget_textinput_layout)
    TextInputLayout widgetTextinputLayout;
    @BindView(R2.id.timer_tv)
    TextView timerTv;
    @BindView(R2.id.phone_code_edit)
    TextInputEditText phoneCodeEdit;
    @BindView(R2.id.get_password_edit)
    TextInputEditText getPasswordEdit;
    @BindView(R2.id.sure_password_edit)
    TextInputEditText surePasswordEdit;
    @BindView(R2.id.sure_btn)
    Button sureBtn;
    @BindView(R2.id.phone_edit)
    TextInputEditText phoneEdit;
    private int recLen = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.user_safety;
    }

    @Override
    protected void initView() {
        initToolbar("账户安全");
    }

    @OnClick({R2.id.timer_tv,R2.id.sure_btn})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.timer_tv) {
            if (isEditTextEmpty(phoneEdit)) {
                showTopToasts("请输入手机号");
                return;
            }
            initTimeOversize();
            mPresenter.sendPHONECODEfindkey(getEditString(phoneEdit));
        } else if (v.getId() == R.id.sure_btn) {
            mPresenter.sureALLfindkey(initALLMSGParams());

        }

    }

    private void initTimeOversize() {
        timerTv.setEnabled(false);
        timerTv.setTextColor(getResources().getColor(R.color.lightglay));
        final Timer timer = new Timer();
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    if (!(recLen <= 0))
                        timerTv.setText(recLen + " S");
                    if (recLen <= 0) {
                        timer.cancel();
                        recLen = 10;
                        timerTv.setText("发送验证码");
                        timerTv.setEnabled(true);
                        timerTv.setTextColor(getResources().getColor(R.color.blue));

                    }
                }
            }
        };
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                recLen--;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(task, 3000, 1000);
    }



    private HttpParams initALLMSGParams() {
        HttpParams params = new HttpParams();
        params.put("mobile", getEditString(phoneEdit));
        params.put("password", getEditString(getPasswordEdit));
        params.put("password_confirm", getEditString(surePasswordEdit));
        return params;
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
    public void setNewPasswordonSuccess(Object msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showTopToasts(((TwoSuccessCodeBean) msg).getMsg());
                Intent intent = new Intent();
                intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
                AppManager.getInstance().killAllActivity();
                startActivity(intent);
            }
        });
    }

    @Override
    public void setPostCodeSuccess(Object msg) {
        showTopToasts(((TwoSuccessCodeBean) msg).getMsg());
        LogUtils.d(msg);
    }

    @Override
    public void setSureCodeSuccess(Object msg) {

    }

}
