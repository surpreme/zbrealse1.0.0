package livestream.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.base.BaseActivity;
import com.aite.a.model.GoodsReportInfo;
import com.aite.a.model.GoodsReportInfo.type_list;
import com.aite.a.parse.NetRun;
import com.aite.a.utils.LQRPhotoSelectUtils;
import com.aite.a.view.MyListView;
import com.aite.a.view.MyShiGuangZhou;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * 直播房间举报
 * 
 * @author Administrator
 *
 */
public class RoomReportActivity extends BaseActivity implements
		OnClickListener {

	private ImageView iv_bcreturn, iv_ivon;
	private TextView tv_bctitle, tv_progress1, tv_progress2, tv_progress3,
			tv_gname, tv_imgname1, tv_imgname2, tv_imgname3, tv_return,
			tv_storename;
	private MyShiGuangZhou msgz_sgz;
	private MyListView lv_type;
	private Spinner sp_jbtitle;
	private EditText et_content;
	private Button bt_choose1, bt_choose2, bt_choose3;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private String room_id = "";
	private GoodsReportInfo goodsReportInfo;
	private MyAdapter myAdapter;
	private LQRPhotoSelectUtils mLqrPhotoSelectUtils;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case goods_report_id:
				if (msg.obj != null) {
					// 举报信息
					try {
						goodsReportInfo = (GoodsReportInfo) msg.obj;
						bitmapUtils.display(iv_ivon,
								goodsReportInfo.goods_info.goods_image);
						tv_gname.setText(goodsReportInfo.goods_info.goods_name);
						tv_storename
								.setText(goodsReportInfo.goods_info.room_id);

						if (goodsReportInfo.type_list!=null&&goodsReportInfo.type_list.size()!=0){
							List<String> menu = new ArrayList<String>();
							goodsReportInfo.type_list.get(0).ischoose=true;
							for (int i = 0; i < goodsReportInfo.type_list.get(0).subject.size(); i++) {
								menu.add(goodsReportInfo.type_list.get(0).subject.get(i).inform_subject_content);
							}
							SpinnerAdapter adapter = new SpinnerAdapter(
									RoomReportActivity.this,
									android.R.layout.simple_spinner_item, menu);
							sp_jbtitle.setAdapter(adapter);
						}
						myAdapter = new MyAdapter(goodsReportInfo.type_list);
						lv_type.setAdapter(myAdapter);
					} catch (Exception e) {
						Toast.makeText(RoomReportActivity.this,
								"已经被举报请等待处理", Toast.LENGTH_SHORT).show();
						finish();
					}
				}
				break;
			case goods_report_err:
				Toast.makeText(RoomReportActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			case submit_report_id:
				// 提交举报
				if (msg.obj != null) {
					String str = (String) msg.obj;
					if (str.equals("举报成功请等待处理")) {
						finish();
					}
					Toast.makeText(RoomReportActivity.this, str,
							Toast.LENGTH_SHORT).show();
				}
				break;
			case submit_report_err:
				Toast.makeText(RoomReportActivity.this,
						getString(R.string.systembusy), Toast.LENGTH_SHORT)
						.show();
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goodsreport);
		findViewById();
	}

	@Override
	protected void findViewById() {
		iv_bcreturn = (ImageView) findViewById(R.id._iv_back);
		iv_ivon = (ImageView) findViewById(R.id.iv_ivon);
		tv_bctitle = (TextView) findViewById(R.id._tv_name);
		tv_progress1 = (TextView) findViewById(R.id.tv_progress1);
		tv_progress2 = (TextView) findViewById(R.id.tv_progress2);
		tv_progress3 = (TextView) findViewById(R.id.tv_progress3);
		tv_gname = (TextView) findViewById(R.id.tv_gname);
		tv_imgname1 = (TextView) findViewById(R.id.tv_imgname1);
		tv_imgname2 = (TextView) findViewById(R.id.tv_imgname2);
		tv_imgname3 = (TextView) findViewById(R.id.tv_imgname3);
		tv_storename = (TextView) findViewById(R.id.tv_storename);
		tv_return = (TextView) findViewById(R.id.tv_return);
		msgz_sgz = (MyShiGuangZhou) findViewById(R.id.msgz_sgz);
		lv_type = (MyListView) findViewById(R.id.lv_type);
		sp_jbtitle = (Spinner) findViewById(R.id.sp_jbtitle);
		et_content = (EditText) findViewById(R.id.et_content);
		bt_choose1 = (Button) findViewById(R.id.bt_choose1);
		bt_choose2 = (Button) findViewById(R.id.bt_choose2);
		bt_choose3 = (Button) findViewById(R.id.bt_choose3);
		tv_bctitle.setText(getString(R.string.goodsdatails_reminder64));
		initView();
	}

	@Override
	protected void initView() {
		iv_bcreturn.setOnClickListener(this);
		bt_choose1.setOnClickListener(this);
		bt_choose2.setOnClickListener(this);
		bt_choose3.setOnClickListener(this);
		tv_return.setOnClickListener(this);
		netRun = new NetRun(this, handler);
		bitmapUtils = new BitmapUtils(this);
		// 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
		mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, lqr, true);
		initData();
	}

	@Override
	protected void initData() {
		room_id = getIntent().getStringExtra("room_id");
		netRun.RoomReport(room_id);
	}

	@Override
	public void ReGetData() {

	}

	public String imgtype = "1";

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id._iv_back) {
			finish();
		} else if (id == R.id.tv_return) {// 提交
			if (sp_jbtitle.getSelectedItem() == null) {
				Toast.makeText(RoomReportActivity.this, "请选择举报类型", Toast.LENGTH_SHORT).show();
				return;
			}
			String title = myAdapter.getTitle(sp_jbtitle.getSelectedItem()
					.toString());
			String type = myAdapter.getType();
			String string = et_content.getText().toString();
			if (TextUtils.isEmpty(string)) {
				Toast.makeText(RoomReportActivity.this,
						getString(R.string.distribution_center37),
						Toast.LENGTH_SHORT).show();
				return;
			}
			System.out.println("---------------title=" + title + "  type="
					+ type + "  string=" + string + "  goods_id=" + goodsReportInfo.goods_info.room_id
					+ "  img1=" + img1 + "  img2=" + img2 + "  img3" + img3);
			netRun.RoomSubmitReport(goodsReportInfo.goods_info.room_id, title,
					type, string, img1, img2, img3);
		} else if (id == R.id.bt_choose1) {// 图1
			imgtype = "1";
			editAvatar();
		} else if (id == R.id.bt_choose2) {// 图2
			imgtype = "2";
			editAvatar();
		} else if (id == R.id.bt_choose3) {// 图3
			imgtype = "3";
			editAvatar();
		}
	}

	/**
	 * 举报类型
	 * 
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter {
		List<type_list> type_list;

		public MyAdapter(List<type_list> type_list) {
			this.type_list = type_list;
		}

		/**
		 * 获得主题
		 * 
		 * @param str
		 */
		public String getTitle(String str) {
			for (int i = 0; i < type_list.size(); i++) {
				if (type_list.get(i).ischoose) {
					for (int j = 0; j < type_list.get(i).subject.size(); j++) {
						if (str.equals(type_list.get(i).subject.get(j).inform_subject_content)) {
							return type_list.get(i).subject.get(j).inform_subject_id
									+ ","
									+ type_list.get(i).subject.get(j).inform_subject_content;
						}
					}
				}
			}
			return "";
		}

		/**
		 * 获得类型
		 * 
		 */
		public String getType() {
			for (int i = 0; i < type_list.size(); i++) {
				if (type_list.get(i).ischoose) {
					return type_list.get(i).inform_type_id + ","
							+ type_list.get(i).inform_type_name;
				}
			}
			return "";
		}

		@Override
		public int getCount() {
			return type_list == null ? 0 : type_list.size();
		}

		@Override
		public Object getItem(int position) {
			return type_list == null ? null : type_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(RoomReportActivity.this,
						R.layout.item_goodsreport, null);
				new ViewHolder(convertView);
			}
			final ViewHolder holder = (ViewHolder) convertView.getTag();
			final type_list type_list2 = type_list.get(position);
			holder.cb_choose.setText(type_list2.inform_type_name + "("
					+ type_list2.inform_type_desc + ")");
			holder.cb_choose.setChecked(type_list2.ischoose);

			holder.cb_choose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (holder.cb_choose.isChecked()) {
						for (int i = 0; i < type_list.size(); i++) {
							type_list.get(i).ischoose = false;
						}
						type_list2.ischoose = true;

						List<String> menu = new ArrayList<String>();
						for (int i = 0; i < type_list2.subject.size(); i++) {
							menu.add(type_list2.subject.get(i).inform_subject_content);
						}
						// 定义适配器
						// sp_type.getSelectedItem().toString()
						SpinnerAdapter adapter = new SpinnerAdapter(
								RoomReportActivity.this,
								android.R.layout.simple_spinner_item, menu);
						sp_jbtitle.setAdapter(adapter);
						notifyDataSetChanged();
					}
				}
			});
			return convertView;
		}

		class ViewHolder {
			CheckBox cb_choose;

			public ViewHolder(View convertView) {
				cb_choose = (CheckBox) convertView.findViewById(R.id.cb_choose);
				convertView.setTag(this);
			}
		}
	}

	/**
	 * 举报主题
	 * 
	 * @author Administrator
	 *
	 */
	private class SpinnerAdapter extends ArrayAdapter<String> {
		Context context;
		List<String> items = new ArrayList<String>();

		public SpinnerAdapter(final Context context,
				final int textViewResourceId, final List<String> items) {
			super(context, textViewResourceId, items);
			this.items = items;
			this.context = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items.get(position));
			tv.setGravity(Gravity.CENTER);
			// tv.setTextColor(Color.BLUE);
			// tv.setTextSize(12, TypedValue.COMPLEX_UNIT_SP);
			tv.setTextSize(12);
			int px2dip = dip2px(context, 10);
			tv.setPadding(0, px2dip, 0, px2dip);
			return convertView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}
			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items.get(position));
			tv.setGravity(Gravity.CENTER);
			// tv.setTextColor(Color.BLUE);
			tv.setTextSize(12);
			// int px2dip = dip2px(context, 10);
			// tv.setPadding(0, px2dip, 0, px2dip);
			return convertView;
		}
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	private File img1 = null, img2 = null, img3 = null;

	private static final int DATE_DIALOG_ID = 1;
	private static final int SHOW_DATAPICK = 0;


	private LQRPhotoSelectUtils.PhotoSelectListener lqr = new LQRPhotoSelectUtils.PhotoSelectListener() {
		@Override
		public void onFinish(File outputFile, Uri outputUri) {
			// 4、当拍照或从图库选取图片成功后回调
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
			Log.i("---------------", "图片路径  " + outputFile.getAbsolutePath() + "    outputFile=" + outputFile.exists());
			if (imgtype.equals("1")) {
				tv_imgname1.setText(outputFile.getName());
				img1 = outputFile;
			} else if (imgtype.equals("2")) {
				tv_imgname2.setText(outputFile.getName());
				img2 = outputFile;
			} else if (imgtype.equals("3")) {
				tv_imgname3.setText(outputFile.getName());
				img3 = outputFile;
			}
		}
	};

	/**
	 * 选择图片
	 */
	private void editAvatar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(RoomReportActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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
		PermissionGen.with(RoomReportActivity.this)
				.addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
				.permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.CAMERA
				).request();
	}

	// 调用系统相册
	protected void startPick(DialogInterface dialog) {
		dialog.dismiss();
		PermissionGen.needPermission(RoomReportActivity.this,
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
				intent.setData(Uri.parse("package:" + RoomReportActivity.this.getPackageName()));
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
