package com.aite.mainlibrary.activity.allshopcard.postairneed;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AllAreaBean;
import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
import com.aite.mainlibrary.Mainbean.TypeBean;
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

public class PostAirNeedPresenter extends BasePresenterImpl<PostAirNeedContract.View> implements PostAirNeedContract.Presenter {

    @Override
    public void getPostMsg(HttpParams httpParams) {
        OkGo.<BaseData<TwoSuccessCodeBean>>post(AppConstant.POSTAIRBANKSERVICEURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<TwoSuccessCodeBean>>() {
                    @Override
                    public BaseData<TwoSuccessCodeBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            TwoSuccessCodeBean successCodeBean = BeanConvertor.convertBean(object.toString(), TwoSuccessCodeBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onPostMsgSuccess(successCodeBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<TwoSuccessCodeBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<TwoSuccessCodeBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void getAreachoice() {
        OkGo.<BaseData<AllAreaBean>>get(AppConstant.AREACHIOCEHELPDOCTORNEEDURL)
                .tag(mView.getContext())
                .execute(new AbsCallback<BaseData<AllAreaBean>>() {
                    @Override
                    public BaseData<AllAreaBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");

                            Gson gson = new Gson();
                            AllAreaBean allAreaBean = gson.fromJson(object.toString(), AllAreaBean.class);
//                            AllAreaBean allAreaBean = BeanConvertor.convertBean(object.toString(), AllAreaBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetAreaChoiceSuccess(allAreaBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<AllAreaBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<AllAreaBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void getTypeService() {
        OkGo.<BaseData<TypeBean>>get(AppConstant.TYCHOICEAIRINFORMATIONURL)
                .tag(mView.getContext())
                .execute(new AbsCallback<BaseData<TypeBean>>() {
                    @Override
                    public BaseData<TypeBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            assert object != null;
                            TypeBean typeBean = gson.fromJson(object.toString(), TypeBean.class);
//                            TypeBean typeBean = BeanConvertor.convertBean(object.toString(), TypeBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetTypeServiceSuccess(typeBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<TypeBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<TypeBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }
}
