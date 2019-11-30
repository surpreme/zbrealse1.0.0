package com.aite.mainlibrary.activity.allchat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutResId() {
        return R.layout.talker_im;
    }

    @Override
    protected void initView() {
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
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
    public void showError(String msg) {

    }
}
