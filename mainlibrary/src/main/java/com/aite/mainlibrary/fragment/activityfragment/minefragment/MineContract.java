package com.aite.mainlibrary.fragment.activityfragment.minefragment;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MineContract {
    interface View extends BaseView {
        void onGetUserInformation(Object msg);
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInformation(HttpParams httpParams);

    }
}
