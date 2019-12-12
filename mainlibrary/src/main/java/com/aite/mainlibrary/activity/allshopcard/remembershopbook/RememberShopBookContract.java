package com.aite.mainlibrary.activity.allshopcard.remembershopbook;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RememberShopBookContract {
    interface View extends BaseView {
        void onGetFoodInformationSuccess(Object msg);

        void postAllInformationSuccess(Object msg);
        void onGetAddressSuccess(Object msg);
        void onPayListSuccess(Object msg);


    }

    interface Presenter extends BasePresenter<View> {
        void getFoodInformation(HttpParams httpParams);

        void postAllInformation(HttpParams httpParams);
        void getAddress(HttpParams httpParams);
        void getPayList(HttpParams httpParams);


    }
}
