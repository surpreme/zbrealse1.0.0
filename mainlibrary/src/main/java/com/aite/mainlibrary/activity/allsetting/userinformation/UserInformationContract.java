package com.aite.mainlibrary.activity.allsetting.userinformation;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UserInformationContract {
    interface View extends BaseView {
        void onGetUserInformation(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInformation(HttpParams httpParams);
        
    }
}
