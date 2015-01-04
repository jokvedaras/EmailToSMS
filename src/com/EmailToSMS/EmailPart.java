package com.EmailToSMS;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface EmailPart {
	/**
	 * Make up all parts of an email
	 */
	
	public void toMimeMessage(MimeMessage message) throws MessagingException;
	
}
