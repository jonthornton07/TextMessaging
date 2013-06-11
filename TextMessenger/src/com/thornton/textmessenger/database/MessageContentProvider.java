package com.thornton.textmessenger.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MessageContentProvider extends ContentProvider{
	private static final String TAG = MessageContentProvider.class.getSimpleName();

	public static final String AUTHORITY = "com.thornton.textmessenger.database.MessageContentProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/messages");
	@Override
	public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
		// TODO: Implement Me
		return 0;
	}

	@Override
	public String getType(final Uri uri) {
		// TODO: Implement Me
		return null;
	}

	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		// TODO: Implement Me
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO: Implement Me
		return true;
	}

	@Override
	public Cursor query(final Uri uri, final String[] projection, final String selection,
			final String[] selectionArgs, final String sortOrder) {
		// TODO: Implement Me
		return null;
	}

	@Override
	public int update(final Uri uri, final ContentValues values, final String selection,
			final String[] selectionArgs) {
		// TODO: Implement Me
		return 0;
	}

}
