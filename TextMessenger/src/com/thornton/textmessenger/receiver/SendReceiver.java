package com.thornton.textmessenger.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SendReceiver extends BaseReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {
		switch(getResultCode()){
		case Activity.RESULT_OK:
			makeToast(context, SENT);
			break;
		case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
			makeToast(context, "Generic Failure");
			break;
		case SmsManager.RESULT_ERROR_NO_SERVICE:
			makeToast(context, "No Service!");
			break;
		case SmsManager.RESULT_ERROR_NULL_PDU:
			makeToast(context, "Null PDU");
			break;
		case SmsManager.RESULT_ERROR_RADIO_OFF:
			makeToast(context, "Radio off");
			break;
		}
	}
}
