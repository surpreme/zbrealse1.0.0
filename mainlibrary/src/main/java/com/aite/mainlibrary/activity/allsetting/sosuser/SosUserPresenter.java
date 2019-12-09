package com.aite.mainlibrary.activity.allsetting.sosuser;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.BinderSosUserListBean;
import com.aite.mainlibrary.Mainbean.BinderUserListBean;
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
 *  邮箱 784787081@qq.com
 */

public class SosUserPresenter extends BasePresenterImpl<SosUserContract.View> implements SosUserContract.Presenter{

    @Override
    public void getUserSoslistInformation(HttpParams httpParams) {
        OkGo.<BaseData<BinderSosUserListBean>>get(AppConstant.LISTSOSPEPPLEINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<BinderSosUserListBean>>() {
                    @Override
                    public BaseData<BinderSosUserListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
                        JSONObject object = jsonObject.optJSONObject("datas");
                        Gson gson=new Gson();

                        BinderSosUserListBean binderUserListBean=gson.fromJson(object.toString(),BinderSosUserListBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetSoslistUserInformation(binderUserListBean));
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<BinderSosUserListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<BinderSosUserListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
