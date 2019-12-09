package com.aite.a.activity.li.bean;

import java.io.Serializable;
import java.util.List;

public class AroundAdvertisementBean implements Serializable {

    /**
     * error_code : 0
     * message :
     * datas : {"swiper":[{"adv_pic":"https://aitecc.com/data/upload/shop/adv/06197183845083670.png","adv_pic_url":""}],"adv":[{"title":"优惠专区","list":[{"adv_pic":"https://aitecc.com/data/upload/shop/adv/06197185816654575.png","adv_pic_url":""},{"adv_pic":"https://aitecc.com/data/upload/shop/adv/06197185564770156.png","adv_pic_url":""}]},{"title":"优选下午茶","list":[{"adv_pic":"https://aitecc.com/data/upload/shop/adv/06197186404308293.png","adv_pic_url":""},{"adv_pic":"https://aitecc.com/data/upload/shop/adv/06197186207748772.png","adv_pic_url":""}]}]}
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
        private List<SwiperBean> swiper;
        private List<AdvBean> adv;

        public List<SwiperBean> getSwiper() {
            return swiper;
        }

        public void setSwiper(List<SwiperBean> swiper) {
            this.swiper = swiper;
        }

        public List<AdvBean> getAdv() {
            return adv;
        }

        public void setAdv(List<AdvBean> adv) {
            this.adv = adv;
        }

        public static class SwiperBean {
            /**
             * adv_pic : https://aitecc.com/data/upload/shop/adv/06197183845083670.png
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

        public static class AdvBean {
            /**
             * title : 优惠专区
             * list : [{"adv_pic":"https://aitecc.com/data/upload/shop/adv/06197185816654575.png","adv_pic_url":""},{"adv_pic":"https://aitecc.com/data/upload/shop/adv/06197185564770156.png","adv_pic_url":""}]
             */

            private String title;
            private List<ListBean> list;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * adv_pic : https://aitecc.com/data/upload/shop/adv/06197185816654575.png
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
}
