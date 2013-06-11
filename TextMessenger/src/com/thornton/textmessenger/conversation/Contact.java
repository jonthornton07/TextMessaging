package com.thornton.textmessenger.conversation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;

public class Contact {

	private String id;
	private String displayName;
	private String phoneNumber;
	private Uri thumb;

	// TODO: Get the users image (not sure how to store this);

	public Contact(final String id, final String displayName, final String phoneNumber, final Uri thumb) {
		setId(id);
		setDisplayName(displayName);
		setPhoneNumber(phoneNumber);
		setThumb(thumb);
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	public InputStream openPhoto(final Context context) {
		final Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(getId()));
		final Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
		final Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] {Contacts.Photo.PHOTO}, null, null, null);
		if (cursor == null) {
			return null;
		}
		try {
			if (cursor.moveToFirst()) {
				final byte[] data = cursor.getBlob(0);
				if (data != null) {
					return new ByteArrayInputStream(data);
				}
			}
		} finally {
			cursor.close();
		}
		return null;
	}

	/**
	 * @return the thumb
	 */
	public Uri getThumb() {
		return thumb;
	}

	/**
	 * @param thumb the thumb to set
	 */
	public void setThumb(final Uri thumb) {
		this.thumb = thumb;
	}
}
