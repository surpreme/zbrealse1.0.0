package com.aite.a.activity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aite.a.model.ChooseAddressInfop;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.AddressPopu;
import com.aite.a.view.CustomDatePicker;
import com.aiteshangcheng.a.R;
import com.aite.a.base.Mark;
import com.aite.a.model.PersonalDataInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.EditTextWithDel;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 个人资料
 *
 * @author xiaoyu
 */
public class PersonalDataActivity extends Activity implements
        OnClickListener, Mark {
    private ImageView iv_back, iv_right;
    private TextView tv_title, tv_birthday, tv_detailed, tv_username, tv_name, tv_gender, tv_birthdayy, tv_homee, tv_ww, tv_qq, tv_province, tv_city, tv_district;
    private EditTextWithDel et_name, et_mobile_phone, et_ww, et_qq,
            et_detailed_address;
    private CircleImageView iv_now_icon;
    private TextView et_username;
    private RadioButton rb_man, rb_woman, rb_secrecy;
    private RelativeLayout rl_tx;
    private Button btn_submit;
    private NetRun netRun;
    private File tempFile;
    private BitmapUtils bitmapUtils;
    private String province_id, city_id, area_id, sex;
    private List<ChooseAddressInfop> provincelist, citylist, arealist;
    private PersonalDataInfo personalDataInfo;
    private int get_area_num = 0;
    private int mYear, mMonth, mDay;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private List<RadioButton> radioButtons = new ArrayList<RadioButton>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case get_region_id:
                    if (get_area_num == 0) {
                        provincelist = (List<ChooseAddressInfop>) msg.obj;
                    } else if (get_area_num == 1) {
                        citylist = (List<ChooseAddressInfop>) msg.obj;
                        city_id = citylist.get(0).area_id;
                        tv_city.setText(citylist.get(0).area_name);
                    } else if (get_area_num == 2) {
                        arealist = (List<ChooseAddressInfop>) msg.obj;
                        area_id = arealist.get(0).area_id;
                        tv_district.setText(arealist.get(0).area_name);
                    }
                    break;
                case get_PersonalData_id:
                    if (msg.obj == null) {
                        CommonTools.showShortToast(PersonalDataActivity.this,
                                getString(R.string.act_no_data));
                    } else {
                        personalDataInfo = (PersonalDataInfo) msg.obj;
                        setPersonalData(personalDataInfo);

                    }
//				mdialog.dismiss();
                    break;
                case get_PersonalData_start:
//				mdialog.setMessage(getString(R.string.act_loading));
//				mdialog.show();
                    break;
                case get_PersonalData_err:
                    CommonTools.showShortToast(PersonalDataActivity.this,
                            getString(R.string.net_reconnect));
//				mdialog.dismiss();
                    break;
                case up_PersonalData_id:
                    if (msg.obj != null)
                        if (msg.obj.equals("1")) {
                            CommonTools.showShortToast(PersonalDataActivity.this,
                                    getString(R.string.update_success));
                            finish();
                        } else {
                            CommonTools.showShortToast(PersonalDataActivity.this,
                                    getString(R.string.update_fail));
                        }
//				mdialog.dismiss();
                    break;
                case up_PersonalData_start:
//				mdialog.setMessage(getString(R.string.act_loading));
//				mdialog.show();
                    break;
                case up_PersonalData_err:
                    CommonTools.showShortToast(PersonalDataActivity.this,
                            getString(R.string.act_net_error));
//				mdialog.dismiss();
                    break;
            }
        }

        ;
    };

    protected void setPersonalData(PersonalDataInfo obj) {
        bitmapUtils.display(iv_now_icon, obj.member_avatar);
        new DownloadImageTask().execute(obj.member_avatar);
        if (State.User != null) {
            et_username.setText(State.User);
        }
        if (obj.member_truename != null) {
            et_name.setText(obj.member_truename);
        }
        if (obj.member_birthday != null) {
            tv_birthday.setText(obj.member_birthday);
        }
        if (obj.member_ww != null) {
            et_ww.setText(obj.member_ww);
        }
        if (obj.member_qq != null) {
            et_qq.setText(obj.member_qq);
        }
        if (obj.member_areainfo != null) {
            et_detailed_address.setText(obj.member_areainfo);
        }
        if (obj.province_name != null) {
            tv_province.setText(obj.province_name);
        }
        if (obj.city_name != null) {
            tv_city.setText(obj.city_name);
        }
        if (obj.area_name != null) {
            tv_district.setText(obj.area_name);
        }

        if (obj.member_provinceid != null) {
            province_id = obj.member_provinceid;
            get_area_num = 1;
            netRun.getSregionList2(province_id);
        }
        if (obj.member_cityid != null) {
            city_id = obj.member_cityid;
            get_area_num = 2;
            netRun.getSregionList2(city_id);
        }
        if (obj.member_areaid != null) {
            area_id = obj.member_areaid;
        }

        if (obj.member_sex != null)
            upRadioButton(Integer.valueOf(obj.member_sex));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

        protected Drawable doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Drawable result) {
            iv_now_icon.setImageDrawable(result);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personal_data_activity);
        netRun = new NetRun(this, handler);
        bitmapUtils = new BitmapUtils(this);
        findViewById();
        initView();
        initData();
        rb_man.performClick();
    }

    protected void findViewById() {
        iv_back = (ImageView) findViewById(R.id._iv_back);
        iv_right = (ImageView) findViewById(R.id._iv_right);
        iv_right.setBackgroundResource(R.drawable.top_more);
        iv_right.setVisibility(View.GONE);
        tv_title = (TextView) findViewById(R.id._tv_name);
        tv_province = (TextView) findViewById(R.id.tv_province);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_district = (TextView) findViewById(R.id.tv_district);


        // iv_photo = (ImageView) findViewById(R.id.iv_photo);
        iv_now_icon = (CircleImageView) findViewById(R.id.now_icon);
        et_username = (TextView) findViewById(R.id.et_username);
        et_name = (EditTextWithDel) findViewById(R.id.et_name);
        et_detailed_address = (EditTextWithDel) findViewById(R.id.et_detailed_address);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        rb_secrecy = (RadioButton) findViewById(R.id.rb_secrecy);
        btn_submit = (Button) findViewById(R.id.personal_data_btn_submit);
        et_ww = (EditTextWithDel) findViewById(R.id.et_ww);
        et_qq = (EditTextWithDel) findViewById(R.id.et_qq);
        tv_title.setText(getString(R.string.personal_info));
        iv_right.setBackgroundResource(R.drawable.guanbi);

        tv_detailed = (TextView) findViewById(R.id.tv_detailed);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_birthdayy = (TextView) findViewById(R.id.tv_birthdayy);
        tv_homee = (TextView) findViewById(R.id.tv_homee);
        tv_ww = (TextView) findViewById(R.id.tv_ww);
        tv_qq = (TextView) findViewById(R.id.tv_qq);
        rl_tx = (RelativeLayout) findViewById(R.id.rl_tx);
//		tv_detailed.post(new Runnable() {
//
//			@Override
//			public void run() {
//				width = tv_detailed.getWidth();
//				runOnUiThread(new Runnable() {
//
//					@Override
//					public void run() {
//						client(tv_username);
//						client(tv_name);
//						client(tv_gender);
//						client(tv_birthdayy);
//						client(tv_homee);
//						client(tv_ww);
//						client(tv_qq);
//					}
//				});
//			}
//		});
    }

    private int width;

    private void client(TextView view) {


        LayoutParams para = view.getLayoutParams();
        para.width = width;
        view.setLayoutParams(para);
    }

    protected void initView() {
        iv_back.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        rl_tx.setOnClickListener(this);
        // iv_photo.setOnClickListener(this);
        rb_man.setOnClickListener(this);
        rb_woman.setOnClickListener(this);
        rb_secrecy.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_province.setOnClickListener(this);
        tv_city.setOnClickListener(this);
        tv_district.setOnClickListener(this);


        radioButtons.add(rb_man);
        radioButtons.add(rb_woman);
        radioButtons.add(rb_secrecy);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_birthday.setText(time.split(" ")[0]);
//				tv_birthday.setText(time);
            }
        }, "1970-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, true);
    }

    private String now;
    private CustomDatePicker customDatePicker1;

    protected void initData() {
        netRun.getPersonalData();
        netRun.getSregionList2(null);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            et_username.setText("\t" + State.User);
            et_name.setText("\t");
            tv_birthday.setText("1993-08-31");
            et_ww.setText("\t" + "");
            et_qq.setText("\t" + "");

        }
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        tv_name.setText(getString(R.string.order_reminder48));
        tv_gender.setText(getString(R.string.order_reminder49));
        tv_birthdayy.setText(getString(R.string.order_reminder50));
        tv_ww.setText(getString(R.string.order_reminder51));
        tv_qq.setText("Q\t\t  Q:");
    }


    private void submitData() {
        try {
//			mdialog.setMessage(getString(R.string.act_loading));
//			mdialog.show();
            String name = et_name.getText().toString();
            String birthday = tv_birthday.getText().toString();
            String ww = et_ww.getText().toString();
            String qq = et_qq.getText().toString();
            String area_info = et_detailed_address.getText().toString();
            if (birthday == null) {
                CommonTools.showShortToast(this,
                        getString(R.string.birthday_is_empty));
                return;
            }
            if (province_id == null) {
                CommonTools.showShortToast(this,
                        getString(R.string.province_empty));
                return;
            }
            if (city_id == null) {
                CommonTools.showShortToast(this, getString(R.string.area_empty));
                return;
            }
            if (area_id == null) {
                CommonTools.showShortToast(this,
                        getString(R.string.downtown_empty));
                return;
            }
            netRun.upPersonalData(name, sex, qq, ww, province_id, city_id,
                    area_id, area_info, birthday, tempFile);
        } catch (Exception e) {
            CommonTools.showShortToast(this, getString(R.string.errr));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rb_man) {
            upRadioButton(1);
        } else if (v.getId() == R.id.rb_woman) {
            upRadioButton(2);
        } else if (v.getId() == R.id.rb_secrecy) {
            upRadioButton(3);
        } else if (v.getId() == R.id._iv_back || v.getId() == R.id._iv_right) {
            finish();
        } else if (v.getId() == R.id.rl_tx) {
            editAvatar();
        } else if (v.getId() == R.id.personal_data_btn_submit) {
            submitData();
        } else if (v.getId() == R.id.tv_birthday) {
            customDatePicker1.show(now);
        } else if (v.getId() == R.id.tv_province) {
            //TODO 省
            get_area_num = 0;
            showAddresspopu(provincelist, tv_province.getLeft(), tv_province.getBottom());
        } else if (v.getId() == R.id.tv_city) {
            //市
            get_area_num = 1;
            showAddresspopu(citylist, tv_city.getLeft(), tv_city.getBottom());
        } else if (v.getId() == R.id.tv_district) {
            //区
            get_area_num = 2;
            showAddresspopu(arealist, tv_district.getLeft(), tv_city.getBottom());
        }


//        switch (v.getId()) {
//            case R.id.rb_man:
//                upRadioButton(1);
//                break;
//            case R.id.rb_woman:
//                upRadioButton(2);
//                break;
//            case R.id.rb_secrecy:
//                upRadioButton(3);
//                break;
//            case R.id._iv_back:
//                finish();
////			overrPre();
//                break;
//            case R.id._iv_right:
//                finish();
////			overrPre();
//                break;
//            case R.id.rl_tx:
//                // CommonTools.showShortToast(this,
//                // getString(R.string.click_picture_to_upload));
//                editAvatar();
//                break;
//            case R.id.personal_data_btn_submit:
//                submitData();
//                break;
//            case R.id.tv_birthday:
////			Message msg = new Message();
////			if (tv_birthday.equals((TextView) v)) {
////				msg.what = SHOW_DATAPICK;
////			}
////			saleHandler.sendMessage(msg);
//                customDatePicker1.show(now);
//                break;
//            //tv_province,tv_city,tv_district
//            case R.id.tv_province:
//                //TODO 省
//                get_area_num = 0;
//                showAddresspopu(provincelist, tv_province.getLeft(), tv_province.getBottom());
//                break;
//            case R.id.tv_city:
//                //市
//                get_area_num = 1;
//                showAddresspopu(citylist, tv_city.getLeft(), tv_city.getBottom());
//                break;
//            case R.id.tv_district:
//                //区
//                get_area_num = 2;
//                showAddresspopu(arealist, tv_district.getLeft(), tv_city.getBottom());
//                break;
//        }
    }

    /**
     * 弹出地址选择
     */
    private void showAddresspopu(List<ChooseAddressInfop> dta, int mar, int mar2) {
        if (dta == null) return;
        AddressPopu addressPopu = new AddressPopu(PersonalDataActivity.this, dta, mar, mar2);
        addressPopu.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#00000000")));
        addressPopu.setmenu(new AddressPopu.menu() {
            @Override
            public void onItemClick(int id) {
                if (get_area_num == 0) {
                    province_id = provincelist.get(id).area_id;
                    tv_province.setText(provincelist.get(id).area_name);
                    area_id = null;
                    arealist = null;
                    tv_district.setText(getString(R.string.order_reminder52));
                    get_area_num = 1;
                    netRun.getSregionList2(province_id);
                } else if (get_area_num == 1) {
                    city_id = citylist.get(id).area_id;
                    tv_city.setText(citylist.get(id).area_name);
                    get_area_num = 2;
                    netRun.getSregionList2(city_id);
                } else if (get_area_num == 2) {
                    area_id = arealist.get(id).area_id;
                    tv_district.setText(arealist.get(id).area_name);
                }
            }
        });
        addressPopu.showAsDropDown(tv_title, 0, 0);
    }

    private void upRadioButton(int i) {
        sex = String.valueOf(i);
        for (int j = 0; j < radioButtons.size(); j++) {
            if ((i - 1) == j) {
                radioButtons.get(j).setChecked(true);
            } else {
                radioButtons.get(j).setChecked(false);
            }
        }
    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Object Month = ((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                    : (mMonth + 1));
            Object Day = (mDay < 10) ? "0" + mDay : mDay;
            tv_birthday
                    .setText(String.valueOf(mYear) + "-" + Month + "-" + Day);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }

    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_DATAPICK = 0;


    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            // 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            tempFile = outputFile;
            Glide.with(PersonalDataActivity.this).load(outputUri).into(iv_now_icon);
        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalDataActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        PermissionGen.with(PersonalDataActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(PersonalDataActivity.this,
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
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
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
                intent.setData(Uri.parse("package:" + PersonalDataActivity.this.getPackageName()));
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

}
