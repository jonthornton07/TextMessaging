package com.thornton.textmessenger.database.bo;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * POPJO class for holding contact information for the application.
 * This holds only the information that is needed to make the application
 * function properly and nothing more.
 * 
 * @author jthornton
 *
 */
public class Contact {

	/**Unique contact id*/
	private String id;

	/**Display name of the contact*/
	private String displayName;

	/**Contact phone number*/
	private String phoneNumber;

	/**URI to get the thumbnail of the contact*/
	private Uri thumb;

	/**
	 * Constructor for the contact
	 * @param id - id of the contact
	 * @param displayName - display name of the contact
	 * @param phoneNumber - phone number of the contact
	 * @param thumb - thumbnail URI for the contact
	 */
	public Contact(final String id, final String displayName, final String phoneNumber, final Uri thumb) {
		setId(id);
		setDisplayName(displayName);
		setPhoneNumber(phoneNumber);
		setThumb(thumb);
	}

	public Contact(final Cursor cursor){
		if(cursor.moveToFirst()){
			setDisplayName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
			setId(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)));
			setPhoneNumber(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			final String uri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
			if(null != uri){
				setThumb(Uri.parse(uri));
			}else{
				setThumb(null);
			}
		}
		cursor.close();
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
