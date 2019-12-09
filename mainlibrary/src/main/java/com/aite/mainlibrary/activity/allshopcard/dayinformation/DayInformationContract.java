package com.aite.mainlibrary.activity.allshopcard.dayinformation;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class DayInformationContract {
    interface View extends BaseView {
        void onGetInformationSuccess(Object msg);

        void onCollectSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void onGetInformation(HttpParams httpParams);

        void onCollect(HttpParams httpParams);

    }
}
