package com.aite.aitezhongbao.bean;


import com.aite.aitezhongbao.View.CityInterface;

/**
 * 创建者   Administrator
 * 创建时间 2018/6/12 17:28
 * 描述:   TODO
 */

public class City implements CityInterface {



    private String Name;
    private String id;




    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId(   ) {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    @Override
    public String getCityName() {
        return Name;
    }


}
