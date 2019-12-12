package com.aite.aitezhongbao.activity.newuser;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.newusermsg.NewusermsgActivity;
import com.aite.aitezhongbao.bean.FirstNewUserBean;
import com.aite.aitezhongbao.bean.SureFindPasswordCodeBean;
import com.aite.mainlibrary.Mainbean.AllAreaBean;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.PopwindowUtils;
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

public class NewUserActivity extends BaseActivity<NewUserContract.View, NewUserPresenter> implements NewUserContract.View {

    @BindView(R.id.next_btn)
    Button next_btn;
    @BindView(R.id.sendcode_tv)
    TextView sendcodeTv;
    @BindView(R.id.number_get_edit)
    TextInputEditText numberGetEdit;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.code_get_edit)
    TextInputEditText codeGetEdit;
    @BindView(R.id.password_edit)
    TextInputEditText passwordEdit;
    @BindView(R.id.sure_password_edit)
    TextInputEditText surePasswordEdit;
    private int recLen = 10;//验证码倒计时

    @Override
    protected int getLayoutResId() {
        return R.layout.new_user_layout;
    }

    @Override
    protected void initView() {
        initToolbar("注册");
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PopwindowUtils.getmInstance().showNewUerTogtherInformaionPopupWindow(NewUserActivity.this);
                }
            }
        });


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


    @OnClick({R.id.sendcode_tv, R.id.next_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendcode_tv:
                if (isEditTextEmpty(numberGetEdit)) {
                    showTopToasts("请输入手机号");
                    return;
                }
                mPresenter.sendphonecode(numberGetEdit.getText().toString().trim());
                initTimeOversize();
                break;
            case R.id.next_btn:
                if (!checkBox.isChecked()) {
                    showToast("请同意用户隐私政策", Gravity.TOP);
                    return;
                }
                mPresenter.postAllmsg(initParams());
                break;
//                AppManager.getInstance().killActivity(NewUserActivity.class);
        }

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
        params.put("regtype", AppConstant.REGTYPE);
        params.put("client", AppConstant.CLIENT);
        params.put("vercode", getEditString(codeGetEdit));
        params.put("username", getEditString(numberGetEdit));
        params.put("password", getEditString(passwordEdit));
        params.put("password_confirm", getEditString(surePasswordEdit));
//        params.put("email", getEditString(surePasswordEdit));
        return params;
    }


    @Override
    public void sendphonecodeonSuccess(Object msg) {
        showTopToasts(((SureFindPasswordCodeBean) msg).getMsg());
        LogUtils.d(msg);
    }

    @Override
    public void postAllmsgonSuccess(Object msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(((FirstNewUserBean) msg).getKey() + "--------" + ((FirstNewUserBean) msg).getUsername());
                Bundle bundle = new Bundle();
                bundle.putString("key", ((FirstNewUserBean) msg).getKey());
                bundle.putString("username", ((FirstNewUserBean) msg).getUsername());
                startActivity(NewusermsgActivity.class, bundle);
            }
        });

    }

}
