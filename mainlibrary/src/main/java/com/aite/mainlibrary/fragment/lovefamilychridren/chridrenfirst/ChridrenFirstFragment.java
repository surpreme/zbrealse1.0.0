package com.aite.mainlibrary.fragment.lovefamilychridren.chridrenfirst;


import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aite.mainlibrary.Mainbean.NewsBean;
import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.NewsRecyAdapter;
import com.lzy.basemodule.base.BaseFragment;
import com.lzy.okgo.model.HttpParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.youth.banner.BannerConfig.NUM_INDICATOR_TITLE;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ChridrenFirstFragment extends BaseFragment<ChridrenFirstContract.View, ChridrenFirstPresenter> implements ChridrenFirstContract.View, OnBannerListener {
    @BindView(R2.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.banner)
    Banner banner;
    private NewsRecyAdapter newsRecyAdapter;
    private List<NewsBean.DatasBean.ListBean> listBeans = new ArrayList<>();
    //banner datalist
    private List<String> list_img = new ArrayList<>();
    private List<String> list_title = new ArrayList<>();

    @Override
    protected void initModel() {
        mPresenter.getlist(initParams());
    }

    @Override
    protected void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        newsRecyAdapter = new NewsRecyAdapter(context, listBeans);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(newsRecyAdapter);
        //banner
        initBanner(banner, BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setIndicatorGravity(BannerConfig.RIGHT)
                .setOnBannerListener(this);
    }

    private HttpParams initParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("is_recommend", 0);
        httpParams.put("pagesize", 100);
        return httpParams;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.news_layout;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetlistSuccess(Object msg) {
        listBeans.addAll(((NewsBean) msg).getDatas().getList());
        newsRecyAdapter.notifyDataSetChanged();
        if (((NewsBean) msg).getDatas().getList() != null || ((NewsBean) msg).getDatas().getList().size() > 0) {
            for (int i = 0; i < 3; i++) {
                list_img.add(((NewsBean) msg).getDatas().getList().get(i).getCircle_img());
                list_title.add(((NewsBean) msg).getDatas().getList().get(i).getCircle_desc());
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
