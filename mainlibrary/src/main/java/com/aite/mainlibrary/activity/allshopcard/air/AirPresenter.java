package com.aite.mainlibrary.activity.allshopcard.air;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AirMainListBean;
import com.aite.mainlibrary.Mainbean.TypeAirBean;
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

public class AirPresenter extends BasePresenterImpl<AirContract.View> implements AirContract.Presenter {

    @Override
    public void getDataList(HttpParams httpParams) {
        OkGo.<BaseData<AirMainListBean>>post(AppConstant.AIRLISTRECYURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<AirMainListBean>>() {
                    @Override
                    public BaseData<AirMainListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            AirMainListBean airMainListBean = BeanConvertor.convertBean(object.toString(), AirMainListBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onDataListSuccess(airMainListBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<AirMainListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<AirMainListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void getTypeDataList(HttpParams httpParams) {
        OkGo.<BaseData<TypeAirBean>>post(AppConstant.EALSETIMEAIRSERVICEMAINUIURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<TypeAirBean>>() {
                    @Override
                    public BaseData<TypeAirBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            TypeAirBean typeAirBean = gson.fromJson(object.toString(), TypeAirBean.class);

//                            TypeAirBean typeAirBean = BeanConvertor.convertBean(object.toString(), TypeAirBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onTypeDatasucess(typeAirBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<TypeAirBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<TypeAirBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
