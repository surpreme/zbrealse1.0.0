package com.aite.a.model;

import java.util.List;

/**
 * 分类
 * 
 * @author Administrator
 *
 */
public class AmClassify2Info {
	public List<goods_class> goods_class;

	public static class goods_class {
		public String gc_id;
		public String gc_name;
		public String type_id;
		public String type_name;
		public String gc_parent_id;
		public String commis_rate;
		public String gc_sort;
		public String gc_virtual;
		public String gc_title ,gc_image;
		public String gc_keywords;
		public String gc_description;
		public List<child> child;

		public static class child {
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
			public String gc_image;
		}

	}

}
