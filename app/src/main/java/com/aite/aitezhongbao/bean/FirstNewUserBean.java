package com.aite.aitezhongbao.bean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

public class FirstNewUserBean extends ErrorBean implements Serializable {


    /**
     * username : 18614079738
     * key : b14cbc7605c05abcc466340f7921c4eb
     */

    private String username;
    private String key;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
