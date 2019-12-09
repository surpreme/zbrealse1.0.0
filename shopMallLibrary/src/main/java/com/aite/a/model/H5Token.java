package com.aite.a.model;

/**
 * 网页授权
 * Created by Administrator on 2017/8/23.
 */
public class H5Token {
    public String code;
    public datas datas;

    public static class datas {
        public String access_token;
        public String access_token_exptime;
    }
}
