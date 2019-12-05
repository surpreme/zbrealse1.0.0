package com.aite.mainlibrary.activity.allsetting;


import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.aite.mainlibrary.R;
import com.aite.mainlibrary.R2;
import com.aite.mainlibrary.adapter.fragmentAdpter.LessBodyViewPagerApdapter;
import com.aite.mainlibrary.adapter.fragmentAdpter.ThingBookPagerApdapter;
import com.aite.mainlibrary.fragment.lessBodyfragment.lessbodybookfragment.LessBodyBookFragment;
import com.aite.mainlibrary.fragment.lessBodyfragment.lessbodyoveredbooklist.LessbodyoveredbooklistFragment;
import com.aite.mainlibrary.fragment.lessBodyfragment.lessbodyunpaybooklist.LessbodyunpaybooklistFragment;
import com.google.android.material.tabs.TabLayout;
import com.lzy.basemodule.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LessbodybookActivity extends BaseActivity {
    private LessBodyViewPagerApdapter lessBodyViewPagerApdapter;
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
    protected boolean isUseMvp() {
        return false;
    }
//     * page_type	get	整型	必须	1		页面类型 1日托 2培训 3就业 4助残活动 5其他服务

    @Override
    protected void initView() {
        switch (getIntent().getStringExtra("page_type")) {
            case "1":
                initToolbar("日托订单");
                break;
            case "2":
                initToolbar("培训订单");
                break;
            case "3":
                initToolbar("就业订单");
                break;
            case "4":
                initToolbar("助残订单");
                break;
            case "5":
                initToolbar("其他订单");
                break;

        }
        initFragment();
    }

    private void initFragment() {
        views = new View[3];
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        views[0] = layoutInflater.inflate(R.layout.smartlayout_recy_layout, null);
        views[1] = layoutInflater.inflate(R.layout.smartlayout_recy_layout, null);
        views[2] = layoutInflater.inflate(R.layout.smartlayout_recy_layout, null);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new LessBodyBookFragment());
        fragments.add(new LessbodyunpaybooklistFragment());
        fragments.add(new LessbodyoveredbooklistFragment());
        lessBodyViewPagerApdapter = new LessBodyViewPagerApdapter(this.getSupportFragmentManager(), fragments, getIntent().getStringExtra("page_type"));
        //一次加载3个 防止销毁（解决懒加载的 只加载一次数据的问题） setOffscreenPageLimit
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        viewPager.setAdapter(lessBodyViewPagerApdapter);
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
