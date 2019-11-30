package com.aite.mainlibrary.activity.allmain;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.DeviceNumberEditActivity;
import com.aite.mainlibrary.activity.allqr.QrCodeActivity;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddDeviceMainActvity extends BaseActivity {
    @BindView(R2.id.scode_img)
    ImageView scodeImg;
    @BindView(R2.id.edit_device_number)
    TextView editDeviceNumber;

    @Override
    protected int getLayoutResId() {
        return R.layout.add_device_main;
    }

    @Override
    protected void initView() {
        initToolbar("添加设备");
        scodeImg.setOnClickListener(this);
        editDeviceNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scode_img) {
            startActivity(QrCodeActivity.class);
        } else if (v.getId() == R.id.edit_device_number)
            startActivity(DeviceNumberEditActivity.class);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showError(String msg) {

    }
}
