package com.thornton.textmessenger.database;

/**
 * POJO representing the message table that will be created in the database.
 * This should be utilized when trying to reference the columns in the database
 * so that if the data gets changed, these will correctly.
 * 
 * @author jthornton
 *
 */
public class MessageTable {

	/**Name of the table*/
	public static final String TABLE_NAME = "message_table";

	/**Primary key of the table*/
	public static final String COLUMN_ID = "_id";

	/**Column representing the number of the sender*/
	public static final String COLUMN_PHONE_NUMBER = "phone_number";

	/**Column representing the text of the message*/
	public static final String COLUMN_MESSAGE_TEXT = "message_text";

	/**Column representing the time the message was sent/received*/
	public static final String COLUMN_MESSAGE_TIME = "message_time";

	/**Column representing if the text was to or from the user*/
	public static final String COLUMN_TO_ME = "to_me";

	/**Column representing if the text has been read*/
	public static final String COLUMN_READ = "message_read";

	//TODO: Create the table
	/**String representing the SQLite command to create the table*/
	public static final String TABLE_CREATE_SQL = "create table "
			+ TABLE_NAME + "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_PHONE_NUMBER + " text not null,  "
			+ COLUMN_MESSAGE_TEXT + " text not null, "
			+ COLUMN_TO_ME + " integer not null, "
			+ COLUMN_READ + " integer not null, "
			+ COLUMN_MESSAGE_TIME + " text not null);";

	/**String representing the SQLite command to drop the table*/
	public static final String TABLE_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

	/**String array containing all the columns in the table*/
	public static final String[] ALL_PROJECTION =
		{MessageTable.COLUMN_ID,
		MessageTable.COLUMN_MESSAGE_TEXT,
		MessageTable.COLUMN_MESSAGE_TIME,
		MessageTable.COLUMN_PHONE_NUMBER,
		MessageTable.COLUMN_READ,
		MessageTable.COLUMN_TO_ME};
}
