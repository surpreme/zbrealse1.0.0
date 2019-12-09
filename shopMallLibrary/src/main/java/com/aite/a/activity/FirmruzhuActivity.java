package com.aite.a.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.adapter.ListItemAdapder;
import com.aite.a.base.BaseActivity;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.utils.Ruzhupopu;
import com.aite.a.utils.Ruzhupopu.address;
import com.aite.a.utils.Ruzhupopu.agency;
import com.aite.a.utils.Ruzhupopu.businessclass;
import com.aite.a.utils.Ruzhupopu.shopclass;
import com.aite.a.utils.Ruzhupopu.shoplevel;
import com.aite.a.view.MyListView;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class FirmruzhuActivity extends BaseActivity implements OnClickListener {

    private EditText et_shopname, et_detailedaddress, et_contact,
            et_contactphone, et_email, et_realname, et_idnumber, et_zfbname,
            et_zfbuser, et_firmzfbuser, et_firmzhzfbuser, et_firmzhzfbuseradd,
            et_shopuser, et_firmshopuser;
    private TextView tv_addressselection, tv_areaagency, tv_shoplevel,
            tv_shopclass, tv_businessproject, tv_agreement, _tv_name;
    private ImageView iv_scanning, iv_firmscanning, _iv_back;
    private MyListView ll_leimu;
    private Button bt_startregistered;
    private int iv;
    private Ruzhupopu ruzhupopu;
    private MyAdapter myadapter;
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firmruzhu);
        findViewById();
    }

    @Override
    public void ReGetData() {

    }

    @Override
    protected void findViewById() {
        _tv_name = (TextView) findViewById(R.id._tv_name);
        tv_addressselection = (TextView) findViewById(R.id.tv_addressselection);
        tv_areaagency = (TextView) findViewById(R.id.tv_areaagency);
        tv_shoplevel = (TextView) findViewById(R.id.tv_shoplevel);
        tv_shopclass = (TextView) findViewById(R.id.tv_shopclass);
        tv_businessproject = (TextView) findViewById(R.id.tv_businessproject);
        tv_agreement = (TextView) findViewById(R.id.tv_agreement);

        et_shopname = (EditText) findViewById(R.id.et_shopname);
        et_detailedaddress = (EditText) findViewById(R.id.et_detailedaddress);
        et_contact = (EditText) findViewById(R.id.et_contact);
        et_contactphone = (EditText) findViewById(R.id.et_contactphone);
        et_email = (EditText) findViewById(R.id.et_email);
        et_realname = (EditText) findViewById(R.id.et_realname);
        et_idnumber = (EditText) findViewById(R.id.et_idnumber);
        et_zfbname = (EditText) findViewById(R.id.et_zfbname);
        et_zfbuser = (EditText) findViewById(R.id.et_zfbuser);
        et_firmzfbuser = (EditText) findViewById(R.id.et_firmzfbuser);
        et_firmzhzfbuser = (EditText) findViewById(R.id.et_firmzhzfbuser);
        et_firmzhzfbuseradd = (EditText) findViewById(R.id.et_firmzhzfbuseradd);
        et_shopuser = (EditText) findViewById(R.id.et_shopuser);
        et_firmshopuser = (EditText) findViewById(R.id.et_firmshopuser);

        iv_scanning = (ImageView) findViewById(R.id.iv_scanning);
        iv_firmscanning = (ImageView) findViewById(R.id.iv_firmscanning);
        _iv_back = (ImageView) findViewById(R.id.iv_firmscanning);

        ll_leimu = (MyListView) findViewById(R.id.ll_leimu);

        bt_startregistered = (Button) findViewById(R.id.bt_startregistered);
        tv_addressselection.setOnClickListener(this);
        tv_areaagency.setOnClickListener(this);
        tv_shoplevel.setOnClickListener(this);
        tv_shopclass.setOnClickListener(this);
        tv_businessproject.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
        iv_scanning.setOnClickListener(this);
        iv_firmscanning.setOnClickListener(this);
        bt_startregistered.setOnClickListener(this);
        _iv_back.setOnClickListener(this);
        initView();
    }

    @Override
    protected void initView() {
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
        Intent intent2 = getIntent();
        String titel = intent2.getStringExtra("titel");
        _tv_name.setText(titel);
        myadapter = new MyAdapter();
        Map<String, String> map = new HashMap<String, String>();
        map.put("level1class", getString(R.string.join_merchant1));
        map.put("level2class", getString(R.string.join_merchant2));
        map.put("level3class", getString(R.string.join_merchant3));
        map.put("level4class", getString(R.string.operation));
        list.add(map);
        myadapter.addList(list);
        ll_leimu.setAdapter(myadapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_addressselection:
//			showPopup(0);
//			break;
//		case R.id.tv_areaagency:
//			showPopup(1);
//			break;
//		case R.id.tv_shoplevel:
//			showPopup(2);
//			break;
//		case R.id.tv_shopclass:
//			showPopup(3);
//			break;
//		case R.id.tv_businessproject:
//			showPopup(4);
//			break;
//		case R.id.tv_agreement:
//
//			break;
//		case R.id.iv_scanning:
//			iv = 0;
//			editAvatar();
//			break;
//		case R.id.iv_firmscanning:
//			iv = 1;
//			editAvatar();
//			break;
//		case R.id.bt_startregistered:
//
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		}
        if (v.getId() == R.id.tv_addressselection) {
            showPopup(0);
        } else if (v.getId() == R.id.tv_areaagency) {
            showPopup(1);
        } else if (v.getId() == R.id.tv_shoplevel) {
            showPopup(2);
        } else if (v.getId() == R.id.tv_shopclass) {
            showPopup(3);
        } else if (v.getId() == R.id.tv_businessproject) {
            showPopup(4);
        } else if (v.getId() == R.id.iv_scanning) {
            iv = 0;
            editAvatar();
        } else if (v.getId() == R.id.iv_firmscanning) {
            iv = 1;
            editAvatar();
        } else if (v.getId() == R.id._iv_back) {
            finish();
        }
    }

    private void showPopup(int pos) {
        ruzhupopu = new Ruzhupopu(this, pos);
        ruzhupopu.showAtLocation(bt_startregistered, Gravity.BOTTOM, 0, 0);// 设置显示位置
        // 地址回调
        ruzhupopu.setaddress(new address() {

            @Override
            public void onClick(String level1menu, String level2menu,
                                String level3menu) {
                if (level1menu != null && !level1menu.equals("")) {
                    tv_addressselection.setText((level1menu == null ? ""
                            : level1menu)
                            + (level2menu == null ? "" : level2menu)
                            + (level3menu == null ? "" : level3menu));
                } else {
                    tv_addressselection.setText(getString(R.string.join_merchant4));
                }
            }
        });
        // 区域代理回调
        ruzhupopu.setagency(new agency() {

            @Override
            public void onClickl(String agency) {
                tv_areaagency.setText(agency == null ? getString(R.string.release_goods143) : agency);
            }
        });
        // 店铺等级回调
        ruzhupopu.setshoplevel(new shoplevel() {

            @Override
            public void onClickls(String level) {
                tv_shoplevel.setText(level == null ? getString(R.string.release_goods143) : level);
            }
        });
        // 店铺分类回调
        ruzhupopu.setshopclass(new shopclass() {

            @Override
            public void onClicklsl(String fclass) {
                tv_shopclass.setText(fclass == null ? getString(R.string.release_goods143) : fclass);
            }
        });
        // 经营类目
        ruzhupopu.setbusinessclass(new businessclass() {

            @Override
            public void onbusiness(Map<String, String> item) {
                myadapter.addItem(item);
            }
        });

    }

    private class MyAdapter extends ListItemAdapder<Map<String, String>> {

        public MyAdapter() {
            super(FirmruzhuActivity.this);
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(FirmruzhuActivity.this,
                        R.layout.ruzhu_item, null);
                new ViewHoldr(convertView);
            }
            ViewHoldr holder = (ViewHoldr) convertView.getTag();
            Map<String, String> item = getItem(position);
            holder.tv_level1.setText(item.get("level1class"));
            holder.tv_level2.setText(item.get("level2class"));
            holder.tv_level3.setText(item.get("level3class"));
            holder.tv_level4.setText(item.get("level4class"));
            holder.tv_level4.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (position != 0) {
                        myadapter.deleteItem(position);
                    }
                }
            });
            return convertView;
        }

        class ViewHoldr {
            TextView tv_level1, tv_level2, tv_level3, tv_level4;

            public ViewHoldr(View convertView) {
                tv_level1 = (TextView) convertView.findViewById(R.id.tv_level1);
                tv_level2 = (TextView) convertView.findViewById(R.id.tv_level2);
                tv_level3 = (TextView) convertView.findViewById(R.id.tv_level3);
                tv_level4 = (TextView) convertView.findViewById(R.id.tv_level4);
                convertView.setTag(this);
            }
        }
    }


    private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
        @Override
        public void onFinish(File outputFile, Uri outputUri) {
            // 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
            Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
            tempFile = outputFile;
            if (iv == 0) {
                Glide.with(FirmruzhuActivity.this).load(outputUri).into(iv_scanning);
            } else {
                Glide.with(FirmruzhuActivity.this).load(outputUri).into(iv_firmscanning);
            }

        }
    };

    /**
     * 选择图片
     */
    private void editAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(FirmruzhuActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
        PermissionGen.with(FirmruzhuActivity.this)
                .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).request();
    }

    // 调用系统相册
    protected void startPick(DialogInterface dialog) {
        dialog.dismiss();
        PermissionGen.needPermission(FirmruzhuActivity.this,
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
                intent.setData(Uri.parse("package:" + FirmruzhuActivity.this.getPackageName()));
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
