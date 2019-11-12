package com.aite.aitezhongbao.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.login.LoginActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FindKeyActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.next_btn)
    Button nextBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.find_key_layout;
    }

    @OnClick({R.id.iv_back, R.id.next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                killThisActvity();
                break;
            case R.id.next_btn:
                startActivity(LoginActivity.class);
                killThisActvity();
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

}
