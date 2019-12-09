package com.aite.a.activity.li.bean;

import java.io.Serializable;
import java.util.List;

public class GuessLikeBean implements Serializable {

    /**
     * error_code : 0
     * message : 获取成功
     * datas : [{"goods_id":"2429","goods_name":"柯基幼犬粮成犬狗粮 柯基犬专用狗粮5KG小型犬狗粮天然粮10斤装","goods_jingle":"","goods_image":"https://aitecc.com/data/upload/shop/store/goods/8/2019/08/22/8_06198066382734808.jpg","store_id":"8","goods_price":"103.00","goods_promotion_price":"103.00","goods_marketprice":"119.00","goods_salenum":"0","level_0_price":"103.00","level_1_price":"100.00","level_2_price":"100.00","level_3_price":"100.00","level_0_auth_price":"103.00","level_1_auth_price":"100.00","level_2_auth_price":"100.00","level_3_auth_price":"99.00","groupbuy_info":null,"xianshi_info":null,"miaosha_info":[],"spellgroup_info":[],"bargain_info":[],"goods_url":"https://aitecc.com/store/index.php?act=goods&op=index&goods_id=2429"},{"goods_id":"1576","goods_name":"简约全棉刺绣水洗棉四件套流苏花边纯色美式公主风纯棉床上用品","goods_jingle":"","goods_image":"https://aitecc.com/data/upload/shop/store/goods/32/2018/05/14/32_05796550085830549.jpg","store_id":"32","goods_price":"227.00","goods_promotion_price":"227.00","goods_marketprice":"300.00","goods_salenum":"8","level_0_price":"227.00","level_1_price":"227.00","level_2_price":"227.00","level_3_price":"227.00","level_0_auth_price":"227.00","level_1_auth_price":"227.00","level_2_auth_price":"227.00","level_3_auth_price":"227.00","groupbuy_info":null,"xianshi_info":null,"miaosha_info":[],"spellgroup_info":[],"bargain_info":[],"goods_url":"https://aitecc.com/store/index.php?act=goods&op=index&goods_id=1576"},{"goods_id":"1536","goods_name":"Sony/索尼 Xperia XZ Premium 索尼XZP 镜面手机 g8142 港版4K屏","goods_jingle":"后置摄像头: 1900万\r\n摄像头类型: 双摄像头（前后）","goods_image":"https://aitecc.com/data/upload/shop/store/goods/2/2018/05/10/2_05792825967293927.png","store_id":"2","goods_price":"4099.00","goods_promotion_price":"4099.00","goods_marketprice":"4999.00","goods_salenum":"61","level_0_price":"4099.00","level_1_price":"4099.00","level_2_price":"4099.00","level_3_price":"4099.00","level_0_auth_price":"4099.00","level_1_auth_price":"4099.00","level_2_auth_price":"4099.00","level_3_auth_price":"4099.00","groupbuy_info":null,"xianshi_info":null,"miaosha_info":[],"spellgroup_info":[],"bargain_info":[],"goods_url":"https://aitecc.com/store/index.php?act=goods&op=index&goods_id=1536"},{"goods_id":"1252","goods_name":"搬家服务","goods_jingle":"","goods_image":"https://aitecc.com/data/upload/shop/store/goods/1/2016/10/28/1_05309862093965960.jpg","store_id":"1","goods_price":"600.00","goods_promotion_price":"600.00","goods_marketprice":"800.00","goods_salenum":"12","level_0_price":"600.00","level_1_price":"600.00","level_2_price":"600.00","level_3_price":"600.00","level_0_auth_price":"600.00","level_1_auth_price":"600.00","level_2_auth_price":"600.00","level_3_auth_price":"600.00","groupbuy_info":null,"xianshi_info":null,"miaosha_info":[],"spellgroup_info":[],"bargain_info":[],"goods_url":"https://aitecc.com/store/index.php?act=goods&op=index&goods_id=1252"}]
     */

    private int error_code;
    private String message;
    private List<DatasBean> datas;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * goods_id : 2429
         * goods_name : 柯基幼犬粮成犬狗粮 柯基犬专用狗粮5KG小型犬狗粮天然粮10斤装
         * goods_jingle :
         * goods_image : https://aitecc.com/data/upload/shop/store/goods/8/2019/08/22/8_06198066382734808.jpg
         * store_id : 8
         * goods_price : 103.00
         * goods_promotion_price : 103.00
         * goods_marketprice : 119.00
         * goods_salenum : 0
         * level_0_price : 103.00
         * level_1_price : 100.00
         * level_2_price : 100.00
         * level_3_price : 100.00
         * level_0_auth_price : 103.00
         * level_1_auth_price : 100.00
         * level_2_auth_price : 100.00
         * level_3_auth_price : 99.00
         * groupbuy_info : null
         * xianshi_info : null
         * miaosha_info : []
         * spellgroup_info : []
         * bargain_info : []
         * goods_url : https://aitecc.com/store/index.php?act=goods&op=index&goods_id=2429
         */

        private String goods_id;
        private String goods_name;
        private String goods_jingle;
        private String goods_image;
        private String store_id;
        private String goods_price;
        private String goods_promotion_price;
        private String goods_marketprice;
        private String goods_salenum;
        private String level_0_price;
        private String level_1_price;
        private String level_2_price;
        private String level_3_price;
        private String level_0_auth_price;
        private String level_1_auth_price;
        private String level_2_auth_price;
        private String level_3_auth_price;
        private Object groupbuy_info;
        private Object xianshi_info;
        private String goods_url;
        private List<?> miaosha_info;
        private List<?> spellgroup_info;
        private List<?> bargain_info;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_jingle() {
            return goods_jingle;
        }

        public void setGoods_jingle(String goods_jingle) {
            this.goods_jingle = goods_jingle;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
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

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }

        public String getGoods_salenum() {
            return goods_salenum;
        }

        public void setGoods_salenum(String goods_salenum) {
            this.goods_salenum = goods_salenum;
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

        public Object getGroupbuy_info() {
            return groupbuy_info;
        }

        public void setGroupbuy_info(Object groupbuy_info) {
            this.groupbuy_info = groupbuy_info;
        }

        public Object getXianshi_info() {
            return xianshi_info;
        }

        public void setXianshi_info(Object xianshi_info) {
            this.xianshi_info = xianshi_info;
        }

        public String getGoods_url() {
            return goods_url;
        }

        public void setGoods_url(String goods_url) {
            this.goods_url = goods_url;
        }

        public List<?> getMiaosha_info() {
            return miaosha_info;
        }

        public void setMiaosha_info(List<?> miaosha_info) {
            this.miaosha_info = miaosha_info;
        }

        public List<?> getSpellgroup_info() {
            return spellgroup_info;
        }

        public void setSpellgroup_info(List<?> spellgroup_info) {
            this.spellgroup_info = spellgroup_info;
        }

        public List<?> getBargain_info() {
            return bargain_info;
        }

        public void setBargain_info(List<?> bargain_info) {
            this.bargain_info = bargain_info;
        }
    }
}
