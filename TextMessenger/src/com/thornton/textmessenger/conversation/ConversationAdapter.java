package com.thornton.textmessenger.conversation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thornton.textmessenger.R;
import com.thornton.textmessenger.database.bo.Message;

public class ConversationAdapter extends ArrayAdapter<List<Message>>{

	/**List of message being displayed*/
	private List<Message> messages;

	private static String TIME_FORMAT = "EEE, MMM d yyyy HH:mm aaa";

	public ConversationAdapter(final Context context, final List<Message> messages) {
		super(context, R.id.message_text);
		this.messages = messages;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final Message message = messages.get(position);
		final int layout = message.getSender() == Message.FROM_ME ? R.layout.conversation_bubble_from : R.layout.conversation_bubble_to;

		final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View rowView = inflater.inflate(layout, parent, false);
		final TextView messageText = (TextView) rowView.findViewById(R.id.message_text);
		final TextView messageTime = (TextView) rowView.findViewById(R.id.message_time);
		messageText.setText(message.getText());

		//TODO: Look into fixing this
		final SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
		messageTime.setText(format.format(new Date(Long.parseLong(message.getTime()))).toString());

		return rowView;
	}

	@Override
	public int getCount(){
		return messages.size();
	}


	public void setMessages(final List<Message> messages){
		this.messages = messages;
	}

	public void addMessage(final Message message){
		messages.add(message);
		notifyDataSetChanged();
	}
}
