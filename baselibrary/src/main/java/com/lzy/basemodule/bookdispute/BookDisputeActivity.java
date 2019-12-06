package com.lzy.basemodule.bookdispute;


import com.lzy.basemodule.R;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BookDisputeActivity extends BaseActivity<BookDisputeContract.View, BookDisputePresenter> implements BookDisputeContract.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.helpdoctor_infomation;
    }

    @Override
    protected void initView() {

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
