package com.aite.mainlibrary.activity.allshopcard.remembershopbook;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.MoreAdressInormationBean;
import com.aite.mainlibrary.Mainbean.MorningNoonEatBean;
import com.aite.mainlibrary.Mainbean.PayListBean;
import com.aite.mainlibrary.Mainbean.RememberFoodInformationBean;
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

public class RememberShopBookPresenter extends BasePresenterImpl<RememberShopBookContract.View> implements RememberShopBookContract.Presenter {

    @Override
    public void getFoodInformation(HttpParams httpParams) {
        OkGo.<BaseData<MorningNoonEatBean>>post(AppConstant.REMEMBERFOODINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<MorningNoonEatBean>>() {
                    @Override
                    public BaseData<MorningNoonEatBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            RememberFoodInformationBean rememberFoodInformationBean = BeanConvertor.convertBean(object.toString(), RememberFoodInformationBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetFoodInformationSuccess(rememberFoodInformationBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<MorningNoonEatBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<MorningNoonEatBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void postAllInformation(HttpParams httpParams) {
        OkGo.<BaseData<MorningNoonEatBean>>post(AppConstant.SUREREMEMBERFOODINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<MorningNoonEatBean>>() {
                    @Override
                    public BaseData<MorningNoonEatBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            //订单id
                            String resultString = object.optString("order_id");
//                            RememberFoodInformationBean rememberFoodInformationBean = BeanConvertor.convertBean(object.toString(), RememberFoodInformationBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.postAllInformationSuccess(resultString));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<MorningNoonEatBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<MorningNoonEatBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void getAddress(HttpParams httpParams) {
        OkGo.<BaseData<MoreAdressInormationBean>>post(AppConstant.INFORMATIONADDRESSPERSONDATAURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<MoreAdressInormationBean>>() {
                    @Override
                    public BaseData<MoreAdressInormationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            MoreAdressInormationBean moreAdressInormationBean = gson.fromJson(object.toString(), MoreAdressInormationBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetAddressSuccess(moreAdressInormationBean));
                        }

//                        dfadsg

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<MoreAdressInormationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<MoreAdressInormationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void getPayList(HttpParams httpParams) {
        OkGo.<BaseData<PayListBean>>get(AppConstant.PAYAWYGETINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<PayListBean>>() {
                    @Override
                    public BaseData<PayListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        try {
                            BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                            if (baseData.getDatas().getError() != null) {
                                mView.showError(baseData.getDatas().getError());
                                return null;
                            } else {
//                            JSONObject object = jsonObject.optJSONObject("datas");

                            }

                        } catch (Exception e) {

                        }

                        Gson gson = new Gson();
                        PayListBean payListBean = gson.fromJson(jsonObject.toString(), PayListBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onPayListSuccess(payListBean));
//                        dfadsg

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<PayListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<PayListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
