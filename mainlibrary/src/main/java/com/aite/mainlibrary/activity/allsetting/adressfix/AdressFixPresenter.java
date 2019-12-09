package com.aite.mainlibrary.activity.allsetting.adressfix;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AddbinduserfamilyBean;
import com.aite.mainlibrary.Mainbean.SettingAddressListBean;
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

public class AdressFixPresenter extends BasePresenterImpl<AdressFixContract.View> implements AdressFixContract.Presenter {

    @Override
    public void getAdressList(HttpParams httpParams) {
        OkGo.<BaseData<SettingAddressListBean>>get(AppConstant.LISTINFORMATIONADDRESSDATAURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<SettingAddressListBean>>() {
                    @Override
                    public BaseData<SettingAddressListBean> convertResponse(okhttp3.Response response) throws Throwable {
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
                        JSONObject object = jsonObject.optJSONObject("datas");
//                        String dataresult = object.optString("address_id");
                        Gson gson = new Gson();
                        SettingAddressListBean settingAddressListBean = gson.fromJson(object.toString(), SettingAddressListBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetAdressListSuccess(settingAddressListBean));
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<SettingAddressListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<SettingAddressListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
