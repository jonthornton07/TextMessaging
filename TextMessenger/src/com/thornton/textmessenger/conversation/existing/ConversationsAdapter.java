package com.thornton.textmessenger.conversation.existing;

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
import com.thornton.textmessenger.database.bo.Message;
import com.thornton.textmessenger.interfaces.ConversationObservable;

public class ConversationsAdapter extends ArrayAdapter<Conversation> {

	private final Context context;

	private List<Conversation> conversations;

	private final ConversationObservable observerable;

	public ConversationsAdapter(final Context context,
			final int textViewResourceId, final List<Conversation> contacts, final ConversationObservable observerable) {
		super(context, textViewResourceId, contacts);
		this.context = context;
		conversations = contacts;
		this.observerable = observerable;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final Conversation conversation = conversations.get(position);
		final List<Message> messages = conversation.getMessages();
		final View rowView = inflater.inflate(R.layout.current_conversation_item, parent, false);
		final ImageView contactImage = (ImageView) rowView.findViewById(R.id.contact_image);
		final TextView textView = (TextView) rowView.findViewById(R.id.display_name);
		rowView.findViewById(R.id.more);
		final TextView lastMessage = (TextView) rowView.findViewById(R.id.last_message);

		contactImage.setImageURI(conversation.getContact().getThumb());

		if(null == conversation.getContact().getDisplayName()){
			textView.setText(conversation.getMessages().get(0).getPhoneNumber());
		}else{
			textView.setText(conversation.getContact().getDisplayName());
		}
		lastMessage.setText(messages.get(messages.size()-1).getText());
		rowView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				observerable.notifyObserver(conversation);
			}

		});

		textView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(final View v) {
				final Contact contact = getItem(position).getContact();
				final Conversation conversation = new Conversation(contact);
				observerable.notifyObserver(conversation);
			}
		});

		return rowView;
	}

	public void setData(final List<Conversation> conversations){
		this.conversations = conversations;
	}
}
