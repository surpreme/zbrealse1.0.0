package com.aite.mainlibrary.fragment.newsChirend.mainnews;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainNewsContract {
    interface View extends BaseView {
        void onGetListSuccess(Object msg);
        void onGetTopNewsSuccess(Object msg);
    }

    interface  Presenter extends BasePresenter<View> {
        void getListMsg(HttpParams httpParams);
        void getTopNews(HttpParams httpParams);
    }
}
