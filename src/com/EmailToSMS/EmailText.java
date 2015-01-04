package com.EmailToSMS;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailText implements EmailPart {
	/**
	 * Handles the text of an email
	 */
	
	private String text;
	
	public EmailText(String text)
	{
		this.text = text;
	}
	
	public void toMimeMessage(MimeMessage message) throws MessagingException
	{
		message.setText(text);
	}
}
