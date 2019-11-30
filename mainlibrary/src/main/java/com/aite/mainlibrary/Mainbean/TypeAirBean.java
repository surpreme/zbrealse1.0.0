package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class TypeAirBean extends ErrorBean implements Serializable {

    /**
     * member_info : {"member_name":"18614079738","member_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/common/default_user_portrait.gif","member_levelname":"默认等级"}
     * adv_list : [{"adv_content":{"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06277528581226859.png","adv_pic_url":""},"adv_title":"广告","adv_desc":""}]
     * class_list : [{"class_id":0,"class_name":"全部"},{"class_id":"1","class_name":"测试1"},{"class_id":"2","class_name":"测试2"}]
     * area_list : [{"area_id":0,"area_name":"全部"},{"area_id":"1","area_name":"北京"},{"area_id":"2","area_name":"天津"},{"area_id":"3","area_name":"河北"},{"area_id":"4","area_name":"山西"},{"area_id":"5","area_name":"内蒙古"},{"area_id":"6","area_name":"辽宁"},{"area_id":"7","area_name":"吉林"},{"area_id":"8","area_name":"黑龙江"},{"area_id":"9","area_name":"上海"},{"area_id":"10","area_name":"江苏"},{"area_id":"11","area_name":"浙江"},{"area_id":"12","area_name":"安徽"},{"area_id":"13","area_name":"福建"},{"area_id":"14","area_name":"江西"},{"area_id":"15","area_name":"山东"},{"area_id":"16","area_name":"河南"},{"area_id":"17","area_name":"湖北"},{"area_id":"18","area_name":"湖南"},{"area_id":"19","area_name":"广东"},{"area_id":"20","area_name":"广西"},{"area_id":"21","area_name":"海南"},{"area_id":"22","area_name":"重庆"},{"area_id":"23","area_name":"四川"},{"area_id":"24","area_name":"贵州"},{"area_id":"25","area_name":"云南"},{"area_id":"26","area_name":"西藏"},{"area_id":"27","area_name":"陕西"},{"area_id":"28","area_name":"甘肃"},{"area_id":"29","area_name":"青海"},{"area_id":"30","area_name":"宁夏"},{"area_id":"31","area_name":"新疆"},{"area_id":"32","area_name":"台湾"},{"area_id":"33","area_name":"香港"},{"area_id":"34","area_name":"澳门"},{"area_id":"35","area_name":"海外"}]
     * time_array : [{"time_id":0,"time_name":"全部"},{"time_id":1,"time_name":"1天"},{"time_id":2,"time_name":"3天"},{"time_id":3,"time_name":"5天"},{"time_id":4,"time_name":"一周"},{"time_id":5,"time_name":"半个月"}]
     */

    private MemberInfoBean member_info;
    private List<AdvListBean> adv_list;
    private List<ClassListBean> class_list;
    private List<AreaListBean> area_list;
    private List<TimeArrayBean> time_array;

    public MemberInfoBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoBean member_info) {
        this.member_info = member_info;
    }

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

    public List<AreaListBean> getArea_list() {
        return area_list;
    }

    public void setArea_list(List<AreaListBean> area_list) {
        this.area_list = area_list;
    }

    public List<TimeArrayBean> getTime_array() {
        return time_array;
    }

    public void setTime_array(List<TimeArrayBean> time_array) {
        this.time_array = time_array;
    }

    public static class MemberInfoBean {
        /**
         * member_name : 18614079738
         * member_avatar : http://zhongbyi.aitecc.com/data/upload/shop/common/default_user_portrait.gif
         * member_levelname : 默认等级
         */

        private String member_name;
        private String member_avatar;
        private String member_levelname;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getMember_avatar() {
            return member_avatar;
        }

        public void setMember_avatar(String member_avatar) {
            this.member_avatar = member_avatar;
        }

        public String getMember_levelname() {
            return member_levelname;
        }

        public void setMember_levelname(String member_levelname) {
            this.member_levelname = member_levelname;
        }
    }

    public static class AdvListBean {
        /**
         * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06277528581226859.png","adv_pic_url":""}
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
             * adv_pic : http://zhongbyi.aitecc.com/data/upload/shop/adv/06277528581226859.png
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

    public static class AreaListBean extends IBaseBean{
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

    public static class TimeArrayBean extends IBaseBean {
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
