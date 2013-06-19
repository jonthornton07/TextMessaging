package com.thornton.textmessenger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database for the application. This gets created when the application is installed.
 * @author jthornton
 *
 */
public class MessagingDatabase extends SQLiteOpenHelper{

	/**Tag for the database*/
	private static final String TAG = MessagingDatabase.class.getSimpleName();

	/**SQLite Database being used by the application*/
	private SQLiteDatabase database;

	/**
	 * Constructor for the database
	 * @param context - activity context
	 * @param name - name of the database
	 * @param factory - cursor factory
	 * @param version - version of the database
	 */
	public MessagingDatabase(final Context context, final String name,
			final CursorFactory factory, final int version) {
		super(context, name, factory, version);
	}

	/**
	 * Called when the database is created.  This is where all the tables are created.
	 * @param db - database being created
	 */
	@Override
	public void onCreate(final SQLiteDatabase db) {
		database = db;
		database.execSQL(MessageTable.TABLE_CREATE_SQL);
	}

	/**
	 * Called when the database is being upgraded.
	 * This is where any data migration/table drops will happen.
	 * @param db - SQLite database being created
	 * @param oldVersion - old version of the database
	 * @param newVersion - new version of the database
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		db.execSQL(MessageTable.TABLE_DROP_SQL);
		onCreate(db);
	}

}
