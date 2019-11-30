package com.aite.mainlibrary.activity.allshopcard.helpdoctorinformation;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.HelpDoctorInformationBean;
import com.aite.mainlibrary.Mainbean.HelpDoctorListBean;
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

public class HelpDoctorInformationPresenter extends BasePresenterImpl<HelpDoctorInformationContract.View> implements HelpDoctorInformationContract.Presenter {

    @Override
    public void getInformation(HttpParams httpParams) {
        OkGo.<BaseData<HelpDoctorInformationBean>>get(AppConstant.INFORMATIONHELPDOCTORNEEDURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HelpDoctorInformationBean>>() {
                    @Override
                    public BaseData<HelpDoctorInformationBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            HelpDoctorInformationBean helpDoctorInformationBean = BeanConvertor.convertBean(object.toString(), HelpDoctorInformationBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetInformationSuccess(helpDoctorInformationBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HelpDoctorInformationBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HelpDoctorInformationBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
