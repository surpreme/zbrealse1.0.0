package com.aite.mainlibrary.activity.allshopcard.booktimebankinformation;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BookTimebankInformationContract {
    interface View extends BaseView {
        void onGetInformationSuccess(Object msg);

        void onStartServiceSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void getInformation(HttpParams httpParams);

        void getInformation(HttpParams httpParams, String mUrl);

        void StartService(HttpParams httpParams);

        void StartService(HttpParams httpParams, String mUrl);


    }
}
