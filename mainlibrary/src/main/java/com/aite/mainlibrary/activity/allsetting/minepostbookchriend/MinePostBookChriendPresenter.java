package com.aite.mainlibrary.activity.allsetting.minepostbookchriend;

import android.app.Activity;
import android.content.Intent;

import com.aite.mainlibrary.Mainbean.MineTogetherServiceBean;
import com.google.gson.Gson;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.androidlife.AppManager;
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

public class MinePostBookChriendPresenter extends BasePresenterImpl<MinePostBookChriendContract.View> implements MinePostBookChriendContract.Presenter {

    @Override
    public void getListInformation(String url, HttpParams httpParams) {
        OkGo.<BaseData<MineTogetherServiceBean>>get(url)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<MineTogetherServiceBean>>() {
                    @Override
                    public BaseData<MineTogetherServiceBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        String login = jsonObject.optString("login", jsonObject.toString());
                        if (login.equals("0")) {
                            LogUtils.d(login);
                            Intent intent = new Intent();
                            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
                            AppManager.getInstance().killAllActivity();
                            mView.getContext().startActivity(intent);
                        }
                        JSONObject object = jsonObject.optJSONObject("datas");
                        Gson gson = new Gson();
                        MineTogetherServiceBean mineTogetherServiceBean = gson.fromJson(object.toString(), MineTogetherServiceBean.class);

//                        DayTogetherChoiceBean dayTogetherChoiceBean = BeanConvertor.convertBean(object.toString(), DayTogetherChoiceBean.class);
                        ((Activity) mView.getContext()).runOnUiThread(()
                                -> mView.onGetListInformationSuccess(mineTogetherServiceBean));

                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        }
                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<MineTogetherServiceBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<MineTogetherServiceBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
