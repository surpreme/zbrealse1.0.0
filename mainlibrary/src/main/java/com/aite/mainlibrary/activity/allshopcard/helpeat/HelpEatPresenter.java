package com.aite.mainlibrary.activity.allshopcard.helpeat;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.HelpEatUIBean;
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



public class HelpEatPresenter extends BasePresenterImpl<HelpEatContract.View> implements HelpEatContract.Presenter{

    @Override
    public void showUiData(HttpParams httpParams) {
        OkGo.<BaseData<HelpEatUIBean>>post(AppConstant.HELPEATUIURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HelpEatUIBean>>() {
                    @Override
                    public BaseData<HelpEatUIBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            HelpEatUIBean helpEatUIBean = BeanConvertor.convertBean(object.toString(), HelpEatUIBean.class);
                            ((Activity)mView.getContext()).runOnUiThread(()
                                    -> mView.getUiDataSuccess(helpEatUIBean));
                        }




                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HelpEatUIBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HelpEatUIBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}