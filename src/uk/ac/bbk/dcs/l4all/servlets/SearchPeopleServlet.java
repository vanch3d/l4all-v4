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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUsersSearch;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

/**
 * 
 * @author George
 * @version
 */
public class SearchPeopleServlet extends HttpServlet {

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
			HttpServletResponse response) throws ServletException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		PrintWriter out = null;
 
		try {
			out = response.getWriter();
			String age = request.getParameter("age");
			String occupations = request.getParameter("occupation");
			String skills = request.getParameter("skill");
			String interests = request.getParameter("interest");
			String goals = request.getParameter("goal");
			String disability = request.getParameter("disability");
			String username = request.getParameter("username");

			if (age == null || age.trim().equals(""))
				age = "0";

			if (occupations == null || occupations.trim().equals(""))
				occupations = "";

			String[] tSkills = null;
			if (skills == null || skills.trim().equals(""))
				tSkills = null;
			else if (skills.contains("+"))
				tSkills = skills.split("+");
			else
				tSkills = new String[]{skills};

			String[] tInterests = null;
			if (interests == null || interests.trim().equals(""))
				tInterests = null;
			else if (interests.contains("+"))
				tInterests = interests.split("+");
			else
				tInterests = new String[]{interests} ;

			String[] tGoals = null;
			if (goals == null || goals.trim().equals(""))
				tGoals = null;
			else if (goals.contains("+"))
				tGoals = goals.split("+");
			else
				tGoals = new String[]{goals};

			if (disability == null)
				disability = "false";

			if (username == null || username.trim().equalsIgnoreCase("")) {
				out
						.println(MessageBuilder
								.createErrorMessage("searchPeople",
										ErrorCodes.INVALID_SEARCH_SET,
										"Invalid user name. Access to this service was denied"));

				return;
			}

			// Call the search for people function from the backend
			L4AllUser[] l4allUsers = L4AllUsersSearch.searchL4AllUsers(Integer
					.parseInt(age), occupations, tSkills, tInterests,
					tGoals, Boolean.valueOf(disability).booleanValue(),
					username);
			out.println(MessageBuilder
					.createSuccessMessageHeader("searchPeople"));
			out.println("<users cardinality='" + l4allUsers.length + "'>");
			for (int i = 0; i < l4allUsers.length; i++) {
				out.println("<user>");
				out.println("<username>" + l4allUsers[i].getUsername()
						+ "</username>");
				out.println("<name><![CDATA[" + l4allUsers[i].getName()
						+ "]]></name>");
				out.println("</user>");
			}
			out.println("</users>");
			out.println(MessageBuilder.createSuccessMessageTail());

			out.close();
		} catch (IOException e) {
			String msg = e.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage("searchPeople",
					ErrorCodes.INVALID_FIELD_FORMAT, msg));
			return;
		} catch (NumberFormatException nfe) {
			String msg = nfe.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage("searchPeople",
					ErrorCodes.INVALID_FIELD_FORMAT, msg));
			return;
		}
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
			HttpServletResponse response) throws ServletException, IOException {
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
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "Short description";
	}
	// </editor-fold>
}
