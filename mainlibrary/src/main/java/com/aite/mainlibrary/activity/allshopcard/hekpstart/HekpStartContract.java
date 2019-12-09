package com.aite.mainlibrary.activity.allshopcard.hekpstart;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class HekpStartContract {
    interface View extends BaseView {
        void  onPostImgSuccess(Object msg);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void  PostImg(HttpParams httpParams,String url);

    }
}
