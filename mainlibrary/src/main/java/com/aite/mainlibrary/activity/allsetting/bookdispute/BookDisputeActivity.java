package com.aite.mainlibrary.activity.allsetting.bookdispute;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BookDisputeActivity extends BaseActivity<BookDisputeContract.View, BookDisputePresenter> implements BookDisputeContract.View {

    @BindView(R2.id.bottom_btn)
    Button bottomBtn;

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
