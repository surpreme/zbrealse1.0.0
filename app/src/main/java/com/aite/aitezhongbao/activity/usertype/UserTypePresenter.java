package com.aite.aitezhongbao.activity.usertype;

import com.aite.aitezhongbao.app.App;
import com.aite.aitezhongbao.bean.SureFindPasswordCodeBean;
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

public class UserTypePresenter extends BasePresenterImpl<UserTypeContract.View> implements UserTypeContract.Presenter{

    @Override
    public void ChoiceHeght(HttpParams httpParams) {
        OkGo.<BaseData<SureFindPasswordCodeBean>>post(AppConstant.THREENEWUSERFIRSTURL)
                .tag(App.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<SureFindPasswordCodeBean>>() {
                    @Override
                    public BaseData<SureFindPasswordCodeBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            SureFindPasswordCodeBean sureFindPasswordCodeBean = BeanConvertor.convertBean(object.toString(), SureFindPasswordCodeBean.class);
                            mView.setChoiceHeghtSuccess(sureFindPasswordCodeBean);

                        }

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<SureFindPasswordCodeBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<SureFindPasswordCodeBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
