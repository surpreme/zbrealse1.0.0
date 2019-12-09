package com.aite.a.activity.li.bean;

import java.io.Serializable;
import java.util.List;

public class NearChoiceTypeBean implements Serializable {

    /**
     * error_code : 0
     * message : 获取成功
     * datas : [{"sc_id":"11","sc_name":"正品商家","sc_bail":"50000","sc_sort":"1"},{"sc_id":"12","sc_name":"234324","sc_bail":"0","sc_sort":"24"},{"sc_id":"2","sc_name":"品牌店","sc_bail":"9800","sc_sort":"255"},{"sc_id":"4","sc_name":"个人店铺","sc_bail":"30000","sc_sort":"255"},{"sc_id":"5","sc_name":"品牌直营店","sc_bail":"88888","sc_sort":"255"},{"sc_id":"6","sc_name":"专卖店","sc_bail":"10000","sc_sort":"255"},{"sc_id":"10","sc_name":"实体店","sc_bail":"1500","sc_sort":"255"},{"sc_id":"13","sc_name":"瓷砖胶","sc_bail":"0","sc_sort":"255"}]
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
         * sc_id : 11
         * sc_name : 正品商家
         * sc_bail : 50000
         * sc_sort : 1
         */

        private String sc_id;
        private String sc_name;
        private String sc_bail;
        private String sc_sort;

        public String getSc_id() {
            return sc_id;
        }

        public void setSc_id(String sc_id) {
            this.sc_id = sc_id;
        }

        public String getSc_name() {
            return sc_name;
        }

        public void setSc_name(String sc_name) {
            this.sc_name = sc_name;
        }

        public String getSc_bail() {
            return sc_bail;
        }

        public void setSc_bail(String sc_bail) {
            this.sc_bail = sc_bail;
        }

        public String getSc_sort() {
            return sc_sort;
        }

        public void setSc_sort(String sc_sort) {
            this.sc_sort = sc_sort;
        }
    }
}
