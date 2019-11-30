package com.aite.mainlibrary.Mainbean;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-28
 * @desc:
 */
public abstract class IBaseBean {

    protected boolean isChecked;

    public abstract String  getId();

    public abstract String getNasme();

    public abstract boolean isIsCheck();

    public void setChecked(boolean isChecked){
        this.isChecked = isChecked;
    }
}
