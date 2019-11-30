package com.aite.aitezhongbao.activity.login;

import android.app.Activity;
import com.aite.aitezhongbao.app.App;
import com.aite.aitezhongbao.bean.FirstNewUserBean;
import com.aite.aitezhongbao.bean.LogInBean;
import com.aite.aitezhongbao.bean.SureFindPasswordCodeBean;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.bean.BaseData;
import com.lzy.basemodule.bean.BaseDataEtras;
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

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(HttpParams params) {
        OkGo.<BaseDataEtras<LogInBean>>post(AppConstant.LOGINURL)
                .tag(App.getContext())
                .params(params)
                .execute(new AbsCallback<BaseDataEtras<LogInBean>>() {
                    @Override
                    public BaseDataEtras<LogInBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseDataEtras baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseDataEtras.class);
                        LogUtils.d(baseData.getRegister_step() + "------" + baseData.getKey());
                        if (baseData.getRegister_step() != null || baseData.getKey() != null) {
                            if (baseData.getRegister_step().equals("2") || baseData.getRegister_step().equals("3"))
                                mView.logInNeedMoreMsg(baseData.getRegister_step(), baseData.getKey());
                        } else if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
//                            JSONObject registejson = jsonObject.optJSONObject("register_step");
//                            JSONObject keyjson = jsonObject.optJSONObject("key");
//                            LogUtils.d(registejson.toString() + "------" + keyjson.toString());
                            LogInBean logInBean = BeanConvertor.convertBean(jsonObject.toString(), LogInBean.class);
                            ((Activity)mView.getContext()).runOnUiThread(()
                                    -> mView.logInSuccess(logInBean));

//                            mView.setNewPasswordonSuccess(sureFindPasswordCodeBean);

                        }

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseDataEtras<LogInBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseDataEtras<LogInBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> o) {
//
////                if (name.equals("lzy") && password.equals("123456")) {
////                    try {
////                        Thread.sleep(500);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    } finally {
////                        o.onNext("ok");
////                    }
////
////                } else o.onComplete();
//            }
//        })
//                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
//                .subscribeOn(Schedulers.io())//执行在io线程
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        mView.logInSuccess(s);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        mView.logInFail("失败");
//                    }
//                });
    }


}