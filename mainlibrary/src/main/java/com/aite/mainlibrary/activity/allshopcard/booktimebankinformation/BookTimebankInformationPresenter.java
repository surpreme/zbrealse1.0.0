package com.aite.mainlibrary.activity.allshopcard.booktimebankinformation;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.HelpDoctorInformationBean;
import com.aite.mainlibrary.Mainbean.StateCodeBean;
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
 *  邮箱 784787081@qq.com
 */

public class BookTimebankInformationPresenter extends BasePresenterImpl<BookTimebankInformationContract.View> implements BookTimebankInformationContract.Presenter{

    @Override
    public void getInformation(HttpParams httpParams) {
//        OkGo.<BaseData<HelpDoctorInformationBean>>get(AppConstant.BOOKINFORMATIONTIMEBANKURL)
//                .tag(mView.getContext())
//                .params(httpParams)
//                .execute(new AbsCallback<BaseData<HelpDoctorInformationBean>>() {
//                    @Override
//                    public BaseData<HelpDoctorInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
//                        LogUtils.d(response.request());
//                        JSONObject jsonObject = new JSONObject(response.body().string());
//                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
//                        if (baseData.getDatas().getError() != null) {
//                            mView.showError(baseData.getDatas().getError());
//                            return null;
//                        } else {
//                            JSONObject object = jsonObject.optJSONObject("datas");
//                            Gson gson=new Gson();
//                            HelpDoctorInformationBean helpDoctorInformationBean = gson.fromJson(object.toString(), HelpDoctorInformationBean.class);
////                            HelpDoctorInformationBean helpDoctorInformationBean = BeanConvertor.convertBean(object.toString(), HelpDoctorInformationBean.class);
//                            ((Activity) mView.getContext()).runOnUiThread(()
//                                    -> mView.onGetInformationSuccess(helpDoctorInformationBean));
//                        }
//
//
//                        return null;
//                    }
//
//                    @Override
//                    public void onStart(Request<BaseData<HelpDoctorInformationBean>, ? extends Request> request) {
//                        LogUtils.d("onStart");
//
//                    }
//
//                    @Override
//                    public void onSuccess(Response<BaseData<HelpDoctorInformationBean>> response) {
//                        LogUtils.d("onSuccess");
//
//                    }
//                });
    }

    @Override
    public void getInformation(HttpParams httpParams, String mUrl) {
        OkGo.<BaseData<HelpDoctorInformationBean>>get(mUrl)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HelpDoctorInformationBean>>() {
                    @Override
                    public BaseData<HelpDoctorInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson=new Gson();
                            HelpDoctorInformationBean helpDoctorInformationBean = gson.fromJson(object.toString(), HelpDoctorInformationBean.class);
//                            HelpDoctorInformationBean helpDoctorInformationBean = BeanConvertor.convertBean(object.toString(), HelpDoctorInformationBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetInformationSuccess(helpDoctorInformationBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HelpDoctorInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HelpDoctorInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void StartService(HttpParams httpParams) {
//        OkGo.<BaseData<HelpDoctorInformationBean>>post(AppConstant.STARTHELPTIMEBANKURL)
//                .tag(mView.getContext())
//                .params(httpParams)
//                .execute(new AbsCallback<BaseData<HelpDoctorInformationBean>>() {
//                    @Override
//                    public BaseData<HelpDoctorInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
//                        LogUtils.d(response.request());
//                        JSONObject jsonObject = new JSONObject(response.body().string());
//                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
//                        if (baseData.getDatas().getError() != null) {
//                            mView.showError(baseData.getDatas().getError());
//                            return null;
//                        } else {
//                            JSONObject object = jsonObject.optJSONObject("datas");
//                            StateCodeBean stateCodeBean = BeanConvertor.convertBean(object.toString(), StateCodeBean.class);
//                            ((Activity) mView.getContext()).runOnUiThread(()
//                                    -> mView.onStartServiceSuccess(stateCodeBean));
//                        }
//
//
//                        return null;
//                    }
//
//                    @Override
//                    public void onStart(Request<BaseData<HelpDoctorInformationBean>, ? extends Request> request) {
//                        LogUtils.d("onStart");
//
//                    }
//
//                    @Override
//                    public void onSuccess(Response<BaseData<HelpDoctorInformationBean>> response) {
//                        LogUtils.d("onSuccess");
//
//                    }
//                });


    }

    @Override
    public void StartService(HttpParams httpParams, String mUrl) {
        OkGo.<BaseData<HelpDoctorInformationBean>>post(mUrl)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HelpDoctorInformationBean>>() {
                    @Override
                    public BaseData<HelpDoctorInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            StateCodeBean stateCodeBean = BeanConvertor.convertBean(object.toString(), StateCodeBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onStartServiceSuccess(stateCodeBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HelpDoctorInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HelpDoctorInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });


    }
}
