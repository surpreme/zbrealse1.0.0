package com.aite.a.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.aite.a.APPSingleton;
import com.aiteshangcheng.a.R;

public class MyTimeUtil {
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	private int recLen = 11;
	private long hours;
	private long minutes;
	private long seconds;
	private TextView txtView;
	private long diff;
	private long days;

	public void setTime(TextView txtView, String time) {
		this.txtView = txtView;
		getTime(time);
		Message message = handler.obtainMessage(1); // Message
		handler.sendMessageDelayed(message, 1000);
	}

	public void setTimestamp(TextView txtView, long Time) {
		this.txtView = txtView;
		String date = DEFAULT_DATE_FORMAT.format(new Date(Time * 1000));
		getTime(date);
		Message message = handler.obtainMessage(1); // Message
		handler.sendMessageDelayed(message, 1000);
	}

	/* 倒计时的主要代码块 */

	public static boolean getTime(long time, TextView jl1, TextView jl2, TextView jl3) {
		long hour = 0;
		long minute = 0;
		long seconds = 0;
		while (time >= 0) {
			hour = time / 3600;
			minute = (time - hour * 3600) / 60;
			seconds = time - hour * 3600 - minute * 60;
			if (jl1 != null)
				jl1.setText(hour + APPSingleton.getContext().getString(R.string.hour).toString());
			if (jl2 != null)
				jl2.setText(minute + APPSingleton.getContext().getString(R.string.minus).toString());
			if (jl3 != null)
				jl3.setText(seconds + APPSingleton.getContext().getString(R.string.seconds).toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			time--;
			if (time == 0) {
				return true;
			}
		}
		return false;
	}

	public final Handler handler = new Handler() {
		public void handleMessage(Message msg) { // handle message
			switch (msg.what) {
			case 1:
				diff = diff + 1000;
				getShowTime();
				Message message = handler.obtainMessage(1);
				handler.sendMessageDelayed(message, 1000);
				break;
			}
			super.handleMessage(msg);
		}

	};

	/**
	 * 得到时间差
	 */
	private void getTime(String time) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = DEFAULT_DATE_FORMAT.format(new Date());
		System.out.println(APPSingleton.getContext().getString(R.string.now_time).toString() + date);
		try {
			Date d1 = df.parse(time);
			Date d2 = df.parse(date);
			diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
			days = diff / (1000 * 60 * 60 * 24);
			hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
			minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
			seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
			String day = APPSingleton.getContext().getString(R.string.day).toString();
			String hour = APPSingleton.getContext().getString(R.string.hour).toString();
			String minus = APPSingleton.getContext().getString(R.string.minus).toString();
			String seconds = APPSingleton.getContext().getString(R.string.seconds).toString();
			txtView.setText("" + days + day + hours + hour + minutes + minus + seconds + seconds);
			System.out.println(APPSingleton.getContext().getString(R.string.now_time).toString() + "diff " + diff);
			System.out.println("" + days + day + hours + hour + minutes + minus + seconds + seconds);
		} catch (Exception e) {
		}
	}

	/**
	 * 获得要显示的时间
	 */
	private void getShowTime() {
		days = diff / (1000 * 60 * 60 * 24);
		hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
		seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
		String day = APPSingleton.getContext().getString(R.string.day).toString();
		String hour = APPSingleton.getContext().getString(R.string.hour).toString();
		String minus = APPSingleton.getContext().getString(R.string.minus).toString();
		String seconds = APPSingleton.getContext().getString(R.string.seconds).toString();
		txtView.setText("" + days + day + hours + hour + minutes + minus + seconds + seconds);
	}

	/**
	 * long time to string
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 * @return
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**
	 * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**
	 * get current time in milliseconds
	 * 
	 * @return
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}

}
