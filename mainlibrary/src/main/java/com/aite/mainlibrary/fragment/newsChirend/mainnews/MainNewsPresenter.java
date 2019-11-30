package com.aite.mainlibrary.fragment.newsChirend.mainnews;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.NewsBean;
import com.aite.mainlibrary.Mainbean.NewsGoodBean;
import com.aite.mainlibrary.Mainbean.TopNewsBean;
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

public class MainNewsPresenter extends BasePresenterImpl<MainNewsContract.View> implements MainNewsContract.Presenter {

    @Override
    public void getListMsg(HttpParams httpParams) {
        OkGo.<BaseData<NewsGoodBean>>get(AppConstant.RECOMMENDED_NEWS)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<NewsGoodBean>>() {
                    @Override
                    public BaseData<NewsGoodBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        NewsGoodBean newsGoodBean = BeanConvertor.convertBean(jsonObject.toString(), NewsGoodBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetListSuccess(newsGoodBean));

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<NewsGoodBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<NewsGoodBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void getTopNews(HttpParams httpParams) {
        OkGo.<BaseData<TopNewsBean>>get(AppConstant.TOP_NEWS)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<TopNewsBean>>() {
                    @Override
                    public BaseData<TopNewsBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        TopNewsBean topNewsBean = BeanConvertor.convertBean(jsonObject.toString(), TopNewsBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetTopNewsSuccess(topNewsBean));

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<TopNewsBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<TopNewsBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }
}
