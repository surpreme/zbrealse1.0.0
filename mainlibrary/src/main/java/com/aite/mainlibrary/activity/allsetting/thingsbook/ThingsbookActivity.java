package com.aite.mainlibrary.activity.allsetting.thingsbook;


import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.activity.allshopcard.timebank.TimeBankContract;
import com.aite.mainlibrary.activity.allshopcard.timebank.TimeBankPresenter;
import com.aite.mainlibrary.adapter.fragmentAdpter.BackgroundViewPagerApdapter;
import com.aite.mainlibrary.adapter.fragmentAdpter.LoveFamilyViewPagerApdapter;
import com.aite.mainlibrary.adapter.fragmentAdpter.ThingBookPagerApdapter;
import com.aite.mainlibrary.fragment.daybookchridren.daybooklist.DaybooklistFragment;
import com.google.android.material.tabs.TabLayout;
import com.lzy.basemodule.BaseConstant.AppConstant;
import com.lzy.basemodule.base.BaseActivity;
import com.lzy.basemodule.mvp.MVPBaseActivity;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ThingsbookActivity extends BaseActivity<ThingsbookContract.View, ThingsbookPresenter> implements ThingsbookContract.View {
    private ThingBookPagerApdapter thingBookPagerApdapter;
    @BindView(R2.id.viewpager)
    ViewPager viewPager;
    @BindView(R2.id.thingsfix_tabMode)
    TabLayout tabLayout;
    private View[] views;

    @Override
    protected int getLayoutResId() {
        return R.layout.thingbook_layout;
    }

    @Override
    protected void initView() {
        initToolbar("订单");
        initFragment();
    }

    private void initFragment() {
        views = new View[3];
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        views[0] = layoutInflater.inflate(R.layout.recy_layout, null);
        views[1] = layoutInflater.inflate(R.layout.maindoctor_information, null);
        views[2] = layoutInflater.inflate(R.layout.recy_layout, null);
        thingBookPagerApdapter = new ThingBookPagerApdapter(this.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(thingBookPagerApdapter);
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
    protected boolean isUseMvp() {
        return false;
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
