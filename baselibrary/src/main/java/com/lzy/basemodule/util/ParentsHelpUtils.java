package com.lzy.basemodule.util;

import android.content.Context;
import android.content.Intent;

import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.bean.BaseData;
import com.lzy.basemodule.bean.BeanConvertor;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.basemodule.mvp.BaseView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-06
 * @desc:
 */
public class ParentsHelpUtils {
    public static void isLogIn(String response, Context context) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        String login = jsonObject.optString("login", jsonObject.toString());
        if (login.equals("0")) {
            LogUtils.d(login);
            Intent intent = new Intent();
            intent.setAction("com.aite.aitezhongbao.app.activity.login.LoginActivity");
            AppManager.getInstance().killAllActivity();
            context.startActivity(intent);
        }
    }
    public static void isError(String response, BaseView mView) throws JSONException {
        BaseData baseData = BeanConvertor.convertBean(response, BaseData.class);
        if (baseData.getDatas().getError() != null) {
            mView.showError(baseData.getDatas().getError());
        }

    }
}
