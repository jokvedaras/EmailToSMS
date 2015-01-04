package com.EmailToSMS;

public class Controller {
	
	EmailController controller;
	
	public Controller(){
		controller = new EmailController();
	}
	
	public boolean sendEmail(String from, String subject, String text){
		Email email = new Email(from, subject);
		email.setEmailPart(new EmailText(text));
		return controller.sendEmail(email);
		
	}
}
