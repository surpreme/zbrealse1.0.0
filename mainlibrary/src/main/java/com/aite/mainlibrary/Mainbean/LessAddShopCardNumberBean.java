package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class LessAddShopCardNumberBean extends ErrorBean implements Serializable {
    /**
     * quantity : 2
     * goods_price : 0.01
     * total_price : 0.02
     */

    private int quantity;
    private String goods_price;
    private String total_price;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
