package com.aite.mainlibrary.activity.allnews.newsinformation;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.NewsGoodBean;
import com.aite.mainlibrary.Mainbean.NewsInformationBean;
import com.google.gson.Gson;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.bean.BaseData;
import com.lzy.basemodule.bean.BeanConvertor;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONObject;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewsInformationPresenter extends BasePresenterImpl<NewsInformationContract.View> implements NewsInformationContract.Presenter {

    @Override
    public void getNewsInformation(HttpParams httpParams) {
        OkGo.<BaseData<NewsInformationBean>>get(AppConstant.NEWS_INFO)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<NewsInformationBean>>() {
                    @Override
                    public BaseData<NewsInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject object = jsonObject.optJSONObject("datas");
                        Gson gson = new Gson();
                        NewsInformationBean newsInformationBean = gson.fromJson(object.toString(), NewsInformationBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetNewsInformationSuccess(newsInformationBean));

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<NewsInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<NewsInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
