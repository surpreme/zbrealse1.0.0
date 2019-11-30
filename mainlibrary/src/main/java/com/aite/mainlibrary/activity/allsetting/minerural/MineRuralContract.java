package com.aite.mainlibrary.activity.allsetting.minerural;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MineRuralContract {
    interface View extends BaseView {
        void onGetMineListSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void GetMineList(HttpParams httpParams);

    }
}
