package com.aite.a.model;

import java.util.List;

/**
 * 社区
 * 
 * @author Administrator
 *
 */
public class Communityallinfo {

	public String hasmore;
	public String page_total;
	public datas datas;

	public static class datas {
		public List<list> list;

		public static class list {
			public String circle_id;
			public String circle_name;
			public String circle_desc;
			public String circle_masterid;
			public String circle_mastername;
			public String circle_img;
			public String class_id;
			public String circle_mcount;
			public String circle_thcount;
			public String circle_gcount;
			public String circle_pursuereason;
			public String circle_notice;
			public String circle_status;
			public String circle_statusinfo;
			public String circle_joinaudit;
			public String circle_addtime;
			public String circle_noticetime;
			public String is_recommend;
			public String is_hot;
			public String circle_tag;
			public String new_verifycount;
			public String new_informcount;
			public String mapply_open;
			public String mapply_ml;
			public String new_mapplycount;
		}
	}

}
