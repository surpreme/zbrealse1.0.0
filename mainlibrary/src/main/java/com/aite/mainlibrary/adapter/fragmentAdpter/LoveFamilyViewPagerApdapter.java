package com.aite.mainlibrary.adapter.fragmentAdpter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentFour;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentSencond;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentThrid;
import com.aite.mainlibrary.fragment.lovefamilychridren.chridrenfirst.ChridrenFirstFragment;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-26
 * @desc:
 */
public class LoveFamilyViewPagerApdapter extends FragmentPagerAdapter {
    private int num;
    ChridrenFirstFragment chridrenFragmentFirst;
    ChridrenFragmentSencond chridrenFragmentSencond;
    ChridrenFragmentThrid chridrenFragmentThrid;
    ChridrenFragmentFour chridrenFragmentFour;


    public LoveFamilyViewPagerApdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (chridrenFragmentFirst == null) {
                    return new ChridrenFirstFragment();
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
