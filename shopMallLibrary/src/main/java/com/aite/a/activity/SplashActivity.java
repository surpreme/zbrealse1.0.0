package com.aite.a.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.aite.a.AppManager;
import com.aite.a.HomeTabActivity;
import com.aite.a.activity.li.activity.WelcomeActivity;
import com.aite.a.activity.li.bean.StartImgBean;
import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.BeanConvertor;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

/**
 * 开屏页
 *
 * @author Administrator
 */
public class SplashActivity extends BaseActivity {
    private Intent intent1, intent2;
    private SharedPreferences sp;
    private ImageView open_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppManager.getInstance().addActivity(this);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);
        // 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);
        findViewById();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private class WaitSplash extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            //跳转向导页面
            intent1 = new Intent(SplashActivity.this, WelcomeActivity.class);
            //跳转主界面
            intent2 = new Intent(SplashActivity.this, HomeTabActivity.class);
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
            sp = getSharedPreferences("config", MODE_PRIVATE);

            boolean update = sp.getBoolean("guide", true);
            if (update == true) {
                startActivity(intent1);
                overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                finish();
            } else {
                startActivity(intent2);
                overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
            }
            SplashActivity.this.finish();
        }
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case login_id:
//				//TODO  自动登录后处理
//				break;
//			}
        }

        ;
    };
    private String client = "android";
    private NetRun netRun = new NetRun(SplashActivity.this, handler);

    protected void initData() {
//		SharedPreferences spf = getSharedPreferences("LoginActivity", MODE_PRIVATE);
//		boolean b = spf.getBoolean("isautomatic", false);
//		if (b) {
//			String username = spf.getString("username", "");
//			String password = spf.getString("password", "");
//			netRun.login(username, password, client);
//		}
        NetRun netRun = new NetRun(this);
        netRun.OnWelcome(new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                StartImgBean startImgBean= BeanConvertor.convertBean(responseInfo.result,StartImgBean.class);
//                LogUtils.d(responseInfo.result);
              Glide.with(SplashActivity.this).load(startImgBean.getDatas().getUpload_images().get(0).getImg_path()) .into(open_img);

            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
        WaitSplash splash = new WaitSplash();
        splash.execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void findViewById() {
        open_img = findViewById(R.id.open_img);


    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    public void ReGetData() {
    }
}
