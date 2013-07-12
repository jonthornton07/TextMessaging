package com.thornton.textmessenger.receiver;

import android.content.Context;
import android.content.Intent;

import com.thornton.textmessenger.MessageActivity;
import com.thornton.textmessenger.database.bo.Message;

public class IntentReceiver extends BaseReceiver{

	private final MessageActivity activity;

	public IntentReceiver(final MessageActivity activity){
		this.activity = activity;
	}

	@Override
	public void onReceive(final Context context, final Intent intent) {
		activity.receiveNewMessage((Message) intent.getSerializableExtra(MESSAGE));
	}
}
