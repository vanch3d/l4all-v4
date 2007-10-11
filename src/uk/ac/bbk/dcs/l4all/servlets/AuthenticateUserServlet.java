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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.bbk.dcs.l4all.beans.AuthenticateUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUserManager;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.beans.Trail;
import uk.ac.bbk.dcs.l4all.beans.UserTrailManager;
import uk.ac.bbk.dcs.l4all.vocabulary.Trails;

/**
 * @author George Papamarkos
 *
 * AuthenticateUserServlet.java
 * uk.ac.bbk.dcs.l4all.beans
 */
public class AuthenticateUserServlet extends HttpServlet
{

    public void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	HttpSession session = request.getSession();

	response.setContentType("text/html");

	ServletContext context = getServletContext();
	RequestDispatcher rd = null;

	AuthenticateUser auth = new AuthenticateUser();
	String username = request.getParameter("username");
	String password = request.getParameter("password");

	if (auth.validate(username, password))
	{
	    L4AllUserManager um = new L4AllUserManager();
	    Message msgUser = um.getUserIdentificationDetails(username);
	    L4AllUser user = (L4AllUser) msgUser.getResultObject();
	    
	    UserTrailManager tm = new UserTrailManager();
	    Message msgTrail = tm.getUserTrailDetails(username);
	    Trail userTrail = (Trail) msgTrail.getResultObject();

	    if (userTrail == null)
	    {
		//session.setAttribute("username", username);
		rd = context.getRequestDispatcher("/jsp/no_timeline_defined.jsp");
		rd.forward(request, response);
		return;
	    }

	    String trailName = userTrail.getTrailName();
	    session.setAttribute(ServletUtilities.SESSION_TIMELINE, trailName);
	    session.setAttribute(ServletUtilities.SESSION_USERNAME, username);
	    
	    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	    response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility

	    //rd = context.getRequestDispatcher("/jsp/home.jsp?username=" + username + "&timeline=" + trailName);
	    //rd = context.getRequestDispatcher("/jsp/timeline.jsp");
	    //rd.forward(request, response);
	    
	    String serverURL = "http://" + request.getServerName() + ":" + 
	    		request.getServerPort() + request.getContextPath() + "/";
	    if (Trails.TYPE_TEMPLATE.equals(user.getStatus()))
		response.sendRedirect(response.encodeRedirectURL(serverURL + "jsp/recomdesk.jsp"));
	    else
		response.sendRedirect(response.encodeRedirectURL(serverURL + "jsp/timeline.jsp"));

	} 
	else
	{
	    request.setAttribute("LOGIN_MSG","Invalid Username or password. Please try again.");
	    rd = context.getRequestDispatcher("/jsp/login.jsp");
	    rd.include(request, response);
	}

    }
}
