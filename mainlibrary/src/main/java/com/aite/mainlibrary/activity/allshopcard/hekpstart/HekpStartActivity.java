package com.aite.mainlibrary.activity.allshopcard.hekpstart;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HekpStartActivity extends BaseActivity<HekpStartContract.View, HekpStartPresenter> implements HekpStartContract.View {

    @BindView(R2.id.help_start_btn)
    Button helpStartBtn;
    @BindView(R2.id.start_service_iv)
    ImageView startServiceIv;
    @BindView(R2.id.result_iv)
    ImageView resultIv;
    @BindView(R2.id.father_layout)
    LinearLayout fatherLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.help_start;
    }

    @Override
    protected void initView() {
        initToolbar("服务开始");

    }

    @OnClick({R2.id.help_start_btn, R2.id.start_service_iv})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.help_start_btn || v.getId() == R.id.start_service_iv) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
            startActivityForResult(intent, BaseConstant.RESULT_CODE.REQUEST_CAMERA);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CAMERA) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                fatherLayout.setVisibility(View.GONE);
                resultIv.setVisibility(View.VISIBLE);
                Glide.with(context).load(bitmap).into(resultIv);
                LogUtils.d(bundle);
            } else {


            }
        }
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
