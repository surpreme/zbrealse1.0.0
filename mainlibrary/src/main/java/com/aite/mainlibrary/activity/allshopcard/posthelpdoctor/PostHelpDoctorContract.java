package com.aite.mainlibrary.activity.allshopcard.posthelpdoctor;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PostHelpDoctorContract {
    interface View extends BaseView {
        void onPostSuccess(Object msg);
        void onGetAreaChoiceSuccess(Object msg);
        void onGetTypeServiceSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void postInformation(HttpParams httpParams);
        void getAreachoice();
        void getTypeService();

    }
}
