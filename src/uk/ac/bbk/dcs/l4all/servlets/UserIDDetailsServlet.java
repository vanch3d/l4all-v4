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
import uk.ac.bbk.dcs.l4all.beans.L4AllUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUserManager;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

/**
 * @author George Papamarkos
 * 
 * SearchServlet.java uk.ac.bbk.dcs.l4all.beans
 */
public class UserIDDetailsServlet extends L4ALLServiceServlet
{
    private static final long serialVersionUID = 1923861500231585782L;

    /* (non-Javadoc)
     * @see uk.ac.bbk.dcs.l4all.servlets.L4ALLServiceServlet#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
	PrintWriter out = null;
	try
	{
	    out = response.getWriter();
	    ServletContext context = getServletContext();
	    RequestDispatcher rd = null;

	    L4AllUser user = null;
	    L4AllUserManager userManager = new L4AllUserManager();

	    if (user == null)
		user = new L4AllUser();

	    response.setContentType("text/html");
	    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    String confirmPassword = request.getParameter("confirmPassword");
	    String name = request.getParameter("name");
	    String age = request.getParameter("age");
	    String email = request.getParameter("email");
	    String gender = request.getParameter("gender");
	    String travelWill = request.getParameter("travelWill");
	    String myLocation = request.getParameter("my_location");
	    String redirect = request.getParameter("redirect");

	    String newUser = request.getParameter("newUser");
	    if (newUser== null) newUser = "1";

	    if (isParamEmpty(redirect))
		redirect= (newUser.equals("0"))? "/jsp/editDetails.jsp" : "/jsp/register.jsp";
	    
	    // Get the access status of the user (USER if non-existant)
	    String accessStatus = request.getParameter("status");
	    if (accessStatus == null)
	    {
		accessStatus = Trails.TYPE_USER;
	    }

	    if (isParamEmpty(username))
	    {
		request.setAttribute("LOGIN_MSG","Mandatory field username is missing");
		rd = context.getRequestDispatcher(redirect);
		rd.forward(request, response);
		return;
	    }

	    if (isParamEmpty(name))
	    {
		request.setAttribute("LOGIN_MSG","Mandatory field Full Name is missing");
		rd = context.getRequestDispatcher(redirect);
		rd.forward(request, response);
		return;
	    }

	    if (isParamEmpty(age))
	    {
		request.setAttribute("LOGIN_MSG","Mandatory field Year of Birth is missing");
		rd = context.getRequestDispatcher(redirect);
		rd.forward(request, response);
		return;
	    }

	    if (isParamEmpty(email))
	    {
		request.setAttribute("LOGIN_MSG","Mandatory field Email is missing");
		rd = context.getRequestDispatcher(redirect);
		rd.forward(request, response);
		return;
	    }
	    boolean pwdEmpty = isParamEmpty(password);
	    boolean pwdConfEmpty = isParamEmpty(confirmPassword);
	    
	    if (!pwdEmpty && !pwdConfEmpty)
	    {
		if (!confirmPassword.trim().equals(password))
		{
		    request.setAttribute("LOGIN_MSG","Passwords do not match!");
		    rd = context.getRequestDispatcher(redirect);
		    rd.forward(request, response);
		    return;
		}
	    }
	    else if(!pwdEmpty || !pwdConfEmpty)
	    {
		request.setAttribute("LOGIN_MSG","Mandatory field password is missing");
		rd = context.getRequestDispatcher(redirect);
		rd.forward(request, response);
		return;
	    }

	    if (isParamEmpty(gender))
	    {
		gender = "Unspecified";
	    }

	    if (isParamEmpty(myLocation))
	    {
		myLocation = "Unspecified";
	    }

	    if (isParamEmpty(travelWill))
	    {
		travelWill = "0";
	    }

	    user.setUsername(username);
	    user.setPassword(password);
	    user.setName(name);
	    user.setAge(Integer.valueOf(age).intValue());
	    user.setEmail(email);
	    user.setGender(gender);
	    user.setMyLocation(myLocation);
	    user.setTravelWillingness(Integer.valueOf(travelWill).intValue());

	    if (newUser.equals("1"))
	    {
		Message msg = userManager.createUser(accessStatus, username,
			password, name, Integer.valueOf(age).intValue(),
			gender, myLocation, Integer.valueOf(travelWill)
				.intValue(), email);

		Message msg2 = userManager.addUserLearningPref("Unspecified",
			"Unspecified", new String[]
			{ "Unspecified" }, new String[]
			{ "Unspecified" }, new String[]
			{ "Unspecified" }, new String[]
			{ "Unspecified" }, "Unspecified", "Unspecified",
			"Unspecified", 0, 0, username);

		/// TODO Dummy solution ... replace with a proper form etc.
		UserTrailManager userTrails = new UserTrailManager();
		Message msg1 = userTrails.addNewTrail(username, username, name
			+ "'s timeline", accessStatus, 1, "", username + ", "
			+ name, 1960, 2030);

		if (msg.getErrorCode() == ErrorCodes.SUCCESS)
		{
		    //					out.println(MessageBuilder
		    //							.createSuccessMessageHeader("saveUserIDDetails"));
		    out.println("<script type=\"text/javascript\">");
		    out.println("function SaveSetting(){");
		    out.println("	opener.location.reload(true);");
		    out.println("	opener.focus();");
		    out.println("	self.close();");
		    out.println("}");
		    out.println("");
		    out.println("</script>");
		    out.println("<body bgcolor=\"#BABA96\">");
		    out.println("<h2>You have registered successfully.</h2>\n");
		    out
			    .println("<h3>Click <a href='jsp/login.jsp'>here</a> to login</h3>\n");
		    out.println("</body>");
		    //					ServletContext context =getServletContext() ;
		    //					RequestDispatcher rd = context.getRequestDispatcher("/jsp/login.jsp");
		    //					rd.forward(request, response);
		    //					out.println(MessageBuilder.createSuccessMessageTail());
		} else
		{
		    out.println(MessageBuilder.createErrorMessage(
			    "saveUserIDDetails", msg.getErrorCode(), msg
				    .getErrorDescription()));
		}
	    } else if (newUser.equals("0"))
	    {
		Message msg = userManager.changeUserIdentificationDetails(
			username, password, name, Integer.valueOf(age)
				.intValue(), gender, myLocation, Integer
				.valueOf(travelWill).intValue(), email);

		if (msg.getErrorCode() == ErrorCodes.SUCCESS)
		{
		    //					out.println(MessageBuilder
		    //							.createSuccessMessageHeader("saveUserIDDetails"));
		    out.println("<script type=\"text/javascript\">");
		    out.println("function SaveSetting(){");
		    out.println("if (opener){");
		    out.println("	opener.location.reload(true);");
		    out.println("	opener.focus();");
		    out.println("	self.close();");
		    out.println("} else {");
		    out.println("	parent.location.reload(true);");
		    out.println("	parent.ajaxwin.hide();");
		    out.println("}}");
		    out.println("");
		    out.println("</script>");
		    out
			    .println("<html><body  background=\"jsp/images/REGbKG3dLNG.jpg\" onload='SaveSetting();'>");
		    out
			    .println("<h2>You have changed your details successfully</h2>");
		    //out.println("<h2>Click <a href='jsp/home.jsp?username="+username+"&timeline="+username+"'>here</a> to return to home</h2>\n");
		    out
			    .println("<h2>Click <a href='#' onclick='javascript:SaveSetting();return false;'>here</a> to return to home</h2>\n");
		    out.println("</body></html>");
		    //					out.println(MessageBuilder.createSuccessMessageTail());
		} else
		{
		    out.println(MessageBuilder.createErrorMessage(
			    "saveUserIDDetails", msg.getErrorCode(), msg
				    .getErrorDescription()));
		}
	    } else
	    {
		out.println(MessageBuilder.createErrorMessage(
			"saveUserIDDetails", ErrorCodes.INVALID_FIELD_FORMAT,
			"The newUser request parameter has to be '0' or '1'"));
	    }

	    out.close();
	} catch (IOException e)
	{
	    String msg = e.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("saveUserIDDetails",
		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
	    return;
	} catch (NumberFormatException nfe)
	{
	    String msg = nfe.getLocalizedMessage();
	    out.println(MessageBuilder.createErrorMessage("saveUserIDDetails",
		    ErrorCodes.INVALID_FIELD_FORMAT, msg));
	    return;
	}
    }

}