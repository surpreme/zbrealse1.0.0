package com.aite.mainlibrary.activity.allnews.newsinformation;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NewsInformationContract {
    interface View extends BaseView {
        void  onGetNewsInformationSuccess(Object msg);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void getNewsInformation(HttpParams httpParams);
        
    }
}
