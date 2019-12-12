package com.aite.mainlibrary.activity.allsetting.bookdispute;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BookDisputeActivity extends BaseActivity<BookDisputeContract.View, BookDisputePresenter> implements BookDisputeContract.View {

    @BindView(R2.id.bottom_btn)
    Button bottomBtn;
    @BindView(R2.id.choice_iv_ll)
    LinearLayout choiceIvLl;
    @BindView(R2.id.src_iv)
    ImageView srcIv;
    private List<Uri> mSelected = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.book_dispute;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        initToolbar("交易投诉");
        bottomBtn.setBackground(getDrawable(R.drawable.round_background_yellow));
        bottomBtn.setText("提交申请");
        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @OnClick({R2.id.choice_iv_ll})
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.choice_iv_ll)
            openImg(this, 3, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
//            ImageUtils.getmInstance().photoClip(this, mSelected.get(0));
            if (mSelected.isEmpty()) return;
            Glide.with(context).load(mSelected.get(0)).into(srcIv);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
