package com.community.model;

/**
 * 发布动态选择地址
 * Created by Administrator on 2017/12/16.
 */
public class SelectAddressInfo {
    public String address;
    public String addressdesc;
    public boolean ischoose;

    public SelectAddressInfo(String address, boolean ischoose,String addressdesc) {
        this.address = address;
        this.addressdesc = addressdesc;
        this.ischoose = ischoose;
    }

    public SelectAddressInfo() {
    }
}
