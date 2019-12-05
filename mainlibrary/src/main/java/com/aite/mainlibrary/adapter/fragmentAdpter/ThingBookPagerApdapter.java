package com.aite.mainlibrary.adapter.fragmentAdpter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aite.mainlibrary.activity.allsetting.thingsbook.ThingsbookActivity;
import com.aite.mainlibrary.fragment.daybookchridren.daybooklist.DaybooklistFragment;
import com.aite.mainlibrary.fragment.daybookchridren.overedbooklist.OveredbooklistFragment;
import com.aite.mainlibrary.fragment.daybookchridren.unpaybooklist.UnPaybooklistFragment;
import com.aite.mainlibrary.fragment.lessBodyfragment.lessbodybookfragment.LessBodyBookFragment;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentFour;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentSencond;
import com.aite.mainlibrary.fragment.lovefamilychridren.ChridrenFragmentThrid;
import com.aite.mainlibrary.fragment.lovefamilychridren.chridrenfirst.ChridrenFirstFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-26
 * @desc:
 */
public class ThingBookPagerApdapter extends FragmentPagerAdapter {
    DaybooklistFragment daybooklistFragment;
    private int num;
    //    ChridrenFirstFragment chridrenFragmentFirst;
    UnPaybooklistFragment unPaybooklistFragment;
    OveredbooklistFragment overedbooklistFragment;
    ChridrenFragmentFour chridrenFragmentFour;


    public ThingBookPagerApdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (daybooklistFragment == null) {
                    return new DaybooklistFragment();
                }
            case 1:
                if (unPaybooklistFragment == null) {
                    return new UnPaybooklistFragment();
                }
            case 2:
                if (overedbooklistFragment == null) {
                    return new OveredbooklistFragment();
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
