package com.aite.mainlibrary.activity.allsetting.addbindinguser;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddBindingUserContract {
    interface View extends BaseView {
        void onPostBindUsersuccess(Object msg);

        void onGetBindUserfamilysuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void postBindUser(HttpParams httpParams);

        void getBindUserfamily(HttpParams httpParams);

    }
}
