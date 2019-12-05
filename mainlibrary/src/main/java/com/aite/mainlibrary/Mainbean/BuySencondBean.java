package com.aite.mainlibrary.Mainbean;

import com.google.gson.annotations.SerializedName;
import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

public class BuySencondBean extends ErrorBean implements Serializable {

    /**
     * goods_info : {"goods_id":"2","goods_commonid":"2","goods_name":"日托服务商品","goods_short_title":"","goods_jingle":"","goods_unit":"","store_id":"2","store_name":"艾特技术","gc_id":"13","gc_id_1":"2","gc_id_2":"5","gc_id_3":"13","brand_id":"0","goods_price":"0.10","goods_promotion_price":"0.10","goods_promotion_type":"0","goods_marketprice":"1.00","goods_costprice":"0.00","goods_serial":"","goods_storage_alarm":"0","goods_click":"12","goods_salenum":"0","goods_collect":"0","goods_spec":"N;","goods_storage":"100","goods_image":"2019/10/31/2_06258484919002918.jpg","goods_state":"1","goods_verify":"1","goods_addtime":"1572504538","goods_edittime":"1575355908","areaid_1":"0","areaid_2":"0","color_id":"0","transport_id":"0","goods_freight":"0.00","goods_vat":"0","goods_commend":"1","goods_stcids":"","evaluation_good_star":"0","evaluation_count":"0","is_virtual":"1","virtual_indate":"1734191999","virtual_limit":"10","virtual_invalid_refund":"0","is_fcode":"0","is_appoint":"0","is_presell":"0","have_gift":"0","is_own_shop":"0","level_0_price":"0.10","level_1_price":"0.10","level_2_price":"0.10","level_3_price":"0.10","level_0_auth_price":"0.10","level_1_auth_price":"0.10","level_2_auth_price":"0.10","level_3_auth_price":"0.10","is_more_discount":"0","goods_type":"0","parent_commonid":"0","parent_goodsid":"0","wholesale_price":"","is_service":"0","is_installment":"0","installment_money":"N;","is_vat":"0","goods_salenum_vr":"0","goods_start_vr":"0","is_index_select":"0","goods_param":"","is_kuajing":"0","origin":null,"kj_service":null,"native":null,"is_native":null,"seaport":null,"clearance":null,"clearance_sn":null,"goods_points_offset":"0","is_visit_comm":"0","is_Independent_comm":"0","comm_rule":null,"is_buy_apply":"1","service_stime":"1572658200","service_etime":"1572669000","service_hours":"3","goods_shipping_fee":"0.00","quantity":1,"goods_total":"0.10","goods_image_url":"http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_240.jpg"}
     * store_info : {"store_id":"2","store_name":"艾特技术","grade_id":"1","member_id":"2","member_name":"艾特技术","seller_name":"艾特技术","sc_id":"0","store_company_name":null,"province_id":"0","area_info":"","store_address":"","store_zip":"","store_state":"1","store_close_info":"","store_sort":"0","store_time":"1571655808","store_end_time":"","store_label":null,"store_banner":null,"store_avatar":null,"store_keywords":"","store_description":"","store_qq":null,"store_ww":null,"store_phone":null,"store_zy":null,"store_domain":null,"store_domain_times":"0","store_recommend":"1","store_theme":"default","store_credit":{"store_desccredit":{"text":"描述相符","credit":5},"store_servicecredit":{"text":"服务态度","credit":5},"store_deliverycredit":{"text":"发货速度","credit":5}},"store_desccredit":"0","store_servicecredit":"0","store_deliverycredit":"0","store_collect":"0","store_slide":null,"store_slide_url":null,"store_stamp":null,"store_printdesc":null,"store_sales":"72","store_presales":null,"store_aftersales":null,"store_workingtime":null,"store_free_price":"0.00","store_decoration_switch":"0","store_decoration_only":"0","store_decoration_image_count":"0","live_store_name":null,"live_store_address":null,"live_store_tel":null,"live_store_bus":null,"is_own_shop":"0","bind_all_gc":"1","store_vrcode_prefix":null,"store_baozh":"0","store_baozhopen":"0","store_baozhrmb":"零","store_qtian":"0","store_zhping":"0","store_erxiaoshi":"0","store_tuihuo":"0","store_shiyong":"0","store_shiti":"0","store_xiaoxie":"0","store_huodaofk":"0","store_points":"","city_id":"0","area_id":"0","store_type":"0","store_agent_level":null,"store_agent_area_id":"0","rm_id":"0","agent_comm":"0","agent_top":"0","agent_top_top":"0","agent_top_top_top":"0","addrole":"0","store_mb_slide":null,"store_mb_slide_url":null,"store_mb_ad1":null,"store_mb_ad2":null,"store_mb_ad1_url":null,"store_mb_ad2_url":null,"store_mb_banner":null,"store_anli_count":"0","visit_number":"0","introduce":"","store_is_weike":"1","task_price_number":"0","store_bank_info":"","store_industry":"0","store_open_rush":"0","goods_page_show_nav":"1","store_info_remark":null,"is_store_nursing":"1","goods_count":"19","store_credit_average":5,"store_credit_percent":100}
     * member_info : {"member_mobile":"18614079738","available_predeposit":"0.00","available_rc_balance":"0.00"}
     */

    private GoodsInfoBean goods_info;
    private StoreInfoBean store_info;
    private MemberInfoBean member_info;

    public GoodsInfoBean getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(GoodsInfoBean goods_info) {
        this.goods_info = goods_info;
    }

    public StoreInfoBean getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreInfoBean store_info) {
        this.store_info = store_info;
    }

    public MemberInfoBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoBean member_info) {
        this.member_info = member_info;
    }

    public static class GoodsInfoBean {
        /**
         * goods_id : 2
         * goods_commonid : 2
         * goods_name : 日托服务商品
         * goods_short_title :
         * goods_jingle :
         * goods_unit :
         * store_id : 2
         * store_name : 艾特技术
         * gc_id : 13
         * gc_id_1 : 2
         * gc_id_2 : 5
         * gc_id_3 : 13
         * brand_id : 0
         * goods_price : 0.10
         * goods_promotion_price : 0.10
         * goods_promotion_type : 0
         * goods_marketprice : 1.00
         * goods_costprice : 0.00
         * goods_serial :
         * goods_storage_alarm : 0
         * goods_click : 12
         * goods_salenum : 0
         * goods_collect : 0
         * goods_spec : N;
         * goods_storage : 100
         * goods_image : 2019/10/31/2_06258484919002918.jpg
         * goods_state : 1
         * goods_verify : 1
         * goods_addtime : 1572504538
         * goods_edittime : 1575355908
         * areaid_1 : 0
         * areaid_2 : 0
         * color_id : 0
         * transport_id : 0
         * goods_freight : 0.00
         * goods_vat : 0
         * goods_commend : 1
         * goods_stcids :
         * evaluation_good_star : 0
         * evaluation_count : 0
         * is_virtual : 1
         * virtual_indate : 1734191999
         * virtual_limit : 10
         * virtual_invalid_refund : 0
         * is_fcode : 0
         * is_appoint : 0
         * is_presell : 0
         * have_gift : 0
         * is_own_shop : 0
         * level_0_price : 0.10
         * level_1_price : 0.10
         * level_2_price : 0.10
         * level_3_price : 0.10
         * level_0_auth_price : 0.10
         * level_1_auth_price : 0.10
         * level_2_auth_price : 0.10
         * level_3_auth_price : 0.10
         * is_more_discount : 0
         * goods_type : 0
         * parent_commonid : 0
         * parent_goodsid : 0
         * wholesale_price :
         * is_service : 0
         * is_installment : 0
         * installment_money : N;
         * is_vat : 0
         * goods_salenum_vr : 0
         * goods_start_vr : 0
         * is_index_select : 0
         * goods_param :
         * is_kuajing : 0
         * origin : null
         * kj_service : null
         * native : null
         * is_native : null
         * seaport : null
         * clearance : null
         * clearance_sn : null
         * goods_points_offset : 0
         * is_visit_comm : 0
         * is_Independent_comm : 0
         * comm_rule : null
         * is_buy_apply : 1
         * service_stime : 1572658200
         * service_etime : 1572669000
         * service_hours : 3
         * goods_shipping_fee : 0.00
         * quantity : 1
         * goods_total : 0.10
         * goods_image_url : http://zhongbyi.aitecc.com/data/upload/shop/store/goods/2/2019/10/31/2_06258484919002918_240.jpg
         */

        private String goods_id;
        private String goods_commonid;
        private String goods_name;
        private String goods_short_title;
        private String goods_jingle;
        private String goods_unit;
        private String store_id;
        private String store_name;
        private String gc_id;
        private String gc_id_1;
        private String gc_id_2;
        private String gc_id_3;
        private String brand_id;
        private String goods_price;
        private String goods_promotion_price;
        private String goods_promotion_type;
        private String goods_marketprice;
        private String goods_costprice;
        private String goods_serial;
        private String goods_storage_alarm;
        private String goods_click;
        private String goods_salenum;
        private String goods_collect;
        private String goods_spec;
        private String goods_storage;
        private String goods_image;
        private String goods_state;
        private String goods_verify;
        private String goods_addtime;
        private String goods_edittime;
        private String areaid_1;
        private String areaid_2;
        private String color_id;
        private String transport_id;
        private String goods_freight;
        private String goods_vat;
        private String goods_commend;
        private String goods_stcids;
        private String evaluation_good_star;
        private String evaluation_count;
        private String is_virtual;
        private String virtual_indate;
        private String virtual_limit;
        private String virtual_invalid_refund;
        private String is_fcode;
        private String is_appoint;
        private String is_presell;
        private String have_gift;
        private String is_own_shop;
        private String level_0_price;
        private String level_1_price;
        private String level_2_price;
        private String level_3_price;
        private String level_0_auth_price;
        private String level_1_auth_price;
        private String level_2_auth_price;
        private String level_3_auth_price;
        private String is_more_discount;
        private String goods_type;
        private String parent_commonid;
        private String parent_goodsid;
        private String wholesale_price;
        private String is_service;
        private String is_installment;
        private String installment_money;
        private String is_vat;
        private String goods_salenum_vr;
        private String goods_start_vr;
        private String is_index_select;
        private String goods_param;
        private String is_kuajing;
        private Object origin;
        private Object kj_service;
        @SerializedName("native")
        private Object nativeX;
        private Object is_native;
        private Object seaport;
        private Object clearance;
        private Object clearance_sn;
        private String goods_points_offset;
        private String is_visit_comm;
        private String is_Independent_comm;
        private Object comm_rule;
        private String is_buy_apply;
        private String service_stime;
        private String service_etime;
        private String service_hours;
        private String goods_shipping_fee;
        private int quantity;
        private String goods_total;
        private String goods_image_url;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_commonid() {
            return goods_commonid;
        }

        public void setGoods_commonid(String goods_commonid) {
            this.goods_commonid = goods_commonid;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_short_title() {
            return goods_short_title;
        }

        public void setGoods_short_title(String goods_short_title) {
            this.goods_short_title = goods_short_title;
        }

        public String getGoods_jingle() {
            return goods_jingle;
        }

        public void setGoods_jingle(String goods_jingle) {
            this.goods_jingle = goods_jingle;
        }

        public String getGoods_unit() {
            return goods_unit;
        }

        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

        public String getGc_id_1() {
            return gc_id_1;
        }

        public void setGc_id_1(String gc_id_1) {
            this.gc_id_1 = gc_id_1;
        }

        public String getGc_id_2() {
            return gc_id_2;
        }

        public void setGc_id_2(String gc_id_2) {
            this.gc_id_2 = gc_id_2;
        }

        public String getGc_id_3() {
            return gc_id_3;
        }

        public void setGc_id_3(String gc_id_3) {
            this.gc_id_3 = gc_id_3;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_promotion_price() {
            return goods_promotion_price;
        }

        public void setGoods_promotion_price(String goods_promotion_price) {
            this.goods_promotion_price = goods_promotion_price;
        }

        public String getGoods_promotion_type() {
            return goods_promotion_type;
        }

        public void setGoods_promotion_type(String goods_promotion_type) {
            this.goods_promotion_type = goods_promotion_type;
        }

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }

        public String getGoods_costprice() {
            return goods_costprice;
        }

        public void setGoods_costprice(String goods_costprice) {
            this.goods_costprice = goods_costprice;
        }

        public String getGoods_serial() {
            return goods_serial;
        }

        public void setGoods_serial(String goods_serial) {
            this.goods_serial = goods_serial;
        }

        public String getGoods_storage_alarm() {
            return goods_storage_alarm;
        }

        public void setGoods_storage_alarm(String goods_storage_alarm) {
            this.goods_storage_alarm = goods_storage_alarm;
        }

        public String getGoods_click() {
            return goods_click;
        }

        public void setGoods_click(String goods_click) {
            this.goods_click = goods_click;
        }

        public String getGoods_salenum() {
            return goods_salenum;
        }

        public void setGoods_salenum(String goods_salenum) {
            this.goods_salenum = goods_salenum;
        }

        public String getGoods_collect() {
            return goods_collect;
        }

        public void setGoods_collect(String goods_collect) {
            this.goods_collect = goods_collect;
        }

        public String getGoods_spec() {
            return goods_spec;
        }

        public void setGoods_spec(String goods_spec) {
            this.goods_spec = goods_spec;
        }

        public String getGoods_storage() {
            return goods_storage;
        }

        public void setGoods_storage(String goods_storage) {
            this.goods_storage = goods_storage;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_state() {
            return goods_state;
        }

        public void setGoods_state(String goods_state) {
            this.goods_state = goods_state;
        }

        public String getGoods_verify() {
            return goods_verify;
        }

        public void setGoods_verify(String goods_verify) {
            this.goods_verify = goods_verify;
        }

        public String getGoods_addtime() {
            return goods_addtime;
        }

        public void setGoods_addtime(String goods_addtime) {
            this.goods_addtime = goods_addtime;
        }

        public String getGoods_edittime() {
            return goods_edittime;
        }

        public void setGoods_edittime(String goods_edittime) {
            this.goods_edittime = goods_edittime;
        }

        public String getAreaid_1() {
            return areaid_1;
        }

        public void setAreaid_1(String areaid_1) {
            this.areaid_1 = areaid_1;
        }

        public String getAreaid_2() {
            return areaid_2;
        }

        public void setAreaid_2(String areaid_2) {
            this.areaid_2 = areaid_2;
        }

        public String getColor_id() {
            return color_id;
        }

        public void setColor_id(String color_id) {
            this.color_id = color_id;
        }

        public String getTransport_id() {
            return transport_id;
        }

        public void setTransport_id(String transport_id) {
            this.transport_id = transport_id;
        }

        public String getGoods_freight() {
            return goods_freight;
        }

        public void setGoods_freight(String goods_freight) {
            this.goods_freight = goods_freight;
        }

        public String getGoods_vat() {
            return goods_vat;
        }

        public void setGoods_vat(String goods_vat) {
            this.goods_vat = goods_vat;
        }

        public String getGoods_commend() {
            return goods_commend;
        }

        public void setGoods_commend(String goods_commend) {
            this.goods_commend = goods_commend;
        }

        public String getGoods_stcids() {
            return goods_stcids;
        }

        public void setGoods_stcids(String goods_stcids) {
            this.goods_stcids = goods_stcids;
        }

        public String getEvaluation_good_star() {
            return evaluation_good_star;
        }

        public void setEvaluation_good_star(String evaluation_good_star) {
            this.evaluation_good_star = evaluation_good_star;
        }

        public String getEvaluation_count() {
            return evaluation_count;
        }

        public void setEvaluation_count(String evaluation_count) {
            this.evaluation_count = evaluation_count;
        }

        public String getIs_virtual() {
            return is_virtual;
        }

        public void setIs_virtual(String is_virtual) {
            this.is_virtual = is_virtual;
        }

        public String getVirtual_indate() {
            return virtual_indate;
        }

        public void setVirtual_indate(String virtual_indate) {
            this.virtual_indate = virtual_indate;
        }

        public String getVirtual_limit() {
            return virtual_limit;
        }

        public void setVirtual_limit(String virtual_limit) {
            this.virtual_limit = virtual_limit;
        }

        public String getVirtual_invalid_refund() {
            return virtual_invalid_refund;
        }

        public void setVirtual_invalid_refund(String virtual_invalid_refund) {
            this.virtual_invalid_refund = virtual_invalid_refund;
        }

        public String getIs_fcode() {
            return is_fcode;
        }

        public void setIs_fcode(String is_fcode) {
            this.is_fcode = is_fcode;
        }

        public String getIs_appoint() {
            return is_appoint;
        }

        public void setIs_appoint(String is_appoint) {
            this.is_appoint = is_appoint;
        }

        public String getIs_presell() {
            return is_presell;
        }

        public void setIs_presell(String is_presell) {
            this.is_presell = is_presell;
        }

        public String getHave_gift() {
            return have_gift;
        }

        public void setHave_gift(String have_gift) {
            this.have_gift = have_gift;
        }

        public String getIs_own_shop() {
            return is_own_shop;
        }

        public void setIs_own_shop(String is_own_shop) {
            this.is_own_shop = is_own_shop;
        }

        public String getLevel_0_price() {
            return level_0_price;
        }

        public void setLevel_0_price(String level_0_price) {
            this.level_0_price = level_0_price;
        }

        public String getLevel_1_price() {
            return level_1_price;
        }

        public void setLevel_1_price(String level_1_price) {
            this.level_1_price = level_1_price;
        }

        public String getLevel_2_price() {
            return level_2_price;
        }

        public void setLevel_2_price(String level_2_price) {
            this.level_2_price = level_2_price;
        }

        public String getLevel_3_price() {
            return level_3_price;
        }

        public void setLevel_3_price(String level_3_price) {
            this.level_3_price = level_3_price;
        }

        public String getLevel_0_auth_price() {
            return level_0_auth_price;
        }

        public void setLevel_0_auth_price(String level_0_auth_price) {
            this.level_0_auth_price = level_0_auth_price;
        }

        public String getLevel_1_auth_price() {
            return level_1_auth_price;
        }

        public void setLevel_1_auth_price(String level_1_auth_price) {
            this.level_1_auth_price = level_1_auth_price;
        }

        public String getLevel_2_auth_price() {
            return level_2_auth_price;
        }

        public void setLevel_2_auth_price(String level_2_auth_price) {
            this.level_2_auth_price = level_2_auth_price;
        }

        public String getLevel_3_auth_price() {
            return level_3_auth_price;
        }

        public void setLevel_3_auth_price(String level_3_auth_price) {
            this.level_3_auth_price = level_3_auth_price;
        }

        public String getIs_more_discount() {
            return is_more_discount;
        }

        public void setIs_more_discount(String is_more_discount) {
            this.is_more_discount = is_more_discount;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public String getParent_commonid() {
            return parent_commonid;
        }

        public void setParent_commonid(String parent_commonid) {
            this.parent_commonid = parent_commonid;
        }

        public String getParent_goodsid() {
            return parent_goodsid;
        }

        public void setParent_goodsid(String parent_goodsid) {
            this.parent_goodsid = parent_goodsid;
        }

        public String getWholesale_price() {
            return wholesale_price;
        }

        public void setWholesale_price(String wholesale_price) {
            this.wholesale_price = wholesale_price;
        }

        public String getIs_service() {
            return is_service;
        }

        public void setIs_service(String is_service) {
            this.is_service = is_service;
        }

        public String getIs_installment() {
            return is_installment;
        }

        public void setIs_installment(String is_installment) {
            this.is_installment = is_installment;
        }

        public String getInstallment_money() {
            return installment_money;
        }

        public void setInstallment_money(String installment_money) {
            this.installment_money = installment_money;
        }

        public String getIs_vat() {
            return is_vat;
        }

        public void setIs_vat(String is_vat) {
            this.is_vat = is_vat;
        }

        public String getGoods_salenum_vr() {
            return goods_salenum_vr;
        }

        public void setGoods_salenum_vr(String goods_salenum_vr) {
            this.goods_salenum_vr = goods_salenum_vr;
        }

        public String getGoods_start_vr() {
            return goods_start_vr;
        }

        public void setGoods_start_vr(String goods_start_vr) {
            this.goods_start_vr = goods_start_vr;
        }

        public String getIs_index_select() {
            return is_index_select;
        }

        public void setIs_index_select(String is_index_select) {
            this.is_index_select = is_index_select;
        }

        public String getGoods_param() {
            return goods_param;
        }

        public void setGoods_param(String goods_param) {
            this.goods_param = goods_param;
        }

        public String getIs_kuajing() {
            return is_kuajing;
        }

        public void setIs_kuajing(String is_kuajing) {
            this.is_kuajing = is_kuajing;
        }

        public Object getOrigin() {
            return origin;
        }

        public void setOrigin(Object origin) {
            this.origin = origin;
        }

        public Object getKj_service() {
            return kj_service;
        }

        public void setKj_service(Object kj_service) {
            this.kj_service = kj_service;
        }

        public Object getNativeX() {
            return nativeX;
        }

        public void setNativeX(Object nativeX) {
            this.nativeX = nativeX;
        }

        public Object getIs_native() {
            return is_native;
        }

        public void setIs_native(Object is_native) {
            this.is_native = is_native;
        }

        public Object getSeaport() {
            return seaport;
        }

        public void setSeaport(Object seaport) {
            this.seaport = seaport;
        }

        public Object getClearance() {
            return clearance;
        }

        public void setClearance(Object clearance) {
            this.clearance = clearance;
        }

        public Object getClearance_sn() {
            return clearance_sn;
        }

        public void setClearance_sn(Object clearance_sn) {
            this.clearance_sn = clearance_sn;
        }

        public String getGoods_points_offset() {
            return goods_points_offset;
        }

        public void setGoods_points_offset(String goods_points_offset) {
            this.goods_points_offset = goods_points_offset;
        }

        public String getIs_visit_comm() {
            return is_visit_comm;
        }

        public void setIs_visit_comm(String is_visit_comm) {
            this.is_visit_comm = is_visit_comm;
        }

        public String getIs_Independent_comm() {
            return is_Independent_comm;
        }

        public void setIs_Independent_comm(String is_Independent_comm) {
            this.is_Independent_comm = is_Independent_comm;
        }

        public Object getComm_rule() {
            return comm_rule;
        }

        public void setComm_rule(Object comm_rule) {
            this.comm_rule = comm_rule;
        }

        public String getIs_buy_apply() {
            return is_buy_apply;
        }

        public void setIs_buy_apply(String is_buy_apply) {
            this.is_buy_apply = is_buy_apply;
        }

        public String getService_stime() {
            return service_stime;
        }

        public void setService_stime(String service_stime) {
            this.service_stime = service_stime;
        }

        public String getService_etime() {
            return service_etime;
        }

        public void setService_etime(String service_etime) {
            this.service_etime = service_etime;
        }

        public String getService_hours() {
            return service_hours;
        }

        public void setService_hours(String service_hours) {
            this.service_hours = service_hours;
        }

        public String getGoods_shipping_fee() {
            return goods_shipping_fee;
        }

        public void setGoods_shipping_fee(String goods_shipping_fee) {
            this.goods_shipping_fee = goods_shipping_fee;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getGoods_total() {
            return goods_total;
        }

        public void setGoods_total(String goods_total) {
            this.goods_total = goods_total;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }
    }

    public static class StoreInfoBean {
        /**
         * store_id : 2
         * store_name : 艾特技术
         * grade_id : 1
         * member_id : 2
         * member_name : 艾特技术
         * seller_name : 艾特技术
         * sc_id : 0
         * store_company_name : null
         * province_id : 0
         * area_info :
         * store_address :
         * store_zip :
         * store_state : 1
         * store_close_info :
         * store_sort : 0
         * store_time : 1571655808
         * store_end_time :
         * store_label : null
         * store_banner : null
         * store_avatar : null
         * store_keywords :
         * store_description :
         * store_qq : null
         * store_ww : null
         * store_phone : null
         * store_zy : null
         * store_domain : null
         * store_domain_times : 0
         * store_recommend : 1
         * store_theme : default
         * store_credit : {"store_desccredit":{"text":"描述相符","credit":5},"store_servicecredit":{"text":"服务态度","credit":5},"store_deliverycredit":{"text":"发货速度","credit":5}}
         * store_desccredit : 0
         * store_servicecredit : 0
         * store_deliverycredit : 0
         * store_collect : 0
         * store_slide : null
         * store_slide_url : null
         * store_stamp : null
         * store_printdesc : null
         * store_sales : 72
         * store_presales : null
         * store_aftersales : null
         * store_workingtime : null
         * store_free_price : 0.00
         * store_decoration_switch : 0
         * store_decoration_only : 0
         * store_decoration_image_count : 0
         * live_store_name : null
         * live_store_address : null
         * live_store_tel : null
         * live_store_bus : null
         * is_own_shop : 0
         * bind_all_gc : 1
         * store_vrcode_prefix : null
         * store_baozh : 0
         * store_baozhopen : 0
         * store_baozhrmb : 零
         * store_qtian : 0
         * store_zhping : 0
         * store_erxiaoshi : 0
         * store_tuihuo : 0
         * store_shiyong : 0
         * store_shiti : 0
         * store_xiaoxie : 0
         * store_huodaofk : 0
         * store_points :
         * city_id : 0
         * area_id : 0
         * store_type : 0
         * store_agent_level : null
         * store_agent_area_id : 0
         * rm_id : 0
         * agent_comm : 0
         * agent_top : 0
         * agent_top_top : 0
         * agent_top_top_top : 0
         * addrole : 0
         * store_mb_slide : null
         * store_mb_slide_url : null
         * store_mb_ad1 : null
         * store_mb_ad2 : null
         * store_mb_ad1_url : null
         * store_mb_ad2_url : null
         * store_mb_banner : null
         * store_anli_count : 0
         * visit_number : 0
         * introduce :
         * store_is_weike : 1
         * task_price_number : 0
         * store_bank_info :
         * store_industry : 0
         * store_open_rush : 0
         * goods_page_show_nav : 1
         * store_info_remark : null
         * is_store_nursing : 1
         * goods_count : 19
         * store_credit_average : 5
         * store_credit_percent : 100
         */

        private String store_id;
        private String store_name;
        private String grade_id;
        private String member_id;
        private String member_name;
        private String seller_name;
        private String sc_id;
        private Object store_company_name;
        private String province_id;
        private String area_info;
        private String store_address;
        private String store_zip;
        private String store_state;
        private String store_close_info;
        private String store_sort;
        private String store_time;
        private String store_end_time;
        private Object store_label;
        private Object store_banner;
        private Object store_avatar;
        private String store_keywords;
        private String store_description;
        private Object store_qq;
        private Object store_ww;
        private Object store_phone;
        private Object store_zy;
        private Object store_domain;
        private String store_domain_times;
        private String store_recommend;
        private String store_theme;
        private StoreCreditBean store_credit;
        private String store_desccredit;
        private String store_servicecredit;
        private String store_deliverycredit;
        private String store_collect;
        private Object store_slide;
        private Object store_slide_url;
        private Object store_stamp;
        private Object store_printdesc;
        private String store_sales;
        private Object store_presales;
        private Object store_aftersales;
        private Object store_workingtime;
        private String store_free_price;
        private String store_decoration_switch;
        private String store_decoration_only;
        private String store_decoration_image_count;
        private Object live_store_name;
        private Object live_store_address;
        private Object live_store_tel;
        private Object live_store_bus;
        private String is_own_shop;
        private String bind_all_gc;
        private Object store_vrcode_prefix;
        private String store_baozh;
        private String store_baozhopen;
        private String store_baozhrmb;
        private String store_qtian;
        private String store_zhping;
        private String store_erxiaoshi;
        private String store_tuihuo;
        private String store_shiyong;
        private String store_shiti;
        private String store_xiaoxie;
        private String store_huodaofk;
        private String store_points;
        private String city_id;
        private String area_id;
        private String store_type;
        private Object store_agent_level;
        private String store_agent_area_id;
        private String rm_id;
        private String agent_comm;
        private String agent_top;
        private String agent_top_top;
        private String agent_top_top_top;
        private String addrole;
        private Object store_mb_slide;
        private Object store_mb_slide_url;
        private Object store_mb_ad1;
        private Object store_mb_ad2;
        private Object store_mb_ad1_url;
        private Object store_mb_ad2_url;
        private Object store_mb_banner;
        private String store_anli_count;
        private String visit_number;
        private String introduce;
        private String store_is_weike;
        private String task_price_number;
        private String store_bank_info;
        private String store_industry;
        private String store_open_rush;
        private String goods_page_show_nav;
        private Object store_info_remark;
        private String is_store_nursing;
        private String goods_count;
        private int store_credit_average;
        private int store_credit_percent;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getSeller_name() {
            return seller_name;
        }

        public void setSeller_name(String seller_name) {
            this.seller_name = seller_name;
        }

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        public Object getStore_company_name() {
            return store_company_name;
        }

        public void setStore_company_name(Object store_company_name) {
            this.store_company_name = store_company_name;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getArea_info() {
            return area_info;
        }

        public void setArea_info(String area_info) {
            this.area_info = area_info;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getStore_zip() {
            return store_zip;
        }

        public void setStore_zip(String store_zip) {
            this.store_zip = store_zip;
        }

        public String getStore_state() {
            return store_state;
        }

        public void setStore_state(String store_state) {
            this.store_state = store_state;
        }

        public String getStore_close_info() {
            return store_close_info;
        }

        public void setStore_close_info(String store_close_info) {
            this.store_close_info = store_close_info;
        }

        public String getStore_sort() {
            return store_sort;
        }

        public void setStore_sort(String store_sort) {
            this.store_sort = store_sort;
        }

        public String getStore_time() {
            return store_time;
        }

        public void setStore_time(String store_time) {
            this.store_time = store_time;
        }

        public String getStore_end_time() {
            return store_end_time;
        }

        public void setStore_end_time(String store_end_time) {
            this.store_end_time = store_end_time;
        }

        public Object getStore_label() {
            return store_label;
        }

        public void setStore_label(Object store_label) {
            this.store_label = store_label;
        }

        public Object getStore_banner() {
            return store_banner;
        }

        public void setStore_banner(Object store_banner) {
            this.store_banner = store_banner;
        }

        public Object getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(Object store_avatar) {
            this.store_avatar = store_avatar;
        }

        public String getStore_keywords() {
            return store_keywords;
        }

        public void setStore_keywords(String store_keywords) {
            this.store_keywords = store_keywords;
        }

        public String getStore_description() {
            return store_description;
        }

        public void setStore_description(String store_description) {
            this.store_description = store_description;
        }

        public Object getStore_qq() {
            return store_qq;
        }

        public void setStore_qq(Object store_qq) {
            this.store_qq = store_qq;
        }

        public Object getStore_ww() {
            return store_ww;
        }

        public void setStore_ww(Object store_ww) {
            this.store_ww = store_ww;
        }

        public Object getStore_phone() {
            return store_phone;
        }

        public void setStore_phone(Object store_phone) {
            this.store_phone = store_phone;
        }

        public Object getStore_zy() {
            return store_zy;
        }

        public void setStore_zy(Object store_zy) {
            this.store_zy = store_zy;
        }

        public Object getStore_domain() {
            return store_domain;
        }

        public void setStore_domain(Object store_domain) {
            this.store_domain = store_domain;
        }

        public String getStore_domain_times() {
            return store_domain_times;
        }

        public void setStore_domain_times(String store_domain_times) {
            this.store_domain_times = store_domain_times;
        }

        public String getStore_recommend() {
            return store_recommend;
        }

        public void setStore_recommend(String store_recommend) {
            this.store_recommend = store_recommend;
        }

        public String getStore_theme() {
            return store_theme;
        }

        public void setStore_theme(String store_theme) {
            this.store_theme = store_theme;
        }

        public StoreCreditBean getStore_credit() {
            return store_credit;
        }

        public void setStore_credit(StoreCreditBean store_credit) {
            this.store_credit = store_credit;
        }

        public String getStore_desccredit() {
            return store_desccredit;
        }

        public void setStore_desccredit(String store_desccredit) {
            this.store_desccredit = store_desccredit;
        }

        public String getStore_servicecredit() {
            return store_servicecredit;
        }

        public void setStore_servicecredit(String store_servicecredit) {
            this.store_servicecredit = store_servicecredit;
        }

        public String getStore_deliverycredit() {
            return store_deliverycredit;
        }

        public void setStore_deliverycredit(String store_deliverycredit) {
            this.store_deliverycredit = store_deliverycredit;
        }

        public String getStore_collect() {
            return store_collect;
        }

        public void setStore_collect(String store_collect) {
            this.store_collect = store_collect;
        }

        public Object getStore_slide() {
            return store_slide;
        }

        public void setStore_slide(Object store_slide) {
            this.store_slide = store_slide;
        }

        public Object getStore_slide_url() {
            return store_slide_url;
        }

        public void setStore_slide_url(Object store_slide_url) {
            this.store_slide_url = store_slide_url;
        }

        public Object getStore_stamp() {
            return store_stamp;
        }

        public void setStore_stamp(Object store_stamp) {
            this.store_stamp = store_stamp;
        }

        public Object getStore_printdesc() {
            return store_printdesc;
        }

        public void setStore_printdesc(Object store_printdesc) {
            this.store_printdesc = store_printdesc;
        }

        public String getStore_sales() {
            return store_sales;
        }

        public void setStore_sales(String store_sales) {
            this.store_sales = store_sales;
        }

        public Object getStore_presales() {
            return store_presales;
        }

        public void setStore_presales(Object store_presales) {
            this.store_presales = store_presales;
        }

        public Object getStore_aftersales() {
            return store_aftersales;
        }

        public void setStore_aftersales(Object store_aftersales) {
            this.store_aftersales = store_aftersales;
        }

        public Object getStore_workingtime() {
            return store_workingtime;
        }

        public void setStore_workingtime(Object store_workingtime) {
            this.store_workingtime = store_workingtime;
        }

        public String getStore_free_price() {
            return store_free_price;
        }

        public void setStore_free_price(String store_free_price) {
            this.store_free_price = store_free_price;
        }

        public String getStore_decoration_switch() {
            return store_decoration_switch;
        }

        public void setStore_decoration_switch(String store_decoration_switch) {
            this.store_decoration_switch = store_decoration_switch;
        }

        public String getStore_decoration_only() {
            return store_decoration_only;
        }

        public void setStore_decoration_only(String store_decoration_only) {
            this.store_decoration_only = store_decoration_only;
        }

        public String getStore_decoration_image_count() {
            return store_decoration_image_count;
        }

        public void setStore_decoration_image_count(String store_decoration_image_count) {
            this.store_decoration_image_count = store_decoration_image_count;
        }

        public Object getLive_store_name() {
            return live_store_name;
        }

        public void setLive_store_name(Object live_store_name) {
            this.live_store_name = live_store_name;
        }

        public Object getLive_store_address() {
            return live_store_address;
        }

        public void setLive_store_address(Object live_store_address) {
            this.live_store_address = live_store_address;
        }

        public Object getLive_store_tel() {
            return live_store_tel;
        }

        public void setLive_store_tel(Object live_store_tel) {
            this.live_store_tel = live_store_tel;
        }

        public Object getLive_store_bus() {
            return live_store_bus;
        }

        public void setLive_store_bus(Object live_store_bus) {
            this.live_store_bus = live_store_bus;
        }

        public String getIs_own_shop() {
            return is_own_shop;
        }

        public void setIs_own_shop(String is_own_shop) {
            this.is_own_shop = is_own_shop;
        }

        public String getBind_all_gc() {
            return bind_all_gc;
        }

        public void setBind_all_gc(String bind_all_gc) {
            this.bind_all_gc = bind_all_gc;
        }

        public Object getStore_vrcode_prefix() {
            return store_vrcode_prefix;
        }

        public void setStore_vrcode_prefix(Object store_vrcode_prefix) {
            this.store_vrcode_prefix = store_vrcode_prefix;
        }

        public String getStore_baozh() {
            return store_baozh;
        }

        public void setStore_baozh(String store_baozh) {
            this.store_baozh = store_baozh;
        }

        public String getStore_baozhopen() {
            return store_baozhopen;
        }

        public void setStore_baozhopen(String store_baozhopen) {
            this.store_baozhopen = store_baozhopen;
        }

        public String getStore_baozhrmb() {
            return store_baozhrmb;
        }

        public void setStore_baozhrmb(String store_baozhrmb) {
            this.store_baozhrmb = store_baozhrmb;
        }

        public String getStore_qtian() {
            return store_qtian;
        }

        public void setStore_qtian(String store_qtian) {
            this.store_qtian = store_qtian;
        }

        public String getStore_zhping() {
            return store_zhping;
        }

        public void setStore_zhping(String store_zhping) {
            this.store_zhping = store_zhping;
        }

        public String getStore_erxiaoshi() {
            return store_erxiaoshi;
        }

        public void setStore_erxiaoshi(String store_erxiaoshi) {
            this.store_erxiaoshi = store_erxiaoshi;
        }

        public String getStore_tuihuo() {
            return store_tuihuo;
        }

        public void setStore_tuihuo(String store_tuihuo) {
            this.store_tuihuo = store_tuihuo;
        }

        public String getStore_shiyong() {
            return store_shiyong;
        }

        public void setStore_shiyong(String store_shiyong) {
            this.store_shiyong = store_shiyong;
        }

        public String getStore_shiti() {
            return store_shiti;
        }

        public void setStore_shiti(String store_shiti) {
            this.store_shiti = store_shiti;
        }

        public String getStore_xiaoxie() {
            return store_xiaoxie;
        }

        public void setStore_xiaoxie(String store_xiaoxie) {
            this.store_xiaoxie = store_xiaoxie;
        }

        public String getStore_huodaofk() {
            return store_huodaofk;
        }

        public void setStore_huodaofk(String store_huodaofk) {
            this.store_huodaofk = store_huodaofk;
        }

        public String getStore_points() {
            return store_points;
        }

        public void setStore_points(String store_points) {
            this.store_points = store_points;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getStore_type() {
            return store_type;
        }

        public void setStore_type(String store_type) {
            this.store_type = store_type;
        }

        public Object getStore_agent_level() {
            return store_agent_level;
        }

        public void setStore_agent_level(Object store_agent_level) {
            this.store_agent_level = store_agent_level;
        }

        public String getStore_agent_area_id() {
            return store_agent_area_id;
        }

        public void setStore_agent_area_id(String store_agent_area_id) {
            this.store_agent_area_id = store_agent_area_id;
        }

        public String getRm_id() {
            return rm_id;
        }

        public void setRm_id(String rm_id) {
            this.rm_id = rm_id;
        }

        public String getAgent_comm() {
            return agent_comm;
        }

        public void setAgent_comm(String agent_comm) {
            this.agent_comm = agent_comm;
        }

        public String getAgent_top() {
            return agent_top;
        }

        public void setAgent_top(String agent_top) {
            this.agent_top = agent_top;
        }

        public String getAgent_top_top() {
            return agent_top_top;
        }

        public void setAgent_top_top(String agent_top_top) {
            this.agent_top_top = agent_top_top;
        }

        public String getAgent_top_top_top() {
            return agent_top_top_top;
        }

        public void setAgent_top_top_top(String agent_top_top_top) {
            this.agent_top_top_top = agent_top_top_top;
        }

        public String getAddrole() {
            return addrole;
        }

        public void setAddrole(String addrole) {
            this.addrole = addrole;
        }

        public Object getStore_mb_slide() {
            return store_mb_slide;
        }

        public void setStore_mb_slide(Object store_mb_slide) {
            this.store_mb_slide = store_mb_slide;
        }

        public Object getStore_mb_slide_url() {
            return store_mb_slide_url;
        }

        public void setStore_mb_slide_url(Object store_mb_slide_url) {
            this.store_mb_slide_url = store_mb_slide_url;
        }

        public Object getStore_mb_ad1() {
            return store_mb_ad1;
        }

        public void setStore_mb_ad1(Object store_mb_ad1) {
            this.store_mb_ad1 = store_mb_ad1;
        }

        public Object getStore_mb_ad2() {
            return store_mb_ad2;
        }

        public void setStore_mb_ad2(Object store_mb_ad2) {
            this.store_mb_ad2 = store_mb_ad2;
        }

        public Object getStore_mb_ad1_url() {
            return store_mb_ad1_url;
        }

        public void setStore_mb_ad1_url(Object store_mb_ad1_url) {
            this.store_mb_ad1_url = store_mb_ad1_url;
        }

        public Object getStore_mb_ad2_url() {
            return store_mb_ad2_url;
        }

        public void setStore_mb_ad2_url(Object store_mb_ad2_url) {
            this.store_mb_ad2_url = store_mb_ad2_url;
        }

        public Object getStore_mb_banner() {
            return store_mb_banner;
        }

        public void setStore_mb_banner(Object store_mb_banner) {
            this.store_mb_banner = store_mb_banner;
        }

        public String getStore_anli_count() {
            return store_anli_count;
        }

        public void setStore_anli_count(String store_anli_count) {
            this.store_anli_count = store_anli_count;
        }

        public String getVisit_number() {
            return visit_number;
        }

        public void setVisit_number(String visit_number) {
            this.visit_number = visit_number;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getStore_is_weike() {
            return store_is_weike;
        }

        public void setStore_is_weike(String store_is_weike) {
            this.store_is_weike = store_is_weike;
        }

        public String getTask_price_number() {
            return task_price_number;
        }

        public void setTask_price_number(String task_price_number) {
            this.task_price_number = task_price_number;
        }

        public String getStore_bank_info() {
            return store_bank_info;
        }

        public void setStore_bank_info(String store_bank_info) {
            this.store_bank_info = store_bank_info;
        }

        public String getStore_industry() {
            return store_industry;
        }

        public void setStore_industry(String store_industry) {
            this.store_industry = store_industry;
        }

        public String getStore_open_rush() {
            return store_open_rush;
        }

        public void setStore_open_rush(String store_open_rush) {
            this.store_open_rush = store_open_rush;
        }

        public String getGoods_page_show_nav() {
            return goods_page_show_nav;
        }

        public void setGoods_page_show_nav(String goods_page_show_nav) {
            this.goods_page_show_nav = goods_page_show_nav;
        }

        public Object getStore_info_remark() {
            return store_info_remark;
        }

        public void setStore_info_remark(Object store_info_remark) {
            this.store_info_remark = store_info_remark;
        }

        public String getIs_store_nursing() {
            return is_store_nursing;
        }

        public void setIs_store_nursing(String is_store_nursing) {
            this.is_store_nursing = is_store_nursing;
        }

        public String getGoods_count() {
            return goods_count;
        }

        public void setGoods_count(String goods_count) {
            this.goods_count = goods_count;
        }

        public int getStore_credit_average() {
            return store_credit_average;
        }

        public void setStore_credit_average(int store_credit_average) {
            this.store_credit_average = store_credit_average;
        }

        public int getStore_credit_percent() {
            return store_credit_percent;
        }

        public void setStore_credit_percent(int store_credit_percent) {
            this.store_credit_percent = store_credit_percent;
        }

        public static class StoreCreditBean {
            /**
             * store_desccredit : {"text":"描述相符","credit":5}
             * store_servicecredit : {"text":"服务态度","credit":5}
             * store_deliverycredit : {"text":"发货速度","credit":5}
             */

            private StoreDesccreditBean store_desccredit;
            private StoreServicecreditBean store_servicecredit;
            private StoreDeliverycreditBean store_deliverycredit;

            public StoreDesccreditBean getStore_desccredit() {
                return store_desccredit;
            }

            public void setStore_desccredit(StoreDesccreditBean store_desccredit) {
                this.store_desccredit = store_desccredit;
            }

            public StoreServicecreditBean getStore_servicecredit() {
                return store_servicecredit;
            }

            public void setStore_servicecredit(StoreServicecreditBean store_servicecredit) {
                this.store_servicecredit = store_servicecredit;
            }

            public StoreDeliverycreditBean getStore_deliverycredit() {
                return store_deliverycredit;
            }

            public void setStore_deliverycredit(StoreDeliverycreditBean store_deliverycredit) {
                this.store_deliverycredit = store_deliverycredit;
            }

            public static class StoreDesccreditBean {
                /**
                 * text : 描述相符
                 * credit : 5
                 */

                private String text;
                private int credit;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getCredit() {
                    return credit;
                }

                public void setCredit(int credit) {
                    this.credit = credit;
                }
            }

            public static class StoreServicecreditBean {
                /**
                 * text : 服务态度
                 * credit : 5
                 */

                private String text;
                private int credit;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getCredit() {
                    return credit;
                }

                public void setCredit(int credit) {
                    this.credit = credit;
                }
            }

            public static class StoreDeliverycreditBean {
                /**
                 * text : 发货速度
                 * credit : 5
                 */

                private String text;
                private int credit;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getCredit() {
                    return credit;
                }

                public void setCredit(int credit) {
                    this.credit = credit;
                }
            }
        }
    }

    public static class MemberInfoBean {
        /**
         * member_mobile : 18614079738
         * available_predeposit : 0.00
         * available_rc_balance : 0.00
         */

        private String member_mobile;
        private String available_predeposit;
        private String available_rc_balance;

        public String getMember_mobile() {
            return member_mobile;
        }

        public void setMember_mobile(String member_mobile) {
            this.member_mobile = member_mobile;
        }

        public String getAvailable_predeposit() {
            return available_predeposit;
        }

        public void setAvailable_predeposit(String available_predeposit) {
            this.available_predeposit = available_predeposit;
        }

        public String getAvailable_rc_balance() {
            return available_rc_balance;
        }

        public void setAvailable_rc_balance(String available_rc_balance) {
            this.available_rc_balance = available_rc_balance;
        }
    }
}
