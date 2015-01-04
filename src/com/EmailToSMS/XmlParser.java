package com.EmailToSMS;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class to parse XML files
 * @author Joe Kvedaras
 *
 */
public class XmlParser extends DefaultHandler {

	//variables to maintain which tag is open
	private boolean usernameFlag = false;
	private boolean passwordFlag = false;
	private boolean propertiesFlag = false;
	private boolean valueFlag = false;
	
	private String propertyName;
	private EmailProperties emailProperties;
	private String xmlLocation = "./res/properties.xml";


	public XmlParser(EmailProperties emailProperties) {
		this.emailProperties = emailProperties;
	}
	

	public void run() {
		parseDocument();
	}

	private void parseDocument() {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			sp.parse(xmlLocation, this);

		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}


	/**
	 * This method is called every time the parser gets and open tag '<'
	 * identifies which tag is being open at the time be assigned a true flag
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
	
		//NOTE: would use switch statement but they are optimized for integers and not Strings.
		//I also have to use ignoreCase when comparing.
		
		if (qName.equalsIgnoreCase("USERNAME")) {
			usernameFlag = true;
		}
		if (qName.equalsIgnoreCase("PASSWORD")) {
			passwordFlag = true;
		}
		if (qName.equalsIgnoreCase("PROPERTIES")){
			propertiesFlag = true;
		}
		//This will allow anyone to add stuff to properties and mess with the program
		//Do I care about this? can i check for good values in properties?
		else{
			valueFlag = true;
			//need to save qName for input into emailProperties
			propertyName = qName;
			
		}
	}

	/**
	 * Gets the data stored in between '<' and '>' tags
	 */
	public void characters(char ch[], int start, int length)
			throws SAXException {

		if (usernameFlag) {
			emailProperties.setUsername(new String(ch, start, length));
		}
		if (passwordFlag) {
			emailProperties.setPassword(new String(ch, start, length));
		}
		if (propertiesFlag && valueFlag) {
			String item = new String(ch,start,length);
			//remove all newLines and tabs
			item = item.replaceAll("[\\\n][\\\t]", "");
			
			if(!item.isEmpty()){//If item is not null, add to properties
				emailProperties.addValue(propertyName, item);
			}
		}
	}

	/**
	 * Called by parser whenever '>' end of tag is found in xml
	 * Make tag flag 'false'
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equalsIgnoreCase("USERNAME")) {
			usernameFlag = false;
		}
		if (qName.equalsIgnoreCase("PASSWORD")) {
			passwordFlag = false;
		}
		if (qName.equalsIgnoreCase("PROPERTIES")){
			propertiesFlag = false;
			valueFlag = false;
		}
	}
};
