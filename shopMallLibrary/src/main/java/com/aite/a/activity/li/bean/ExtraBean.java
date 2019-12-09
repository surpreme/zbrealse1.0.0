package com.aite.a.activity.li.bean;

public class ExtraBean<T> {
    String pId;
    T t;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
