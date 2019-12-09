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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ShaiDanInfo;
import com.aite.a.model.UploadImgInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.community.adapter.Photo2Adapter;
import com.community.model.PhotoInfo;
import com.lidroid.xutils.BitmapUtils;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 晒单
 * 
 * @author Administrator
 *
 */
public class ShaiDanActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_gimg, _iv_back;
	private TextView tv_gname, tv_pingjia, _tv_name, tv_submit;
	private RatingBar rb_score;
	 private MyGridView gv_photo;
	private Photo2Adapter photoAdapter;
	private BitmapUtils bitmapUtilss;
	private NetRun netRun;
	private ShaiDanInfo shaiDanInfo;
	private UploadImgInfo uploadImgInfo;
	private String evaluate_image1="",evaluate_image2="",evaluate_image3="",evaluate_image4="",evaluate_image5="";
	private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
	private int imagerid;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case shai_dan_id:
				if (msg.obj!=null) {
					shaiDanInfo=(ShaiDanInfo) msg.obj;
					if (shaiDanInfo.geval_info!=null) {
						bitmapUtilss.display(iv_gimg, shaiDanInfo.geval_info.geval_goodsimage);
						tv_gname.setText(shaiDanInfo.geval_info.geval_goodsname);
						tv_pingjia.setText(shaiDanInfo.geval_info.geval_content);
						int geval_scores = Integer.parseInt(shaiDanInfo.geval_info.geval_scores);
						rb_score.setRating(geval_scores);
					}
				}
				break;
			case shai_dan_err:
				Toast.makeText(ShaiDanActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
				break;
			case shaidan_upload_id:
				//提交
				if (msg.obj!=null) {
					String str=(String) msg.obj;
					if (str.equals("1")) {
						Toast.makeText(ShaiDanActivity.this, getString(R.string.order_reminder129), Toast.LENGTH_SHORT).show();
						finish();
					}
				}
				break;
			case shaidan_upload_err:
				Toast.makeText(ShaiDanActivity.this, getString(R.string.systembusy), Toast.LENGTH_SHORT).show();
				break;
				case PICKIMAGE://选择图片
					if (msg.obj != null) {
						imagerid = (int) msg.obj;
						editAvatar();
					}
					break;
			}
		};
	};
	private String geval_id;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shaidan);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_gimg = (ImageView) findViewById(R.id.iv_gimg);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		tv_gname = (TextView) findViewById(R.id.tv_gname);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_pingjia = (TextView) findViewById(R.id.tv_pingjia);
		tv_submit = (TextView) findViewById(R.id.tv_submit);
		rb_score = (RatingBar) findViewById(R.id.rb_score);
		gv_photo = findViewById(R.id.gv_photo);
		_iv_back.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
		initView();
	}

	@Override
	protected void initView() {
		// 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
		mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
		List<PhotoInfo> data = new ArrayList<>();
		data.add(new PhotoInfo(false, null));
		photoAdapter = new Photo2Adapter(this, data, handler, getW());
		gv_photo.setAdapter(photoAdapter);
		_tv_name.setText(R.string.app_name);
		initData();
	}

	@Override
	protected void initData() {
		netRun = new NetRun(this, handler);
		bitmapUtilss = new BitmapUtils(this);
//		Album.initialize(AlbumConfig.newBuilder(this)
//				.setAlbumLoader(new MediaLoader()).build());
		
		Intent intent2 = getIntent();
		geval_id = intent2.getStringExtra("geval_id");
		netRun.ShaiDan(geval_id);
//		Myevaluationinfo myevaluationinfo = (Myevaluationinfo) intent2
//				.getSerializableExtra("data");
//		bitmapUtilss.display(iv_gimg, myevaluationinfo.geval_goodsimage);
//		tv_gname.setText(myevaluationinfo.geval_goodsname);
//		int parseInt = Integer.parseInt(myevaluationinfo.geval_scores);
//		rb_score.setRating(parseInt);
//		tv_pingjia.setText(myevaluationinfo.geval_content);
	}

	@Override
	public void ReGetData() {

	}


	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.tv_submit){
			// 提交
			List<File> data = photoAdapter.getData();
			netRun.ShaiDanUpload(geval_id, data,shaiDanInfo.ac_id);
		}
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_submit:
//			// 提交
//			List<File> data = photoAdapter.getData();
//			netRun.ShaiDanUpload(geval_id, data,shaiDanInfo.ac_id);
//			break;
//		}
	}

	private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
		@Override
		public void onFinish(File outputFile, Uri outputUri) {
			// 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
			Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
			List<File> data = new ArrayList<>();
			data.add(outputFile);
			photoAdapter.add(imagerid, data);
			}
//			netRun.UploadImg(shaiDanInfo.ac_id, tempFile, "image"+k);
	};

	/**
	 * 选择图片
	 */
	private void editAvatar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(ShaiDanActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
		PermissionGen.with(ShaiDanActivity.this)
				.addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
				.permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.CAMERA
				).request();
	}

	// 调用系统相册
	protected void startPick(DialogInterface dialog) {
		dialog.dismiss();
//		PermissionGen.needPermission(ShaiDanActivity.this,
//				LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
//				new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//						Manifest.permission.WRITE_EXTERNAL_STORAGE});
		Album.image(this)
				.multipleChoice()//多选
				.camera(false)//相机
				.columnCount(3)//列数
				.selectCount(6 - photoAdapter.getCount())//最大选择数量
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
				intent.setData(Uri.parse("package:" + ShaiDanActivity.this.getPackageName()));
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
		return (int) (width - (20 * scale + 0.5f)) / 5;
	}

}
