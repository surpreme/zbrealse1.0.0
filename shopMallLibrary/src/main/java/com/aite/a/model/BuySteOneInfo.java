package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

public class BuySteOneInfo implements Serializable {
	private static final long serialVersionUID = -6827029623890974934L;
	public List<StoreCartlList> storeInfos;
	public AddressInfo2 addressInfo;
	public String freight_hash;
	public String ifshow_offpay;
	public String vat_hash;
	public String is_vat_deny;
	public String available_predeposit;
	public String available_rc_balance;
	public Object inv_info;

	public BuySteOneInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BuySteOneInfo(List<StoreCartlList> storeInfos, AddressInfo2 addressInfo,
			String freight_hash, String ifshow_offpay, String vat_hash,String is_vat_deny,
			String available_predeposit, String available_rc_balance,
			Object inv_info) {
		super();
		this.storeInfos = storeInfos;
		this.addressInfo = addressInfo;
		this.freight_hash = freight_hash;
		this.ifshow_offpay = ifshow_offpay;
		this.vat_hash = vat_hash;
		this.is_vat_deny = is_vat_deny;
		this.available_predeposit = available_predeposit;
		this.available_rc_balance = available_rc_balance;
		this.inv_info = inv_info;
	}

	@Override
	public String toString() {
		return "BuySteOneInfo [storeInfo=" + storeInfos + ", addressInfo="
				+ addressInfo + ", freight_hash=" + freight_hash
				+ ", ifshow_offpay=" + ifshow_offpay + ", vat_hash=" + vat_hash
				+ ", available_predeposit=" + available_predeposit
				+ ", available_rc_balance=" + available_rc_balance
				+ ", inv_info=" + inv_info + "]";
	}

	public static class AddressInfo2 {
		public String dlyp_id;
		public String city_id;
		public String area_id;
		public String address;
		public String address_id;
		public String true_name;
		public String is_default;
		public String mob_phone;
		public String tel_phone;
		public String area_info;
		public String member_id;

		public AddressInfo2() {
			super();
		}

		public AddressInfo2(String dlyp_id, String city_id, String area_id,
				String address, String address_id, String true_name,
				String is_default, String mob_phone, String tel_phone,
				String area_info, String member_id) {
			super();
			this.dlyp_id = dlyp_id;
			this.city_id = city_id;
			this.area_id = area_id;
			this.address = address;
			this.address_id = address_id;
			this.true_name = true_name;
			this.is_default = is_default;
			this.mob_phone = mob_phone;
			this.tel_phone = tel_phone;
			this.area_info = area_info;
			this.member_id = member_id;
		}

	}

	public static class StoreCartlList {
		public String freight;
		public String store_mansong_rule_list;
		public String store_voucher_list;
		public String store_name;
		public String store_goods_total;
		public String leaveamessage;
		public String freight_message;
		public List<GoodsList2> goods_list;

		public StoreCartlList() {
			super();

		}

		public StoreCartlList(String freight, String store_mansong_rule_list,
				String store_voucher_list, String store_name,
				String store_goods_total, String freight_message,
				List<GoodsList2> goods_list) {
			super();
			this.freight = freight;
			this.store_mansong_rule_list = store_mansong_rule_list;
			this.store_voucher_list = store_voucher_list;
			this.store_name = store_name;
			this.store_goods_total = store_goods_total;
			this.freight_message = freight_message;
			this.goods_list = goods_list;
		}

		public static class GoodsList2 {
			public String buyer_id;
			public String gc_id;
			public String goods_num;
			public String state;
			public String goods_image_url;
			public String goods_name;
			public String store_id;
			public String is_fcode;
			public String goods_storage;
			public String goods_image;
			public String goods_vat;
			public String goods_total;
			public String transport_id;
			public String goods_freight;
			public String bl_id;
			public String groupbuy_info;
			public String goods_price;
			public String cart_id;
//			public String xianshi_info;
			public String have_gift;
			public String goods_storage_alarm;
			public String goods_commonid;
			public String goods_id;
			public String storage_state;
			public String store_name;

			public GoodsList2() {
				super();
			}

			public GoodsList2(String buyer_id, String gc_id, String goods_num,
					String state, String goods_image_url, String goods_name,
					String store_id, String is_fcode, String goods_storage,
					String goods_image, String goods_vat, String goods_total,
					String transport_id, String goods_freight, String bl_id,
					String groupbuy_info, String goods_price, String cart_id,
					String xianshi_info, String have_gift,
					String goods_storage_alarm, String goods_commonid,
					String goods_id, String storage_state, String store_name) {
				super();
				this.buyer_id = buyer_id;
				this.gc_id = gc_id;
				this.goods_num = goods_num;
				this.state = state;
				this.goods_image_url = goods_image_url;
				this.goods_name = goods_name;
				this.store_id = store_id;
				this.is_fcode = is_fcode;
				this.goods_storage = goods_storage;
				this.goods_image = goods_image;
				this.goods_vat = goods_vat;
				this.goods_total = goods_total;
				this.transport_id = transport_id;
				this.goods_freight = goods_freight;
				this.bl_id = bl_id;
				this.groupbuy_info = groupbuy_info;
				this.goods_price = goods_price;
				this.cart_id = cart_id;
//				this.xianshi_info = xianshi_info;
				this.have_gift = have_gift;
				this.goods_storage_alarm = goods_storage_alarm;
				this.goods_commonid = goods_commonid;
				this.goods_id = goods_id;
				this.storage_state = storage_state;
				this.store_name = store_name;
			}
		}
	}
}
