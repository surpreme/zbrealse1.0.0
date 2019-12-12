package com.aite.mainlibrary.activity.allshopcard.numbershop;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.NumberBankInformationBean;
import com.aite.mainlibrary.Mainbean.TimeShoplistBean;
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

public class NumberShopPresenter extends BasePresenterImpl<NumberShopContract.View> implements NumberShopContract.Presenter{

    @Override
    public void GetShopList(HttpParams httpParams) {
        OkGo.<BaseData<TimeShoplistBean>>get(AppConstant.NUMBERSHOPLISTINFORMATIONURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<TimeShoplistBean>>() {
                    @Override
                    public BaseData<TimeShoplistBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            TimeShoplistBean timeShoplistBean = gson.fromJson(object.toString(), TimeShoplistBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetShopListSuccess(timeShoplistBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<TimeShoplistBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<TimeShoplistBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
