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
import com.aite.a.view.CustomDatePicker;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.community.model.GettogetherClassifyInfo;
import com.community.utils.MenuPopu;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 创建活动
 * Created by mayn on 2018/9/14.
 */
public class CreateGettogetherActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_goback, iv_add;
    private TextView tv_release,tv_classify,tv_starttime,tv_endtime;
    private EditText et_title, et_price, et_desc, et_address;
    private RelativeLayout rl_classify, rl_starttime, rl_endtime, rl_address, rl_top;


    private List<GettogetherClassifyInfo> gettogetherClassifyInfo;
    private List<String> classify;
    private String class_id = "", start_time, end_time, now;
    private File thumb;
    private boolean isstarttime = true;//是否开始时间
    private CustomDatePicker customDatePicker1;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    private NetRun netRun;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case gettogether_classify_id://分类列表
                    if (msg.obj != null) {
                        gettogetherClassifyInfo = (List<GettogetherClassifyInfo>) msg.obj;
                        classify = new ArrayList<>();
                        for (int i = 0; i < gettogetherClassifyInfo.size(); i++) {
                            classify.add(gettogetherClassifyInfo.get(i).class_name);
                        }
                    }
                    break;
                case gettogether_classify_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
                case create_gettogether_id://创建活动
                    if (msg.obj!=null){
                        String str= (String) msg.obj;
                        Toast.makeText(appSingleton, str, Toast.LENGTH_SHORT).show();
                        if (str.equals(getString(R.string.find_reminder7))){
                            finish();
                        }
                    }
                    break;
                case create_gettogether_err:
                    Toast.makeText(appSingleton, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategettogether);
        findViewById();
    }

    @Override
    protected void findViewById() {
        iv_goback = (ImageView) findViewById(R.id.iv_goback);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_release = (TextView) findViewById(R.id.tv_release);
        tv_classify = (TextView) findViewById(R.id.tv_classify);
        tv_starttime = (TextView) findViewById(R.id.tv_starttime);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);
        et_title = (EditText) findViewById(R.id.et_title);
        et_price = (EditText) findViewById(R.id.et_price);
        et_address = (EditText) findViewById(R.id.et_address);
        et_desc = (EditText) findViewById(R.id.et_desc);
        rl_classify = (RelativeLayout) findViewById(R.id.rl_classify);
        rl_starttime = (RelativeLayout) findViewById(R.id.rl_starttime);
        rl_endtime = (RelativeLayout) findViewById(R.id.rl_endtime);
        rl_address = (RelativeLayout) findViewById(R.id.rl_address);
        rl_top = (RelativeLayout) findViewById(R.id.rl_top);
        initView();
    }

    @Override
    protected void initView() {
        netRun = new NetRun(this, handler);
        iv_goback.setOnClickListener(this);
        tv_release.setOnClickListener(this);
        rl_classify.setOnClickListener(this);
        rl_starttime.setOnClickListener(this);
        rl_endtime.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        customDatePicker1 = new CustomDatePicker(CreateGettogetherActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                if (isstarttime) {
                    tv_starttime.setText(time);
                    start_time = time;
                } else {
                    tv_endtime.setText(time);
                    end_time = time;
                }
//            time.split(" ")[0]
            }
        }, now, "2080-01-01 00:00");// 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(true); // 显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        initData();
    }


    @Override
    protected void initData() {
        netRun.GettogetherClassify();
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_goback:
//                finish();
//                break;
//            case R.id.tv_release://提交
//                String title = et_title.getText().toString();
//                String price = et_price.getText().toString();
//                String address = et_address.getText().toString();
//                String desc = et_desc.getText().toString();
//                if (TextUtils.isEmpty(title)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder8), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(price)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder9), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(address)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder10), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(start_time)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder11), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(end_time)) {
//                    Toast.makeText(appSingleton, getString(R.string.find_reminder12), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(desc)) {
//                    desc = "";
//                }
//                netRun.CreateGettogether(title, class_id, start_time, end_time, price, address, desc, thumb);
//                break;
//            case R.id.rl_classify://分类
//                showpopu(classify);
//                break;
//            case R.id.rl_starttime://开始时间
//                isstarttime = true;
//
//                customDatePicker1.show(now);
//                break;
//            case R.id.rl_endtime://结束时间
//                isstarttime = false;
//                customDatePicker1.show(now);
//                break;
//            case R.id.iv_add://选图片
//                editAvatar();
//                break;
//        }

        if(v.getId()==R.id.iv_goback){
            finish();
        }else if(v.getId()==R.id.tv_release){
            String title = et_title.getText().toString();
            String price = et_price.getText().toString();
            String address = et_address.getText().toString();
            String desc = et_desc.getText().toString();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder8), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(price)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder9), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(address)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder10), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(start_time)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder11), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(end_time)) {
                Toast.makeText(appSingleton, getString(R.string.find_reminder12), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(desc)) {
                desc = "";
            }
            netRun.CreateGettogether(title, class_id, start_time, end_time, price, address, desc, thumb);
        }else if(v.getId()==R.id.rl_classify){
            //分类
            showpopu(classify);
        }else if(v.getId()==R.id.rl_starttime){
            //开始时间
            isstarttime = true;

            customDatePicker1.show(now);
        }else if(v.getId()==R.id.rl_endtime){
            //结束时间
            isstarttime = false;
            customDatePicker1.show(now);
        }else if(v.getId()==R.id.iv_add){
            //选图片
            editAvatar();
        }

    }

    private void showpopu(List<String> data) {
        MenuPopu menuPopu = new MenuPopu(CreateGettogetherActivity.this, data);
        menuPopu.setmenu(new MenuPopu.menu() {
            @Override
            public void onItemClick(int id) {
                class_id = gettogetherClassifyInfo.get(id).class_id;
                tv_classify.setText(gettogetherClassifyInfo.get(id).class_name);
            }
        });
        menuPopu.showAsDropDown(rl_classify);
    }


    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            thumb = outputFile;
            Glide.with(CreateGettogetherActivity.this).load(outputUri).into(iv_add);
        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CreateGettogetherActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        PermissionGen.with(CreateGettogetherActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(CreateGettogetherActivity.this,
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
