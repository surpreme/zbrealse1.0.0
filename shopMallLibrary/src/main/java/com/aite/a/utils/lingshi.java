package com.aite.a.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aite.a.model.Area_list;
import com.aite.a.model.CategoryList;

public class lingshi {
	private static lingshi ls = null;

	public static lingshi getInstance() {
		if (ls == null) { // line 12
			ls = new lingshi(); // line 13
		}
		return ls;
	}
	//支付界面
	private String store_id;
	private String store_name;
	private String goods_freight;
	private String freight_message;
	//订单界面的商品信息
	private int position;
	private List<Map<String, Object>> data=new ArrayList<Map<String, Object>>();
	private String order_id;
	private boolean isupdate=false;
	//订单预存款支付
	private String pay;
	//从买家订单界面跳支付
	List<String> goods_names = new ArrayList<String>();
	List<String> goods_url = new ArrayList<String>();
	List<String> order_amount = new ArrayList<String>();
	List<String> shipping_fee = new ArrayList<String>();
	List<String> goods_num1 = new ArrayList<String>();
	List<String> goods_price = new ArrayList<String>();
	List<String> store_name1 = new ArrayList<String>();
	
	List<CategoryList> categoryOne = new ArrayList<CategoryList>();
	List<Area_list> arealist = new ArrayList<Area_list>();
	//店铺详情客服QQ号
	private String store_qq;
	//微信是否登录
	private Boolean islogin=false;
	//首页排序
	List<String> gc_name = new ArrayList<String>();
	//评论列表页数
	public String page_total;
	
	
	public List<Area_list> getArealist() {
		return arealist;
	}

	public void setArealist(List<Area_list> arealist) {
		this.arealist = arealist;
	}

	public List<CategoryList> getCategoryOne() {
		return categoryOne;
	}

	public void setCategoryOne(List<CategoryList> categoryOne) {
		this.categoryOne = categoryOne;
	}

	public List<String> getGc_name() {
		return gc_name;
	}

	public void setGc_name(String gc_name) {
		this.gc_name.add(gc_name);
	}


	public List<String> getGoods_names() {
		return goods_names;
	}


	public void setGoods_names(List<String> goods_names) {
		this.goods_names = goods_names;
	}


	public List<String> getGoods_url() {
		return goods_url;
	}


	public void setGoods_url(List<String> goods_url) {
		this.goods_url = goods_url;
	}


	public List<String> getOrder_amount() {
		return order_amount;
	}


	public void setOrder_amount(List<String> order_amount) {
		this.order_amount = order_amount;
	}


	public List<String> getShipping_fee() {
		return shipping_fee;
	}


	public void setShipping_fee(List<String> shipping_fee) {
		this.shipping_fee = shipping_fee;
	}


	public List<String> getGoods_num1() {
		return goods_num1;
	}


	public void setGoods_num1(List<String> goods_num1) {
		this.goods_num1 = goods_num1;
	}


	public List<String> getGoods_price() {
		return goods_price;
	}


	public void setGoods_price(List<String> goods_price) {
		this.goods_price = goods_price;
	}


	public List<String> getStore_name1() {
		return store_name1;
	}


	public void setStore_name1(List<String> store_name1) {
		this.store_name1 = store_name1;
	}


	public Boolean getIslogin() {
		return islogin;
	}
	
	
	public void setIslogin(Boolean islogin) {
		this.islogin = islogin;
	}


	public String getStore_qq() {
		return store_qq;
	}

	public void setStore_qq(String store_qq) {
		this.store_qq = store_qq;
	}

	
	

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public boolean isIsupdate() {
		return isupdate;
	}

	public void setIsupdate(boolean isupdate) {
		this.isupdate = isupdate;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data.add(data);
	}

	public String getFreight_message() {
		return freight_message;
	}

	public void setFreight_message(String freight_message) {
		this.freight_message = freight_message;
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

	public String getGoods_freight() {
		return goods_freight;
	}

	public void setGoods_freight(String goods_freight) {
		this.goods_freight = goods_freight;
	}

	public lingshi() {
	}

	public lingshi(String store_id, String store_name, String goods_freight) {
		super();
		this.store_id = store_id;
		this.store_name = store_name;
		this.goods_freight = goods_freight;
	}

	public lingshi(String store_id, String store_name, String goods_freight,
			String freight_message) {
		super();
		this.store_id = store_id;
		this.store_name = store_name;
		this.goods_freight = goods_freight;
		this.freight_message = freight_message;
	}
}
