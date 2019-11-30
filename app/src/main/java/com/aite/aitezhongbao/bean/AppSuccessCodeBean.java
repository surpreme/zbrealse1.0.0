package com.aite.aitezhongbao.bean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

public class AppSuccessCodeBean extends ErrorBean implements Serializable {

    /**
     * state : 1
     * msg : 新密码已经发送至您的手机，请尽快登录并更改密码！
     */

    private int state;
    private String msg;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
