package com.aite.aitezhongbao.activity.newusermsg;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.usertype.UserTypeActivity;
import com.aite.aitezhongbao.utils.DistrictUtils;
import com.aite.aitezhongbao.utils.IDNumberUtils;
import com.aite.mainlibrary.Mainbean.AllAreaBean;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.FileUtils;
import com.lzy.basemodule.view.StatusBarUtils;
import com.lzy.okgo.model.HttpParams;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewusermsgActivity extends BaseActivity<NewusermsgContract.View, NewusermsgPresenter> implements NewusermsgContract.View {
    @BindView(R.id.next_btn)
    Button next_btn;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.log_img_id)
    ImageView logImgId;
    @BindView(R.id.name_edit)
    TextInputEditText nameEdit;
    @BindView(R.id.address_edit)
    TextInputEditText addressEdit;
    @BindView(R.id.peoplebooknumber_edit)
    TextInputEditText peoplebooknumberEdit;
    @BindView(R.id.people_book_first_tv)
    TextView peopleBookFirstTv;
    @BindView(R.id.people_book_second_tv)
    TextView peopleBookSecondTv;
    @BindView(R.id.people_book_first_img)
    ImageView peopleBookFirstImg;
    @BindView(R.id.people_book_second_img)
    ImageView peopleBookSecondImg;
    @BindView(R.id.ll_site)
    LinearLayout mLlSite;
    @BindView(R.id.tv_region)
    TextView mTvRegion;

    private String key;
    private String username;
    private List<Uri> mSelected;
    private List<Uri> mSelected2;


    //省市区数据
    private List<AllAreaBean.ListBean> mDistrictList = new ArrayList<>();
    private String[] mSiteId;
    private DistrictUtils mDistrictUtils;

    @Override
    protected int getLayoutResId() {
        return R.layout.user_msg_sure_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setColor(context, Color.WHITE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            key = bundle.getString("key");
//            username = bundle.getString("username");
        }
    }

    @Override
    protected void initDatas() {
        mPresenter.getAreachoice();
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Glide.with(context).load(mSelected.get(0)).into(peopleBookFirstImg);
        } else if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_TWO && resultCode == RESULT_OK) {
            mSelected2 = Matisse.obtainResult(data);
            Glide.with(context).load(mSelected2.get(0)).into(peopleBookSecondImg);

        } else {

        }

    }

    @OnClick({R.id.iv_back, R.id.next_btn, R.id.people_book_first_tv, R.id.people_book_second_tv, R.id.ll_site})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.people_book_first_tv:
                openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE);
                break;
            case R.id.people_book_second_tv:
                openImg(this, 1, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_TWO);
                break;
            case R.id.next_btn:

                nextStep();


                break;
//                startActivity(NewUserMsgActivity.class);
//                AppManager.getInstance().killActivity(NewUserActivity.class);
            case R.id.ll_site:
                //省市区
                if (mDistrictList.size() != 0) {
                    mDistrictUtils = new DistrictUtils(getContext(), mDistrictList, mTvRegion);
                    mDistrictUtils.showDialogCity();
                } else {
                    mPresenter.getAreachoice();
                }

                break;
        }

    }

    /***
     * 提交
     */
    private void nextStep() {
        String name = nameEdit.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showToast("请填写真实名字...");
            return;
        }


        if (TextUtils.isEmpty(mTvRegion.getText())) {
            showToast("请选择服务地址再提交...");
            return;
        }

        String addres = addressEdit.getText().toString();
        if (TextUtils.isEmpty(addres)) {
            showToast("请填写详情地址...");
            return;
        }

        String numID = peoplebooknumberEdit.getText().toString();
        if (!IDNumberUtils.isIDNumber(numID)) {
            showToast("请填写正确的身份证号码...");
            return;
        }
        if (mSelected == null || mSelected2 == null) {
            showToast("请上传图片...");
            return;
        }
        mPresenter.sureAllmsg(initParams(name, addres, numID));
        startActivity(UserTypeActivity.class);
    }


    private HttpParams initParams(String name, String addres, String numID) {
        mSiteId = mDistrictUtils.getSiteId();
        //Log.d(TAG, "\n省ID =:" + mSiteId[0] + "\n市ID =:" + mSiteId[1] + "\n区ID:" + mSiteId[2]);
        HttpParams params = new HttpParams();
        if (!isStringEmpty(key))
            params.put("key", key);
        params.put("member_truename", name);
        params.put("member_idcard", numID);
        params.put("member_address", addres);
        //省市区
        params.put("member_provinceid", mSiteId[0]);
        params.put("member_cityid", mSiteId[1]);
        params.put("member_areaid", mSiteId[2]);
//        //身份证图片
        params.put("member_card_zthumb", Objects.requireNonNull(FileUtils.getFileByUri(context, mSelected.get(0))));
        params.put("member_card_fthumb", Objects.requireNonNull(FileUtils.getFileByUri(context, mSelected2.get(0))));
        return params;
    }

    @Override
    protected void onResume() {
        super.onResume();
        key = getIntent().getStringExtra("key");
    }


    @Override
    protected void initReStart() {

    }

    @Override
    public void sureAllmsgSuccess(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(msg);
                showTopToasts(msg);
                startActivity(UserTypeActivity.class);
            }
        });
    }

    /**
     * 全部省市区数据
     * @param bean
     */
    @Override
    public void onGetAreaChoiceSuccess(AllAreaBean bean) {
        mDistrictList.clear();
        mDistrictList = bean.getList();
    }


}
