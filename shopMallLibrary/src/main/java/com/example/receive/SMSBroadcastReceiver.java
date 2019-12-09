package com.example.receive;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aite.a.utils.CommonTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.text.TextUtils;

/**
 * 短信监听
 * 
 * @author
 *
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

	private MessageListener mMessageListener;
	public final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	public SMSBroadcastReceiver() {
		super();
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
			Object[] objs = (Object[]) intent.getExtras().get("pdus");
			for (Object obj : objs) {
				byte[] pdu = (byte[]) obj;
				SmsMessage sms = SmsMessage.createFromPdu(pdu);
				// 短信的内容
				String message = sms.getMessageBody();
				String from = sms.getOriginatingAddress();
				if (!TextUtils.isEmpty(from)) {
					mMessageListener.onReceived(message);
				}
			}
		}

	}

	// 回调接口
	public interface MessageListener {
		public void onReceived(String message);
	}

	public void setOnReceivedMessageListener(MessageListener messageListener) {
		this.mMessageListener = messageListener;
	}
}
