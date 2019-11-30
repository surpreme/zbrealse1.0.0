package com.aite.aitezhongbao.d;

import com.aite.mainlibrary.Mainbean.MainUiDataBean;

import java.util.Iterator;

public class p {
    //win+r全局替换
//     for (MainUiDataBean.AdvListBean advListBean : ((MainUiDataBean) msg).getAdv_list()) {
//        list_img.add(advListBean.getAdv_content().getAdv_pic());
//    }
//    Iterator<MainUiDataBean.AdvListBean> iterator1 = ((MainUiDataBean) msg).getAdv_list().iterator();
//            while (iterator1.hasNext()) {
//        list_img.add(iterator1.next().getAdv_content().getAdv_pic());
//    }
    //Palette.from(bitmap).
//
//    generate(new Palette.PaletteAsyncListener() {
//        @Override
//        public void onGenerated (Palette palette){
//            Palette.Swatch vibrant = palette.getVibrantSwatch();
//            if (vibrant == null) {
//                for (Palette.Swatch swatch : palette.getSwatches()) {
//                    vibrant = swatch;
//                    break;
//                }
//            }
//            // 这样获取的颜色可以进行改变。
//            int rbg = vibrant.getRgb();
//
//            // ... 省略一些无关紧要的代码
//
//            tabLayout.setBackgroundColor(rbg);
//            toolbar.setBackgroundColor(rbg);
//            if (Build.VERSION.SDK_INT > 21) {
//                Window window = getWindow();
//                //状态栏改变颜色。
//                int color = changeColor(rbg);
//                window.setStatusBarColor(color);
//            }
//        }
//    });
//
//    // 对获取到的RGB颜色进行修改。（涉及到位运算，我也不是很懂这块）
//    private int changeColor(int rgb) {
//        int red = rgb >> 16 & 0xFF;
//        int green = rgb >> 8 & 0xFF;
//        int blue = rgb & 0xFF;
//        red = (int) Math.floor(red * (1 - 0.2));
//        green = (int) Math.floor(green * (1 - 0.2));
//        blue = (int) Math.floor(blue * (1 - 0.2));
//        return Color.rgb(red, green, blue);
//    }
}
