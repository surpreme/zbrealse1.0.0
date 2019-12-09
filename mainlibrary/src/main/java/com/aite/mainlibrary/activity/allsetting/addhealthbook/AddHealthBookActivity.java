package com.aite.mainlibrary.activity.allsetting.addhealthbook;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.util.FileUtils;
import com.lzy.okgo.model.HttpParams;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddHealthBookActivity extends BaseActivity<AddHealthBookContract.View, AddHealthBookPresenter> implements AddHealthBookContract.View {

    @BindView(R2.id.add_image_tv)
    TextView addImageTv;
    @BindView(R2.id.post_img)
    ImageView postImg;
    @BindView(R2.id.sick_name_edit)
    EditText sickNameEdit;
    @BindView(R2.id.sick_time_edit)
    EditText sickTimeEdit;
    @BindView(R2.id.sick_information_edit)
    EditText sickInformationEdit;
    private List<Uri> mSelected = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.actviity_add_health;
    }

    @Override
    protected void initView() {
        initToolbar("添加", "提交", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStringEmpty(getEditString(sickNameEdit)) || isStringEmpty(getEditString(sickTimeEdit)) || isStringEmpty(getEditString(sickInformationEdit))) {
                    showToast("请检查信息", Gravity.TOP);
                    return;
                }
                mPresenter.PostInformation(initParams());

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
//            ImageUtils.getmInstance().photoClip(this, mSelected.get(0));
            if (mSelected.isEmpty()) return;
            Glide.with(context).load(mSelected.get(0)).into(postImg);
        }

    }

    @OnClick({R2.id.add_image_tv})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_image_tv) {
            openImg(this, 3, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE);


        }
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("type", getIntent().getStringExtra("type"));
        httpParams.put("name", getEditString(sickNameEdit));
        httpParams.put("time", getEditString(sickTimeEdit));
        httpParams.put("description", getEditString(sickInformationEdit));
        if (mSelected != null && !mSelected.isEmpty()) {
            for (Uri uri : mSelected) {
                File file = FileUtils.getFileByUri(context, uri);
                if (file != null && file.exists()) {
                    httpParams.put("images", file);
                }
            }
        }
        return httpParams;
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
    public void onPostInformationSuccess(Object msg) {
        showToast((String) msg, Gravity.TOP);
        onBackPressed();

    }


}
