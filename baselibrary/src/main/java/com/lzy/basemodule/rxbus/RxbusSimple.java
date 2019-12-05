package com.lzy.basemodule.rxbus;

import android.content.Context;

import com.blankj.rxbus.RxBus;
import com.lzy.basemodule.R;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-01
 * @desc:
 */
public class RxbusSimple {
    public void test(Context context) {
        RxBus.getDefault().post("jumpShopCar", "jumpShopCar");
        RxBus.getDefault().subscribe(context, "jumpShopCar", new RxBus.Callback<String>() {
            @Override
            public void onEvent(String o) {


            }
        });
    }

}
