package com.aite.mainlibrary.activity.allsetting.healthbook;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class HealthBookContract {
    interface View extends BaseView {
        void onGetInformationListSuccess(Object msg);
        void onPostInformationSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getInformationList(HttpParams httpParams);
        void onPostInformation(HttpParams httpParams);

    }
}
