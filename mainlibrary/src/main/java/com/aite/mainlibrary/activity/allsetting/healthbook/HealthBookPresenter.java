package com.aite.mainlibrary.activity.allsetting.healthbook;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
import com.aite.mainlibrary.Mainbean.HealthbookMainBean;
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

public class HealthBookPresenter extends BasePresenterImpl<HealthBookContract.View> implements HealthBookContract.Presenter {

    @Override
    public void getInformationList(HttpParams httpParams) {
        OkGo.<BaseData<HealthbookMainBean>>get(AppConstant.GETHEALTHINFORMATIONDATAURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HealthbookMainBean>>() {
                    @Override
                    public BaseData<HealthbookMainBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        try {
                            BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                            if (baseData.getDatas().getError() != null) {
                                mView.showError(baseData.getDatas().getError());
                            }

                        } catch (Exception e) {
                            LogUtils.e(e);
                        }
                        JSONObject object = jsonObject.optJSONObject("datas");
                        Gson gson = new Gson();
                        HealthbookMainBean healthbookMainBean = gson.fromJson(object.toString(), HealthbookMainBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetInformationListSuccess(healthbookMainBean));
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HealthbookMainBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HealthbookMainBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void onPostInformation(HttpParams httpParams) {
        OkGo.<BaseData<HealthbookMainBean>>post(AppConstant.MIANADDHEALTHINFORMATIONDATAURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HealthbookMainBean>>() {
                    @Override
                    public BaseData<HealthbookMainBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        try {
                            BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                            if (baseData.getDatas().getError() != null) {
                                mView.showError(baseData.getDatas().getError());
                            }

                        } catch (Exception e) {
                            LogUtils.e(e);
                        }
                       String resultInforamtion=jsonObject.optString("datas");
//                        Gson gson=new Gson();
//                        HealthbookMainBean healthbookMainBean = gson.fromJson(jsonObject.toString(), HealthbookMainBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onPostInformationSuccess(resultInforamtion));
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HealthbookMainBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HealthbookMainBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
