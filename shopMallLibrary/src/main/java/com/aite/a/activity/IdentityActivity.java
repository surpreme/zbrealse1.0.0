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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.IdentityInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 实名认证
 *
 * @author Administrator
 *
 */
public class IdentityActivity extends BaseActivity implements OnClickListener {
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private EditText ed_zsname;
	private ImageView iv_identityimg, _iv_back,iv_zzimg;
	private Button bt_xz,bt_xz2;
	private TextView tv_filename, tv_state, _tv_name, tv_present,tv_zzfilename;
	private LinearLayout ll_sh;
	private String is_live;
	private IdentityInfo identityInfo;
	private File id_card_img,business_license_img;
	private File tempFile;
	private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case identity_id:
					if (msg.obj != null) {
						identityInfo = (IdentityInfo) msg.obj;
						if (identityInfo.member_is_auth!=null&&identityInfo.member_is_auth.equals("1")) {
							tv_present.setVisibility(View.VISIBLE);
							tv_present.setText(getString(R.string.evaluation_tips20));
							tv_present.setClickable(false);
						}
						// 状态 -1：未提交过认证信息 0：已提交认证申请，但未审核 1：已审核通过 2：审核失败
						if (identityInfo.member_auth_info.treatment_state
								.equals("1")) {				//已审核通过
							ll_sh.setVisibility(View.VISIBLE);
							tv_state.setText("审核通过");
							tv_present.setVisibility(View.GONE);
						}
							else if (identityInfo.member_auth_info.treatment_state
								.equals("2")) {					//审核失败
							tv_present.setVisibility(View.VISIBLE);
							tv_state.setText("审核失败,请重新提交");
						}
							else if (identityInfo.member_auth_info.treatment_state
								.equals("0")) {					//已提交认证申请,未审核
							ll_sh.setVisibility(View.VISIBLE);
							tv_state.setText("已提交认证,等待审核");
							tv_present.setVisibility(View.GONE);
						}
						if (identityInfo.member_auth_info.mobile != null) {
							ed_zsname
									.setText(identityInfo.member_auth_info.mobile);
						}

						if (identityInfo.member_auth_info.id_card_img != null) {
							iv_identityimg.setVisibility(View.VISIBLE);
							Glide.with(IdentityActivity.this).load(identityInfo.member_auth_info.id_card_img).into(iv_identityimg);
						} else {
							iv_identityimg.setVisibility(View.GONE);
						}
						if (identityInfo.member_auth_info.business_license_img != null) {
							iv_zzimg.setVisibility(View.VISIBLE);
							Glide.with(IdentityActivity.this).load(identityInfo.member_auth_info.business_license_img).into(iv_zzimg);
						} else {
							iv_zzimg.setVisibility(View.GONE);
						}
					}
					break;
				case identity_err:
					Toast.makeText(IdentityActivity.this, getString(R.string.systembusy),
							Toast.LENGTH_SHORT).show();
					break;
				case identity_save_id:
					if (msg.obj != null) {
						String re = (String) msg.obj;

						Toast.makeText(IdentityActivity.this, re,
								Toast.LENGTH_SHORT).show();
						finish();
					}
					break;
				case identity_save_err:
					String re = (String) msg.obj;
					re = (re == null || re.equals("")) ? getString(R.string.systembusy) : re ;
					Toast.makeText(IdentityActivity.this, re,
							Toast.LENGTH_SHORT).show();
					break;
			}
		};
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentication);
		findViewById();
	}

	@Override
	protected void findViewById() {
		ed_zsname = (EditText) findViewById(R.id.ed_zsname);
		iv_identityimg = (ImageView) findViewById(R.id.iv_identityimg);
		bt_xz = (Button) findViewById(R.id.bt_xz);
		bt_xz2 = (Button) findViewById(R.id.bt_xz2);
		tv_filename = (TextView) findViewById(R.id.tv_filename);
		tv_state = (TextView) findViewById(R.id.tv_state);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_zzimg = (ImageView) findViewById(R.id.iv_zzimg);
		tv_present = (TextView) findViewById(R.id.tv_present);
		tv_zzfilename = (TextView) findViewById(R.id.tv_zzfilename);
		ll_sh = (LinearLayout) findViewById(R.id.ll_sh);
		_iv_back.setOnClickListener(this);
		bt_xz.setOnClickListener(this);
		bt_xz2.setOnClickListener(this);
		tv_present.setOnClickListener(this);
		initView();
	}

	@Override
	protected void initView() {
		is_live=getIntent().getStringExtra("is_live");
		if (is_live==null){
			is_live="0";
		}
		_tv_name.setText(getString(R.string.distribution_center27));
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		// 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
		mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
		initData();
	}

	@Override
	protected void initData() {
		netRun.YYIdentity();
	}

	@Override
	public void ReGetData() {

	}

	private int imgtype=1;

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//			case R.id._iv_back:
//				finish();
//				break;
//			case R.id.bt_xz:
//				// 选择身份证图像
//				imgtype=1;
//				editAvatar();
//				break;
//			case R.id.bt_xz2:
//				//选择营业执照图像
//				imgtype=2;
//				editAvatar();
//				break;
//			case R.id.tv_present:
//				// 提交认证
//				String string = ed_zsname.getText().toString();
//				if (TextUtils.isEmpty(string)) {
//					Toast.makeText(IdentityActivity.this, getString(R.string.type_in_phone_regist),
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				netRun.YYIdentitysave(string, id_card_img, business_license_img,is_live);
//				break;
//		}
		if(v.getId()== R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.bt_xz){
			// 选择身份证图像
			imgtype=1;
			editAvatar();
		}else if(v.getId()==R.id.bt_xz2){
			//选择营业执照图像
			imgtype=2;
			editAvatar();
		}else if(v.getId()==R.id.tv_present){
			// 提交认证
			String string = ed_zsname.getText().toString();
			if (TextUtils.isEmpty(string)) {
				Toast.makeText(IdentityActivity.this, getString(R.string.type_in_phone_regist),
						Toast.LENGTH_SHORT).show();
				return;
			}
			netRun.YYIdentitysave(string, id_card_img, business_license_img,is_live);
		}
	}


	private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
		@Override
		public void onFinish(File outputFile, Uri outputUri) {
			// 4、当拍照或从图库选取图片成功后回调
			Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
			tempFile=outputFile;
			if (imgtype==1) {
				//身份证
				id_card_img=tempFile;
				tv_filename.setText(tempFile.getName());
				iv_identityimg.setVisibility(View.VISIBLE);
				Glide.with(IdentityActivity.this).load(outputUri).into(iv_identityimg);
			}else if (imgtype==2){
				//营业执照
				business_license_img=tempFile;
				tv_zzfilename.setText(tempFile.getName());
				iv_zzimg.setVisibility(View.VISIBLE);
				Glide.with(IdentityActivity.this).load(outputUri).into(iv_zzimg);
			}

		}
	};

	/**
	 * 选择图片
	 */
	private void editAvatar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(IdentityActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
		PermissionGen.with(IdentityActivity.this)
				.addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
				.permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.CAMERA
				).request();
	}

	// 调用系统相册
	protected void startPick(DialogInterface dialog) {
		dialog.dismiss();
		PermissionGen.needPermission(IdentityActivity.this,
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
				intent.setData(Uri.parse("package:" + IdentityActivity.this.getPackageName()));
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
