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

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * @author George Papamarkos
 */
public class LearnDirectCourseSearch {

	private String keyword;

	private String level;

	/**
	 * Searches for courses in LearnDirect database
	 * 
	 * @param keyword
	 * @param level
	 * @param postcode
	 * @param distance
	 * @return
	 */
	public static LearnDirectCourse[] searchLDCourses(String keyword, String level, String postcode, String distance) {
		
		LearnDirectCourse [] courses = null ;
		/// Searching on LearnDirect
		Hashtable valuePairs = new Hashtable();
		
		
		valuePairs.put("origin", "L");
		if (postcode == null) {
			valuePairs.put("postcode","");
		} else {
			valuePairs.put("postcode",postcode);
		}
		
		if (distance == null)
			valuePairs.put("distance","");
		else
			valuePairs.put("distance",distance);
		
		valuePairs.put("LDCS","");
		
		if (level == null)
			valuePairs.put("Level","");
		else
			valuePairs.put("Level",level);
		
		valuePairs.put("LearningType","ALL");
		valuePairs.put("GetXRecords","100");
		valuePairs.put("system_id","U");
		
		valuePairs.put("PhraseSearch",keyword);
		
		Document results = LearnDirectWSCallInterface.searchLDResourceOppList(valuePairs);
		if (results==null) return courses;
		NodeList records = LearnDirectWSCallInterface.getRecords(results);
		
		int numOfRecords = LearnDirectWSCallInterface.getNumOfRecords(results);
		courses = new LearnDirectCourse[numOfRecords];
		
		for (int i = 0 ; i < numOfRecords ; i++) {
			Hashtable courseDetails = LearnDirectWSCallInterface.getRecordDetails(records.item(i));
			
			courses[i] = new LearnDirectCourse();
			courses[i].setCourseCode((String)courseDetails.get("CourseId"));
			courses[i].setTitle((String)courseDetails.get("Name"));
			courses[i].setDescription((String)courseDetails.get("Description"));
			courses[i].setPublisher((String)courseDetails.get("Venue"));
		}
		
		return courses;
	}

	/**
	 * Retrieves the details of a LearnDirect course according to its ID
	 * 
	 * @param courseID The ID of the course
	 * @return
	 */
	public static synchronized LearnDirectCourse getLDCourseDetails(String courseID) {
		LearnDirectCourse course = new LearnDirectCourse();
		
		Document courseXML = LearnDirectWSCallInterface.searchLDResourceOpportunity(courseID);
		if (courseXML== null) return null;
		
		Hashtable courseDetails = LearnDirectWSCallInterface.getCourseDetails(courseXML);
		
		String error  = (String)courseDetails.get("ErrorCode");
		if (!"0".equals(error)) return null;
		
		course.setCourseCode(courseID);
		course.setTitle((String)courseDetails.get("CourseTitle"));
		course.setDescription((String)courseDetails.get("Description"));
		course.setPublisher((String)courseDetails.get("Provider"));
		course.setDate((String)courseDetails.get("StartDetails"));
		course.setURL((String)courseDetails.get("ProvWebsite"));
		course.setProvAddress1((String)courseDetails.get("ProvAddress1"));
		course.setProvAddress2((String)courseDetails.get("ProvAddress2"));
		course.setProvAddress3((String)courseDetails.get("ProvAddress3"));
		course.setProvAddress4((String)courseDetails.get("ProvAddress4"));
		course.setProvPostCode((String)courseDetails.get("ProvPostcode"));
		

		course.setVenueAddress1((String)courseDetails.get("VenueAddress1"));
		course.setVenueAddress2((String)courseDetails.get("VenueAddress2"));
		course.setVenueAddress3((String)courseDetails.get("VenueAddress3"));
		course.setVenueAddress4((String)courseDetails.get("VenueAddress4"));
		course.setVenuePostCode((String)courseDetails.get("VenuePostcode"));
		
		course.setContactTelNo((String)courseDetails.get("ContactTelNo"));
		course.setCourseContact((String)courseDetails.get("CourseContact"));
		course.setEntry((String)courseDetails.get("Entry"));
		course.setQualificationTitle((String)courseDetails.get("QualificationType"));
		course.setDuration((String)courseDetails.get("Duration"));
		course.setCost((String)courseDetails.get("Cost"));
		course.setContext((String)courseDetails.get("AttendanceType"));
		
		return course ;
	}
	
	
	/**
	 * @return Returns the keyword.
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            The keyword to set.
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return Returns the level.
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            The level to set.
	 */
	public void setLevel(String level) {
		this.level = level;
	}
}
