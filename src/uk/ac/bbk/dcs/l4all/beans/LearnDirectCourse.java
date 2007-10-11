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

/**
 * @author George Papamarkos
 *
 * Course.java
 * uk.ac.bbk.dcs.l4all.beans
 */
public class LearnDirectCourse {
	
	private String courseCode = "";
	private String title = "" ;
	private String noRating = "" ;
	private String date = "";
	private String context = "";
	private String type = "";
	private String language ="";
	private String creator = "";
	private String URL = "";
	private String description = "";
	private String publisher = "";
	private String averageRating = "";
	private String contributor = "";
	private String metaDate = "";
	private String subject = "";
	private String entity = "";
	
	// extra for LearnDirect
	private String provAddress1 ;
	private String provAddress2 ;
	private String provAddress3 ;
	private String provAddress4 ;
	private String provPostCode ;
	
	private String venueAddress1 ;
	private String venueAddress2 ;
	private String venueAddress3 ;
	private String venueAddress4 ;
	private String venuePostCode ;
	
	private String courseContact ;
	private String contactTelNo ;
	private String entry;
	private String qualificationTitle ;
	private String duration ;
	private String cost ;
	
	public String toXML()
	{
	    String res = null;
	    res = ("<course learn_direct='1'>");
	    res += ("<course_id>" + getCourseCode() + "</course_id>");
	    res += ("<course_title><![CDATA[" + getTitle() + "]]></course_title>");
	    res += ("<course_description><![CDATA[" + getDescription() + "]]></course_description>");
	    res += ("<course_URL><![CDATA[" + getURL()+ "]]></course_URL>");
	    res += ("<course_level><![CDATA[" + getQualificationTitle()+ "]]></course_level>");
	    res += ("</course>");
	    return res;
	}
	
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getContactTelNo() {
		return contactTelNo;
	}
	public void setContactTelNo(String contactTelNo) {
		this.contactTelNo = contactTelNo;
	}
	public String getCourseContact() {
		return courseContact;
	}
	public void setCourseContact(String courseContact) {
		this.courseContact = courseContact;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public String getProvAddress1() {
		return provAddress1;
	}
	public void setProvAddress1(String provAddress1) {
		this.provAddress1 = provAddress1;
	}
	public String getProvAddress2() {
		return provAddress2;
	}
	public void setProvAddress2(String provAddress2) {
		this.provAddress2 = provAddress2;
	}
	public String getProvAddress3() {
		return provAddress3;
	}
	public void setProvAddress3(String provAddress3) {
		this.provAddress3 = provAddress3;
	}
	public String getProvAddress4() {
		return provAddress4;
	}
	public void setProvAddress4(String provAddress4) {
		this.provAddress4 = provAddress4;
	}
	public String getProvPostCode() {
		return provPostCode;
	}
	public void setProvPostCode(String provPostCode) {
		this.provPostCode = provPostCode;
	}
	public String getQualificationTitle() {
		return qualificationTitle;
	}
	public void setQualificationTitle(String qualificationTitle) {
		this.qualificationTitle = qualificationTitle;
	}
	public String getVenueAddress1() {
		return venueAddress1;
	}
	public void setVenueAddress1(String venueAddress1) {
		this.venueAddress1 = venueAddress1;
	}
	public String getVenueAddress2() {
		return venueAddress2;
	}
	public void setVenueAddress2(String venueAddress2) {
		this.venueAddress2 = venueAddress2;
	}
	public String getVenueAddress3() {
		return venueAddress3;
	}
	public void setVenueAddress3(String venueAddress3) {
		this.venueAddress3 = venueAddress3;
	}
	public String getVenueAddress4() {
		return venueAddress4;
	}
	public void setVenueAddress4(String venueAddress4) {
		this.venueAddress4 = venueAddress4;
	}
	public String getVenuePostCode() {
		return venuePostCode;
	}
	public void setVenuePostCode(String venuePostCode) {
		this.venuePostCode = venuePostCode;
	}
	
	
	public String getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getMetaDate() {
		return metaDate;
	}
	public void setMetaDate(String metaDate) {
		this.metaDate = metaDate;
	}
	public String getNoRating() {
		return noRating;
	}
	public void setNoRating(String noRating) {
		this.noRating = noRating;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getURI() {
		return URL;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String url) {
		URL = url;
	}
}
