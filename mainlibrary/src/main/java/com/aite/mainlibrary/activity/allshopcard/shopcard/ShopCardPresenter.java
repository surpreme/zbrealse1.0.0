package com.aite.mainlibrary.activity.allshopcard.shopcard;

import android.app.Activity;
import android.content.Intent;

import com.aite.mainlibrary.Mainbean.LessAddShopCardNumberBean;
import com.aite.mainlibrary.Mainbean.RecyChoiceUIBean;
import com.aite.mainlibrary.Mainbean.ShopCardlistBean;
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

public class ShopCardPresenter extends BasePresenterImpl<ShopCardContract.View> implements ShopCardContract.Presenter {

    @Override
    public void getShopCardList(HttpParams httpParams) {
        OkGo.<BaseData<ShopCardlistBean>>get(AppConstant.SHOPCARDLISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<ShopCardlistBean>>() {
                    @Override
                    public BaseData<ShopCardlistBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            ShopCardlistBean shopCardlistBean = BeanConvertor.convertBean(object.toString(), ShopCardlistBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onShopCardListSuccess(shopCardlistBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<ShopCardlistBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ShopCardlistBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void deleteShopCardItem(HttpParams httpParams) {
        OkGo.<BaseData<ShopCardlistBean>>post(AppConstant.DELETESHOPCARDLISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<ShopCardlistBean>>() {
                    @Override
                    public BaseData<ShopCardlistBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String datas = jsonObject.optString("datas", jsonObject.toString());
                        LogUtils.e(datas);
                        String login = jsonObject.optString("login", jsonObject.toString());
                        if (login.equals("0")) {
                            LogUtils.d(login);
                            Intent intent = new Intent();
                            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
                            AppManager.getInstance().killAllActivity();
                            mView.getContext().startActivity(intent);
                        }
                        if (datas.equals("1")) {
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.ondeleteShopCardItemSuccess(datas));
                        }

                        EtraLoginBaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), EtraLoginBaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<ShopCardlistBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ShopCardlistBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void addlessShopThingNumber(HttpParams httpParams) {
        OkGo.<BaseData<LessAddShopCardNumberBean>>post(AppConstant.ADDLESSSHOPCARDLISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<LessAddShopCardNumberBean>>() {
                    @Override
                    public BaseData<LessAddShopCardNumberBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject sucessobject=jsonObject.optJSONObject("datas");
                        LessAddShopCardNumberBean lessAddShopCardNumberBean = BeanConvertor.convertBean(sucessobject.toString(), LessAddShopCardNumberBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onlessShopThingNumberSuccess(lessAddShopCardNumberBean));
                        String datas = jsonObject.optString("datas", jsonObject.toString());
                        LogUtils.e(datas);
                        String login = jsonObject.optString("login", jsonObject.toString());
                        if (login.equals("0")) {
                            LogUtils.d(login);
                            Intent intent = new Intent();
                            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
                            AppManager.getInstance().killAllActivity();
                            mView.getContext().startActivity(intent);
                        }
                        EtraLoginBaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), EtraLoginBaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<LessAddShopCardNumberBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<LessAddShopCardNumberBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
