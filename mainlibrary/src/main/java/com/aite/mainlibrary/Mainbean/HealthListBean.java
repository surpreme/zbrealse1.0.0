package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class HealthListBean extends ErrorBean implements Serializable {


    /**
     * code : 200
     * hasmore : false
     * page_total : 1
     * datas : [{"id":"13","name":"","time":"1970-01-01 08:00:00","description":"","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"12","name":"","time":"1970-01-01 08:00:00","description":"","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"11","name":"","time":"1970-01-01 08:00:00","description":"","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"10","name":"心脏病","time":"1970-07-09 00:00:00","description":"救心丸","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"9","name":"感冒","time":"2019-12-06 00:00:00","description":"流鼻涕","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"8","name":"感冒","time":"2019-12-06 00:00:00","description":"流鼻涕","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"7","name":"感冒","time":"2019-12-06 00:00:00","description":"流鼻涕","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"6","name":"感冒","time":"2019-12-06 00:00:00","description":"流鼻涕","diseases_imgs":[],"member_id":"7","type":"1"},{"id":"5","name":"感冒","time":"2019-12-06 00:00:00","description":"流鼻涕","diseases_imgs":[],"member_id":"7","type":"1"}]
     */

    private int code;
    private boolean hasmore;
    private int page_total;
    private List<DatasBean> datas;

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

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * id : 13
         * name :
         * time : 1970-01-01 08:00:00
         * description :
         * diseases_imgs : []
         * member_id : 7
         * type : 1
         */

        private String id;
        private String name;
        private String time;
        private String description;
        private String member_id;
        private String type;
        private List<?> diseases_imgs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<?> getDiseases_imgs() {
            return diseases_imgs;
        }

        public void setDiseases_imgs(List<?> diseases_imgs) {
            this.diseases_imgs = diseases_imgs;
        }
    }
}
