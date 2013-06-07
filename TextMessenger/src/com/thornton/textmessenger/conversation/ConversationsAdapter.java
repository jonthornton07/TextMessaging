package com.thornton.textmessenger.conversation;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ConversationsAdapter extends ArrayAdapter<Contact>{

	public ConversationsAdapter(final Context context, final int resource,
			final int textViewResourceId, final List<Contact> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO: Do stuff here
	}

}
