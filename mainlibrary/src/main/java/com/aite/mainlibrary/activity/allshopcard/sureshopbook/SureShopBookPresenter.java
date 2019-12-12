package com.aite.mainlibrary.activity.allshopcard.sureshopbook;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.BookInfprmationMorningNoonEatBean;
import com.aite.mainlibrary.Mainbean.MoreAdressInormationBean;
import com.aite.mainlibrary.Mainbean.MorningNoonEatBean;
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


public class SureShopBookPresenter extends BasePresenterImpl<SureShopBookContract.View> implements SureShopBookContract.Presenter {

    @Override
    public void getInformation(HttpParams httpParams) {
        OkGo.<BaseData<BookInfprmationMorningNoonEatBean>>get(AppConstant.ALLBOOKINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<BookInfprmationMorningNoonEatBean>>() {
                    @Override
                    public BaseData<BookInfprmationMorningNoonEatBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            BookInfprmationMorningNoonEatBean bookInfprmationMorningNoonEatBean = BeanConvertor.convertBean(object.toString(), BookInfprmationMorningNoonEatBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetInformationSuccess(bookInfprmationMorningNoonEatBean));
                        }

//                        dfadsg

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<BookInfprmationMorningNoonEatBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<BookInfprmationMorningNoonEatBean>> response) {
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
}
