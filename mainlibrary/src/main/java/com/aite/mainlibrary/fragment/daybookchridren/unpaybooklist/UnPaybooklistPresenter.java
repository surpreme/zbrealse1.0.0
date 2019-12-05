package com.aite.mainlibrary.fragment.daybookchridren.unpaybooklist;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.BookMorningNoonEatBean;
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

public class UnPaybooklistPresenter extends BasePresenterImpl<UnPaybooklistContract.View> implements UnPaybooklistContract.Presenter {

    @Override
    public void getinformation(HttpParams httpParams) {
        OkGo.<BaseData<BookMorningNoonEatBean>>get(AppConstant.MINEALLBOOKURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<BookMorningNoonEatBean>>() {
                    @Override
                    public BaseData<BookMorningNoonEatBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            BookMorningNoonEatBean bookMorningNoonEatBean = BeanConvertor.convertBean(object.toString(), BookMorningNoonEatBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetinformationSuccess(bookMorningNoonEatBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<BookMorningNoonEatBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<BookMorningNoonEatBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });


    }
}
