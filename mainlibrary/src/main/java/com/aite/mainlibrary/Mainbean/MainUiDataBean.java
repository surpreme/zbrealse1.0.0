package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class MainUiDataBean extends ErrorBean implements Serializable {

    private List<AdvListBean> adv_list;
    private List<?> article;
    private List<PensionAdvsBean> pension_advs;
    private List<DisAdvsBean> dis_advs;

    public List<AdvListBean> getAdv_list() {
        return adv_list;
    }

    public void setAdv_list(List<AdvListBean> adv_list) {
        this.adv_list = adv_list;
    }

    public List<?> getArticle() {
        return article;
    }

    public void setArticle(List<?> article) {
        this.article = article;
    }

    public List<PensionAdvsBean> getPension_advs() {
        return pension_advs;
    }

    public void setPension_advs(List<PensionAdvsBean> pension_advs) {
        this.pension_advs = pension_advs;
    }

    public List<DisAdvsBean> getDis_advs() {
        return dis_advs;
    }

    public void setDis_advs(List<DisAdvsBean> dis_advs) {
        this.dis_advs = dis_advs;
    }

    public static class AdvListBean {
        /**
         * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06269587721719728.jpg","adv_pic_url":""}
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
             * adv_pic : http://zhongbyi.aitecc.com/data/upload/shop/adv/06269587721719728.jpg
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

    public static class PensionAdvsBean {
        /**
         * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06269595991492265.png","adv_pic_url":""}
         * adv_title : 助餐
         * adv_desc :
         * page_pension : 1
         */

        private AdvContentBeanX adv_content;
        private String adv_title;
        private String adv_desc;
        private int page_pension;

        public AdvContentBeanX getAdv_content() {
            return adv_content;
        }

        public void setAdv_content(AdvContentBeanX adv_content) {
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

        public int getPage_pension() {
            return page_pension;
        }

        public void setPage_pension(int page_pension) {
            this.page_pension = page_pension;
        }

        public static class AdvContentBeanX {
            /**
             * adv_pic : http://zhongbyi.aitecc.com/data/upload/shop/adv/06269595991492265.png
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

    public static class DisAdvsBean {
        /**
         * adv_content : {"adv_pic":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06269598566563979.png","adv_pic_url":""}
         * adv_title : 日托
         * adv_desc :
         * page_dis : 1
         */

        private AdvContentBeanXX adv_content;
        private String adv_title;
        private String adv_desc;
        private int page_dis;

        public AdvContentBeanXX getAdv_content() {
            return adv_content;
        }

        public void setAdv_content(AdvContentBeanXX adv_content) {
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

        public int getPage_dis() {
            return page_dis;
        }

        public void setPage_dis(int page_dis) {
            this.page_dis = page_dis;
        }

        public static class AdvContentBeanXX {
            /**
             * adv_pic : http://zhongbyi.aitecc.com/data/upload/shop/adv/06269598566563979.png
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
