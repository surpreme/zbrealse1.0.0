package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class HelpElderHouseListBean extends ErrorBean implements Serializable {


    /**
     * list_total : 1
     * is_nextpage : 0
     * list : [{"store_id":"2","store_name":"艾特技术","store_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/common/05048801297977373.png","bill_cycle":0,"is_binding":0}]
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
         * store_id : 2
         * store_name : 艾特技术
         * store_avatar : http://zhongbyi.aitecc.com/data/upload/shop/common/05048801297977373.png
         * bill_cycle : 0
         * is_binding : 0
         */

        private String store_id;
        private String store_name;
        private String store_avatar;

        public String getBill_cycle() {
            return bill_cycle;
        }

        public String getIs_binding() {
            return is_binding;
        }

        private String bill_cycle;
        private String is_binding;

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }


        public void setBill_cycle(String bill_cycle) {
            this.bill_cycle = bill_cycle;
        }

        public void setIs_binding(String is_binding) {
            this.is_binding = is_binding;
        }

    }
}
