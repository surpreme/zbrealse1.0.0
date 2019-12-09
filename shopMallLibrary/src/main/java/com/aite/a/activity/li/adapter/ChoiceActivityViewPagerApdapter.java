package com.aite.a.activity.li.adapter;


import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aite.a.activity.li.fragment.type.AmClassBrandFragment;
import com.aite.a.activity.li.fragment.type.AmClassShopFragment;
import com.aite.a.activity.li.fragment.type.AmClassifyFragment;
import com.aite.a.activity.li.fragment.shop.ShopAllFragment;

public class ChoiceActivityViewPagerApdapter extends FragmentPagerAdapter {
    private int num;
     AmClassifyFragment amClassifyFragment;
     AmClassBrandFragment amClassBrandFragment;
     ShopAllFragment shopAllFragment;


    public ChoiceActivityViewPagerApdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (amClassifyFragment == null) {
                    return new AmClassifyFragment();
                }
            case 1:
                if (amClassBrandFragment == null) {
                    return new AmClassBrandFragment();
                }
            case 2:
                if (shopAllFragment == null) {
                    return new AmClassShopFragment();
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
