package com.aite.aitezhongbao.bean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

public class SureFindPasswordCodeBean extends ErrorBean implements Serializable {

    /**
     * state : 1
     * msg : 验证通过
     */

    private String state;
    private String msg;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
