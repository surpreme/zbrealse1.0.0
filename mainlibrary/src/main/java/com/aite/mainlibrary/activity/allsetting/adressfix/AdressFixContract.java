package com.aite.mainlibrary.activity.allsetting.adressfix;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AdressFixContract {
    interface View extends BaseView {
        void onGetAdressListSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getAdressList(HttpParams httpParams);

    }
}
