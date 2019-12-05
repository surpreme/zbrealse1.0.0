package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-01
 * @desc:
 */
public class LessBodyInformationBean extends ErrorBean implements Serializable {

    /**
     * goods_info : {"goods_id":"1","goods_name":"测试日托服务商品","goods_price":"0.01","is_virtual":"1","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"}
     * isFavorites : 0
     * goods_commend_list : [{"goods_id":"8","goods_name":"测试早餐1","goods_price":"0.01","goods_marketprice":"1.00","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"goods_id":"9","goods_name":"测试早餐2","goods_price":"0.10","goods_marketprice":"1.00","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/11/22/2_06277498241227640_240.jpg"},{"goods_id":"1","goods_name":"测试日托服务商品","goods_price":"0.01","goods_marketprice":"1.00","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"goods_id":"10","goods_name":"测试午餐1","goods_price":"0.01","goods_marketprice":"1.00","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_240.jpg"},{"goods_id":"11","goods_name":"测试午餐2","goods_price":"0.10","goods_marketprice":"1.00","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg"},{"goods_id":"2","goods_name":"日托服务商品","goods_price":"0.10","goods_marketprice":"1.00","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_240.jpg"}]
     * evaluate_info : {"good":0,"normal":0,"bad":0,"all":0,"good_percent":100,"normal_percent":0,"bad_percent":0,"good_star":0,"star_average":0}
     * goods_evaluate_list : []
     */

    private GoodsInfoBean goods_info;
    private int isFavorites;
    private EvaluateInfoBean evaluate_info;
    private List<GoodsCommendListBean> goods_commend_list;
    private List<?> goods_evaluate_list;

    public GoodsInfoBean getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(GoodsInfoBean goods_info) {
        this.goods_info = goods_info;
    }

    public int getIsFavorites() {
        return isFavorites;
    }

    public void setIsFavorites(int isFavorites) {
        this.isFavorites = isFavorites;
    }

    public EvaluateInfoBean getEvaluate_info() {
        return evaluate_info;
    }

    public void setEvaluate_info(EvaluateInfoBean evaluate_info) {
        this.evaluate_info = evaluate_info;
    }

    public List<GoodsCommendListBean> getGoods_commend_list() {
        return goods_commend_list;
    }

    public void setGoods_commend_list(List<GoodsCommendListBean> goods_commend_list) {
        this.goods_commend_list = goods_commend_list;
    }

    public List<?> getGoods_evaluate_list() {
        return goods_evaluate_list;
    }

    public void setGoods_evaluate_list(List<?> goods_evaluate_list) {
        this.goods_evaluate_list = goods_evaluate_list;
    }

    public static class GoodsInfoBean {
        /**
         * goods_id : 1
         * goods_name : 测试日托服务商品
         * goods_price : 0.01
         * is_virtual : 1
         * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg
         */

        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String is_virtual;
        private String goods_image_url;

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

        public String getIs_virtual() {
            return is_virtual;
        }

        public void setIs_virtual(String is_virtual) {
            this.is_virtual = is_virtual;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }
    }

    public static class EvaluateInfoBean {
        /**
         * good : 0
         * normal : 0
         * bad : 0
         * all : 0
         * good_percent : 100
         * normal_percent : 0
         * bad_percent : 0
         * good_star : 0
         * star_average : 0
         */

        private int good;
        private int normal;
        private int bad;
        private int all;
        private int good_percent;
        private int normal_percent;
        private int bad_percent;
        private int good_star;
        private int star_average;

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getNormal() {
            return normal;
        }

        public void setNormal(int normal) {
            this.normal = normal;
        }

        public int getBad() {
            return bad;
        }

        public void setBad(int bad) {
            this.bad = bad;
        }

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public int getGood_percent() {
            return good_percent;
        }

        public void setGood_percent(int good_percent) {
            this.good_percent = good_percent;
        }

        public int getNormal_percent() {
            return normal_percent;
        }

        public void setNormal_percent(int normal_percent) {
            this.normal_percent = normal_percent;
        }

        public int getBad_percent() {
            return bad_percent;
        }

        public void setBad_percent(int bad_percent) {
            this.bad_percent = bad_percent;
        }

        public int getGood_star() {
            return good_star;
        }

        public void setGood_star(int good_star) {
            this.good_star = good_star;
        }

        public int getStar_average() {
            return star_average;
        }

        public void setStar_average(int star_average) {
            this.star_average = star_average;
        }
    }

    public static class GoodsCommendListBean {
        /**
         * goods_id : 8
         * goods_name : 测试早餐1
         * goods_price : 0.01
         * goods_marketprice : 1.00
         * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/29/2_06256787071214709_240.jpg
         */

        private String goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_marketprice;
        private String goods_image_url;

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

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }
    }
}
