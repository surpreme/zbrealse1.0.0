package com.aite.a.model;

/**
 * 店铺分类
 * Created by Administrator on 2017/5/6.
 */
public class StoreClassifyInfo {
    public boolean istitle=false;
    public String stc_name;
    public String mrid;

    public StoreClassifyInfo(boolean istitle, String stc_name) {
        this.istitle = istitle;
        this.stc_name = stc_name;
    }
    public StoreClassifyInfo(boolean istitle, String stc_name,String mrid) {
        this.istitle = istitle;
        this.stc_name = stc_name;
        this.mrid = mrid;
    }

    public StoreClassifyInfo() {
    }
}
