package com.aite.mainlibrary.Mainbean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-25
 * @desc:
 */
public class TestChirenBean implements Serializable {
    public String chirenid;
    public String chirenname;
    public Boolean chirenisChecked;

    public String getChirenid() {
        return chirenid;
    }

    public void setChirenid(String chirenid) {
        this.chirenid = chirenid;
    }

    public String getChirenname() {
        return chirenname;
    }

    public void setChirenname(String chirenname) {
        this.chirenname = chirenname;
    }

    public Boolean getChirenisChecked() {
        return chirenisChecked;
    }

    public void setChirenisChecked(Boolean chirenisChecked) {
        this.chirenisChecked = chirenisChecked;
    }
}
