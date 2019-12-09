package com.aite.a.fargment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.activity.AddressManageActivity;
import com.aite.a.activity.BbsActivity;
import com.aite.a.activity.BuyerOrderFgActivity;
import com.aite.a.activity.CartActivity;
import com.aite.a.activity.ChangePassword;
import com.aite.a.activity.DistributionActivity;
import com.aite.a.activity.FavoriteListFargmentActivity;
import com.aite.a.activity.InformationActivity;
import com.aite.a.activity.IntegralShopActivity;
import com.aite.a.activity.MyStoreActivity;
import com.aite.a.activity.Myevaluation;
import com.aite.a.activity.MyfootprintActivity;
import com.aite.a.activity.PersonalActivity;
import com.aite.a.activity.PersonalInformationActivity;
import com.aite.a.activity.SellerOrderActivity;
import com.aite.a.base.BaseInformation;
import com.aite.a.model.User;
import com.aite.a.parse.NetRun;
import com.aite.a.view.CircleImageView;
import com.aite.a.view.MyGridView;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.BitmapUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//我的
public class Fragment_Mine extends BaseInformation implements OnClickListener {

	private CircleImageView personal_iv_avatr;
	private TextView personal_tv_name, predepoit, goods_fi_count,
			store_fi_count, seller_tx, _tv_name;
	private ImageView _iv_back;
	private RelativeLayout rl_allorder;
	private MyGridView mgv_navigation, mgv_maijia, mgv_maijia2;
	protected User user;
	private NetRun netRun;
	private BitmapUtils bitmapUtils;
	private static final int PHOTO_CARMERA = 1;
	private static final int PHOTO_PICK = 2;
	private static final int PHOTO_CUT = 3;
	// 买家菜单数组
	private int[] maijiaimg = new int[] { R.drawable.maijiadingdan,
			R.drawable.xuniorder, R.drawable.fuwuorder, R.drawable.gouwuche,
			R.drawable.myzuji, R.drawable.dizhi, R.drawable.gerenziliao,
			R.drawable.xiugaimima, R.drawable.zujifcf, R.drawable.xunifcf,
			R.drawable.myevaluation, R.drawable.kongbainull };
	private List<String> maijiatext;
	// 买家菜单数组
	private int[] maijiaimg2 = new int[] { R.drawable.shangjiadianpu,
			R.drawable.shangjiadingdan, R.drawable.kongbainull,
			R.drawable.kongbainull, };

	private List<String> maijiatext2;
	// 导航菜单数组
	private int[] navigationimage = new int[] { R.drawable.daizhifufcf,
			R.drawable.daifahuofcf, R.drawable.daishouhuofcf,
			R.drawable.dingdanfcf, R.drawable.jiaoyiwangcengfcf };
	private List<String> navigationtext;
	private List<String> navigationtextnum;

	// 创建一个以当前系统时间为名称的文件，防止重复
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
		return sdf.format(date) + ".png";
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case member_id:
				if (msg.obj != null) {
					user = (User) msg.obj;
					// 文字颜色修改
					personal_tv_name.setText(getString(R.string.welcome_page2)
							+ user.user_name + "\t！");
					// predepoit.setText("￥" + user.predepoit + "\n"
					// + getString(R.string.the_balance));goods_fi_count

//					settextviewcolor(goods_fi_count, user.goods_favorites_count
//							+ "\n", getString(R.string.collection_goods));
//					settextviewcolor(predepoit, "￥" + user.predepoit + "\n",
//							getString(R.string.the_balance));
//					settextviewcolor(store_fi_count, user.store_favorites_count
//							+ "\n", getString(R.string.collection_shop));

					// 菜单
					initmenu(user.ORDER_STATE_NEW, user.ORDER_STATE_PAY,
							user.ORDER_STATE_SEND, user.ORDER_STATE_SUCCESS);
					// 订单状态数量修改
					if (user.avator != null && !user.avator.equals("null")
							&& !user.avator.equals("")) {
						bitmapUtils.display(personal_iv_avatr, user.avator);
						new DownloadImageTask().execute(user.avator);
					}
					if (user.store_id != null && !user.store_id.equals("0")) {
						// TODO 卖家接口完成之后在显示
						seller_tx.setVisibility(View.VISIBLE);
						mgv_maijia2.setVisibility(View.VISIBLE);
					} else {
						seller_tx.setVisibility(View.GONE);
						mgv_maijia2.setVisibility(View.GONE);
					}
				} else {
					Toast.makeText(getActivity(),
							getString(R.string.act_no_data), Toast.LENGTH_SHORT)
							.show();
				}
				break;
			}
		};
	};

	@Override
	protected void initView() {
		personal_iv_avatr = (CircleImageView) layout
				.findViewById(R.id.personal_iv_avatr);
		personal_tv_name = (TextView) layout
				.findViewById(R.id.personal_tv_name);
		seller_tx = (TextView) layout.findViewById(R.id.seller_tx);
		predepoit = (TextView) layout.findViewById(R.id.predepoit);
		_tv_name = (TextView) layout.findViewById(R.id._tv_name);
		goods_fi_count = (TextView) layout.findViewById(R.id.goods_fi_count);
		store_fi_count = (TextView) layout.findViewById(R.id.store_fi_count);
		rl_allorder = (RelativeLayout) layout.findViewById(R.id.rl_allorder);
		mgv_navigation = (MyGridView) layout.findViewById(R.id.mgv_navigation);
		mgv_maijia = (MyGridView) layout.findViewById(R.id.mgv_maijia);
		mgv_maijia2 = (MyGridView) layout.findViewById(R.id.mgv_maijia2);
	}

	@Override
	protected void initData() {
		_tv_name.setText(getString(R.string.app_name));
		_iv_back.setVisibility(View.GONE);
		netRun = new NetRun(getActivity(), handler);
		bitmapUtils = new BitmapUtils(getActivity());
		if (State.UserKey == null) {
			Intent intent = new Intent(MAIN_);
			getActivity().sendBroadcast(intent);
		} else {
			netRun.getMember();
		}
	}

	@Override
	protected int getlayoutResId() {
		return R.layout.fragment_mine;
	}

	private void initmenu(String neww, String pay, String send, String success) {
		// 买家中心
		maijiatext = new ArrayList<String>();
		maijiatext.add(getString(R.string.buyer_orders));
		maijiatext.add(getString(R.string.virtualorders));
		maijiatext.add(getString(R.string.serviceorders));
		maijiatext.add(getString(R.string.my_shopping_cart));
		maijiatext.add(getString(R.string.my_footprint));
		maijiatext.add(getString(R.string.address_manage));
		maijiatext.add(getString(R.string.perdata));
		maijiatext.add(getString(R.string.update_password));
		maijiatext.add(getString(R.string.collectionfcf2));
		maijiatext.add(getString(R.string.collectionfcf));
		maijiatext.add(getString(R.string.myevaluation));
		maijiatext.add("");
		// 卖家中心
		maijiatext2 = new ArrayList<String>();
		maijiatext2.add(getString(R.string.my_shop));
		maijiatext2.add(getString(R.string.merchant_order));
		maijiatext2.add("");
		maijiatext2.add("");
		// 导航菜单
		navigationtext = new ArrayList<String>();
		navigationtext.add(getString(R.string.notpaying));
		navigationtext.add(getString(R.string.to_be_shipped));
		navigationtext.add(getString(R.string.goods_to_be_received));
		navigationtext.add(getString(R.string.all_orders1));
		navigationtext.add(getString(R.string.transaction_completion));
		// 订单状态数量
		navigationtextnum = new ArrayList<String>();
		navigationtextnum.add(neww);
		navigationtextnum.add(pay);
		navigationtextnum.add(send);
		navigationtextnum.add(success);
		navigationtextnum.add(success);
		// 买家中心
		mjadapter = new MjAdapter();
		mgv_maijia.setAdapter(mjadapter);
		// 卖家中心
		mjAdapter2 = new MjAdapter2();
		mgv_maijia2.setAdapter(mjAdapter2);
		// 导航菜单
		navigationAdapter = new NavigationAdapter();
		mgv_navigation.setAdapter(navigationAdapter);
	}

	private MjAdapter mjadapter;
	private MjAdapter2 mjAdapter2;
	private NavigationAdapter navigationAdapter;

	@Override
	public void onClick(View v) {
	}

	// 买家中心
	private class MjAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return maijiaimg.length;
		}

		@Override
		public Object getItem(int position) {

			return maijiaimg == null ? null : maijiaimg[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = View.inflate(getActivity(), R.layout.maijia_item,
					null);
			ImageView iv_menuimg = (ImageView) convertView
					.findViewById(R.id.iv_menuimg);
			TextView tv_menutext = (TextView) convertView
					.findViewById(R.id.tv_menutext);

			LinearLayout ll_menuitem = (LinearLayout) convertView
					.findViewById(R.id.ll_menuitem);
			// 高度适配
			LayoutParams layoutParams = ll_menuitem.getLayoutParams();
			layoutParams.height = getScreenWidth(getActivity()) / 4;
			ll_menuitem.setLayoutParams(layoutParams);

			iv_menuimg.setImageResource(maijiaimg[position]);
			tv_menutext.setText(maijiatext.get(position));
			ll_menuitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (position) {
					case 0:
						// 订单管理
						Intent intent1 = new Intent(getActivity(),
								BuyerOrderFgActivity.class);
						startActivity(intent1);
						break;
					case 1:
						// 虚拟订单
						Intent intent2 = new Intent(getActivity(),
								PersonalActivity.class);
						intent2.putExtra("type", "0");
						startActivity(intent2);
						break;
					case 2:
						// 服务订单
						Intent intent3 = new Intent(getActivity(),
								PersonalActivity.class);
						intent3.putExtra("type", "0");
						startActivity(intent3);
						break;
					case 3:
						// 购物车
						Intent intent4 = new Intent(getActivity(),
								CartActivity.class);
						Bundle bundle4 = new Bundle();
						bundle4.putString("shoopping", "shoopping");
						intent4.putExtras(bundle4);
						startActivity(intent4);
						break;
					case 4:
						// 我的足迹
						startActivity(new Intent(getActivity(),
								MyfootprintActivity.class));
						break;
					case 5:
						// 地址管理
						Intent intent5 = new Intent(getActivity(),
								AddressManageActivity.class);
						startActivity(intent5);
						break;
					case 6:
						// 个人资料
						Intent intent6 = new Intent(getActivity(),
								PersonalInformationActivity.class);
						Bundle bundle6 = new Bundle();
						bundle6.putString("icon", user.avator);
						intent6.putExtras(bundle6);
						startActivity(intent6);
						break;
					case 7:
						// 修改密码
						Intent intent7 = new Intent(getActivity(),
								ChangePassword.class);
						startActivity(intent7);
						break;
					case 8:
						// 收藏店铺
						Intent intent8 = new Intent(getActivity(),
								FavoriteListFargmentActivity.class);
						intent8.putExtra("i", 1);
						startActivity(intent8);
						break;
					case 9:
						// 收藏商品
						Intent intent9 = new Intent(getActivity(),
								FavoriteListFargmentActivity.class);
						intent9.putExtra("i", 2);
						startActivity(intent9);
						break;
					case 10:
						// 我的评价
						Intent intent10 = new Intent(getActivity(),
								Myevaluation.class);
						intent10.putExtra("touxiang", user.avator);
						intent10.putExtra("names", user.user_name);
						startActivity(intent10);
						break;
					case 11:
						// 我要开店
						// Intent intent5 = new Intent(PersonalActivity.this,
						// FreeOpenShopActivity.class);
						// startActivity(intent5);
						break;
					case 12:
						// // 提现
						// startActivity(new Intent(PersonalActivity.this,
						// BalanceTxActivity.class));
						// 分销
						startActivity(new Intent(getActivity(),
								DistributionActivity.class));
						break;
					case 13:
						// 论坛
						startActivity(new Intent(getActivity(),
								BbsActivity.class));
						// // 分销
						// Intent intent56 = new Intent(PersonalActivity.this,
						// DistributionActivity.class);
						// startActivity(intent56);
						break;
					case 14:
						// 积分商城
						startActivity(new Intent(getActivity(),
								IntegralShopActivity.class));
						break;
					case 15:
						// 资讯
						startActivity(new Intent(getActivity(),
								InformationActivity.class));
						break;
					}
				}
			});
			return convertView;
		}
	}

	// 卖家中心
	private class MjAdapter2 extends BaseAdapter {

		@Override
		public int getCount() {

			return maijiaimg2.length;
		}

		@Override
		public Object getItem(int position) {

			return maijiaimg2 == null ? null : maijiaimg2[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = View.inflate(getActivity(), R.layout.maijia_item,
					null);
			ImageView iv_menuimg = (ImageView) convertView
					.findViewById(R.id.iv_menuimg);
			TextView tv_menutext = (TextView) convertView
					.findViewById(R.id.tv_menutext);

			LinearLayout ll_menuitem = (LinearLayout) convertView
					.findViewById(R.id.ll_menuitem);
			// 高度适配
			LayoutParams layoutParams = ll_menuitem.getLayoutParams();
			layoutParams.height = getScreenWidth(getActivity()) / 4;
			ll_menuitem.setLayoutParams(layoutParams);

			iv_menuimg.setImageResource(maijiaimg2[position]);
			tv_menutext.setText(maijiatext2.get(position));
			ll_menuitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (position) {
					case 0:
						// 我的店铺
						startActivity(new Intent(getActivity(),
								MyStoreActivity.class));
						break;
					case 1:
						// 商家订单
						startActivity(new Intent(getActivity(),
								SellerOrderActivity.class));
						break;
					case 2:
						// // 服务订单
						// Intent intent4 = new Intent(PersonalActivity.this,
						// VrOrder.class);
						// intent4.putExtra("type", "0");
						// startActivity(intent4);
						break;
					case 3:
						// // 购物车
						// Bundle bundle5 = new Bundle();
						// bundle5.putString("shoopping", "shoopping");
						// openActivity(CartActivity.class, bundle5);
						// overrIn();
						break;

					}
				}
			});
			return convertView;
		}
	}

	// 我的订单
	private class NavigationAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return navigationimage.length;
		}

		@Override
		public Object getItem(int position) {

			return navigationimage == null ? null : navigationimage[position];
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.navigation_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			// 高度适配
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.ll_navigationitem
					.getLayoutParams();
			layoutParams.height = getScreenWidth(getActivity()) / 5;
			holder.ll_navigationitem.setLayoutParams(layoutParams);

			holder.iv_1daizhifufcf.setImageResource(navigationimage[position]);
			holder.tv_navigationname.setText(navigationtext.get(position));
			if (navigationtextnum != null
					&& !navigationtextnum.get(position).equals("0")) {
				holder.tv_new.setVisibility(View.VISIBLE);
				holder.tv_new.setText(navigationtextnum.get(position));
			} else {
				holder.tv_new.setVisibility(View.INVISIBLE);
			}
			holder.ll_navigationitem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getActivity(),
							BuyerOrderFgActivity.class);
					switch (position) {
					case 0:
						// 待支付
						intent.putExtra("viewPager", 1);
						break;
					case 1:
						// 待发货
						intent.putExtra("viewPager", 2);
						break;
					case 2:
						// 待收货
						intent.putExtra("viewPager", 3);
						break;
					case 3:
						// 评价
						intent.putExtra("viewPager", 4);
						break;
					case 4:
						// 交易完成
						intent.putExtra("viewPager", 4);
						break;

					}
					startActivity(intent);
				}
			});
			return convertView;
		}

		class ViewHolder {
			LinearLayout ll_navigationitem;
			ImageView iv_1daizhifufcf;
			TextView tv_new, tv_navigationname;

			public ViewHolder(View convertView) {
				ll_navigationitem = (LinearLayout) convertView
						.findViewById(R.id.ll_navigationitem);
				iv_1daizhifufcf = (ImageView) convertView
						.findViewById(R.id.iv_1daizhifufcf);
				tv_new = (TextView) convertView.findViewById(R.id.tv_new);
				tv_navigationname = (TextView) convertView
						.findViewById(R.id.tv_navigationname);
				convertView.setTag(this);
			}
		}
	}

	// 获得屏幕宽度
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * 选择图片
	 */
	private void editAvatar() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle(getString(R.string.select_picture_source));
		String[] items = new String[] { getString(R.string.media_lib),
				getString(R.string.take_picture) };
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
		dialog.setNegativeButton(getString(R.string.cancel), null);
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

	private Bitmap bmp = null;

	// 将裁剪后的图片显示在ImageView上
	private void setPicToView(Intent data) {
		Bundle bundle = data.getExtras();
		if (null != bundle) {
			// bmp = bundle.getParcelable("data");
			Drawable path = Drawable.createFromPath(tempFile.getAbsolutePath());
			// personal_bg.setImageBitmap(path);
			personal_iv_avatr.setBackgroundDrawable(path);
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
				Bundle bundle = data.getExtras();
				if (bundle != null) {
					bmp = bundle.getParcelable("data");
					saveCropPic(bmp);
				}
				setPicToView(data);
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

		protected Drawable doInBackground(String... urls) {
			return loadImageFromNetwork(urls[0]);
		}

		protected void onPostExecute(Drawable result) {
			if (result == null) {
				return;
			}
			personal_iv_avatr.setImageDrawable(result);
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

//	private void settextviewcolor(TextView view, String amount, String tex) {
//		String[] colors = getResources().getStringArray(R.array.colors);
//		StringBuffer sb = new StringBuffer();
//		String[] con = new String[] { amount, tex };
//
//		for (int i = 0; i < con.length; i++) {
//			sb.append(con[i]);
//		}
//
//		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
//				sb.toString());
//		int begin = 0;
//
//		for (int j = 0; j < colors.length; j++) {
//			String color = colors[j];
//			String[] s = color.split(",");
//
//			int mycolor = Color.rgb(Integer.parseInt(s[0]),
//					Integer.parseInt(s[1]), Integer.parseInt(s[2]));
//
//			int textlength = con[j].length();
//			spannableStringBuilder.setSpan(new ForegroundColorSpan(mycolor),
//					begin, begin + textlength,
//					spannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
//			begin += textlength;
//		}
//		view.setText(spannableStringBuilder);
//	}

}
