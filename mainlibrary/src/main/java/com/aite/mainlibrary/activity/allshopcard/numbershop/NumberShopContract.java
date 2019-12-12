package com.aite.mainlibrary.activity.allshopcard.numbershop;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NumberShopContract {
    interface View extends BaseView {
        void onGetShopListSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void GetShopList(HttpParams httpParams);

    }
}
