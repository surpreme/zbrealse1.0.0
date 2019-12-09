package com.qq.xgdemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.aite.a.APPSingleton;
import com.aiteshangcheng.a.R;
import com.aite.a.activity.PushActivity;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

public class MessageReceiver extends XGPushBaseReceiver {
	private Intent intent = new Intent("com.qq.xgdemo.activity.UPDATE_LISTVIEW");
	public static final String LogTag = "TPushReceiver";

	private void show(Context context, String text) {
		// Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	// 通知展示
	@Override
	public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
		if (context == null || notifiShowedRlt == null) {
			return;
		}
		XGNotification notific = new XGNotification();
		notific.setMsg_id(notifiShowedRlt.getMsgId());
		notific.setTitle(notifiShowedRlt.getTitle());
		notific.setContent(notifiShowedRlt.getContent());
		// notificationActionType==1为Activity，2为url，3为intent
		notific.setNotificationActionType(notifiShowedRlt.getNotificationActionType());
		// Activity,url,intent都可以通过getActivity()获得
		notific.setActivity(notifiShowedRlt.getActivity());
		notific.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
		NotificationService.getInstance(context).save(notific);
		context.sendBroadcast(intent);
		show(context, APPSingleton.getContext().getString(R.string.have_one_message).toString() + APPSingleton.getContext().getString(R.string.notice_show).toString() + notifiShowedRlt.toString());
	}

	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = APPSingleton.getContext().getString(R.string.registration_success).toString();
		} else {
			text = APPSingleton.getContext().getString(R.string.registration_fail).toString() + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"" + APPSingleton.getContext().getString(R.string.set_success).toString();
		} else {
			text = "\"" + tagName + "\"" + APPSingleton.getContext().getString(R.string.set_fail_code).toString() + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}
		String text = "";
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"" + APPSingleton.getContext().getString(R.string.act_del_success).toString();
		} else {
			text = "\"" + tagName + "\"" + APPSingleton.getContext().getString(R.string.delete_fail_code).toString() + errorCode;
		}
		Log.d(LogTag, text);
		show(context, text);

	}

	// 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
	@Override
	public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}
		String text = "";
		if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
			// 通知在通知栏被点击啦。。。。。
			// APP自己处理点击的相关动作
			// 这个动作可以在activity的onResume也能监听，请看第3点相关内容
			text = APPSingleton.getContext().getString(R.string.notice_is_opened).toString() + message;
			Intent intent = new Intent();
			intent.setClass(context.getApplicationContext(), PushActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.getApplicationContext().startActivity(intent);
		} else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
			// 通知被清除啦。。。。
			// APP自己处理通知被清除后的相关动作
			text = APPSingleton.getContext().getString(R.string.notice_is_cleaned).toString() + message;
		}
		// Toast.makeText(context, "广播接收到通知被点击:" + message.toString(),
		// Toast.LENGTH_SHORT).show();
		// 获取自定义key-value
		// String customContent = message.getCustomContent();
		// if (customContent != null && customContent.length() != 0) {
		// try {
		// JSONObject obj = new JSONObject(customContent);
		// // key1为前台配置的key
		// if (!obj.isNull("key")) {
		// String value = obj.getString("key");
		// Log.d(LogTag, "get custom value:" + value);
		// }
		// // ...
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// APP自主处理的过程。。。
		// Log.d(LogTag, text);
		// show(context, text);

	}

	@Override
	public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult message) {
		// TODO Auto-generated method stub
		// if (context == null || message == null) {
		// return;
		// }
		// String text = "";
		// if (errorCode == XGPushBaseReceiver.SUCCESS) {
		// text = message + "注册成功";
		// // 在这里拿token
		// String token = message.getToken();
		// } else {
		// text = message + "注册失败，错误码：" + errorCode;
		// }
		// Log.d(LogTag, text);
		// show(context, text);
	}

	// 消息透传
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		// TODO Auto-generated method stub
		String text = APPSingleton.getContext().getString(R.string.receive_message).toString() + message.toString();
		// 获取自定义key-value
		String customContent = message.getCustomContent();
		if (customContent != null && customContent.length() != 0) {
			try {
				JSONObject obj = new JSONObject(customContent);
				// key1为前台配置的key
				if (!obj.isNull("key")) {
					String value = obj.getString("key");
					Log.d(LogTag, "get custom value:" + value);
				}
				// ...
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// APP自主处理消息的过程...
		Log.d(LogTag, text);
		show(context, text);
	}

}
