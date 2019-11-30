package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class AirMainListBean extends ErrorBean implements Serializable {

    /**
     * list_total : 2
     * is_nextpage : 0
     * list : [{"id":"2","title":"呼吸","member_id":"7","class_id":"1","address":"南山","start_time":"2019-11-24","end_time":"01-01","credit":"0.00","is_order":1,"class_name":"测试喘息","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/common/default_user_portrait.gif"},{"id":"3","title":"呼吸道","member_id":"7","class_id":"1","address":"南山","start_time":"2019-11-24","end_time":"01-01","credit":"0.00","is_order":1,"class_name":"测试喘息","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/common/default_user_portrait.gif"}]
     */

    private String list_total;
    private int is_nextpage;
    private List<ListBean> list;

    public String getList_total() {
        return list_total;
    }

    public void setList_total(String list_total) {
        this.list_total = list_total;
    }

    public int getIs_nextpage() {
        return is_nextpage;
    }

    public void setIs_nextpage(int is_nextpage) {
        this.is_nextpage = is_nextpage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 2
         * title : 呼吸
         * member_id : 7
         * class_id : 1
         * address : 南山
         * start_time : 2019-11-24
         * end_time : 01-01
         * credit : 0.00
         * is_order : 1
         * class_name : 测试喘息
         * memebr_avatar : http://zhongbyi.aitecc.com/data/upload/shop/common/default_user_portrait.gif
         */

        private String id;
        private String title;
        private String member_id;
        private String class_id;
        private String address;
        private String start_time;
        private String end_time;
        private String credit;
        private int is_order;
        private String class_name;
        private String memebr_avatar;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public int getIs_order() {
            return is_order;
        }

        public void setIs_order(int is_order) {
            this.is_order = is_order;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getMemebr_avatar() {
            return memebr_avatar;
        }

        public void setMemebr_avatar(String memebr_avatar) {
            this.memebr_avatar = memebr_avatar;
        }
    }
}
