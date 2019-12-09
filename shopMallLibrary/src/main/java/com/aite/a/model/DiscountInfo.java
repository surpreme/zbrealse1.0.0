package com.aite.a.model;

/**
 * 活动规则
 * Created by Administrator on 2017/5/31.
 */
public class DiscountInfo {
    public String index;
    public String number="1";
    public String discount="100";

    public DiscountInfo( String number, String discount) {
        this.number = number;
        this.discount = discount;
    }

    public DiscountInfo() {
    }
}
