package com.thornton.textmessenger.database.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.thornton.textmessenger.database.MessageTable;

public class Message implements Serializable, Comparable<Message>{

	private static final long serialVersionUID = -7815235102869833111L;
	private int id;
	private String phoneNumber;
	private String text;
	private String time;
	private int sender;
	private int read;

	public static final int FROM_ME = 0;

	public static final int TO_ME = 1;

	public static final int NOT_READ = 0;

	public static final int READ = 1;

	public Message(final int id, final String phoneNumber, final String text, final String time, final int sender, final int read) {
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.text = text;
		this.time = time;
		setRead(read);
		setSender(sender);
	}

	public Message(final String phoneNumber, final String text, final String time, final int sender, final int read) {
		this.phoneNumber = phoneNumber;
		this.text = text;
		this.time = time;
		setRead(read);
		setSender(sender);
	}

	public static List<Message> getMessagesFromCursor(final Cursor cursor){
		final List<Message> messages = new ArrayList<Message>();
		while(cursor.moveToNext()){
			final int id = cursor.getInt(cursor.getColumnIndex(MessageTable.COLUMN_ID));
			final String phoneNumber = cursor.getString(cursor.getColumnIndex(MessageTable.COLUMN_PHONE_NUMBER));
			final String text = cursor.getString(cursor.getColumnIndex(MessageTable.COLUMN_MESSAGE_TEXT));
			final String time = cursor.getString(cursor.getColumnIndex(MessageTable.COLUMN_MESSAGE_TIME));
			final int sender = cursor.getInt(cursor.getColumnIndex(MessageTable.COLUMN_TO_ME));
			final int read = cursor.getInt(cursor.getColumnIndex(MessageTable.COLUMN_READ));
			messages.add(new Message(id, phoneNumber, text, time, sender, read));
		}

		cursor.close();

		return messages;
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

	/**
	 * @return the sender
	 */
	public int getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(final int sender) {
		this.sender = sender;
	}

	/**
	 * @return the read
	 */
	public int getRead() {
		return read;
	}

	/**
	 * @param read the read to set
	 */
	public void setRead(final int read) {
		this.read = read;
	}

	@Override
	public int compareTo(final Message another) {
		String thisNumber = phoneNumber;
		String anotherNumber = another.phoneNumber;
		if(thisNumber.length() == 11){
			thisNumber = thisNumber.substring(1, 11);
		}
		if(anotherNumber.length() == 11){
			anotherNumber = anotherNumber.substring(1, 11);
		}
		final int numberCompare = thisNumber.compareTo(anotherNumber);
		if(numberCompare == 0){
			final int timeCompare = time.compareTo(another.time);
			return timeCompare;
		}else{
			return numberCompare;
		}
	}
}
