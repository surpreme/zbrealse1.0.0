package com.aite.a.model;

/**
 * 支付宝
 * Created by Administrator on 2017/9/30.
 */
public class ZfbGetConfigInfo {
    public String payment_id;
    public String payment_code;
    public String payment_name;
    public payment_config payment_config;
    public String payment_state;
    public String store_id;
    public static class payment_config{
        public String app_id;
        public String merchant_private_key;
        public String alipay_public_key;
        public String sign;
    }
}
