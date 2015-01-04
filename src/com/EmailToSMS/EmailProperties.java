package com.EmailToSMS;

import java.util.Properties;

/**
 * Handle all the email properties that are read from the XML file to send in email
 * @author Joe Kvedaras
 *
 */
public class EmailProperties {
	
	private java.util.Properties propertiesFromXml;
	private String username;
	private String password;

	
	public EmailProperties(){
		//Hold all the key/pair values from XML document. Set to get properties from the System as default
		propertiesFromXml = System.getProperties();
	}
	
	//---------------Mutator Methods----------------------
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * Add key/value pair to propertiesFromXml
	 */
	public void addValue(String key, String value){
		propertiesFromXml.put(key, value);
	}
	
	//----------------Accessor Methods--------------------
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	/**
	 * Return properties
	 */
	public Properties getProperties(){
		return propertiesFromXml;
	}
	
}
