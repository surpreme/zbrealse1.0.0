package com.aite.aitezhongbao.activity.login;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void logInSuccess(Object msg);
        void logInFail(String error);

    }

    interface  Presenter extends BasePresenter<View> {
        void login(String name,String password);

    }
}
