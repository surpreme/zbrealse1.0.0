package com.aite.mainlibrary.fragment.newsChirend.mainnews;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.NewsGoodBean;
import com.aite.mainlibrary.Mainbean.TopNewsBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.AroundGoodRecyAdapter;
import com.aite.mainlibrary.adapter.TopNewsRecyAdapter;
import com.lzy.basemodule.base.BaseFragment;
import com.lzy.basemodule.logcat.LogUtils;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainNewsFragment extends BaseFragment<MainNewsContract.View, MainNewsPresenter> implements MainNewsContract.View, OnBannerListener {
    @BindView(R2.id.banner)
    Banner banner;
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.top_recy)
    RecyclerView topRecy;
    @BindView(R2.id.title_top_tv)
    TextView title_top_tv;
    private AroundGoodRecyAdapter aroundGoodRecyAdapter;
    private TopNewsRecyAdapter topNewsRecyAdapter;
    private List<NewsGoodBean.DatasBean> newsGoodBeans = new ArrayList<>();
    private List<TopNewsBean.DatasBean> topnewsbean = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();

    @Override
    protected void initModel() {
        mPresenter.getListMsg(initParams());
        mPresenter.getTopNews(initParams());
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
//        httpParams.put("key", AppConstant.KEY);
        return httpParams;
    }

    @Override
    protected void initViews() {
        //banner
        initBanner(banner, BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);
        title_top_tv.setVisibility(View.GONE);
        //初始化适配器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        aroundGoodRecyAdapter = new AroundGoodRecyAdapter(context, newsGoodBeans);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(aroundGoodRecyAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        topRecy.setLayoutManager(linearLayoutManager2);
        topNewsRecyAdapter = new TopNewsRecyAdapter(context, topnewsbean);
        topRecy.setAdapter(topNewsRecyAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.news_main_fragment;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetListSuccess(Object msg) {
        newsGoodBeans.addAll(((NewsGoodBean) msg).getDatas());
        aroundGoodRecyAdapter.notifyDataSetChanged();
        if (newsGoodBeans.size() > 0) {
            title_top_tv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onGetTopNewsSuccess(Object msg) {
        topnewsbean.addAll(((TopNewsBean) msg).getDatas());
        topNewsRecyAdapter.notifyDataSetChanged();
        if (((TopNewsBean) msg).getDatas() != null || ((TopNewsBean) msg).getDatas().size() > 0) {
            for (TopNewsBean.DatasBean advListBean : ((TopNewsBean) msg).getDatas()) {
                list_img.add(advListBean.getArticle_image());
                list_title.add(advListBean.getArticle_title());
                LogUtils.d(advListBean.getArticle_title());

            }
            banner.setImages(list_img);
            banner.setBannerTitles(list_title);
            banner.startAutoPlay();
            banner.start();
        }
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
