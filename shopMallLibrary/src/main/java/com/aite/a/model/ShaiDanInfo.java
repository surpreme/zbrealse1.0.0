package com.aite.a.model;

import java.util.List;

/**
 * 晒单
 * 
 * @author Administrator
 *
 */
public class ShaiDanInfo {
	public geval_info geval_info;
	public store_info store_info;
	public String ac_id;

	public static class geval_info {
		public String geval_id;
		public String geval_orderid;
		public String geval_orderno;
		public String geval_ordergoodsid;
		public String geval_goodsid;
		public String geval_goodsname;
		public String geval_goodsprice;
		public String geval_goodsimage;
		public String geval_scores;
		public String geval_content;
		public String geval_isanonymous;
		public String geval_addtime;
		public String geval_storeid;
		public String geval_storename;
		public String geval_frommemberid;
		public String geval_frommembername;
		public String geval_state;
		public String geval_remark;
		public String geval_explain;
		public String geval_image;
	}

	public static class store_info {
		public String store_id;
		public String store_name;
		public String grade_id;
		public String member_id;
		public String member_name;
		public String seller_name;
		public String sc_id;
		public String store_company_name;
		public String province_id;
		public String area_info;
		public String store_address;
		public String store_zip;
		public String store_state;
		public String store_close_info;
		public String store_sort;
		public String store_time;
		public String store_end_time;
		public String store_label;
		public String store_banner;
		public String store_avatar;
		public String store_keywords;
		public String store_description;
		public String store_qq;
		public String store_ww;
		public String store_phone;
		public String store_zy;
		public String store_domain;
		public String store_domain_times;
		public String store_recommend;
		public String store_theme;
		public store_credit store_credit;
		public String store_desccredit;
		public String store_servicecredit;
		public String store_deliverycredit;
		public String store_collect;
		public String store_slide;
		public String store_slide_url;
		public String store_stamp;
		public String store_printdesc;
		public String store_sales;
		public List<store_presales> store_presales;
		public String store_workingtime;
		public String store_free_price;
		public String store_decoration_switch;
		public String store_decoration_only;
		public String store_decoration_image_count;
		public String is_own_shop;
		public String bind_all_gc;
		public String store_baozh;
		public String store_baozhopen;
		public String store_baozhrmb;
		public String store_qtian;
		public String store_zhping;
		public String store_erxiaoshi;
		public String store_tuihuo;
		public String store_shiyong;
		public String store_shiti;
		public String store_xiaoxie;
		public String store_huodaofk;
		public String store_points;
		public String city_id;
		public String area_id;
		public String store_type;
		public String rm_id;
		public String agent_comm;
		public String agent_top;
		public String agent_top_top;
		public String agent_top_top_top;
		public String addrole;
		public String store_mb_ad1;
		public String store_mb_ad2;
		public String store_mb_banner;
		public String store_anli_count;
		public String visit_number;
		public String introduce;
		public String store_is_weike;
		public String task_price_number;
		public String store_jion_address;
		public String store_jion_province_id;
		public String store_jion_city_id;
		public String store_jion_area_id;
		public String goods_count;
		public String store_credit_average;
		public String store_credit_percent;

		public static class store_presales {
			public String name;
			public String type;
			public String num;
		}

		public static class store_credit {
			public store_desccredit store_desccredit;
			public store_servicecredit store_servicecredit;
			public store_deliverycredit store_deliverycredit;

			public static class store_desccredit {
				public String text;
				public String credit;
				public String percent;
				public String percent_class;
				public String percent_text;
			}

			public static class store_servicecredit {
				public String text;
				public String credit;
				public String percent;
				public String percent_class;
				public String percent_text;
			}

			public static class store_deliverycredit {
				public String text;
				public String credit;
				public String percent;
				public String percent_class;
				public String percent_text;
			}
		}

	}

}
