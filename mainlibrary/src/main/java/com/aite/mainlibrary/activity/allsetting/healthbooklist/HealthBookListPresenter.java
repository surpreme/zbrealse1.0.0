package com.aite.mainlibrary.activity.allsetting.healthbooklist;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
import com.aite.mainlibrary.Mainbean.HealthListBean;
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

public class HealthBookListPresenter extends BasePresenterImpl<HealthBookListContract.View> implements HealthBookListContract.Presenter {

    @Override
    public void GetInformationList(HttpParams httpParams) {
        OkGo.<BaseData<HealthListBean>>get(AppConstant.LISTMSGHEALTHINFORMATIONDATAURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HealthListBean>>() {
                    @Override
                    public BaseData<HealthListBean> convertResponse(okhttp3.Response response) throws Throwable {
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
                        Gson gson=new Gson();
                        HealthListBean healthListBean=gson.fromJson(jsonObject.toString(),HealthListBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetInformationListSuccess(healthListBean));
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HealthListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HealthListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
