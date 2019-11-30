package com.aite.mainlibrary.activity.allshopcard.daytogether;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class DayTogetherContract {
    interface View extends BaseView {
        void onGetListSuccess(Object msg);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void getListMsg(HttpParams httpParams);


    }
}
