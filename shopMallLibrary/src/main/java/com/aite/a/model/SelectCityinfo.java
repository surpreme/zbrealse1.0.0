package com.aite.a.model;

public class SelectCityinfo {

	private String area_id;
	private String area_name;
	private String area_parent_id;
	private String area_sort;
	private String area_deep;
	private String area_region;
	private String a_letter;
	private String shopcity_open;

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getArea_parent_id() {
		return area_parent_id;
	}

	public void setArea_parent_id(String area_parent_id) {
		this.area_parent_id = area_parent_id;
	}

	public String getArea_sort() {
		return area_sort;
	}

	public void setArea_sort(String area_sort) {
		this.area_sort = area_sort;
	}

	public String getArea_deep() {
		return area_deep;
	}

	public void setArea_deep(String area_deep) {
		this.area_deep = area_deep;
	}

	public String getArea_region() {
		return area_region;
	}

	public void setArea_region(String area_region) {
		this.area_region = area_region;
	}

	public String getA_letter() {
		return a_letter;
	}

	public void setA_letter(String a_letter) {
		this.a_letter = a_letter;
	}

	public String getShopcity_open() {
		return shopcity_open;
	}

	public void setShopcity_open(String shopcity_open) {
		this.shopcity_open = shopcity_open;
	}

	public SelectCityinfo() {
	}

	public SelectCityinfo(String area_id, String area_name,
			String area_parent_id, String area_sort, String area_deep,
			String area_region, String a_letter, String shopcity_open) {
		super();
		this.area_id = area_id;
		this.area_name = area_name;
		this.area_parent_id = area_parent_id;
		this.area_sort = area_sort;
		this.area_deep = area_deep;
		this.area_region = area_region;
		this.a_letter = a_letter;
		this.shopcity_open = shopcity_open;
	}

	@Override
	public String toString() {
		return "SelectCityinfo [area_id=" + area_id + ", area_name="
				+ area_name + ", area_parent_id=" + area_parent_id
				+ ", area_sort=" + area_sort + ", area_deep=" + area_deep
				+ ", area_region=" + area_region + ", a_letter=" + a_letter
				+ ", shopcity_open=" + shopcity_open + "]";
	}
}
