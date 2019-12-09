package com.aite.a.activity.li.bean;

import java.io.Serializable;
import java.util.List;

public class NearBean implements Serializable {

    /**
     * code : 200
     * datas : {"list":[{"store_id":"5","store_name":"冷落","area_info":"天津天津市河东区","store_address":"天津天津市河东区123","store_points":"117.261693,39.126626","store_label":"https://aitecc.com/data/upload/shop/store/05175743716096234.jpg","store_zy":"女装","store_credit":"0","store_recommend":"0","store_sales":"0","city_id":"40","area_id":"56","distance":"9999315"},{"store_id":"6","store_name":"艾特集团","area_info":"广东省深圳市南山区","store_address":"广东省深圳市南山区高新园赋安科技大厦909","store_points":"113.958437,22.543689","store_label":"https://aitecc.com/data/upload/shop/common/default_store_avatar.gif","store_zy":"","store_credit":"0","store_recommend":"0","store_sales":"0","city_id":"291","area_id":"3058","distance":"11624845"},{"store_id":"7","store_name":"熊大的杂货铺","area_info":"黑龙江省大兴安岭地区大兴安岭地区松岭区","store_address":"黑龙江省大兴安岭地区大兴安岭地区松岭区雪山岭23号杨树洞1室","store_points":"124.196104,51.991789","store_label":"https://aitecc.com/data/upload/shop/store/05174833367287051.png","store_zy":"大兴安岭原材料，药材，东三宝，野味","store_credit":"0","store_recommend":"0","store_sales":"0","city_id":"142","area_id":"1810","distance":"8581242"},{"store_id":"10","store_name":"Android百货","area_info":"广东省深圳市南山区","store_address":"广东省深圳市南山区高新园赋安科技大厦909","store_points":"113.958437,22.543689","store_label":"https://aitecc.com/data/upload/shop/common/default_store_avatar.gif","store_zy":"","store_credit":"0","store_recommend":"1","store_sales":"0","city_id":"291","area_id":"3058","distance":"11624845"},{"store_id":"11","store_name":"锅锅百货","area_info":"广东省深圳市南山区","store_address":"广东省深圳市南山区高新园赋安科技大厦909","store_points":"113.958437,22.543689","store_label":"https://aitecc.com/data/upload/shop/store/06003770451369000.jpg","store_zy":"羽绒服 男，羽绒服 女","store_credit":"0","store_recommend":"1","store_sales":"0","city_id":"291","area_id":"3058","distance":"11624845"}],"page":{"hasmore":true,"page_total":36}}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * list : [{"store_id":"5","store_name":"冷落","area_info":"天津天津市河东区","store_address":"天津天津市河东区123","store_points":"117.261693,39.126626","store_label":"https://aitecc.com/data/upload/shop/store/05175743716096234.jpg","store_zy":"女装","store_credit":"0","store_recommend":"0","store_sales":"0","city_id":"40","area_id":"56","distance":"9999315"},{"store_id":"6","store_name":"艾特集团","area_info":"广东省深圳市南山区","store_address":"广东省深圳市南山区高新园赋安科技大厦909","store_points":"113.958437,22.543689","store_label":"https://aitecc.com/data/upload/shop/common/default_store_avatar.gif","store_zy":"","store_credit":"0","store_recommend":"0","store_sales":"0","city_id":"291","area_id":"3058","distance":"11624845"},{"store_id":"7","store_name":"熊大的杂货铺","area_info":"黑龙江省大兴安岭地区大兴安岭地区松岭区","store_address":"黑龙江省大兴安岭地区大兴安岭地区松岭区雪山岭23号杨树洞1室","store_points":"124.196104,51.991789","store_label":"https://aitecc.com/data/upload/shop/store/05174833367287051.png","store_zy":"大兴安岭原材料，药材，东三宝，野味","store_credit":"0","store_recommend":"0","store_sales":"0","city_id":"142","area_id":"1810","distance":"8581242"},{"store_id":"10","store_name":"Android百货","area_info":"广东省深圳市南山区","store_address":"广东省深圳市南山区高新园赋安科技大厦909","store_points":"113.958437,22.543689","store_label":"https://aitecc.com/data/upload/shop/common/default_store_avatar.gif","store_zy":"","store_credit":"0","store_recommend":"1","store_sales":"0","city_id":"291","area_id":"3058","distance":"11624845"},{"store_id":"11","store_name":"锅锅百货","area_info":"广东省深圳市南山区","store_address":"广东省深圳市南山区高新园赋安科技大厦909","store_points":"113.958437,22.543689","store_label":"https://aitecc.com/data/upload/shop/store/06003770451369000.jpg","store_zy":"羽绒服 男，羽绒服 女","store_credit":"0","store_recommend":"1","store_sales":"0","city_id":"291","area_id":"3058","distance":"11624845"}]
         * page : {"hasmore":true,"page_total":36}
         */

        private PageBean page;
        private List<ListBean> list;
        private String error;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * hasmore : true
             * page_total : 36
             */

            private boolean hasmore;
            private int page_total;

            public boolean isHasmore() {
                return hasmore;
            }

            public void setHasmore(boolean hasmore) {
                this.hasmore = hasmore;
            }

            public int getPage_total() {
                return page_total;
            }

            public void setPage_total(int page_total) {
                this.page_total = page_total;
            }
        }

        public static class ListBean {
            /**
             * store_id : 5
             * store_name : 冷落
             * area_info : 天津天津市河东区
             * store_address : 天津天津市河东区123
             * store_points : 117.261693,39.126626
             * store_label : https://aitecc.com/data/upload/shop/store/05175743716096234.jpg
             * store_zy : 女装
             * store_credit : 0
             * store_recommend : 0
             * store_sales : 0
             * city_id : 40
             * area_id : 56
             * distance : 9999315
             */

            private String store_id;
            private String store_name;
            private String area_info;
            private String store_address;
            private String store_points;
            private String store_label;
            private String store_zy;
            private String store_credit;
            private String store_recommend;
            private String store_sales;
            private String city_id;
            private String area_id;
            private String distance;

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

            public String getArea_info() {
                return area_info;
            }

            public void setArea_info(String area_info) {
                this.area_info = area_info;
            }

            public String getStore_address() {
                return store_address;
            }

            public void setStore_address(String store_address) {
                this.store_address = store_address;
            }

            public String getStore_points() {
                return store_points;
            }

            public void setStore_points(String store_points) {
                this.store_points = store_points;
            }

            public String getStore_label() {
                return store_label;
            }

            public void setStore_label(String store_label) {
                this.store_label = store_label;
            }

            public String getStore_zy() {
                return store_zy;
            }

            public void setStore_zy(String store_zy) {
                this.store_zy = store_zy;
            }

            public String getStore_credit() {
                return store_credit;
            }

            public void setStore_credit(String store_credit) {
                this.store_credit = store_credit;
            }

            public String getStore_recommend() {
                return store_recommend;
            }

            public void setStore_recommend(String store_recommend) {
                this.store_recommend = store_recommend;
            }

            public String getStore_sales() {
                return store_sales;
            }

            public void setStore_sales(String store_sales) {
                this.store_sales = store_sales;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }
        }
    }
}
