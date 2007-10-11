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
 * GetCourseDetails.java
 *
 * Created on 15 Δεκέμβριος 2005, 5:45 μμ
 */

package uk.ac.bbk.dcs.l4all.servlets;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllCourse;
import uk.ac.bbk.dcs.l4all.beans.L4AllCoursesSearch;
import uk.ac.bbk.dcs.l4all.beans.LearnDirectCourseSearch;
import uk.ac.bbk.dcs.l4all.beans.LearnDirectCourse;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

/**
 * 
 * @author George
 * @version
 */
public class GetCourseDetails extends HttpServlet
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
	
	String courseID = getParam(params,"course_id");
	if (courseID == null)
	{
	    res = (MessageBuilder.createErrorMessage("getCourseDetails",
		    ErrorCodes.EMPTY_FIELD_ERROR, "No course ID specified!"));
	    return res;
	}

	if (username == null)
	{
	    res = (MessageBuilder.createErrorMessage("getCourseDetails",
		    ErrorCodes.EMPTY_FIELD_ERROR,
		    "Invalid username. Access was denied."));
	    return res;
	}

	L4AllCourse l4allCourse = L4AllCoursesSearch.getL4AllCourseDetails(courseID);

	if (l4allCourse != null)
	{
	    res = (MessageBuilder.createSuccessMessageHeader("getCourse"));
	    res += l4allCourse.toXML();
//	    res += ("<course learn_direct='0'>");
//	    res += ("<course_id>" + courseID + "</course_id>");
//	    res += ("<course_title><![CDATA[" + l4allCourse.getTitle() + "]]></course_title>");
//	    res += ("<course_description><![CDATA["
//			    + l4allCourse.getDescription()
//			    + "]]></course_description>");
//	    res += ("<course_URL><![CDATA[" + l4allCourse.getURL()
//		    + "]]></course_URL>");
//	    res += ("</course>");
	    res += (MessageBuilder.createSuccessMessageTail());
	    return res;
	}
	
	LearnDirectCourse ldCourse = LearnDirectCourseSearch.getLDCourseDetails(courseID);

	if (ldCourse != null)
	{
	    res = (MessageBuilder.createSuccessMessageHeader("getCourse"));
	    res += ldCourse.toXML();
//	    res += ("<course learn_direct='1'>");
//	    res += ("<course_id>" + courseID + "</course_id>");
//	    res += ("<course_title><![CDATA[" + ldCourse.getTitle() + "]]></course_title>");
//	    res += ("<course_description><![CDATA[" + ldCourse.getDescription() + "]]></course_description>");
//	    res += ("<course_URL><![CDATA[" + ldCourse.getURL()+ "]]></course_URL>");
//	    res += ("</course>");
	    res += (MessageBuilder.createSuccessMessageTail());

	    return res;
	}
	
	// No such course exists
	res = (MessageBuilder.createErrorMessage("getCourseDetails",
			ErrorCodes.EMPTY_RESULT_SET, "No such course available!"));
	    return res;

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     */
    protected void processRequest(HttpServletRequest request,
	    HttpServletResponse response)
    {
	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	PrintWriter out = null;
	try
	{
	    out = response.getWriter();

	    //String courseID = request.getParameter("course_id");
	    String username = request.getParameter("username");
	    Hashtable map = new Hashtable(request.getParameterMap());
	    String res = processRequest(username, map);
	    out.println(res);	

//	    if (courseID == null)
//	    {
//		out.println(MessageBuilder.createErrorMessage(
//			"getCourseDetails", ErrorCodes.EMPTY_FIELD_ERROR,
//			"No course ID specified!"));
//		return;
//	    }
//
//	    if (username == null)
//	    {
//		out.println(MessageBuilder.createErrorMessage(
//			"getCourseDetails", ErrorCodes.EMPTY_FIELD_ERROR,
//			"Invalid username. Access was denied."));
//		return;
//	    }
//
//	    LearnDirectCourse ldCourse = LearnDirectCourseSearch
//		    .getLDCourseDetails(courseID);
//
//	    if (ldCourse != null)
//	    {
//
//		out.println(MessageBuilder
//			.createSuccessMessageHeader("getCourse"));
//		out.println("<course learn_direct='1'>");
//		out.println("<course_id>" + courseID + "</course_id>");
//		out.println("<course_title><![CDATA[" + ldCourse.getTitle()
//			+ "]]></course_title>");
//		out.println("<course_description><![CDATA["
//			+ ldCourse.getDescription()
//			+ "]]></course_description>");
//		out.println("<course_URL><![CDATA[" + ldCourse.getURL()
//			+ "]]></course_URL>");
//		out.println("</course>");
//		out.println(MessageBuilder.createSuccessMessageTail());
//		out.close();
//
//		return;
//	    }
//
//	    L4AllCourse l4allCourse = L4AllCoursesSearch
//		    .getL4AllCourseDetails(courseID);
//
//	    if (l4allCourse != null)
//	    {
//		out.println(MessageBuilder
//			.createSuccessMessageHeader("getCourse"));
//		out.println("<course learn_direct='0'>");
//		out.println("<course_id>" + courseID + "</course_id>");
//		out.println("<course_title><![CDATA[" + l4allCourse.getTitle()
//			+ "]]></course_title>");
//		out.println("<course_description><![CDATA["
//			+ l4allCourse.getDescription()
//			+ "]]></course_description>");
//		out.println("<course_URL><![CDATA[" + l4allCourse.getURL()
//			+ "]]></course_URL>");
//		out.println("</course>");
//		out.println(MessageBuilder.createSuccessMessageTail());
//		out.close();
//
//		return;
//	    }
	} catch (IOException e)
	{
	    String msg = e.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("getCourse",
		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
	    return;
	} 
//	catch (NumberFormatException nfe)
//	{
//	    String msg = nfe.getLocalizedMessage();
//	    out.println(MessageBuilder.createErrorMessage("getCourse",
//		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
//	    return;
//	}

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
    // the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException
    {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo()
    {
	return "Short description";
    }
    // </editor-fold>
}
