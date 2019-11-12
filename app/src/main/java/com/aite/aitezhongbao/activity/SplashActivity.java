package com.aite.aitezhongbao.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.aite.aitezhongbao.R;
import com.aite.aitezhongbao.activity.login.LoginActivity;
import com.lzy.basemodule.androidlife.AppManager;
import com.lzy.basemodule.view.StatusBarUtils;
import com.lzy.basemodule.base.BaseActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.spalsh_img)
    ImageView spalsh_img;

    @Override
    protected int getLayoutResId() {
        return R.layout.splash_layout;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setTransparent(context);
        WaitSplash splash = new WaitSplash();
        splash.execute();

    }

    private class WaitSplash extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            SharedPreferences sharedPreferences = context.getSharedPreferences("share", MODE_PRIVATE);
            boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (isFirstRun) {
                startActivity(WelcomeActivity.class);
                editor.putBoolean("isFirstRun", false);
                editor.apply();
            } else {
                startActivity(LoginActivity.class);
            }
            AppManager.getInstance().killActivity(SplashActivity.class);
        }
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }
}
