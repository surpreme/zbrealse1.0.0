package com.aite.aitezhongbao.bean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

public class LogInBean extends ErrorBean implements Serializable {


    /**
     * code : 200
     * datas : {"username":"18614079738","key":"a19f866421ca61c7f21f8eb73435b486","config":{"friend_valid":0,"member_id":"7"}}
     */

    private String code;
    private DatasBean datas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * username : 18614079738
         * key : a19f866421ca61c7f21f8eb73435b486
         * config : {"friend_valid":0,"member_id":"7"}
         */

        private String username;
        private String key;
        private ConfigBean config;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public ConfigBean getConfig() {
            return config;
        }

        public void setConfig(ConfigBean config) {
            this.config = config;
        }

        public static class ConfigBean {
            /**
             * friend_valid : 0
             * member_id : 7
             */

            private String friend_valid;
            private String member_id;

            public String getFriend_valid() {
                return friend_valid;
            }

            public void setFriend_valid(String friend_valid) {
                this.friend_valid = friend_valid;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }
        }
    }
}
