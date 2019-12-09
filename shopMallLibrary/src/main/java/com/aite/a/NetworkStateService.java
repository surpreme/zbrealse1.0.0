package com.aite.a;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import com.aiteshangcheng.a.R;

/**
 * 网络监控服务
 * 
 * @author CAOYOU
 * 
 */
public class NetworkStateService extends Service {

	private static final String tag = "tag";
	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	private static Reboot reboot;
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				Log.d(tag, APPSingleton.getContext().getString(R.string.network_changet).toString());
				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					AppManager.getInstance().killActivity(NetworkDialog.class);
					// if (reboot != null)
					// reboot.ReGetData();
				} else {
					Intent i = new Intent();
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.setClass(context, NetworkDialog.class);
					context.startActivity(i);
				}
			}
		}
	};

	public interface Reboot {
		public void ReGetData();
	}

	public static void setReboot(Reboot reboot) {
		NetworkStateService.reboot = reboot;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		AppManager.getInstance().addService(this);
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

}
