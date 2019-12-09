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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.aite.a.base.BaseActivity;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aiteshangcheng.a.R;
import com.bumptech.glide.Glide;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 分销
 * @author Administrator
 *
 */
public class DistributionActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_icon, _iv_back;
	private TextView tv_username, tv_commissionnum, tv_integralnum, tv_fansnum,
			_tv_name;
	private Button bt_tixian;
	private ExpandableListView elv_fxcenter;
	private MyAdapter myadapter;
	// 创建一个以当前系统时间为名称的文件，防止重复
	private File tempFile ;
	private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribution);
		findViewById();
	}

	@Override
	public void ReGetData() {

	}

	View inflate;

	@Override
	protected void findViewById() {
		inflate = View.inflate(this, R.layout.distribution_tou, null);
		iv_icon = (ImageView) inflate.findViewById(R.id.iv_icon);
		_iv_back = (ImageView) findViewById(R.id._iv_back);
		tv_username = (TextView) inflate.findViewById(R.id.tv_username);
		tv_commissionnum = (TextView) inflate
				.findViewById(R.id.tv_commissionnum);
		tv_integralnum = (TextView) inflate.findViewById(R.id.tv_integralnum);
		tv_fansnum = (TextView) inflate.findViewById(R.id.tv_fansnum);
		_tv_name = (TextView) findViewById(R.id._tv_name);
		bt_tixian = (Button) inflate.findViewById(R.id.bt_tixian);
		elv_fxcenter = (ExpandableListView) findViewById(R.id.elv_fxcenter);
		elv_fxcenter.setOnGroupClickListener(g);
		elv_fxcenter.setOnChildClickListener(c);
		iv_icon.setOnClickListener(this);
		_iv_back.setOnClickListener(this);
		bt_tixian.setOnClickListener(this);
		initData();
		initView();
	}

	List<String> parent = null;
	Map<String, List<String>> map = null;
	private int[] fximg = new int[] { R.drawable.fx1, R.drawable.fx2,
			R.drawable.fx3, R.drawable.fx4, R.drawable.fx5, R.drawable.fx6 };

	@Override
	protected void initView() {
		// 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
		mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, false);
		_tv_name.setText(getI18n(R.string.app_name));
		elv_fxcenter.addHeaderView(inflate);
		myadapter = new MyAdapter();
		elv_fxcenter.setAdapter(myadapter);
	}

	@Override
	protected void initData() {
		parent = new ArrayList<String>();
		parent.add(getString(R.string.distribution_center6));
		parent.add(getString(R.string.distribution_center7));
		parent.add(getString(R.string.distribution_center8));
		parent.add(getString(R.string.myredpackage));
		parent.add(getString(R.string.rich));
		parent.add(getString(R.string.distribution_center9));

		map = new HashMap<String, List<String>>();

		List<String> list1 = new ArrayList<String>();
		map.put(getString(R.string.distribution_center17), list1);

		List<String> list2 = new ArrayList<String>();
		list2.add(getString(R.string.distribution_center41));
		list2.add(getString(R.string.distribution_center42));
		list2.add(getString(R.string.distribution_center43));
		list2.add(getString(R.string.distribution_center44));
		list2.add(getString(R.string.distribution_center45));
		map.put(getString(R.string.distribution_center7), list2);

		List<String> list3 = new ArrayList<String>();
		list3.add(getString(R.string.transaction_completion));
		list3.add(getString(R.string.distribution_center46));
		list3.add(getString(R.string.distribution_center47));
		map.put(getString(R.string.distribution_center8), list3);

		List<String> list4 = new ArrayList<String>();
		map.put(getString(R.string.myredpackage), list4);
		List<String> list5 = new ArrayList<String>();
		map.put(getString(R.string.rich), list5);
		List<String> list6 = new ArrayList<String>();
		map.put(getString(R.string.distribution_center9), list6);

	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.iv_icon){
			editAvatar();
		}else if(v.getId()==R.id._iv_back){
			finish();
		}else if(v.getId()==R.id.bt_tixian){
			startActivity(new Intent(DistributionActivity.this,
					BalanceTxActivity.class));
		}
//		switch (v.getId()) {
//		case R.id.iv_icon:
//			editAvatar();
//			break;
//		case R.id._iv_back:
//			finish();
//			break;
//		case R.id.bt_tixian:
//			startActivity(new Intent(DistributionActivity.this,
//					BalanceTxActivity.class));
//			break;
//		}
	}

	private class MyAdapter extends BaseExpandableListAdapter {

		@Override
		public int getChildrenCount(int groupPosition) {
			String key = parent.get(groupPosition);
			int size = map.get(key).size();
			return size;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = parent.get(groupPosition);
			return (map.get(key).get(childPosition));
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {

			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			String key = DistributionActivity.this.parent.get(groupPosition);
			String info = map.get(key).get(childPosition);
			if (convertView == null) {
				convertView = View.inflate(DistributionActivity.this,
						R.layout.distribution_item2, null);
			}
			TextView tv_zidistribution = (TextView) convertView
					.findViewById(R.id.tv_zidistribution);
			tv_zidistribution.setText(info);
			return convertView;
		}

		@Override
		public int getGroupCount() {

			return parent.size();
		}

		@Override
		public Object getGroup(int groupPosition) {

			return parent.get(groupPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {

			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(DistributionActivity.this,
						R.layout.distribution_item, null);
			}
			ImageView iv_recommended = (ImageView) convertView
					.findViewById(R.id.iv_recommended);
			TextView tv_recommended = (TextView) convertView
					.findViewById(R.id.tv_recommended);
			iv_recommended.setBackgroundResource(fximg[groupPosition]);
			tv_recommended.setText(DistributionActivity.this.parent
					.get(groupPosition));
			return convertView;
		}

		@Override
		public boolean hasStableIds() {

			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return true;
		}

	}

	/**
	 * 分销一级菜单监听
	 */
	private OnGroupClickListener g = new OnGroupClickListener() {

		@Override
		public boolean onGroupClick(ExpandableListView parent, View v,
				int groupPosition, long id) {
			return false;
		}
	};
	/**
	 * 分销二级菜单监听
	 */
	private OnChildClickListener c = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			return false;
		}
	};

	private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
		@Override
		public void onFinish(File outputFile, Uri outputUri) {
			// 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
			Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
			tempFile = outputFile;
			Glide.with(DistributionActivity.this).load(outputUri).into(iv_icon);
		}
	};

	/**
	 * 选择图片
	 */
	private void editAvatar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(DistributionActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
		PermissionGen.with(DistributionActivity.this)
				.addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
				.permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.CAMERA
				).request();
	}

	// 调用系统相册
	protected void startPick(DialogInterface dialog) {
		dialog.dismiss();
		PermissionGen.needPermission(DistributionActivity.this,
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
				intent.setData(Uri.parse("package:" + DistributionActivity.this.getPackageName()));
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
