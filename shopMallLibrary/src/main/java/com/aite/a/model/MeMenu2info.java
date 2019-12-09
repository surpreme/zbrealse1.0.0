package com.aite.a.model;

import java.util.List;

/**
 * 个人中心菜单
 * Created by mayn on 2018/4/26.
 */

public class MeMenu2info {
    public List<item>item;
    public String title;
    public static class item{
        public int img;
        public String txt;
        public String num;

        public item(int img, String txt, String num) {
            this.img = img;
            this.txt = txt;
            this.num = num;
        }
    }
    public MeMenu2info(List<MeMenu2info.item> item, String title) {
        this.item = item;
        this.title = title;
    }
}
