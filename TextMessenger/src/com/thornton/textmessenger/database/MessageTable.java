package com.thornton.textmessenger.database;

public class MessageTable {

	public static final String TABLE_NAME = "message_table";

	public static final String COLUMN_ID = "_id";

	public static final String COLUMN_PHONE_NUMBER = "phone_number";

	public static final String COLUMN_MESSAGE_TEXT = "message_text";

	public static final String COLUMN_MESSAGE_TIME = "message_time";

	//TODO: Create the table
	public static final String TABLE_CREATE_SQL = "";

	public static final String TABLE_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
