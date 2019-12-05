package com.aite.mainlibrary.adapter.fragmentAdpter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentFour;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentSencond;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentThrid;
import com.aite.mainlibrary.fragment.minechridren.minerunning.MineRunningFragment;
import com.aite.mainlibrary.fragment.newsChirend.mainnews.MainNewsFragment;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-26
 * @desc:
 */
public class MineRunningViewPagerApdapter extends FragmentPagerAdapter {
    private int num;
    MineRunningFragment mineRunningFragment;
    ChridrenFragmentSencond chridrenFragmentSencond;


    public MineRunningViewPagerApdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mineRunningFragment == null) {
                    return new MainNewsFragment();
                }
            case 1:
                if (chridrenFragmentSencond == null) {
                    return new ChridrenFragmentSencond();
                }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
