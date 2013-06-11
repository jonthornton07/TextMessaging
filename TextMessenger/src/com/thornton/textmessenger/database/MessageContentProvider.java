package com.thornton.textmessenger.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MessageContentProvider extends ContentProvider{

	@Override
	public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(final Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(final Uri uri, final ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(final Uri uri, final String[] projection, final String selection,
			final String[] selectionArgs, final String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(final Uri uri, final ContentValues values, final String selection,
			final String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
