package com.aite.mainlibrary.activity.allsetting.helpdocotorinformationbook;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PostInformationBookContract {
    interface View extends BaseView {
        void onGetBookInforMationSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void getBookInforMation(String url, HttpParams httpParams);

    }
}
