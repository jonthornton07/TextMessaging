package com.thornton.textmessenger.interfaces;

import com.thornton.textmessenger.database.bo.Conversation;

public interface ConversationObserver{

	public void conversationStarted(final Conversation contact);
}
