package com.aite.mainlibrary.activity.allsetting;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allsetting.addbindinguser.AddBindingUserActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;

public class BindingUserActivity extends BaseActivity {
    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.bank_recy)
    RecyclerView bankRecy;
    @BindView(R2.id.bottom_btn)
    Button bottomBtn;

    @Override
    protected int getLayoutResId() {
        return R.layout.binding_user;
    }

    @Override
    protected void initView() {
        tvTitle.setText("绑定账号");
        ivBack.setOnClickListener(this);
        bottomBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) onBackPressed();
        if (v.getId() == R.id.bottom_btn) startActivity(AddBindingUserActivity.class);

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
