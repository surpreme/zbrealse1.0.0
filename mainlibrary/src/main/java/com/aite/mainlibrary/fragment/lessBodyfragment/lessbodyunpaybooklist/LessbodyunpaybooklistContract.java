package com.aite.mainlibrary.fragment.lessBodyfragment.lessbodyunpaybooklist;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LessbodyunpaybooklistContract {
    interface View extends BaseView {
        void onGetinformationSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getinformation(HttpParams httpParams);

    }
}
