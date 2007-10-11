/*
* Copyright (C) 2005 Birkbeck College University of London
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/
package uk.ac.bbk.dcs.l4all.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author George Papamarkos
 *
 * LearnDirectSearch.java
 * uk.ac.bbk.dcs.l4all.beans
 */
public class LearnDirectWSCallInterface {
	private static String LD_URL = "http://213.219.10.140/pls/cgi-bin/hc_xml_search_request.request_search_do";

	static DocumentBuilderFactory xmlFactory ; 
	static DocumentBuilder xmlBuilder ;	
	static {
		xmlFactory = DocumentBuilderFactory.newInstance();
		try {
			xmlBuilder = xmlFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches the LearnDirect for the list of courses, according to the parameters
	 * specified in <b>valuePairs</b> Hashtable
	 * 
	 * @param valuePairs
	 * @return
	 */
	public static synchronized Document searchLDResourceOppList(Hashtable valuePairs) {
		
		GetMethod getMethod = new GetMethod(LD_URL);
		
		NameValuePair [] data = {
				new NameValuePair ("origin",(String)valuePairs.get("origin")),
				new NameValuePair ("postcode",(String)valuePairs.get("postcode")),
				new NameValuePair ("distance",(String)valuePairs.get("distance")),
				new NameValuePair ("LDCS",(String)valuePairs.get("LDCS")),
				new NameValuePair ("Level",(String)valuePairs.get("Level")),
				new NameValuePair ("LearningType",(String)valuePairs.get("LearningType")),
				new NameValuePair ("GetXRecords",(String)valuePairs.get("GetXRecords")),
				new NameValuePair ("system_id",(String)valuePairs.get("system_id")),
				new NameValuePair ("AffiliateID","210905"),
				new NameValuePair ("PhraseSearch",(String)valuePairs.get("PhraseSearch"))					
		} ;
	
		getMethod.setQueryString(data);
	
		HttpClient httpClient = new HttpClient();
		InputStream resultXMLStream = null;
		Document xmlDocument = null;
		
		try {
			int result = httpClient.executeMethod(getMethod);
			
			if (result != 200) 
				return null ;
			
			String dummy = getMethod.getResponseBodyAsString();
			
			System.out.println(dummy);
			
			resultXMLStream = getMethod.getResponseBodyAsStream();
			xmlDocument = xmlBuilder.parse(resultXMLStream);
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		
		return xmlDocument;
	}
	
	/**
	 * Searches the LearnDirect for the details of a specific course, according to the parameters
	 * specified in <b>valuePairs</b> Hashtable
	 * 
	 * @param valuePairs
	 * @return
	 */
	public static synchronized Document searchLDResourceOpportunity(String courseID) {
		GetMethod getMethod = new GetMethod(LD_URL);
		
		NameValuePair [] data = {
				new NameValuePair ("origin","L"),
				new NameValuePair ("CourseID",courseID),
				new NameValuePair ("system_id","U"),
				new NameValuePair ("AffiliateID","210905"),					
		} ;
		
		getMethod.setQueryString(data);
	
		HttpClient httpClient = new HttpClient();
		InputStream resultXMLStream = null;
		Document xmlDocument = null;
		
		try {
			int result = httpClient.executeMethod(getMethod);
			
			if (result != 200) 
				return null ;
			
			resultXMLStream = getMethod.getResponseBodyAsStream();
			xmlDocument = xmlBuilder.parse(resultXMLStream);
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		
		return xmlDocument;
	}
	
	/**
	 * Retrives a result record contents
	 * @param record
	 * @return
	 */
	public static synchronized Hashtable getRecordDetails(Node record) {
		Hashtable valuePairs = new Hashtable();
		
		String courseID = "";
		String courseName = "";
		String courseDescription = "" ;
		String courseVenue = "";
		
		Element res = null;
		
		if (record instanceof Element)
			res = (Element)record ;
		
		NodeList courseInfo = res.getChildNodes();
		
		Node curNode = null;
		for (int i=0 ; i < courseInfo.getLength() ; i++) {
			curNode = courseInfo.item(i);
			String tagName = curNode.getNodeName();
			
			if (tagName.equals("CourseId")) {
				courseID = getChildText(curNode);
			} else if (tagName.equals("Name")) {
				courseName = getChildText(curNode);
			} else if (tagName.equals("Description")) {
				courseDescription = getChildText(curNode);
			} else if (tagName.equals("Venue")) {
				courseVenue = getChildText(curNode);
			}
		}
		
		valuePairs.put("CourseId", courseID);
		valuePairs.put("Name", courseName);
		valuePairs.put("Description", courseDescription);
		valuePairs.put("Venue",courseVenue);
		
		return valuePairs;
	}
	
	public static synchronized Hashtable getCourseDetails(Document courseNode) {
		
		Hashtable valuePairs = new Hashtable();
		
		String learningSummary = "";
		String provider = "";
		String provAddress1 = "";
		String provAddress2 = "";
		String provAddress3 = "";
		String provAddress4 = "";
		String provPostcode = "";
		String courseContact = "";
		String courseTelNo = "";
		String venueAddress1 = "";
		String venueAddress2 = "";
		String venueAddress3 = "";
		String venueAddress4 = "";
		String venuePostcode = "";
		String entry = "";
		String qualTitle = "";
		String startDetails = "";
		String duration = "";
		String cost = "";
		String provWebsite = "";
		String attendanceType = "";
		String courseTitle = "";
		String courseDescription = "";
		
		String errorCode = "";
		String errorDesc = "";
		
		Document resultDoc = null ;
		
		if (courseNode.getNodeType() == Node.DOCUMENT_NODE)
			resultDoc = (Document)courseNode;
		else
			resultDoc = courseNode.getOwnerDocument();
		
		NodeList courseDetails = resultDoc.getElementsByTagName("learndirect-opp");
		courseDetails = courseDetails.item(0).getChildNodes();
		
		Node curNode = null ;
		
		for (int i=0; i < courseDetails.getLength() ; i++)
		{
			curNode = courseDetails.item(i);
			String tagName = curNode.getNodeName();
			if (tagName.equals("ErrorCode")) {
			    	errorCode = getChildText(curNode);
			} else if (tagName.equals("ErrorDescription")) {
			    	errorDesc = getChildText(curNode);
			} else if (tagName.equals("LearningSummary")) {
				learningSummary = getChildText(curNode);
			} else if (tagName.equals("Provider")) {
				provider = getChildText(curNode);
			} else if (tagName.equals("ProvAddress1")) {
				provAddress1 = getChildText(curNode);
			} else if (tagName.equals("ProvAddress2")) {
				provAddress2 = getChildText(curNode);
			} else if (tagName.equals("ProvAddress3")) {
				provAddress3 = getChildText(curNode);
			} else if (tagName.equals("ProvAddress4")) {
				provAddress4 = getChildText(curNode);
			} else if (tagName.equals("ProvPostcode")) {
				provPostcode = getChildText(curNode);
			} else if (tagName.equals("CourseContact")) {
				courseContact = getChildText(curNode);
			} else if (tagName.equals("ContactTelNo")) {
				courseTelNo = getChildText(curNode);
			} else if (tagName.equals("Entry")) {
				entry = getChildText(curNode);
			} else if (tagName.equals("QualificationType")) {
				qualTitle = getChildText(curNode);
			} else if (tagName.equals("StartDetails")) {
				startDetails = getChildText(curNode);
			} else if (tagName.equals("Duration")) {
				duration = getChildText(curNode);
			} else if (tagName.equals("Cost")) {
				cost = getChildText(curNode);
			} else if (tagName.equals("ProvWebsite")) {
				provWebsite = getChildText(curNode);
			} else if (tagName.equals("AttendanceType")) {
				attendanceType = getChildText(curNode);
			} else if (tagName.equals("CourseTitle")) {
				courseTitle = getChildText(curNode);
			} else if (tagName.equals("Description")) {
				courseDescription = getChildText(curNode);
			} else if (tagName.equals("VenueAddress1")) {
				venueAddress1 = getChildText(curNode);
			} else if (tagName.equals("VenueAddress2")) {
				venueAddress2 = getChildText(curNode);
			} else if (tagName.equals("VenueAddress3")) {
				venueAddress3 = getChildText(curNode);
			} else if (tagName.equals("VenueAddress4")) {
				venueAddress4 = getChildText(curNode);
			} else if (tagName.equals("VenuePostcode")) {
				venuePostcode = getChildText(curNode);
			}
		}
		
		valuePairs.put("ErrorCode",errorCode);
		valuePairs.put("ErrorDesc",errorDesc);
		valuePairs.put("Provider",provider);
		valuePairs.put("ProvAddress1",provAddress1);
		valuePairs.put("ProvAddress2",provAddress2);
		valuePairs.put("ProvAddress3",provAddress3);
		valuePairs.put("ProvAddress4",provAddress4);
		valuePairs.put("ProvPostcode",provPostcode);
		valuePairs.put("VenueAddress1",venueAddress1);
		valuePairs.put("VenueAddress2",venueAddress2);
		valuePairs.put("VenueAddress3",venueAddress3);
		valuePairs.put("VenueAddress4",venueAddress4);
		valuePairs.put("VenuePostcode",venuePostcode);
		valuePairs.put("CourseContact",courseContact);
		valuePairs.put("ContactTelNo",courseTelNo);
		valuePairs.put("Entry",entry);
		valuePairs.put("QualificationTitle",qualTitle);
		valuePairs.put("StartDetails",startDetails);
		valuePairs.put("Duration",duration);	
		valuePairs.put("Cost",cost);
		valuePairs.put("ProvWebsite",provWebsite);
		valuePairs.put("AttendanceType", attendanceType);
		valuePairs.put("CourseTitle", courseTitle);
		valuePairs.put("Description", courseDescription);
		
		return valuePairs;
	}
	
	
	/**
	 * Extracts the error details from a LD search response
	 * @param resultDoc
	 * @return
	 */
	public static Hashtable getError(Document resultDoc) {
		Hashtable error = new Hashtable();
		
		Element root = resultDoc.getDocumentElement();
		
		Element errorCodeElement = (Element)root.getElementsByTagName("ErrorCode").item(0);
		Element errorDescriptionElement = (Element)root.getElementsByTagName("ErrorDescription ").item(0);
		
		String errorCode = getChildText(errorCodeElement);
		String errorDescription = getChildText(errorDescriptionElement);
		
		error.put("ErrorCode", errorCode);
		error.put("ErrorDescription", errorDescription);
		
		return error;
	}
	
	/**
	 * Util method giving the text child of an XML node (sic)
	 * @param node
	 * @return
	 */
	public static String getChildText(Node node){
		String childText = "";
		Node childNode;
		
		childNode = node.getChildNodes().item(0);
		
		if (childNode != null)
			if (childNode.getNodeType() == Node.TEXT_NODE)
				childText = childNode.getNodeValue();
			else 
				childText = "";
		
		return childText ;
	}
	
	public static NodeList getRecords(Document resultDoc) {
		NodeList records = resultDoc.getElementsByTagName("learndirect-list");
		records = ((Element)records.item(0)).getElementsByTagName("Record");
		
		return records ;
	}
	
	public static int getNumOfRecords(Document doc) {
		return getRecords(doc).getLength();
	}
}
