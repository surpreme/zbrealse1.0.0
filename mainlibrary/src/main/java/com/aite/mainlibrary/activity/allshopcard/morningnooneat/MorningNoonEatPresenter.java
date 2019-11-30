package com.aite.mainlibrary.activity.allshopcard.morningnooneat;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.MainUiDataBean;
import com.aite.mainlibrary.Mainbean.MorningNoonEatBean;
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

public class MorningNoonEatPresenter extends BasePresenterImpl<MorningNoonEatContract.View> implements MorningNoonEatContract.Presenter{

    @Override
    public void getEatDataList(HttpParams httpParams,String type) {
        //1 早上 post
        if (type.equals("1")){
            OkGo.<BaseData<MorningNoonEatBean>>post(AppConstant.MORNINGNOONTHINGLISTURL)
                    .tag(mView.getContext())
                    .params(httpParams)
                    .execute(new AbsCallback<BaseData<MorningNoonEatBean>>() {
                        @Override
                        public BaseData<MorningNoonEatBean> convertResponse(okhttp3.Response response) throws Throwable {
                            LogUtils.d(response.request());
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                            if (baseData.getDatas().getError() != null) {
                                mView.showError(baseData.getDatas().getError());
                                return null;
                            } else {
                                JSONObject object = jsonObject.optJSONObject("datas");
                                MorningNoonEatBean morningNoonEatBean = BeanConvertor.convertBean(object.toString(), MorningNoonEatBean.class);
                                ((Activity)mView.getContext()).runOnUiThread(()
                                        -> mView.getDataListSuccess(morningNoonEatBean));
                            }




                            return null;
                        }

                        @Override
                        public void onStart(Request<BaseData<MorningNoonEatBean>, ? extends Request> request) {
                            LogUtils.d("onStart");

                        }

                        @Override
                        public void onSuccess(Response<BaseData<MorningNoonEatBean>> response) {
                            LogUtils.d("onSuccess");

                        }
                    });
            //2 中午 get
        }else {
            OkGo.<BaseData<MorningNoonEatBean>>get(AppConstant.MORNINGNOONTHINGLISTURL)
                    .tag(mView.getContext())
                    .params(httpParams)
                    .execute(new AbsCallback<BaseData<MorningNoonEatBean>>() {
                        @Override
                        public BaseData<MorningNoonEatBean> convertResponse(okhttp3.Response response) throws Throwable {
                            LogUtils.d(response.request());
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                            if (baseData.getDatas().getError() != null) {
                                mView.showError(baseData.getDatas().getError());
                                return null;
                            } else {
                                JSONObject object = jsonObject.optJSONObject("datas");
                                MorningNoonEatBean morningNoonEatBean = BeanConvertor.convertBean(object.toString(), MorningNoonEatBean.class);
                                ((Activity)mView.getContext()).runOnUiThread(()
                                        -> mView.getDataListSuccess(morningNoonEatBean));
                            }




                            return null;
                        }

                        @Override
                        public void onStart(Request<BaseData<MorningNoonEatBean>, ? extends Request> request) {
                            LogUtils.d("onStart");

                        }

                        @Override
                        public void onSuccess(Response<BaseData<MorningNoonEatBean>> response) {
                            LogUtils.d("onSuccess");

                        }
                    });
        }

    }
}
