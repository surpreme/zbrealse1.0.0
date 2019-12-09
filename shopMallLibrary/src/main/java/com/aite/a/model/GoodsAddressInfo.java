package com.aite.a.model;

import java.util.List;

/**
 * 商品所在地
 * Created by Administrator on 2017/5/4.
 */
public class GoodsAddressInfo {
    public List<list> list;

    public static class list {
        public String area_id;
        public String area_name;
        public List<citylist> citylist;

        public static class citylist {
            public String area_id;
            public String area_name;
        }

    }

}
