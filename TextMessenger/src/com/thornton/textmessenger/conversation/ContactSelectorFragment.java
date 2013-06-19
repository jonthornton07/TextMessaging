package com.thornton.textmessenger.conversation;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.thornton.textmessenger.R;

public class ContactSelectorFragment extends ListFragment implements
LoaderCallbacks<Cursor>, ConversationObservable {

	private static final String TAG = ContactSelectorFragment.class.getSimpleName();

	// This is the Adapter being used to display the list's data
	ContactSelectorAdapter mAdapter;

	ConversationObserver observer;

	// These are the Contacts rows that we will retrieve

	// TODO: Maybe use the photo URI to get the photo
	static final String[] PROJECTION = new String[] {
		ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.NUMBER,
		ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ,
		ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI};

	// This is the select criteria
	//	static final String SELECTION = "((" + ContactsContract.Data.DISPLAY_NAME
	//			+ " NOTNULL) AND (" + ContactsContract.Data.DISPLAY_NAME
	//			+ " != '' ))";

	static final String SELECTION = "(" + ContactsContract.CommonDataKinds.Phone.NUMBER
			+ " != '') AND (" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
			+ " != '' )";

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getActivity().getSupportLoaderManager().initLoader(0, null, this);
	}

	// Called when a new Loader needs to be created
	@Override
	public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.
		return new CursorLoader(getActivity(),
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, SELECTION, null,
				ContactsContract.CommonDataKinds.Phone.TIMES_CONTACTED + " DESC");
	}

	// Called when a previously created loader has finished loading
	@Override
	public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
		// Swap the new cursor in. (The framework will take care of closing the
		// old cursor once we return.)


		// Create an empty adapter we will use to display the loaded data.
		// We pass null for the cursor, then update it in onLoadFinished()
		mAdapter = new ContactSelectorAdapter(getActivity(), R.layout.new_contact_list_item, convertToContacts(data));
		setListAdapter(mAdapter);
	}

	private List<Contact> convertToContacts(final Cursor data) {
		final List<Contact> contacts = new ArrayList<Contact>();
		String id;
		String name;
		String number = "";
		String uriPath;
		Uri uri = null;
		Contact previous = null;
		while(data.moveToNext()){
			name = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			if(null != previous && previous.getDisplayName().equals(name)){
				continue;
			}
			id = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
			number = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			uriPath = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
			if(null != uriPath){
				uri = Uri.parse(uriPath);
			}else{
				uri = null;
			}
			final Contact contact = new Contact(id, name, number, uri);
			contacts.add(contact);
			previous = contact;
		}

		data.close();

		return contacts;
	}

	// Called when a previously created loader is reset, making the data
	// unavailable
	@Override
	public void onLoaderReset(final Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed. We need to make sure we are no
		// longer using it.
	}

	@Override
	public void onListItemClick(final ListView l, final View v,
			final int position, final long id) {
		// Do something when a list item is clicked
	}


	@Override
	public void notifyObserver(final Contact contact) {
		if(null != observer){
			observer.contactClicked(contact);
		}
		//TODO: Remove the else;
		else{
			Log.i(TAG, "No observer registered");
		}
	}

	@Override
	public void setObserver(final ConversationObserver observer) {
		this.observer = observer;
	}
}