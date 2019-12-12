package com.aite.mainlibrary.activity.allshopcard.timebankrules;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.TimeBankListBean;
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

public class TimeBankRulesPresenter extends BasePresenterImpl<TimeBankRulesContract.View> implements TimeBankRulesContract.Presenter{

    @Override
    public void getRulesSuccess(HttpParams httpParams) {
        OkGo.<BaseData<TimeBankListBean>>get(AppConstant.RULESNUMBERINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<TimeBankListBean>>() {
                    @Override
                    public BaseData<TimeBankListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                           String url=object.getString("url");
//                            TimeBankListBean timeBankListBean = gson.fromJson(object.toString(), TimeBankListBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetRulesSuccess(url));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<TimeBankListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<TimeBankListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }
}
