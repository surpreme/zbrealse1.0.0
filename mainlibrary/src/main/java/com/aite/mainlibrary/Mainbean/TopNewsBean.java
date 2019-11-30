package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class TopNewsBean extends ErrorBean implements Serializable {

    /**
     * code : 200
     * datas : [{"article_id":"55","article_title":"中秋夜深圳地铁3号线被孔明灯逼停 地铁发生短路","article_image":"https://aitecc.com/data/upload/cms/article/2/05274232573365326.jpg","article_link":"","article_publish_time":"2016-09-17","article_author":"aitebobo","article_abstract":"15日晚23点43分左右，龙岗线地铁发生短路，产生火花。车站工作人员马上赶往现场进行处理，所幸没有乘客受伤，列车和车站器材也没有造成损坏。","article_click":"149","article_image_all":"a:1:{s:21:\"05274232573365326.jpg\";a:4:{s:4:\"name\";s:21:\"05274232573365326.jpg\";s:5:\"width\";i:238;s:6:\"height\";i:147;s:4:\"path\";s:1:\"2\";}}","article_comment_count":"45","article_class_id":"1","article_publisher_id":"2"},{"article_id":"54","article_title":"不惧电商不怕关店 实体零售怕什么？","article_image":"https://aitecc.com/data/upload/cms/article/166/05266760356119970.jpg","article_link":"","article_publish_time":"2016-09-08","article_author":"taowenping1988","article_abstract":"","article_click":"339","article_image_all":"a:1:{s:21:\"05266760356119970.jpg\";a:4:{s:4:\"name\";s:21:\"05266760356119970.jpg\";s:5:\"width\";i:468;s:6:\"height\";i:348;s:4:\"path\";s:3:\"166\";}}","article_comment_count":"24","article_class_id":"1","article_publisher_id":"166"},{"article_id":"43","article_title":"不惧电商不怕关店 实体零售怕什么？","article_image":"https://aitecc.com/data/upload/cms/article/3/05265570720563181.jpg","article_link":"","article_publish_time":"2016-09-07","article_author":"aitesoft","article_abstract":"关店、销售乏力、利润下滑，在外界看来，实体零售屡屡被曝出的窘境与电商的侵袭不无关系。不过在零售操盘手看来，电商并不可怕，关店也是一个正常的商业决策，相反，一些不那么显而易见的地方，比如城市商业规划、政策监管以及相关税费调整、房租成本，才是真正影响到实体零售命运的因素。","article_click":"324","article_image_all":"a:1:{s:21:\"05265570720563181.jpg\";a:4:{s:4:\"name\";s:21:\"05265570720563181.jpg\";s:5:\"width\";i:468;s:6:\"height\";i:348;s:4:\"path\";s:1:\"3\";}}","article_comment_count":"1","article_class_id":"1","article_publisher_id":"3"}]
     */

    private int code;
    private List<DatasBean> datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * article_id : 55
         * article_title : 中秋夜深圳地铁3号线被孔明灯逼停 地铁发生短路
         * article_image : https://aitecc.com/data/upload/cms/article/2/05274232573365326.jpg
         * article_link :
         * article_publish_time : 2016-09-17
         * article_author : aitebobo
         * article_abstract : 15日晚23点43分左右，龙岗线地铁发生短路，产生火花。车站工作人员马上赶往现场进行处理，所幸没有乘客受伤，列车和车站器材也没有造成损坏。
         * article_click : 149
         * article_image_all : a:1:{s:21:"05274232573365326.jpg";a:4:{s:4:"name";s:21:"05274232573365326.jpg";s:5:"width";i:238;s:6:"height";i:147;s:4:"path";s:1:"2";}}
         * article_comment_count : 45
         * article_class_id : 1
         * article_publisher_id : 2
         */

        private String article_id;
        private String article_title;
        private String article_image;
        private String article_link;
        private String article_publish_time;
        private String article_author;
        private String article_abstract;
        private String article_click;
        private String article_image_all;
        private String article_comment_count;
        private String article_class_id;
        private String article_publisher_id;

        public String getArticle_id() {
            return article_id;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public String getArticle_title() {
            return article_title;
        }

        public void setArticle_title(String article_title) {
            this.article_title = article_title;
        }

        public String getArticle_image() {
            return article_image;
        }

        public void setArticle_image(String article_image) {
            this.article_image = article_image;
        }

        public String getArticle_link() {
            return article_link;
        }

        public void setArticle_link(String article_link) {
            this.article_link = article_link;
        }

        public String getArticle_publish_time() {
            return article_publish_time;
        }

        public void setArticle_publish_time(String article_publish_time) {
            this.article_publish_time = article_publish_time;
        }

        public String getArticle_author() {
            return article_author;
        }

        public void setArticle_author(String article_author) {
            this.article_author = article_author;
        }

        public String getArticle_abstract() {
            return article_abstract;
        }

        public void setArticle_abstract(String article_abstract) {
            this.article_abstract = article_abstract;
        }

        public String getArticle_click() {
            return article_click;
        }

        public void setArticle_click(String article_click) {
            this.article_click = article_click;
        }

        public String getArticle_image_all() {
            return article_image_all;
        }

        public void setArticle_image_all(String article_image_all) {
            this.article_image_all = article_image_all;
        }

        public String getArticle_comment_count() {
            return article_comment_count;
        }

        public void setArticle_comment_count(String article_comment_count) {
            this.article_comment_count = article_comment_count;
        }

        public String getArticle_class_id() {
            return article_class_id;
        }

        public void setArticle_class_id(String article_class_id) {
            this.article_class_id = article_class_id;
        }

        public String getArticle_publisher_id() {
            return article_publisher_id;
        }

        public void setArticle_publisher_id(String article_publisher_id) {
            this.article_publisher_id = article_publisher_id;
        }
    }
}
