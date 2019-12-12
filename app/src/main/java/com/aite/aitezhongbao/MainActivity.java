package com.aite.aitezhongbao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aite.mainlibrary.fragment.activityfragment.AroundBackgroundFragment;
import com.aite.mainlibrary.fragment.activityfragment.LoveFamilyFragment;
import com.aite.mainlibrary.fragment.activityfragment.ShopFragment;
import com.aite.mainlibrary.fragment.activityfragment.main.MainFragment;
import com.aite.mainlibrary.fragment.activityfragment.minefragment.MineFragment;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.view.StatusBarUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.main_img)
    ImageView mainImg;
    @BindView(R.id.main_tv)
    TextView mainTv;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.shop_img)
    ImageView shopImg;
    @BindView(R.id.shop_tv)
    TextView shopTv;
    @BindView(R.id.shop_layout)
    RelativeLayout shopLayout;
    @BindView(R.id.aroundbackground_img)
    ImageView aroundbackgroundImg;
    @BindView(R.id.aroundbackground_tv)
    TextView aroundbackgroundTv;
    @BindView(R.id.aroundbackground_layout)
    RelativeLayout aroundbackgroundLayout;
    @BindView(R.id.news_img)
    ImageView newsImg;
    @BindView(R.id.news_tv)
    TextView newsTv;
    @BindView(R.id.news_layout)
    RelativeLayout newsLayout;
    @BindView(R.id.my_img)
    ImageView myImg;
    @BindView(R.id.my_tv)
    TextView myTv;
    @BindView(R.id.my_layout)
    RelativeLayout myLayout;
    private static final String[] FRAGMENT_TAG = {"MainFragment", "ShopFragment", "AroundBackgroundFragment", "LoveFamilyFragment", "MineFragment"};
    protected String CODE_FRAGMENT_KEY = "fragment_tag";//key

    private MainFragment mainFragment;
    private MineFragment mineFragment;
    private LoveFamilyFragment loveFamilyFragment;
    private ShopFragment shopFragment;
    private AroundBackgroundFragment aroundBackgroundFragment;
    private int mFragmentTag_index = 0;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        if (getSavedInstanceState() != null) {
            if (getSavedInstanceState().getInt(CODE_FRAGMENT_KEY) == 0 && mainFragment == null)
                mainFragment = (MainFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[0]);
//            if (getSavedInstanceState().getInt(CODE_FRAGMENT_KEY) == 1 && shopFragment == null)
//                shopFragment = (ShopFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[1]);
            if (getSavedInstanceState().getInt(CODE_FRAGMENT_KEY) == 1 && aroundBackgroundFragment == null)
                aroundBackgroundFragment = (AroundBackgroundFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[1]);
            if (getSavedInstanceState().getInt(CODE_FRAGMENT_KEY) == 2 && loveFamilyFragment == null)
                loveFamilyFragment = (LoveFamilyFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[2]);
            if (getSavedInstanceState().getInt(CODE_FRAGMENT_KEY) == 3 && mineFragment == null)
                mineFragment = (MineFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG[3]);
            setTabSelection(getSavedInstanceState().getInt(CODE_FRAGMENT_KEY));
        } else {
            setTabSelection(0);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CODE_FRAGMENT_KEY, mFragmentTag_index);
        super.onSaveInstanceState(outState);
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

    private FragmentManager fragmentManager;


    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        mFragmentTag_index = index;
        switch (index) {
            case 0:
                mainImg.setImageResource(R.mipmap.mainimg);
                mainTv.setTextColor(getResources().getColor(R.color.blue));
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                    transaction.add(R.id.content, mainFragment, FRAGMENT_TAG[index]);
                } else {
                    transaction.show(mainFragment);
                }
                break;
//            case 1:
//                shopImg.setImageResource(R.drawable.shop);
//                shopTv.setTextColor(getResources().getColor(R.color.blue));
//                if (shopFragment == null) {
//                    shopFragment = new ShopFragment();
//                    transaction.add(R.id.content, shopFragment, FRAGMENT_TAG[index]);
//                } else {
//                    transaction.show(shopFragment);
//                }
//                break;
            case 1:
                aroundbackgroundImg.setImageResource(R.drawable.around);
                aroundbackgroundTv.setTextColor(getResources().getColor(R.color.blue));
                if (aroundBackgroundFragment == null) {
                    aroundBackgroundFragment = new AroundBackgroundFragment();
                    transaction.add(R.id.content, aroundBackgroundFragment, FRAGMENT_TAG[index]);
                } else {
                    transaction.show(aroundBackgroundFragment);
                }
                break;
            case 2:
                newsImg.setImageResource(R.drawable.app_news);
                newsTv.setTextColor(getResources().getColor(R.color.blue));
                if (loveFamilyFragment == null) {
                    loveFamilyFragment = new LoveFamilyFragment();
                    transaction.add(R.id.content, loveFamilyFragment, FRAGMENT_TAG[index]);
                } else {
                    transaction.show(loveFamilyFragment);
                }
                break;
            case 3:
                myImg.setImageResource(R.drawable.mine);
                myTv.setTextColor(getResources().getColor(R.color.blue));
                StatusBarUtils.setColor(context, getResources().getColor(R.color.blue));
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.content, mineFragment, FRAGMENT_TAG[index]);
                } else {
                    transaction.show(mineFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();

    }

    private void clearSelection() {
        StatusBarUtils.setColor(context, getResources().getColor(R.color.white));
        shopImg.setImageResource(R.mipmap.unshop);
        shopTv.setTextColor(Color.parseColor("#82858b"));
        mainImg.setImageResource(R.drawable.unmain);
        mainTv.setTextColor(Color.parseColor("#82858b"));
        aroundbackgroundImg.setImageResource(R.mipmap.unaroundbackground);
        aroundbackgroundTv.setTextColor(Color.parseColor("#82858b"));
        newsImg.setImageResource(R.mipmap.unnews);
        newsTv.setTextColor(Color.parseColor("#82858b"));
        myImg.setImageResource(R.mipmap.unmy);
        myTv.setTextColor(Color.parseColor("#82858b"));


    }

    /**
     * 隐藏碎片 避免重叠
     * 爱家 unnews
     * 我的 unmy /// mine
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
//        if (shopFragment != null) {
//            transaction.hide(shopFragment);
//        }
        if (aroundBackgroundFragment != null) {
            transaction.hide(aroundBackgroundFragment);
        }
        if (loveFamilyFragment != null) {
            transaction.hide(loveFamilyFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }

    }


    @OnClick({R.id.main_layout, R.id.shop_layout, R.id.aroundbackground_layout, R.id.news_layout, R.id.my_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.content:
//                break;
//            case R.id.main_img:
//                break;
//            case R.id.main_tv:
//                break;
            case R.id.main_layout:
                setTabSelection(0);
                break;
//            case R.id.shop_img:
//                break;
//            case R.id.shop_tv:
//                break;
            case R.id.shop_layout:
                //       setTabSelection(1);
//                Intent intent = new Intent(/*getContext(), HomeTabActivity.class*/);
//                intent.setClassName(getContext(),com.aite.a.HomeTabActivity);
//                //getContext(),com.aite.a.activity.MainActivity.class
////                intent.setAction("com.aite.zhongbao.shop.MainActivity");
//                startActivity(intent);

                break;
//            case R.id.aroundbackground_img:
//                break;
//            case R.id.aroundbackground_tv:
//                break;
            case R.id.aroundbackground_layout:
                setTabSelection(1);
//                Intent information = new Intent(/*getContext(), InformationActivity.class*/);
//                information.setClassName(getContext(),"com.aite.a.activity.InformationActivity");
//                startActivity(information);

                break;
//            case R.id.news_img:
//                break;
//            case R.id.news_tv:
//                break;
            case R.id.news_layout:
//                Intent intent1 = new Intent(com.aite.a.activity.MainActivity.this,
//                        InformationActivity.class);
//                startActivity(intent1);
//                Intent person_in = new Intent(/*getContext(), InformationActivity.class*/);
//                person_in.setClassName(getContext(),"com.aite.a.activity.InformationActivity");
//                person_in.putExtra("person_in", "2");
//                startActivity(person_in);
                setTabSelection(2);
                break;
//            case R.id.my_img:
//                break;
//            case R.id.my_tv:
//                break;
            case R.id.my_layout:
                setTabSelection(3);
                break;
        }
    }

}
//public class AroundBackgroundFragment extends BaseFragment {
//    @BindView(R2.id.webView)
//    WebView webView;
//    private static final String WEBURL = "https://aitecc.com/wap/index.php?act=news";
//    //    https://aitecc.com/wap/index.php?act=news
//
//    @Override
//    protected void initModel() {
//
//    }
//
//    @Override
//    protected void initViews() {
//        initWebSetting();
//        webView.loadUrl(WEBURL);
//    }
//
//    private void initWebSetting() {
//        // 声明WebSettings子类
//        WebSettings webSettings = webView.getSettings();
//        // 支持javascript
//        webSettings.setJavaScriptEnabled(true);
//        // 设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//
//        // 缩放操作
//        webSettings.setSupportZoom(true); // 支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); // 设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); // 隐藏原生的缩放控件
//        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
//
//        //启用数据库
//        webSettings.setDatabaseEnabled(true);
//        String dir = MainApp.getAppContext().getDir("database", Context.MODE_PRIVATE).getPath();
//        //启用地理定位
//        webSettings.setGeolocationEnabled(true);
//        //设置定位的数据库路径
//        webSettings.setGeolocationDatabasePath(dir);
//        //最重要的方法，一定要设置，这就是出不来的主要原因
//        webSettings.setDomStorageEnabled(true);
//        webView.addJavascriptInterface(new com.aite.mainlibrary.fragment.AroundBackgroundFragment.JsInterface(getActivity()), "AndroidWebView");
//        //如果不设置WebViewClient，请求会跳转系统浏览器
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
//                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
//                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//                view.loadUrl(WEBURL);
//
//                return false;
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
//                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    if (request.getUrl().toString().contains("sina.cn")) {
//                        view.loadUrl("http://ask.csdn.net/questions/178242");
//                        return true;
//                    }
//                }
//
//                return false;
//            }
//
//        });
//
//    }
//
//    /**
//     * 监听js事件
//     *
//     * @author Administrator
//     */
//    class JsInterface {
//        private Context mContext;
//
//        public JsInterface(Context context) {
//            this.mContext = context;
//        }
//
//        /**
//         * 返回
//         */
//        @JavascriptInterface
//        public void AppGoBack() {
//            Log.i("----------------", "返回  ");
//            if (webView.canGoBack()) {
//                webView.goBack();
//            } else {
//                getActivity().finish();
//            }
//        }
//    }
//
//    @Override
//    protected int getLayoutResId() {
//        return com.aite.mainlibrary.R.layout.activity_around_fragment;
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    public boolean onBackPressed(){
//        if (webView.canGoBack()){
//            webView.goBack();
//            return false;
//        }else {
//            return true;
//        }
//    }
//}
//    @Override
//    public void onBackPressed() {
//        if (mFragmentTag_index == 2 ) {
//            if (aroundBackgroundFragment.onBackPressed() ) {
//                super.onBackPressed();
//            } else {
//                return;
//            }
//        } else if (mFragmentTag_index == 3){
//            if (loveFamilyFragment.onBackPressed()) {
//                super.onBackPressed();
//            } else {
//                return;
//            }
//        } else {
//            return;
//        }
//    }