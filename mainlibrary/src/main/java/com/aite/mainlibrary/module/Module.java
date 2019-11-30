package com.aite.mainlibrary.module;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 链接v 和 p（网络层）
 * 弃用
 */
public class Module {

    public Observable<Long> c(){
        return Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
