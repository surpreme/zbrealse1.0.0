package com.aite.aitezhongbao.activity.usertype;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.login.LoginActivity;
import com.aite.aitezhongbao.bean.SureFindPasswordCodeBean;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.util.FileUtils;
import com.lzy.basemodule.view.StatusBarUtils;
import com.lzy.okgo.model.HttpParams;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UserTypeActivity extends BaseActivity<UserTypeContract.View, UserTypePresenter> implements UserTypeContract.View {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.next_btn)
    Button next_btn;
    //顶部图标
    @BindView(R.id.log_img_id)
    ImageView logImgId;
    //上传医师资格证（正）tv
    @BindView(R.id.person_book_first_tv)
    TextView personBookFirstTv;
    @BindView(R.id.person_book_sencond_tv)
    TextView personBookSencondTv;
    //上传医师资格证（正）img
    @BindView(R.id.person_book_first_img)
    ImageView personBookFirstImg;
    @BindView(R.id.person_book_sencond_img)
    ImageView personBookSencondImg;
    //本人手持工作证（正）tv
    @BindView(R.id.work_book_first_tv)
    TextView workBookFirstTv;
    @BindView(R.id.work_book_second_tv)
    TextView workBookSecondTv;
    //本人手持工作证（正）img
    @BindView(R.id.work_book_first_img)
    ImageView workBookFirstImg;
    @BindView(R.id.work_book_second_img)
    ImageView workBookSecondImg;
    //checkbox 会员 义工 护工 医生
    @BindView(R.id.people_chex)
    CheckBox peopleChex;
    @BindView(R.id.worker_chex)
    CheckBox workerChex;
    @BindView(R.id.girldoctor_chex)
    CheckBox girldoctorChex;
    @BindView(R.id.doctor_chex)
    CheckBox doctorChex;
    //提交
    @BindView(R.id.doctor_post_msg_ll)
    LinearLayout doctorPostMsgLl;
    private String key;
    private List<Uri> mSelected = new ArrayList<>();
    private List<Uri> mSelected2 = new ArrayList<>();
    private List<Uri> mSelected3 = new ArrayList<>();
    private List<Uri> mSelected4 = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.user_type_layout;
    }

    /**
     * REQUEST_CODE_CHOOSE_IMAGE person_book_first_tv医生资格证
     * REQUEST_CODE_CHOOSE_IMAGE_TWO work_book_first_tv工作证
     *
     * @param v
     */
    @OnClick({R.id.iv_back, R.id.log_img_id, R.id.people_chex, R.id.worker_chex, R.id.girldoctor_chex, R.id.doctor_chex, R.id.person_book_first_tv, R.id.person_book_sencond_tv, R.id.work_book_first_tv, R.id.work_book_second_tv, R.id.doctor_post_msg_ll, R.id.next_btn})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.person_book_first_tv:
                openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE);
                break;
            case R.id.work_book_first_tv:
                openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_TWO);
                break;
            case R.id.person_book_sencond_tv:
                openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_THREE);
                break;
            case R.id.work_book_second_tv:
                openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_FOUR);
                break;

            case R.id.next_btn:
                if (Checkmsg(doctorChex) == 1) {
                    if (mSelected.isEmpty() || mSelected2.isEmpty() || mSelected3.isEmpty()) {
                        showTopToasts("请上传图片");
                        return;
                    }
                }
                mPresenter.ChoiceHeght(initParams());
//                AppManager.getInstance().killAllActivity();
//                startActivity(LoginActivity.class);
                break;
//                AppManager.getInstance().killActivity(NewUserActivity.class);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Glide.with(context).load(mSelected.get(0)).into(personBookFirstImg);
        } else if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_TWO && resultCode == RESULT_OK) {
            mSelected2 = Matisse.obtainResult(data);
            Glide.with(context).load(mSelected2.get(0)).into(workBookFirstImg);

        } else if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_THREE && resultCode == RESULT_OK) {
            mSelected3 = Matisse.obtainResult(data);
            Glide.with(context).load(mSelected3.get(0)).into(personBookSencondImg);

        } else if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_FOUR && resultCode == RESULT_OK) {
            mSelected4 = Matisse.obtainResult(data);
            Glide.with(context).load(mSelected4.get(0)).into(workBookSecondImg);

        } else {

        }

    }

    @Override
    protected void initView() {
        StatusBarUtils.setColor(context, Color.WHITE);
        doctorPostMsgLl.setVisibility(View.GONE);
        doctorChex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    doctorPostMsgLl.setVisibility(View.VISIBLE);
                } else {
                    doctorPostMsgLl.setVisibility(View.GONE);

                }
            }
        });


    }

    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        params.put("is_member", Checkmsg(peopleChex));
        params.put("is_hugong", Checkmsg(girldoctorChex));
        params.put("is_doctors", Checkmsg(doctorChex));
        params.put("is_volunteer", Checkmsg(workerChex));
        if (!mSelected.isEmpty())
            params.put("doctor_license_z", FileUtils.getFileByUri(context, mSelected.get(0)));
        if (!mSelected3.isEmpty())
            params.put("doctor_license_f", FileUtils.getFileByUri(context, mSelected3.get(0)));
        if (!mSelected2.isEmpty())
            params.put("work_permit", FileUtils.getFileByUri(context, mSelected2.get(0)));


//        params.put("doctor_license_z", Objects.requireNonNull(FileUtils.getFileByUri(context, mSelected.get(0))));

        params.put("key", key);
        return params;
    }

    private int Checkmsg(CheckBox checkBox) {
        return checkBox.isChecked() ? 1 : 0;
    }

    @Override
    protected void initDatas() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        key = getIntent().getStringExtra("key");
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void setChoiceHeghtSuccess(Object msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showTopToasts(((SureFindPasswordCodeBean) msg).getMsg());
                if (((SureFindPasswordCodeBean) msg).getState().equals("1"))
                    startActivity(LoginActivity.class);
            }
        });


    }


    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.log_img_id:
                break;
            case R.id.people_chex:
                break;
            case R.id.worker_chex:
                break;
            case R.id.girldoctor_chex:
                break;
            case R.id.doctor_chex:
                break;
            case R.id.person_book_first_tv:
                break;
            case R.id.person_book_sencond_tv:
                break;
            case R.id.person_book_first_img:
                break;
            case R.id.person_book_sencond_img:
                break;
            case R.id.work_book_first_tv:
                break;
            case R.id.work_book_second_tv:
                break;
            case R.id.work_book_first_img:
                break;
            case R.id.work_book_second_img:
                break;
            case R.id.doctor_post_msg_ll:
                break;
            case R.id.next_btn:
                break;
        }
    }
}
