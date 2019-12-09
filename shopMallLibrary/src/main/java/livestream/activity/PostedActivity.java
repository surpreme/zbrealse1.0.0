package livestream.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 发布动态
 * Created by Administrator on 2017/9/14.
 */
public class PostedActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_return, iv_close;
    private TextView tv_save, tv_address;
    private EditText et_con;
    private LinearLayout ll_address;
    private MyGridView gv_photo;
    private int width=0;
    private List<File> photolist;
    private PhotoAdapter photoAdapter;

    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;
    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return sdf.format(date) + ".png";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zbposted);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_return = (ImageView) findViewById(R.id.iv_return);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_address = (TextView) findViewById(R.id.tv_address);
        et_con = (EditText) findViewById(R.id.et_con);
        gv_photo = (MyGridView) findViewById(R.id.gv_photo);
        ll_address= (LinearLayout) findViewById(R.id.ll_address);
        initView();
    }

    @Override
    protected void initView() {
        iv_return.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        photolist=new ArrayList<>();
        photolist.add(null);
        photoAdapter=new PhotoAdapter(photolist);
        gv_photo.setAdapter(photoAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_return) {
            finish();
        } else if (id == R.id.tv_save) {//发布
        } else if (id == R.id.iv_close) {//关闭地址
            ll_address.setVisibility(View.GONE);
        }
    }

    private int photoid=0;

    /**
     * 图片
     */
    private class PhotoAdapter extends BaseAdapter {
        List<File> photolist;
        public PhotoAdapter(List<File> photolist) {
            this.photolist=photolist;
        }

        /**
         * 修改或添加
         * @param photoid 等于最后一个为添加反之修改
         * @param f
         */
        private void setItem(int photoid,File f){
            if (photoid!=(getCount()-1)){
                photolist.set(photoid,f);
            }else{
                photolist.remove(getCount()-1);
                photolist.add(f);
                if (getCount()<9){
                    photolist.add(null);
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return photolist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(PostedActivity.this, R.layout.item_zbphoto, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (photolist.get(position)==null){
                holder.iv_photo.setImageResource(R.drawable.zb_addphoto);
            }else{
                Drawable path = Drawable.createFromPath(photolist.get(position).toString());
                holder.iv_photo.setImageDrawable(path);
            }
            holder.iv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoid=position;
                    editAvatar();
                }
            });
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.iv_photo.getLayoutParams();
            layoutParams.height=(int)(width/4.2);
            layoutParams.width=(int)(width/4.2);
            holder.iv_photo.setLayoutParams(layoutParams);
            return convertView;
        }

        class ViewHolder {
            ImageView iv_photo;

            public ViewHolder(View convertView) {
                iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
                convertView.setTag(this);
            }
        }
    }

    @Override
    public void ReGetData() {

    }

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getI18n(R.string.select_picture_source));
        String[] items = new String[] { getI18n(R.string.media_lib),
                getI18n(R.string.take_picture) };
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0:
                        // 调用相册
                        startPick(dialog);
                        break;
                    case 1:
                        // 调用拍照
                        startCamera(dialog);
                        break;
                }
            }
        });
        dialog.setNegativeButton(getI18n(R.string.cancel), null);
        dialog.create().show();
    }

    // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, PHOTO_CARMERA);
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_PICK);
    }

    // 调用系统裁剪
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }

    private Bitmap bmp = null;

    // 将裁剪后的图片显示在ImageView上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (null != bundle) {
//            Drawable path = Drawable.createFromPath(tempFile.getAbsolutePath());
            photoAdapter.setItem(photoid,tempFile);
            Log.i("---------------------", "图片路径  "+tempFile.getAbsolutePath());
            tempFile=new File(Environment.getExternalStorageDirectory(),
                    getPhotoFileName());
        }
    }

    // 把裁剪后的图片保存到sdcard上
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CARMERA:
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                break;
            case PHOTO_PICK:
                if (null != data) {
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        bmp = bundle.getParcelable("data");
                        saveCropPic(bmp);
                    }
                    setPicToView(data);
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
