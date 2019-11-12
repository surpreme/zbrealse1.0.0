package com.aite.mainlibrary.mvp;

public interface SampleContract {
    interface Presenter extends BasePresenter {
        //获取数据
        void getData( int userId);
        //检查数据是否有效
        void checkData();
        //删除消息
        void deleteMsg( int msgId);
    }

    interface View extends BaseView<Presenter> {
        //显示加载中
        void showLoading();
        //刷新界面
        void refreshUI(String data);
        //显示错误界面
        void showError();
    }
}
