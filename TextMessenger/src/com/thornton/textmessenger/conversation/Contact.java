package com.thornton.textmessenger.conversation;

public class Contact {

	private String displayName;
	private String phoneNumber;

	// TODO: Get the users image (not sure how to store this);

	public Contact(final String displayName, final String phoneNumber) {
		setDisplayName(displayName);
		setPhoneNumber(phoneNumber);
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

}
