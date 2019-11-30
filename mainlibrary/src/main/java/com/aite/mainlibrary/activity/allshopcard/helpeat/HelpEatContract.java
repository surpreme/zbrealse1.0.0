package com.aite.mainlibrary.activity.allshopcard.helpeat;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class HelpEatContract {
    interface View extends BaseView {
        void getUiDataSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void showUiData(HttpParams httpParams);


    }
}
