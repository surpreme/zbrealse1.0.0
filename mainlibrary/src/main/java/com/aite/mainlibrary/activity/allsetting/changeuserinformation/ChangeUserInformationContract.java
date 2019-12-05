package com.aite.mainlibrary.activity.allsetting.changeuserinformation;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChangeUserInformationContract {
    interface View extends BaseView {
        void onPostInformationSuccess(Object mag);

    }

    interface Presenter extends BasePresenter<View> {
        void postInformation(HttpParams httpParams);

    }
}
