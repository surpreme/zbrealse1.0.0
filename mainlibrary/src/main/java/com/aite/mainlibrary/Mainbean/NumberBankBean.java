package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class NumberBankBean extends ErrorBean implements Serializable {


    /**
     * list_total : 2
     * is_nextpage : 0
     * list : [{"pl_id":"27","pl_memberid":"7","pl_membername":"18614079738","pl_adminid":null,"pl_adminname":null,"pl_points":"1920","pl_addtime":"2019.12.11 09:34","pl_desc":"义工参与时间银行服务【过后风格黄飞鸿风格】获得义工积分奖励","pl_stage":"volunteer","pl_module":"1"},{"pl_id":"28","pl_memberid":"7","pl_membername":"18614079738","pl_adminid":null,"pl_adminname":null,"pl_points":"960","pl_addtime":"2019.12.11 09:36","pl_desc":"义工参与时间银行服务【太累了】获得义工积分奖励","pl_stage":"volunteer","pl_module":"1"}]
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
         * pl_id : 27
         * pl_memberid : 7
         * pl_membername : 18614079738
         * pl_adminid : null
         * pl_adminname : null
         * pl_points : 1920
         * pl_addtime : 2019.12.11 09:34
         * pl_desc : 义工参与时间银行服务【过后风格黄飞鸿风格】获得义工积分奖励
         * pl_stage : volunteer
         * pl_module : 1
         */

        private String pl_id;
        private String pl_memberid;
        private String pl_membername;
        private Object pl_adminid;
        private Object pl_adminname;
        private String pl_points;
        private String pl_addtime;
        private String pl_desc;
        private String pl_stage;
        private String pl_module;

        public String getPl_id() {
            return pl_id;
        }

        public void setPl_id(String pl_id) {
            this.pl_id = pl_id;
        }

        public String getPl_memberid() {
            return pl_memberid;
        }

        public void setPl_memberid(String pl_memberid) {
            this.pl_memberid = pl_memberid;
        }

        public String getPl_membername() {
            return pl_membername;
        }

        public void setPl_membername(String pl_membername) {
            this.pl_membername = pl_membername;
        }

        public Object getPl_adminid() {
            return pl_adminid;
        }

        public void setPl_adminid(Object pl_adminid) {
            this.pl_adminid = pl_adminid;
        }

        public Object getPl_adminname() {
            return pl_adminname;
        }

        public void setPl_adminname(Object pl_adminname) {
            this.pl_adminname = pl_adminname;
        }

        public String getPl_points() {
            return pl_points;
        }

        public void setPl_points(String pl_points) {
            this.pl_points = pl_points;
        }

        public String getPl_addtime() {
            return pl_addtime;
        }

        public void setPl_addtime(String pl_addtime) {
            this.pl_addtime = pl_addtime;
        }

        public String getPl_desc() {
            return pl_desc;
        }

        public void setPl_desc(String pl_desc) {
            this.pl_desc = pl_desc;
        }

        public String getPl_stage() {
            return pl_stage;
        }

        public void setPl_stage(String pl_stage) {
            this.pl_stage = pl_stage;
        }

        public String getPl_module() {
            return pl_module;
        }

        public void setPl_module(String pl_module) {
            this.pl_module = pl_module;
        }
    }
}
