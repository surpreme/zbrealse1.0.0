package com.aite.mainlibrary.activity.allshopcard.timebank;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TimeBankContract {
    interface View extends BaseView {
        void onMainUiListDataSuccess(Object msg);
        void onElseMainUiListDataSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void showUiListData(HttpParams httpParams);
        void showElseUiListData(HttpParams httpParams);
    }
}
