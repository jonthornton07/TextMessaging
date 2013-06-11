package com.thornton.textmessenger.conversation;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.thornton.textmessenger.R;

public class ContactSelectorAdapter extends ArrayAdapter<Contact> {

	private final Context context;

	private final List<Contact> contacts;

	public ContactSelectorAdapter(final Context context,
			final int textViewResourceId, final List<Contact> contacts) {
		super(context, textViewResourceId, contacts);
		this.context = context;
		this.contacts = contacts;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.new_contact_list_item, parent, false);
		final QuickContactBadge contactImage = (QuickContactBadge) rowView.findViewById(R.id.contact_image);
		final TextView textView = (TextView) rowView.findViewById(R.id.display_name);
		final ImageView imageView = (ImageView) rowView.findViewById(R.id.more);
		contactImage.assignContactFromPhone(contacts.get(position).getPhoneNumber(), false);
		textView.setText(contacts.get(position).getDisplayName());
		imageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				// TODO Auto-generated method stub

			}

		});

		return rowView;
	}
}
