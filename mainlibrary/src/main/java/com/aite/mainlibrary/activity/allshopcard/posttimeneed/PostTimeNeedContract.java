package com.aite.mainlibrary.activity.allshopcard.posttimeneed;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PostTimeNeedContract {
    interface View extends BaseView {
        void onPostServiceSuccess(Object msg);
        void onGetAreaChoiceSuccess(Object msg);

        void onChoiceServiceTypeSuccess(Object msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getPostService(HttpParams httpParams);

        void getChoiceServiceType();
        void getAreachoice();

    }
}
