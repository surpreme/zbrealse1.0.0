package com.aite.mainlibrary.activity.allsetting.addhealthbook;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
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

public class AddHealthBookPresenter extends BasePresenterImpl<AddHealthBookContract.View> implements AddHealthBookContract.Presenter {

    @Override
    public void PostInformation(HttpParams httpParams) {
        OkGo.<BaseData<AddbinduserfamilyBean>>post(AppConstant.CHDRENADDHEALTHINFORMATIONDATAURL)
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
                       String resultInformation=jsonObject.optString("datas");
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onPostInformationSuccess(resultInformation));
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
