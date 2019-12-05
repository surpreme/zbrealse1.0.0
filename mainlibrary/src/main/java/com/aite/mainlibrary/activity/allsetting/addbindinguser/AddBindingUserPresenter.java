package com.aite.mainlibrary.activity.allsetting.addbindinguser;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
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

public class AddBindingUserPresenter extends BasePresenterImpl<AddBindingUserContract.View> implements AddBindingUserContract.Presenter {

    @Override
    public void postBindUser(HttpParams httpParams) {
        OkGo.<BaseData<TwoSuccessCodeBean>>post(AppConstant.SAVEBINDINGUSERURL)
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
                        }
                        AddbinduserfamilyBean addbinduserfamilyBean = BeanConvertor.convertBean(jsonObject.toString(), AddbinduserfamilyBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onPostBindUsersuccess(addbinduserfamilyBean));


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
    public void getBindUserfamily(HttpParams httpParams) {
        OkGo.<BaseData<AddbinduserfamilyBean>>post(AppConstant.GETFIMILYBINDINGUSERURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<AddbinduserfamilyBean>>() {
                    @Override
                    public BaseData<AddbinduserfamilyBean> convertResponse(okhttp3.Response response) throws Throwable {
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


                        Gson gson = new Gson();
                        AddbinduserfamilyBean addbinduserfamilyBean = gson.fromJson(jsonObject.toString(), AddbinduserfamilyBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetBindUserfamilysuccess(addbinduserfamilyBean));
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<AddbinduserfamilyBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<AddbinduserfamilyBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }
}
