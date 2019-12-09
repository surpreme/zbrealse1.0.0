package com.lzy.basemodule.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lzy.basemodule.util.ArithUtil;

import java.lang.ref.WeakReference;

public class BitmapUtils {



    public static Bitmap setBitmapSize(WeakReference<Context> reference, int id, int w, int h, boolean isAlpha) {
        Resources resources = reference.get().getResources();
        BitmapFactory.Options opts = new BitmapFactory.Options();//获取自定义参数对象
        opts.inJustDecodeBounds = true;//设置只是解密（减少占用图片内存）只是修改图片属性
        //修复图片参数（给的是图片地址，修改图片参数先要调用可以修改参数的方法decodeResource（）第三参数就是修改参数的对象）
        //执行 BitmapFactory.decodeResource（）方法 设置的opts属性才生效
        BitmapFactory.decodeResource(resources, id, opts);
        /**
         * 修改参数
         */
        //先获取加载图片的宽高
        int outWidth = opts.outWidth;
        int outHeight = opts.outHeight;

        //设置缩放图片的系数(int类型)
        opts.inSampleSize = getSampleSize(outWidth, outHeight, w, h);

        //设置是否需要透明度//安卓默认透明度未
        if (!isAlpha) {
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
        }

        //注意前面设置只获取图片信息，这里要设置回获取图片
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    //计算图片实际宽高 是 传入宽高值的 最小整数倍数
    private static int getSampleSize(int outWidth, int outHeight, int w, int h) {
        /*int sizer = 1;
        if (outWidth > w && outHeight > h) {
            sizer = 2;
            while (outWidth / sizer > w && outHeight / sizer > h) {
                sizer *= 2;
            }
        }

        sizer /= 2;*/
        int sizer = (int) (ArithUtil.round(ArithUtil.div(outWidth,w) > ArithUtil.div(outHeight,h)
                ? ArithUtil.div(outWidth,w):ArithUtil.div(outHeight,h),0));
        return sizer;
    }
}
