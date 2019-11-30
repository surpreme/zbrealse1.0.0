package com.aite.aitezhongbao.activity.newusermsg;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.usertype.UserTypeActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.util.FileUtils;
import com.lzy.basemodule.view.StatusBarUtils;
import com.lzy.okgo.model.HttpParams;
import com.zhihu.matisse.Matisse;

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
    private String key;
    private String username;
    private List<Uri> mSelected;
    private List<Uri> mSelected2;

    @Override
    protected int getLayoutResId() {
        return R.layout.user_msg_sure_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setColor(context, Color.WHITE);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            key = bundle.getString("key");
////            username = bundle.getString("username");
//        }

    }

    @Override
    protected void initDatas() {

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

    @OnClick({R.id.iv_back, R.id.next_btn, R.id.people_book_first_tv, R.id.people_book_second_tv})
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
                if (mSelected == null || mSelected2 == null)
                    return;
                mPresenter.sureAllmsg(initParams());
//                startActivity(UserTypeActivity.class);
                break;
//                startActivity(NewUserMsgActivity.class);
//                AppManager.getInstance().killActivity(NewUserActivity.class);
        }
    }


    private HttpParams initParams() {
        HttpParams params = new HttpParams();
        if (!isStringEmpty(key))
            params.put("key", key);
        params.put("member_truename", getEditString(nameEdit));
        params.put("member_idcard", getEditString(peoplebooknumberEdit));
        params.put("member_address", getEditString(addressEdit));
        //省市区
        params.put("member_provinceid", 1);
        params.put("member_cityid", 2);
        params.put("member_areaid", 3);
        //身份证图片
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
}
