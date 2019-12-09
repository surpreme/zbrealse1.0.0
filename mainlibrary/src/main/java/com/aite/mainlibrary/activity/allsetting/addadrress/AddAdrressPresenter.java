package com.aite.mainlibrary.activity.allsetting.addadrress;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
import com.aite.mainlibrary.Mainbean.AllAreaBean;
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

public class AddAdrressPresenter extends BasePresenterImpl<AddAdrressContract.View> implements AddAdrressContract.Presenter {

    @Override
    public void postMsg(HttpParams httpParams) {
        OkGo.<BaseData<AddbinduserfamilyBean>>post(AppConstant.ADDADDRESSDATAURL)
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
                        JSONObject object = jsonObject.optJSONObject("datas");
                        String dataresult=object.optString("address_id");
//                        Gson gson = new Gson();
//                        AddbinduserfamilyBean addbinduserfamilyBean = gson.fromJson(jsonObject.toString(), AddbinduserfamilyBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onPostMsgSuccess(dataresult));
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
}
