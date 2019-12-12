package com.aite.mainlibrary.adapter.fragmentAdpter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentFour;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentSencond;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentThrid;
import com.aite.mainlibrary.fragment.lovefamilychridren.chridrenfirst.ChridrenFirstFragment;

import java.util.ArrayList;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-26
 * @desc:
 */
public class LoveFamilyViewPagerApdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public LoveFamilyViewPagerApdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
