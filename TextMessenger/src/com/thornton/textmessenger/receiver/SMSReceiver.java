package com.thornton.textmessenger.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;

import com.thornton.textmessenger.MessageActivity;
import com.thornton.textmessenger.R;
import com.thornton.textmessenger.database.StorageHelper;
import com.thornton.textmessenger.database.bo.Message;

public class SMSReceiver extends BaseReceiver{

	@Override
	public void onReceive(final Context context, final Intent intent){
		final Bundle bundle = intent.getExtras();
		if(null == bundle){return;}

		final Message message = extractMessage(context, bundle);
		StorageHelper.insertMessage(context, message);
		alertNewMessage(context, message);
		createNotification(context, message);
		//abortBroadcast();
	}

	private void createNotification(final Context context, final Message message) {
		final Intent intent = new Intent(context, MessageActivity.class);
		final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

		final Notification notification = new NotificationCompat.Builder(context)
		.setContentTitle("Message From: " + message.getPhoneNumber())
		.setContentText(message.getText())
		.setContentIntent(pendingIntent)
		.setSmallIcon(R.drawable.ic_contact_picture)
		.build();

		final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		notification.flags |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		manager.notify(0, notification);
	}

	private Message extractMessage(final Context context, final Bundle bundle) {
		final Object[] pdus = (Object[]) bundle.get("pdus");
		final SmsMessage[] smsMessage = new SmsMessage[pdus.length];

		String number = "";
		String text = "";
		String time = "";

		for(int i = 0; i < smsMessage.length; i++){
			smsMessage[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			if(i == 0){
				number = smsMessage[i].getOriginatingAddress();
			}

			text += smsMessage[i].getMessageBody().toString();
			time = String.valueOf(smsMessage[i].getTimestampMillis());
		}
		return new Message(0, number, text, time, Message.TO_ME, Message.NOT_READ);
	}

	private void alertNewMessage(final Context context, final Message message) {
		final Intent i = new Intent();
		i.setAction(SMS_RECEIVED_ACTION);
		i.putExtra(MESSAGE, message);
		context.sendBroadcast(i);
	}
}
