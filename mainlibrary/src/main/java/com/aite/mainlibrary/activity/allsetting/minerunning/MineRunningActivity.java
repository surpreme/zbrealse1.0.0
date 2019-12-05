package com.aite.mainlibrary.activity.allsetting.minerunning;


import android.view.LayoutInflater;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.fragmentAdpter.BackgroundViewPagerApdapter;
import com.aite.mainlibrary.adapter.fragmentAdpter.MineRunningViewPagerApdapter;
import com.google.android.material.tabs.TabLayout;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MineRunningActivity extends BaseActivity<MineRunningContract.View, MineRunningPresenter> implements MineRunningContract.View {
    @BindView(R2.id.viewpager)
    ViewPager viewPager;
    @BindView(R2.id.thingsfix_tabMode)
    TabLayout tabLayout;
    private View[] views;
    private MineRunningViewPagerApdapter viewPagerAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.mine_running_activity;
    }

    @Override
    protected void initView() {
        initToolbar("我的服务");
        initFragment();

    }

    private void initFragment() {
        views = new View[2];
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        views[0] = layoutInflater.inflate(R.layout.smartlayout_recy_layout, null);
        views[1] = layoutInflater.inflate(R.layout.maindoctor_information, null);
        viewPagerAdapter = new MineRunningViewPagerApdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
    protected void initDatas() {

    }

    @Override
    protected void initResume() {

    }

    @Override
    protected void initReStart() {

    }
}
