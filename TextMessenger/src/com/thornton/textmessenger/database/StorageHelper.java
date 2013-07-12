package com.thornton.textmessenger.database;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.thornton.textmessenger.Logger;
import com.thornton.textmessenger.database.bo.Message;

public final class StorageHelper {

	private StorageHelper(){
		Logger.log("Storage Helper cannot be instantiated!!!");
	}

	public static void insertMessage(final Context context, final Message message){
		final Uri uri = Uri.parse(MessageContentProvider.CONTENT_URI + "/" );
		final ContentValues values = new ContentValues();
		values.put(MessageTable.COLUMN_MESSAGE_TIME, message.getTime());
		values.put(MessageTable.COLUMN_MESSAGE_TEXT, message.getText());
		values.put(MessageTable.COLUMN_PHONE_NUMBER, message.getPhoneNumber());
		values.put(MessageTable.COLUMN_TO_ME, message.getSender());
		values.put(MessageTable.COLUMN_READ, message.getRead());
		context.getContentResolver().insert(uri, values);
	}

}
