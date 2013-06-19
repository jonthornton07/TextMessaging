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

	/**Column representing the textof the message*/
	public static final String COLUMN_MESSAGE_TEXT = "message_text";

	/**Column representing the time the message was sent/received*/
	public static final String COLUMN_MESSAGE_TIME = "message_time";

	/**Contact the message was sent to or received by*/
	public static final String COLUMN_MESAGE_CONTACT = "message_contact";

	//TODO: Create the table
	/**String representing the SQLite command to create the table*/
	public static final String TABLE_CREATE_SQL = "";

	/**String representing the SQLite command to drop the table*/
	public static final String TABLE_DROP_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
