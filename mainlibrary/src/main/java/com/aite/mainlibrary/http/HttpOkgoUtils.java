package com.aite.mainlibrary.http;

import android.app.Activity;
import android.content.Context;

import com.aite.mainlibrary.R;
import com.lzy.basemodule.bean.BaseData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

public class HttpOkgoUtils {
    private volatile static HttpOkgoUtils mInstance;

    public static HttpOkgoUtils getmInstance() {
        if (mInstance == null) {
            synchronized (HttpOkgoUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpOkgoUtils();
                }
            }
        }
        return mInstance;
    }

    private HttpOkgoUtils() {

    }

    private static boolean isActivity(Context context) {
        return !(context instanceof Activity);
    }

//    public void mMsgCenterActivity(Context context, HttpParams params, String urladrress) {
//        if (isActivity(context)) return;
//        OkGo.<BaseData<BaseData>>post(context.getResources().getString(R.string.baseUrl) + context.getString(R.string.addbankbar))
//                .tag(context)
//                .params(params)
//                .execute(new AbsCallback<BaseData<BaseData>>() {
//                    @Override
//                    public BaseData<BaseData> convertResponse(okhttp3.Response response) throws Throwable {
//
//                        return null;
//                    }
//
//                    @Override
//                    public void onStart(Request<BaseData<BaseData>, ? extends Request> request) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(Response<BaseData<BaseData>> response) {
//
//
//                    }
//                });
//
//
//    }

}
