package com.aite.mainlibrary.activity.allshopcard.air;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AirContract {
    interface View extends BaseView {
        void onDataListSuccess(Object msg);
        void onTypeDatasucess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void getDataList(HttpParams httpParams);
        void getTypeDataList(HttpParams httpParams);

    }
}
