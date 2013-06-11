package com.thornton.textmessenger.conversation;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.thornton.textmessenger.R;

public class ContactSelectorFragment extends ListFragment implements
LoaderCallbacks<Cursor> {

	// This is the Adapter being used to display the list's data
	ContactSelectorAdapter mAdapter;

	// These are the Contacts rows that we will retrieve

	// TODO: Maybe use the photo URI to get the photo
	static final String[] PROJECTION = new String[] {
		ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.HAS_PHONE_NUMBER };

	// This is the select criteria
	static final String SELECTION = "((" + ContactsContract.Data.DISPLAY_NAME
			+ " NOTNULL) AND (" + ContactsContract.Data.DISPLAY_NAME
			+ " != '' ))";

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
				ContactsContract.Data.CONTENT_URI, PROJECTION, SELECTION, null,
				null);
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
		while(data.moveToNext()){
			id = data.getString(data.getColumnIndex(ContactsContract.Contacts._ID));
			name = data.getString(data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			if (Integer.parseInt(data.getString(data.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
				final Cursor pCur = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);

				while (pCur.moveToNext()) {
					number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				}
				pCur.close();

			}
			contacts.add(new Contact(id, name, number));
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
}