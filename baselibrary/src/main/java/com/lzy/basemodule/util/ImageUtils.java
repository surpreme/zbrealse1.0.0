package com.lzy.basemodule.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.lzy.basemodule.BaseConstant.BaseConstant;
import com.lzy.basemodule.logcat.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static ImageUtils mInstance;

    public static ImageUtils getmInstance() {
        if (mInstance == null) {
            synchronized (ImageUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageUtils();
                }
            }
        }
        return mInstance;
    }

    public static void photoClip(Activity activity, Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, BaseConstant.RESULT_CODE.REQUEST_CODE_CHOOSE_IMAGE_CLIP);
    }

    public static void saveBitmap(Context context,Bitmap bitmap, String name) {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + name + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //压缩图片，如果要保存png，就用Bitmap.CompressFormat.PNG，要保存jpg就用Bitmap.CompressFormat.JPEG,质量是100%，表示不压缩
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtils.e("保存bitmp--路径找不到" + e);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("保存bitmp--错误" + e);

        }
        //通知相册更新
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bitmap, file.toString(), null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        LogUtils.e("保存bitmp--成功");



    }
}
