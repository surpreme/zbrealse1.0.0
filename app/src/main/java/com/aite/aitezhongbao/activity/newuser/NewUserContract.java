package com.aite.aitezhongbao.activity.newuser;

import com.aite.mainlibrary.Mainbean.AllAreaBean;
import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewUserContract {
    interface View extends BaseView {
        void sendphonecodeonSuccess(Object msg);

        void postAllmsgonSuccess(Object msg);



    }

    interface Presenter extends BasePresenter<View> {
        void sendphonecode(String phonenumbe);

        void postAllmsg(HttpParams params);

    }
}
