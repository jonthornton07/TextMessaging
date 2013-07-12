package com.thornton.textmessenger.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.widget.Toast;

public abstract class BaseReceiver extends BroadcastReceiver{

	public static final String DELIVERED = "SMS Delivered";

	public static final String SMS = "sms";

	public static final String TIME = "time";

	public static final String MESSAGE = "message";

	public static final String SENT = "SMS Sent";

	public static final String SMS_RECEIVED_ACTION = "SMS_RECEIVED_ACTION";

	public static final String NUMBER = "number";

	protected void makeToast(final Context context, final String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
