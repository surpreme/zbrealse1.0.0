package com.aite.mainlibrary.activity.allsetting.addsosuser;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AddSosUserContract {
    interface View extends BaseView {
        void onGetBindUserfamilysuccess(Object msg);
        void onPostAllBindUserfamilyInformationSuccess(Object msg);

    }

    interface  Presenter extends BasePresenter<View> {
        void getBindUserfamily(HttpParams httpParams);
        void postAllBindUserfamilyInformation(HttpParams httpParams);

    }
}
