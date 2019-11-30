package com.aite.mainlibrary.activity.allshopcard.helpdoctor;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HelpdoctorContract {
    interface View extends BaseView {
        void onGetListSuccess(Object msg);
        void onGetTypeDataSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void getList(HttpParams httpParams);
        void getTypeData(HttpParams httpParams);

    }
}
