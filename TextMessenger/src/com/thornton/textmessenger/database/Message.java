package com.thornton.textmessenger.database;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = -7815235102869833111L;
	private int id;
	private String phoneNumber;
	private String text;
	private String time;

	public Message(final int id, final String phoneNumber, final String text, final String time) {
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.text = text;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(final String time) {
		this.time = time;
	}
}
