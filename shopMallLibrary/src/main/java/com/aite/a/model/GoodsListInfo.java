package com.aite.a.model;

import java.util.List;

/**
 * 商品列表
 * Created by Administrator on 2017/6/7.
 */
public class GoodsListInfo {
    public List<goods_list> goods_list;
    public list_page list_page;

    public static class goods_list {
        public String goods_id;
        public String goods_commonid;
        public String goods_name;
        public String goods_short_title;
        public String goods_jingle;
        public String store_id;
        public String store_name;
        public String goods_price;
        public String goods_image;
       
		@Override
		public String toString() {
			return "goods_list [goods_id=" + goods_id + ", goods_commonid="
					+ goods_commonid + ", goods_name=" + goods_name
					+ ", goods_short_title=" + goods_short_title
					+ ", goods_jingle=" + goods_jingle + ", store_id="
					+ store_id + ", store_name=" + store_name
					+ ", goods_price=" + goods_price + ", goods_image="
					+ goods_image + "]";
		}
        
    }

    public static class list_page {
        public String hasmore;
        public String page_total;
    }

}
