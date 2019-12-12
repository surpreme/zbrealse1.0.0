package com.aite.aitezhongbao.activity.newusermsg;

import com.aite.mainlibrary.Mainbean.AllAreaBean;
import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewusermsgContract {
    interface View extends BaseView {
        void sureAllmsgSuccess(String msg);


        void onGetAreaChoiceSuccess(AllAreaBean msg);

    }

    interface Presenter extends BasePresenter<View> {
        void sureAllmsg(HttpParams httpParams);

        void getAreachoice();

    }
}
