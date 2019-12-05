package com.aite.mainlibrary.activity.allnews.newsinformation;


import android.os.Bundle;
import android.webkit.ClientCertRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.mainlibrary.Mainbean.NewsInformationBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class NewsInformationActivity extends BaseActivity<NewsInformationContract.View, NewsInformationPresenter> implements NewsInformationContract.View {

    @BindView(R2.id.webview)
    WebView webview;
    @BindView(R2.id.news_title_tv)
    TextView newsTitleTv;
    @BindView(R2.id.user_icon_iv)
    ImageView userIconIv;
    @BindView(R2.id.news_name_tv)
    TextView newsNameTv;
    @BindView(R2.id.news_time_tv)
    TextView newsTimeTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.news_information;
    }

    @Override
    protected void initView() {
        initToolbar("新闻详情");
        webview.loadUrl("http://aitecc.com/wap/index.php?act=cms&op=show&article_id=" + getIntent().getStringExtra("article_id"));
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                super.onReceivedClientCertRequest(view, request);
            }
        });

    }

    @Override
    protected void initDatas() {
        mPresenter.getNewsInformation(initParams());
        //http://aitecc.com/wap/index.php?act=cms&op=show&article_id=

    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("article_id", isStringEmpty(getIntent().getStringExtra("article_id")) ? "" : getIntent().getStringExtra("article_id"));
        return httpParams;
    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }

    @Override
    public void onGetNewsInformationSuccess(Object msg) {
        //article_author
        LogUtils.d(((NewsInformationBean) msg).getArticle_title());
        newsTitleTv.setText(((NewsInformationBean) msg).getArticle_title());
        newsNameTv.setText(((NewsInformationBean) msg).getArticle_author());
        newsTimeTv.setText(((NewsInformationBean) msg).getArticle_publish_time());
        Glide.with(context).load(((NewsInformationBean) msg).getPublisher_avatar()).transform(new CircleCrop()).into(userIconIv);

    }

}
