package com.aite.mainlibrary.activity.allshopcard.bookinformation;


import com.aite.mainlibrary.R;
import com.lzy.basemodule.base.BaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BookinformationActivity extends BaseActivity<BookinformationContract.View, BookinformationPresenter> implements BookinformationContract.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.book_informationlayout;
    }

    @Override
    protected void initView() {
        initToolbar("订单详情");
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
