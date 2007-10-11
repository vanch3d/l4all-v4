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
/*
 * Created on 15-Nov-2005
 * 
 * uk.ac.bbk.dcs.l4all.tests in l4all
 *
 */
package uk.ac.bbk.dcs.l4all.tests;

import java.util.Hashtable;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import uk.ac.bbk.dcs.l4all.beans.LearnDirectWSCallInterface;

/**
 * @author George Papamarkos
 *
 * LearnDirectConnectionTest.java
 * uk.ac.bbk.dcs.l4all.tests
 */
public class LearnDirectConnectionTest {

	/// http://www.learndirect-advice.co.uk/findacourse/
	private static String url = "http://213.219.10.140/pls/cgi-bin/hc_xml_search_request.request_search_do";
	
	public static void main(String[] args) {
		
		    Hashtable valuePairs = new Hashtable();
			
			valuePairs.put("origin", "L");
//			valuePairs.put("CourseID", "5268538");
			valuePairs.put("postcode","WC1");
			valuePairs.put("distance","25");
			valuePairs.put("LDCS","");
			valuePairs.put("Level","C");
			valuePairs.put("LearningType","ALL");
			valuePairs.put("GetXRecords","100");
			valuePairs.put("system_id","U");
			valuePairs.put("PhraseSearch","Oracle");

			Document resultXMLDocument = LearnDirectWSCallInterface.searchLDResourceOppList(valuePairs);
			
			
//			NodeList records = LearnDirectSearch.getRecords(resultXMLDocument);
////			
//			for (int i=0 ; i < records.getLength(); i++) {
//				System.out.println(LearnDirectSearch.getRecordDetails(records.item(i)).toString());
//			}
			
			walk(resultXMLDocument);
			
			/// The conservative implementation
			
//			URL learnDirectURL;
//			learnDirectURL = new URL(url);
//			HttpURLConnection conn = (HttpURLConnection)learnDirectURL.openConnection();
//			
//			conn.setRequestMethod("POST");
//			conn.setUseCaches(false);
//			conn.setDoOutput(true);
//			conn.setDoInput(true);
//			
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			
//			//Send POST output.
//		    DataOutputStream printout = new DataOutputStream(conn.getOutputStream ());
//		    
//		    String content = "origin=L&postcode=&distance=5&LDCS=AF.2&Level=&LearningType=&GetXRecords=1000&system_id=U&AffiliateID=210905&PhraseSearch=Development";
//		    printout.writeBytes (content);
//		    printout.flush ();
//		    printout.close ();
//		    // Get response data.
//		    DataInputStream input = new DataInputStream (conn.getInputStream ());
//		    String str;
//			
//		    while (null != ((str = input.readLine())))
//		    	System.out.println (str);
//
//		    input.close ();
		    
			
			///////// Implementation using Apach Commons HTTP Client
//			PostMethod postMethod = new PostMethod(url);
//			
//			NameValuePair [] data = {
//					new NameValuePair ("origin","L"),
//					new NameValuePair ("postcode",""),
//					new NameValuePair ("distance","5"),
//					new NameValuePair ("LDCS","AF.2"),
//					new NameValuePair ("Level",""),
//					new NameValuePair ("LearningType",""),
//					new NameValuePair ("GetXRecords","100"),
//					new NameValuePair ("system_id","U"),
//					new NameValuePair ("AffiliateID","210905"),
//					new NameValuePair ("PhraseSearch","Computer")					
//			} ;
//			
//			postMethod.setRequestBody(data);
//			HttpClient httpClient = new HttpClient();
//			
//			try {
//				int result = httpClient.executeMethod(postMethod);
//				System.out.println("Response Status Code : " + result);
//				System.out.println("Response Body : ");
//				System.out.println(postMethod.getResponseBodyAsString());
//			} finally {
//				postMethod.releaseConnection();
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
	}
	
	private static void walk(Node node) {
        int type = node.getNodeType();
        switch (type) {
            case Node.DOCUMENT_NODE: {
                System.out.println("<?xml version=\"1.0\" encoding=\"" + "UTF-8" + "\"?>");
                break;
            }//end of document
            case Node.ELEMENT_NODE: {
                System.out.print('<' + node.getNodeName());
                NamedNodeMap nnm = node.getAttributes();
                if (nnm != null) {
                    int len = nnm.getLength();
                    Attr attr;
                    for (int i = 0; i < len; i++) {
                        attr = (Attr) nnm.item(i);
                        System.out.print(' ' + attr.getNodeName() + "=\"" + attr.getNodeValue() + '"');
                    }
                }
                System.out.print('>');

                break;

            }//end of element
            case Node.ENTITY_REFERENCE_NODE: {

                System.out.print('&' + node.getNodeName() + ';');
                break;

            }//end of entity
            case Node.CDATA_SECTION_NODE: {
                System.out.print("<![CDATA[" + node.getNodeValue() + "]]>");
                break;

            }
            case Node.TEXT_NODE: {
                System.out.print(node.getNodeValue());
                break;
            }
            case Node.PROCESSING_INSTRUCTION_NODE: {
                System.out.print("<?" + node.getNodeName());
                String data = node.getNodeValue();
                if (data != null && data.length() > 0) {
                    System.out.print(' ');
                    System.out.print(data);
                }
                System.out.println("?>");
                break;

            }
        }//end of switch

        //recurse
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            walk(child);
        }

        //without this the ending tags will miss
        if (type == Node.ELEMENT_NODE) {
            System.out.print("</" + node.getNodeName() + ">");
        }
    }//end of walk
}
