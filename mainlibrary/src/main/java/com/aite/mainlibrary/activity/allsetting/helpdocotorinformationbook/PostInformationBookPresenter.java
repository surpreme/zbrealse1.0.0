package com.aite.mainlibrary.activity.allsetting.helpdocotorinformationbook;

import android.app.Activity;
import android.content.Intent;

import com.aite.mainlibrary.Mainbean.HelpDoctorBookInformationBean;
import com.google.gson.Gson;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.bean.BaseData;
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

public class PostInformationBookPresenter extends BasePresenterImpl<PostInformationBookContract.View> implements PostInformationBookContract.Presenter {

    @Override
    public void getBookInforMation(String url, HttpParams httpParams) {
        OkGo.<BaseData<HelpDoctorBookInformationBean>>get(url)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HelpDoctorBookInformationBean>>() {
                    @Override
                    public BaseData<HelpDoctorBookInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String login = jsonObject.optString("login", jsonObject.toString());
                        Gson yy = new Gson();
                        BaseData baseData = yy.fromJson(response.body().string(), BaseData.class);

                        if (login.equals("0")) {
                            LogUtils.d(login);
                            Intent intent = new Intent();
                            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
                            AppManager.getInstance().killAllActivity();
                            mView.getContext().startActivity(intent);
                        }
                        JSONObject object = jsonObject.optJSONObject("datas");
                        Gson gson = new Gson();
                        HelpDoctorBookInformationBean helpDoctorBookInformationBean = gson.fromJson(object.toString(), HelpDoctorBookInformationBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetBookInforMationSuccess(helpDoctorBookInformationBean));
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HelpDoctorBookInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HelpDoctorBookInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });


    }
}
