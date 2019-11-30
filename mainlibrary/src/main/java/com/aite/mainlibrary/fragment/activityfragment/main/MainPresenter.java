package com.aite.mainlibrary.fragment.activityfragment.main;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.MainUiDataBean;
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

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{

    @Override
    public void showUiData(HttpParams httpParams) {
        OkGo.<BaseData<MainUiDataBean>>post(AppConstant.MAINUIDATAURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<MainUiDataBean>>() {
                    @Override
                    public BaseData<MainUiDataBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            MainUiDataBean firstNewUserBean = BeanConvertor.convertBean(object.toString(), MainUiDataBean.class);
                            ((Activity)mView.getContext()).runOnUiThread(()
                                    -> mView.getUiDataSuccess(firstNewUserBean));
                        }




                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<MainUiDataBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<MainUiDataBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }
}
