package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class BinderSosUserListBean extends ErrorBean implements Serializable {


    /**
     * list_total : 16
     * is_nextpage : 1
     * list : [{"id":"6","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291251303189837.jpg","realname":"法国","mobile":"18614079738","relation":"2","is_default":"0"},{"id":"7","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291253654740898.jpg","realname":"公众号","mobile":"18614079738","relation":"3","is_default":"1"},{"id":"8","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291253717408700.jpg","realname":"公众号","mobile":"18614079738","relation":"3","is_default":"1"},{"id":"9","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291257643642588.png","realname":"回来了","mobile":"18614079738","relation":"2","is_default":"1"},{"id":"10","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291257656620750.png","realname":"回来了","mobile":"18614079738","relation":"2","is_default":"1"},{"id":"11","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291257661468561.png","realname":"回来了","mobile":"18614079738","relation":"2","is_default":"1"},{"id":"12","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291257663335252.png","realname":"回来了","mobile":"18614079738","relation":"2","is_default":"1"},{"id":"13","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291257665051758.png","realname":"回来了","mobile":"18614079738","relation":"2","is_default":"1"},{"id":"14","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291257666618607.png","realname":"回来了","mobile":"18614079738","relation":"2","is_default":"1"},{"id":"15","member_id":"7","avatar":"http://zhongbyi.aitecc.com/data/upload/shop/member/06291257668176307.png","realname":"回来了","mobile":"18614079738","relation":"2","is_default":"1"}]
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
         * id : 6
         * member_id : 7
         * avatar : http://zhongbyi.aitecc.com/data/upload/shop/member/06291251303189837.jpg
         * realname : 法国
         * mobile : 18614079738
         * relation : 2
         * is_default : 0
         */

        private String id;
        private String member_id;
        private String avatar;
        private String realname;
        private String mobile;
        private String relation;
        private String is_default;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
