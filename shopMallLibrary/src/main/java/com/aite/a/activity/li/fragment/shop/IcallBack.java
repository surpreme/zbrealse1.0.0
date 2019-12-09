package com.aite.a.activity.li.fragment.shop;

import com.aite.a.activity.li.fragment.type.AmClassShopFragment;
/**
 * 弃用
 */
public class IcallBack  {
    private AmClassShopFragment.ChocieInterface chocieInterface;

    public IcallBack(AmClassShopFragment.ChocieInterface chocieInterface) {
        this.chocieInterface = chocieInterface;
    }

    public void setAwayChocieInterface(int i){
        chocieInterface.away(i);
    }
    public void setBuyChocieInterface(int i){
        chocieInterface.buy(i);
    }
}
