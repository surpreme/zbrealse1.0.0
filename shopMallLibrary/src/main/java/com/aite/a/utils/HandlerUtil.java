package com.aite.a.utils;

import android.os.Handler;
import android.os.Message;

public class HandlerUtil {
	static HandlerUtil handlerUtil;

	public static HandlerUtil Init() {
		if (handlerUtil != null)
			return new HandlerUtil();
		return handlerUtil;
	}

	public void postMessage(int what, int arg1, int arg2, Object obj,
			Handler handler) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.arg1 = arg1;
		msg.arg2 = arg2;
		msg.obj = obj;
		handler.handleMessage(msg);
	}

	public void postMessage(int what, int arg2, Object obj, Handler handler) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.arg2 = arg2;
		msg.obj = obj;
		handler.handleMessage(msg);
	}

	public void postMessage(int what, Object obj, Handler handler) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.obj = obj;
		handler.handleMessage(msg);
	}

	public void postMessage(Object obj, Handler handler) {
		Message msg = handler.obtainMessage();
		msg.obj = obj;
		handler.handleMessage(msg);
	}

	public void postMessage(int what, Handler handler) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		handler.handleMessage(msg);
	}

}
