package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

/**
 * 发布服务分类
 * 
 * @author Administrator
 *
 */
public class ReleaseTaskInfo implements Serializable{

	public String gc_id;
	public String gc_name;
	public String type_id;
	public String type_name;
	public String gc_parent_id;
	public String commis_rate;
	public String gc_sort;
	public String gc_virtual;
	public String gc_title;
	public String gc_keywords;
	public String gc_description;
	public String gc_show_in_menu;
	public String gc_bind_param_type_id;
	public String IsSystem;
	public String IsEnable;
	public List<class2> class2;

	public static class class2 {
		public String gc_id;
		public String gc_name;
		public String type_id;
		public String type_name;
		public String gc_parent_id;
		public String commis_rate;
		public String gc_sort;
		public String gc_virtual;
		public String gc_title;
		public String gc_keywords;
		public String gc_description;
		public String gc_show_in_menu;
		public String gc_bind_param_type_id;
		public String IsSystem;
		public String IsEnable;
		public List<class3> class3;

		public static class class3 {
			public String gc_id;
			public String gc_name;
			public String type_id;
			public String type_name;
			public String gc_parent_id;
			public String commis_rate;
			public String gc_sort;
			public String gc_virtual;
			public String gc_title;
			public String gc_keywords;
			public String gc_description;
			public String gc_show_in_menu;
			public String gc_bind_param_type_id;
			public String IsSystem;
			public String IsEnable;
		}
	}

}
