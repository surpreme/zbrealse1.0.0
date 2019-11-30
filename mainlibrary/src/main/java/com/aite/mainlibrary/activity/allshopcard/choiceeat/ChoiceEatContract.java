package com.aite.mainlibrary.activity.allshopcard.choiceeat;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChoiceEatContract {
    interface View extends BaseView {
        void getDataSuccess(Object msg);
        void getScondDataSuccess(Object msg);
        void addShopCardSuccesss(Object msg);
        void onShopCardSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getDatalist(HttpParams httpParams);
        void getScondDatalist(HttpParams httpParams);
        void addShopCard(HttpParams httpParams);
        void getShopCard(HttpParams httpParams);


    }
}
