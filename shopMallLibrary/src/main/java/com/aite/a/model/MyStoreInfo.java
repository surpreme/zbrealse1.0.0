package com.aite.a.model;

import java.util.List;

/**
 * 我的店铺实体类
 * 
 * @author CAOYOU
 *
 */
public class MyStoreInfo {
	public String daily_sales;
	public List<String> phone_array;
	public String monthly_sales;
	public List<GoodsList> goods_list;
	public StoreInfo store_info;
	public String sold_out;
	public String all_shop_count;
	public String onsell;
	public String total_money;
	public String Yesterday_total;

	public class DailySales {
		public String orderamount;
		public String ordernum;
		public String total_order_amount;

		@Override
		public String toString() {
			return "DailySales [orderamount=" + orderamount + ", ordernum="
					+ ordernum + "]";
		}
	}

	public class MonthlySales {
		public String orderamount;
		public String ordernum;
		public String total_order_amount;

		@Override
		public String toString() {
			return "MonthlySales [orderamount=" + orderamount + ", ordernum="
					+ ordernum + "]";
		}
	}

	public class GoodsList {
		public String goods_id;
		public String goods_name;
		public String goodsnum;
		public String goods_image;

		@Override
		public String toString() {
			return "GoodsList [goods_id=" + goods_id + ", goods_name="
					+ goods_name + ", goodsnum=" + goodsnum + ", goods_image="
					+ goods_image + "]";
		}
	}

	public class StoreInfo {
		public StoreCredit store_credit;
		public String seller_name;
		public String grade_name;
		public String store_sales;
		public String grade_albumlimit;
		public String store_servicecredit;
		public String store_close_info;
		public String store_zhping;
		public String store_free_price;
		public String store_domain_times;
		public String store_theme;
		public String store_tuihuo;
		public String store_state;
		public String store_qq;
		public String store_end_time_text;
		public String store_workingtime;
		public String store_name;
		public String goods_count;
		public String store_vrcode_prefix;
		public String province_id;
		public String store_deliverycredit;
		public String store_baozh;
		public String store_id;
		public String store_points;
		public String store_qtian;
		public String store_banner;
		public String store_shiti;
		public String store_slide_url;
		public String live_store_bus;
		public String live_store_address;
		public String store_baozhrmb;
		public String bind_all_gc;
		public String store_recommend;
		public String store_desccredit;
		public String store_time;
		public String grade_id;
		public String store_credit_percent;
		public String dlyp_state;
		public String store_keywords;
		public String area_id;
		public String store_type;
		public String store_decoration_image_count;
		public String store_credit_average;
		public String store_company_name;
		public String store_phone;
		public String store_domain;
		public String store_decoration_only;
		public String store_collect;
		public String area_info;
		public String city_id;
		public String store_description;
		public String live_store_name;
		public String store_stamp;
		public String store_ww;
		public String store_baozhopen;
		public String store_xiaoxie;
		public String store_erxiaoshi;
		public String store_address;
		public String store_label;
		public String store_sort;
		public String store_zip;
		public String is_own_shop;
		public String store_slide;
		public String store_shiyong;
		public String live_store_tel;
		public String store_zy;
		public String member_id;
		public String grade_goodslimit;
		public List<Sales> store_aftersales;
		public String sc_id;
		public String store_printdesc;
		public List<Sales> store_presales;
		public String store_huodaofk;
		public String member_name;
		public String store_end_time;
		public String store_decoration_switch;
		public String store_avatar;

		public class Sales {
			public String name;
			public String type;
			public String num;

			@Override
			public String toString() {
				return "Sales [name=" + name + ", type=" + type + ", num="
						+ num + "]";
			}

		}

		public class StoreCredit {
			public StoreDeliverycredit store_deliverycredit;
			public StoreServicecredit store_servicecredit;
			public StoreDesccredit store_desccredit;

			public class StoreDeliverycredit {
				public String text;
				public String percent;
				public String percent_text;
				public String percent_class;
				public String credit;

				@Override
				public String toString() {
					return "StoreDeliverycredit [text=" + text + ", percent="
							+ percent + ", percent_text=" + percent_text
							+ ", percent_class=" + percent_class + ", credit="
							+ credit + "]";
				}

			}

			public class StoreServicecredit {
				public String text;
				public String percent;
				public String percent_text;
				public String percent_class;
				public String credit;

				@Override
				public String toString() {
					return "StoreServicecredit [text=" + text + ", percent="
							+ percent + ", percent_text=" + percent_text
							+ ", percent_class=" + percent_class + ", credit="
							+ credit + "]";
				}
			}

			public class StoreDesccredit {
				public String text;
				public String percent;
				public String percent_text;
				public String percent_class;
				public String credit;

				@Override
				public String toString() {
					return "StoreDesccredit [text=" + text + ", percent="
							+ percent + ", percent_text=" + percent_text
							+ ", percent_class=" + percent_class + ", credit="
							+ credit + "]";
				}
			}

			@Override
			public String toString() {
				return "StoreCredit [store_deliverycredit="
						+ store_deliverycredit + ", store_servicecredit="
						+ store_servicecredit + ", store_desccredit="
						+ store_desccredit + "]";
			}
		}

		@Override
		public String toString() {
			return "StoreInfoo [seller_name=" + seller_name + ", grade_name="
					+ grade_name + ", store_sales=" + store_sales
					+ ", grade_albumlimit=" + grade_albumlimit
					+ ", store_servicecredit=" + store_servicecredit
					+ ", store_close_info=" + store_close_info
					+ ", store_credit=" + store_credit + ", store_zhping="
					+ store_zhping + ", store_free_price=" + store_free_price
					+ ", store_domain_times=" + store_domain_times
					+ ", store_theme=" + store_theme + ", store_tuihuo="
					+ store_tuihuo + ", store_state=" + store_state
					+ ", store_qq=" + store_qq + ", store_end_time_text="
					+ store_end_time_text + ", store_workingtime="
					+ store_workingtime + ", store_name=" + store_name
					+ ", goods_count=" + goods_count + ", store_vrcode_prefix="
					+ store_vrcode_prefix + ", province_id=" + province_id
					+ ", store_deliverycredit=" + store_deliverycredit
					+ ", store_baozh=" + store_baozh + ", store_id=" + store_id
					+ ", store_points=" + store_points + ", store_qtian="
					+ store_qtian + ", store_banner=" + store_banner
					+ ", store_shiti=" + store_shiti + ", store_slide_url="
					+ store_slide_url + ", live_store_bus=" + live_store_bus
					+ ", live_store_address=" + live_store_address
					+ ", store_baozhrmb=" + store_baozhrmb + ", bind_all_gc="
					+ bind_all_gc + ", store_recommend=" + store_recommend
					+ ", store_desccredit=" + store_desccredit
					+ ", store_time=" + store_time + ", grade_id=" + grade_id
					+ ", store_credit_percent=" + store_credit_percent
					+ ", store_keywords=" + store_keywords + ", area_id="
					+ area_id + ", store_decoration_image_count="
					+ store_decoration_image_count + ", store_credit_average="
					+ store_credit_average + ", store_company_name="
					+ store_company_name + ", store_phone=" + store_phone
					+ ", store_domain=" + store_domain
					+ ", store_decoration_only=" + store_decoration_only
					+ ", store_collect=" + store_collect + ", area_info="
					+ area_info + ", city_id=" + city_id
					+ ", store_description=" + store_description
					+ ", live_store_name=" + live_store_name + ", store_stamp="
					+ store_stamp + ", store_ww=" + store_ww
					+ ", store_baozhopen=" + store_baozhopen
					+ ", store_xiaoxie=" + store_xiaoxie + ", store_erxiaoshi="
					+ store_erxiaoshi + ", store_address=" + store_address
					+ ", store_label=" + store_label + ", store_sort="
					+ store_sort + ", store_zip=" + store_zip
					+ ", is_own_shop=" + is_own_shop + ", store_slide="
					+ store_slide + ", store_shiyong=" + store_shiyong
					+ ", live_store_tel=" + live_store_tel + ", store_zy="
					+ store_zy + ", member_id=" + member_id
					+ ", grade_goodslimit=" + grade_goodslimit
					+ ", store_aftersales=" + store_aftersales + ", sc_id="
					+ sc_id + ", store_printdesc=" + store_printdesc
					+ ", store_presales=" + store_presales
					+ ", store_huodaofk=" + store_huodaofk + ", member_name="
					+ member_name + ", store_end_time=" + store_end_time
					+ ", store_decoration_switch=" + store_decoration_switch
					+ ", store_avatar=" + store_avatar + "]";
		}
	}

	@Override
	public String toString() {
		return "MyStoreInfo [daily_sales=" + daily_sales + ", phone_array="
				+ phone_array + ", monthly_sales=" + monthly_sales
				+ ", goods_list=" + goods_list + ", store_info=" + store_info
				+ "]";
	}

}
