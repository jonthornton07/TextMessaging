package com.thornton.textmessenger.receiver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class DeliveryReceiver extends BaseReceiver {

	@Override
	public void onReceive(final Context context, final Intent intent) {

		//TODO:  Show some image for this or something
		switch(getResultCode()){
		case Activity.RESULT_OK:
			makeToast(context, DELIVERED);
			break;
		case Activity.RESULT_CANCELED:
			makeToast(context, "Not delivered");
			break;
		}
	}
}
