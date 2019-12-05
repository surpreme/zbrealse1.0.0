package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class BookMorningNoonEatBean extends ErrorBean implements Serializable {

    /**
     * list_total : 39
     * is_nextpage : 1
     * order_list : [{"order_id":"70","order_amount":"0.01","goods_id":"8","store_id":"2","goods_name":"测试早餐1","goods_price":"0.01","goods_num":"1","goods_image":"2019/10/29/2_06256787071214709.jpg","add_time":"2019-12-02","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"order_id":"66","order_amount":"0.01","goods_id":"8","store_id":"2","goods_name":"测试早餐1","goods_price":"0.01","goods_num":"1","goods_image":"2019/10/29/2_06256787071214709.jpg","add_time":"2019-12-02","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"order_id":"65","order_amount":"0.01","goods_id":"8","store_id":"2","goods_name":"测试早餐1","goods_price":"0.01","goods_num":"1","goods_image":"2019/10/29/2_06256787071214709.jpg","add_time":"2019-12-02","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"order_id":"63","order_amount":"0.10","goods_id":"9","store_id":"2","goods_name":"测试早餐2","goods_price":"0.10","goods_num":"1","goods_image":"2019/11/22/2_06277498241227640.jpg","add_time":"2019-12-01","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_240.jpg"},{"order_id":"62","order_amount":"0.10","goods_id":"9","store_id":"2","goods_name":"测试早餐2","goods_price":"0.10","goods_num":"1","goods_image":"2019/11/22/2_06277498241227640.jpg","add_time":"2019-12-01","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_240.jpg"},{"order_id":"61","order_amount":"0.01","goods_id":"8","store_id":"2","goods_name":"测试早餐1","goods_price":"0.01","goods_num":"1","goods_image":"2019/10/29/2_06256787071214709.jpg","add_time":"2019-12-01","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"order_id":"60","order_amount":"0.10","goods_id":"9","store_id":"2","goods_name":"测试早餐2","goods_price":"0.10","goods_num":"1","goods_image":"2019/11/22/2_06277498241227640.jpg","add_time":"2019-12-01","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_240.jpg"},{"order_id":"59","order_amount":"0.10","goods_id":"9","store_id":"2","goods_name":"测试早餐2","goods_price":"0.10","goods_num":"1","goods_image":"2019/11/22/2_06277498241227640.jpg","add_time":"2019-12-01","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_240.jpg"},{"order_id":"57","order_amount":"0.01","goods_id":"8","store_id":"2","goods_name":"测试早餐1","goods_price":"0.01","goods_num":"1","goods_image":"2019/10/29/2_06256787071214709.jpg","add_time":"2019-12-01","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"order_id":"56","order_amount":"0.10","goods_id":"9","store_id":"2","goods_name":"测试早餐2","goods_price":"0.10","goods_num":"1","goods_image":"2019/11/22/2_06277498241227640.jpg","add_time":"2019-11-30","evaluation_state":"0","use_state":"0","order_state":"10","is_buy_apply":"0","order_shipping_fee":"0.00","order_state_text":"待付款","if_cancel":1,"if_pay":1,"if_detail":0,"is_verify":0,"if_evaluation":0,"goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_240.jpg"}]
     */

    private String list_total;
    private int is_nextpage;
    private List<OrderListBean> order_list;

    public String getList_total() {
        return list_total;
    }

    public void setList_total(String list_total) {
        this.list_total = list_total;
    }

    public int getIs_nextpage() {
        return is_nextpage;
    }

    public void setIs_nextpage(int is_nextpage) {
        this.is_nextpage = is_nextpage;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class OrderListBean {
        /**
         * order_id : 70
         * order_amount : 0.01
         * goods_id : 8
         * store_id : 2
         * goods_name : 测试早餐1
         * goods_price : 0.01
         * goods_num : 1
         * goods_image : 2019/10/29/2_06256787071214709.jpg
         * add_time : 2019-12-02
         * evaluation_state : 0
         * use_state : 0
         * order_state : 10
         * is_buy_apply : 0
         * order_shipping_fee : 0.00
         * order_state_text : 待付款
         * if_cancel : 1
         * if_pay : 1
         * if_detail : 0
         * is_verify : 0
         * if_evaluation : 0
         * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg
         */

        private String order_id;
        private String order_amount;
        private String goods_id;
        private String store_id;
        private String goods_name;
        private String goods_price;
        private String goods_num;
        private String goods_image;
        private String add_time;
        private String evaluation_state;
        private String use_state;
        private String order_state;
        private String is_buy_apply;
        private String order_shipping_fee;
        private String order_state_text;
        private int if_cancel;
        private int if_pay;
        private int if_detail;
        private int is_verify;
        private int if_evaluation;
        private String goods_image_url;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(String order_amount) {
            this.order_amount = order_amount;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
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

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getEvaluation_state() {
            return evaluation_state;
        }

        public void setEvaluation_state(String evaluation_state) {
            this.evaluation_state = evaluation_state;
        }

        public String getUse_state() {
            return use_state;
        }

        public void setUse_state(String use_state) {
            this.use_state = use_state;
        }

        public String getOrder_state() {
            return order_state;
        }

        public void setOrder_state(String order_state) {
            this.order_state = order_state;
        }

        public String getIs_buy_apply() {
            return is_buy_apply;
        }

        public void setIs_buy_apply(String is_buy_apply) {
            this.is_buy_apply = is_buy_apply;
        }

        public String getOrder_shipping_fee() {
            return order_shipping_fee;
        }

        public void setOrder_shipping_fee(String order_shipping_fee) {
            this.order_shipping_fee = order_shipping_fee;
        }

        public String getOrder_state_text() {
            return order_state_text;
        }

        public void setOrder_state_text(String order_state_text) {
            this.order_state_text = order_state_text;
        }

        public int getIf_cancel() {
            return if_cancel;
        }

        public void setIf_cancel(int if_cancel) {
            this.if_cancel = if_cancel;
        }

        public int getIf_pay() {
            return if_pay;
        }

        public void setIf_pay(int if_pay) {
            this.if_pay = if_pay;
        }

        public int getIf_detail() {
            return if_detail;
        }

        public void setIf_detail(int if_detail) {
            this.if_detail = if_detail;
        }

        public int getIs_verify() {
            return is_verify;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }

        public int getIf_evaluation() {
            return if_evaluation;
        }

        public void setIf_evaluation(int if_evaluation) {
            this.if_evaluation = if_evaluation;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }
    }
}
