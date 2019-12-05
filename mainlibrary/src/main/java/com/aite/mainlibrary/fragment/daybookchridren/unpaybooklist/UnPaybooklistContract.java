package com.aite.mainlibrary.fragment.daybookchridren.unpaybooklist;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class UnPaybooklistContract {
    interface View extends BaseView {
        void onGetinformationSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void getinformation(HttpParams httpParams);

    }
}
