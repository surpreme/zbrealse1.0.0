package com.aite.recylibrary.emnu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constant {
    public static String[] settingTv = {
            "助餐", "助医", "时间银行", "喘息服务", "其他服务"
    };


    public static List<MainMultipleItem> getMultipleItemData() {
        List<MainMultipleItem> list = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            list.add(new MainMultipleItem(MainMultipleItem.SOUND, "关爱老年健康  记忆与爱同行"));
            list.add(new MainMultipleItem(MainMultipleItem.TITLE, "我的设备"));
            List<Status> statuses = new ArrayList<>();
            for (String s : Arrays.asList(settingTv)) {
                Status status = new Status(s);
                statuses.add(status);
            }
            list.add(new MainMultipleItem(MainMultipleItem.HELPOLDERSERVICE, statuses));
        }

        return list;
    }
}
