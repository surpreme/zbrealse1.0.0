package com.aite.mainlibrary.activity.allsetting.bindinguser;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingUserContract {
    interface View extends BaseView {
        void  onGetInformationSuccess(Object msg);

        
    }

    interface  Presenter extends BasePresenter<View> {
        void  getInformation(HttpParams httpParams);

    }
}
