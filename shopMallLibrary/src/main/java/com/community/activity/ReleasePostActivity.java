package com.community.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.ForumClassifyInfo;
import com.community.utils.MenuPopu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 发布帖子
 * Created by mayn on 2018/9/20.
 */
public class ReleasePostActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_img;
    private RelativeLayout rl_classify;
    private TextView tv_classify, tv_release;
    private EditText et_input, et_title;

    private File thumb;
    private String article_class = "";
    private List<String> classify;
    private List<ForumClassifyInfo> forumClassifyInfo;
    private NetRun netRun;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case forum_classify_id://帖子分类
                    if (msg.obj != null) {
                        forumClassifyInfo = (List<ForumClassifyInfo>) msg.obj;
                        if (forumClassifyInfo != null && forumClassifyInfo.size() != 0) {
                            article_class = forumClassifyInfo.get(0).class_id;
                            tv_classify.setText(forumClassifyInfo.get(0).class_name);
                            for (int i = 0; i < forumClassifyInfo.size(); i++) {
                                classify.add(forumClassifyInfo.get(i).class_name);
                            }
                        }
                    }
                    break;
                case forum_classify_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case release_post_id://发布帖子
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        if (str.contains(getString(R.string.succeed))){
                            finish();
                        }
                    }
                    break;
                case release_post_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_releasepost);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        rl_classify = (RelativeLayout) findViewById(R.id.rl_classify);
        tv_classify = (TextView) findViewById(R.id.tv_classify);
        tv_release = (TextView) findViewById(R.id.tv_release);
        et_input = (EditText) findViewById(R.id.et_input);
        et_title = (EditText) findViewById(R.id.et_title);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_goback.setOnClickListener(this);
        iv_img.setOnClickListener(this);
        rl_classify.setOnClickListener(this);
        tv_release.setOnClickListener(this);
        classify = new ArrayList<>();
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        initData();
    }

    @Override
    protected void initData() {
        netRun.ForumClassify();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.iv_goback){
            finish();
        }else if(v.getId()== R.id.iv_img){
            //图片
            editAvatar();
        }else if(v.getId()==R.id.rl_classify){
            //分类
            if (classify.size() != 0) {
                showPopu(classify);
            }
        }else if(v.getId()==R.id.tv_release){
            //发布
            String title = et_input.getText().toString();
            String desc = et_title.getText().toString();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder8), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(desc)) {
                Toast.makeText(appSingleton, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
                return;
            }
            if (thumb == null || !thumb.exists()) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder110), Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.ReleasePost(title, article_class, thumb, desc);
        }

//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.iv_img://图片
//                editAvatar();
//                break;
//            case R.id.rl_classify://分类
//                if (classify.size() != 0) {
//                    showPopu(classify);
//                }
//                break;
//            case R.id.tv_release://发布
//                String title = et_input.getText().toString();
//                String desc = et_title.getText().toString();
//                if (TextUtils.isEmpty(title)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder8), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(desc)) {
//                    Toast.makeText(appSingleton, getString(R.string.please_enter_content), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (thumb == null || !thumb.exists()) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder110), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                netRun.ReleasePost(title, article_class, thumb, desc);
//                break;
//        }
    }

    public void showPopu(List<String> data) {
        MenuPopu menuPopu = new MenuPopu(ReleasePostActivity.this, data);
        menuPopu.setmenu(new MenuPopu.menu() {
            @Override
            public void onItemClick(int id) {
                article_class = forumClassifyInfo.get(id).class_id;
                tv_classify.setText(forumClassifyInfo.get(id).class_name);
            }
        });
        menuPopu.showAsDropDown(rl_classify);
    }

    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            thumb = outputFile;
            Glide.with(ReleasePostActivity.this).load(outputUri).into(iv_img);
        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReleasePostActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        PermissionGen.with(ReleasePostActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(ReleasePostActivity.this,
                LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE});
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
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }

    @Override
    public void ReGetData() {

    }
}
