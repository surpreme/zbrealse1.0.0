package com.aite.a.model;

import java.io.Serializable;
import java.util.List;

public class GoodsDetailsInfo implements Serializable {
    private static final long serialVersionUID = -6827029623890974934L;
    public GoodsInfo goods_info;
    public List<store_callcenter> store_callcenter;
    public mansong_info mansong_info;
    public String isFavorites;
    public String goods_image;
    public List<GoodsCommendList> goods_commend_list;
    public List<GoodsEvaluateList> goods_evaluate_list;
    public List<spec_goods_list> spec_goods_list;

    public StoreInfoo store_info;
    public Spec spec;

    public static class store_callcenter{
        public String type_name;
        public List<callcenter_list>callcenter_list;
        public static class callcenter_list{
            public String name;
            public String type;
            public String num;
        }
    }

    public static class mansong_info {
        public String mansong_name;
    }

    public static class spec_goods_list {
        public String goods_id;
        public String goods_name;
        public String goods_storage;
        public String goods_price;
        public String goods_marketprice;
        public String goods_image_url;
    }

    public static class Spec {
        public List<SpecList> spec_list;
        public List<SpecImage> spec_images;
        public List<SpecValues> spec_value;
        public List<GoodsSpec> goods_spec;
        public List<GoodsAttr> goods_attr;

        public static class SpecImage {
            public String id;
            public String spec_image;

            public SpecImage(String id, String spec_image) {
                super();
                this.id = id;
                this.spec_image = spec_image;
            }

            @Override
            public String toString() {
                return "SpecImage [id=" + id + ", spec_image=" + spec_image + "]";
            }
        }

        public static class SpecList {
            public String id;
            public String goods_id;

            public SpecList(String id, String goods_id) {
                super();
                this.id = id;
                this.goods_id = goods_id;
            }

            @Override
            public String toString() {
                return "SpecList [id=" + id + ", goods_id=" + goods_id + "]";
            }
        }

        public static class SpecValues {
            public String id;
            public String name;
            public List<SpecValue> spec_value;

            public static class SpecValue {
                public String id;
                public String name;
                public boolean isSelect;

                public SpecValue(String id, String name) {
                    super();
                    this.id = id;
                    this.name = name;
                }

                @Override
                public String toString() {
                    return "SpecValue [id=" + id + ", name=" + name + "]";
                }

                public void setSelect(boolean isSelect) {
                    this.isSelect = isSelect;
                }

            }

            public SpecValues(String id, String name, List<SpecValue> spec_value) {
                super();
                this.id = id;
                this.name = name;
                this.spec_value = spec_value;
            }
        }

        public static class GoodsSpec {
            public String id;
            public String name;

            public GoodsSpec(String id, String name) {
                super();
                this.id = id;
                this.name = name;
            }

            @Override
            public String toString() {
                return "GoodsSpec [id=" + id + ", name=" + name + "]";
            }
        }

        public static class GoodsAttr {
            public String id;
            public String name;

            public GoodsAttr(String id, String name) {
                super();
                this.id = id;
                this.name = name;
            }

            @Override
            public String toString() {
                return "GoodsAttr [id=" + id + ", name=" + name + "]";
            }
        }

        public Spec(List<SpecList> spec_list, List<SpecImage> spec_images, List<SpecValues> spec_value, List<GoodsSpec> goods_spec, List<GoodsAttr> goods_attr) {
            super();
            this.spec_list = spec_list;
            this.spec_images = spec_images;
            this.spec_value = spec_value;
            this.goods_spec = goods_spec;
            this.goods_attr = goods_attr;
        }

    }

    public static class GoodsInfo {
        public String goods_name;
        public String goods_jingle;
        public String goods_unit;
        public String promotion_type;
        public String goods_image_primary;
        public String gc_id_1;
        public String gc_id_2;
        public String area_info;
        public String gc_id_3;
        public String mobile_body;
        public String goods_specname;
        public String goods_price;
        public String goods_marketprice;
        public String goods_costprice;
        public String goods_discount;
        public String goods_serial;
        public String goods_storage_alarm;
        public String transport_id;
        public String transport_title;
        public String goods_freight;
        public String goods_vat;
        public String areaid_1;
        public String areaid_2;
        public String goods_stcids;
        public String plateid_top;
        public String plateid_bottom;
        public String is_virtual;
        public String virtual_indate;
        public String virtual_limit;
        public String virtual_invalid_refund;
        public String is_fcode;
        public String is_appoint;
        public String appoint_satedate;
        public String is_presell;
        public String presell_deliverdate;
        public String is_own_shop;
        public String goods_id;
        public String goods_promotion_price;
        public String promotion_price;
        public String goods_promotion_type;
        public String goods_click;
        public String goods_salenum;
        public String goods_collect;
        public String goods_storage;
        public String color_id;
        public String evaluation_good_star;
        public String evaluation_count;
        public String have_gift;
        public Object groupbuy_info;
        public String xianshi_info;
        public String goods_url;
        public List<goods_param_info> goods_param_info;
        public List<goods_spec_info> goods_spec_info;

        public static class goods_spec_info {
            public String spec_id;
            public String spec_name;
            public List<spec_value> spec_value;

            public static class spec_value {
                public String id;
                public String value;
                public boolean ispick = false;

                @Override
                public String toString() {
                    return "spec_value{" +
                            "id='" + id + '\'' +
                            ", value='" + value + '\'' +
                            ", ispick=" + ispick +
                            '}';
                }
            }
        }

        public static class goods_param_info {
            public String name;
            public List<data> data;

            public static class data {
                public String param_id;
                public String param_name;
                public String is_use;
                public String param_value;
            }
        }


        @Override
        public String toString() {
            return "GoodsInfo [goods_name=" + goods_name + ", goods_jingle=" + goods_jingle + ", gc_id_1=" + gc_id_1 + ", gc_id_2=" + gc_id_2 + ", gc_id_3=" + gc_id_3 + ", mobile_body=" + mobile_body + ", goods_specname=" + goods_specname + ", goods_price=" + goods_price + ", goods_marketprice=" + goods_marketprice + ", goods_costprice=" + goods_costprice + ", goods_discount=" + goods_discount + ", goods_serial=" + goods_serial + ", goods_storage_alarm=" + goods_storage_alarm + ", transport_id=" + transport_id + ", transport_title=" + transport_title + ", goods_freight=" + goods_freight + ", goods_vat=" + goods_vat + ", areaid_1=" + areaid_1 + ", areaid_2=" + areaid_2 + ", goods_stcids=" + goods_stcids + ", plateid_top=" + plateid_top + ", plateid_bottom=" + plateid_bottom
                    + ", is_virtual=" + is_virtual + ", virtual_indate=" + virtual_indate + ", virtual_limit=" + virtual_limit + ", virtual_invalid_refund=" + virtual_invalid_refund + ", is_fcode=" + is_fcode + ", is_appoint=" + is_appoint + ", appoint_satedate=" + appoint_satedate + ", is_presell=" + is_presell + ", presell_deliverdate=" + presell_deliverdate + ", is_own_shop=" + is_own_shop + ", goods_id=" + goods_id + ", goods_promotion_price=" + goods_promotion_price + ", goods_promotion_type=" + goods_promotion_type + ", goods_click=" + goods_click + ", goods_salenum=" + goods_salenum + ", goods_collect=" + goods_collect + ", goods_storage=" + goods_storage + ", color_id=" + color_id + ", evaluation_good_star=" + evaluation_good_star + ", evaluation_count=" + evaluation_count
                    + ", have_gift=" + have_gift + ", groupbuy_info=" + groupbuy_info + ", xianshi_info=" + xianshi_info + ", goods_url=" + goods_url + "]";
        }
    }

    public static class GoodsCommendList {
        public String goods_id;
        public String goods_name;
        public String goods_price;
        public String goods_image_url;

        @Override
        public String toString() {
            return "GoodsCommendList [goods_id=" + goods_id + ", goods_name=" + goods_name + ", goods_price=" + goods_price + ", goods_image_url=" + goods_image_url + "]";
        }
    }

    public static class GoodsEvaluateList {
        public String geval_frommembername;
        public String geval_addtime;
        public String geval_scores;
        public String geval_content;
        public String geval_member_avatar;
        public List<String> geval_image;

        @Override
        public String toString() {
            return "GoodsEvaluateList [geval_frommembername=" + geval_frommembername + ", geval_addtime=" + geval_addtime + ", geval_scores=" + geval_scores + ", geval_content=" + geval_content + "]";
        }
    }

    public static class StoreInfo {
        public String store_id;
        public String store_name;
        public String member_id;
        public String member_name;
        public String avatar;
        public String store_qq;

        @Override
        public String toString() {
            return "StoreInfoo [store_id=" + store_id + ", store_name=" + store_name + ", member_id=" + member_id + ", member_name=" + member_name + ", avatar=" + avatar + ",store_qq=" + store_qq + "]";
        }
    }

//	@Override
//	public String toString() {
//		return "CopyOfBuySteOneInfo [goods_info=" + goods_info
//				+ ", mansong_info=" + mansong_info + ", isFavorites="
//				+ isFavorites + ", goods_image=" + goods_image
//				+ ", goods_commend_list=" + goods_commend_list
//				+ ", goods_evaluate_list=" + goods_evaluate_list
//				+ ", store_info=" + store_info + "]";
//	}

}
