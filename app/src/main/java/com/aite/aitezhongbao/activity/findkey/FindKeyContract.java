package com.aite.aitezhongbao.activity.findkey;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FindKeyContract {
    interface View extends BaseView {
        void setNewPasswordonSuccess(Object msg);
        void setPostCodeSuccess(Object msg);
        void setSureCodeSuccess(Object msg);

        
    }

    interface  Presenter extends BasePresenter<View> {
        void sendPHONECODEfindkey(String phonenumber);
        void surefindkey(HttpParams httpParams);
        void sureALLfindkey(HttpParams httpParams);


    }
}
