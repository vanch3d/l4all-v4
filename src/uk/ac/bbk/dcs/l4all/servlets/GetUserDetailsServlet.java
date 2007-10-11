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

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import uk.ac.bbk.dcs.l4all.beans.ErrorCodes;
import uk.ac.bbk.dcs.l4all.beans.L4AllUser;
import uk.ac.bbk.dcs.l4all.beans.L4AllUserManager;
import uk.ac.bbk.dcs.l4all.beans.Message;
import uk.ac.bbk.dcs.l4all.util.MessageBuilder;

/**
 * 
 * @author George
 * @version
 */
public class GetUserDetailsServlet extends HttpServlet {

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
			HttpServletResponse response) {

		String requesting_username = request.getParameter("req_username");
		String username = request.getParameter("username");

		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		PrintWriter out = null;
		try {
			out = response.getWriter();

			L4AllUserManager usrMgr = new L4AllUserManager();
			Message idMsg = usrMgr
					.getUserIdentificationDetails(requesting_username);
			Message lpMsg = usrMgr
					.getUserLearningPrefDetails(requesting_username);

			if (idMsg.getErrorCode() == ErrorCodes.SUCCESS) {

				L4AllUser idUser = (L4AllUser) idMsg.getResultObject();
				L4AllUser lpUser = null ;
				if (lpMsg != null)
					lpUser = (L4AllUser) lpMsg.getResultObject();

				idUser.addLearningPref(lpUser);
				
				out.println(MessageBuilder
						.createSuccessMessageHeader("getUserDetails"));
				out.println("<user>");
				out.println("<userID>" + idUser.getId() + "</userID>");
				out.println("<fullname>" + idUser.getName() + "</fullname>");
				out.println("<age>" + idUser.getAge() + "</age>");
				out.println("<location>" + idUser.getMyLocation()
						+ "</location>");
				if (lpUser == null) {
					out.println("<occupation>Not specified</occupation>");
					out.println("<qual><![CDATA[Not specified]]></qual>");
					out.println("<skills><![CDATA[Not specified]]></skills>");
					out.println("<interests><![CDATA[Not specified]]></interests>");
				} else {
					out.println("<occupation>" + lpUser.getPresentOccupation()
							+ "</occupation>");
					out.println("<qual><![CDATA["
							+ lpUser.getInAString(lpUser.getQualifications())
							+ "]]></qual>");
					out.println("<skills><![CDATA["
							+ lpUser.getInAString(lpUser.getSkills())
							+ "]]></skills>");
					out.println("<interests><![CDATA["
							+ lpUser.getInAString(lpUser.getInterests())
							+ "]]></interests>");
				}
				out.println("</user>");
				out.println(MessageBuilder.createSuccessMessageTail());
			} else {
				if (idMsg.getErrorCode() != ErrorCodes.SUCCESS) {
					out.print(MessageBuilder.createErrorMessage(
							"getUserDetails", idMsg.getErrorCode(), idMsg
									.getErrorDescription()));
					return;
				}

				if (lpMsg.getErrorCode() != ErrorCodes.SUCCESS) {
					out.print(MessageBuilder.createErrorMessage(
							"getUserDetails", lpMsg.getErrorCode(), lpMsg
									.getErrorDescription()));
					return;
				}
			}

			out.close();
		} catch (IOException e) {
			String msg = e.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage("getUserDetails",
					ErrorCodes.INVALID_FIELD_FORMAT, msg));
			return;
		} catch (NumberFormatException nfe) {
			String msg = nfe.getLocalizedMessage();
			out.println(MessageBuilder.createErrorMessage("getUserDetails",
					ErrorCodes.INVALID_FIELD_FORMAT, msg));
			return;
		}
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
