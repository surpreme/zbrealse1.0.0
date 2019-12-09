package com.aite.a.activity.li.bean;

import java.util.List;

public class BrandInnerBean {
    private List<ImageBean> image;

    public List<ImageBean> getImage() {
        return image;
    }

    public void setImage(List<ImageBean> image) {
        this.image = image;
    }

    public static class ImageBean {
        /**
         * brand_id : 90
         * brand_name_bak : 贝亲
         * brand_initial : B
         * brand_class_bak : 母婴用品
         * brand_pic : https://aitecc.com/data/upload/shop/brand/05955950488476660_sm.png
         * brand_sort : 0
         * brand_recommend : 1
         * brand_href : 0
         * store_id : 0
         * brand_apply : 1
         * class_id : 857
         * show_type : 0
         */

        private String brand_id;
        private String brand_name_bak;
        private String brand_initial;
        private String brand_class_bak;
        private String brand_pic;
        private String brand_sort;
        private String brand_recommend;
        private String brand_href;
        private String store_id;
        private String brand_apply;
        private String class_id;
        private String show_type;

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name_bak() {
            return brand_name_bak;
        }

        public void setBrand_name_bak(String brand_name_bak) {
            this.brand_name_bak = brand_name_bak;
        }

        public String getBrand_initial() {
            return brand_initial;
        }

        public void setBrand_initial(String brand_initial) {
            this.brand_initial = brand_initial;
        }

        public String getBrand_class_bak() {
            return brand_class_bak;
        }

        public void setBrand_class_bak(String brand_class_bak) {
            this.brand_class_bak = brand_class_bak;
        }

        public String getBrand_pic() {
            return brand_pic;
        }

        public void setBrand_pic(String brand_pic) {
            this.brand_pic = brand_pic;
        }

        public String getBrand_sort() {
            return brand_sort;
        }

        public void setBrand_sort(String brand_sort) {
            this.brand_sort = brand_sort;
        }

        public String getBrand_recommend() {
            return brand_recommend;
        }

        public void setBrand_recommend(String brand_recommend) {
            this.brand_recommend = brand_recommend;
        }

        public String getBrand_href() {
            return brand_href;
        }

        public void setBrand_href(String brand_href) {
            this.brand_href = brand_href;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getBrand_apply() {
            return brand_apply;
        }

        public void setBrand_apply(String brand_apply) {
            this.brand_apply = brand_apply;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getShow_type() {
            return show_type;
        }

        public void setShow_type(String show_type) {
            this.show_type = show_type;
        }
    }
}
