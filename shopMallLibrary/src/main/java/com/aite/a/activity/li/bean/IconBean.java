package com.aite.a.activity.li.bean;

import java.io.Serializable;
import java.util.List;

public class IconBean implements Serializable {

    /**
     * error_code : 0
     * message :
     * datas : [{"member_id":"1897","member_name":"wly1","member_avatar":"https://aitecc.com/data/upload/shop/avatar/avatar_1897.png"},{"member_id":"1119","member_name":"chen1","member_avatar":"https://aitecc.com/data/upload/shop/avatar/avatar_1119.jpg"},{"member_id":"135","member_name":"aiteshop","member_avatar":"https://aitecc.com/data/upload/shop/avatar/avatar_135.jpg"},{"member_id":"3","member_name":"aitesoft","member_avatar":"https://aitecc.com/data/upload/shop/avatar/avatar_3.jpg"}]
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
         * member_id : 1897
         * member_name : wly1
         * member_avatar : https://aitecc.com/data/upload/shop/avatar/avatar_1897.png
         */

        private String member_id;
        private String member_name;
        private String member_avatar;

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

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }
    }
}
