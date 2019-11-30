package com.aite.mainlibrary.adapter.fragmentAdpter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentFour;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentSencond;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentThrid;
import com.aite.mainlibrary.fragment.lovefamilychridren.chridrenfirst.ChridrenFirstFragment;
import com.aite.mainlibrary.fragment.newsChirend.mainnews.MainNewsFragment;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-26
 * @desc:
 */
public class BackgroundViewPagerApdapter extends FragmentPagerAdapter {
    private int num;
    MainNewsFragment mainNewsFragment;
    ChridrenFragmentSencond chridrenFragmentSencond;
    ChridrenFragmentThrid chridrenFragmentThrid;
    ChridrenFragmentFour chridrenFragmentFour;


    public BackgroundViewPagerApdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (mainNewsFragment == null) {
                    return new MainNewsFragment();
                }
            case 1:
                if (chridrenFragmentSencond == null) {
                    return new ChridrenFragmentSencond();
                }
            case 2:
                if (chridrenFragmentThrid == null) {
                    return new ChridrenFragmentThrid();
                }
            case 3:
                if (chridrenFragmentFour == null) {
                    return new ChridrenFragmentFour();
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
