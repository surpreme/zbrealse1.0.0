package com.aite.a.activity.li.bean;

import java.io.Serializable;
import java.util.List;

public class AroundareaBean implements Serializable {

    /**
     * error_code : 0
     * message : 拉取成功
     * datas : {"areaList":[{"area_id":"3059","area_name":"南山区"},{"area_id":"3060","area_name":"宝安区"},{"area_id":"3061","area_name":"盐田区"},{"area_id":"3062","area_name":"福田区"},{"area_id":"3063","area_name":"罗湖区"},{"area_id":"3064","area_name":"龙岗区"},{"area_id":"45057","area_name":"光明新区"},{"area_id":"45058","area_name":"龙华新区"},{"area_id":"45059","area_name":"坪山新区"},{"area_id":"45060","area_name":"大鹏新区"}]}
     */

    private int error_code;
    private String message;
    private DatasBean datas;



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

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private String error;
        private List<AreaListBean> areaList;

        public List<AreaListBean> getAreaList() {
            return areaList;
        }
        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public void setAreaList(List<AreaListBean> areaList) {
            this.areaList = areaList;
        }

        public static class AreaListBean {
            /**
             * area_id : 3059
             * area_name : 南山区
             */

            private String area_id;
            private String area_name;

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }
        }
    }
}
