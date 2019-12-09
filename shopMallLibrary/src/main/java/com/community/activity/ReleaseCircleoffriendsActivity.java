package com.community.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.community.adapter.PhotoAdapter;
import com.community.model.PhotoInfo;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 发朋友圈
 * Created by mayn on 2018/9/20.
 */
public class ReleaseCircleoffriendsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback;
    private EditText et_con;
    private MyGridView gv_photo;
    private TextView tv_issue, tv_address, tv_type;
    private PhotoAdapter photoAdapter;
    private RelativeLayout rl_address, rl_type;
    private String type = "0";
    private int ACTIVITY_REQUEST_SELECT_PHOTO = 109;

    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private int imagerid;
    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PICKIMAGE://选择图片
                    if (msg.obj != null) {
                        imagerid = (int) msg.obj;
                        editAvatar();
                    }
                    break;
                case release_circleoffriends_id://发布
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))) {
                            finish();
                        }
                    }
                    break;
                case release_circleoffriends_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasecircleoffriends);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        et_con = (EditText) findViewById(R.id.et_con);
        gv_photo = (MyGridView) findViewById(R.id.gv_photo);
        tv_issue = (TextView) findViewById(R.id.tv_issue);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_type = (TextView) findViewById(R.id.tv_type);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        rl_type = (RelativeLayout) findViewById(R.id.rl_type);
        initView();
    }

    @Override
    protected void initView() {
        iv_goback.setOnClickListener(this);
        tv_issue.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_type.setOnClickListener(this);
        netRun = new NetRun(this, handler);

//        Album.initialize(AlbumConfig.newBuilder(this)
//                .setAlbumLoader(new MediaLoader()).build());

        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        List<PhotoInfo> data = new ArrayList<>();
        data.add(new PhotoInfo(false, null));
        photoAdapter = new PhotoAdapter(this, data, handler, getW());
        gv_photo.setAdapter(photoAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_issue:
//                String s = et_con.getText().toString();
//                String s1 = tv_address.getText().toString();
//                if (TextUtils.isEmpty(s)) {
//                    Toast.makeText(appSingleton, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (s1.equals(getString(R.string.find_reminder98))) {
//                    s1 = "";
//                }
//                List<File> data = photoAdapter.getData();
//                netRun.ReleaseCircleoffriends(s, data, s1, type);
//                break;
//            case R.id.rl_address://地址
//                Intent intent = new Intent(appSingleton, SelectAddressActivity.class);
//                intent.putExtra("chooseaddress", tv_address.getText().toString());
//                startActivityForResult(intent, 107);
//                break;
//            case R.id.rl_type://谁可以看
//                Intent intent2 = new Intent(appSingleton, ReleaseCircleoffriendsTypeActivity.class);
//                startActivityForResult(intent2, 108);
//                break;
//        }
        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.tv_issue){
            String s = et_con.getText().toString();
            String s1 = tv_address.getText().toString();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(appSingleton, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
                return;
            }
            if (s1.equals(getString(R.string.find_reminder98))) {
                s1 = "";
            }
            List<File> data = photoAdapter.getData();
            netRun.ReleaseCircleoffriends(s, data, s1, type);
        }else if(v.getId()==R.id.rl_address){
            //地址
            Intent intent = new Intent(appSingleton, SelectAddressActivity.class);
            intent.putExtra("chooseaddress", tv_address.getText().toString());
            startActivityForResult(intent, 107);
        }else if(v.getId()==R.id.rl_type){
            //谁可以看
            Intent intent2 = new Intent(appSingleton, ReleaseCircleoffriendsTypeActivity.class);
            startActivityForResult(intent2, 108);
        }
    }

    /**
     * 图片高度
     *
     * @return
     */
    private int getW() {
        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        float scale = getResources().getDisplayMetrics().density;
        return (int) (width - (20 * scale + 0.5f)) / 3;
    }

    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
//            if (imagerid==photoAdapter.getCount()-1){
            List<File> data = new ArrayList<>();
            data.add(outputFile);
            photoAdapter.add(imagerid, data);
//            }
        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReleaseCircleoffriendsActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setTitle(getString(R.string.complaint_prompt19));
        String[] items = new String[]{getString(R.string.complaint_prompt20),
                getString(R.string.complaint_prompt21)};

        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.create().show();
    }

    // 调用系统相机
    protected void startCamera(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.with(ReleaseCircleoffriendsActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
//        PermissionGen.needPermission(ReleaseCircleoffriendsActivity.this,
//                LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
//                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE});
        Album.image(this)
                .multipleChoice()//多选
                .camera(false)//相机
                .columnCount(3)//列数
                .selectCount(10 - photoAdapter.getCount())//最大选择数量
//                .checkedList(mAlbumFiles)//默认选中
//                .filterSize() // 过滤文件大小.
//                .filterMimeType() // 过滤文件格式.
                .afterFilterVisibility(false) // 显示过滤文件.
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        List<File> data = new ArrayList<>();
                        for (AlbumFile s : result) {
                            data.add(new File(s.getPath()));
                        }
                        photoAdapter.add(imagerid, data);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 107) {
            if (data != null) {
                String address = data.getStringExtra("address");
                if (address.equals(getString(R.string.find_reminder99))) {
                    tv_address.setText(getString(R.string.find_reminder98));
                } else {
                    tv_address.setText(address);
                }
            }
        } else if (requestCode == 108) {
            if (data != null) {
                type = data.getStringExtra("type");
                tv_type.setText(type.equals("0") ? getString(R.string.find_reminder100) : type.equals("1") ?
                        getString(R.string.find_reminder101) : type.equals("1") ? getString(R.string.find_reminder102) : "");
            }
        } else {
            // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
            mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void ReGetData() {

    }

}
