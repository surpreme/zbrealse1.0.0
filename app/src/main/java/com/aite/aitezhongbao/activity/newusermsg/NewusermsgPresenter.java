package com.aite.aitezhongbao.activity.newusermsg;

import android.app.Activity;
import com.aite.aitezhongbao.app.App;
import com.aite.aitezhongbao.bean.FirstNewUserBean;
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

public class NewusermsgPresenter extends BasePresenterImpl<NewusermsgContract.View> implements NewusermsgContract.Presenter{

    @Override
    public void sureAllmsg(HttpParams httpParams) {
        OkGo.<BaseData<FirstNewUserBean>>post(AppConstant.TWONEWUSERFIRSTURL)
                .tag(App.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<FirstNewUserBean>>() {
                    @Override
                    public BaseData<FirstNewUserBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            SureFindPasswordCodeBean sureFindPasswordCodeBean = BeanConvertor.convertBean(object.toString(), SureFindPasswordCodeBean.class);
                            ((Activity)mView.getContext()).runOnUiThread(()
                                    ->mView.sureAllmsgSuccess(sureFindPasswordCodeBean.getState()));
                        }

                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<FirstNewUserBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<FirstNewUserBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });

    }
}
