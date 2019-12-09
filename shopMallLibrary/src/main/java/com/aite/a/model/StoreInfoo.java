package com.aite.a.model;

import java.io.Serializable;
import java.util.List;


public class StoreInfoo implements Serializable {

    public String store_id;
    public String store_name;
    public String member_id;
    public String member_name;
    public String avatar;
    public String freight;
    public String store_mansong_rule_list;
    public String store_voucher_list;
    public String store_goods_total;
    public String freight_message;
    public String store_collect;

    public String seller_name;
    public String area_info;
    public String store_label;
    public String store_zy;
    public String goods_count;
    public String store_time;
    public String store_qq;
    public String store_ww;
    public String store_phone;
    public String store_sales;
    public String store_desccredit;
    public String store_servicecredit;
    public String store_deliverycredit;
    public String goods_new_count;
    public String store_banner;
    public List<StoreCredit> credits;
    public store_credit_info store_credit_info;

    public StoreInfoo() {
        super();
    }

    public static class store_credit_info {
        public store_desccredit store_desccredit;
        public store_servicecredit store_servicecredit;
        public store_deliverycredit store_deliverycredit;

        public static class store_desccredit {
            public String text;
            public String credit;
        }

        public static class store_servicecredit {
            public String text;
            public String credit;
        }

        public static class store_deliverycredit {
            public String text;
            public String credit;
        }
    }

    @Override
    public String toString() {
        return "StoreInfoo [store_id=" + store_id + ", store_name=" + store_name + ", member_id=" + member_id + ", member_name=" + member_name + ", avatar=" + avatar + ", freight=" + freight + ", store_mansong_rule_list=" + store_mansong_rule_list + ", store_voucher_list=" + store_voucher_list + ", store_goods_total=" + store_goods_total + ", freight_message=" + freight_message + ", seller_name=" + seller_name + ", area_info=" + area_info + ", store_label=" + store_label + ", store_zy=" + store_zy + ", goods_count=" + goods_count + ", store_time=" + store_time + ", store_qq=" + store_qq + ", store_ww=" + store_ww + ", store_phone=" + store_phone + ", store_sales=" + store_sales + ", store_desccredit=" + store_desccredit + ", store_servicecredit=" + store_servicecredit
                + ", store_deliverycredit=" + store_deliverycredit + ", goods_new_count=" + goods_new_count + ", credits=" + credits + "]";
    }

    public StoreInfoo(String freight, String store_mansong_rule_list, String store_voucher_list, String store_name, String store_goods_total, String freight_message) {
        super();
        this.freight = freight;
        this.store_mansong_rule_list = store_mansong_rule_list;
        this.store_voucher_list = store_voucher_list;
        this.store_name = store_name;
        this.store_goods_total = store_goods_total;
        this.freight_message = freight_message;
    }

    public StoreInfoo(String store_id, String store_name, String member_id, String member_name, String avatar) {
        super();
        this.store_id = store_id;
        this.store_name = store_name;
        this.member_id = member_id;
        this.member_name = member_name;
        this.avatar = avatar;
    }

    public StoreInfoo(String store_avatar, String store_banner, String store_id, String store_name,
                      String seller_name, String area_info, String store_label, String store_zy, String goods_count,
                      String store_time, String store_qq, String store_ww, String store_phone, String store_sales,
                      String store_desccredit, String store_servicecredit, String store_deliverycredit, String goods_new_count, String store_collect,
                      List<StoreCredit> credits) {
        super();
        this.avatar = store_avatar;
        this.store_banner = store_banner;
        this.store_id = store_id;
        this.store_collect = store_collect;
        this.store_name = store_name;
        this.seller_name = seller_name;
        this.area_info = area_info;
        this.store_label = store_label;
        this.store_zy = store_zy;
        this.goods_count = goods_count;
        this.store_time = store_time;
        this.store_qq = store_qq;
        this.store_ww = store_ww;
        this.store_phone = store_phone;
        this.store_sales = store_sales;
        this.store_desccredit = store_desccredit;
        this.store_servicecredit = store_servicecredit;
        this.store_deliverycredit = store_deliverycredit;
        this.goods_new_count = goods_new_count;
        this.credits = credits;
    }


    public static class StoreCredit implements Serializable {
        private static final long serialVersionUID = 165489894L;
        String text;
        String credit;

        public StoreCredit(String text, String credit) {
            super();
            this.text = text;
            this.credit = credit;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        @Override
        public String toString() {
            return "StoreCredit [text=" + text + ", credit=" + credit + "]";
        }

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

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getStore_mansong_rule_list() {
        return store_mansong_rule_list;
    }

    public void setStore_mansong_rule_list(String store_mansong_rule_list) {
        this.store_mansong_rule_list = store_mansong_rule_list;
    }

    public String getStore_voucher_list() {
        return store_voucher_list;
    }

    public void setStore_voucher_list(String store_voucher_list) {
        this.store_voucher_list = store_voucher_list;
    }

    public String getStore_goods_total() {
        return store_goods_total;
    }

    public void setStore_goods_total(String store_goods_total) {
        this.store_goods_total = store_goods_total;
    }

    public String getFreight_message() {
        return freight_message;
    }

    public void setFreight_message(String freight_message) {
        this.freight_message = freight_message;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
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

    public String getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(String goods_count) {
        this.goods_count = goods_count;
    }

    public String getStore_time() {
        return store_time;
    }

    public void setStore_time(String store_time) {
        this.store_time = store_time;
    }

    public String getStore_qq() {
        return store_qq;
    }

    public void setStore_qq(String store_qq) {
        this.store_qq = store_qq;
    }

    public String getStore_ww() {
        return store_ww;
    }

    public void setStore_ww(String store_ww) {
        this.store_ww = store_ww;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getStore_sales() {
        return store_sales;
    }

    public void setStore_sales(String store_sales) {
        this.store_sales = store_sales;
    }

    public String getStore_desccredit() {
        return store_desccredit;
    }

    public void setStore_desccredit(String store_desccredit) {
        this.store_desccredit = store_desccredit;
    }

    public String getStore_servicecredit() {
        return store_servicecredit;
    }

    public void setStore_servicecredit(String store_servicecredit) {
        this.store_servicecredit = store_servicecredit;
    }

    public String getStore_deliverycredit() {
        return store_deliverycredit;
    }

    public void setStore_deliverycredit(String store_deliverycredit) {
        this.store_deliverycredit = store_deliverycredit;
    }

    public String getGoods_new_count() {
        return goods_new_count;
    }

    public void setGoods_new_count(String goods_new_count) {
        this.goods_new_count = goods_new_count;
    }

    public List<StoreCredit> getCredits() {
        return credits;
    }

    public void setCredits(List<StoreCredit> credits) {
        this.credits = credits;
    }

    public String getStore_banner() {
        return store_banner;
    }

    public void setStore_banner(String store_banner) {
        this.store_banner = store_banner;
    }

}
