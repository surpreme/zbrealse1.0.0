package com.aite.mainlibrary.mvp;

import com.aite.mainlibrary.mvp.BasePresenter;

public interface BaseView<P extends BasePresenter> {
    void setPresenter(P presenter);
}
