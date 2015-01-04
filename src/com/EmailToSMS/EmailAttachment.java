package com.EmailToSMS;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

public class EmailAttachment {
	
	private String path;
	
	/**
	 * Add any attachments to the email
	 */
	public EmailAttachment(String path)
	{
		this.path = path;
	}
	
	
	
	public void toMimeMessage(MimeMessage message) throws MessagingException{
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(path);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(path);
		Object multipart = null;
		((Multipart)(Object) multipart).addBodyPart(messageBodyPart);
		message.setContent((Multipart) multipart);
	}
}
