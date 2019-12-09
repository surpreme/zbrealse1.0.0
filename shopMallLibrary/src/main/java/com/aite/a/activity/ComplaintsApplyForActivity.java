package com.aite.a.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ComplaintsInfo;
import com.aite.a.model.ComplaintsInfo.subject_list;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.MyListView;
import com.aite.a.view.MyShiGuangZhou;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 投诉申请
 *
 * @author Administrator
 */
public class ComplaintsApplyForActivity extends BaseActivity implements
        OnClickListener {
    private TextView _tv_name, tv_progress1, tv_progress2, tv_progress3,
            tv_progress4, tv_progress5, tv_img1, tv_img2, tv_img3, tv_imgname1,
            tv_imgname2, tv_imgname3, tv_submit, tv_cancel;
    private ImageView _iv_back;
    private MyShiGuangZhou msgz_sgz;
    private CheckBox cb_theme1, cb_theme2;
    private EditText ed_content;
    private NetRun netRun;
    private BitmapUtils bitmapUtils;
    private int imgnum = 0;
    private ComplaintsInfo complaintsInfo;
    private MyListView mlv_tousu;
    private Madapter madapter;
    private int xz = 0;
    private String img1 = "";
    private String img2 = "";
    private String img3 = "";

    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case complaints_id:
                    if (msg.obj != null) {
                        try {
                            complaintsInfo = (ComplaintsInfo) msg.obj;
                            if (complaintsInfo.subject_list.size() != 0) {
                                complaintsInfo.subject_list.get(0).isxz = true;
                                madapter = new Madapter(complaintsInfo.subject_list);
                                mlv_tousu.setAdapter(madapter);
                            }
                        } catch (Exception e) {
                            Toast.makeText(ComplaintsApplyForActivity.this,
                                    getString(R.string.complaint_prompt15), Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case complaints_err:
                    Toast.makeText(ComplaintsApplyForActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case complaints_img_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        switch (imgnum) {
                            case 0:
                                img1 = str;
                                break;
                            case 1:
                                img2 = str;
                                break;
                            case 2:
                                img3 = str;
                                break;
                        }
                    }
                    break;
                case complaints_img_err:
                    Toast.makeText(ComplaintsApplyForActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
                case complaints_submit_id:
                    if (msg.obj != null) {
                        String str = (String) msg.obj;
                        Toast.makeText(ComplaintsApplyForActivity.this, str,
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                case complaints_submit_err:
                    Toast.makeText(ComplaintsApplyForActivity.this,
                            getString(R.string.systembusy), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        }

        ;
    };
    private String order_id, goods_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_applyfor);
        findViewById();
    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_progress1 = (TextView) findViewById(R.id.tv_progress1);
        tv_progress2 = (TextView) findViewById(R.id.tv_progress2);
        tv_progress3 = (TextView) findViewById(R.id.tv_progress3);
        tv_progress4 = (TextView) findViewById(R.id.tv_progress4);
        tv_progress5 = (TextView) findViewById(R.id.tv_progress5);
        tv_img1 = (TextView) findViewById(R.id.tv_img1);
        tv_img2 = (TextView) findViewById(R.id.tv_img2);
        tv_img3 = (TextView) findViewById(R.id.tv_img3);
        tv_imgname1 = (TextView) findViewById(R.id.tv_imgname1);
        tv_imgname2 = (TextView) findViewById(R.id.tv_imgname2);
        tv_imgname3 = (TextView) findViewById(R.id.tv_imgname3);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        _iv_back = (ImageView) findViewById(R.id._iv_back);
        msgz_sgz = (MyShiGuangZhou) findViewById(R.id.msgz_sgz);
        cb_theme1 = (CheckBox) findViewById(R.id.cb_theme1);
        cb_theme2 = (CheckBox) findViewById(R.id.cb_theme2);
        ed_content = (EditText) findViewById(R.id.ed_content);
        mlv_tousu = (MyListView) findViewById(R.id.mlv_tousu);
        _iv_back.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_img1.setOnClickListener(this);
        tv_img2.setOnClickListener(this);
        tv_img3.setOnClickListener(this);
        cb_theme1.setOnClickListener(this);
        cb_theme2.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        _tv_name.setText(getString(R.string.complaint_prompt16));
        bitmapUtils = new BitmapUtils(this);
        netRun = new NetRun(this, handler);
        order_id = getIntent().getStringExtra("order_id");
        goods_id = getIntent().getStringExtra("goods_id");
        initData();
    }

    @Override
    protected void initData() {
        netRun.Complaint(order_id, goods_id);
    }

    @Override
    public void ReGetData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id._iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_cancel) {
            finish();
        } else if (v.getId() == R.id.tv_submit) {
            // 提交
            String input_complain_content = ed_content.getText().toString();
            if (TextUtils.isEmpty(input_complain_content)) {
                Toast.makeText(ComplaintsApplyForActivity.this, getString(R.string.complaint_prompt17),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (img1.equals("") && img2.equals("") && img3.equals("")) {
                Toast.makeText(ComplaintsApplyForActivity.this, getString(R.string.complaint_prompt18),
                        Toast.LENGTH_SHORT).show();
                return;
            }
            netRun.ComplaintsSubmit(order_id, goods_id, madapter.getzt(),
                    input_complain_content, img1, img2, img3);
        } else if (v.getId() == R.id.tv_img1) {
            // 图1
            imgnum = 0;
            editAvatar();
        } else if (v.getId() == R.id.tv_img2) {
            // 图2
            imgnum = 1;
            editAvatar();
        } else if (v.getId() == R.id.tv_img3) {
            // 图3
            imgnum = 2;
            editAvatar();
        } else if (v.getId() == R.id.cb_theme1) {
            if (cb_theme2.isChecked()) {
                cb_theme2.setChecked(false);
            }
        } else if (v.getId() == R.id.cb_theme2) {
            if (cb_theme1.isChecked()) {
                cb_theme1.setChecked(false);
            }
        }
//		switch (v.getId()) {
//			case R.id._iv_back:
//				finish();
//				break;
//			case R.id.tv_cancel:
//				finish();
//				break;
//			case R.id.tv_submit:
//				// 提交
//				String input_complain_content = ed_content.getText().toString();
//				if (TextUtils.isEmpty(input_complain_content)) {
//					Toast.makeText(ComplaintsApplyForActivity.this, getString(R.string.complaint_prompt17),
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				if (img1.equals("") && img2.equals("") && img3.equals("")) {
//					Toast.makeText(ComplaintsApplyForActivity.this, getString(R.string.complaint_prompt18),
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				netRun.ComplaintsSubmit(order_id, goods_id, madapter.getzt(),
//						input_complain_content, img1, img2, img3);
//				break;
//			case R.id.tv_img1:
//				// 图1
//				imgnum = 0;
//				editAvatar();
//				break;
//			case R.id.tv_img2:
//				// 图2
//				imgnum = 1;
//				editAvatar();
//				break;
//			case R.id.tv_img3:
//				// 图3
//				imgnum = 2;
//				editAvatar();
//				break;
//			case R.id.cb_theme1:
//				if (cb_theme2.isChecked()) {
//					cb_theme2.setChecked(false);
//				}
//				break;
//			case R.id.cb_theme2:
//				if (cb_theme1.isChecked()) {
//					cb_theme1.setChecked(false);
//				}
//				break;
//		}
    }

    /**
     * 投诉选项
     *
     * @author Administrator
     */
    private class Madapter extends BaseAdapter {
        List<subject_list> subject_list;

        public Madapter(List<subject_list> subject_list) {
            this.subject_list = subject_list;
        }

        public String getzt() {
            String zt = subject_list.get(xz).complain_subject_id + "," + subject_list.get(xz).complain_subject_content;
            return zt;
        }

        @Override
        public int getCount() {
            return subject_list.size();
        }

        @Override
        public Object getItem(int position) {
            return subject_list == null ? null : subject_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(ComplaintsApplyForActivity.this,
                        R.layout.complaints_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.cb_theme1
                    .setText(subject_list.get(position).complain_subject_content);
            holder.tv_miaoshu
                    .setText(subject_list.get(position).complain_subject_desc);
            if (position == xz) {
                holder.cb_theme1.setChecked(true);
            } else {
                holder.cb_theme1.setChecked(false);
            }
            holder.cb_theme1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    xz = position;
                    notifyDataSetChanged();
                }
            });
            // holder.cb_theme1.setOnCheckedChangeListener(new
            // OnCheckedChangeListener() {
            //
            // @Override
            // public void onCheckedChanged(CompoundButton buttonView, boolean
            // isChecked) {
            // xz=position;
            // notifyDataSetChanged();
            // }
            // });
            return convertView;
        }

        class ViewHolder {
            CheckBox cb_theme1;
            TextView tv_miaoshu;

            public ViewHolder(View convertView) {
                cb_theme1 = (CheckBox) convertView.findViewById(R.id.cb_theme1);
                tv_miaoshu = (TextView) convertView
                        .findViewById(R.id.tv_miaoshu);
                convertView.setTag(this);
            }
        }
    }

    private File[] filee = new File[3];


    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            // 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            tempFile = outputFile;
            filee[imgnum] = tempFile;
            switch (imgnum) {
                case 0:
                    netRun.complaintsimg("complain_pic1", tempFile);
                    tv_imgname1.setText(tempFile.getName());
                    break;
                case 1:
                    netRun.complaintsimg("complain_pic2", tempFile);
                    tv_imgname2.setText(tempFile.getName());
                    break;
                case 2:
                    netRun.complaintsimg("complain_pic3", tempFile);
                    tv_imgname3.setText(tempFile.getName());
                    break;
            }

        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ComplaintsApplyForActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        PermissionGen.with(ComplaintsApplyForActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(ComplaintsApplyForActivity.this,
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
                intent.setData(Uri.parse("package:" + ComplaintsApplyForActivity.this.getPackageName()));
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
