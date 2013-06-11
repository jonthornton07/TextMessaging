package com.thornton.textmessenger.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thornton.textmessenger.conversation.Contact;

public class Conversation implements Serializable{

	private static final long serialVersionUID = 6503795028855813287L;

	private List<Message> messages;

	private Contact contact;

	public Conversation(final Contact contact){
		setContact(contact);
		setMessages(new ArrayList<Message>());
	}

	public Conversation(final Contact contact, final List<Message> messages){
		setContact(contact);
		setMessages(messages);
	}

	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(final List<Message> messages) {
		this.messages = messages;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(final Contact contact) {
		this.contact = contact;
	}

}
