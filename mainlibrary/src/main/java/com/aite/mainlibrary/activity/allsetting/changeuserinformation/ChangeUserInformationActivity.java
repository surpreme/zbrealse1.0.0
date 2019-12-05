package com.aite.mainlibrary.activity.allsetting.changeuserinformation;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.OnClickLstenerInterface;
import com.lzy.basemodule.PopwindowUtils;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.FileUtils;
import com.lzy.basemodule.util.ImageUtils;
import com.lzy.basemodule.util.TimeUtils;
import com.lzy.okgo.model.HttpParams;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChangeUserInformationActivity extends BaseActivity<ChangeUserInformationContract.View, ChangeUserInformationPresenter> implements ChangeUserInformationContract.View {

    @BindView(R2.id.user_icon)
    ImageView userIcon;
    @BindView(R2.id.user_icon_ll)
    LinearLayout userIconLl;
    @BindView(R2.id.name_edit)
    EditText nameEdit;
    @BindView(R2.id.grender_ll)
    LinearLayout grenderLl;
    @BindView(R2.id.birthday_ll)
    LinearLayout birthdayLl;
    @BindView(R2.id.birthday_tv)
    TextView birthdayTv;
    @BindView(R2.id.bender_tv)
    TextView benderTv;
    private List<Uri> mSelected = new ArrayList<>();
    private String MEMBER_SEX = "";
    private String mDate = "";

    @Override
    protected int getLayoutResId() {
        return R.layout.change_user_setting_layout;
    }

    @Override
    protected void initView() {
        initToolbar("编辑个人信息", "完成", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.postInformation(initParams());
                onBackPressed();

            }
        });
        userIconLl.setOnClickListener(this);
        grenderLl.setOnClickListener(this);
        birthdayLl.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
//            File file = FileUtils.getFileByUri(context, mSelected.get(0));
            ImageUtils.getmInstance().photoClip(this, mSelected.get(0));

        } else if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_CLIP && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //在这里获得了剪裁后的Bitmap对象，可以用于上传
                Bitmap bitmap = bundle.getParcelable("data");
                ImageUtils.getmInstance().saveBitmap(context, bitmap, "userIcon");
                Glide.with(context).load(bitmap).transform(new CircleCrop()).into(userIcon);

            }


        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.user_icon_ll) {
            openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE);
        }
        if (v.getId() == R.id.grender_ll) {
            PopwindowUtils.getmInstance().showChioceGenderPopupWindow(context, Gravity.BOTTOM, 0.9f, new OnClickLstenerInterface.OnThingClickInterface() {
                @Override
                public void getString(String msg) {
                    LogUtils.d(msg);
                    benderTv.setText(msg.equals("superman") ? "男" : "女");
                    if (msg.equals("superman"))
                        MEMBER_SEX = "1";
                    else MEMBER_SEX = "2";
                }
            });
        } else if (v.getId() == R.id.birthday_ll) {
            initChoiceTimer(new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    birthdayTv.setText(String.valueOf(TimeUtils.stampToDate(date.getTime())));
                    LogUtils.d(date.getTime());
                    mDate = String.valueOf(TimeUtils.stampToDate2(date.getTime()));
                    LogUtils.e(String.valueOf(TimeUtils.getTime(date.getTime())));
                }
            }, "选择出生日期", 1920, false);
            pvTime.show();
        }

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("key", AppConstant.KEY);
        httpParams.put("member_sex", isStringEmpty(MEMBER_SEX) ? "" : MEMBER_SEX);
        if (mSelected != null && !mSelected.isEmpty()) {
            if (mSelected.get(0) != null) {
                if (FileUtils.getFileByUri(context, mSelected.get(0)).exists()) {
                    httpParams.put("avator", FileUtils.getFileByUri(context, mSelected.get(0)));

                }
            }

        }
        httpParams.put("member_truename", isEditTextEmpty(nameEdit) ? "" : getEditString(nameEdit));
        httpParams.put("birthday", isStringEmpty(mDate) ? "" : mDate);
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
    public void onPostInformationSuccess(Object mag) {
        if (((String) mag).toString().equals("1")) {
            onBackPressed();
        }

    }



}
