package com.thornton.textmessenger.conversation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thornton.textmessenger.R;
import com.thornton.textmessenger.database.Conversation;
import com.thornton.textmessenger.database.Message;

public class ConversationsAdapter extends ArrayAdapter<Message>{

	private final Context context;
	private final Conversation conversation;

	public ConversationsAdapter(final Context context, final int resource,final Conversation conversation) {
		super(context, resource, conversation.getMessages());
		this.context = context;
		this.conversation = conversation;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(R.layout.conversation_bubble, parent, false);
		final TextView text = (TextView) rowView.findViewById(R.id.message_text);
		final TextView time = (TextView) rowView.findViewById(R.id.message_time);

		text.setText(conversation.getMessages().get(position).getText());
		time.setText(conversation.getMessages().get(position).getTime());

		return rowView;
	}

}
