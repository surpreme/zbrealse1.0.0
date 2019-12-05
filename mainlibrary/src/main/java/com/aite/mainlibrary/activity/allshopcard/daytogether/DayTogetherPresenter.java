package com.aite.mainlibrary.activity.allshopcard.daytogether;

import android.app.Activity;
import android.content.Intent;

import com.aite.mainlibrary.Mainbean.AirMainListBean;
import com.aite.mainlibrary.Mainbean.DayTogetherChoiceBean;
import com.aite.mainlibrary.Mainbean.LessDayBean;
import com.aite.mainlibrary.Mainbean.RecyChoiceUIBean;
import com.google.gson.Gson;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.bean.BaseData;
import com.lzy.basemodule.bean.BeanConvertor;
import com.lzy.basemodule.bean.EtraLoginBaseData;
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

public class DayTogetherPresenter extends BasePresenterImpl<DayTogetherContract.View> implements DayTogetherContract.Presenter {

    @Override
    public void getListMsg(HttpParams httpParams) {
        OkGo.<BaseData<LessDayBean>>get(AppConstant.ALLLESSBODYLISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<LessDayBean>>() {
                    @Override
                    public BaseData<LessDayBean> convertResponse(okhttp3.Response response) throws Throwable {
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
                        LessDayBean lessDayBean = BeanConvertor.convertBean(object.toString(), LessDayBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetListSuccess(lessDayBean));

                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<LessDayBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<LessDayBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void getChoiceInformation(HttpParams httpParams) {
        OkGo.<BaseData<DayTogetherChoiceBean>>get(AppConstant.CHOICEMSGLESSBODYLISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<DayTogetherChoiceBean>>() {
                    @Override
                    public BaseData<DayTogetherChoiceBean> convertResponse(okhttp3.Response response) throws Throwable {
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
                        Gson gson = new Gson();
                        DayTogetherChoiceBean dayTogetherChoiceBean = gson.fromJson(object.toString(), DayTogetherChoiceBean.class);

//                        DayTogetherChoiceBean dayTogetherChoiceBean = BeanConvertor.convertBean(object.toString(), DayTogetherChoiceBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetchoiceInformationSuccess(dayTogetherChoiceBean));

                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<DayTogetherChoiceBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<DayTogetherChoiceBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}


