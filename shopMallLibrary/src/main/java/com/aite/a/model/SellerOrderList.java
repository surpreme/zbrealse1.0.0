package com.aite.a.model;

import java.util.List;

public class SellerOrderList {
	public String order_id;
	public String order_sn;
	public String pay_sn;
	public String store_id;
	public String store_name;
	public String buyer_id;
	public String buyer_name;
	public String buyer_email;
	public String add_time;
	public String payment_code;
	public String payment_time;
	public String finnshed_time;
	public String goods_amount;
	public String order_amount;
	public String rcb_amount;
	public String pd_amount;
	public String shipping_fee;
	public String evaluation_state;
	public String order_state;
	public String refund_state;
	public String lock_state;
	public String delete_state;
	public String refund_amount;
	public String delay_time;
	public String order_from;
	public String shipping_code;
	public String state_desc;
	public String payment_name;
	public String if_cancel;
	public String if_modify_price;
	public String if_goods_price;
	public String if_send;
	public String if_lock;
	public String if_deliver;
	public String goods_count;
	public ExtendOrderCommon extend_order_common;
	public ExtendMember extend_member;
	public List<ExtendOrderGoods> extend_order_goods;
	public List<GoodsList> goods_list;

	public class ExtendOrderCommon {
		public String order_id;
		public String store_id;
		public String shipping_time;
		public String shipping_express_id;
		public String evaluation_time;
		public String evalseller_state;
		public String evalseller_time;
		public String order_message;
		public String order_pointscount;
		public String voucher_price;
		public String voucher_code;
		public String deliver_explain;
		public String daddress_id;
		public String reciver_name;
		public String reciver_province_id;
		public String reciver_city_id;
		public String dlyo_pickup_code;
		public Object promotion_info;
		public ReciverInfo reciver_info;

		public class ReciverInfo {
			public String phone;
			public String mob_phone;
			public String tel_phone;
			public String address;
			public String area;
			public String street;
		}

	}

	public class ExtendMember {
		public String member_id;
		public String member_name;
		public String member_truename;
		public String member_avatar;
		public String member_sex;
		public String member_birthday;
		public String member_passwd;
		public String member_paypwd;
		public String member_email;
		public String member_email_bind;
		public String member_mobile;
		public String member_mobile_bind;
		public String member_qq;
		public String member_ww;
		public String member_login_num;
		public String member_time;
		public String member_login_time;
		public String member_old_login_time;
		public String member_login_ip;
		public String member_old_login_ip;
		public String member_qqopenid;
		public String member_qqinfo;
		public String member_sinaopenid;
		public String member_sinainfo;
		public String member_points;
		public String available_predeposit;
		public String freeze_predeposit;
		public String available_rc_balance;
		public String freeze_rc_balance;
		public String inform_allow;
		public String is_buy;
		public String is_allowtalk;
		public String member_state;
		public String member_snsvisitnum;
		public String member_areaid;
		public String member_cityid;
		public String member_provinceid;
		public String member_areainfo;
		public String member_privacy;
		public String member_quicklink;
		public String member_exppoints;
	}

	public class ExtendOrderGoods {
		public String rec_id;
		public String order_id;
		public String goods_id;
		public String goods_name;
		public String goods_price;
		public String goods_num;
		public String goods_image;
		public String goods_pay_price;
		public String store_id;
		public String buyer_id;
		public String goods_type;
		public String promotions_id;
		public String commis_rate;
		public String gc_id;
	}

	public class GoodsList {
		public String rec_id;
		public String order_id;
		public String goods_id;
		public String goods_name;
		public String goods_price;
		public String goods_num;
		public String goods_image;
		public String goods_pay_price;
		public String store_id;
		public String buyer_id;
		public String goods_type;
		public String promotions_id;
		public String commis_rate;
		public String gc_id;
		public String image_60_url;
		public String image_240_url;
		public String goods_type_cn;
		public String goods_url;
	}

}
