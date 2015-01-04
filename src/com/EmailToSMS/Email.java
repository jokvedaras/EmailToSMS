package com.EmailToSMS;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * Basic email class. An email consists of a header and optional email parts (if you don't want to send blank space)
 * @author Joe Kvedaras
 */
public class Email {

	// Sender's email ID needs to be mentioned. No WhiteSpace. Needs text before
	// and after @.may have not ^(.*@.*)
	private String from;
	private List<String> to;
	private List<String> cc;
	private List<String> bcc;
	private String subject;
	private List<EmailPart> emailParts;
	
	/**
	 * Constructor for an Email
	 * @param from, subject, text
	 */
	public Email(String from, String subject){
		this.from = from;
		this.subject = subject;
	}


	// ---------------Mutator Methods------------

	public void setTo(String to) {
		if (this.to == null) {
			this.to = new ArrayList<String>();
			this.to.add(to);
		} else {
			this.to.add(to);
		}
	}

	public void setCc(String cc) {
		if (this.cc == null) {
			this.cc = new ArrayList<String>();
			this.cc.add(cc);
		} else {
			this.cc.add(cc);
		}
	}

	public void setBcc(String bcc) {
		if (this.bcc == null) {
			this.bcc = new ArrayList<String>();
			this.bcc.add(bcc);
		} else {
			this.bcc.add(bcc);
		}
	}
	
	public void setEmailPart(EmailPart part) {
		if (this.emailParts == null) {
			this.emailParts = new ArrayList<EmailPart>();
			this.emailParts.add(part);
		} else {
			this.emailParts.add(part);
		}
	}

	
	
	
	
	// -----------------Mime Message Methods-----------------
	/**
	 * Make the email a MimeMessage to be sent by EmailController
	 * @param session
	 * @return MimeMessage
	 */
	public MimeMessage toMimeMessage(Session session){
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// If sending to multiple email addresses. use addRecipients(Message.RecipientType type,
			// Address[] addresses); type would be set to TO, CC, BCC. addresses is
			// the array of email IDs. You would need to use InternetAddress() method while specifying email IDs
			message.addRecipients(Message.RecipientType.TO,addAddresses(message));

			// Set Subject: header field
			message.setSubject(subject);

			// Add all Emails parts to email
			for (EmailPart part: emailParts){
				part.toMimeMessage(message);
			}
			
			return message;

		} catch (MessagingException e) {
			System.out.println("ERROR: when making email a MimeMessage");
			e.printStackTrace();
			return null;
		}
		
	}
	
	

	/**
	 * Return all the addresses in email as Addresses
	 * 
	 * @param to
	 * @return All addresses as an array
	 * @return null if bad input
	 */
	private Address[] addAddresses(MimeMessage message) {
		// Make new array size of ArrayList
		Address emails[] = new Address[to.size()];
		// Add every element in ArrayList into Array
		for (int ii = 0; ii < to.size(); ii++) {
			try {
				emails[ii] = new InternetAddress(to.get(ii));
			} catch (AddressException e) {
				System.err.println("ERROR: When converting Strings to Addresses");
				e.printStackTrace();
			}
		}
		return emails;

	}
}
