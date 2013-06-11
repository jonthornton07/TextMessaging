package com.thornton.textmessenger.conversation;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thornton.textmessenger.R;
import com.thornton.textmessenger.database.Conversation;

public class ConversationFragment extends Fragment implements LoaderCallbacks<Cursor>{

	private Conversation conversation;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		//TODO: Remove this, this is just for stubbing stuff out
		final View view = inflater.inflate(R.layout.conversation_fragment, null);

		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(final int arg0, final Bundle arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onLoadFinished(final Loader<Cursor> arg0, final Cursor arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoaderReset(final Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	public void setContact(final Contact contact){
		if(conversation == null){
			conversation = new Conversation(contact);
		}
		//TOOD: Load conversations for that contact
	}
}
