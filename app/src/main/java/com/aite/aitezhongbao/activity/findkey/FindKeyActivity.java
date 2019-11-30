package com.aite.aitezhongbao.activity.findkey;


import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.login.LoginActivity;
import com.aite.aitezhongbao.bean.SureFindPasswordCodeBean;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FindKeyActivity extends BaseActivity<FindKeyContract.View, FindKeyPresenter> implements FindKeyContract.View {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.sendcode_tv)
    TextView sendcodeTv;
    @BindView(R.id.number_get_edit)
    TextInputEditText number_get_edit;
    @BindView(R.id.over_btn)
    Button overBtn;
    @BindView(R.id.keycode_get_edit)
    TextInputEditText keycodeGetEdit;
    @BindView(R.id.first_password_edit)
    TextInputEditText firstPasswordEdit;
    @BindView(R.id.sure_password_edit)
    TextInputEditText surePasswordEdit;
    @BindView(R.id.title_tv1)
    TextView titleTv1;
    @BindView(R.id.title_tv2)
    TextView titleTv2;
    @BindView(R.id.widget_textinput_layout)
    TextInputLayout widgetTextinputLayout;
    @BindView(R.id.first_ll)
    LinearLayout firstLl;
    @BindView(R.id.second_ll)
    LinearLayout secondLl;
    @BindView(R.id.next_ll)
    LinearLayout nextLl;
    @BindView(R.id.over_ll)
    LinearLayout overLl;
    private int recLen = 10;

    @Override
    protected int getLayoutResId() {
        return R.layout.find_key_layout;
    }

    @OnClick({R.id.iv_back, R.id.next_btn, R.id.sendcode_tv, R.id.over_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                killThisActvity();
                break;
            case R.id.sendcode_tv:
                if (isEditTextEmpty(number_get_edit)) {
                    showTopToasts("请输入手机号");
                    return;
                }
                initTimeOversize();
                mPresenter.sendPHONECODEfindkey(getEditString(number_get_edit));
                break;
            case R.id.next_btn:
                mPresenter.surefindkey(initParams());
                break;
            case R.id.over_btn:
//                startActivity(LoginActivity.class);
//                killThisActvity();
                mPresenter.sureALLfindkey(initALLMSGParams());
                break;
        }
    }

    @Override
    protected void initView() {
        titleTv1.setTextColor(getResources().getColor(R.color.blue));
        titleTv2.setTextColor(getResources().getColor(R.color.lightglay));
        secondLl.setVisibility(View.GONE);
        overLl.setVisibility(View.GONE);


    }

    private void initTimeOversize() {
        sendcodeTv.setEnabled(false);
        sendcodeTv.setTextColor(getResources().getColor(R.color.lightglay));
        final Timer timer = new Timer();
        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    if (!(recLen <= 0))
                        sendcodeTv.setText(recLen + " S");
                    if (recLen <= 0) {
                        timer.cancel();
                        recLen = 10;
                        sendcodeTv.setText("发送验证码");
                        sendcodeTv.setEnabled(true);
                        sendcodeTv.setTextColor(getResources().getColor(R.color.blue));

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

    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        params.put("mobile", getEditString(number_get_edit));
        params.put("mobile_code", getEditString(keycodeGetEdit));
        return params;
    }

    private HttpParams initALLMSGParams() {
        HttpParams params = new HttpParams();
        params.put("mobile", getEditString(number_get_edit));
        params.put("password", getEditString(firstPasswordEdit));
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
                showTopToasts(((SureFindPasswordCodeBean) msg).getMsg());
                startActivity(LoginActivity.class);
                finish();
            }
        });

    }

    @Override
    public void setPostCodeSuccess(Object msg) {
        showTopToasts(((SureFindPasswordCodeBean) msg).getMsg());
        LogUtils.d(msg);
    }

    @Override
    public void setSureCodeSuccess(Object msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleTv1.setTextColor(getResources().getColor(R.color.lightglay));
                titleTv2.setTextColor(getResources().getColor(R.color.blue));
                firstLl.setVisibility(View.GONE);
                secondLl.setVisibility(View.VISIBLE);
                nextLl.setVisibility(View.GONE);
                overLl.setVisibility(View.VISIBLE);
            }
        });

    }
}
