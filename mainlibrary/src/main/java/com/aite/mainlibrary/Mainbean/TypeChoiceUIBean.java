package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class TypeChoiceUIBean extends ErrorBean implements Serializable {

    private List<AdvListBean> adv_list;
    private List<ListClassBean> list_class;

    public List<AdvListBean> getAdv_list() {
        return adv_list;
    }

    public void setAdv_list(List<AdvListBean> adv_list) {
        this.adv_list = adv_list;
    }

    public List<ListClassBean> getList_class() {
        return list_class;
    }

    public void setList_class(List<ListClassBean> list_class) {
        this.list_class = list_class;
    }

    public static class AdvListBean {
        /**
         * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06263787889033609.jpg","adv_pic_url":""}
         * adv_title : 广告
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
             * adv_pic : http://zhongbyi.aitecc.com/data/upload/shop/adv/06263787889033609.jpg
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

    public static class ListClassBean {
        /**
         * gc_id : 16
         * gc_name : 特色经典
         */

        private String gc_id;
        private String gc_name;

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

        public String getGc_name() {
            return gc_name;
        }

        public void setGc_name(String gc_name) {
            this.gc_name = gc_name;
        }
    }
}
