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
package uk.ac.bbk.dcs.l4all.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllCourse;
import uk.ac.bbk.dcs.l4all.beans.L4AllCoursesSearch;
import uk.ac.bbk.dcs.l4all.beans.LearnDirectCourse;
import uk.ac.bbk.dcs.l4all.beans.LearnDirectCourseSearch;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

/**
 * @author George Papamarkos
 * 
 * SearchServlet.java uk.ac.bbk.dcs.l4all.beans
 */
public class SearchCoursesServlet extends HttpServlet
{
    /**
     * Return the value of the given parameter from the Map.
     * The trick is to assume that there is no duplication of parameters in the request and
     * therefore there is either no value or a single one.
     * @param map	The map in which to search the parameter 
     * @param key	The key of the parameter to retrieve
     * @return		The value of the given parameter
     */
    private String getParam(Map map, String key)
    {
	String res[] = (String[]) map.get(key);
	if (res == null)
	    return null;
	else
	    return res[0];
    }

    /**
     * Perform the search request.
     * @param username	The identifier of the user
     * @param params	A map of all parameters defining the request
     * @return		A XML string containing the result of the request
     */
    public String processRequest(String username, Hashtable params)
    {
	String res = null;

	String keyword = getParam(params, "keyword");
	String courseLevel = getParam(params, "course_level");
	String courseSubject = getParam(params, "subject");
	String courseInstitution = getParam(params, "institution");
	String courseType = getParam(params, "type");
	String postcode = getParam(params, "postcode");
	String distance = getParam(params, "distance");
	String includeLearnDirect = getParam(params, "includeLD");
	String searchMode = getParam(params, "searchMode");

	if (courseLevel == null)
	    courseLevel = "";
	if (courseSubject == null)
	    courseSubject = "";
	if (courseInstitution == null)
	    courseInstitution = "";
	if (courseType == null)
	    courseType = "";
	if (postcode == null)
	    postcode = "";
	if (distance == null)
	    distance = "";

	// Check for username
	if (username == null || username.trim().equals(""))
	{
	    res = MessageBuilder.createErrorMessage("searchCourses",
		    ErrorCodes.INVALID_SEARCH_SET,
		    "Invalid user name. Access to this service was denied");
	    return res;
	}

	// Check for access to Learn Direct
	int learnDirect = -1;
	try
	{
	    learnDirect = Integer.valueOf(includeLearnDirect).intValue();
	} catch (NumberFormatException nfe)
	{
	    res = MessageBuilder.createErrorMessage("searchCourses",
		    ErrorCodes.INVALID_SEARCH_SET,
		    "You have to provide 1 or 0 in 'includeLD' flag!");

	    return res;
	}
	if (learnDirect < 0)
	{
	    res = MessageBuilder.createErrorMessage("searchCourses",
		    ErrorCodes.INVALID_SEARCH_SET,
		    "You have to provide 1 or 0 in 'includeLD' flag!");
	    return res;
	}

	String[] keywords = null;
	if (keyword == null)
	{
	    res = MessageBuilder.createErrorMessage("searchCourses",
		    ErrorCodes.INVALID_SEARCH_SET,
		    "No search phrase was specified");

	    return res;
	} 
	else if (keyword.contains("AND"))
	    keywords = keyword.split("AND");
	else
	    keywords = new String[] { keyword };

	// Start building the message
	int ldCoursesNum = 0;
	int l4allCoursesNum = 0;

	LearnDirectCourse[] ldCourses = null;
	L4AllCourse[] l4allCourses = null;

	res = MessageBuilder.createSuccessMessageHeader("searchCourses");

	// Check if LearnDirect is included in search or not
	if (learnDirect == 1)
	{
	    String kw = "";
	    for (int k = 0; k < keywords.length; k++)
		kw += keywords[k];

	    ldCourses = LearnDirectCourseSearch.searchLDCourses(kw,
		    courseLevel, postcode, distance);
	    ldCoursesNum = (ldCourses == null) ? 0 : ldCourses.length;
	}

	l4allCourses = L4AllCoursesSearch.searchL4AllCourse(courseSubject,
		courseType, keywords, courseInstitution, courseLevel,
		searchMode);
	l4allCoursesNum = l4allCourses.length;

	res += ("<courses cardinality='" + (ldCoursesNum + l4allCoursesNum) + "'>");

	for (int i = 0; i < ldCoursesNum; i++)
	{
	    if (ldCourses[i] instanceof LearnDirectCourse)
	    {
		LearnDirectCourse ldCourse = (LearnDirectCourse) ldCourses[i];
		res += ("<course learn_direct='1'>");
		res += ("<course_Id>" + ldCourse.getCourseCode() + "</course_Id>");
		res += ("<course_title><![CDATA[" + ldCourse.getTitle() + "]]></course_title>");
		res += ("<course_description><![CDATA["
			+ ldCourse.getDescription() + "]]></course_description>");
		//res += ("<course_url><![CDATA[" + ldCourse.getURL() + "]]></course_url>");
	        res += ("<course_institution><![CDATA[" + ldCourse.getPublisher() + "]]></course_institution>");
		res += ("</course>");
	    }
	}

	for (int i = 0; i < l4allCoursesNum; i++)
	{
	    if (l4allCourses[i] instanceof L4AllCourse)
	    {
		L4AllCourse l4allCourse = (L4AllCourse) l4allCourses[i];
		res += ("<course learn_direct='0'>");
		res += ("<course_Id>" + l4allCourse.getCourseCode() + "</course_Id>");
		res += ("<course_title><![CDATA[" + l4allCourse.getTitle() + "]]></course_title>");
		res += ("<course_description><![CDATA["
			+ l4allCourse.getDescription() + "]]></course_description>");
		//res += ("<course_type><![CDATA[" + l4allCourse.getCourseType() + "]]></course_type>");
		//res += ("<course_url><![CDATA[" + l4allCourse.getURL() + "]]></course_url>");
	        res += ("<course_institution><![CDATA[" + l4allCourse.getInstitution() + "]]></course_institution>");
		res += ("</course>");
	    }
	}

	res += ("</courses>");
	res += (MessageBuilder.createSuccessMessageTail());

	return res;
    }

    private void processRequest(HttpServletRequest request,
	    HttpServletResponse response)
    {
	PrintWriter out = null;
	try
	{
	    out = response.getWriter();
	} catch (IOException e)
	{
	    e.printStackTrace();
	    return;
	}

	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0

	String username = request.getParameter("username");
	Hashtable map = new Hashtable(request.getParameterMap());
	String res = processRequest(username, map);
	out.println(res);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException
    {
	processRequest(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	processRequest(request, response);
    }
}
