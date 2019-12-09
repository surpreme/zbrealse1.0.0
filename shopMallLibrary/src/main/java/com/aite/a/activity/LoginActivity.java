package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.AppManager;
import com.aite.a.HomeTabActivity;
import com.aite.a.activity.li.util.PopupWindowUtil;
import com.aite.a.base.BaseActivity;
import com.aite.a.base.Mark;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.utils.lingshi;
import com.aiteshangcheng.a.wxapi.WXEntryActivity;
import com.aiteshangcheng.a.R;
import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * 登录
 *
 * @author chenyi
 */
public class LoginActivity extends BaseActivity implements OnClickListener,
        Mark {
    private TextView tv_reg, tv_forget, tv_qqlogin, tv_wxlogin;
    private ImageView iv_cha, iv_isciphertext;
    private EditText et_name, et_password;
    private String username, password;
    private String client = "android";
    private Button btn_login;
    private String _intentTag;
    public SharedPreferences sp;
    private Editor editor;
    private NetRun netRun;
    private Tencent mTencent;
    private String nickname;
    private String sex;
    private String head_pic;
    private String APP_ID = "1105250647";
    private CheckBox cb_login_automatic,protected_check;
    private ImageView fanhui;
    private Boolean isautomatic = true;
    private APPSingleton application;
    private boolean isciphertext = true;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case login_id:
                    editor = sp.edit();
                    if (msg.obj.equals("1")) {
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.putBoolean("isautomatic", isautomatic);
                        editor.commit();
//					if ("home".equals(_intentTag)) {
//						intent = new Intent(PERSONAL_);
//						LoginActivity.this.sendBroadcast(intent);
//					}
                        application.handler.sendMessage(application.handler.obtainMessage(login_id, "1"));
                        netRun.getGPSInfo(application.province, application.city, application.district);
                        /*CommonTools.showShortToast(LoginActivity.this,
                                getI18n(R.string.login_success));*/
//					application.handler.sendMessage(handler.obtainMessage(login_id,
//							"1"));
//					LoginActivity.this.finish();

                        if (jumpurl == null) {
                            Intent intent3 = new Intent(LoginActivity.this,
                                    HomeTabActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent3);
                            overrPre();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, WebActivity.class);
                            intent.putExtra("url", jumpurl.replace("http://aitecc.com",""));
                            startActivity(intent);
                        }
                        finish();
                    } else {
                        editor.clear();
                        editor.commit();
                        CommonTools.showShortToast(LoginActivity.this,
                                msg.obj.toString());
                    }
                    mdialog.dismiss();
                    break;
                case login_err:
                    mdialog.dismiss();
                    CommonTools.showShortToast(LoginActivity.this,
                            getI18n(R.string.act_net_error));
                    break;
                case login_start:
//				mdialog.setMessage(getI18n(R.string.logging));
                    mdialog.show();
                    break;
            }
        }

        ;
    };
    private TextView tv_sms_reg;
    private String jumpurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        setContentView(R.layout.login_activity);
        jumpurl = getIntent().getStringExtra("jumpurl");
        Log.i("-----------jumpUrl:", " " +jumpurl );
        netRun = new NetRun(this, handler);
        sp = getSharedPreferences("LoginActivity", MODE_PRIVATE);
        application = (APPSingleton) getApplication();
        findViewById();
        _intentTag = getIntent().getStringExtra("login_tag");
        // 腾讯实例
        mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());

        initViews();
    }

    private void initViews() {

    }

    @Override
    protected void findViewById() {
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        protected_check=findViewById(R.id.protected_check);
        et_name = (EditText) findViewById(R.id.login_et_name);
        et_name.setText(username);
        et_password = (EditText) findViewById(R.id.login_et_password);
//		et_password.setText(password);
        btn_login = (Button) findViewById(R.id.login_btn_);
        tv_sms_reg = (TextView) findViewById(R.id.login_tv_sms_reg);
        tv_reg = (TextView) findViewById(R.id.login_tv_reg);
        tv_forget = (TextView) findViewById(R.id.login_tv_forget);
        tv_qqlogin = (TextView) findViewById(R.id.tv_qqlogin);
        tv_wxlogin = (TextView) findViewById(R.id.tv_wxlogin);
        cb_login_automatic = (CheckBox) findViewById(R.id.cb_login_automatic);
        fanhui = (ImageView) findViewById(R.id.fanhui);
        iv_cha = (ImageView) findViewById(R.id.iv_cha);
        iv_isciphertext = (ImageView) findViewById(R.id.iv_isciphertext);
        initView();
        initData();
        // 当et_name等于空的时候密码和自动登录都取消
        et_name.addTextChangedListener(watcher);
    }

    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            boolean empty = TextUtils.isEmpty(et_name.getText().toString());
            if (empty || et_name.getText().toString().equals("")) {
                et_password.setText("");
                cb_login_automatic.setChecked(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        et_name.setText(username);
        et_password.setText(password);
        if (mTencent.isSessionValid()) {
            CommonTools.showShortToast(this, getI18n(R.string.login_success));
        }
        // boolean isautomatic1 = sp.getBoolean("isautomatic", false);
        // if (isautomatic1) {
        // btn_login.performClick();
        // }
    }

    @Override
    protected void initView() {
        btn_login.setOnClickListener(this);
        tv_reg.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
        tv_sms_reg.setOnClickListener(this);
        tv_qqlogin.setOnClickListener(this);
        tv_wxlogin.setOnClickListener(this);
        fanhui.setOnClickListener(this);
        iv_cha.setOnClickListener(this);
        protected_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    PopupWindowUtil.getmInstance().setProtectedPop(LoginActivity.this);
                }
            }
        });
        iv_isciphertext.setOnClickListener(this);
//        cb_login_automatic.setOnCheckedChangeListener(cc);
    }

    @Override
    protected void initData() {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        if (username != null && password != null) {
            et_name.setText(username);
            et_password.setText(password);
            login();
        }
    }

    private void login() {
        if (!protected_check.isChecked()){
            Toast.makeText(this,"请同意隐私政策",Toast.LENGTH_SHORT).show();
            return;
        }
        username = et_name.getText().toString();
        password = et_password.getText().toString();
        if (TextUtils.isEmpty(username)) {
            CommonTools.showShortToast(this, getI18n(R.string.username_empty));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            CommonTools.showShortToast(this, getI18n(R.string.password_empty));
            return;
        }
        netRun.login(username, password, client);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.login_btn_:
//                login();
//                break;
//            case R.id.login_tv_sms_reg:
//                Bundle bundle = new Bundle();
//                bundle.putString("reg", "sms");
//                openActivity(RegisterActivity.class, bundle);
//                overrIn();
//                break;
//            case R.id.login_tv_reg:
//                Bundle bundle2 = new Bundle();
//                bundle2.putString("reg", "sms");
//                openActivity(RegisterActivity.class, bundle2);
//                overrIn();
//                break;
//            case R.id.login_tv_forget:
//                Bundle bundle3 = new Bundle();
//                bundle3.putString("reg", "forget");
//                openActivity(RegisterActivity.class, bundle3);
//                overrIn();
//                break;
//            case R.id.tv_qqlogin:
//                // TODO QQ登录 (暂无接口)
//                Login();
//                break;
//            case R.id.tv_wxlogin:
//                // TODO 微信登录 (暂无接口)
//                lingshi.getInstance().setIslogin(true);
//                startActivity(new Intent(LoginActivity.this, WXEntryActivity.class));
//                break;
//            case R.id.iv_cha:
//                et_name.setText("");
//                break;
//            case R.id.iv_isciphertext:
//                //  是否明文
//                if (isciphertext) {
//                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    iv_isciphertext.setImageResource(R.drawable.ciphertext1);
//                    if (!TextUtils.isEmpty(et_password.getText().toString())) {
//                        et_password.setSelection(et_password.getText().toString().length());
//                    }
//                    isciphertext = false;
//                } else {
//                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    iv_isciphertext.setImageResource(R.drawable.ciphertext2);
//                    if (!TextUtils.isEmpty(et_password.getText().toString())) {
//                        et_password.setSelection(et_password.getText().toString().length());
//                    }
//                    isciphertext = true;
//                }
//                break;
//            case R.id.fanhui:
//                finish();
//                break;
//        }

        if(v.getId()==R.id.login_btn_){
            login();
        }else if(v.getId()==R.id.login_tv_sms_reg||v.getId()==R.id.login_tv_reg){
            Bundle bundle = new Bundle();
            bundle.putString("reg", "sms");
            openActivity(RegisterActivity.class, bundle);
            overrIn();
        } else if (v.getId()==R.id.login_tv_forget) {
            Bundle bundle3 = new Bundle();
            bundle3.putString("reg", "forget");
            openActivity(RegisterActivity.class, bundle3);
            overrIn();
        }else if(v.getId()==R.id.iv_cha){
            et_name.setText("");
        }else if(v.getId()==R.id.iv_isciphertext){
            //  是否明文
            if (isciphertext) {
                et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iv_isciphertext.setImageResource(R.drawable.ciphertext1);
                if (!TextUtils.isEmpty(et_password.getText().toString())) {
                    et_password.setSelection(et_password.getText().toString().length());
                }
                isciphertext = false;
            } else {
                et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iv_isciphertext.setImageResource(R.drawable.ciphertext2);
                if (!TextUtils.isEmpty(et_password.getText().toString())) {
                    et_password.setSelection(et_password.getText().toString().length());
                }
                isciphertext = true;
            }
        }else if(v.getId()==R.id.fanhui){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if ("home".equals(_intentTag)) {
            intent = new Intent(MAIN_);
            this.sendBroadcast(intent);
        }
        super.onBackPressed();
    }

    @Override
    public void ReGetData() {
        initData();
    }

//    private OnCheckedChangeListener cc = new OnCheckedChangeListener() {
//
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView,
//                                     boolean isChecked) {
//            //当用户名或密码为空时不能点击自动登录
//            if (TextUtils.isEmpty(et_name.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString())) {
//                cb_login_automatic.setChecked(false);
//            } else {
//
//            }
//
//        }
//    };

    /**
     * QQ登录
     */
    public void Login() {
        // 实列化
        mTencent = Tencent.createInstance(APP_ID, this);
        // 判断是否已经登录
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", listener);

        }
    }

    /**
     * QQ注销
     */
    public void logout() {
        mTencent.logout(this);
    }

    /**
     * 腾讯回调监听
     */
    private IUiListener listener = new IUiListener() {

        @Override
        public void onCancel() {

        }

        @Override
        public void onComplete(Object arg0) {
            JSONObject obj = (JSONObject) arg0;
            String token = obj.optString("access_token");
            String openid = obj.optString("openid");
            String expires_in = obj.optString("expires_in");
            // 设置数据
            mTencent.setOpenId(openid);
            mTencent.setAccessToken(token, expires_in);
            UserInfo userInfo = new UserInfo(LoginActivity.this,
                    mTencent.getQQToken());

            userInfo.getUserInfo(new IUiListener() {

                @Override
                public void onError(UiError arg0) {

                }

                @Override
                public void onComplete(Object arg0) {
                    JSONObject json = (JSONObject) arg0;
                    final String openid = mTencent.getOpenId();
                    nickname = json.optString("nickname");
                    head_pic = json.optString("figureurl_qq_2");
                    sex = json.optString("gender");
                    Toast.makeText(LoginActivity.this, "正在开发中...",
                            Toast.LENGTH_LONG).show();
                    // Intent intent2 = new Intent(LoginActivity.this,
                    // PersonalActivity.class);
                    // intent2.putExtra("nickname", nickname);
                    // intent2.putExtra("sex", sex);
                    // intent2.putExtra("head_pic", head_pic);
                    // startActivity(intent2);
                    // finish();
                }

                @Override
                public void onCancel() {

                }
            });
        }

        @Override
        public void onError(UiError arg0) {

        }

    };

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;
}
