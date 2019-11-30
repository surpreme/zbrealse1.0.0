package com.aite.mainlibrary.activity.allsetting;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.view.PayPwdEditText;

import butterknife.BindView;

public class PaySettingActivity extends BaseActivity {
    @BindView(R2.id.sure_password_edit)
    PayPwdEditText surePasswordEdit;
    @BindView(R2.id.get_password_edit)
    PayPwdEditText get_password_edit;

    @Override
    protected int getLayoutResId() {
        return R.layout.pay_setting;
    }

    @Override
    protected void initView() {
//        surePasswordEdit.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        initToolbar("支付设置");
        initBottomBtn("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        get_password_edit.initStyle(R.drawable.juxing_gray_background, 6, 0.33f, R.color.glaytxt, R.color.glaytxt, 30);
        surePasswordEdit.initStyle(R.drawable.juxing_gray_background, 6, 0.33f, R.color.glaytxt, R.color.glaytxt, 30);
        surePasswordEdit.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
        });
        get_password_edit.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

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
