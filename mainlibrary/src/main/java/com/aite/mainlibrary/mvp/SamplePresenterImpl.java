package com.aite.mainlibrary.mvp;

public class SamplePresenterImpl implements SampleContract.Presenter {

    private SampleContract.View mView;

    public SamplePresenterImpl(SampleContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void getData(int userId) {

    }

    @Override
    public void checkData() {

    }

    @Override
    public void deleteMsg(int msgId) {

    }

    @Override
    public void start() {

    }
}
