package com.aite.mainlibrary.Mainbean;

import com.google.gson.annotations.SerializedName;
import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;
import java.util.List;

public class MineTogetherServiceBean extends ErrorBean implements Serializable {

    /**
     * list_total : 38
     * is_nextpage : 1
     * list : [{"id":"2","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733413","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"3","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733415","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"4","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733416","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"5","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733416","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"6","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733416","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"7","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733416","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"8","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733417","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"9","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733417","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"10","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733417","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0},{"id":"11","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"娜娜山","start_time":"2019-11-27","end_time":"01-01","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733417","status":"0","order_status":"0","memebr_avatar":"http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg","is_cancel":1,"is_delete":1,"isverify":0,"qrcodeimg":"","is_evaluate":0}]
     */

    private String list_total;
    private int is_nextpage;
    private List<ListBean> list;

    public String getList_total() {
        return list_total;
    }

    public void setList_total(String list_total) {
        this.list_total = list_total;
    }

    public int getIs_nextpage() {
        return is_nextpage;
    }

    public void setIs_nextpage(int is_nextpage) {
        this.is_nextpage = is_nextpage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 2
         * member_id : 7
         * title : 医生
         * doctor_name : 内科
         * department : 内科
         * class_id : 1
         * realname :
         * mobile : 18614079728
         * province_id : 1
         * city_id : 2
         * area_id : 3
         * address : 娜娜山
         * start_time : 2019-11-27
         * end_time : 01-01
         * duration : 840.0
         * remarks : 无
         * credit : 0.00
         * addtime : 1574733413
         * status : 0
         * order_status : 0
         * memebr_avatar : http://zhongbyi.aitecc.com/data/upload/shop/avatar/avatar_7.jpg
         * is_cancel : 1
         * is_delete : 1
         * isverify : 0
         * qrcodeimg :
         * is_evaluate : 0
         */

        private String id;
        private String member_id;
        @SerializedName(value = "title", alternate = {"dh_title", "tb_title", "rp_title"})
        private String title;


        //服务id
        private String tb_id;
        private String doctor_name;
        private String department;
        private String class_id;
        private String realname;
        private String mobile;
        private String province_id;
        private String city_id;
        private String area_id;
        private String address;
        private String start_time;
        private String end_time;
        private String duration;
        private String remarks;
        private String credit;
        private String addtime;
        private String status;
        private String order_status;
        private String memebr_avatar;
        private int is_cancel;
        private int is_delete;
        private int isverify;
        private String qrcodeimg;
        private int is_evaluate;

        public String getId() {
            return id;
        }

        public String getTb_id() {
            return tb_id;
        }

        public void setTb_id(String tb_id) {
            this.tb_id = tb_id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea_id() {
            return area_id;
        }


        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getMemebr_avatar() {
            return memebr_avatar;
        }

        public void setMemebr_avatar(String memebr_avatar) {
            this.memebr_avatar = memebr_avatar;
        }

        public int getIs_cancel() {
            return is_cancel;
        }

        public void setIs_cancel(int is_cancel) {
            this.is_cancel = is_cancel;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public int getIsverify() {
            return isverify;
        }

        public void setIsverify(int isverify) {
            this.isverify = isverify;
        }

        public String getQrcodeimg() {
            return qrcodeimg;
        }

        public void setQrcodeimg(String qrcodeimg) {
            this.qrcodeimg = qrcodeimg;
        }

        public int getIs_evaluate() {
            return is_evaluate;
        }

        public void setIs_evaluate(int is_evaluate) {
            this.is_evaluate = is_evaluate;
        }
    }
}
