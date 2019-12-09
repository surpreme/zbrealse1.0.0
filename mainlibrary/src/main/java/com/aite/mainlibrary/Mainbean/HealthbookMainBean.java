package com.aite.mainlibrary.Mainbean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-07
 * @desc:
 */
public class HealthbookMainBean extends ErrorBean implements Serializable {

    /**
     * member_id : 7
     * member_height : null
     * member_weight : null
     * member_blood_types : null
     * is_organ_donor : null
     */

    private String member_id;
    private String member_height;
    private String member_weight;
    private String member_blood_types;
    private String is_organ_donor;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_height() {
        return member_height;
    }

    public void setMember_height(String member_height) {
        this.member_height = member_height;
    }

    public String getMember_weight() {
        return member_weight;
    }

    public void setMember_weight(String member_weight) {
        this.member_weight = member_weight;
    }

    public String getMember_blood_types() {
        return member_blood_types;
    }

    public void setMember_blood_types(String member_blood_types) {
        this.member_blood_types = member_blood_types;
    }

    public String getIs_organ_donor() {
        return is_organ_donor;
    }

    public void setIs_organ_donor(String is_organ_donor) {
        this.is_organ_donor = is_organ_donor;
    }
}
