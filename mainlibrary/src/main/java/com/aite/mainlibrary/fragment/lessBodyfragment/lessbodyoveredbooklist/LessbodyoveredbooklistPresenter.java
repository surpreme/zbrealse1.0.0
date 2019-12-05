package com.aite.mainlibrary.fragment.lessBodyfragment.lessbodyoveredbooklist;

import android.app.Activity;

import com.aite.mainlibrary.Mainbean.BookLessBodyFamilyBean;
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

public class LessbodyoveredbooklistPresenter extends BasePresenterImpl<LessbodyoveredbooklistContract.View> implements LessbodyoveredbooklistContract.Presenter{

    @Override
    public void getinformation(HttpParams httpParams) {
        OkGo.<BaseData<BookLessBodyFamilyBean>>get(AppConstant.BOOKINFORMATIONLISTMINELISTURL)
                .tag(mView.getContext())
                .params(httpParams)
                .execute(new AbsCallback<BaseData<BookLessBodyFamilyBean>>() {
                    @Override
                    public BaseData<BookLessBodyFamilyBean> convertResponse(okhttp3.Response response) throws Throwable {
                        LogUtils.d(response.request());
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        BaseData baseData = BeanConvertor.convertBean(jsonObject.toString(), BaseData.class);
                        if (baseData.getDatas().getError() != null) {
                            mView.showError(baseData.getDatas().getError());
                            return null;
                        } else {
                            JSONObject object = jsonObject.optJSONObject("datas");
                            BookLessBodyFamilyBean bookLessBodyFamilyBean = BeanConvertor.convertBean(object.toString(), BookLessBodyFamilyBean.class);
                            ((Activity) mView.getContext()).runOnUiThread(()
                                    -> mView.onGetinformationSuccess(bookLessBodyFamilyBean));
                        }


                        return null;
                    }

                    @Override
                    public void onStart(Request<BaseData<BookLessBodyFamilyBean>, ? extends Request> request) {
                        LogUtils.d("onStart");

                    }

                    @Override
                    public void onSuccess(Response<BaseData<BookLessBodyFamilyBean>> response) {
                        LogUtils.d("onSuccess");

                    }
                });


    }
}
