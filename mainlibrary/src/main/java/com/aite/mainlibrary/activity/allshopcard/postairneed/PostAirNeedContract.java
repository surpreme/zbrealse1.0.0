package com.aite.mainlibrary.activity.allshopcard.postairneed;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PostAirNeedContract {
    interface View extends BaseView {
        void onPostMsgSuccess(Object msg);
        void onGetAreaChoiceSuccess(Object msg);
        void onGetTypeServiceSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void getPostMsg(HttpParams httpParams);
        void getAreachoice();
        void getTypeService();
    }
}
