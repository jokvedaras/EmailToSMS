package com.EmailToSMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

/**
 * Class to control all the behavior of sending an email to anyone you want.
 * @author Joe Kvedaras
 *
 */
public class EmailController {

	private String addressTextFile = "./res/emailAddresses";
	private boolean debug = false;
		
	public EmailController(){
		
	}
	
	/**
	 * Wrap private method for sending email
	 * @return
	 */
	public boolean sendEmail(Email email){
		return sendEmailNow(email);
	}
	
	
	
	/**
	 * Get properties from XML and return Session object
	 */
	private Session getProperties(){
		EmailProperties emailProperties = new EmailProperties();
		XmlParser xml = new XmlParser(emailProperties);
		xml.run();
		
		
		//Set the Session Object to authenticate username and password.
		Session session = Session.getInstance(emailProperties.getProperties(),
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailProperties.getUsername(), emailProperties.getPassword());
					}
				  });
		
		return session;
	}
	
	
	
	
	
	/**
	 * Send an email to all address in emailAddress.txt with the text body from the GUI
	 * @throws FileNotFoundException 
	 * @throws AddressException 
	 */
	private boolean sendEmailNow(Email email){
		
		//Grab email addresses. Exceptions will be caught in getAddress() Method
		getAddressFromText(addressTextFile, email);
		
		Session session = getProperties();
		
		MimeMessage message = email.toMimeMessage(session);
		
		try {
			// Send message
			if(!debug){
				Transport.send(message);
			}
			//Message must have sent if it made it this far
			return true;
			
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
		//Method didn't run because it caught exception
		return false;
	}
	
	
	/**
	 * Read text file of phone numbers and add it to email
	 * @return String array of TO addresses
	 * @throws FileNotFoundException 
	 * @throws AddressException 
	 */
	private void getAddressFromText(String textFile, Email email){
		
		File f = new File (textFile);
		
		try{
			Scanner sc = new Scanner(f);
			
			//Scan file using regex. "//" is a comment in file so don't take any preceding white space
			//or any character after comment
			sc.useDelimiter(" *//.*\n*");
			
			while(sc.hasNext()){
				String emailAddress = sc.next();
				if (!emailAddress.isEmpty()){//Make sure emailAddress is not an empty string
					email.setTo(emailAddress);
				}
				
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found" + f);
		}
	}
}
