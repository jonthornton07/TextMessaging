package com.thornton.textmessenger.conversation;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

public class ContactSelectorFragment extends ListFragment
implements LoaderCallbacks<Cursor> {

	// This is the Adapter being used to display the list's data
	SimpleCursorAdapter mAdapter;

	// These are the Contacts rows that we will retrieve
	static final String[] PROJECTION = new String[] {ContactsContract.Data._ID,
		ContactsContract.Data.DISPLAY_NAME};

	// This is the select criteria
	static final String SELECTION = "((" +
			ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
			ContactsContract.Data.DISPLAY_NAME + " != '' ))";

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create a progress bar to display while the list loads

		// For the cursor adapter, specify which columns go into which views
		final String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
		final int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1

		// Create an empty adapter we will use to display the loaded data.
		// We pass null for the cursor, then update it in onLoadFinished()
		mAdapter = new SimpleCursorAdapter(this.getActivity(),
				android.R.layout.simple_list_item_1, null,
				fromColumns, toViews, 0);
		setListAdapter(mAdapter);

		// Prepare the loader.  Either re-connect with an existing one,
		// or start a new one.
		this.getActivity().getSupportLoaderManager().initLoader(0, null, this);
	}

	// Called when a new Loader needs to be created
	@Override
	public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
		// Now create and return a CursorLoader that will take care of
		// creating a Cursor for the data being displayed.
		return new CursorLoader(this.getActivity(), ContactsContract.Data.CONTENT_URI,
				PROJECTION, SELECTION, null, null);
	}

	// Called when a previously created loader has finished loading
	@Override
	public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
		// Swap the new cursor in.  (The framework will take care of closing the
		// old cursor once we return.)
		mAdapter.swapCursor(data);
	}

	// Called when a previously created loader is reset, making the data unavailable
	@Override
	public void onLoaderReset(final Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be closed.  We need to make sure we are no
		// longer using it.
		mAdapter.swapCursor(null);
	}

	@Override
	public void onListItemClick(final ListView l, final View v, final int position, final long id) {
		// Do something when a list item is clicked
	}
}