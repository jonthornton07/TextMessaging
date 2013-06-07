package com.thornton.textmessenger.conversation;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ContactSelectorAdapter extends ArrayAdapter<Contact> {

	public ContactSelectorAdapter(final Context context,
			final int textViewResourceId, final List<Contact> objects) {
		super(context, textViewResourceId, objects);
		// TODO Make the data here
	}

}
