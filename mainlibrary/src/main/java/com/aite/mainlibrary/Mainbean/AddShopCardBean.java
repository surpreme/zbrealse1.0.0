package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class AddShopCardBean extends ErrorBean implements Serializable {

    /**
     * code : 200
     * datas : 1
     */

    private int code;
    private String datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }
}
