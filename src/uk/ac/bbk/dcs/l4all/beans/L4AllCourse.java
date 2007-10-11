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

import uk.ac.bbk.dcs.l4all.db.RDFRepositoryManager;
import uk.ac.bbk.dcs.l4all.vocabulary.Dc_ext;

import com.hp.hpl.jena.db.ModelRDB;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC_11;

/**
 * @author George Papamarkos
 *
 * CourseMetadataManager.java uk.ac.bbk.dcs.l4all.beans
 */
public class L4AllCourse {
    
    //////GLOBAL CONSTANTS //////////
    private static final String BASE_USER_URL = "http://www.dcs.bbk.ac.uk/~gpapa05/l4all/courses/";
    
    private static final String MODEL_NAME = "l4all_courses";
    
    private ModelRDB model = RDFRepositoryManager.getModel(MODEL_NAME);
    
    private String level = ""; // dc-ext:level
    private String title = ""; // dc:title
    private String creator = ""; // dc:creator
    private String description = ""; // dc:description
    private String institution = ""; // dc:publisher
    private String memberOfStaff = ""; // dc: contributor
    private String startDate = ""; // dc:date
    private String courseType = ""; // dc-ext:courseType
    private String modeOfStudy = ""; // dc-ext:modeOfStudy
    private String courseCode = ""; // dc:identifier
    private String url = ""; // dc:source
    private String language = "";  // dc:language
    private String scope = ""; // dc:coverage
    private String entryRequirements = ""; // dc-ext:entry
    private String skills = ""; // dc-ext:skills
    private String cost = ""; // dc-ext:cost
    private String subject = ""; // dc:subject
    
    /**
     * Return the course as an XML structure
     * @return
     */
    public String toXML()
    {
	String res = null;

        res = ("<course learn_direct='0'>");
        res += ("<course_id>" + getCourseCode() + "</course_id>");
        res += ("<course_title><![CDATA[" + getTitle() + "]]></course_title>");
        res += ("<course_description><![CDATA["
        	    + getDescription() + "]]></course_description>");
        res += ("<course_URL><![CDATA[" + getURL() + "]]></course_URL>");
        res += ("<course_level><![CDATA[" + getLevel() + "]]></course_level>");
        res += ("<course_creator><![CDATA[" + getCreator() + "]]></course_creator>");
        res += ("<course_institution><![CDATA[" + getInstitution() + "]]></course_institution>");
        res += ("<course_memberOfStaff><![CDATA[" + getMemberOfStaff() + "]]></course_memberOfStaff>");
        res += ("<course_startDate><![CDATA[" + getStartDate() + "]]></course_startDate>");
        res += ("<course_courseType><![CDATA[" + getCourseType() + "]]></course_courseType>");
        res += ("<course_modeOfStudy><![CDATA[" + getModeOfStudy() + "]]></course_modeOfStudy>");
        res += ("<course_language><![CDATA[" + getLanguage() + "]]></course_language>");
        res += ("<course_scope><![CDATA[" + getScope() + "]]></course_scope>");
        res += ("<course_entryRequirements><![CDATA[" + getEntryRequirements() + "]]></course_entryRequirements>");
        res += ("<course_skills><![CDATA[" + getSkills() + "]]></course_skills>");
        res += ("<course_cost><![CDATA[" + getCost() + "]]></course_cost>");
        res += ("<course_subject><![CDATA[" + getSubject() + "]]></course_subject>");
        res += ("</course>");
	return res;
    }
    
    public Message createNewCourse() {
        String COURSE_URL = BASE_USER_URL + getCourseCode();
        
//        if (RDFGraphUtil.hasResource(COURSE_URL, model))
//            return new Message(
//                    ErrorCodes.UNIQUE_USERNAME_CONSTRAINT_VIOLATION_ERROR,
//                    "The username exists already. Please choose another one");
        
        // Create the Course Resource node
        Resource course = model.createResource(COURSE_URL, Dc_ext.Course);
        
        course.addProperty(DC_11.identifier,getCourseCode());
        course.addProperty(Dc_ext.level, getLevel());
        course.addProperty(DC_11.title, getTitle());
        course.addProperty(DC_11.creator, getCreator());
        course.addProperty(DC_11.description, getDescription());
        course.addProperty(DC_11.publisher, getInstitution());
        course.addProperty(DC_11.contributor, getMemberOfStaff());
        course.addProperty(DC_11.date, getStartDate());
        course.addProperty(Dc_ext.courseType, getCourseType());
        course.addProperty(Dc_ext.modeOfStudy, getModeOfStudy());
        course.addProperty(DC_11.source, getURL());
        course.addProperty(DC_11.coverage, getScope());
        course.addProperty(Dc_ext.entry, getEntryRequirements());
        course.addProperty(Dc_ext.skills, getSkills());
        course.addProperty(Dc_ext.cost, getCost());
        course.addProperty(DC_11.subject, getSubject());

        // was missing
        course.addProperty(DC_11.language, getLanguage());
        
        // Dump it to the stdout
        model.write(System.out);
        
        return new Message(ErrorCodes.SUCCESS,
                "Successful Registration");
    }
    
    //// Getters and Setters
    
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEntryRequirements() {
        return entryRequirements;
    }
    public void setEntryRequirements(String entryRequirements) {
        this.entryRequirements = entryRequirements;
    }
    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getMemberOfStaff() {
        return memberOfStaff;
    }
    public void setMemberOfStaff(String memberOfStaff) {
        this.memberOfStaff = memberOfStaff;
    }
    public String getModeOfStudy() {
        return modeOfStudy;
    }
    public void setModeOfStudy(String modeOfStudy) {
        this.modeOfStudy = modeOfStudy;
    }
    public String getScope() {
        return scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
    
    public String getCourseType() {
        return courseType;
    }
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
    public String getURL() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    
}