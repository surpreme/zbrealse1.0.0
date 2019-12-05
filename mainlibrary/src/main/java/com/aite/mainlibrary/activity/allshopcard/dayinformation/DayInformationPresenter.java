package com.aite.mainlibrary.activity.allshopcard.dayinformation;

import android.app.Activity;
import android.content.Intent;

import com.aite.mainlibrary.Mainbean.LessBodyInformationBean;
import com.aite.mainlibrary.Mainbean.LessDayBean;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.androidlife.AppManager;
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

public class DayInformationPresenter extends BasePresenterImpl<DayInformationContract.View> implements DayInformationContract.Presenter {

    @Override
    public void onGetInformation(HttpParams httpParams) {
        OkGo.<BaseData<LessBodyInformationBean>>get(AppConstant.INFORMATIONALLLESSBODYLISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<LessBodyInformationBean>>() {
                    @Override
                    public BaseData<LessBodyInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String login = jsonObject.optString("login", jsonObject.toString());
                        if (login.equals("0")) {
                            LogUtils.d(login);
                            Intent intent = new Intent();
                            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
                            AppManager.getInstance().killAllActivity();
                            mView.getContext().startActivity(intent);
                        }
                        JSONObject object = jsonObject.optJSONObject("datas");
                        LessBodyInformationBean lessBodyInformationBean = BeanConvertor.convertBean(object.toString(), LessBodyInformationBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetInformationSuccess(lessBodyInformationBean));

                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<LessBodyInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<LessBodyInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
