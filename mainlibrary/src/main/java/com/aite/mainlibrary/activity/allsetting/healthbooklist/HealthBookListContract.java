package com.aite.mainlibrary.activity.allsetting.healthbooklist;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class HealthBookListContract {
    interface View extends BaseView {
        void onGetInformationListSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void GetInformationList(HttpParams httpParams);

    }
}
