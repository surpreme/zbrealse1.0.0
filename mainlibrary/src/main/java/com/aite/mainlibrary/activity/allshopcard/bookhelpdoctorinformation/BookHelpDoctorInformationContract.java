package com.aite.mainlibrary.activity.allshopcard.bookhelpdoctorinformation;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BookHelpDoctorInformationContract {
    interface View extends BaseView {
        void onGetInformationSuccess(Object msg);
        void onStartServiceSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void getInformation(HttpParams httpParams);

        void StartService(HttpParams httpParams);

    }
}
