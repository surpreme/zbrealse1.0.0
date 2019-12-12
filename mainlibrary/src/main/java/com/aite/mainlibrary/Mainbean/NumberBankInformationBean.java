package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

public class NumberBankInformationBean extends ErrorBean implements Serializable {


    /**
     * member_volunteer_points : 2880
     * expired_credit : 1920.00
     * expired_time : 2020-12-11
     */

    private String member_volunteer_points;
    private String expired_credit;
    private String expired_time;

    public String getMember_volunteer_points() {
        return member_volunteer_points;
    }

    public void setMember_volunteer_points(String member_volunteer_points) {
        this.member_volunteer_points = member_volunteer_points;
    }

    public String getExpired_credit() {
        return expired_credit;
    }

    public void setExpired_credit(String expired_credit) {
        this.expired_credit = expired_credit;
    }

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }
}
