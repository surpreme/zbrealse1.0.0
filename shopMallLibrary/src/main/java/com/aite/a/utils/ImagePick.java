package com.aite.a.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.aiteshangcheng.a.R;

import java.io.File;


/**
 * Created by Dara on 2015/8/26 0026.
 */
public class ImagePick implements View.OnClickListener {

    private final int PICK_REQUEST = 0x1001;
    private final int TAKE_REQUEST = 0x1002;

    private static final String imgDir = Environment.getExternalStorageDirectory() + File.separator + "cloud";
    public static String imgFile = imgDir + File.separator + "tmp.jpg";

    private Activity activity;
    private Dialog dialog;

    public interface MyUri {
        void getUri(Uri uri);
    }

    public interface MyDismiss {
        void dismiss();
    }

    private MyDismiss md;

    public ImagePick(Activity activity) {
        this.activity = activity;

        dialog = new Dialog(activity, R.style.DialogStyle);
        View root = LayoutInflater.from(activity).inflate(R.layout.select_img, null);
        // 设置点击监听
        root.findViewById(R.id.take_photo).setOnClickListener(this);
        root.findViewById(R.id.album_photo).setOnClickListener(this);
        root.findViewById(R.id.cancel).setOnClickListener(this);

        dialog.setContentView(root);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (md != null) {
                    md.dismiss();
                }
            }
        });
    }

    public void setCancel(MyDismiss md) {
        this.md = md;
    }

    /**
     * 显示Dialog
     */
    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.take_photo){
            takePhoto();
        }else if(v.getId()==R.id.album_photo){
            getAlbum();
        }else if(v.getId()==R.id.cancel){
            if (md != null) {
                md.dismiss();
            }
        }
//        switch (v.getId()) {
//            case R.id.take_photo:
//                takePhoto();
//                break;
//            case R.id.album_photo:
//                getAlbum();
//                break;
//            case R.id.cancel:
//                if (md != null) {
//                    md.dismiss();
//                }
//                break;
//        }
        dialog.dismiss();
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        String sdState = Environment.getExternalStorageState();
        // 如果SD卡可读写
        if (sdState.equals(Environment.MEDIA_MOUNTED)) {
            new File(imgDir).mkdirs();
            File file = new File(imgFile);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            activity.startActivityForResult(intent, TAKE_REQUEST);
        } else {
            Toast.makeText(activity, activity.getString(R.string.order_reminder180), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从相册获取
     */
    private void getAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        activity.startActivityForResult(intent, PICK_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, MyUri uri) {
        if (resultCode != activity.RESULT_OK) {
            return;
        }
        if (requestCode == PICK_REQUEST) {
            if (data.getData() != null) {
                uri.getUri(data.getData());
            }
        } else if (requestCode == TAKE_REQUEST) {
            if (data != null) {
                Uri myUri;
                if (data.getData() != null) {
                    uri.getUri(data.getData());
                    return;
                }
                if (data.hasExtra("data")) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    try {
                        myUri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, null, null));
                        uri.getUri(myUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                        getUri(uri);
                    }
                } else {
                    // Nexus 6 返回Intent{}得不到数据
                    getUri(uri);
                }
            } else {
                getUri(uri);
            }
        }
    }

    private void getUri(MyUri uri) {
        File file = new File(imgFile);
        if (file != null && file.exists()) {
            uri.getUri(Uri.fromFile(file));
        }
    }

    /**
     * 裁剪图片，并按照给定的长宽输出
     *
     * @param activity
     * @param uri          拍照或者选择照片后获得的URI
     * @param outputWidth  输出的宽度
     * @param outputHeight 输出的高度
     * @param requestCode  请求码
     */
    public void cropImg(Activity activity, Uri uri, int outputWidth, int outputHeight, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", outputWidth);
        intent.putExtra("aspectY", outputHeight);
        intent.putExtra("outputX", outputWidth);
        intent.putExtra("outputY", outputHeight);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, requestCode);
    }
}