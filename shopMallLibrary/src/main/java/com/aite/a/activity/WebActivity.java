package com.aite.a.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.H5Token;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.AndroidBug5497Workaround;
import com.aite.a.utils.ImagePick;
import com.aiteshangcheng.a.R;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 需授权网页
 *
 * @author Administrator
 */
public class WebActivity extends BaseActivity {
    private final static String TAG = WebActivity.class.getName();
    private String url, token;
    private NetRun netRun;
    private SharedPreferences.Editor editor;
    public SharedPreferences sp;
    private H5Token h5Token;
    private WebView webView;
    //图片选择
    private ValueCallback<Uri> mFilePathCallback;
    private ValueCallback<Uri[]> mFilePathCallbackArray;
    private ImagePick ip;

    protected Handler mHandler = null;
    private final int GO_BACK = 1415928;//返回
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case get_token_id:
                    //授权
                    editor = sp.edit();
                    if (msg.obj != null) {
                        h5Token = (H5Token) msg.obj;
                        if (h5Token == null || h5Token.datas == null || h5Token.datas.access_token == null) {
                            return;
                        }
                        editor.putString("time", h5Token.datas.access_token_exptime);
                        editor.putString("token", h5Token.datas.access_token);
                        editor.putString("userid", State.UserId);
                        editor.commit();
                        if (isCheckToken) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            return;
                        }
                        token = h5Token.datas.access_token;
                        setCookie();
                        webView.loadUrl(url);
                    } else {
                        editor.clear();
                        editor.commit();
                    }
                    break;
                case get_token_err:
                    Toast.makeText(WebActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case GO_BACK:
                    //返回
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    break;
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        AndroidBug5497Workaround.assistActivity(this);
        //清除授权
        clearCookies(this);
        url = getIntent().getStringExtra("url");
        if (url.contains("?")) {
            url = url + "&comefrom=app";
        } else {
            url = url + "?comefrom=app";
        }
        if (url.contains("&amp;")) {
            url = url.replace("&amp;", "&");
        }
        Log.i("---------------------", "链接   " + url);
        netRun = new NetRun(this, handler);
        webView = (WebView) this.findViewById(R.id.home_zsk_webview);

        // 声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        // 支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 在js中调用本地java方法
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");

        // 设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings
                .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        // 缩放操作
        webSettings.setSupportZoom(true); // 支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); // 设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); // 隐藏原生的缩放控件

        //启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setDomStorageEnabled(true);

        // 设置WebView缓存
        // 优先使用缓存:
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 缓存模式如下：
        // LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        // LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        // LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        // LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        // 不使用缓存:
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.requestFocus();
        webView.setWebChromeClient(new WebChromeClient() {
            //权限
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(getString(R.string.tip))
                        .setMessage(message)
                        .setPositiveButton(getString(R.string.confirm), new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNeutralButton(getString(R.string.cancel), new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                builder.setOnCancelListener(new AlertDialog.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        result.cancel();
                    }
                });

                // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
                builder.setOnKeyListener(new AlertDialog.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                        return true;
                    }
                });
                // 禁止响应按back键的事件
                // builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final android.webkit.JsResult result) {
                //网页提示
//                new AlertDialog.Builder(WebActivity.this)
//                        .setTitle("提示!")
//                        .setMessage(message)
//                        .setPositiveButton(android.R.string.ok,
//                                new AlertDialog.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        result.confirm();
//                                    }
//                                }).setCancelable(false).create().show();
                Log.i("------------------", "提示   " + message);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(getString(R.string.tip)).setMessage(message).setPositiveButton(getString(R.string.release_goods94), null);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    //加载完成
//                    myDialog.dismiss();
                    //获取焦点
                    webView.setFocusableInTouchMode(true);
                    webView.setFocusable(true);

                } else {
                    //加载中
//                    if(!isFinishing()){
//                        myDialog.show();
//                    }
                }
            }

            // file upload callback (Android 2.2 (API level 8) -- Android 2.3
            // (API level 10)) (hidden method)
            public void openFileChooser(ValueCallback<Uri> filePathCallback) {
                handle(filePathCallback);
            }

            // file upload callback (Android 3.0 (API level 11) -- Android 4.0
            // (API level 15)) (hidden method)
            public void openFileChooser(ValueCallback filePathCallback,
                                        String acceptType) {
                handle(filePathCallback);
            }

            // file upload callback (Android 4.1 (API level 16) -- Android 4.3
            // (API level 18)) (hidden method)
            public void openFileChooser(ValueCallback<Uri> filePathCallback,
                                        String acceptType, String capture) {
                handle(filePathCallback);
            }

            // for Lollipop
            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                // Double check that we don't have any existing callbacks
                if (mFilePathCallbackArray != null) {
                    mFilePathCallbackArray.onReceiveValue(null);
                }
                mFilePathCallbackArray = filePathCallback;
                showDialog();
                return true;
            }

            /**
             * 处理5.0以下系统回调
             *
             * @param filePathCallback
             */
            private void handle(ValueCallback<Uri> filePathCallback) {
                // if(mFilePathCallback!=null)
                if (filePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;
                showDialog();
            }

            /**
             * 显示照片选取Dialog
             */
            public void showDialog() {
                if (ip == null) {
                    ip = new ImagePick(WebActivity.this);
                }
                ip.setCancel(new ImagePick.MyDismiss() {
                    @Override
                    public void dismiss() {
                        handleCallback(null);
                    }
                });
                ip.show();
            }

        });
        webView.setWebViewClient(new HelloWebViewClient());
        //授权
        initAuthorise();
        // synCookies(this,url);
        // webView.loadUrl(url);//加载需要显示的网页
    }


    /**
     * 授权
     */
    private void initAuthorise() {
        sp = getSharedPreferences("erptime",
                MODE_PRIVATE);
        //已登录
        if (State.UserKey != null) {
            //判断是否更换用户
            String userid = sp.getString("userid", "");
            if (userid != null && userid.equals(State.UserId)) {
                // 未更换用户 判断授权时间
                String string2 = sp.getString("time", "");
                if (string2 != null && !string2.equals("")) {
                    long parseLong = Long.parseLong(string2 + "000");
                    long l = System.currentTimeMillis();
                    Log.i("---------------------", "当前时间   " + l + "   上次授权时间  " + parseLong);
                    if (l > parseLong) {
                        //授权超时重新请求
                        netRun.getToken();
                    } else {
                        //授权有效 使用原有授权
                        String string3 = sp.getString("token", "");
                        Log.i("---------------------", "授权   " + string3);
                        if (string3 != null) {
                            token = string3;
                            setCookie();
                            webView.loadUrl(url);
                        } else {
                            //token丢失 直接加载
                            webView.loadUrl(url);
                        }
                    }
                } else {
                    //授权时间为空 请求授权
                    netRun.getToken();
                }
            } else {
                //更换用户 请求授权
                netRun.getToken();
            }
        } else {
            //未登录 直接加载
            webView.loadUrl(url);
        }
    }

    /* 改写物理按键返回的逻辑 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("-------------------", " 链接 " + webView.getUrl());
        if (webView.getUrl() != null && webView.getUrl().contains("https://wappaygw.alipay.com/")) {
            finish();
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // 返回上一页面
//            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void ReGetData() {

    }

    /**
     * 监听js事件
     *
     * @author Administrator
     */
    class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        /**
         * 登录
         * 在js中调用window.AndroidWebView.AppLogin(name)，便会触发此方法。
         * 此方法名称一定要和js中AppLogin方法一样
         */
        @JavascriptInterface
        public void AppLogin(String jumpurl) {
            Log.i("---------------------", "登录   " + jumpurl);
            Intent intent = new Intent(WebActivity.this, LoginActivity.class);
//			intent.putExtra("fromid", 5);
            intent.putExtra("jumpurl", jumpurl);
            startActivity(intent);
            Toast.makeText(WebActivity.this, getString(R.string.order_reminder109), Toast.LENGTH_SHORT).show();
            finish();
        }

        /**
         * 返回
         */
        @JavascriptInterface
        public void AppGoBack() {
            Log.i("----------------", "返回  ");
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }

        /**
         * 授权失败
         */
        @JavascriptInterface
        public void CheckTokenApp() {
            Log.i("-------------------", "授权失败");
            if (State.UserKey != null) {
                isCheckToken = true;
                netRun.getToken();
            }
        }

        /**
         * 直接退出
         */
        @JavascriptInterface
        public void AppFinish() {
            Log.i("-------------------", "直接退出");
            finish();
        }

        /**
         * 去首页
         */
        @JavascriptInterface
        public void AppToHome(int index) {
            Log.i("-------------------", "去首页  " + index);
            ToHome(index, WebActivity.this);
            finish();
        }
    }

    /**
     * 首页切换
     *
     * @param id      下标
     * @param context
     */
    public static void ToHome(int id, Context context) {
        Log.i("-----------------", "首页切换  " + id);
        // 消息广播频道
        Intent intent = null;
        if (id == 0) {
            intent = new Intent(MAIN_);
        } else if (id == 1) {
            intent = new Intent(PERSONAL_);
        } else if (id == 2) {
            intent = new Intent(PERIPHERY_);
        } else if (id == 3) {
            intent = new Intent(CART_);
        } else if (id == 4) {
            intent = new Intent(CATEGORY_);
        }
        // 有序
        context.sendOrderedBroadcast(intent, null);
    }

    //是否授权失败
    private boolean isCheckToken = false;
    private String cartnums = "0";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    /**
     * 清除Cookie
     *
     * @param context
     */
    public static void clearCookies(Context context) {
        // Edge case: an illegal state exception is thrown if an instance of
        // CookieSyncManager has not be created.  CookieSyncManager is normally
        // created by a WebKit view, but this might happen if you start the
        // app, restore saved state, and click logout before running a UI
        // dialog in a WebView -- in which case the app crashes
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr =
                CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    @SuppressLint("NewApi")
    void setCookie() {
        String StringCookie = "accesstoken=" + token + ";path=/";
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, StringCookie);
    }

//	public static void synCookies(Context context, String url) {
//		CookieSyncManager cookieSyncManager = CookieSyncManager
//				.createInstance(context);
//		cookieSyncManager.sync();
//		CookieManager cookieManager = CookieManager.getInstance();
//		cookieManager.removeAllCookie();
//		cookieManager.setCookie(url, token);
//		CookieSyncManager.getInstance().sync();
//	}

    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("---------------", "链接  " + url);
            if (shouldOverrideUrlLoadingByApp(view, url)) {
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) { // 正常照片选取的时候调用
            ip.onActivityResult(requestCode, resultCode, data,
                    new ImagePick.MyUri() {
                        @Override
                        public void getUri(Uri uri) {
                            handleCallback(uri);
                        }
                    });
        } else {
            // 取消了照片选取的时候调用
            handleCallback(null);
        }
    }

    /**
     * 处理WebView的回调
     *
     * @param uri
     */
    private void handleCallback(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mFilePathCallbackArray != null) {
                if (uri != null) {
                    mFilePathCallbackArray.onReceiveValue(new Uri[]{uri});
                } else {
                    mFilePathCallbackArray.onReceiveValue(null);
                }
                mFilePathCallbackArray = null;
            }
        } else {
            if (mFilePathCallback != null) {
                if (uri != null) {
                    mFilePathCallback.onReceiveValue(uri);
                } else {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = null;
            }
        }
    }

    @Override
    protected void findViewById() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initView() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initData() {
        // TODO Auto-generated method stub

    }

    /**
     * 系统可以处理的url正则
     */
    private static final Pattern ACCEPTED_URI_SCHEME = Pattern.compile("(?i)"
            + // switch on case insensitive matching
            '('
            + // begin group for scheme
            "(?:http|https|ftp|file)://" + "|(?:inline|data|about|javascript):" + "|(?:.*:.*@)"
            + ')' + "(.*)");

    public boolean shouldOverrideUrlLoadingByApp(WebView view, String url) {
        return shouldOverrideUrlLoadingByAppInternal(view, url, true);
    }

    /**
     * 广告业务的特殊处理
     * 根据url的scheme处理跳转第三方app的业务
     * 如果应用程序可以处理该url,就不要让浏览器处理了,返回true;
     * 如果没有应用程序可以处理该url，先判断浏览器能否处理，如果浏览器也不能处理，直接返回false拦截该url，不要再让浏览器处理。
     * 避免出现当deepLink无法调起目标app时，展示加载失败的界面。
     * <p>
     * 某些含有deepLink链接的网页，当使用deepLink跳转目标app失败时，如果将该deepLinkUrl交给系统处理，系统处理不了，会导致加载失败；
     * 示例：
     * 网页Url：https://m.ctrip.com/webapp/hotel/hoteldetail/687592/checkin-1-7.html?allianceid=288562&sid=964106&sourceid=2504&sepopup=12
     * deepLinkUrl：ctrip://wireless/InlandHotel?checkInDate=20170504&checkOutDate=20170505&hotelId=687592&allianceid=288562&sid=960124&sourceid=2504&ouid=Android_Singapore_687592
     *
     * @param interceptExternalProtocol 是否拦截自定义的外部协议，true：拦截，无论如何都不让浏览器处理；false：不拦截，如何没有成功处理该协议，继续让浏览器处理
     */
    private boolean shouldOverrideUrlLoadingByAppInternal(WebView view, String url, boolean interceptExternalProtocol) {
        if (isAcceptedScheme(url)) return false;
        Intent intent;
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
        } catch (URISyntaxException e) {
            return interceptExternalProtocol;
        }

        intent.setComponent(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            intent.setSelector(null);
        }

        //intent://dangdang://product//pid=23248697#Intent;scheme=dangdang://product//pid=23248697;package=com.dangdang.buy2;end
        //该Intent无法被设备上的应用程序处理
        if (getPackageManager().resolveActivity(intent, 0) == null) {
            return tryHandleByMarket(intent) || interceptExternalProtocol;
        }

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            return interceptExternalProtocol;
        }
        return true;
    }

    private boolean tryHandleByMarket(Intent intent) {
        String packagename = intent.getPackage();
        if (packagename != null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="
                    + packagename));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * 该url是否属于浏览器能处理的内部协议
     */
    private boolean isAcceptedScheme(String url) {
        //正则中忽略了大小写，保险起见，还是转成小写
        String lowerCaseUrl = url.toLowerCase();
        Matcher acceptedUrlSchemeMatcher = ACCEPTED_URI_SCHEME.matcher(lowerCaseUrl);
        if (acceptedUrlSchemeMatcher.matches()) {
            return true;
        }
        return false;
    }


}