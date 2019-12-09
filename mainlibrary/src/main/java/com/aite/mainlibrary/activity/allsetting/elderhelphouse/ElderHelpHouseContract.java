package com.aite.mainlibrary.activity.allsetting.elderhelphouse;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ElderHelpHouseContract {
    interface View extends BaseView {
        void onGetHelpEdlerHouseInformationSuccess(Object mag);

        void onChangeBinderHelpEdlerHouseSuccess(Object mag);
    }

    interface Presenter extends BasePresenter<View> {
        void getHelpEdlerHouseInformation(HttpParams httpParams);

        void changeBinderHelpEdlerHouse(HttpParams httpParams, String url);

    }
}
