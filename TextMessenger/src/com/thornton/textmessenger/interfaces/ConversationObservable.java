package com.thornton.textmessenger.interfaces;

import com.thornton.textmessenger.database.bo.Conversation;

public interface ConversationObservable {

	public void notifyObserver(Conversation conversation);

	public void setObserver(ConversationObserver observer);
}
