package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class DayTogetherChoiceBean extends ErrorBean implements Serializable {

    private List<ListOrderBean> list_order;
    private List<ListClassBean> list_class;
    private List<ListTimeBean> list_time;

    public List<ListOrderBean> getList_order() {
        return list_order;
    }

    public void setList_order(List<ListOrderBean> list_order) {
        this.list_order = list_order;
    }

    public List<ListClassBean> getList_class() {
        return list_class;
    }

    public void setList_class(List<ListClassBean> list_class) {
        this.list_class = list_class;
    }

    public List<ListTimeBean> getList_time() {
        return list_time;
    }

    public void setList_time(List<ListTimeBean> list_time) {
        this.list_time = list_time;
    }

    public static class ListOrderBean extends IBaseBean {
        /**
         * sort_id : 0
         * sort_name : 综合
         */

        private String sort_id;
        private String sort_name;


        @Override
        public String getId() {
            return sort_id;
        }

        @Override
        public String getNasme() {
            return sort_name;
        }

        @Override
        public boolean isIsCheck() {
            return isChecked;
        }
    }

    public static class ListClassBean extends IBaseBean {
        /**
         * gc_id : 0
         * gc_name : 全部
         */

        private String gc_id;
        private String gc_name;

        @Override
        public String getId() {
            return gc_id;
        }

        @Override
        public String getNasme() {
            return gc_name;
        }

        @Override
        public boolean isIsCheck() {
            return isChecked;
        }
    }

    public static class ListTimeBean  extends IBaseBean {
        /**
         * time_id : 0
         * time_name : 全部
         */

        private String time_id;
        private String time_name;

        @Override
        public String getId() {
            return time_id;
        }

        @Override
        public String getNasme() {
            return time_name;
        }

        @Override
        public boolean isIsCheck() {
            return isChecked;
        }
    }
}
