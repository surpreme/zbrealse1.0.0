package com.aite.mainlibrary.activity.allshopcard.helpdoctor;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.ElseHelpDoctorBean;
import com.aite.mainlibrary.Mainbean.ElseTimeBankListBean;
import com.aite.mainlibrary.Mainbean.HelpDoctorListBean;
import com.aite.mainlibrary.Mainbean.TimeBankListBean;
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

public class HelpdoctorPresenter extends BasePresenterImpl<HelpdoctorContract.View> implements HelpdoctorContract.Presenter {

    @Override
    public void getList(HttpParams httpParams) {
        OkGo.<BaseData<HelpDoctorListBean>>get(AppConstant.LISTHELPDOCTORNEEDURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<HelpDoctorListBean>>() {
                    @Override
                    public BaseData<HelpDoctorListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            HelpDoctorListBean helpDoctorListBean = BeanConvertor.convertBean(object.toString(), HelpDoctorListBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetListSuccess(helpDoctorListBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<HelpDoctorListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<HelpDoctorListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }

    /**
     * 广告信息 分类
     *
     * @param httpParams
     */
    @Override
    public void getTypeData(HttpParams httpParams) {
        OkGo.<BaseData<ElseHelpDoctorBean>>post(AppConstant.LELSEHELPDOCTORNEEDURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<ElseHelpDoctorBean>>() {
                    @Override
                    public BaseData<ElseHelpDoctorBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            Gson gson = new Gson();
                            ElseHelpDoctorBean elseHelpDoctorBean = gson.fromJson(object.toString(), ElseHelpDoctorBean.class);
//                            ElseHelpDoctorBean elseHelpDoctorBean = BeanConvertor.convertBean(object.toString(), ElseHelpDoctorBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetTypeDataSuccess(elseHelpDoctorBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<ElseHelpDoctorBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<ElseHelpDoctorBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }


}
