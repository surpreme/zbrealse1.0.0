package com.aite.mainlibrary.activity.allshopcard.talkbook;


import com.aite.mainlibrary.R;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TalkBookActivity extends BaseActivity<TalkBookContract.View, TalkBookPresenter> implements TalkBookContract.View {

    @Override
    protected int getLayoutResId() {
        return R.layout.talk_book;
    }

    @Override
    protected void initView() {
        initToolbar("评价");
        LogUtils.d(getIntent().getStringExtra("id"));

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
    public void onTalkSuccess(Object msg) {

    }
}
