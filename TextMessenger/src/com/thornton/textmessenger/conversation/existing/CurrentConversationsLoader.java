package com.thornton.textmessenger.conversation.existing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.AsyncTaskLoader;

import com.thornton.textmessenger.database.MessageContentProvider;
import com.thornton.textmessenger.database.MessageTable;
import com.thornton.textmessenger.database.bo.Contact;
import com.thornton.textmessenger.database.bo.Conversation;
import com.thornton.textmessenger.database.bo.Message;

public class CurrentConversationsLoader extends AsyncTaskLoader<List<Conversation>>{

	// TODO: Maybe use the photo URI to get the photo
	private static final String[] PROJECTION = new String[] {
		ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.NUMBER,
		ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ,
		ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI};

	public CurrentConversationsLoader(final Context context) {
		super(context);
	}

	@Override
	public List<Conversation> loadInBackground() {
		final List<Message> messages = loadMessages();
		Collections.sort(messages);

		return loadConversationsFromMessages(messages);
	}

	private List<Message> loadMessages() {
		final Uri uri = Uri.parse(MessageContentProvider.CONTENT_URI + "/" );
		final Cursor cursor = getContext().getContentResolver().query(uri, MessageTable.ALL_PROJECTION, null, null, MessageTable.COLUMN_PHONE_NUMBER);
		return Message.getMessagesFromCursor(cursor);
	}

	private List<Conversation> loadConversationsFromMessages(final List<Message> messages){
		final List<Conversation> conversation = new ArrayList<Conversation>();
		List<Message> currentMessages = null;
		String currentNumber = "";
		String messageNumber = "";
		for(final Message message : messages){
			messageNumber = message.getPhoneNumber();
			if(messageNumber.length() == 11){
				messageNumber = messageNumber.substring(1, 11);
			}
			if(currentNumber.equals("")){
				currentNumber = messageNumber;
				currentMessages = new ArrayList<Message>();
				currentMessages.add(message);
			}else if(currentNumber.equals(messageNumber)){
				currentMessages.add(message);
			}else{
				conversation.add(loadConversationFromMessage(currentMessages));
				currentNumber = messageNumber;
				currentMessages = new ArrayList<Message>();
				currentMessages.add(message);
			}

		}
		if(!currentMessages.isEmpty()){
			conversation.add(loadConversationFromMessage(currentMessages));
		}
		return conversation;
	}

	private Conversation loadConversationFromMessage(final List<Message> messages){

		final String selection = "(" + ContactsContract.CommonDataKinds.Phone.NUMBER + " like '%" + messages.get(0).getPhoneNumber() + "')";
		final Cursor cursor = getContext().getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				PROJECTION,
				selection,
				null,
				null);

		final Contact contact = new Contact(cursor);
		return new Conversation(contact, new ArrayList<Message>(messages));
	}

}
