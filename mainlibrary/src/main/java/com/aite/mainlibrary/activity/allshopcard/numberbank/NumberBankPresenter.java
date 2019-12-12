package com.aite.mainlibrary.activity.allshopcard.numberbank;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.MorningNoonEatBean;
import com.aite.mainlibrary.Mainbean.NumberBankBean;
import com.aite.mainlibrary.Mainbean.NumberBankInformationBean;
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

public class NumberBankPresenter extends BasePresenterImpl<NumberBankContract.View> implements NumberBankContract.Presenter {

    @Override
    public void getMainUi(HttpParams httpParams) {
        OkGo.<BaseData<NumberBankInformationBean>>get(AppConstant.NUMBERBANKINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<NumberBankInformationBean>>() {
                    @Override
                    public BaseData<NumberBankInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            NumberBankInformationBean numberBankInformationBean = gson.fromJson(object.toString(), NumberBankInformationBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetMainUiSuccess(numberBankInformationBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<NumberBankInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<NumberBankInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void getChoiceUi(HttpParams httpParams) {
        OkGo.<BaseData<NumberBankInformationBean>>get(AppConstant.NUMBERBANKINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<NumberBankInformationBean>>() {
                    @Override
                    public BaseData<NumberBankInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            NumberBankInformationBean numberBankInformationBean = gson.fromJson(object.toString(), NumberBankInformationBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetMainUiSuccess(numberBankInformationBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<NumberBankInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<NumberBankInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void getInformationList(HttpParams httpParams) {
        OkGo.<BaseData<NumberBankBean>>get(AppConstant.AGOGETNUMBERBANKINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<NumberBankBean>>() {
                    @Override
                    public BaseData<NumberBankBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            NumberBankBean numberBankBean = gson.fromJson(object.toString(), NumberBankBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetInformationListSuccess(numberBankBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<NumberBankBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<NumberBankBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
