package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class NewsBean extends ErrorBean implements Serializable {

    /**
     * code : 200
     * hasmore : true
     * page_total : 2
     * datas : {"list":[{"circle_id":"69","circle_name":"1111231","circle_desc":"1","circle_masterid":"1986","circle_mastername":"boslm","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"1","circle_mcount":"1","circle_thcount":"0","circle_gcount":"0","circle_pursuereason":"1","circle_notice":"","circle_status":"1","circle_statusinfo":"","circle_joinaudit":"0","circle_addtime":"1573090460","circle_noticetime":null,"is_recommend":"0","is_hot":"0","circle_tag":"1","new_verifycount":"0","new_informcount":"0","mapply_open":"0","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"67","circle_name":"交友社区1","circle_desc":"1234567","circle_masterid":"1968","circle_mastername":"chen123","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"1","circle_mcount":"1","circle_thcount":"1","circle_gcount":"0","circle_pursuereason":"","circle_notice":null,"circle_status":"1","circle_statusinfo":null,"circle_joinaudit":"0","circle_addtime":"1572491070","circle_noticetime":null,"is_recommend":"0","is_hot":"0","circle_tag":"","new_verifycount":"0","new_informcount":"0","mapply_open":"0","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"66","circle_name":"交友社区","circle_desc":"最大交友社区","circle_masterid":"1968","circle_mastername":"chen123","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"3","circle_mcount":"1","circle_thcount":"0","circle_gcount":"0","circle_pursuereason":"","circle_notice":null,"circle_status":"1","circle_statusinfo":null,"circle_joinaudit":"0","circle_addtime":"1572340464","circle_noticetime":null,"is_recommend":"0","is_hot":"0","circle_tag":"","new_verifycount":"0","new_informcount":"0","mapply_open":"0","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"63","circle_name":"刘海贵-艾特技术社区","circle_desc":"测试对对对","circle_masterid":"1032","circle_mastername":"刘海贵","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"3","circle_mcount":"3","circle_thcount":"2","circle_gcount":"0","circle_pursuereason":"测试","circle_notice":null,"circle_status":"1","circle_statusinfo":null,"circle_joinaudit":"0","circle_addtime":"1571369497","circle_noticetime":null,"is_recommend":"0","is_hot":"0","circle_tag":"艾特技术,刘海贵,测试","new_verifycount":"0","new_informcount":"0","mapply_open":"0","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"62","circle_name":"时尚宝妈","circle_desc":"宝妈也可以时尚","circle_masterid":"1382","circle_mastername":"爱吃鱼的猫","circle_img":"https://aitecc.com/data/upload/circle/group/62.jpg","class_id":"1","circle_mcount":"4","circle_thcount":"0","circle_gcount":"0","circle_pursuereason":null,"circle_notice":"","circle_status":"1","circle_statusinfo":"","circle_joinaudit":"0","circle_addtime":"1542020913","circle_noticetime":null,"is_recommend":"1","is_hot":"1","circle_tag":"","new_verifycount":"0","new_informcount":"0","mapply_open":"0","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"61","circle_name":"美妆","circle_desc":"美丽是一种自信","circle_masterid":"1382","circle_mastername":"爱吃鱼的猫","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"2","circle_mcount":"1","circle_thcount":"0","circle_gcount":"0","circle_pursuereason":"","circle_notice":"","circle_status":"1","circle_statusinfo":"","circle_joinaudit":"0","circle_addtime":"1542020361","circle_noticetime":null,"is_recommend":"1","is_hot":"1","circle_tag":"美妆","new_verifycount":"0","new_informcount":"0","mapply_open":"0","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"60","circle_name":"购物狂的心声","circle_desc":"逛、爱买、爱看是女孩子逛街购物的天性,当然啦,逛街购物还有分不同级别的,最高级别就是所谓的购物狂,不巧,在下就是这样一位购物达人。","circle_masterid":"1381","circle_mastername":"z366623943","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"2","circle_mcount":"2","circle_thcount":"0","circle_gcount":"0","circle_pursuereason":"当我们将心爱的物品装进自己的口袋，当我们拎着大包小袋满载而归，当我们哼着歌拆着一个又一个包裹时，那种感觉，既满足又痛快，而被满足和痛快的对象，它大概叫做\u2014\u2014自我价值感。","circle_notice":"","circle_status":"1","circle_statusinfo":"","circle_joinaudit":"1","circle_addtime":"1542017104","circle_noticetime":null,"is_recommend":"1","is_hot":"1","circle_tag":"购物","new_verifycount":"1","new_informcount":"0","mapply_open":"1","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"59","circle_name":"女神新装","circle_desc":"我们拥有的不仅仅是美丽","circle_masterid":"1382","circle_mastername":"爱吃鱼的猫","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"2","circle_mcount":"8","circle_thcount":"4","circle_gcount":"0","circle_pursuereason":"美丽是一门学问","circle_notice":"没有丑女，只有。。。","circle_status":"1","circle_statusinfo":"","circle_joinaudit":"1","circle_addtime":"1542014366","circle_noticetime":null,"is_recommend":"1","is_hot":"1","circle_tag":"美丽","new_verifycount":"7","new_informcount":"0","mapply_open":"1","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"58","circle_name":"猪猪妈妈省钱吧","circle_desc":"花钱如流水，教你如何用最少的钱买好的东西！","circle_masterid":"1382","circle_mastername":"爱吃鱼的猫","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"2","circle_mcount":"1","circle_thcount":"0","circle_gcount":"0","circle_pursuereason":"花钱也是一门学问","circle_notice":"开启省钱模式","circle_status":"1","circle_statusinfo":null,"circle_joinaudit":"1","circle_addtime":"1542014217","circle_noticetime":null,"is_recommend":"1","is_hot":"1","circle_tag":"省钱","new_verifycount":"0","new_informcount":"0","mapply_open":"1","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":null},{"circle_id":"56","circle_name":"游戏","circle_desc":"测试群组是否可以创建","circle_masterid":"1032","circle_mastername":"刘海贵","circle_img":"https://aitecc.com/data/upload/circle/default_group_logo.gif","class_id":"0","circle_mcount":"6","circle_thcount":"1","circle_gcount":"0","circle_pursuereason":"无须条件","circle_notice":"","circle_status":"1","circle_statusinfo":"","circle_joinaudit":"0","circle_addtime":"1537871033","circle_noticetime":null,"is_recommend":"1","is_hot":"1","circle_tag":"测试","new_verifycount":"0","new_informcount":"0","mapply_open":"0","mapply_ml":"0","new_mapplycount":"0","circle_type":"web","circle_back_img":"shop/adv/05901641832703171.png"}]}
     */

    private int code;
    private boolean hasmore;
    private int page_total;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isHasmore() {
        return hasmore;
    }

    public void setHasmore(boolean hasmore) {
        this.hasmore = hasmore;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * circle_id : 69
             * circle_name : 1111231
             * circle_desc : 1
             * circle_masterid : 1986
             * circle_mastername : boslm
             * circle_img : https://aitecc.com/data/upload/circle/default_group_logo.gif
             * class_id : 1
             * circle_mcount : 1
             * circle_thcount : 0
             * circle_gcount : 0
             * circle_pursuereason : 1
             * circle_notice :
             * circle_status : 1
             * circle_statusinfo :
             * circle_joinaudit : 0
             * circle_addtime : 1573090460
             * circle_noticetime : null
             * is_recommend : 0
             * is_hot : 0
             * circle_tag : 1
             * new_verifycount : 0
             * new_informcount : 0
             * mapply_open : 0
             * mapply_ml : 0
             * new_mapplycount : 0
             * circle_type : web
             * circle_back_img : null
             */

            private String circle_id;
            private String circle_name;
            private String circle_desc;
            private String circle_masterid;
            private String circle_mastername;
            private String circle_img;
            private String class_id;
            private String circle_mcount;
            private String circle_thcount;
            private String circle_gcount;
            private String circle_pursuereason;
            private String circle_notice;
            private String circle_status;
            private String circle_statusinfo;
            private String circle_joinaudit;
            private String circle_addtime;
            private Object circle_noticetime;
            private String is_recommend;
            private String is_hot;
            private String circle_tag;
            private String new_verifycount;
            private String new_informcount;
            private String mapply_open;
            private String mapply_ml;
            private String new_mapplycount;
            private String circle_type;
            private Object circle_back_img;

            public String getCircle_id() {
                return circle_id;
            }

            public void setCircle_id(String circle_id) {
                this.circle_id = circle_id;
            }

            public String getCircle_name() {
                return circle_name;
            }

            public void setCircle_name(String circle_name) {
                this.circle_name = circle_name;
            }

            public String getCircle_desc() {
                return circle_desc;
            }

            public void setCircle_desc(String circle_desc) {
                this.circle_desc = circle_desc;
            }

            public String getCircle_masterid() {
                return circle_masterid;
            }

            public void setCircle_masterid(String circle_masterid) {
                this.circle_masterid = circle_masterid;
            }

            public String getCircle_mastername() {
                return circle_mastername;
            }

            public void setCircle_mastername(String circle_mastername) {
                this.circle_mastername = circle_mastername;
            }

            public String getCircle_img() {
                return circle_img;
            }

            public void setCircle_img(String circle_img) {
                this.circle_img = circle_img;
            }

            public String getClass_id() {
                return class_id;
            }

            public void setClass_id(String class_id) {
                this.class_id = class_id;
            }

            public String getCircle_mcount() {
                return circle_mcount;
            }

            public void setCircle_mcount(String circle_mcount) {
                this.circle_mcount = circle_mcount;
            }

            public String getCircle_thcount() {
                return circle_thcount;
            }

            public void setCircle_thcount(String circle_thcount) {
                this.circle_thcount = circle_thcount;
            }

            public String getCircle_gcount() {
                return circle_gcount;
            }

            public void setCircle_gcount(String circle_gcount) {
                this.circle_gcount = circle_gcount;
            }

            public String getCircle_pursuereason() {
                return circle_pursuereason;
            }

            public void setCircle_pursuereason(String circle_pursuereason) {
                this.circle_pursuereason = circle_pursuereason;
            }

            public String getCircle_notice() {
                return circle_notice;
            }

            public void setCircle_notice(String circle_notice) {
                this.circle_notice = circle_notice;
            }

            public String getCircle_status() {
                return circle_status;
            }

            public void setCircle_status(String circle_status) {
                this.circle_status = circle_status;
            }

            public String getCircle_statusinfo() {
                return circle_statusinfo;
            }

            public void setCircle_statusinfo(String circle_statusinfo) {
                this.circle_statusinfo = circle_statusinfo;
            }

            public String getCircle_joinaudit() {
                return circle_joinaudit;
            }

            public void setCircle_joinaudit(String circle_joinaudit) {
                this.circle_joinaudit = circle_joinaudit;
            }

            public String getCircle_addtime() {
                return circle_addtime;
            }

            public void setCircle_addtime(String circle_addtime) {
                this.circle_addtime = circle_addtime;
            }

            public Object getCircle_noticetime() {
                return circle_noticetime;
            }

            public void setCircle_noticetime(Object circle_noticetime) {
                this.circle_noticetime = circle_noticetime;
            }

            public String getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(String is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getIs_hot() {
                return is_hot;
            }

            public void setIs_hot(String is_hot) {
                this.is_hot = is_hot;
            }

            public String getCircle_tag() {
                return circle_tag;
            }

            public void setCircle_tag(String circle_tag) {
                this.circle_tag = circle_tag;
            }

            public String getNew_verifycount() {
                return new_verifycount;
            }

            public void setNew_verifycount(String new_verifycount) {
                this.new_verifycount = new_verifycount;
            }

            public String getNew_informcount() {
                return new_informcount;
            }

            public void setNew_informcount(String new_informcount) {
                this.new_informcount = new_informcount;
            }

            public String getMapply_open() {
                return mapply_open;
            }

            public void setMapply_open(String mapply_open) {
                this.mapply_open = mapply_open;
            }

            public String getMapply_ml() {
                return mapply_ml;
            }

            public void setMapply_ml(String mapply_ml) {
                this.mapply_ml = mapply_ml;
            }

            public String getNew_mapplycount() {
                return new_mapplycount;
            }

            public void setNew_mapplycount(String new_mapplycount) {
                this.new_mapplycount = new_mapplycount;
            }

            public String getCircle_type() {
                return circle_type;
            }

            public void setCircle_type(String circle_type) {
                this.circle_type = circle_type;
            }

            public Object getCircle_back_img() {
                return circle_back_img;
            }

            public void setCircle_back_img(Object circle_back_img) {
                this.circle_back_img = circle_back_img;
            }
        }
    }
}
