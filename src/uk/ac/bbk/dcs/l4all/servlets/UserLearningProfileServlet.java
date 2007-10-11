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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllUserManager;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

public class UserLearningProfileServlet extends HttpServlet
{
    private void processRequest(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException
    {
	response.setContentType("text/xml");
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0

	PrintWriter out = null;
	try
	{
	    out = response.getWriter();
	    String pastOccupation = request.getParameter("pastOccupation");
	    String presentOccupation = request.getParameter("presentOccupation");
	    String[] qualificationsList = request.getParameterValues("quals");
	    String[] skillsList = request.getParameterValues("skills");
	    String[] interestsList = request.getParameterValues("interests");
	    String[] goalsList = request.getParameterValues("goals");
	    String studyType = request.getParameter("studyType");
	    String hoursOfStudyPW = request.getParameter("hos");
	    String budget = request.getParameter("budget");
	    String learningMethod = request.getParameter("learningMethod");
	    String disability = request.getParameter("disability");
	    String username = (String) request.getParameter("username");
	    String newUser = request.getParameter("newUser");

	    if (pastOccupation == null) pastOccupation = "";
	    if (presentOccupation == null) presentOccupation = "";

	    //			String[] tQuals = null;
	    //			if (qualificationsList.trim().equals("")
	    //					|| qualificationsList == null)
	    //				tQuals = null;
	    //			else if (qualificationsList.contains("+"))
	    //				tQuals = qualificationsList.split("+");
	    //			else
	    //				tQuals = new String[]{qualificationsList};

	    //			String[] tSkills = null;
	    //			if (skillsList.trim().equals("") || skillsList == null)
	    //				tSkills = null;
	    //			else if (skillsList.contains("+"))
	    //				tSkills = skillsList.split("+");
	    //			else
	    //				tSkills = new String[]{skillsList};

	    //			String[] tInterests = null;
	    //			if (interestsList.trim().equals("") || interestsList == null)
	    //				tInterests = null;
	    //			else if (interestsList.contains("+"))
	    //				tInterests = interestsList.split("+");
	    //			else
	    //				tInterests = new String[]{interestsList} ;
	    //
	    //			String[] tGoals = null;
	    //			if (goalsList.trim().equals("") || goalsList == null)
	    //				tGoals = null;
	    //			else if (goalsList.contains("+"))
	    //				tGoals = goalsList.split("+");
	    //			else
	    //				tGoals = new String[]{goalsList};

	    if (studyType == null) studyType = "";

	    int hos = 0;
	    if (hoursOfStudyPW == null || hoursOfStudyPW.trim().equals(""))
		hos = 0;
	    else
		hos = Integer.parseInt(hoursOfStudyPW);

	    int bdgt = 0;
	    if (budget == null || budget.trim().equals(""))
		bdgt = 0;
	    else
		bdgt = Integer.parseInt(budget);

	    if (learningMethod == null)
		learningMethod = "";

	    if (disability == null || disability.trim().equals(""))
		disability = "None";

	    if (username == null || username.trim().equals(""))
	    {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage(
			"saveUserLearningProfile",
			ErrorCodes.EMPTY_FIELD_ERROR,
			"No username was specified"));
		return;
	    }

	    ServletContext context = getServletContext();
	    RequestDispatcher rd = null;

	    if (newUser == null)
            {
		out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
		out.println(MessageBuilder.createErrorMessage(
			"saveUserLearningProfile",
			ErrorCodes.INVALID_FIELD_FORMAT,
			"You have to choose between an existing or a new user"));
		return;
//                response.setContentType("text/html");
//                out.println("<html><body  background=\"jsp/images/REGbKG3dLNG.jpg\">");
//                out.println("<h2>ERROR</h2>");
//                out.println("Error Code : " + ErrorCodes.INVALID_FIELD_FORMAT);
//                out.println("Error Description : You have to choose between an existing or a new user");
//                out.println("</body></html>");
//                return;
            }
	    else if (newUser.trim().equals("1"))
            {
                L4AllUserManager usrMgr = new L4AllUserManager();
                Message msg = usrMgr.addUserLearningPref(pastOccupation,
            	    presentOccupation, qualificationsList, skillsList,
            	    interestsList, goalsList, studyType,
            	    learningMethod, disability, bdgt, hos, username);
            
                if (msg.getErrorCode() == ErrorCodes.SUCCESS)
                {
                    //out.println(MessageBuilder.createSuccessMessageHeader("saveUserLearningProfile"));
                    //out.println("<username>" + username + "</username>");
                    //out.println(MessageBuilder.createSuccessMessageTail());
                    response.setContentType("text/html");
                    rd = context.getRequestDispatcher("/jsp/success.jsp?username="+ username + "&timeline=" + username);
                    rd.forward(request, response);
                    return;
                } 
                else
                {
                    response.setContentType("text/html");
                    out.println("<html><body  background=\"jsp/images/REGbKG3dLNG.jpg\">");
                    out.println("<h2>ERROR</h2>");
                    out.println("Error Code : " + msg.getErrorCode());
                    out.println("Error Description : " + msg.getErrorDescription());
                    out.println("</body></html>");
                    return;
                }
            } 
	    else if (newUser.trim().equals("0"))
	    {
		L4AllUserManager usrMgr = new L4AllUserManager();
		Message msg = usrMgr.changeUserLearningPreferences(
			    pastOccupation, presentOccupation, skillsList,
			    goalsList, qualificationsList, interestsList, bdgt,
			    studyType, hos, disability, learningMethod,
			    username);

                if (msg.getErrorCode() == ErrorCodes.SUCCESS)
                {
                    out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/success.xsl\"?>");
                    out.println(MessageBuilder.createSuccessMessageHeader("saveUserLearningProfile"));
                    out.println("<username>" + username + "</username>");
                    out.println(MessageBuilder.createSuccessMessageTail());
                    //response.setContentType("text/html");
                    //rd = context
                    //	.getRequestDispatcher("/jsp/success.jsp?username="
                    //		+ username + "&timeline=" + username);
                    //rd.forward(request, response);
                    
                    return;
                } 
                else
                {
    			out.println("<?xml-stylesheet type=\"text/xsl\" href=\"xsl/error.xsl\"?>");
    			out.println(MessageBuilder.createErrorMessage(
    					"saveUserLearningProfile",
    					msg.getErrorCode(),
    					msg.getErrorDescription()));
    			return;

		//					out.println(MessageBuilder.createErrorMessage(
                    //							"saveUserLearningProfile", msg.getErrorCode(), msg
                    //									.getErrorDescription()));
                    //					response.setContentType("text/html");
//                    out
//                    	.println("<html><body  background=\"jsp/images/REGbKG3dLNG.jpg\">");
//                    out.println("<h2>ERROR</h2>");
//                    out.println("Error Code : " + msg.getErrorCode());
//                    out.println("Error Description : "
//                    	+ msg.getErrorDescription());
//                    out.println("</body></html>");
//                    return;
                }
	    }

	} 
	catch (IOException e)
	{
	    String msg = e.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage(
		    "saveUserLearningProfile", ErrorCodes.INVALID_FIELD_FORMAT,
		    msg));
	    return;
	} 
//	catch (NumberFormatException nfe)
//	{
//	    String msg = nfe.getLocalizedMessage();
//	    out.println(MessageBuilder.createErrorMessage(
//		    "saveUserLearningProfile", ErrorCodes.INVALID_FIELD_FORMAT,
//		    msg));
//	    return;
//	}
    }

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
}
