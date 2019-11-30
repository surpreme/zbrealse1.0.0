package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-23
 * @desc:
 */
public class ElseHelpDoctorBean extends ErrorBean implements Serializable {


    private List<AdvListBean> adv_list;
    private List<ClassListBean> class_list;
    private List<AreaListBeanI> area_list;
    private List<TimeArrayBean> time_array;

    public List<AdvListBean> getAdv_list() {
        return adv_list;
    }

    public void setAdv_list(List<AdvListBean> adv_list) {
        this.adv_list = adv_list;
    }

    public List<ClassListBean> getClass_list() {
        return class_list;
    }

    public void setClass_list(List<ClassListBean> class_list) {
        this.class_list = class_list;
    }

    public List<AreaListBeanI> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<AreaListBeanI> area_list) {
        this.area_list = area_list;
    }

    public List<TimeArrayBean> getTime_array() {
        return time_array;
    }

    public void setTime_array(List<TimeArrayBean> time_array) {
        this.time_array = time_array;
    }

    public static class AdvListBean {
        /**
         * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06277528818205940.png","adv_pic_url":""}
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
             * adv_pic : http://zhongbyi.aitecc.com/data/upload/shop/adv/06277528818205940.png
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

    public static class ClassListBean extends IBaseBean{
        /**
         * class_id : 0
         * class_name : 全部
         */

        private String class_id;
        private String class_name;


        @Override
        public String getId() {
            return class_id;
        }

        @Override
        public String getNasme() {
            return class_name;
        }

        @Override
        public boolean isIsCheck() {
            return isChecked;
        }
    }

    public static class AreaListBeanI extends IBaseBean {
        /**
         * area_id : 0
         * area_name : 全部
         */

        private String area_id;
        private String area_name;

        @Override
        public String getId() {
            return area_id;
        }

        @Override
        public String getNasme() {
            return area_name;
        }

        @Override
        public boolean isIsCheck() {
            return isChecked;
        }

    }

    public static class TimeArrayBean extends IBaseBean{
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
