package com.aite.a.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.PersonalDataInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.CustomDatePicker;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 个人资料
 * Created by mayn on 2018/11/22.
 */
public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener {
    private TextView _tv_name, tv_gender, tv_brithday, tv_address, tv_save, tv_name, tv_email;
    private ImageView _iv_back, iv_avatar;
    private RelativeLayout rl_avatar, rl_gender, rl_birthday, rl_address, rl_email;
    private EditText et_truename, et_qq, et_ww;

    private String now;
    private CustomDatePicker customDatePicker1;
    private String province_id = "", city_id = "", area_id = "", sex = "0";
    private File tempFile;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private PersonalDataInfo personalDataInfo;
    private String[] sexArry = new String[3];// 性别选择
    private boolean isrefresh = false;

    private NetRun netRun;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case get_PersonalData_id://个人资料
                    if (msg.obj == null) {
                        Toast.makeText(appSingleton, getString(R.string.act_no_data), Toast.LENGTH_SHORT).show();
                    } else {
                        personalDataInfo = (PersonalDataInfo) msg.obj;
                        if (isrefresh) {
                            isrefresh = false;
                            tv_email.setText(personalDataInfo.member_email);
                        } else {
                            province_id = personalDataInfo.member_provinceid;
                            city_id = personalDataInfo.member_cityid;
                            area_id = personalDataInfo.member_areaid;
                            sex = personalDataInfo.member_sex;
                            tv_address.setText(personalDataInfo.member_areainfo);
                            tv_brithday.setText(personalDataInfo.member_birthday);
                            tv_name.setText(personalDataInfo.member_name);
                            tv_email.setText(personalDataInfo.member_email);
                            et_truename.setText(personalDataInfo.member_truename);
                            et_qq.setText(personalDataInfo.member_qq);
                            et_ww.setText(personalDataInfo.member_ww);
                            if (personalDataInfo.member_sex == null || personalDataInfo.member_sex.equals("-1") || personalDataInfo.member_sex.equals("0")) {
                                tv_gender.setText(sexArry[0]);
                            } else {
                                tv_gender.setText(sexArry[Integer.parseInt(personalDataInfo.member_sex) - 1]);
                            }
                            if (personalDataInfo.member_avatar != null && !personalDataInfo.member_avatar.equals("null")
                                    && !personalDataInfo.member_avatar.equals("")) {
                                new DownloadImageTask().execute(personalDataInfo.member_avatar);
                            }
                        }
                    }
                    break;
                case get_PersonalData_err:
                    Toast.makeText(appSingleton, getString(R.string.net_reconnect), Toast.LENGTH_SHORT).show();
                    break;
                case up_PersonalData_id://修改
                    if (msg.obj != null)
                        if (msg.obj.equals("1")) {
                            Toast.makeText(appSingleton, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(appSingleton, getString(R.string.update_fail), Toast.LENGTH_SHORT).show();
                        }
                    break;
                case up_PersonalData_err:
                    Toast.makeText(appSingleton, getString(R.string.act_net_error), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = findViewById(R.id._tv_name);
        tv_gender = findViewById(R.id.tv_gender);
        tv_brithday = findViewById(R.id.tv_brithday);
        tv_address = findViewById(R.id.tv_address);
        tv_save = findViewById(R.id.tv_save);
        _iv_back = findViewById(R.id._iv_back);
        iv_avatar = findViewById(R.id.iv_avatar);
        rl_avatar = findViewById(R.id.rl_avatar);
        rl_gender = findViewById(R.id.rl_gender);
        rl_birthday = findViewById(R.id.rl_birthday);
        rl_address = findViewById(R.id.rl_address);
        tv_email = findViewById(R.id.tv_email);
        et_truename = findViewById(R.id.et_truename);
        et_qq = findViewById(R.id.et_qq);
        et_ww = findViewById(R.id.et_ww);
        tv_name = findViewById(R.id.tv_name);
        rl_email = findViewById(R.id.rl_email);
        initView();
    }

    @Override
    protected void initView() {
        _tv_name.setText(getString(R.string.perdata));
        netRun = new NetRun(this, handler);
        rl_avatar.setOnClickListener(this);
        rl_gender.setOnClickListener(this);
        rl_birthday.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        rl_email.setOnClickListener(this);
        sexArry[0] = getString(R.string.boy);
        sexArry[1] = getString(R.string.girl);
        sexArry[2] = getString(R.string.confidential);
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_brithday.setText(time.split(" ")[0]);
//				tv_birthday.setText(time);
            }
        }, "1970-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动
        initData();
    }

    @Override
    protected void initData() {
        netRun.getPersonalData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isrefresh) {
            netRun.getPersonalData();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_email) {
            Intent intent2 = new Intent(this, EditBindingActivity.class);
            intent2.putExtra("type", "1");
            startActivity(intent2);
            isrefresh = true;
        } else if (view.getId() == R.id.rl_avatar) {
            //头像
            editAvatar();
        } else if (view.getId() == R.id.rl_gender) {
            //性别
            showSexChooseDialog();
        } else if (view.getId() == R.id.rl_birthday) {
            //生日
            customDatePicker1.show(now);
        } else if (view.getId() == R.id.rl_address) {
            //地址
            Intent intent = new Intent();
            intent.setClass(this, AddresSregionListActivity.class);
            startActivityForResult(intent, Sregion_Result);
        } else if (view.getId() == R.id.tv_save) {
            //提交
            String name = et_truename.getText().toString();
            String qq = et_qq.getText().toString();
            String ww = et_ww.getText().toString();
            String birthday = tv_brithday.getText().toString();
            String area_info = tv_address.getText().toString();
            if (TextUtils.isEmpty(name)) {
                name = "";
            }
            if (TextUtils.isEmpty(qq)) {
                qq = "";
            }
            if (TextUtils.isEmpty(ww)) {
                ww = "";
            }
            if (TextUtils.isEmpty(birthday)) {
                birthday = "";
            }
            if (TextUtils.isEmpty(area_info)) {
                area_info = "";
            }
            if (TextUtils.isEmpty(sex)) {
                sex = "";
            }
            if (TextUtils.isEmpty(province_id)) {
                province_id = "";
            }
            if (TextUtils.isEmpty(city_id)) {
                city_id = "";
            }
            if (TextUtils.isEmpty(area_id)) {
                area_id = "";
            }
            netRun.upPersonalData(name, sex, qq, ww, province_id, city_id,
                    area_id, area_info, birthday, tempFile);
        } else if (view.getId() == R.id._iv_back) {
            finish();
        }
//        switch (view.getId()) {
//            case R.id.rl_email://邮箱
//                Intent intent2 = new Intent(this, EditBindingActivity.class);
//                intent2.putExtra("type", "1");
//                startActivity(intent2);
//                isrefresh = true;
//                break;
//            case R.id.rl_avatar:
//                //头像
//                editAvatar();
//                break;
//            case R.id.rl_gender:
//                //性别
//                showSexChooseDialog();
//                break;
//            case R.id.rl_birthday:
//                //生日
//                customDatePicker1.show(now);
//                break;
//            case R.id.rl_address:
//                //地址
//                Intent intent = new Intent();
//                intent.setClass(this, AddresSregionListActivity.class);
//                startActivityForResult(intent, Sregion_Result);
//                break;
//            case R.id.tv_save:
//                //提交
//                String name = et_truename.getText().toString();
//                String qq = et_qq.getText().toString();
//                String ww = et_ww.getText().toString();
//                String birthday = tv_brithday.getText().toString();
//                String area_info = tv_address.getText().toString();
//                if (TextUtils.isEmpty(name)) {
//                    name = "";
//                }
//                if (TextUtils.isEmpty(qq)) {
//                    qq = "";
//                }
//                if (TextUtils.isEmpty(ww)) {
//                    ww = "";
//                }
//                if (TextUtils.isEmpty(birthday)) {
//                    birthday = "";
//                }
//                if (TextUtils.isEmpty(area_info)) {
//                    area_info = "";
//                }
//                if (TextUtils.isEmpty(sex)) {
//                    sex = "";
//                }
//                if (TextUtils.isEmpty(province_id)) {
//                    province_id = "";
//                }
//                if (TextUtils.isEmpty(city_id)) {
//                    city_id = "";
//                }
//                if (TextUtils.isEmpty(area_id)) {
//                    area_id = "";
//                }
//                netRun.upPersonalData(name, sex, qq, ww, province_id, city_id,
//                        area_id, area_info, birthday, tempFile);
//                break;
//            case R.id._iv_back://返回
//                finish();
//                break;
//        }
    }


    private void showSexChooseDialog() {
        int checkeditem = 0;
        String s = tv_gender.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            for (int i = 0; i < sexArry.length; i++) {
                if (s.equals(sexArry[i])) {
                    checkeditem = i;
                    break;
                }
            }
        }
        AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
        builder3.setSingleChoiceItems(sexArry, checkeditem, new DialogInterface.OnClickListener() {// 默认的选中

            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                tv_gender.setText(sexArry[which]);
                sex = (which + 1) + "";
                dialog.dismiss();
            }
        });
        builder3.show();// 让弹出框显示
    }

    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            // 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            tempFile = outputFile;
            Glide.with(PersonalInformationActivity.this).load(outputFile).into(iv_avatar);
        }
    };

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Drawable result) {
            if (result == null) {
                return;
            }
            iv_avatar.setImageDrawable(result);
        }
    }

    private Drawable loadImageFromNetwork(String imageUrl) {
        Drawable drawable = null;
        try {
            // 可以在这里通过第二个参数(文件名)来判断，是否本地有此图片
            drawable = Drawable.createFromStream(
                    new URL(imageUrl).openStream(), null);
        } catch (IOException e) {
            Log.d("skythinking", e.getMessage());
        }
        if (drawable == null) {
            Log.d("skythinking", "null drawable");
        } else {
            Log.d("skythinking", "not null drawable");
        }

        return drawable;
    }

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalInformationActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        PermissionGen.with(PersonalInformationActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(PersonalInformationActivity.this,
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

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Sregion_Result) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                String district = bundle.getString("district_name");
                String city = bundle.getString("city_name");
                String province = bundle.getString("province_name");
                city_id = bundle.getString("city_id");
                area_id = bundle.getString("area_id");
                province_id = bundle.getString("province_id");
                tv_address.setText(province + city + district);
            }
        } else {
            // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
            mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
        }
    }

    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle(getString(R.string.access_request));
        //设置正文
        builder.setMessage(getString(R.string.access_request2));

        //添加确定按钮点击事件
        builder.setPositiveButton(getString(R.string.go_to_set), new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + PersonalInformationActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }


    @Override
    public void ReGetData() {

    }
}
