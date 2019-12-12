package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;
import com.lzy.basemodule.bean.IImgBaseBean;

import java.io.Serializable;
import java.util.List;

public class PayListBean extends ErrorBean implements Serializable {


    /**
     * code : 200
     * datas : [{"payment_id":"1","payment_code":"alipay","payment_name":"支付宝","payment_image":"http://zhongbyi.aitecc.com/mall/templates/default/images/payment/alipay_logo.gif"},{"payment_id":"3","payment_code":"app_wxpay","payment_name":"微信外支付","payment_image":"http://zhongbyi.aitecc.com/mall/templates/default/images/payment/app_wxpay_logo.gif"},{"payment_id":99,"payment_code":"predeposit","payment_name":"钱包","payment_image":"http://zhongbyi.aitecc.com/mall/templates/default/images/payment/predeposit_logo.gif"}]
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

    public static class DatasBean extends IImgBaseBean {
        /**
         * payment_id : 1
         * payment_code : alipay
         * payment_name : 支付宝
         * payment_image : http://zhongbyi.aitecc.com/mall/templates/default/images/payment/alipay_logo.gif
         */

        private String payment_id;
        private String payment_code;
        private String payment_name;
        private String payment_image;


        @Override
        public String getId() {
            return payment_id;
        }

        @Override
        public String getImg() {
            return payment_image;
        }

        @Override
        public String getCode() {
            return payment_code;
        }

        @Override
        public String getNasme() {
            return payment_name;
        }

        @Override
        public boolean isIsCheck() {
            return isChecked;
        }
    }
}
