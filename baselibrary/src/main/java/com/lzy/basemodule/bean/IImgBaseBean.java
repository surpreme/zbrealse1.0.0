package com.lzy.basemodule.bean;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-28
 * @desc:
 */
public abstract class IImgBaseBean {

    protected boolean isChecked;

    public abstract String getId();

    public abstract String getImg();
    public abstract String getCode();


    public abstract String getNasme();

    public abstract boolean isIsCheck();

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
