package com.aite.a.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.ReleaseFile2Info;
import com.aite.a.model.ReleaseFileInfo;
import com.aite.a.model.ReleaseTaskInfo;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.RelaaseTaskPopu;
import com.aite.a.utils.RelaaseTaskPopu.calsshuidiao1;
import com.aite.a.utils.RelaaseTaskTimePopu;
import com.aite.a.utils.RelaaseTaskTimePopu.getTime;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

/**
 * 发布任务
 * 
 * @author Administrator
 *
 */
public class ReleaseTaskActivity extends BaseActivity implements
		OnClickListener {

	private TextView _tv_name, tv_category, tv_svtime;
	private ImageView _iv_back, iv_addimg, iv_addfile;
	private EditText ed_demand, ed_budget, ed_svnum, ed_title;
	private MyGridView mgv_fujian;
	private RelativeLayout rl_category, rl_budget;
	private RadioGroup rg_ms;
	private RadioButton rb_zhaobiao, rb_areward;
	private NetRun netRun;
	private BitmapUtils btu;
	private List<ReleaseTaskInfo> releaseTaskInfo = null;
	private RelaaseTaskPopu relaaseTaskPopu;
	private Button bt_submit;
	private String file_sn = "";
	private static final int DOCUMENT = 4;
	private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 5;
	private static final int CAPTURE_ADDRESS_ACTIVITY_REQUEST_CODE = 6;
	private static final int CAPTURE_SHOOTING_ACTIVITY_REQUEST_CODE = 7;
	private ReleaseFileInfo releaseFileInfo;
	private ImagelistAdapter imagelistAdapter;

	private List<ReleaseFileInfo> rlist;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case release_task_id:
				if (msg.obj != null) {
					releaseTaskInfo = (List<ReleaseTaskInfo>) msg.obj;
					isan = false;
					showpopw(releaseTaskInfo);
				}
				break;
			case release_task_err:

				break;
			case image_upload_id:
				if (msg.obj != null) {
					if (rlist == null) {
						rlist = new ArrayList<ReleaseFileInfo>();
					}
					rlist.add(releaseFileInfo = (ReleaseFileInfo) msg.obj);
					if (file_sn.equals("")) {
						releaseFileInfo = (ReleaseFileInfo) msg.obj;
						file_sn = releaseFileInfo.file_sn;
					}
				}
				break;
			case release_task2_id:
				if (msg.obj != null) {
					String re = (String) msg.obj;
					Toast.makeText(ReleaseTaskActivity.this, re,
							Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			}
		};
	};

	private List<ReleaseFile2Info> imglist = new ArrayList<ReleaseFile2Info>();
	// 创建一个以当前系统时间为名称的文件，防止重复
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
		return sdf.format(date) + ".png";
	}

	private static final int PHOTO_CARMERA = 1;
	private static final int PHOTO_PICK = 2;
	private static final int PHOTO_CUT = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_releasetask);
		findViewById();
	}

	@Override
	public void ReGetData() {

	}

	@Override
	protected void findViewById() {
		_tv_name = (TextView) findViewById(R.id._tv_name);
		tv_category = (TextView) findViewById(R.id.tv_category);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		iv_addimg = (ImageView) findViewById(R.id.iv_addimg);
		ed_demand = (EditText) findViewById(R.id.ed_demand);
		ed_title = (EditText) findViewById(R.id.ed_title);
		mgv_fujian = (MyGridView) findViewById(R.id.mgv_fujian);
		rl_category = (RelativeLayout) findViewById(R.id.rl_category);
		rl_budget = (RelativeLayout) findViewById(R.id.rl_budget);
		ed_budget = (EditText) findViewById(R.id.ed_budget);
		ed_svnum = (EditText) findViewById(R.id.ed_svnum);
		rg_ms = (RadioGroup) findViewById(R.id.rg_ms);
		rb_zhaobiao = (RadioButton) findViewById(R.id.rb_zhaobiao);
		rb_areward = (RadioButton) findViewById(R.id.rb_areward);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		tv_svtime = (TextView) findViewById(R.id.tv_svtime);
		iv_addfile = (ImageView) findViewById(R.id.iv_addfile);
		initView();
	}

	@Override
	protected void initView() {
		iv_addimg.setOnClickListener(this);
		rl_category.setOnClickListener(this);
		rl_budget.setOnClickListener(this);
		_iv_back.setOnClickListener(this);
		tv_svtime.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		iv_addfile.setOnClickListener(this);
		rg_ms.setOnCheckedChangeListener(list);
		netRun = new NetRun(this, handler);
		btu = new BitmapUtils(this);
		getPersimmions();
		_tv_name.setText(getString(R.string.releasetask));
		_iv_back.setBackgroundResource(R.drawable.jd_return);
		initData();
	}

	@Override
	protected void initData() {

	}

	private boolean isan = false;

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.rl_category){
			// 类目
			if (State.UserKey==null) {
				Toast.makeText(ReleaseTaskActivity.this, getString(R.string.order_reminder109), Toast.LENGTH_SHORT).show();
				return;
			}
			if (releaseTaskInfo == null) {
				if (!isan) {
					netRun.ReleaseTask();
					isan = true;
				}
			} else {
				showpopw(releaseTaskInfo);
			}
		}else if(v.getId()==R.id.iv_addimg){
			// 添加图片
			if (imglist.size() < 5) {
				editAvatar();
			} else {
				Toast.makeText(ReleaseTaskActivity.this,
						getString(R.string.ceiling), Toast.LENGTH_SHORT).show();
			}
		} else if (v.getId()==R.id.iv_addfile) {
			// 添加文件
			if (imglist.size() < 5) {
				SendDocument();
			} else {
				Toast.makeText(ReleaseTaskActivity.this,
						getString(R.string.ceiling), Toast.LENGTH_SHORT).show();
			}
		}else if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.tv_svtime){
			// 时间选择
			showtimepopw();
		}else if(v.getId()==R.id.bt_submit){
			// 提交
			SubmitDemand();
		}

//		switch (v.getId()) {
//		case R.id.rl_category:
//			// 类目
//			if (State.UserKey==null) {
//				Toast.makeText(ReleaseTaskActivity.this, getString(R.string.order_reminder109), Toast.LENGTH_SHORT).show();
//				return;
//			}
//			if (releaseTaskInfo == null) {
//				if (!isan) {
//					netRun.ReleaseTask();
//					isan = true;
//				}
//			} else {
//				showpopw(releaseTaskInfo);
//			}
//			break;
//		case R.id.rl_budget:
//			// 预算
//			break;
//		case R.id.iv_addimg:
//			// 添加图片
//			if (imglist.size() < 5) {
//				editAvatar();
//			} else {
//				Toast.makeText(ReleaseTaskActivity.this,
//						getString(R.string.ceiling), Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.iv_addfile:
//			// 添加文件
//			if (imglist.size() < 5) {
//				SendDocument();
//			} else {
//				Toast.makeText(ReleaseTaskActivity.this,
//						getString(R.string.ceiling), Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.tv_svtime:
//			// 时间选择
//			showtimepopw();
//			break;
//		case R.id.bt_submit:
//			// 提交
//			SubmitDemand();
//			break;
//		}
	}

	/**
	 * 提交需求
	 */
	private void SubmitDemand() {
		if (TextUtils.isEmpty(ed_title.getText().toString())) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(ed_demand.getText().toString())) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(ed_svnum.getText().toString())) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (fbtype == null) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(ed_budget.getText().toString())) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (category_id3 == null) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(tv_category.getText().toString())) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(tv_svtime.getText().toString())) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (file_sn.equals("")) {
			Toast.makeText(ReleaseTaskActivity.this,
					getString(R.string.pleasefillinthecompletedata),
					Toast.LENGTH_SHORT).show();
			return;
		}
		netRun.ReleaseTask2(ed_title.getText().toString(), ed_demand.getText()
				.toString(), ed_svnum.getText().toString(), fbtype, ed_budget
				.getText().toString(), category_id3, tv_category.getText()
				.toString(), tv_svtime.getText().toString(), file_sn);
	}

	/**
	 * 模式监听
	 */
	private RadioGroup.OnCheckedChangeListener list = new RadioGroup.OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == rb_zhaobiao.getId()) {
				// 招标
				fbtype = "2";
			} else if (checkedId == rb_areward.getId()) {
				// 悬赏
				fbtype = "1";
			}
		}
	};

	public String fbtype = null;

	/**
	 * 显示类目选择器
	 * 
	 * @param releaseTaskInfo
	 */
	private void showpopw(List<ReleaseTaskInfo> releaseTaskInfo) {
		relaaseTaskPopu = new RelaaseTaskPopu(ReleaseTaskActivity.this,
				releaseTaskInfo, 1, false);
		relaaseTaskPopu.showAtLocation(bt_submit, Gravity.BOTTOM, 0, 0);// 设置显示位置
		relaaseTaskPopu.setcalsshuidiao1(new calsshuidiao1() {

			@Override
			public void onItemClick(String class1_id, String clas2_id,
					String clas3_id, String class1_name, String clas2_name,
					String clas3_name) {
				category_id1 = class1_id;
				category_id2 = clas2_id;
				category_id3 = clas3_name;
				tv_category.setText(class1_name + "  " + clas2_name + "  "
						+ clas3_name);
			}
		});
	}

	// 类目ID
	private String category_id1, category_id2, category_id3;

	/**
	 * 时间选择
	 */
	private void showtimepopw() {
		RelaaseTaskTimePopu relaaseTaskTimePopu = new RelaaseTaskTimePopu(
				ReleaseTaskActivity.this);
		relaaseTaskTimePopu.showAtLocation(bt_submit, Gravity.BOTTOM, 0, 0);
		relaaseTaskTimePopu.setgetTime(new getTime() {

			@Override
			public void onItemClick(String timee) {
				tv_svtime.setText(timee);
			}
		});
	}

	/**
	 * 图片适配
	 * 
	 * @author Administrator
	 *
	 */
	private class ImagelistAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return imglist.size();
		}

		public void delimg(int pos) {
			imglist.remove(pos);
			notifyDataSetChanged();
		}

		@Override
		public Object getItem(int position) {
			return imglist == null ? null : imglist.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ReleaseTaskActivity.this,
						R.layout.image2_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ReleaseFile2Info releaseFile2Info = imglist.get(position);
			if (releaseFile2Info.file == null) {
				holder.iv_photo.setImageBitmap(releaseFile2Info.img);
				holder.tv_filename.setVisibility(View.GONE);
			} else {
				holder.tv_filename.setText(releaseFile2Info.file);
				holder.iv_photo.setVisibility(View.GONE);
			}
			holder.iv_del.setVisibility(View.VISIBLE);
			holder.iv_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 删除
					delimg(position);
					netRun.ImageDel(rlist.get(position).eid);
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_photo, iv_del;
			TextView tv_filename;

			public ViewHolder(View convertView) {
				iv_photo = (ImageView) convertView.findViewById(R.id.iv_photo);
				iv_del = (ImageView) convertView.findViewById(R.id.iv_del);
				tv_filename = (TextView) convertView
						.findViewById(R.id.tv_filename);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 选择图片
	 */
	private void editAvatar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(getI18n(R.string.select_picture_source));
		String[] items = new String[] { getI18n(R.string.media_lib),
				getI18n(R.string.take_picture) };
		dialog.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
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
		dialog.setNegativeButton(getI18n(R.string.cancel), null);
		dialog.create().show();
	}

	// 调用系统相机
	protected void startCamera(DialogInterface dialog) {
		dialog.dismiss();
		// 调用系统的拍照功能
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra("camerasensortype", 2); // 调用前置摄像头
		intent.putExtra("autofocus", true); // 自动对焦
		intent.putExtra("fullScreen", false); // 全屏
		intent.putExtra("showActionIcons", false);
		// 指定调用相机拍照后照片的存储路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
		startActivityForResult(intent, PHOTO_CARMERA);
	}

	// 调用系统相册
	protected void startPick(DialogInterface dialog) {
		dialog.dismiss();
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_PICK);
	}

	// 调用系统裁剪
	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以裁剪
		intent.putExtra("crop", true);
		// aspectX,aspectY是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX,outputY是裁剪图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		// 设置是否返回数据
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_CUT);
	}

	// 将裁剪后的图片显示在ImageView上
	private void setPicToView(Intent data) {
		Bundle bundle = data.getExtras();
		if (null != bundle) {
			final Bitmap bmp = bundle.getParcelable("data");
			// iv_icon.setImageBitmap(bmp);
			imglist.add(new ReleaseFile2Info(bmp, null));
			ImagelistAdapter imagelistAdapter = new ImagelistAdapter();
			mgv_fujian.setAdapter(imagelistAdapter);
			saveCropPic(bmp);
			// filelist.add(tempFile);
			Log.i("skythinking", tempFile.getAbsolutePath());
		}
	}

	private List<File> filelist = new ArrayList<File>();

	// 把裁剪后的图片保存到sdcard上
	private void saveCropPic(Bitmap bmp) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileOutputStream fis = null;
		bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
		try {
			fis = new FileOutputStream(tempFile);
			fis.write(baos.toByteArray());
			fis.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != baos) {
					baos.close();
				}
				if (null != fis) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 上传
		netRun.ImageUpload(file_sn, tempFile);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_CARMERA:
			startPhotoZoom(Uri.fromFile(tempFile), 300);
			break;
		case PHOTO_PICK:
			if (null != data) {
				startPhotoZoom(data.getData(), 300);
			}
			break;
		case PHOTO_CUT:
			if (null != data) {
				setPicToView(data);
			}
			break;
		case DOCUMENT:
			// 得到选中的文件
			if (data != null) {
				Uri uri = data.getData();// 得到uri，后面就是将uri转化成file的过程。
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor actualimagecursor = managedQuery(uri, proj, null, null,
						null);
				int actual_image_column_index = actualimagecursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				actualimagecursor.moveToFirst();
				String img_path = actualimagecursor
						.getString(actual_image_column_index);
				File file = new File(img_path);
				if (file.exists()) {
					String name = file.getName();
					if (name.endsWith(".jpg") || name.endsWith(".gif")
							|| name.endsWith(".jpeg") || name.endsWith(".png")
							|| name.endsWith(".doc") || name.endsWith(".xls")) {
						imglist.add(new ReleaseFile2Info(null, file.getName()));
						ImagelistAdapter imagelistAdapter = new ImagelistAdapter();
						mgv_fujian.setAdapter(imagelistAdapter);
						// 上传
						netRun.ImageUpload(file_sn, file);
					} else {
						Toast.makeText(ReleaseTaskActivity.this, getString(R.string.order_reminder110),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ReleaseTaskActivity.this, getString(R.string.order_reminder111),
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 调用文件管理器
	 */
	private void SendDocument() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");// 设置类型，任意类型
		// intent.setType("image/*");
		// intent.setType("audio/*"); //选择音频
		// intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
		// intent.setType("video/*;image/*");//同时选择视频和图片
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, DOCUMENT);
	}

	public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
	public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
	public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
	public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

	/**
	 * 获取文件指定文件的指定单位的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @param sizeType
	 *            获取大小的类型1为B、2为KB、3为MB、4为GB
	 * @return double值的大小
	 */
	public static long getFileOrFilesSize(String filePath, int sizeType) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("------------------获取文件大小失败");
		}
		return blockSize;
	}

	/**
	 * 调用此方法自动计算指定文件或指定文件夹的大小
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 计算好的带B、KB、MB、GB的字符串
	 */
	public static String getAutoFileOrFilesSize(String filePath) {
		File file = new File(filePath);
		long blockSize = 0;
		try {
			if (file.isDirectory()) {
				blockSize = getFileSizes(file);
			} else {
				blockSize = getFileSize(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("获取文件大小", "获取失败!");
		}
		return FormetFileSize(blockSize);
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @return
	 * @throws Exception
	 */
	private static long getFileSize(File file) throws Exception {
		long size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			file.createNewFile();
			Log.e("获取文件大小", "文件不存在!");
		}
		return size;
	}

	/**
	 * 获取指定文件夹
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	private static long getFileSizes(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSizes(flist[i]);
			} else {
				size = size + getFileSize(flist[i]);
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	private static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize = "0B";
		if (fileS == 0) {
			return wrongSize;
		}
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	/**
	 * 转换文件大小,指定转换的类型
	 * 
	 * @param fileS
	 * @param sizeType
	 * @return
	 */
	private static double FormetFileSize(long fileS, int sizeType) {
		DecimalFormat df = new DecimalFormat("#.00");
		double fileSizeLong = 0;
		switch (sizeType) {
		case SIZETYPE_B:
			fileSizeLong = Double.valueOf(df.format((double) fileS));
			break;
		case SIZETYPE_KB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
			break;
		case SIZETYPE_MB:
			fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
			break;
		case SIZETYPE_GB:
			fileSizeLong = Double.valueOf(df
					.format((double) fileS / 1073741824));
			break;
		default:
			break;
		}
		return fileSizeLong;
	}

	// 权限信息
	private String permissionInfo;
	private final int SDK_PERMISSION_REQUEST = 127;

	@TargetApi(23)
	private void getPersimmions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			ArrayList<String> permissions = new ArrayList<String>();
			if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.CAMERA);
			}
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
			}
			if (checkSelfPermission(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS);
			}
			if (permissions.size() > 0) {
				requestPermissions(
						permissions.toArray(new String[permissions.size()]),
						SDK_PERMISSION_REQUEST);
			}
		}
	}

	@TargetApi(23)
	private boolean addPermission(ArrayList<String> permissionsList,
			String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
			if (shouldShowRequestPermissionRationale(permission)) {
				return true;
			} else {
				permissionsList.add(permission);
				return false;
			}

		} else {
			return true;
		}
	}

	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		// TODO Auto-generated method stub
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

	}

}
