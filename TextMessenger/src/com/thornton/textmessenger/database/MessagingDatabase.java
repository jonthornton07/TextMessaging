package com.thornton.textmessenger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MessagingDatabase extends SQLiteOpenHelper{

	private static final String TAG = MessagingDatabase.class.getSimpleName();
	private SQLiteDatabase database;

	public MessagingDatabase(final Context context, final String name,
			final CursorFactory factory, final int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		database = db;
		database.execSQL(MessageTable.TABLE_CREATE_SQL);
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL(MessageTable.TABLE_DROP_SQL);
		onCreate(db);
	}

}
