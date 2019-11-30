package com.aite.mainlibrary.activity.allshopcard.shopcard;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShopCardContract {
    interface View extends BaseView {
        void onShopCardListSuccess(Object msg);
        void ondeleteShopCardItemSuccess(Object msg);
        void onlessShopThingNumberSuccess(Object msg);
    }

    interface  Presenter extends BasePresenter<View> {
        void getShopCardList(HttpParams httpParams);
        void deleteShopCardItem(HttpParams httpParams);
        void  addlessShopThingNumber(HttpParams httpParams);


    }
}
