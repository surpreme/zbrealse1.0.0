package com.community.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.view.MyDialog;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.utils.ZoomImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * 预览图片
 * Created by Administrator on 2017/5/22.
 */
public class ImagePreview extends BaseActivity {
    private ViewPager id_viewpager;
    private ImageView[] mImageViews;
    public static ImagePreview instance;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 456456:
                    if (msg.obj != null) {
                        Bitmap bm = (Bitmap) msg.obj;
                        ShowMainboardUpgrade(getString(R.string.find_reminder76), getString(R.string.user_save), bm);
                    }
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepreview);
        instance = this;
        findViewById();
    }

    @Override
    protected void findViewById() {
        id_viewpager = (ViewPager) findViewById(R.id.id_viewpager);

        initData();
    }

    @Override
    protected void initView() {

    }


    private ArrayList<String> photourl;
    private String str = "http://";

    @Override
    protected void initData() {
        photourl = getIntent().getStringArrayListExtra("photourl");
        int id = getIntent().getIntExtra("id", 0);
        mImageViews = new ImageView[photourl.size()];
        id_viewpager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageView imageView = new ZoomImageView(getApplicationContext());
                Glide.with(ImagePreview.this)
                        .load(photourl.get(position))
                        .into(imageView);
//                if (photourl.get(position).contains(str)||photourl.get(position).contains("https://")) {
//                    if (photourl.get(position).endsWith(".jpg")
//                            || photourl.get(position).endsWith(".jpeg") || photourl.get(position).endsWith(".png")){
//                        ImageLoader.getInstance().displayImage(photourl.get(position), imageView);
//                    }else{
//                        try {
//                            DownLoad downLoad = new DownLoad();
//                            Bitmap bitmap2 = downLoad.execute(photourl.get(position)).get();
//                            imageView.setImageBitmap(bitmap2);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else {
//                    Bitmap bitmap = BitmapFactory
//                            .decodeFile(photourl.get(position));
//                    imageView.setImageBitmap(bitmap);
//                }
                imageView.sethandler(handler);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mImageViews.length;
            }
        });
        id_viewpager.setCurrentItem(id);
    }

    private MyDialog myDialog;
    private View inflate;

    /**
     * 提示保存相册
     */
    protected void ShowMainboardUpgrade(String str, String str2, final Bitmap bm) {
        inflate = View.inflate(ImagePreview.this, R.layout.dialog_upgrade,
                null);
        TextView tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        TextView tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tv_determine = (TextView) inflate.findViewById(R.id.tv_determine);
        TextView tv_centent = (TextView) inflate.findViewById(R.id.tv_centent);
        tv_centent.setText(str);
        tv_title.setText(str2);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                if (myDialog != null) {
                    myDialog.dismiss();
                }
            }
        });

        tv_determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确定
                if (myDialog != null) {
                    myDialog.dismiss();
                    try {
                        boolean b = saveImageToGallery(ImagePreview.this, bm);
                        if (b) {
                            Toast.makeText(ImagePreview.this,getString(R.string.evaluation_tips66), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.i("----------------", "保存错误  " + e.getMessage());
                    }
                }
            }
        });
        if (myDialog == null) {
            myDialog = new MyDialog(ImagePreview.this, getw() - 50, 550,
                    inflate, R.style.loading_dialog);
            myDialog.show();
        } else {
            myDialog.show();
        }
    }

    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void ReGetData() {

    }

    /**
     * 下载图片
     *
     * @author Administrator
     */
    private class DownLoad extends AsyncTask<String, Integer, Bitmap> {
        // onPreExecute方法在execute()后执行
        @Override
        protected void onPreExecute() {
        }

        // doInBackground方法内部执行后台任务,不能在里面更新UI，否则有异常。
        @Override
        protected Bitmap doInBackground(String... params) {
            URL imageUrl = null;
            try {
                imageUrl = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("------------------", e.getMessage());
            }
            if (imageUrl != null) {
                try {
                    // 使用HttpURLConnection打开连接
                    HttpURLConnection urlConn = (HttpURLConnection) imageUrl
                            .openConnection();
                    urlConn.setDoInput(true);
                    urlConn.connect();
                    // 将得到的数据转化成InputStream
                    InputStream is = urlConn.getInputStream();
                    // 将InputStream转换成Bitmap
                    Bitmap mDownLoadBtBitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    return mDownLoadBtBitmap;
                } catch (IOException e) {
                    Log.e("------------------", e.getMessage());
                }
            }
            return null;
        }

        // onProgressUpdate方法用于更新进度信息
        @Override
        protected void onProgressUpdate(Integer... progresses) {
            System.out.println("---------------------进度" + progresses + "");
        }

        // onPostExecute用于doInBackground执行完后，更新界面UI。
        // result是doInBackground返回的结果
        @Override
        protected void onPostExecute(Bitmap result) {
        }

        // onCancelled方法用于取消Task执行，更新UI
        @Override
        protected void onCancelled() {
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    private int getw() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }
}
