package com.aite.mainlibrary.activity.allsetting.sosuser;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SosUserContract {
    interface View extends BaseView {
        void onGetSoslistUserInformation(Object msg);


    }

    interface  Presenter extends BasePresenter<View> {
        void getUserSoslistInformation(HttpParams httpParams);

    }
}
