package com.aite.mainlibrary.activity.allshopcard.morningnooneat;

import com.lzy.basemodule.mvp.BasePresenter;
import com.lzy.basemodule.mvp.BaseView;
import com.lzy.okgo.model.HttpParams;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MorningNoonEatContract {
    interface View extends BaseView {
        void getDataListSuccess(Object msg);
        
    }

    interface  Presenter extends BasePresenter<View> {
        void getEatDataList(HttpParams httpParams,String type);
        
    }
}
