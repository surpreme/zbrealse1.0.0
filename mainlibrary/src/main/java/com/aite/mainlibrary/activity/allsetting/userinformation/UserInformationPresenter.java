package com.aite.mainlibrary.activity.allsetting.userinformation;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.HelpDoctorInformationBean;
import com.aite.mainlibrary.Mainbean.TwoSuccessCodeBean;
import com.aite.mainlibrary.Mainbean.UseInformationBean;
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

public class UserInformationPresenter extends BasePresenterImpl<UserInformationContract.View> implements UserInformationContract.Presenter{

    @Override
    public void getUserInformation(HttpParams httpParams) {
        OkGo.<BaseData<UseInformationBean>>get(AppConstant.GETPEPPLEINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<UseInformationBean>>() {
                    @Override
                    public BaseData<UseInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
                        JSONObject object = jsonObject.optJSONObject("datas");
                        UseInformationBean useInformationBean = BeanConvertor.convertBean(object.toString(), UseInformationBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetUserInformation(useInformationBean));


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<UseInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<UseInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
