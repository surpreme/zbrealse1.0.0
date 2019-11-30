package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class ShopCardlistBean extends ErrorBean implements Serializable {

    /**
     * cart_list : [{"cart_id":"3","buyer_id":"7","store_id":"2","store_name":"艾特技术","goods_id":"12","goods_name":"测试助餐套餐","goods_price":"0.01","goods_num":"31","goods_image":"2019/10/31/2_06258484919002918.jpg","bl_id":"0","cart_type":"1","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_240.jpg","goods_sum":"0.31","goods_shipping_fee":"0.10"},{"cart_id":"4","buyer_id":"7","store_id":"2","store_name":"艾特技术","goods_id":"13","goods_name":"助餐商品","goods_price":"0.01","goods_num":"42","goods_image":"2019/11/22/2_06277498241227640.jpg","bl_id":"0","cart_type":"1","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_240.jpg","goods_sum":"0.42","goods_shipping_fee":"0.00"}]
     * sum : 0.73
     * total_shipping_fee : 0.1
     */

    private String sum;
    private double total_shipping_fee;
    private List<CartListBean> cart_list;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public double getTotal_shipping_fee() {
        return total_shipping_fee;
    }

    public void setTotal_shipping_fee(double total_shipping_fee) {
        this.total_shipping_fee = total_shipping_fee;
    }

    public List<CartListBean> getCart_list() {
        return cart_list;
    }

    public void setCart_list(List<CartListBean> cart_list) {
        this.cart_list = cart_list;
    }

    public static class CartListBean {
        /**
         * cart_id : 3
         * buyer_id : 7
         * store_id : 2
         * store_name : 艾特技术
         * goods_id : 12
         * goods_name : 测试助餐套餐
         * goods_price : 0.01
         * goods_num : 31
         * goods_image : 2019/10/31/2_06258484919002918.jpg
         * bl_id : 0
         * cart_type : 1
         * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_240.jpg
         * goods_sum : 0.31
         * goods_shipping_fee : 0.10
         */

        private String cart_id;
        private String buyer_id;
        private String store_id;
        private String store_name;
        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_image;
        private String bl_id;
        private String cart_type;
        private String goods_image_url;
        private String goods_sum;
        private String goods_shipping_fee;
        private boolean choosed;

        public boolean isChoosed() {
            return choosed;
        }

        public void setChoosed(boolean choosed) {
            this.choosed = choosed;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
        }

        public String getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getBl_id() {
            return bl_id;
        }

        public void setBl_id(String bl_id) {
            this.bl_id = bl_id;
        }

        public String getCart_type() {
            return cart_type;
        }

        public void setCart_type(String cart_type) {
            this.cart_type = cart_type;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }

        public String getGoods_sum() {
            return goods_sum;
        }

        public void setGoods_sum(String goods_sum) {
            this.goods_sum = goods_sum;
        }

        public String getGoods_shipping_fee() {
            return goods_shipping_fee;
        }

        public void setGoods_shipping_fee(String goods_shipping_fee) {
            this.goods_shipping_fee = goods_shipping_fee;
        }
    }
}
