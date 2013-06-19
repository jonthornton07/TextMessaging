package com.thornton.textmessenger.conversation;

public interface ConversationObservable {

	public void notifyObserver(Contact contact);

	public void setObserver(ConversationObserver observer);
}
