package com.aite.a.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;

/**
 * 发布帖子
 * 
 * @author Administrator
 *
 */
public class ReleasepostActivity extends BaseActivity implements
		OnClickListener {
	private ImageView _iv_back;
	private TextView _tv_name, _tx_right;
	private EditText et_content;
	private MyGridView gv_bbs_image;
	private CheckBox cb_bbs_anonymous;
	private Button bt_starrelease;
	private MyAdapter myAdapter;
	private List<Bitmap> imglist;
	private Bitmap bss_addimage;
	private boolean ismax=false;
	private MyAdapter myadapter;
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
		setContentView(R.layout.releasepost_activity);
		findViewById();
	}

	@Override
	public void ReGetData() {
		
	}

	@Override
	protected void findViewById() {
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		_tx_right = (TextView) findViewById(R.id._tx_right);
		et_content = (EditText) findViewById(R.id.et_content);
		gv_bbs_image = (MyGridView) findViewById(R.id.gv_bbs_image);
		cb_bbs_anonymous = (CheckBox) findViewById(R.id.cb_bbs_anonymous);
		bt_starrelease = (Button) findViewById(R.id.bt_starrelease);
		initView();
	}

	@Override
	protected void initView() {
		_tv_name.setText(getI18n(R.string.fabutiezi));
		_tx_right.setTextColor(Color.WHITE);
		_tx_right.setText(getI18n(R.string.cancel));
		
		_iv_back.setOnClickListener(this);
		_tx_right.setOnClickListener(this);
		bt_starrelease.setOnClickListener(this);
		bss_addimage = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.bss_addimage);
		imglist = new ArrayList<Bitmap>();
		imglist.add(bss_addimage);
		myAdapter = new MyAdapter(imglist);
		gv_bbs_image.setAdapter(myAdapter);
	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id._iv_back||v.getId()==R.id._tx_right){
			finish();
		}
//		switch (v.getId()) {
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id._tx_right:
//			finish();
//			break;
//		case R.id.bt_starrelease:
//			// TODO 发布
//			break;
//		}
	}

	/**
	 * 添加图片适配器
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<Bitmap> data = new ArrayList<Bitmap>();

		public MyAdapter(List<Bitmap> data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {

			return data == null ? null : data.get(position);
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(ReleasepostActivity.this,
						R.layout.bbs_imagelist, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.iv_img.setImageBitmap(data.get(position));
			holder.iv_img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (position == data.size() - 1) {
						if (!ismax) {
							editAvatar();
						}
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView iv_img;

			public ViewHolder(View convertView) {
				iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
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
			if (imglist.size() < 9) {
				imglist.remove(imglist.size() - 1);
				imglist.add(bmp);
				imglist.add(bss_addimage);
				myAdapter = new MyAdapter(imglist);
				gv_bbs_image.setAdapter(myAdapter);
			}else {
				ismax=true;
				imglist.remove(imglist.size() - 1);
				imglist.add(bmp);
				myAdapter = new MyAdapter(imglist);
				gv_bbs_image.setAdapter(myAdapter);
			}
			saveCropPic(bmp);
			Log.i("skythinking", tempFile.getAbsolutePath());

		}
	}

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

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
