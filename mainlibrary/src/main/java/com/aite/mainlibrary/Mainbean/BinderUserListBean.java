package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class BinderUserListBean extends ErrorBean implements Serializable {

    /**
     * list_total : 4
     * is_nextpage : 0
     * list : [{"id":"1","member_id":"7","to_member_id":"21","realname":"xiao","mobile":"13208983000","relation":"5"},{"id":"2","member_id":"7","to_member_id":"16","realname":"xiao1","mobile":"19965412404","relation":"5"},{"id":"3","member_id":"7","to_member_id":"14","realname":"布雫雫","mobile":"13608696703","relation":"1"},{"id":"4","member_id":"7","to_member_id":"15","realname":"布雫雫","mobile":"18565836912","relation":"1"}]
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
         * id : 1
         * member_id : 7
         * to_member_id : 21
         * realname : xiao
         * mobile : 13208983000
         * relation : 5
         */

        private String id;
        private String member_id;
        private String to_member_id;
        private String realname;
        private String mobile;
        private String relation;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getTo_member_id() {
            return to_member_id;
        }

        public void setTo_member_id(String to_member_id) {
            this.to_member_id = to_member_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }
}
