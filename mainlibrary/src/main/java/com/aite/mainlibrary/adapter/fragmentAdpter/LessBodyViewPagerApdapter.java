package com.aite.mainlibrary.adapter.fragmentAdpter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aite.mainlibrary.fragment.lessBodyfragment.lessbodybookfragment.LessBodyBookFragment;
import com.aite.mainlibrary.fragment.lessBodyfragment.lessbodyoveredbooklist.LessbodyoveredbooklistFragment;
import com.aite.mainlibrary.fragment.lessBodyfragment.lessbodyunpaybooklist.LessbodyunpaybooklistFragment;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentSencond;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentThrid;
import com.aite.mainlibrary.fragment.minechridren.minerunning.MineRunningFragment;
import com.aite.mainlibrary.fragment.newsChirend.mainnews.MainNewsFragment;

import java.util.ArrayList;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-26
 * @desc:
 */
public class LessBodyViewPagerApdapter extends FragmentPagerAdapter {
    private String page_type = "";
    private ArrayList<Fragment> fragments;

    public LessBodyViewPagerApdapter(FragmentManager fm, ArrayList<Fragment> fragments, String page_type) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.page_type = page_type;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("page_type", page_type);
        assert fragment != null;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
