package com.aite.mainlibrary.activity.allsetting.addhealthbook;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AddHealthBookContract {
    interface View extends BaseView {
        void onPostInformationSuccess(Object msg);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void PostInformation(HttpParams httpParams);

    }
}
