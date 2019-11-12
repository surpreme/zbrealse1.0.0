package com.lzy.basemodule.string;



import java.util.List;

public class StringUtils {
    public static StringBuilder initList(List<String> list) {
        StringBuilder warehouse_order_id = new StringBuilder();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String a = list.get(i);
                if (i != list.size() - 1)
                    warehouse_order_id.append(a).append(",");
                else
                    warehouse_order_id.append(a);
            }
        }
        return warehouse_order_id;
    }
//    public static String getPingYin(String inputString) {
//        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
//        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//        format.setVCharType(HanyuPinyinVCharType.WITH_V);
//
//        char[] input = inputString.trim().toCharArray();
//        String output = "";
//
//        try {
//            for (char curchar : input) {
//                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
//                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
//                    output += temp[0];
//                } else
//                    output += java.lang.Character.toString(curchar);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
//        return output;
//    }
}
