package com.aite.a.model;

public class ManageCategoryList {
	public String one_category;
	public String one_category_id;
	public String two_category;
	public String two_category_id;
	public String three_category;
	public String three_category_id;

	public String toStringName() {
		return one_category + ", " + two_category + ", " + three_category;
	}

	public String toStringId() {
		return one_category_id + ", " + two_category_id + ", "
				+ three_category_id;
	}

	public ManageCategoryList(String one_category, String one_category_id,
			String two_category, String two_category_id, String three_category,
			String three_category_id) {
		super();
		this.one_category = one_category;
		this.one_category_id = one_category_id;
		this.two_category = two_category;
		this.two_category_id = two_category_id;
		this.three_category = three_category;
		this.three_category_id = three_category_id;
	}

}
