package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class HelpEatUIBean extends ErrorBean implements Serializable {

    /**
     * nursing_store_id : 0
     * nursing_store_name : 全部
     * nursing_list : [{"store_id":"2","store_name":"艾特技术","bill_cycle":0}]
     * adv_list : [{"adv_content":{"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06277711385957246.png","adv_pic_url":""},"adv_title":"轮播1","adv_desc":""}]
     */

    private int nursing_store_id;
    private String nursing_store_name;
    private List<NursingListBean> nursing_list;
    private List<AdvListBean> adv_list;

    public int getNursing_store_id() {
        return nursing_store_id;
    }

    public void setNursing_store_id(int nursing_store_id) {
        this.nursing_store_id = nursing_store_id;
    }

    public String getNursing_store_name() {
        return nursing_store_name;
    }

    public void setNursing_store_name(String nursing_store_name) {
        this.nursing_store_name = nursing_store_name;
    }

    public List<NursingListBean> getNursing_list() {
        return nursing_list;
    }

    public void setNursing_list(List<NursingListBean> nursing_list) {
        this.nursing_list = nursing_list;
    }

    public List<AdvListBean> getAdv_list() {
        return adv_list;
    }

    public void setAdv_list(List<AdvListBean> adv_list) {
        this.adv_list = adv_list;
    }

    public static class NursingListBean {
        /**
         * store_id : 2
         * store_name : 艾特技术
         * bill_cycle : 0
         */

        private String store_id;
        private String store_name;
        private int bill_cycle;

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

        public int getBill_cycle() {
            return bill_cycle;
        }

        public void setBill_cycle(int bill_cycle) {
            this.bill_cycle = bill_cycle;
        }
    }

    public static class AdvListBean {
        /**
         * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06277711385957246.png","adv_pic_url":""}
         * adv_title : 轮播1
         * adv_desc :
         */

        private AdvContentBean adv_content;
        private String adv_title;
        private String adv_desc;

        public AdvContentBean getAdv_content() {
            return adv_content;
        }

        public void setAdv_content(AdvContentBean adv_content) {
            this.adv_content = adv_content;
        }

        public String getAdv_title() {
            return adv_title;
        }

        public void setAdv_title(String adv_title) {
            this.adv_title = adv_title;
        }

        public String getAdv_desc() {
            return adv_desc;
        }

        public void setAdv_desc(String adv_desc) {
            this.adv_desc = adv_desc;
        }

        public static class AdvContentBean {
            /**
             * adv_pic : http://zhongbyi.aitecc.com/data/upload/shop/adv/06277711385957246.png
             * adv_pic_url :
             */

            private String adv_pic;
            private String adv_pic_url;

            public String getAdv_pic() {
                return adv_pic;
            }

            public void setAdv_pic(String adv_pic) {
                this.adv_pic = adv_pic;
            }

            public String getAdv_pic_url() {
                return adv_pic_url;
            }

            public void setAdv_pic_url(String adv_pic_url) {
                this.adv_pic_url = adv_pic_url;
            }
        }
    }
}
