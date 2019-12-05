package com.aite.mainlibrary.fragment.activityfragment;


import android.view.LayoutInflater;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.fragmentAdpter.BackgroundViewPagerApdapter;
import com.aite.mainlibrary.adapter.fragmentAdpter.LoveFamilyViewPagerApdapter;
import com.google.android.material.tabs.TabLayout;
import com.lzy.basemodule.base.BaseFragment;
import com.youth.banner.listener.OnBannerListener;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AroundBackgroundFragment extends BaseFragment{
    @BindView(R2.id.viewpager)
    ViewPager viewPager;
    @BindView(R2.id.thingsfix_tabMode)
    TabLayout tabLayout;
    private View[] views;
    private BackgroundViewPagerApdapter viewPagerAdapter;


    @Override
    protected void initModel() {

    }
    private void initFragment() {
        views = new View[4];
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        views[0] = layoutInflater.inflate(R.layout.news_main_fragment, null);
        views[1] = layoutInflater.inflate(R.layout.maindoctor_information, null);
        views[2] = layoutInflater.inflate(R.layout.recy_layout, null);
        views[3] = layoutInflater.inflate(R.layout.recy_layout, null);
        viewPagerAdapter = new BackgroundViewPagerApdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    @Override
    protected void initViews() {
        initFragment();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_around_fragment;
    }

    @Override
    public void onClick(View v) {

    }

}
