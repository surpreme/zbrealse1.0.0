package com.aite.mainlibrary.activity.allshopcard.numberbank;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class NumberBankContract {
    interface View extends BaseView {
        void onGetMainUiSuccess(Object msg);
        void onGetChoiceUiSuccess(Object msg);
        void onGetInformationListSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getMainUi(HttpParams httpParams);
        void getChoiceUi(HttpParams httpParams);
        void getInformationList(HttpParams httpParams);

    }
}
