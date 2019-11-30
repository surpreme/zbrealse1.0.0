package com.aite.mainlibrary.activity.allshopcard.choiceeat;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.aite.mainlibrary.Mainbean.AddShopCardBean;
import com.aite.mainlibrary.Mainbean.ShopCardlistBean;
import com.aite.mainlibrary.Mainbean.TypeChoiceUIBean;
import com.aite.mainlibrary.Mainbean.RecyChoiceUIBean;
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

public class ChoiceEatPresenter extends BasePresenterImpl<ChoiceEatContract.View> implements ChoiceEatContract.Presenter {

    @Override
    public void getDatalist(HttpParams httpParams) {
        OkGo.<BaseData<TypeChoiceUIBean>>get(AppConstant.CHOOCELISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<TypeChoiceUIBean>>() {
                    @Override
                    public BaseData<TypeChoiceUIBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        LogUtils.e(AppConstant.KEY);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            TypeChoiceUIBean choiceUIBean = BeanConvertor.convertBean(object.toString(), TypeChoiceUIBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.getDataSuccess(choiceUIBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<TypeChoiceUIBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<TypeChoiceUIBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void getScondDatalist(HttpParams httpParams) {
        OkGo.<BaseData<RecyChoiceUIBean>>post(AppConstant.CHOOCESENCONDLISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<RecyChoiceUIBean>>() {
                    @Override
                    public BaseData<RecyChoiceUIBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            RecyChoiceUIBean recyChoiceUIBean = BeanConvertor.convertBean(object.toString(), RecyChoiceUIBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.getScondDataSuccess(recyChoiceUIBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<RecyChoiceUIBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<RecyChoiceUIBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }

    @Override
    public void addShopCard(HttpParams httpParams) {
        OkGo.<BaseData<RecyChoiceUIBean>>post(AppConstant.ADDSHOPCARDURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<RecyChoiceUIBean>>() {
                    @Override
                    public BaseData<RecyChoiceUIBean> convertResponse(okhttp3.Response response) throws Throwable {
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
                                    -> mView.addShopCardSuccesss(datas));
                        }

                        EtraLoginBaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), EtraLoginBaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
//                        if (baseData.getLogin() != null) {
//                            LogUtils.d(baseData.getLogin());
////                            String object = jsonObject.optString(jsonObject.toString(), "datas");
////                            LogUtils.e(object);
////                            RecyChoiceUIBean recyChoiceUIBean = BeanConvertor.convertBean(object.toString(), RecyChoiceUIBean.class);
////                            ((Activity) mView.getContext()).runOnUiThread(()
////                                    -> mView.getScondDataSuccess(recyChoiceUIBean));
//                        }
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<RecyChoiceUIBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<RecyChoiceUIBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    @Override
    public void getShopCard(HttpParams httpParams) {
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
                                    -> mView.onShopCardSuccess(shopCardlistBean));
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

}
