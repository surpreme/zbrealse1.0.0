package com.aite.mainlibrary.activity.allshopcard.timebankrules;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TimeBankRulesContract {
    interface View extends BaseView {
        void  onGetRulesSuccess(Object msg);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void  getRulesSuccess(HttpParams httpParams);

    }
}
