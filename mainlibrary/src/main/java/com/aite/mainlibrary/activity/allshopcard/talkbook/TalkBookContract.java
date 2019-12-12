package com.aite.mainlibrary.activity.allshopcard.talkbook;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TalkBookContract {
    interface View extends BaseView {
        void onTalkSuccess(Object msg);

    }

    interface Presenter extends BasePresenter<View> {
        void onTalk(HttpParams httpParams);

    }
}
