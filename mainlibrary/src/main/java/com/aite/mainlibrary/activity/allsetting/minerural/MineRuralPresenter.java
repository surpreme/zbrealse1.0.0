package com.aite.mainlibrary.activity.allsetting.minerural;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.AirMainListBean;
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

public class MineRuralPresenter extends BasePresenterImpl<MineRuralContract.View> implements MineRuralContract.Presenter{

    @Override
    public void GetMineList(HttpParams httpParams) {
        OkGo.<BaseData<AirMainListBean>>get(AppConstant.MINECOLLECTPOSTLIST)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<AirMainListBean>>() {
                    @Override
                    public BaseData<AirMainListBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            AirMainListBean airMainListBean = BeanConvertor.convertBean(object.toString(), AirMainListBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetMineListSuccess(airMainListBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<AirMainListBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<AirMainListBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });
    }
}
