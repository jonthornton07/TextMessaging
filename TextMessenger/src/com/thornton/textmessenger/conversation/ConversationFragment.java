package com.thornton.textmessenger.conversation;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.thornton.textmessenger.R;
import com.thornton.textmessenger.database.MessageContentProvider;
import com.thornton.textmessenger.database.MessageTable;
import com.thornton.textmessenger.database.bo.Conversation;
import com.thornton.textmessenger.database.bo.Message;
import com.thornton.textmessenger.interfaces.MessageObserver;
import com.thornton.textmessenger.interfaces.MessageObserverable;

public class ConversationFragment extends Fragment implements LoaderCallbacks<Cursor>, MessageObserverable{

	private Conversation conversation;

	private ConversationAdapter adapter;

	private ListView list;

	private Button send;

	private EditText messageBox;

	private MessageObserver observer;

	private Loader<Cursor> loader;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.conversation_fragment, null);
		list = (ListView) view.findViewById(R.id.message_list);
		send = (Button) view.findViewById(R.id.message_send);
		messageBox = (EditText) view.findViewById(R.id.message_text_box);

		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(final View v) {
				if(!TextUtils.isEmpty(messageBox.getText())){
					final String phoneNumber = conversation.getContact().getPhoneNumber();
					final String messageText = messageBox.getText().toString();
					final String time = String.valueOf(System.currentTimeMillis());
					final Message message = new Message(phoneNumber, messageText, time, Message.FROM_ME, Message.READ);
					if(null != observer){
						observer.sendMessage(message);
					}
					receiveNewMessage(message);
					messageBox.setText("");
				}

			}

		});


		return view;
	}

	@Override
	public void onResume(){
		super.onResume();
		if(null != conversation){
			loader = getActivity().getSupportLoaderManager().initLoader(2, null, this);
		}
	}

	public void setConversation(final Conversation conversation){
		this.conversation = conversation;
		if(null!= list){
			list.setAdapter(null);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(final int arg0, final Bundle arg1) {
		String phoneNumber = conversation.getContact().getPhoneNumber();

		if(phoneNumber.length() > 10){
			phoneNumber = phoneNumber.substring(1, 11);
		}
		return new CursorLoader(getActivity(),
				MessageContentProvider.CONTENT_URI, MessageTable.ALL_PROJECTION,
				MessageTable.COLUMN_PHONE_NUMBER + " = " + phoneNumber, null, null);
	}

	@Override
	public void onLoadFinished(final Loader<Cursor> arg0, final Cursor arg1) {
		final List<Message> messages = Message.getMessagesFromCursor(arg1);
		if(null == adapter){
			adapter = new ConversationAdapter(getActivity(), messages);
			list.setAdapter(adapter);
		}else{
			adapter.setMessages(messages);
			list.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(final Loader<Cursor> arg0) {

	}

	@Override
	public void receiveNewMessage(final Message message) {
		//If there is a conversation and the conversation numbers match add it
		if(null != conversation && conversation.getContact().getPhoneNumber().contains(message.getPhoneNumber())){
			conversation.getMessages().add(message);

			//If the adapter is already set up add the message
			if(null != adapter){
				adapter.addMessage(message);
				list.smoothScrollToPosition(adapter.getCount() - 1);
			}
		}
	}

	@Override
	public void setMessageObserver(final MessageObserver observer) {
		this.observer = observer;
	}
}
