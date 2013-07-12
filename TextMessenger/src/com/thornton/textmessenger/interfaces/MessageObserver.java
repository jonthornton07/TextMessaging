package com.thornton.textmessenger.interfaces;

import com.thornton.textmessenger.database.bo.Message;

public interface MessageObserver {

	public void sendMessage(final Message message);

	public void storeMessage(final Message message);

	public void receiveNewMessage(final Message mesage);

}
