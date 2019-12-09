package com.aite.a;

import java.io.File;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import com.aite.a.base.Mark;
import com.aite.a.utils.CommonTools;
import com.aite.a.view.MyDialog;
import com.aiteshangcheng.a.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 检查更新
 * 
 * @author CAOYOU
 * 
 */
public class ExamineUpdate {
	private Activity activity;
	private MyDialog mdialog;
	private boolean hint = false;

	public ExamineUpdate(Activity activity, MyDialog mdialog, boolean hint) {
		super();
		this.activity = activity;
		this.mdialog = mdialog;
		this.hint = hint;
		update();
	}

	/**
	 * 检查更新
	 */
	private void update() {
		HttpUtils httpUtils = new HttpUtils();
//		mdialog.setMessage(APPSingleton.getContext().getString(R.string.checking_for_update).toString());
		httpUtils.send(HttpMethod.GET, Mark.apk_version, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				try {
					JSONObject object = new JSONObject(responseInfo.result);
					JSONObject datas = object.getJSONObject("datas");
					String version = datas.getString("version");
					final String url = datas.getString("url");
					float APPVersion = Float.valueOf(getVersionCode(activity));
					if (APPVersion != Float.valueOf(version)) {
						AlertDialog.Builder ad = new AlertDialog.Builder(activity);
						ad.setTitle(APPSingleton.getContext().getString(R.string.tip).toString());// 设置对话框标题
						String recent_version = APPSingleton.getContext().getString(R.string.recent_version).toString();
						ad.setItems(new String[] { recent_version + getVersionCode(activity), recent_version + version }, null);
						ad.setNegativeButton(APPSingleton.getContext().getString(R.string.cancel).toString(), null);
						ad.setPositiveButton(APPSingleton.getContext().getString(R.string.confirm).toString(), new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								downloadApk(url);
							}
						});
						ad.show();
					} else {
						if (hint == true) {
							AlertDialog.Builder ad = new AlertDialog.Builder(activity);
							ad.setTitle(APPSingleton.getContext().getString(R.string.tip).toString());// 设置对话框标题
							ad.setItems(new String[] { APPSingleton.getContext().getString(R.string.the_latest_version).toString() }, null);
							ad.setPositiveButton(APPSingleton.getContext().getString(R.string.confirm).toString(), null);
							ad.show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				mdialog.dismiss();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				mdialog.dismiss();
				CommonTools.showShortToast(activity, APPSingleton.getContext().getString(R.string.act_net_error).toString());
			}

			@Override
			public void onStart() {
				mdialog.show();
				super.onStart();
			}
		});
	}

	/**
	 * 下载APP
	 * 
	 * @param apkUrl
	 */
	private void downloadApk(String apkUrl) {
		CompleteReceiver completeReceiver = new CompleteReceiver();
		activity.registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		DownloadManager downloadManager = (DownloadManager) activity.getSystemService(activity.DOWNLOAD_SERVICE);
		String dir = isFolderExist(activity.getString(R.string.app_name)+"-A");
		completeReceiver.save_path = dir + "/"+activity.getString(R.string.app_name)+"-A.apk";
		File f = new File(dir + "/"+activity.getString(R.string.app_name)+"-A.apk");
		if (f.exists())
			f.delete();
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
		request.setDestinationInExternalPublicDir("aite", activity.getString(R.string.app_name)+"-A.apk");
		request.allowScanningByMediaScanner();// 表示允许MediaScanner扫描到这个文件，默认不允许。
		request.setTitle(APPSingleton.getContext().getString(R.string.program_update).toString());// 设置下载中通知栏提示的标题
		request.setDescription(APPSingleton.getContext().getString(R.string.downloading) + dir);// 设置下载中通知栏提示的介绍
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		@SuppressWarnings("unused")
		long downloadId = downloadManager.enqueue(request);
	}

	/**
	 * 文件下载通知
	 * 
	 * @author CAOYOU
	 * 
	 */
	class CompleteReceiver extends BroadcastReceiver {
		public String save_path = "";

		@Override
		public void onReceive(Context context, Intent intent) {
			downComplete(save_path);
		}

		private void downComplete(String filePath) {
			System.out.println("filePath : " + filePath);
			File _file = new File(filePath);
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");// 向用户显示数据
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 以新压入栈
			intent.addCategory("android.intent.category.DEFAULT");
			// intent.setType("application/vnd.android.package-archive");
			// intent.setData(Uri.fromFile(file));
			Uri abc = Uri.fromFile(_file);
			intent.setDataAndType(abc, "application/vnd.android.package-archive");
			activity.startActivity(intent);
		}

	};

	private String isFolderExist(String dir) {
		File folder = Environment.getExternalStoragePublicDirectory(dir);
		@SuppressWarnings("unused")
		boolean rs = (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
		return folder.getAbsolutePath();
	}

	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	// 版本号
	public static float getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;

		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pi;
	}
}
