package com.thornton.textmessenger.interfaces;

import com.thornton.textmessenger.database.bo.Message;

public interface MessageObserverable {

	public void receiveNewMessage(final Message message);

	public void setMessageObserver(final MessageObserver observer);
}
