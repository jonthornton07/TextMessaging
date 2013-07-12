package com.thornton.textmessenger.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content provider for getting data from the message table in the application.
 * @author jthornton
 *
 */
public class MessageContentProvider extends ContentProvider{

	/**Private database to get the mesages from*/
	private MessagingDatabase database;

	// Used for the UriMacher
	private static final int MESSAGES = 10;
	private static final int MESSAGE_ID = 20;

	/**Authority for the content provider*/
	public static final String AUTHORITY = "de.thornton.android.message.messageContentProvider";

	/**The base path of the provider*/
	public static final String BASE_PATH = "messages";

	/**Content URI for the content provider*/
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	// Creates a UriMatcher object.
	private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		matcher.addURI(AUTHORITY, BASE_PATH, MESSAGES);
		matcher.addURI(AUTHORITY, BASE_PATH + "/#", MESSAGE_ID);
	}

	@Override
	public boolean onCreate() {
		database = new MessagingDatabase(getContext());
		return true;
	}


	@Override
	public Cursor query(final Uri uri, final String[] projection, final String selection,
			final String[] selectionArgs, final String sortOrder) {

		//Using SQLiteQueryBuilder instead of query() method
		final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

		//Set the table
		builder.setTables(MessageTable.TABLE_NAME);

		final int uriType = matcher.match(uri);
		switch(uriType){
		case MESSAGES:
			break;
		case MESSAGE_ID:
			builder.appendWhere(MessageTable.COLUMN_ID + "=" + uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		final SQLiteDatabase db = database.getWritableDatabase();
		final Cursor cursor = builder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);

		// Make sure that potential listeners are getting notified
		//cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}


	@Override
	public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
		final int uriType = matcher.match(uri);
		final SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case MESSAGES:
			rowsDeleted = sqlDB.delete(MessageTable.TABLE_NAME, selection,
					selectionArgs);
			break;
		case MESSAGE_ID:
			final String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(MessageTable.TABLE_NAME,
						MessageTable.COLUMN_ID + "=" + id,
						null);
			} else {
				rowsDeleted = sqlDB.delete(MessageTable.TABLE_NAME,
						MessageTable.COLUMN_ID + "=" + id
						+ " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(final Uri uri) {
		return null;
	}

	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		final int uriType = matcher.match(uri);
		final SQLiteDatabase sqlDB = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case MESSAGES:
			id = sqlDB.insert(MessageTable.TABLE_NAME, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int update(final Uri uri, final ContentValues values, final String selection,
			final String[] selectionArgs) {
		final int uriType = matcher.match(uri);
		final SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case MESSAGES:
			rowsUpdated = sqlDB.update(MessageTable.TABLE_NAME,
					values,
					selection,
					selectionArgs);
			break;
		case MESSAGE_ID:
			final String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(MessageTable.TABLE_NAME,
						values,
						MessageTable.COLUMN_ID + "=" + id,
						null);
			} else {
				rowsUpdated = sqlDB.update(MessageTable.TABLE_NAME,
						values,
						MessageTable.COLUMN_ID + "=" + id
						+ " and "
						+ selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

}
