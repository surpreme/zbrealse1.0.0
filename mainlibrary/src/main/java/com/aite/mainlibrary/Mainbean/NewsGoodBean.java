package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class NewsGoodBean extends ErrorBean implements Serializable {


    /**
     * code : 200
     * datas : [{"article_id":"60","article_title":"以习近平同志为核心的党中央关心内蒙古发展纪实","article_image":"https://aitecc.com/data/upload/cms/article/135/05554305132932828.png","article_link":"","article_publish_time":"2017-08-07","article_author":"aiteshop","article_abstract":"美好梦想，奔驰在辽阔草原\u2014\u2014以习近平同志为核心的党中央关心内蒙古发展纪实","article_click":"274","article_image_all":"a:1:{s:21:\"05554305132932828.png\";a:4:{s:4:\"name\";s:21:\"05554305132932828.png\";s:5:\"width\";i:414;s:6:\"height\";i:384;s:4:\"path\";s:3:\"135\";}}","article_comment_count":"7","article_class_id":"6","article_publisher_id":"135"},{"article_id":"59","article_title":"丹麦曲奇饼干盒\u201c妙用\u201d","article_image":"https://aitecc.com/data/upload/cms/article/135/05550205267396306.png","article_link":"","article_publish_time":"2017-08-02","article_author":"aiteshop","article_abstract":"丹麦曲奇饼干盒\u201c妙用\u201d","article_click":"235","article_image_all":"a:1:{s:21:\"05550205267396306.png\";a:4:{s:4:\"name\";s:21:\"05550205267396306.png\";s:5:\"width\";i:232;s:6:\"height\";i:182;s:4:\"path\";s:3:\"135\";}}","article_comment_count":"47","article_class_id":"5","article_publisher_id":"135"},{"article_id":"55","article_title":"中秋夜深圳地铁3号线被孔明灯逼停 地铁发生短路","article_image":"https://aitecc.com/data/upload/cms/article/2/05274232573365326.jpg","article_link":"","article_publish_time":"2016-09-17","article_author":"aitebobo","article_abstract":"15日晚23点43分左右，龙岗线地铁发生短路，产生火花。车站工作人员马上赶往现场进行处理，所幸没有乘客受伤，列车和车站器材也没有造成损坏。","article_click":"149","article_image_all":"a:1:{s:21:\"05274232573365326.jpg\";a:4:{s:4:\"name\";s:21:\"05274232573365326.jpg\";s:5:\"width\";i:238;s:6:\"height\";i:147;s:4:\"path\";s:1:\"2\";}}","article_comment_count":"45","article_class_id":"1","article_publisher_id":"2"}]
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
         * article_id : 60
         * article_title : 以习近平同志为核心的党中央关心内蒙古发展纪实
         * article_image : https://aitecc.com/data/upload/cms/article/135/05554305132932828.png
         * article_link :
         * article_publish_time : 2017-08-07
         * article_author : aiteshop
         * article_abstract : 美好梦想，奔驰在辽阔草原——以习近平同志为核心的党中央关心内蒙古发展纪实
         * article_click : 274
         * article_image_all : a:1:{s:21:"05554305132932828.png";a:4:{s:4:"name";s:21:"05554305132932828.png";s:5:"width";i:414;s:6:"height";i:384;s:4:"path";s:3:"135";}}
         * article_comment_count : 7
         * article_class_id : 6
         * article_publisher_id : 135
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
