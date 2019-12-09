package com.aite.a.model;

import java.io.Serializable;

public class GoodsManageList implements Serializable {
	private static final long serialVersionUID = -3904741286555327069L;
	public String commonid;
	public String goods_name;
	public String gc_id_1;
	public String gc_id_2;
	public String gc_id_3;
	public String goods_image;
	public String goods_price;
	public String goods_marketprice;
	public String goods_costprice;
	public String goods_discount;
	public String goods_storage;
	public String province_id;
	public String city_id;
	public String goods_freight;
	public String brand_name;
	public String goods_commonid;
	public String goods_selltime;
	public String store_name;
	public String appoint_satedate;
	public String goods_lock;
	public String brand_id;
	public String store_id;
	public String is_fcode;
	public String is_virtual;
	public String goods_state;
	public String goods_stateremark;
	public String goods_attr;
	public String spec_value;
	public String plateid_bottom;
	public String mobile_body;
	public String goods_body;
	public String goods_storage_alarm;
	public String virtual_invalid_refund;
	public String gc_id;
	public String goods_serial;
	public String goods_verify;
	public String transport_id;
	public String areaid_1;
	public String presell_deliverdate;
	public String areaid_2;
	public String goods_verifyremark;
	public String virtual_indate;
	public String is_presell;
	public String goods_specname;
	public String is_appoint;
	public String transport_title;
	public String is_own_shop;
	public String goods_addtime;
	public String goods_vat;
	public String type_id;
	public String virtual_limit;
	public String spec_name;
	public String plateid_top;
	public String goods_stcids;
	public String goods_commend;
	public String gc_name;
	public String goods_jingle;

	@Override
	public String toString() {
		return "GoodsManageList [commonid=" + commonid + ", goods_name="
				+ goods_name + ", gc_id_1=" + gc_id_1 + ", gc_id_2=" + gc_id_2
				+ ", gc_id_3=" + gc_id_3 + ", goods_image=" + goods_image
				+ ", goods_price=" + goods_price + ", goods_marketprice="
				+ goods_marketprice + ", goods_costprice=" + goods_costprice
				+ ", goods_discount=" + goods_discount + ", goods_storage="
				+ goods_storage + ", province_id=" + province_id + ", city_id="
				+ city_id + ", goods_freight=" + goods_freight + "]";
	}

}
