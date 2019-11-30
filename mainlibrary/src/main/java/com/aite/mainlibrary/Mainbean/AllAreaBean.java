package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class AllAreaBean extends ErrorBean implements Serializable {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * area_id : 1
         * area_name : 北京
         * citylist : [{"area_id":"36","area_name":"北京市","arealist":[{"area_id":"37","area_name":"东城区"},{"area_id":"38","area_name":"西城区"},{"area_id":"41","area_name":"朝阳区"},{"area_id":"42","area_name":"丰台区"},{"area_id":"43","area_name":"石景山区"},{"area_id":"44","area_name":"海淀区"},{"area_id":"45","area_name":"门头沟区"},{"area_id":"46","area_name":"房山区"},{"area_id":"47","area_name":"通州区"},{"area_id":"48","area_name":"顺义区"},{"area_id":"49","area_name":"昌平区"},{"area_id":"50","area_name":"大兴区"},{"area_id":"51","area_name":"怀柔区"},{"area_id":"52","area_name":"平谷区"},{"area_id":"53","area_name":"密云县"},{"area_id":"54","area_name":"延庆县"},{"area_id":"566","area_name":"其他"}]}]
         */

        private String area_id;
        private String area_name;
        private List<CitylistBean> citylist;

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

        public List<CitylistBean> getCitylist() {
            return citylist;
        }

        public void setCitylist(List<CitylistBean> citylist) {
            this.citylist = citylist;
        }

        public static class CitylistBean {
            /**
             * area_id : 36
             * area_name : 北京市
             * arealist : [{"area_id":"37","area_name":"东城区"},{"area_id":"38","area_name":"西城区"},{"area_id":"41","area_name":"朝阳区"},{"area_id":"42","area_name":"丰台区"},{"area_id":"43","area_name":"石景山区"},{"area_id":"44","area_name":"海淀区"},{"area_id":"45","area_name":"门头沟区"},{"area_id":"46","area_name":"房山区"},{"area_id":"47","area_name":"通州区"},{"area_id":"48","area_name":"顺义区"},{"area_id":"49","area_name":"昌平区"},{"area_id":"50","area_name":"大兴区"},{"area_id":"51","area_name":"怀柔区"},{"area_id":"52","area_name":"平谷区"},{"area_id":"53","area_name":"密云县"},{"area_id":"54","area_name":"延庆县"},{"area_id":"566","area_name":"其他"}]
             */

            private String area_id;
            private String area_name;
            private List<ArealistBean> arealist;

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

            public List<ArealistBean> getArealist() {
                return arealist;
            }

            public void setArealist(List<ArealistBean> arealist) {
                this.arealist = arealist;
            }

            public static class ArealistBean {
                /**
                 * area_id : 37
                 * area_name : 东城区
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
}
