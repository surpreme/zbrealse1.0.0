package com.aite.a.model;

import java.util.List;

/**评价列表
 * Created by mayn on 2018/11/14.
 */
public class GoodsEvaluateInfo {
    public goods_evaluate_info goods_evaluate_info;
    public String list_total;
    public String is_nextpage;
    public List<goodsevallist> goodsevallist;

    public static class goods_evaluate_info {
        public String good;
        public String normal;
        public String bad;
        public String all;
        public String good_percent;
        public String normal_percent;
        public String bad_percent;
        public String good_star;
        public String star_average;
        public String pj_url;
        public String cl_url;
        public String comments_list_url;
    }

    public static class goodsevallist {
        public String geval_id;
        public String geval_orderid;
        public String geval_orderno;
        public String geval_ordergoodsid;
        public String geval_goodsid;
        public String geval_goodsname;
        public String geval_goodsprice;
        public String geval_goodsimage;
        public String geval_scores;
        public String geval_content;
        public String geval_isanonymous;
        public String geval_addtime;
        public String geval_storeid;
        public String geval_storename;
        public String geval_frommemberid;
        public String geval_frommembername;
        public String geval_state;
        public String geval_remark;
        public String geval_explain;
        public String geval_frommemberavatar;
        public List<String> geval_image;
    }

}
