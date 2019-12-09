package com.aite.a.model;

public class CategoryList {
	String gc_id;
	String gc_name;
	String gc_show_in_menu;
	String gc_image;

	public CategoryList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getGc_id() {
		return gc_id;
	}

	public void setGc_id(String gc_id) {
		this.gc_id = gc_id;
	}

	public String getGc_name() {
		return gc_name;
	}

	public void setGc_name(String gc_name) {
		this.gc_name = gc_name;
	}

	public String getGc_show_in_menu() {
		return gc_show_in_menu;
	}

	public void setGc_show_in_menu(String gc_show_in_menu) {
		this.gc_show_in_menu = gc_show_in_menu;
	}

	public String getGc_image() {
		return gc_image;
	}

	public void setGc_image(String gc_image) {
		this.gc_image = gc_image;
	}

	public CategoryList(String gc_id, String gc_name, String gc_show_in_menu, String gc_image) {
		super();
		this.gc_id = gc_id;
		this.gc_name = gc_name;
		this.gc_show_in_menu = gc_show_in_menu;
		this.gc_image = gc_image;
	}

	@Override
	public String toString() {
		return "CategoryList [gc_id=" + gc_id + ", gc_name=" + gc_name + ", gc_show_in_menu=" + gc_show_in_menu + ",gc_image=" + gc_image + "]";
	}

}
