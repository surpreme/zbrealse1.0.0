package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class TimeShoplistBean extends ErrorBean implements Serializable {


    /**
     * list_total : 4
     * is_nextpage : 0
     * list : [{"pgoods_id":"4","pgoods_name":"西装","pgoods_points":"5000","pgoods_image":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287977541665083_mid.png","pgoods_image_old":"06287977541665083.png","pgoods_image_small":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287977541665083_small.png","ex_state":"end"},{"pgoods_id":"3","pgoods_name":"皮草","pgoods_points":"35000","pgoods_image":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287976971441165_mid.png","pgoods_image_old":"06287976971441165.png","pgoods_image_small":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287976971441165_small.png","ex_state":"end"},{"pgoods_id":"2","pgoods_name":"卫衣","pgoods_points":"400","pgoods_image":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287976637177408_mid.png","pgoods_image_old":"06287976637177408.png","pgoods_image_small":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287976637177408_small.png","ex_state":"end"},{"pgoods_id":"1","pgoods_name":"大衣","pgoods_points":"1000","pgoods_image":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287976171183738_mid.png","pgoods_image_old":"06287976171183738.png","pgoods_image_small":"http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287976171183738_small.png","ex_state":"end"}]
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
         * pgoods_id : 4
         * pgoods_name : 西装
         * pgoods_points : 5000
         * pgoods_image : http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287977541665083_mid.png
         * pgoods_image_old : 06287977541665083.png
         * pgoods_image_small : http://zhongbyi.aitecc.com/data/upload/shop/pointprod/06287977541665083_small.png
         * ex_state : end
         */

        private String pgoods_id;
        private String pgoods_name;
        private String pgoods_points;
        private String pgoods_image;
        private String pgoods_image_old;
        private String pgoods_image_small;
        private String ex_state;

        public String getPgoods_id() {
            return pgoods_id;
        }

        public void setPgoods_id(String pgoods_id) {
            this.pgoods_id = pgoods_id;
        }

        public String getPgoods_name() {
            return pgoods_name;
        }

        public void setPgoods_name(String pgoods_name) {
            this.pgoods_name = pgoods_name;
        }

        public String getPgoods_points() {
            return pgoods_points;
        }

        public void setPgoods_points(String pgoods_points) {
            this.pgoods_points = pgoods_points;
        }

        public String getPgoods_image() {
            return pgoods_image;
        }

        public void setPgoods_image(String pgoods_image) {
            this.pgoods_image = pgoods_image;
        }

        public String getPgoods_image_old() {
            return pgoods_image_old;
        }

        public void setPgoods_image_old(String pgoods_image_old) {
            this.pgoods_image_old = pgoods_image_old;
        }

        public String getPgoods_image_small() {
            return pgoods_image_small;
        }

        public void setPgoods_image_small(String pgoods_image_small) {
            this.pgoods_image_small = pgoods_image_small;
        }

        public String getEx_state() {
            return ex_state;
        }

        public void setEx_state(String ex_state) {
            this.ex_state = ex_state;
        }
    }
}
