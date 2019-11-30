package com.aite.recylibrary.emnu;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class MainMultipleItem implements MultiItemEntity {
    public static final int DEVICE = 1;
    public static final int SOUND = 2;
    public static final int TITLE = 3;
    public static final int BOTTOM_LL = 4;
    public static final int HELPOLDERSERVICE = 5;
    private int itemType;
    private String soundstring;


    public List<Status> getSettingTv() {
        return settingTv;
    }

    public void setSettingTv(List<Status> settingTv) {
        this.settingTv = settingTv;
    }

    private List<Status> settingTv;

    public String getTitlebar() {
        return titlebar;
    }

    public void setTitlebar(String titlebar) {
        this.titlebar = titlebar;
    }

    private String titlebar;


    public MainMultipleItem(int itemType,String soundstring) {
        this.itemType = itemType;
        this.soundstring = soundstring;
    }
    public MainMultipleItem(int itemType,List<Status> settingTv) {
        this.itemType = itemType;
        this.settingTv = settingTv;
    }

    public String getSoundstring() {
        return soundstring;
    }

    public void setSoundstring(String soundstring) {
        this.soundstring = soundstring;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
