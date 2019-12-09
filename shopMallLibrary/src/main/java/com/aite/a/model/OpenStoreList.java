package com.aite.a.model;

import java.util.List;

public class OpenStoreList {
	public List<GcList> gc_list;
	public List<GradeList> grade_lists;
	public List<StoreClass> store_class;

	public static class GcList {
		public String gc_description;
		public String gc_id;
		public String gc_title;
		public String commis_rate;
		public String gc_name;
		public String type_name;
		public String gc_sort;
		public String gc_keywords;
		public String type_id;
		public String gc_virtual;
		public String gc_parent_id;
	}

	public static class GradeList {
		public String sg_price;
		public String sg_template;
		public String sg_album_limit;
		public String sg_template_number;
		public String sg_description;
		public String sg_name;
		public String sg_sort;
		public String sg_function;
		public String function_str;
		public String sg_goods_limit;
		public String sg_id;
		public String sg_space_limit;
	}

	public static class StoreClass {
		public String sc_name;
		public String sc_sort;
		public String sc_bail;
		public String sc_id;
	}

	public List<GcList> getGc_list() {
		return gc_list;
	}

	public void setGc_list(List<GcList> gc_list) {
		this.gc_list = gc_list;
	}

	public List<GradeList> getGrade_lists() {
		return grade_lists;
	}

	public void setGrade_lists(List<GradeList> grade_lists) {
		this.grade_lists = grade_lists;
	}

	public List<StoreClass> getStore_class() {
		return store_class;
	}

	public void setStore_class(List<StoreClass> store_class) {
		this.store_class = store_class;
	}
}
