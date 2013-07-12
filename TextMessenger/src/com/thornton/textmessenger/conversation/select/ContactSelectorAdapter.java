package com.thornton.textmessenger.conversation.select;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thornton.textmessenger.R;
import com.thornton.textmessenger.database.bo.Contact;
import com.thornton.textmessenger.database.bo.Conversation;
import com.thornton.textmessenger.interfaces.ConversationObservable;

public class ContactSelectorAdapter extends ArrayAdapter<Contact> {

	private final Context context;

	private final List<Contact> contacts;

	private final ConversationObservable observerable;

	public ContactSelectorAdapter(final Context context,
			final int textViewResourceId, final List<Contact> contacts, final ConversationObservable observerable) {
		super(context, textViewResourceId, contacts);
		this.context = context;
		this.contacts = contacts;
		this.observerable = observerable;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.new_contact_list_item, parent, false);
		final ImageView contactImage = (ImageView) rowView.findViewById(R.id.contact_image);
		final TextView textView = (TextView) rowView.findViewById(R.id.display_name);
		final ImageView imageView = (ImageView) rowView.findViewById(R.id.more);
		contactImage.setImageURI(contacts.get(position).getThumb());
		textView.setText(contacts.get(position).getDisplayName());
		imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub

			}

		});

		textView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(final View v) {
				final Contact contact = getItem(position);
				final Conversation conversation = new Conversation(contact);
				observerable.notifyObserver(conversation);
			}
		});

		return rowView;
	}
}
